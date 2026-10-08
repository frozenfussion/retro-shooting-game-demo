package com.example.retroshooter.game

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.Background
import com.example.retroshooter.graphics.Clip
import com.example.retroshooter.graphics.Colors
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.LOSE_PILOT_BODY
import com.example.retroshooter.graphics.LOSE_PILOT_HANDS
import com.example.retroshooter.graphics.LOSE_PILOT_HEAD
import com.example.retroshooter.graphics.OFFICE_DESK
import com.example.retroshooter.graphics.PLANE
import com.example.retroshooter.graphics.PROP
import com.example.retroshooter.graphics.PixelFont
import com.example.retroshooter.graphics.TextBox
import com.example.retroshooter.graphics.TextLine
import com.example.retroshooter.graphics.TimeOfDay
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * The "GAME OVER" splash screen: the enemy pilot, grinning behind his mahogany desk, in front
 * of a three-panel window that goes all the way up to the ceiling. A comic bubble says
 * "YOU'RE A LOSER." and the blue plane cruises past outside.
 *
 * The pilot has the same face as the soldier on the win screen (no beard, yellow hair).
 * His head is a separate sprite so it can wobble and laugh, just like in the game.
 */
object GameOverScreen {
    /** The close-up pilot is drawn at double size. */
    private const val SCALE = 2

    private const val WALL = 0xFF16352F.toInt()
    private const val FRAME = 0xFF10161F.toInt()
    private const val FRAME_EDGE = 0xFF5B6174.toInt()
    private const val CORNICE_DARK = 0xFF3A1410.toInt()
    private const val CORNICE_LIGHT = 0xFFA8503A.toInt()
    private const val CORNICE_SHADOW = 0xFF0D201C.toInt()
    private const val INK = 0xFF15122E.toInt()
    private const val PAPER = 0xFFFFFFFF.toInt()

    /** Left edge and width of the three window panes. */
    private val panes = listOf(14 to 44, 68 to 44, 122 to 44)

