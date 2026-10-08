package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.config.LevelSettings
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.PILOT_BODY
import com.example.retroshooter.graphics.PILOT_HEAD
import com.example.retroshooter.graphics.PLANE
import com.example.retroshooter.graphics.PROP
import java.util.Random
import kotlin.math.abs
import kotlin.math.sin

/**
 * The enemy plane. It flies left to right across the top of the screen,
 * dropping bombs. When it leaves on the right it comes back on the left at a
 * new height. Its speed, HP and bomb rate come from [LevelSettings].
 *
 * The plane is drawn from several sprites layered together: the plane body,
 * the spinning propeller, the pilot's suit, and the pilot's head. The head is
 * separate so it can wobble while the laughing pilot stays seated.
 */
class EnemyPlane(
    private val settings: LevelSettings,
    private val random: Random,
) : Entity(-PLANE.width.toFloat() - 10f, 0f, PLANE.width, PLANE.height) {

    val maxHp: Int = settings.planeHp
    var hp: Int = settings.planeHp
        private set

    private var age = 0f
    private var hitFlash = 0f
    private var bombTimer = settings.bombIntervalSeconds * 0.5f

    /** True if the next bomb waits until the plane is right above the soldier. */
    private var nextBombIsAimed = false

    init {
        pickNewAltitude()
    }

    private fun pickNewAltitude() {
        y = (GameConfig.PLANE_ALTITUDE_MIN +
            random.nextInt(GameConfig.PLANE_ALTITUDE_MAX - GameConfig.PLANE_ALTITUDE_MIN + 1)).toFloat()
    }

    override fun update(dt: Float, world: World) {
        age += dt
        if (hitFlash > 0f) hitFlash -= dt

        x += settings.planeSpeed * dt
        if (x > GameConfig.SCREEN_WIDTH) {
            x = -width - 10f
            pickNewAltitude()
        }

        // When the timer runs out, drop a bomb (only while the plane is on screen).
        // An "aimed" bomb waits until the plane is over the soldier, but never longer than MAX_AIM_WAIT.
        bombTimer -= dt
        val onScreen = x > 0 && x + width < GameConfig.SCREEN_WIDTH
        if (bombTimer <= 0f && onScreen) {
            val planeCenter = x + width / 2f
            val playerCenter = world.player.x + world.player.width / 2f
            val overPlayer = abs(planeCenter - playerCenter) <= GameConfig.BOMB_AIM_TOLERANCE
            val waitedTooLong = bombTimer < -MAX_AIM_WAIT
            if (!nextBombIsAimed || overPlayer || waitedTooLong) {
                bombTimer = settings.bombIntervalSeconds * (0.8f + random.nextFloat() * 0.4f)
                nextBombIsAimed = random.nextFloat() < GameConfig.BOMB_AIM_CHANCE
                world.bombs.add(Bomb(x + 13f, y + 17f, settings.bombFallSpeed))
                world.sound.play(Sfx.BOMB_DROP)
            }
        }
    }

    /** Called when a rocket hits. Destroys the plane when HP runs out. */
    fun hit(world: World) {
        hp--
        hitFlash = 0.2f
        world.sound.play(Sfx.PLANE_HIT)
        if (hp <= 0) {
            alive = false
            world.onPlaneDestroyed(this)
        }
    }

    override fun draw(gfx: Gfx) {
        // Blink for a moment after being hit.
        if (hitFlash > 0f && (hitFlash * 25).toInt() % 2 == 0) return

        val px = x.toInt()
        val py = y.toInt()
        gfx.drawSprite(PLANE, px, py)
        gfx.drawSprite(if ((age * 14).toInt() % 2 == 0) PROP[0] else PROP[1], px + PLANE.width, py + 2)
        gfx.drawSprite(PILOT_BODY, px + 10, py + 2)

        // The head wobbles by 1 pixel and swaps between grin and laugh frames.
        val wobbleX = Math.round(sin(age * 5f))
        val wobbleY = Math.round(sin(age * 9f))
        val headFrame = if ((age * 4).toInt() % 2 == 0) PILOT_HEAD[0] else PILOT_HEAD[1]
        gfx.drawSprite(headFrame, px + 10 + wobbleX, py - 6 + wobbleY)
    }

    private companion object {
        /** Seconds an aimed bomb may wait before it drops anyway. */
        const val MAX_AIM_WAIT = 8f
    }
}
