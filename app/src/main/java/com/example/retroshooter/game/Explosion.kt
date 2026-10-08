package com.example.retroshooter.game

import com.example.retroshooter.graphics.EXPLOSION
import com.example.retroshooter.graphics.Gfx

/**
 * A short fireball animation (flash, blast, smoke). It does no damage; it is only for show.
 * [delay] waits before it appears, so several explosions can pop one after another.
 */
class Explosion(centerX: Float, centerY: Float, private var delay: Float = 0f) :
    Entity(centerX - 6f, centerY - 6f, 12, 12) {

    private var age = 0f

    override fun update(dt: Float, world: World) {
        if (delay > 0f) {
            delay -= dt
            return
        }
        age += dt
        if (age >= FRAME_SECONDS * EXPLOSION.size) alive = false
    }

    override fun draw(gfx: Gfx) {
        if (delay > 0f || !alive) return
        val frame = (age / FRAME_SECONDS).toInt().coerceIn(0, EXPLOSION.size - 1)
        gfx.drawSprite(EXPLOSION[frame], x.toInt(), y.toInt())
    }

    private companion object {
        const val FRAME_SECONDS = 0.18f
    }
}
