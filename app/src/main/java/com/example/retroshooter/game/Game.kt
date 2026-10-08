package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.audio.SoundPlayer
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.config.LevelSettings
import com.example.retroshooter.graphics.Background
import com.example.retroshooter.graphics.Colors
import com.example.retroshooter.graphics.Gfx
import com.example.retroshooter.graphics.PixelFont
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
            GameState.GAME_OVER, GameState.WON ->
                if (stateSeconds >= GameConfig.END_SCREEN_TAP_DELAY) startNewGame()
        }
    }

    // ------------------------------------------------------------------
    // Drawing
    // ------------------------------------------------------------------

    fun draw(gfx: Gfx) {
        val w = GameConfig.SCREEN_WIDTH
        val h = world.screenHeight
        background.draw(gfx, timeOfDay, h, seconds)

        if (state != GameState.TITLE) world.draw(gfx)
        if (state != GameState.TITLE) Hud.draw(gfx, world, sound.musicEnabled)

        val mid = h / 3
        when (state) {
            GameState.TITLE -> drawTitle(gfx, w, h)
            GameState.PLAYING -> {
                if (world.introSeconds > 0f) {
                    shadowed(gfx, "LEVEL ${world.level}", w / 2, mid, Colors.WHITE, 3)
                    shadowed(gfx, if (world.level == 1) "GET READY" else "FASTER!", w / 2, mid + 22, Colors.GOLD, 1)
                }
            }
            GameState.LEVEL_CLEAR -> {
                shadowed(gfx, "LEVEL ${world.level}", w / 2, mid, Colors.WHITE, 2)
                shadowed(gfx, "CLEAR!", w / 2, mid + 16, Colors.GOLD, 3)
            }
            GameState.GAME_OVER -> {
                if (stateSeconds > 0.8f) {
                    shadowed(gfx, "GAME OVER", w / 2, mid, Colors.RED, 3)
                    shadowed(gfx, "LEVEL REACHED: ${world.level}", w / 2, mid + 26, Colors.WHITE, 1)
                    shadowed(gfx, "TAP TO TRY AGAIN", w / 2, mid + 40, Colors.GOLD, 1)
                }
            }
            GameState.WON -> {
                shadowed(gfx, "YOU WIN!", w / 2, mid, Colors.GOLD, 3)
                shadowed(gfx, "ALL ${GameConfig.LEVEL_COUNT} LEVELS CLEARED", w / 2, mid + 26, Colors.WHITE, 1)
                if (stateSeconds > GameConfig.END_SCREEN_TAP_DELAY) {
                    shadowed(gfx, "TAP TO PLAY AGAIN", w / 2, mid + 40, Colors.CYAN, 1)
                }
            }
        }

        if (paused) {
            shadowed(gfx, "PAUSED", w / 2, mid, Colors.WHITE, 3)
            shadowed(gfx, "TAP TO CONTINUE", w / 2, mid + 26, Colors.GOLD, 1)
        }
    }

    private fun drawTitle(gfx: Gfx, w: Int, h: Int) {
        shadowed(gfx, GameConfig.GAME_TITLE, w / 2, h / 4, Colors.GOLD, 4)
        shadowed(gfx, "TILT PHONE TO MOVE", w / 2, h / 2, Colors.WHITE, 1)
        shadowed(gfx, "TAP TO FIRE ROCKETS", w / 2, h / 2 + 10, Colors.WHITE, 1)
        shadowed(gfx, "SHOOT DOWN THE PLANE", w / 2, h / 2 + 20, Colors.WHITE, 1)
        shadowed(gfx, "SURVIVE ${GameConfig.LEVEL_COUNT} LEVELS", w / 2, h / 2 + 30, Colors.WHITE, 1)
        // Blinking prompt.
        if ((seconds * 2).toInt() % 2 == 0) {
            shadowed(gfx, "TAP TO START", w / 2, h / 2 + 52, Colors.CYAN, 2)
        }
    }

    /** Draws centered text with a dark drop shadow so it reads on any background. */
    private fun shadowed(gfx: Gfx, text: String, centerX: Int, y: Int, color: Int, scale: Int) {
        PixelFont.drawCentered(gfx, text, centerX + scale, y + scale, Colors.SHADOW, scale)
        PixelFont.drawCentered(gfx, text, centerX, y, color, scale)
    }
}
