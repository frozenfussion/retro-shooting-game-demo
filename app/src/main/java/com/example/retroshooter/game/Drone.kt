package com.example.retroshooter.game

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.DRONE
import com.example.retroshooter.graphics.Gfx

/**
 * The supply drone. It flies right to left across the screen and drops one
 * crate at a random spot along the way. It is friendly: rockets pass through it.
 */
class Drone(private val dropX: Float, private val crateType: CrateType) :
    Entity(GameConfig.SCREEN_WIDTH + 10f, GameConfig.DRONE_ALTITUDE.toFloat(), 20, 7) {

    private var age = 0f
    private var hasDropped = false

    override fun update(dt: Float, world: World) {
        age += dt
        x -= GameConfig.DRONE_SPEED * dt
        // Slight bobbing up and down.
        y = GameConfig.DRONE_ALTITUDE + kotlin.math.sin(age * 2f) * 3f

        if (!hasDropped && x + width / 2f <= dropX) {
            hasDropped = true
            world.crates.add(Crate(x + width / 2f - 5.5f, y + height, crateType))
        }
        if (x + width < -4) alive = false
    }

    override fun draw(gfx: Gfx) {
        // The two DRONE frames are the spinning rotors.
        val frame = if ((age * 12).toInt() % 2 == 0) DRONE[0] else DRONE[1]
        gfx.drawSprite(frame, x.toInt(), y.toInt())
    }
}
