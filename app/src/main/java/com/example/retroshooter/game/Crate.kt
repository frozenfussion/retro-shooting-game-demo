package com.example.retroshooter.game

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.PARACHUTE
import kotlin.math.sin

/**
 * A supply crate floating down on a parachute. Catch it with the soldier to
 * get its [type]. If it lands and nobody takes it, it disappears after a while.
 */
class Crate(
    x: Float,
    y: Float,
    val type: CrateType,
    /** How strongly the crate swings left and right while falling (grows with the level). */
    private val sway: Float = GameConfig.CRATE_SWAY_BASE,
) : Entity(x, y, 11, 11) {

    private var age = 0f
    private var landedFor = 0f
    private var landed = false

    override fun update(dt: Float, world: World) {
        age += dt
        if (!landed) {
            y += GameConfig.CRATE_FALL_SPEED * dt
            x += sin(age * 2.5f) * sway * dt // swings left and right
            x = x.coerceIn(0f, (GameConfig.SCREEN_WIDTH - width).toFloat())
            if (y + height >= world.groundTop) {
                y = (world.groundTop - height).toFloat()
                landed = true
            }
        } else {
            landedFor += dt
            if (landedFor >= GameConfig.CRATE_GROUND_SECONDS) alive = false
        }

        // Caught by the soldier? The soldier gets a slightly bigger "catch zone".
        if (world.player.overlaps(this, grow = 2)) {
            type.apply(world.player)
            world.sound.play(type.sound)
            alive = false
        }
    }

    override fun draw(gfx: Gfx) {
        // Blink during the last second on the ground, as a warning.
        if (landed && landedFor > GameConfig.CRATE_GROUND_SECONDS - 1f && (landedFor * 8).toInt() % 2 == 0) return
        if (!landed) gfx.drawSprite(PARACHUTE, x.toInt() - 1, y.toInt() - PARACHUTE.height)
        gfx.drawSprite(type.sprite, x.toInt(), y.toInt())
    }
}
