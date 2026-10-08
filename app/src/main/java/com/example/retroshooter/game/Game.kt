package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.audio.SoundPlayer
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.config.LevelSettings
import com.example.retroshooter.graphics.Background
import com.example.retroshooter.graphics.Colors
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.TextBox
import com.example.retroshooter.graphics.TextLine
import com.example.retroshooter.graphics.TimeOfDay
import java.util.Calendar
import java.util.Random

/**
 * The whole game: it owns the [World] and decides which screen is showing
 * (title, playing, level clear, game over, won).
 *
 * The Android side (GameView) only has to call four things:
 *  - [update] 60 times a second
 *  - [draw] every frame
 *  - [onTap] when the screen is touched
 *  - set [tilt] from the accelerometer
 */
class Game(
    private val sound: SoundPlayer,
    /** Returns the current hour (0-23). Tests replace this to pretend it is night. */
    private val hourOfDay: () -> Int = { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) },
    /** Source of randomness. Tests pass a seeded one so runs are repeatable. */
    random: Random = Random(),
) {
    val world = World(sound, random)
    private val background = Background()

    var state: GameState = GameState.TITLE
        private set

    /** Steering from -1 (left) to +1 (right). Set from the accelerometer. */
    @Volatile
    var tilt: Float = 0f

    /** True while the app is in the background. Game time stops. */
    @Volatile
    var paused: Boolean = false
        private set

    private var seconds = 0f
    private var stateSeconds = 0f
    private var timeOfDay = TimeOfDay.forHour(hourOfDay())
    private var clockCheckTimer = 0f

    /** Tells the game how tall the phone's screen is, in game pixels. */
    fun resize(screenHeight: Int) = world.setScreenHeight(screenHeight)

    /** Call when the app goes to the background. */
    fun pause() {
        if (state == GameState.PLAYING) paused = true
    }

    // ------------------------------------------------------------------
    // Update
    // ------------------------------------------------------------------

    fun update(dt: Float) {
        seconds += dt
        refreshClock(dt)
        if (paused) return
        stateSeconds += dt

        when (state) {
            GameState.TITLE -> {
                // Nothing moves on the title screen except the sky.
            }
            GameState.PLAYING -> {
                world.player.tilt = tilt
                world.update(dt)
                if (world.playerDead) {
                    changeState(GameState.GAME_OVER)
                    sound.stopMusic()
                    sound.play(Sfx.GAME_OVER)
                } else if (world.planeDestroyed) {
                    changeState(GameState.LEVEL_CLEAR)
                    sound.play(Sfx.LEVEL_CLEAR)
                }
            }
            GameState.LEVEL_CLEAR -> {
                world.player.tilt = tilt
                world.updateQuiet(dt)
                if (stateSeconds >= GameConfig.LEVEL_CLEAR_SECONDS) {
                    if (world.level >= GameConfig.LEVEL_COUNT) {
                        changeState(GameState.WON)
                        sound.stopMusic()
                        sound.play(Sfx.WIN)
                    } else {
                        world.startLevel(world.level + 1)
                        sound.startMusic(LevelSettings.forLevel(world.level).musicBpm)
                        changeState(GameState.PLAYING)
                    }
                }
            }
            GameState.GAME_OVER, GameState.WON -> world.updateQuiet(dt)
        }
    }

    /** Re-reads the phone clock once a second so the sky changes at the right hour. */
    private fun refreshClock(dt: Float) {
        clockCheckTimer -= dt
        if (clockCheckTimer <= 0f) {
            clockCheckTimer = 1f
            timeOfDay = TimeOfDay.forHour(hourOfDay())
        }
    }

    private fun changeState(newState: GameState) {
        state = newState
        stateSeconds = 0f
    }

    private fun startNewGame() {
        world.newGame()
        sound.startMusic(LevelSettings.forLevel(1).musicBpm)
        sound.play(Sfx.START)
        changeState(GameState.PLAYING)
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------

    /** A tap at game-pixel coordinates (x, y). */
    fun onTap(x: Float, y: Float) {
        // The music button works on every screen.
        if (x >= Hud.MUSIC_BUTTON_X && y <= Hud.MUSIC_BUTTON_HEIGHT) {
            sound.musicEnabled = !sound.musicEnabled
            sound.play(Sfx.CLICK)
            return
        }
        if (paused) {
            paused = false
            return
        }
        when (state) {
            GameState.TITLE -> startNewGame()
            GameState.PLAYING -> if (world.introSeconds <= 0f) world.fireRocket()
            GameState.LEVEL_CLEAR -> Unit // nothing to shoot between levels
            GameState.GAME_OVER ->
                if (stateSeconds >= GameConfig.GAME_OVER_SPLASH_DELAY + GameConfig.END_SCREEN_TAP_DELAY) startNewGame()
            GameState.WON ->
                if (stateSeconds >= GameConfig.END_SCREEN_TAP_DELAY) startNewGame()
        }
    }

    // ------------------------------------------------------------------
    // Drawing
    // ------------------------------------------------------------------

    fun draw(gfx: Gfx) {
        val w = GameConfig.SCREEN_WIDTH
        val h = world.screenHeight

        // The two end-of-game splash screens draw everything themselves.
        if (state == GameState.WON) {
            background.draw(gfx, timeOfDay, h, seconds)
            VictoryScreen.draw(gfx, seconds, stateSeconds, h)
            Hud.drawMusicButton(gfx, sound.musicEnabled)
            return
        }
        if (state == GameState.GAME_OVER && stateSeconds >= GameConfig.GAME_OVER_SPLASH_DELAY) {
            GameOverScreen.draw(gfx, background, timeOfDay, seconds, stateSeconds - GameConfig.GAME_OVER_SPLASH_DELAY, h, world.level)
            Hud.drawMusicButton(gfx, sound.musicEnabled)
            return
        }

        background.draw(gfx, timeOfDay, h, seconds)
        if (state != GameState.TITLE) {
            world.draw(gfx)
            Hud.draw(gfx, world, sound.musicEnabled)
        }

        val mid = h / 3
        when (state) {
            GameState.TITLE -> drawTitle(gfx, w, h)
            GameState.PLAYING -> {
                if (world.introSeconds > 0f) {
                    textBox(
                        gfx, w / 2, mid,
                        TextLine("LEVEL ${world.level}", Colors.WHITE, 3),
                        TextLine(if (world.level == 1) "GET READY" else "FASTER!", Colors.GOLD),
                    )
                }
            }
            GameState.LEVEL_CLEAR -> textBox(
                gfx, w / 2, mid,
                TextLine("LEVEL ${world.level}", Colors.WHITE, 2),
                TextLine("CLEAR!", Colors.GOLD, 3),
            )
            // GAME_OVER (the first moments, while the explosion plays) shows only the world; WON never gets here.
            GameState.GAME_OVER, GameState.WON -> Unit
        }

        if (paused) {
            textBox(
                gfx, w / 2, mid,
                TextLine("PAUSED", Colors.WHITE, 3),
                TextLine("TAP TO CONTINUE", Colors.GOLD),
            )
        }
    }

    private fun drawTitle(gfx: Gfx, w: Int, h: Int) {
        textBox(gfx, w / 2, h / 5, TextLine(GameConfig.GAME_TITLE, Colors.GOLD, 4))
        textBox(
            gfx, w / 2, h / 2 - 20,
            TextLine("TILT PHONE TO MOVE", Colors.WHITE),
            TextLine("TAP TO FIRE ROCKETS", Colors.WHITE),
            TextLine("SHOOT DOWN THE PLANE", Colors.WHITE),
            TextLine("SURVIVE ${GameConfig.LEVEL_COUNT} LEVELS", Colors.WHITE),
        )
        // The prompt blinks: the box stays, the text switches on and off.
        val promptColor = if ((seconds * 2).toInt() % 2 == 0) Colors.GOLD else Colors.BLACK
        textBox(gfx, w / 2, h / 2 + 52, TextLine("TAP TO START", promptColor, 2))
    }

    /** Draws a black text box (see [TextBox]). */
    private fun textBox(gfx: Gfx, centerX: Int, top: Int, vararg lines: TextLine) {
        TextBox.draw(gfx, centerX, top, *lines)
    }
}
