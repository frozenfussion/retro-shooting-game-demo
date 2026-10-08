package com.example.retroshooter.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import com.example.retroshooter.config.GameConfig
import java.util.EnumMap

/**
 * The real sound system for the phone.
 *
 * At start-up it builds every sound effect from its recipe ([SoundEffects]) and
 * loads each one into an Android [AudioTrack]. Playing a sound is then just
 * "rewind and press play". The background music is one long looping AudioTrack
 * built by [Music].
 *
 * If anything goes wrong with the phone's audio, the game keeps running
 * silently instead of crashing.
 */
class AudioEngine(context: Context) : SoundPlayer {

    private val prefs = context.getSharedPreferences("retro_shooter", Context.MODE_PRIVATE)
    private val effects = EnumMap<Sfx, AudioTrack>(Sfx::class.java)
    private val lock = Any()

    private var music: AudioTrack? = null
    private var musicBpm = 0
    private var musicWanted = false
    private var appActive = true

    /** The music on/off button. The choice is saved on the phone. */
    override var musicEnabled: Boolean = prefs.getBoolean(KEY_MUSIC, GameConfig.MUSIC_DEFAULT_ON)
        set(value) {
            field = value
            prefs.edit().putBoolean(KEY_MUSIC, value).apply()
            synchronized(lock) { applyMusicState() }
        }

    init {
        // Building all the sounds takes a moment, so do it off the main thread.
        Thread({
            for (sfx in Sfx.entries) {
                try {
                    val pcm = SoundSynth.toPcm(SoundEffects.build(sfx), GameConfig.SFX_VOLUME)
                    val track = createStaticTrack(pcm)
                    synchronized(lock) { effects[sfx] = track }
                } catch (e: Exception) {
                    // Leave this one effect silent.
                }
            }
        }, "SoundBuilder").start()
    }

    override fun play(sfx: Sfx) {
        synchronized(lock) {
            val track = effects[sfx] ?: return // not built yet, or failed
            try {
                track.stop()
                track.reloadStaticData()
                track.play()
            } catch (e: IllegalStateException) {
                // Audio hiccup: skip this sound.
            }
        }
    }

    override fun startMusic(bpm: Int) {
        synchronized(lock) {
            musicWanted = true
            if (music == null || bpm != musicBpm) {
                releaseMusic()
                try {
                    val pcm = SoundSynth.toPcm(Music.build(bpm), GameConfig.MUSIC_VOLUME)
                    val track = createStaticTrack(pcm)
                    track.setLoopPoints(0, pcm.size, -1) // -1 = repeat forever
                    music = track
                    musicBpm = bpm
                } catch (e: Exception) {
                    music = null
                }
            }
            applyMusicState()
        }
    }

    override fun stopMusic() {
        synchronized(lock) {
            musicWanted = false
            releaseMusic()
        }
    }

    /** Call when the app goes to the background: silences the music. */
    fun onAppPause() = synchronized(lock) {
        appActive = false
        applyMusicState()
    }

    /** Call when the app comes back. */
    fun onAppResume() = synchronized(lock) {
        appActive = true
        applyMusicState()
    }

    /** Frees the audio hardware. Call when the app closes. */
    fun release() {
        synchronized(lock) {
            releaseMusic()
            for (track in effects.values) track.release()
            effects.clear()
        }
    }

    /** Plays or pauses the music depending on the button, the app state and whether a game is running. */
    private fun applyMusicState() {
        val track = music ?: return
        try {
            if (musicWanted && musicEnabled && appActive) track.play() else track.pause()
        } catch (e: IllegalStateException) {
            // Ignore.
        }
    }

    private fun releaseMusic() {
        music?.let {
            try {
                it.stop()
            } catch (e: IllegalStateException) {
                // Already stopped.
            }
            it.release()
        }
        music = null
    }

    /** Loads samples into a one-shot AudioTrack that can be replayed. */
    private fun createStaticTrack(pcm: ShortArray): AudioTrack {
        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build(),
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(SoundSynth.SAMPLE_RATE)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build(),
            )
            .setBufferSizeInBytes(pcm.size * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()
        track.write(pcm, 0, pcm.size)
        return track
    }

    private companion object {
        const val KEY_MUSIC = "music_enabled"
    }
}
