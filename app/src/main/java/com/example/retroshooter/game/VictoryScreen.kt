package com.example.retroshooter.game

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.Colors
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.MISSILE_BIG
import com.example.retroshooter.graphics.PALETTE
import com.example.retroshooter.graphics.TextBox
import com.example.retroshooter.graphics.TextLine
import com.example.retroshooter.graphics.WIN_SOLDIER
import com.example.retroshooter.graphics.WIN_SOLDIER_GRIP
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * The "YOU WIN!" splash screen: a close-up of the soldier in a skateboard crouch, grinning,
 * a soda in one hand and a selfie stick in the other, with missiles launching behind him.
 *
 * The stick points at the viewer (we are looking through the selfie camera), so it runs
 * from his fist down to the bottom of the screen and gets thicker as it comes closer.
 *
 * Everything here is drawn from the sprites in Sprites.kt, so editing the sprites in the
 * Sprite Lab changes this screen too. The sky behind it is drawn by the caller.
 */
object VictoryScreen {
    /** The close-up characters are drawn at double size. */
    private const val SCALE = 2

    /** One missile: where it launches, how fast it climbs, where it bursts, and when it repeats. */
    private class Launch(val x: Int, val speed: Float, val apex: Int, val offset: Float, val period: Float)

    private val launches = listOf(
        Launch(22, 105f, 92, 0.0f, 3.6f),
        Launch(150, 120f, 70, 0.9f, 3.4f),
        Launch(58, 95f, 126, 1.7f, 3.9f),
        Launch(118, 110f, 104, 2.4f, 3.7f),
        Launch(88, 130f, 58, 3.0f, 3.5f),
    )

    private val sparkColors = intArrayOf(
        0xFFFFD93B.toInt(), 0xFFFF9A1F.toInt(), 0xFFE63946.toInt(), 0xFFF6F1E6.toInt(), 0xFF8FC3FF.toInt(),
    )

    /**
     * @param seconds time since the game started (drives the animation)
     * @param stateSeconds time since the win screen appeared
     * @param screenHeight height of the game screen in game pixels
     */
    fun draw(gfx: Gfx, seconds: Float, stateSeconds: Float, screenHeight: Int) {
        val w = GameConfig.SCREEN_WIDTH
        val ground = screenHeight - GameConfig.GROUND_HEIGHT

        drawLaunches(gfx, seconds, ground)

        // The soldier bobs up and down and winks every few seconds.
        val frame = if ((seconds * 1.6f).toInt() % 3 == 2) 1 else 0
        val bob = sin(seconds * 4f).roundToInt()
        val soldier = WIN_SOLDIER[frame]
        val x = w / 2 - soldier.width * SCALE / 2
        val y = ground - soldier.height * SCALE + bob
        gfx.drawSprite(soldier, x, y, SCALE)
        drawSelfieStick(gfx, x + WIN_SOLDIER_GRIP.first * SCALE, y + WIN_SOLDIER_GRIP.second * SCALE, screenHeight)

        // Text: title and subtitle at the top, the prompt at the bottom.
        val titleHeight = TextBox.draw(gfx, w / 2, 10, TextLine("YOU WIN!", Colors.GOLD, 3))
        TextBox.draw(gfx, w / 2, 10 + titleHeight + 4, TextLine("ALL ${GameConfig.LEVEL_COUNT} LEVELS CLEARED", Colors.WHITE))
        val blinkOn = (seconds * 2f).toInt() % 2 == 0
        val showPrompt = stateSeconds > GameConfig.END_SCREEN_TAP_DELAY && blinkOn
        TextBox.draw(
            gfx, w / 2, screenHeight - 28,
            TextLine("TAP TO PLAY AGAIN", if (showPrompt) Colors.GOLD else Colors.BLACK),
        )
    }

