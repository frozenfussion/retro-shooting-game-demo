package com.example.retroshooter.engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.game.Game
import com.example.retroshooter.graphics.AndroidGfx
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.min

/**
 * The screen the game is drawn on.
 *
 * It does three jobs:
 *  1. Runs the game loop on its own thread ([GameThread]) while the screen is visible.
 *  2. Draws the finished game picture onto the phone screen, scaled up with no smoothing.
 *  3. Turns touches on the phone screen into taps in game-pixel coordinates.
 *
 * The game is always 180 game pixels wide. The height is chosen to fit the
 * phone (see [GameConfig.SCREEN_HEIGHT_MIN]), so tall phones get a taller sky.
 */
class GameView(context: Context, private val game: Game) : SurfaceView(context), SurfaceHolder.Callback {

    /** Where the game picture sits on the phone screen. */
    private class Layout(val gameHeight: Int, val scale: Float, val left: Float, val top: Float)

    private val taps = ConcurrentLinkedQueue<FloatArray>()
    private val presentPaint = Paint().apply {
        isFilterBitmap = false // nearest-neighbour scaling = crisp pixels
        isAntiAlias = false
    }
    private val dest = RectF()

    private var thread: GameThread? = null
    private var gfx: AndroidGfx? = null

    @Volatile private var surfaceWidth = 0
    @Volatile private var surfaceHeight = 0

    /** Space at the top of the screen taken by a camera notch / cut-out. The game is drawn below it. */
    @Volatile private var topInset = 0

    @Volatile private var layout: Layout? = null

    init {
        holder.addCallback(this)
        isFocusable = true
        setOnApplyWindowInsetsListener { _, insets ->
            if (Build.VERSION.SDK_INT >= 28) {
                topInset = insets.displayCutout?.safeInsetTop ?: 0
            }
            insets
        }
    }

    // ------------------------------------------------------------------
    // Surface lifecycle: start the game loop when the screen appears, stop it when it goes away.
    // ------------------------------------------------------------------

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        surfaceWidth = width
        surfaceHeight = height
        if (thread == null) {
            thread = GameThread(holder, game, this).also { it.start() }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread?.stopAndJoin()
        thread = null
    }

    // ------------------------------------------------------------------
    // Touch
    // ------------------------------------------------------------------

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val l = layout ?: return true
        if (event.actionMasked == MotionEvent.ACTION_DOWN || event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            val i = event.actionIndex
            // Convert phone-screen pixels into game pixels.
            taps.add(floatArrayOf((event.getX(i) - l.left) / l.scale, (event.getY(i) - l.top) / l.scale))
        }
        return true
    }

    /** Hands the queued taps to the game. Called by the game thread, so the game is only ever touched by one thread. */
    fun deliverTaps() {
        while (true) {
            val tap = taps.poll() ?: break
            game.onTap(tap[0], tap[1])
        }
    }

    // ------------------------------------------------------------------
    // Drawing (called by the game thread once per frame)
    // ------------------------------------------------------------------

    fun render(canvas: Canvas) {
        val sw = surfaceWidth
        val sh = surfaceHeight
        if (sw == 0 || sh == 0) return

        // Work out how big the game screen should be for this phone.
        val availableHeight = (sh - topInset).coerceAtLeast(1)
        val widthScale = sw / GameConfig.SCREEN_WIDTH.toFloat()
        val gameHeight = (availableHeight / widthScale).toInt()
            .coerceIn(GameConfig.SCREEN_HEIGHT_MIN, GameConfig.SCREEN_HEIGHT_MAX)
        val scale = min(widthScale, availableHeight / gameHeight.toFloat())

        var g = gfx
        if (g == null || g.height != gameHeight) {
            g = AndroidGfx(GameConfig.SCREEN_WIDTH, gameHeight)
            gfx = g
            game.resize(gameHeight)
        }
        val left = (sw - GameConfig.SCREEN_WIDTH * scale) / 2f
        val top = topInset + (availableHeight - gameHeight * scale) / 2f
        layout = Layout(gameHeight, scale, left, top)

        game.draw(g)

        canvas.drawColor(Color.BLACK)
        dest.set(left, top, left + GameConfig.SCREEN_WIDTH * scale, top + gameHeight * scale)
        canvas.drawBitmap(g.frame, null, dest, presentPaint)
    }
}
