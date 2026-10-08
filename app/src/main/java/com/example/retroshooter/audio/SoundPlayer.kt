package com.example.retroshooter.audio

/**
 * What the game needs from the sound system. The game never touches Android
 * audio directly; it only calls these functions.
 *
 * [AudioEngine] is the real implementation. The unit tests use a silent one.
 */
interface SoundPlayer {
    /** Plays a short sound effect. */
    fun play(sfx: Sfx)

    /** Starts (or restarts) the background music, faster for higher levels. */
    fun startMusic(bpm: Int)

    /** Stops the background music. */
    fun stopMusic()

    /** The music on/off button. Remembered between runs. */
    var musicEnabled: Boolean
}

/** A sound player that does nothing. Used by tests and as a safe fallback. */
class SilentSoundPlayer : SoundPlayer {
    override fun play(sfx: Sfx) {}
    override fun startMusic(bpm: Int) {}
    override fun stopMusic() {}
    override var musicEnabled: Boolean = true
}