    /** Missiles climb with a smoke trail, then burst into a ring of sparks. */
    private fun drawLaunches(gfx: Gfx, seconds: Float, ground: Int) {
        val missileHeight = MISSILE_BIG[0].height * SCALE
        val missileWidth = MISSILE_BIG[0].width * SCALE

        for ((i, launch) in launches.withIndex()) {
            val local = (seconds + launch.offset) % launch.period
            val base = ground - missileHeight + 8
            val rise = (base - launch.apex) / launch.speed

            if (local < rise) {
                // Smoke puffs behind the missile: they grow, drift and fade.
                for (k in 1 until 16) {
                    val age = k * 0.06f
                    if (local - age < 0f) break
                    val puffY = base - launch.speed * (local - age)
                    val size = 3 + (k / 3) * 2
                    val alpha = (1f - k / 16f) * 0.9f
                    val rgb = if (k < 4) 0xFFD9A0 else 0xDCDCE8
                    val px = launch.x + missileWidth / 2f - size / 2f + ((k * 7 + i * 3) % 5 - 2) * 1.2f
                    gfx.fillRect(px.roundToInt(), (puffY + missileHeight - 2).roundToInt(), size, size, withAlpha(rgb, alpha))
                }
                // Flash on the ground at lift-off.
                if (local < 0.3f) {
                    val alpha = 1f - local / 0.3f
                    gfx.fillRect(launch.x - 2, ground - 5, missileWidth + 4, 5, withAlpha(0xFFD93B, alpha))
                    gfx.fillRect(launch.x + 2, ground - 9, missileWidth - 4, 4, withAlpha(0xFF9A1F, alpha))
                }
                val flame = if ((seconds * 14f).toInt() % 2 == 0) MISSILE_BIG[0] else MISSILE_BIG[1]
                gfx.drawSprite(flame, launch.x, (base - launch.speed * local).roundToInt(), SCALE)
            } else if (local < rise + 1f) {
                // Burst: 18 sparks fly outwards and fade.
                val q = local - rise
                val cx = launch.x + missileWidth / 2f
                val cy = launch.apex.toFloat()
                for (s in 0 until 18) {
                    val angle = s / 18f * 2f * Math.PI.toFloat()
                    val radius = 4f + q * 34f
                    val px = cx + kotlin.math.cos(angle) * radius
                    val py = cy + sin(angle) * radius * 0.9f + q * q * 16f
                    val color = sparkColors[(s + i) % sparkColors.size]
                    gfx.fillRect(px.roundToInt(), py.roundToInt(), 3, 3, withAlpha(color and 0xFFFFFF, 1f - q))
                }
            }
        }
    }

    /**
     * The selfie stick, from the soldier's fist (gripX, gripY) down to the bottom of the screen.
     * It gets wider the lower it goes, because the end of it is closer to the camera.
     */
    private fun drawSelfieStick(gfx: Gfx, gripX: Int, gripY: Int, screenHeight: Int) {
        val outline = PALETTE.getValue('K')
        val body = PALETTE.getValue('m')
        val shine = PALETTE.getValue('M')
        val span = max(1, screenHeight - gripY)
        var y = gripY
        while (y < screenHeight) {
            val t = (y - gripY).toFloat() / span
            val centerX = gripX + 0.34f * (y - gripY)
            val width = ((3 * SCALE + t * t * 16 * SCALE) / SCALE).roundToInt() * SCALE
            val left = (((centerX - width / 2f) / SCALE).roundToInt()) * SCALE
            gfx.fillRect(left, y, width, SCALE, outline)
            gfx.fillRect(left + SCALE, y, width - 2 * SCALE, SCALE, body)
            gfx.fillRect(left + SCALE, y, max(SCALE, ((width * 0.22f) / SCALE).roundToInt() * SCALE), SCALE, shine)
            y += SCALE
        }
    }

    /** Builds an 0xAARRGGBB color from 0xRRGGBB and an opacity from 0 to 1. */
    private fun withAlpha(rgb: Int, alpha: Float): Int =
        ((alpha.coerceIn(0f, 1f) * 255).toInt() shl 24) or (rgb and 0xFFFFFF)
}
