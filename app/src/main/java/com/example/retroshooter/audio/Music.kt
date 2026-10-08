package com.example.retroshooter.audio

import com.example.retroshooter.audio.SoundSynth.Wave
import com.example.retroshooter.audio.SoundSynth.midiToHz
import com.example.retroshooter.audio.SoundSynth.mixInto
import com.example.retroshooter.audio.SoundSynth.tone

/**
 * The background music: an 8-bar chiptune loop in A minor (Am - F - C - G, twice).
 *
 * It is built note by note with [SoundSynth], like an old tracker or MIDI file:
 *  - melody: square wave
 *  - bass: triangle wave
 *  - drums: a kick (low thump) and snare/hi-hat (bursts of noise)
 *
 * The tempo (BPM) is a parameter, so higher levels get a faster song.
 * The loop length is an exact whole number of steps so it repeats with no click.
 */
object Music {
    private const val BARS = 8
    private const val STEPS_PER_BAR = 8 // eighth notes

    // Notes are MIDI numbers (69 = A4 = 440 Hz). 0 = rest.
    // One row per bar, eight eighth-notes per row.
    private val melody: List<IntArray> = listOf(
        // First four bars: running arpeggios over Am, F, C, G.
        intArrayOf(69, 72, 76, 72, 69, 72, 76, 81),
        intArrayOf(65, 69, 72, 69, 65, 69, 72, 77),
        intArrayOf(67, 72, 76, 72, 67, 72, 76, 79),
        intArrayOf(67, 71, 74, 71, 67, 71, 74, 79),
        // Last four bars: a catchier tune on top.
        intArrayOf(76, 0, 76, 77, 76, 72, 69, 0),
        intArrayOf(77, 0, 77, 79, 77, 72, 65, 0),
        intArrayOf(79, 76, 72, 76, 79, 0, 76, 72),
        intArrayOf(74, 0, 71, 74, 71, 67, 0, 0),
    )

    /** Root note of the bass for each bar (A, F, C, G, then again). */
    private val bassRoots = intArrayOf(45, 41, 48, 43, 45, 41, 48, 43)

    /** Number of samples in one eighth-note step at this tempo. */
    fun stepSamples(bpm: Int): Int = (60f / bpm / 2f * SoundSynth.SAMPLE_RATE).toInt()

    /** Number of samples in the whole loop at this tempo. */
    fun loopSamples(bpm: Int): Int = BARS * STEPS_PER_BAR * stepSamples(bpm)

    /** Builds the whole loop as samples between -1.0 and 1.0. */
    fun build(bpm: Int, volume: Float = 1f): FloatArray {
        val step = stepSamples(bpm)
        val stepSeconds = step.toFloat() / SoundSynth.SAMPLE_RATE
        val out = FloatArray(loopSamples(bpm))

        for (bar in 0 until BARS) {
            for (s in 0 until STEPS_PER_BAR) {
                val at = (bar * STEPS_PER_BAR + s) * step

                // Melody.
                val note = melody[bar][s]
                if (note != 0) {
                    mixInto(
                        out, at,
                        tone(Wave.SQUARE, midiToHz(note), seconds = stepSeconds * 0.9f, volume = 0.16f, duty = 0.25f, decay = 0.6f),
                    )
                }

                // Bass: root on beats 1 and 3, the fifth on beat 4.
                val root = bassRoots[bar]
                val bassNote = when (s) {
                    0, 4 -> root
                    2 -> root + 12
                    6 -> root + 7
                    else -> 0
                }
                if (bassNote != 0) {
                    mixInto(out, at, tone(Wave.TRIANGLE, midiToHz(bassNote), seconds = stepSeconds * 1.8f, volume = 0.3f, decay = 0.5f))
                }

                // Drums.
                when (s) {
                    0, 4 -> mixInto(out, at, tone(Wave.TRIANGLE, 160f, 45f, 0.12f, 0.45f))                    // kick
                    2, 6 -> mixInto(out, at, tone(Wave.NOISE, 6000f, 2500f, 0.09f, 0.14f))                    // snare
                    else -> mixInto(out, at, tone(Wave.NOISE, 9000f, 9000f, 0.03f, 0.05f))                    // hi-hat
                }
            }
        }
        if (volume != 1f) for (i in out.indices) out[i] *= volume
        return out
    }
}
