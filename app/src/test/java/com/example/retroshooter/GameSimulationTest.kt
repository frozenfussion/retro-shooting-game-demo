package com.example.retroshooter

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.audio.SoundPlayer
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.game.CrateType
import com.example.retroshooter.game.Game
import com.example.retroshooter.game.GameState
import com.example.retroshooter.game.Crate
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.Sprite
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Random

/**
 * Plays the real game on the computer, with no phone. A "bot" steers and
 * fires, and we check that the rules work: levels advance, the game can be
 * lost, crates help, and drawing never crashes.
 */
class GameSimulationTest {

    private class RecordingSound : SoundPlayer {
        val played = mutableListOf<Sfx>()
        var musicStarts = 0
        override fun play(sfx: Sfx) { played.add(sfx) }
        override fun startMusic(bpm: Int) { musicStarts++ }
        override fun stopMusic() {}
        override var musicEnabled: Boolean = true
    }

    /** A Gfx that only counts what was drawn. */
    private class CountingGfx : Gfx {
        var sprites = 0
        var rects = 0
        override fun drawSprite(sprite: Sprite, x: Int, y: Int) { sprites++ }
        override fun fillRect(x: Int, y: Int, w: Int, h: Int, color: Int) { rects++ }
    }

    private val dt = 1f / 60f

    private fun newGame(sound: SoundPlayer, seed: Long = 1, hour: Int = 12): Game =
        Game(sound, hourOfDay = { hour }, random = Random(seed)).also { it.resize(400) }

    /** Taps the screen until the game leaves the title/end screen. */
    private fun start(game: Game) {
        game.onTap(90f, 200f)
        assertEquals(GameState.PLAYING, game.state)
    }

    /** Steers under the plane and fires, like a decent (not perfect) player. */
    private fun botStep(game: Game) {
        val plane = game.world.plane
        val player = game.world.player
        if (plane != null && plane.alive) {
            val target = plane.x + plane.width / 2f + 14f // lead the shot a little
            val here = player.x + player.width / 2f
            game.tilt = when {
                target - here > 4f -> 1f
                target - here < -4f -> -1f
                else -> 0f
            }
            game.onTap(90f, 200f) // fire
        } else {
            game.tilt = 0f
        }
        game.update(dt)
    }

    @Test
    fun startsOnTheTitleScreenAndTapStartsTheGame() {
        val game = newGame(RecordingSound())
        assertEquals(GameState.TITLE, game.state)
        start(game)
        assertEquals(1, game.world.level)
        assertEquals(GameConfig.LIVES_START, game.world.player.lives)
        assertEquals(GameConfig.AMMO_START, game.world.player.ammo)
    }

    @Test
    fun aBotCanBeatLevelOneAndMoveOnToLevelTwo() {
        val sound = RecordingSound()
        val game = newGame(sound)
        start(game)
        var steps = 0
        while (game.world.level == 1 && game.state != GameState.GAME_OVER && steps < 60 * 120) {
            botStep(game)
            steps++
        }
        assertEquals("the bot should reach level 2", 2, game.world.level)
        assertTrue(sound.played.contains(Sfx.LEVEL_CLEAR))
        assertTrue(sound.played.contains(Sfx.PLANE_HIT))
        // Next level hands out bonus rockets.
        assertTrue(game.world.player.ammo <= GameConfig.AMMO_MAX)
    }

    @Test
    fun standingStillAndNeverFiringLosesTheGame() {
        val sound = RecordingSound()
        val game = newGame(sound)
        start(game)
        var steps = 0
        while (game.state == GameState.PLAYING && steps < 60 * 600) {
            game.update(dt)
            steps++
        }
        assertEquals(GameState.GAME_OVER, game.state)
        assertTrue(sound.played.contains(Sfx.GAME_OVER))
        assertTrue(sound.played.contains(Sfx.PLAYER_HIT))
    }

    @Test
    fun gameOverScreenIgnoresTapsAtFirstThenRestarts() {
        val game = newGame(RecordingSound())
        start(game)
        while (game.state == GameState.PLAYING) game.update(dt)
        assertEquals(GameState.GAME_OVER, game.state)
        game.onTap(90f, 200f) // too early
        assertEquals(GameState.GAME_OVER, game.state)
        repeat(60) { game.update(dt) }
        game.onTap(90f, 200f)
        assertEquals(GameState.PLAYING, game.state)
        assertEquals(1, game.world.level)
        assertEquals(GameConfig.LIVES_START, game.world.player.lives)
    }

