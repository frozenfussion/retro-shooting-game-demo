package com.example.retroshooter.graphics

import com.example.retroshooter.config.GameConfig
import java.util.Random
import kotlin.math.sqrt

/**
 * Draws the sky, sun or moon, stars, clouds and ground.
 *
 * The look depends on the [TimeOfDay]: dawn, day, evening or night. It is all
 * drawn with plain rectangles. To add a new look (rain, say), add a new entry
 * to [looks] and to the [TimeOfDay] enum.
 */
class Background {

    /** Everything that makes one time of day look different. */
    private class Look(
        val sky: IntArray,          // 8 bands of sky, top to bottom
        val water: IntArray,        // 7 bands below the horizon (a reflection)
        val orbColor: Int,
        val orbX: Int,
        val orbY: Int,
        val orbIsMoon: Boolean,
        val orbOnHorizon: Boolean,  // true = only the top half shows (sunrise / sunset)
        val starFraction: Float,    // 0 = no stars, 1 = all stars
        val cloudColor: Int,
        val groundColor: Int,
        val grassColor: Int,
        val grassShade: Int,
    )

    private fun c(rgb: Int): Int = rgb or (0xFF shl 24)
    private fun colors(vararg rgb: Int) = IntArray(rgb.size) { c(rgb[it]) }

    private val looks: Map<TimeOfDay, Look> = mapOf(
        TimeOfDay.DAWN to Look(
            sky = colors(0x2b2a6b, 0x4a3a85, 0x74498f, 0xa8568f, 0xd96e8f, 0xf58f8f, 0xffb18a, 0xffd39a),
            water = colors(0xffd39a, 0xf58f8f, 0xd96e8f, 0xa8568f, 0x74498f, 0x4a3a85, 0x2b2a6b),
            orbColor = c(0xfff0c4), orbX = 46, orbY = 140, orbIsMoon = false, orbOnHorizon = true,
            starFraction = 0.25f, cloudColor = c(0xf6b0b8),
            groundColor = c(0x23183f), grassColor = c(0x46805a), grassShade = c(0x2f5c42),
        ),
        TimeOfDay.DAY to Look(
            sky = colors(0x3d9bf2, 0x4fa8f5, 0x62b4f7, 0x78c1f9, 0x8fcdfa, 0xa6d8fb, 0xbde3fc, 0xd4eefd),
            water = colors(0xd4eefd, 0xa6d8fb, 0x78c1f9, 0x4fa8f5, 0x3d8fe0, 0x2e78c8, 0x2563ae),
            orbColor = c(0xfff3a0), orbX = 140, orbY = 62, orbIsMoon = false, orbOnHorizon = false,
            starFraction = 0f, cloudColor = c(0xffffff),
            groundColor = c(0x2a4a2a), grassColor = c(0x6fcf4f), grassShade = c(0x3f9a35),
        ),
        TimeOfDay.EVENING to Look(
            sky = colors(0x1a1245, 0x26175c, 0x3a1a6e, 0x5a1f7e, 0x8a2a86, 0xc2408a, 0xff6f6f, 0xffa15c),
            water = colors(0xffa15c, 0xff6f6f, 0xc2408a, 0x8a2a86, 0x5a1f7e, 0x3a1a6e, 0x26175c),
            orbColor = c(0xfff6d6), orbX = 90, orbY = 140, orbIsMoon = false, orbOnHorizon = true,
            starFraction = 0.4f, cloudColor = c(0xc2408a),
            groundColor = c(0x0d0a24), grassColor = c(0x2c6f3c), grassShade = c(0x1b4a28),
        ),
        TimeOfDay.NIGHT to Look(
            sky = colors(0x05051a, 0x080826, 0x0b0b33, 0x0e0e3d, 0x121248, 0x161652, 0x1a1a5c, 0x1e1e66),
            water = colors(0x1e1e66, 0x1a1a5c, 0x161652, 0x121248, 0x0e0e3d, 0x0b0b33, 0x080826),
            orbColor = c(0xe8ecff), orbX = 140, orbY = 56, orbIsMoon = true, orbOnHorizon = false,
            starFraction = 1f, cloudColor = c(0x1c1c55),
            groundColor = c(0x04040f), grassColor = c(0x1c4a2a), grassShade = c(0x12321c),
        ),
    )

