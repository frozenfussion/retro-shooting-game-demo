package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.SHIELD_BUBBLE
import com.example.retroshooter.graphics.SKATER

/**
 * The soldier on the skateboard.
 *
 * The hitbox is narrower than the picture (the picture includes the rocket
 * launcher), so bombs that only brush the launcher miss. The sprite is drawn
 * [SPRITE_OFFSET_X] pixels to the left of the hitbox to line them up.
 */
class Player : Entity(0f, 0f, 12, 24) {

    /** Steering from -1 (full left) to +1 (full right). Set by the input code every frame. */
    var tilt = 0f

    var lives = GameConfig.LIVES_START
    var ammo = GameConfig.AMMO_START

    /** Seconds of shield left. 0 means no shield. */
    var shieldSeconds = 0f

    /** Seconds of "just got hit" protection left. The soldier blinks while this is above 0. */
    var invulnerableSeconds = 0f

    private var fireCooldown = 0f

    /** Puts the soldier back to the starting state for a new game. */
    fun reset() {
        x = (GameConfig.SCREEN_WIDTH - width) / 2f
        tilt = 0f
        lives = GameConfig.LIVES_START
        ammo = GameConfig.AMMO_START
        shieldSeconds = 0f
        invulnerableSeconds = 0f
        fireCooldown = 0f
        alive = true
    }

    override fun update(dt: Float, world: World) {
        if (!alive) return
        // Move, then keep the soldier on screen.
        x += tilt * GameConfig.PLAYER_MAX_SPEED * dt
        x = x.coerceIn(0f, (GameConfig.SCREEN_WIDTH - width).toFloat())

        if (shieldSeconds > 0f) shieldSeconds = maxOf(0f, shieldSeconds - dt)
        if (invulnerableSeconds > 0f) invulnerableSeconds = maxOf(0f, invulnerableSeconds - dt)
        if (fireCooldown > 0f) fireCooldown -= dt
    }

    /** Fires a rocket if the soldier has ammo and the launcher has cooled down. */
    fun tryFire(world: World) {
        if (!alive || fireCooldown > 0f) return
        if (ammo <= 0) {
            world.sound.play(Sfx.OUT_OF_AMMO)
            fireCooldown = GameConfig.FIRE_COOLDOWN
            return
        }
        ammo--
        fireCooldown = GameConfig.FIRE_COOLDOWN
        // The launcher tube is on the right side of the sprite.
        world.rockets.add(Rocket(x + 9f, y - 8f))
        world.sound.play(Sfx.ROCKET_LAUNCH)
    }

    /** Called when a bomb hits the soldier. A shield soaks up the hit instead of a life. */
    fun hurt(world: World) {
        if (!alive || invulnerableSeconds > 0f) return
        if (shieldSeconds > 0f) {
            shieldSeconds = 0f
            invulnerableSeconds = 0.5f
            world.sound.play(Sfx.SHIELD_BREAK)
            return
        }
        lives--
        invulnerableSeconds = GameConfig.INVULNERABLE_SECONDS
        world.sound.play(Sfx.PLAYER_HIT)
        world.sound.play(Sfx.PILOT_LAUGH)
        if (lives <= 0) {
            alive = false
            world.addExplosion(x + width / 2f, y + height / 2f)
        }
    }

    override fun draw(gfx: Gfx) {
        if (!alive) return
        val blinkOff = invulnerableSeconds > 0f && (invulnerableSeconds * 10).toInt() % 2 == 0
        if (!blinkOff) gfx.drawSprite(SKATER, x.toInt() - SPRITE_OFFSET_X, y.toInt())

        // Shield bubble; flickers during the last 2 seconds as a warning.
        val shieldVisible = shieldSeconds > 2f || (shieldSeconds > 0f && (shieldSeconds * 8).toInt() % 2 == 0)
        if (shieldVisible) {
            gfx.drawSprite(
                SHIELD_BUBBLE,
                (x + width / 2f).toInt() - SHIELD_BUBBLE.width / 2,
                (y + height / 2f).toInt() - SHIELD_BUBBLE.height / 2,
            )
        }
    }

    private companion object {
        const val SPRITE_OFFSET_X = 2
    }
}
