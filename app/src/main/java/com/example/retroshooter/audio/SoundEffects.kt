package com.example.retroshooter.audio

import com.example.retroshooter.audio.SoundSynth.Wave
import com.example.retroshooter.audio.SoundSynth.mix
import com.example.retroshooter.audio.SoundSynth.sequence
import com.example.retroshooter.audio.SoundSynth.silence
import com.example.retroshooter.audio.SoundSynth.tone

/**
 * The recipe for every sound effect, built from [SoundSynth] pieces.
 *
 * Want to change how something sounds? Edit its recipe here. The numbers are
 * pitch (Hz), length (seconds) and volume. Try changing them and listen.
 */
object SoundEffects {

    /** Builds the samples (-1.0 to 1.0) for one effect. */
    fun build(sfx: Sfx): FloatArray = when (sfx) {
        // A rising whoosh: a sliding buzz over a burst of noise.
        Sfx.ROCKET_LAUNCH -> mix(
            samples(0.28f),
            0 to tone(Wave.SQUARE, 250f, 900f, 0.28f, 0.25f, duty = 0.25f),
            0 to tone(Wave.NOISE, 3000f, 900f, 0.28f, 0.25f),
        )

        // Big boom: noise that starts hissy and rumbles down, plus a low thump.
        Sfx.EXPLOSION -> mix(
            samples(0.55f),
            0 to tone(Wave.NOISE, 5000f, 120f, 0.55f, 0.6f),
            0 to tone(Wave.TRIANGLE, 110f, 35f, 0.4f, 0.6f),
        )

        // The classic falling whistle.
        Sfx.BOMB_DROP -> tone(Wave.SQUARE, 1100f, 350f, 0.45f, 0.18f, duty = 0.125f, decay = 0.6f)

        // Ouch: low growl plus crunch.
        Sfx.PLAYER_HIT -> mix(
            samples(0.4f),
            0 to tone(Wave.TRIANGLE, 200f, 50f, 0.4f, 0.7f),
            0 to tone(Wave.NOISE, 2500f, 400f, 0.25f, 0.45f),
        )

        // Shield pops: quick downward ping.
        Sfx.SHIELD_BREAK -> sequence(
            tone(Wave.TRIANGLE, 1200f, 300f, 0.15f, 0.5f),
            tone(Wave.NOISE, 4000f, 1500f, 0.1f, 0.3f),
        )

        // Metal crunch.
        Sfx.PLANE_HIT -> mix(
            samples(0.22f),
            0 to tone(Wave.NOISE, 3500f, 700f, 0.2f, 0.5f),
            0 to tone(Wave.SQUARE, 320f, 120f, 0.15f, 0.3f, duty = 0.25f),
        )

        // "Ha ha ha!" - three little blips going up and down.
        Sfx.PILOT_LAUGH -> sequence(
            tone(Wave.SQUARE, 420f, 380f, 0.07f, 0.22f),
            silence(0.04f),
            tone(Wave.SQUARE, 380f, 340f, 0.07f, 0.22f),
            silence(0.04f),
            tone(Wave.SQUARE, 440f, 360f, 0.07f, 0.22f),
            silence(0.04f),
            tone(Wave.SQUARE, 340f, 300f, 0.1f, 0.22f),
        )

        // Happy rising arpeggio (C major).
        Sfx.PICKUP_LIFE -> sequence(
            tone(Wave.SQUARE, 523f, seconds = 0.07f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 659f, seconds = 0.07f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 784f, seconds = 0.07f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 1047f, seconds = 0.2f, volume = 0.3f),
        )

        // Two quick pings.
        Sfx.PICKUP_AMMO -> sequence(
            tone(Wave.SQUARE, 700f, seconds = 0.07f, volume = 0.3f, duty = 0.25f, decay = 0.3f),
            tone(Wave.SQUARE, 1050f, seconds = 0.14f, volume = 0.3f, duty = 0.25f),
        )

        // Shimmering upward slide.
        Sfx.PICKUP_SHIELD -> mix(
            samples(0.35f),
            0 to tone(Wave.TRIANGLE, 400f, 1200f, 0.35f, 0.5f),
            0 to tone(Wave.SQUARE, 800f, 2400f, 0.35f, 0.12f, duty = 0.125f),
        )

        // Dry click: no ammo.
        Sfx.OUT_OF_AMMO -> tone(Wave.NOISE, 1800f, 600f, 0.05f, 0.35f)

        // Level clear jingle (rising).
        Sfx.LEVEL_CLEAR -> sequence(
            tone(Wave.SQUARE, 523f, seconds = 0.12f, volume = 0.3f, decay = 0.4f),
            tone(Wave.SQUARE, 659f, seconds = 0.12f, volume = 0.3f, decay = 0.4f),
            tone(Wave.SQUARE, 784f, seconds = 0.12f, volume = 0.3f, decay = 0.4f),
            tone(Wave.SQUARE, 1047f, seconds = 0.12f, volume = 0.3f, decay = 0.4f),
            tone(Wave.SQUARE, 1319f, seconds = 0.4f, volume = 0.3f),
        )

        // Sad falling notes.
        Sfx.GAME_OVER -> sequence(
            tone(Wave.TRIANGLE, 392f, seconds = 0.22f, volume = 0.5f, decay = 0.3f),
            tone(Wave.TRIANGLE, 330f, seconds = 0.22f, volume = 0.5f, decay = 0.3f),
            tone(Wave.TRIANGLE, 262f, seconds = 0.22f, volume = 0.5f, decay = 0.3f),
            tone(Wave.TRIANGLE, 196f, 120f, 0.7f, 0.5f),
        )

        // Fanfare.
        Sfx.WIN -> sequence(
            tone(Wave.SQUARE, 523f, seconds = 0.15f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 523f, seconds = 0.15f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 523f, seconds = 0.15f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 659f, seconds = 0.35f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 523f, seconds = 0.15f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 659f, seconds = 0.15f, volume = 0.3f, decay = 0.3f),
            tone(Wave.SQUARE, 784f, seconds = 0.7f, volume = 0.3f),
        )

        // Start-of-game blip.
        Sfx.START -> sequence(
            tone(Wave.SQUARE, 392f, seconds = 0.08f, volume = 0.3f),
            tone(Wave.SQUARE, 523f, seconds = 0.08f, volume = 0.3f),
            tone(Wave.SQUARE, 784f, seconds = 0.16f, volume = 0.3f),
        )

        // Menu click.
        Sfx.CLICK -> tone(Wave.SQUARE, 900f, 700f, 0.05f, 0.25f)
    }

    private fun samples(seconds: Float) = (seconds * SoundSynth.SAMPLE_RATE).toInt()
}
