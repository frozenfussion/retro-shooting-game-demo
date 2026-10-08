package com.example.retroshooter.engine

import android.view.SurfaceHolder
import com.example.retroshooter.game.Game
import kotlin.math.min

/**
 * The game loop: the heartbeat of the game.
 *
 * Forever, until stopped, it repeats:
 *   1. hand any screen taps to the game
 *   2. update the game in fixed 1/60 second steps (so the game runs at the same
 *      speed on every phone, however fast it is)
 *   3. draw one picture on the screen
 *   4. sleep a little so we do not burn the battery
 */
class GameThread(
    private val holder: SurfaceHolder,
    private val game: Game,
    private val view: GameView,
) : Thread("GameThread") {

    @Volatile private var running = true

    override fun run() {
        var last = System.nanoTime()
        var leftover = 0f

        while (running) {
            val frameStart = System.nanoTime()
            // Time since the last frame. Capped, so after a stall the game does not "jump".
            leftover += min(0.1f, (frameStart - last) / 1_000_000_000f)
            last = frameStart

            view.deliverTaps()
            while (leftover >= STEP) {
                game.update(STEP)
                leftover -= STEP
            }

            val canvas = try {
                holder.lockCanvas()
            } catch (e: IllegalStateException) {
                null
            }
            if (canvas != null) {
                try {
                    view.render(canvas)
                } finally {
                    holder.unlockCanvasAndPost(canvas)
                }
            }

            val elapsedMs = (System.nanoTime() - frameStart) / 1_000_000
            if (elapsedMs < FRAME_MS) {
                try {
                    sleep(FRAME_MS - elapsedMs)
                } catch (e: InterruptedException) {
                    break
                }
            }
        }
    }

    /** Stops the loop and waits for it to finish. */
    fun stopAndJoin() {
        running = false
        interrupt()
        try {
            join()
        } catch (e: InterruptedException) {
            // Giving up waiting is fine.
        }
    }

    private companion object {
        /** One game step: 1/60 of a second. */
        const val STEP = 1f / 60f
        const val FRAME_MS = 16L
    }
}
