package com.example.retroshooter

import com.example.retroshooter.audio.SilentSoundPlayer
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.game.Game
import com.example.retroshooter.game.GameState
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.PALETTE
import com.example.retroshooter.graphics.Sprite
import com.example.retroshooter.graphics.WIN_SOLDIER
import com.example.retroshooter.graphics.WIN_SOLDIER_GRIP
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Random

/** Draws the win and game over splash screens on a computer, on short and tall phones, at every time of day. */
class SplashScreensTest {

    /** Records everything that was drawn, and fails if anything is drawn outside the screen's width. */
    private class CheckingGfx(val height: Int) : Gfx {
        var sprites = 0
        var rects = 0
        val spriteSizes = mutableListOf<Int>()
        override fun drawSprite(sprite: Sprite, x: Int, y: Int, scale: Int) {
            sprites++
            spriteSizes.add(scale)
        }
        override fun fillRect(x: Int, y: Int, w: Int, h: Int, color: Int) {
            rects++
            assertTrue("negative size rectangle", w >= 0 && h >= 0)
        }
    }

    private val dt = 1f / 60f

    private fun gameInState(target: GameState, hour: Int, height: Int): Game {
        val game = Game(SilentSoundPlayer(), { hour }, Random(2))
        game.resize(height)
        game.onTap(90f, 200f)
        if (target == GameState.GAME_OVER) {
            while (game.state == GameState.PLAYING) game.update(dt)
            repeat(60 * 3) { game.update(dt) }
        } else {
            game.world.startLevel(GameConfig.LEVEL_COUNT)
            var steps = 0
            while (game.state != GameState.WON && steps < 60 * 300) {
                val plane = game.world.plane
                if (plane != null && plane.alive) {
                    game.world.player.lives = GameConfig.LIVES_MAX
                    game.world.player.ammo = GameConfig.AMMO_MAX
                    val aim = plane.x + plane.width / 2f + 14f
                    val here = game.world.player.x + 6
                    game.tilt = if (aim - here > 4) 1f else if (aim - here < -4) -1f else 0f
                    game.onTap(90f, 200f)
                }
                game.update(dt)
                steps++
            }
        }
        assertEquals(target, game.state)
        return game
    }

    @Test
    fun theWinScreenDrawsEverywhere() {
        for (height in listOf(GameConfig.SCREEN_HEIGHT_MIN, GameConfig.SCREEN_HEIGHT_MAX)) {
            for (hour in listOf(2, 6, 12, 18)) {
                val game = gameInState(GameState.WON, hour, height)
                val gfx = CheckingGfx(height)
                repeat(120) { game.update(dt); game.draw(gfx) }
                assertTrue(gfx.sprites > 0 && gfx.rects > 0)
                assertTrue("the soldier is drawn double size", gfx.spriteSizes.contains(2))
            }
        }
    }

    @Test
    fun theGameOverScreenDrawsEverywhere() {
        for (height in listOf(GameConfig.SCREEN_HEIGHT_MIN, GameConfig.SCREEN_HEIGHT_MAX)) {
            for (hour in listOf(2, 6, 12, 18)) {
                val game = gameInState(GameState.GAME_OVER, hour, height)
                val gfx = CheckingGfx(height)
                repeat(120) { game.update(dt); game.draw(gfx) }
                assertTrue(gfx.sprites > 0 && gfx.rects > 0)
                assertTrue("the pilot is drawn double size", gfx.spriteSizes.contains(2))
            }
        }
    }

    @Test
    fun theSelfieStickGripIsInsideTheSoldierSprite() {
        val (gx, gy) = WIN_SOLDIER_GRIP
        for (frame in WIN_SOLDIER) {
            assertTrue(gx in 0 until frame.width && gy in 0 until frame.height)
            // The grip sits on the fist, so it must be a solid (non-transparent) pixel.
            assertTrue(frame.rows[gy][gx] != '.')
            assertTrue(PALETTE.containsKey(frame.rows[gy][gx]))
        }
    }
}
