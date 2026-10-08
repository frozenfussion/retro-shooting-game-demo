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
            GameState.GAME_OVER -> {
                if (stateSeconds > 0.8f) {
                    textBox(
                        gfx, w / 2, mid,
                        TextLine("GAME OVER", Colors.RED, 3),
                        TextLine("LEVEL REACHED: ${world.level}", Colors.WHITE),
                        TextLine("TAP TO TRY AGAIN", Colors.GOLD),
                    )
                }
            }
            GameState.WON -> textBox(
                gfx, w / 2, mid,
                TextLine("YOU WIN!", Colors.GOLD, 3),
                TextLine("ALL ${GameConfig.LEVEL_COUNT} LEVELS CLEARED", Colors.WHITE),
                if (stateSeconds > GameConfig.END_SCREEN_TAP_DELAY) TextLine("TAP TO PLAY AGAIN", Colors.GOLD) else TextLine(" ", Colors.WHITE),
            )
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

    /** One line of text inside a [textBox]. */
    private class TextLine(val text: String, val color: Int, val scale: Int = 1)

    /**
     * Draws lines of centered text inside a solid black box with a thin white border.
     * A plain dark box keeps the text readable on any background: bright day sky, sunset, or night.
     */
    private fun textBox(gfx: Gfx, centerX: Int, top: Int, vararg lines: TextLine) {
        val padding = 6
        val gap = 4
        val textWidth = lines.maxOf { PixelFont.width(it.text, it.scale) }
        val textHeight = lines.sumOf { PixelFont.GLYPH_HEIGHT * it.scale } + gap * (lines.size - 1)
        val boxWidth = textWidth + padding * 2
        val boxHeight = textHeight + padding * 2
        val left = centerX - boxWidth / 2

        gfx.fillRect(left - 1, top - 1, boxWidth + 2, boxHeight + 2, Colors.WHITE) // border
        gfx.fillRect(left, top, boxWidth, boxHeight, Colors.BLACK)                  // box

        var y = top + padding
        for (line in lines) {
            PixelFont.drawCentered(gfx, line.text, centerX, y, line.color, line.scale)
            y += PixelFont.GLYPH_HEIGHT * line.scale + gap
        }
    }
}