    @Test
    fun cratesGiveWhatTheyPromise() {
        val sound = RecordingSound()
        val game = newGame(sound)
        start(game)
        val world = game.world
        val player = world.player

        player.lives = 2
        world.crates.add(Crate(player.x, player.y, CrateType.LIFE))
        game.update(dt)
        assertEquals(3, player.lives)

        player.ammo = 1
        world.crates.add(Crate(player.x, player.y, CrateType.AMMO))
        game.update(dt)
        assertEquals(1 + GameConfig.AMMO_PER_CRATE, player.ammo)

        world.crates.add(Crate(player.x, player.y, CrateType.SHIELD))
        game.update(dt)
        assertTrue(player.shieldSeconds > 0f)
        assertTrue(sound.played.containsAll(listOf(Sfx.PICKUP_LIFE, Sfx.PICKUP_AMMO, Sfx.PICKUP_SHIELD)))
    }

    @Test
    fun livesAndAmmoNeverExceedTheirMaximums() {
        val game = newGame(RecordingSound())
        start(game)
        val player = game.world.player
        player.lives = GameConfig.LIVES_MAX
        player.ammo = GameConfig.AMMO_MAX
        game.world.crates.add(Crate(player.x, player.y, CrateType.LIFE))
        game.world.crates.add(Crate(player.x, player.y, CrateType.AMMO))
        game.update(dt)
        assertEquals(GameConfig.LIVES_MAX, player.lives)
        assertEquals(GameConfig.AMMO_MAX, player.ammo)
    }

    @Test
    fun outOfAmmoClicksInsteadOfFiring() {
        val sound = RecordingSound()
        val game = newGame(sound)
        start(game)
        repeat(100) { game.update(dt) } // let the "LEVEL 1" banner pass
        game.world.player.ammo = 0
        game.onTap(90f, 200f)
        assertTrue(sound.played.contains(Sfx.OUT_OF_AMMO))
        assertTrue(game.world.rockets.isEmpty())
    }

    @Test
    fun theMusicButtonTogglesMusic() {
        val sound = RecordingSound()
        val game = newGame(sound)
        assertTrue(sound.musicEnabled)
        game.onTap(175f, 5f)
        assertTrue(!sound.musicEnabled)
        game.onTap(175f, 5f)
        assertTrue(sound.musicEnabled)
    }

    @Test
    fun everyScreenCanBeDrawnAtEveryTimeOfDay() {
        for (hour in listOf(2, 6, 12, 18, 22)) {
            val game = newGame(RecordingSound(), hour = hour)
            val gfx = CountingGfx()
            game.draw(gfx) // title
            start(game)
            repeat(200) { botStep(game); game.draw(gfx) } // playing, with the intro banner
            assertTrue(gfx.sprites > 0 && gfx.rects > 0)
        }
    }

    @Test
    fun theWinScreenIsReachableAfterTheLastLevel() {
        val sound = RecordingSound()
        val game = newGame(sound)
        start(game)
        // Jump straight to the last level to keep the test fast.
        game.world.startLevel(GameConfig.LEVEL_COUNT)
        var steps = 0
        while (game.state != GameState.WON && game.state != GameState.GAME_OVER && steps < 60 * 300) {
            game.world.player.ammo = GameConfig.AMMO_MAX // plenty of rockets
            game.world.player.lives = GameConfig.LIVES_MAX // keep the bot alive
            botStep(game)
            steps++
        }
        assertEquals(GameState.WON, game.state)
        assertTrue(sound.played.contains(Sfx.WIN))
        game.draw(CountingGfx())
    }

    @Test
    fun theGamePausesWhenToldTo() {
        val game = newGame(RecordingSound())
        start(game)
        repeat(100) { game.update(dt) }
        val x = game.world.plane!!.x
        game.pause()
        repeat(100) { game.update(dt) }
        assertEquals(x, game.world.plane!!.x, 0f)
        game.onTap(90f, 200f) // tap to continue
        game.update(dt)
        assertTrue(game.world.plane!!.x != x)
    }
}
