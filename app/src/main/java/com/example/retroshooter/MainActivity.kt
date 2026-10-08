package com.example.retroshooter

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import com.example.retroshooter.audio.AudioEngine
import com.example.retroshooter.engine.GameView
import com.example.retroshooter.game.Game
import com.example.retroshooter.input.TiltSensor

/**
 * The only screen of the app. It creates the pieces and connects them:
 *
 *   TiltSensor --tilt--> Game <--taps-- GameView (draws the game)
 *                          |
 *                          +--sounds--> AudioEngine
 *
 * It also starts and stops things when the app goes to the background.
 */
class MainActivity : Activity() {

    private lateinit var audio: AudioEngine
    private lateinit var game: Game
    private lateinit var tiltSensor: TiltSensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) // do not dim while playing

        audio = AudioEngine(this)
        game = Game(audio)
        tiltSensor = TiltSensor(this) { game.tilt = it }

        setContentView(GameView(this, game))
        hideSystemBars()
    }

    override fun onResume() {
        super.onResume()
        hideSystemBars()
        tiltSensor.start()
        audio.onAppResume()
    }

    override fun onPause() {
        super.onPause()
        tiltSensor.stop()
        game.pause()
        audio.onAppPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        audio.release()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemBars()
    }

    /** Full-screen mode: hide the status bar and navigation bar (swipe from the edge to bring them back). */
    @Suppress("DEPRECATION")
    private fun hideSystemBars() {
        if (Build.VERSION.SDK_INT >= 30) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.systemBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }
}
