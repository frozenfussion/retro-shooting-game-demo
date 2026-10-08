package com.example.retroshooter.game

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.ROCKET

/** A rocket fired by the soldier. It flies straight up until it hits the plane or leaves the screen. */
class Rocket(x: Float, y: Float) : Entity(x, y, 3, 8) {
    private var age = 0f

    override fun update(dt: Float, world: World) {
        age += dt
        y -= GameConfig.ROCKET_SPEED * dt
        if (y + height < 0) alive = false
    }

    override fun draw(gfx: Gfx) {
        // The two ROCKET frames are the flickering flame.
        val frame = if ((age * 20).toInt() % 2 == 0) ROCKET[0] else ROCKET[1]
        gfx.drawSprite(frame, x.toInt(), y.toInt())
    }
}