    /** Fixed star positions (same every run so the sky does not shuffle). */
    private val stars: List<IntArray> = Random(7).let { rnd ->
        List(34) { intArrayOf(rnd.nextInt(GameConfig.SCREEN_WIDTH), rnd.nextInt(120)) }
    }

    /** Cloud: x start, y, size. */
    private val clouds = listOf(
        intArrayOf(20, 30, 1), intArrayOf(110, 70, 2), intArrayOf(60, 115, 1), intArrayOf(150, 20, 2),
    )

    /**
     * Draws the whole background.
     * @param screenHeight height of the game screen in game pixels
     * @param seconds time since the game started, used to animate stars and clouds
     */
    fun draw(gfx: Gfx, timeOfDay: TimeOfDay, screenHeight: Int, seconds: Float) {
        val look = looks.getValue(timeOfDay)
        val w = GameConfig.SCREEN_WIDTH
        val groundTop = screenHeight - GameConfig.GROUND_HEIGHT

        // The horizon sits at 50% of the way down the playfield.
        val horizon = groundTop / 2

        // Sky: 8 bands above the horizon.
        val bandH = horizon / look.sky.size + 1
        for (i in look.sky.indices) {
            gfx.fillRect(0, i * (horizon / look.sky.size), w, bandH, look.sky[i])
        }
        // Reflection: 7 bands from the horizon down to the ground.
        val waterH = (groundTop - horizon) / look.water.size + 1
        for (i in look.water.indices) {
            gfx.fillRect(0, horizon + i * ((groundTop - horizon) / look.water.size), w, waterH, look.water[i])
        }

        // Twinkling stars.
        val starCount = (stars.size * look.starFraction).toInt()
        for (i in 0 until starCount) {
            if ((seconds * 2f).toInt().plus(i) % 7 != 0) {
                gfx.fillRect(stars[i][0], stars[i][1] % (horizon - 8), 1, 1, c(0xFFFFFF))
            }
        }

        // Sun or moon.
        val orbY = if (look.orbOnHorizon) horizon else look.orbY
        when {
            look.orbIsMoon -> {
                disc(gfx, look.orbX, orbY, 11, look.orbColor, 11)
                disc(gfx, look.orbX + 5, orbY - 3, 10, look.sky[2], 10) // bite out of the moon = crescent
            }
            look.orbOnHorizon -> disc(gfx, look.orbX, orbY, 20, look.orbColor, 0) // top half only
            else -> disc(gfx, look.orbX, orbY, 10, look.orbColor, 10)
        }

        // Clouds drifting right.
        for (cl in clouds) {
            val size = cl[2]
            val cx = ((cl[0] + seconds * (3 + size * 2)) % (w + 60)).toInt() - 30
            val cy = cl[1]
            gfx.fillRect(cx, cy, 22 * size, 4, look.cloudColor)
            gfx.fillRect(cx + 4 * size, cy - 4, 12 * size, 4, look.cloudColor)
            gfx.fillRect(cx + 2 * size, cy + 4, 16 * size, 2, look.cloudColor)
        }

        // Ground.
        gfx.fillRect(0, groundTop, w, GameConfig.GROUND_HEIGHT, look.groundColor)
        gfx.fillRect(0, groundTop, w, 3, look.grassColor)
        var x = 0
        while (x < w) {
            gfx.fillRect(x, groundTop + 3, 4, 2, look.grassShade)
            x += 8
        }
    }

    /**
     * Draws a round disc out of horizontal lines.
     * @param lastRow how many rows below the center to draw (0 = top half only)
     */
    private fun disc(gfx: Gfx, cx: Int, cy: Int, radius: Int, color: Int, lastRow: Int) {
        for (dy in -radius..lastRow) {
            val half = (sqrt((radius * radius - dy * dy).toFloat()) + 0.5f).toInt()
            gfx.fillRect(cx - half, cy + dy, half * 2 + 1, 1, color)
        }
    }
}
