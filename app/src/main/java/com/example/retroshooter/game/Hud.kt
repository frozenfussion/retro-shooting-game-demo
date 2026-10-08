package com.example.retroshooter.game

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.Colors
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.HEART
import com.example.retroshooter.graphics.ICON_MUSIC_OFF
import com.example.retroshooter.graphics.ICON_MUSIC_ON
import com.example.retroshooter.graphics.PixelFont
import com.example.retroshooter.graphics.ROCKET

/**
 * The heads-up display: lives, rockets, shield, level, the plane's health
 * bar and the music button.
 */
object Hud {
    /** The music button's touch area (a bit bigger than the icon so it is easy to hit). */
    const val MUSIC_BUTTON_X = GameConfig.SCREEN_WIDTH - 18
    const val MUSIC_BUTTON_HEIGHT = 18

    fun draw(gfx: Gfx, world: World, musicOn: Boolean) {
        val w = GameConfig.SCREEN_WIDTH
        val player = world.player

        // Row 1: lives (left), level (center), music button (right).
        for (i in 0 until player.lives) gfx.drawSprite(HEART, 4 + i * 9, 4)
        PixelFont.drawCentered(gfx, "LEVEL ${world.level}", w / 2, 5, Colors.WHITE)
        drawMusicButton(gfx, musicOn)

        // Row 2: rockets (left), the plane's health bar (right).
        gfx.drawSprite(ROCKET[0], 5, 14)
        PixelFont.draw(gfx, "X${player.ammo}", 11, 16, if (player.ammo == 0) Colors.RED else Colors.WHITE)

        val plane = world.plane
        if (plane != null) {
            val barX = 116
            val barW = 58
            PixelFont.draw(gfx, "ENEMY", barX - 23, 16, Colors.WHITE)
            gfx.fillRect(barX - 1, 15, barW + 2, 7, Colors.SHADOW)
            gfx.fillRect(barX, 16, barW, 5, Colors.BAR_BACK)
            val filled = barW * plane.hp.coerceAtLeast(0) / plane.maxHp
            gfx.fillRect(barX, 16, filled, 5, Colors.RED)
        }

        // Shield timer: a thin light-blue bar under the rocket count.
        if (player.shieldSeconds > 0f) {
            val len = (30 * player.shieldSeconds / GameConfig.SHIELD_SECONDS).toInt()
            gfx.fillRect(4, 26, 30, 3, Colors.SHADOW)
            gfx.fillRect(5, 27, len, 1, Colors.LIGHT_BLUE)
        }
    }

    /** The music on/off icon in the top-right corner. The splash screens draw it too. */
    fun drawMusicButton(gfx: Gfx, musicOn: Boolean) {
        gfx.drawSprite(if (musicOn) ICON_MUSIC_ON else ICON_MUSIC_OFF, GameConfig.SCREEN_WIDTH - 13, 3)
    }
}