    /**
     * @param background draws the sky seen through the window
     * @param seconds time since the game started (drives the animation)
     * @param stateSeconds time since the game over screen appeared
     * @param level the level the player reached
     */
    fun draw(
        gfx: Gfx,
        background: Background,
        timeOfDay: TimeOfDay,
        seconds: Float,
        stateSeconds: Float,
        screenHeight: Int,
        level: Int,
    ) {
        val w = GameConfig.SCREEN_WIDTH
        val deskY = screenHeight - OFFICE_DESK.height
        val windowTop = 14
        val windowBottom = deskY - 22

        // Wall and the wooden cornice along the ceiling.
        gfx.fillRect(0, 0, w, screenHeight, WALL)
        gfx.fillRect(0, 0, w, 9, CORNICE_DARK)
        gfx.fillRect(0, 9, w, 2, CORNICE_LIGHT)
        gfx.fillRect(0, 11, w, 3, CORNICE_SHADOW)

        // The sky inside each pane, then the blue plane flying past outside.
        for ((left, width) in panes) {
            background.draw(gfx, timeOfDay, screenHeight, seconds, Clip(left, windowTop, width, windowBottom - windowTop), withGround = false)
        }
        val planeX = ((seconds * 18f) % (w + 80)).toInt() - 60
        val planeY = 150
        gfx.drawSprite(PLANE, planeX, planeY)
        gfx.drawSprite(if ((seconds * 14f).toInt() % 2 == 0) PROP[0] else PROP[1], planeX + PLANE.width, planeY + 2)
        // The plane is only seen through the glass, so repaint the wall on both sides of the window.
        gfx.fillRect(0, windowTop - 4, 10, windowBottom - windowTop + 10, WALL)
        gfx.fillRect(170, windowTop - 4, 10, windowBottom - windowTop + 10, WALL)

        // Window frames: dark steel with a light edge.
        gfx.fillRect(10, windowTop - 4, 160, 4, FRAME)
        gfx.fillRect(10, windowBottom, 160, 6, FRAME)
        gfx.fillRect(10, windowTop - 4, 4, windowBottom - windowTop + 10, FRAME)
        gfx.fillRect(166, windowTop - 4, 4, windowBottom - windowTop + 10, FRAME)
        gfx.fillRect(58, windowTop, 10, windowBottom - windowTop, FRAME)
        gfx.fillRect(112, windowTop, 10, windowBottom - windowTop, FRAME)
        for (x in intArrayOf(14, 58, 112)) gfx.fillRect(x, windowTop, 1, windowBottom - windowTop, FRAME_EDGE)
        gfx.fillRect(10, windowBottom, 160, 1, FRAME_EDGE)

        // The pilot: suit first, then the head (wobbling and laughing like in the game).
        val bodyY = deskY - 62
        gfx.drawSprite(LOSE_PILOT_BODY, w / 2 - LOSE_PILOT_BODY.width * SCALE / 2, bodyY, SCALE)
        val wobbleX = sin(seconds * 5f).roundToInt()
        val wobbleY = sin(seconds * 9f).roundToInt()
        val head = LOSE_PILOT_HEAD[(seconds * 4f).toInt() % 2]
        val headY = bodyY - head.height * SCALE + 10
        gfx.drawSprite(head, w / 2 - head.width * SCALE / 2 + wobbleX * SCALE, headY + wobbleY * SCALE, SCALE)

        // The desk in front of him, and his clasped hands resting on top of it.
        gfx.drawSprite(OFFICE_DESK, 0, deskY)
        gfx.drawSprite(
            LOSE_PILOT_HANDS,
            w / 2 - LOSE_PILOT_HANDS.width * SCALE / 2,
            deskY - LOSE_PILOT_HANDS.height * SCALE + 10,
            SCALE,
        )

        // The comic bubble, with its tail pointing at the pilot.
        drawBubble(gfx, 8, 86, 84, 40, 56, headY + 56, listOf("YOU'RE A", "LOSER."))

        // Text.
        TextBox.draw(gfx, w / 2, 16, TextLine("GAME OVER", Colors.RED, 3))
        TextBox.draw(gfx, w / 2, screenHeight - 48, TextLine("LEVEL REACHED: $level", Colors.WHITE))
        val showPrompt = stateSeconds > GameConfig.END_SCREEN_TAP_DELAY && (seconds * 2f).toInt() % 2 == 0
        TextBox.draw(
            gfx, w / 2, screenHeight - 26,
            TextLine("TAP TO TRY AGAIN", if (showPrompt) Colors.GOLD else Colors.BLACK),
        )
    }

    /**
     * A comic speech bubble: white with a dark outline, cut corners, and a tail that
     * runs down to the speaker at (tipX, tipY).
     */
    private fun drawBubble(gfx: Gfx, x: Int, y: Int, w: Int, h: Int, tipX: Int, tipY: Int, lines: List<String>) {
        // Outline, then paper, both with cut corners.
        gfx.fillRect(x + 2, y, w - 4, h, INK)
        gfx.fillRect(x, y + 2, w, h - 4, INK)
        gfx.fillRect(x + 4, y + 2, w - 8, h - 4, PAPER)
        gfx.fillRect(x + 2, y + 4, w - 4, h - 8, PAPER)

        // The tail: a stepped wedge that narrows towards the speaker.
        val tailX = x + (w * 0.28f).roundToInt()
        val tailTop = y + h - 3
        val steps = maxOf(1, ((tipY - tailTop) / 2f).roundToInt())
        for (i in 0..steps) {
            val yy = tailTop + i * 2
            val t = i.toFloat() / steps
            val cx = tailX + (tipX - tailX) * t
            val half = (9 * (1 - t)).roundToInt() + 1
            gfx.fillRect((cx - half - 2).roundToInt(), yy, 2 * half + 4, 2, INK)
            if (half > 1) gfx.fillRect((cx - half).roundToInt(), yy, 2 * half, 2, PAPER)
        }
        // Open the bubble's outline where the tail joins it.
        gfx.fillRect(tailX - 8, y + h - 4, 19, 4, PAPER)

        var textY = y + 7
        for (line in lines) {
            PixelFont.draw(gfx, line, (x + w / 2f - PixelFont.width(line, 2) / 2f).roundToInt(), textY, INK, 2)
            textY += 14
        }
    }
}
