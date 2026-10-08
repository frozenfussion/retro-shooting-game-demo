package com.example.retroshooter.audio

import java.util.Random
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow

/**
 * A tiny sound synthesizer, like the sound chips in 80s arcade machines.
 *
 * Sound is just a long list of numbers (samples) between -1.0 and 1.0. The
 * phone's speaker reads them 22,050 times a second. By choosing the numbers
 * we make a tone:
 *  - SQUARE: flips between +1 and -1. Buzzy, the classic "beep".
 *  - TRIANGLE: ramps up and down. Softer, good for bass.
 *  - NOISE: random numbers. Explosions, drums and whooshes.
 */
object SoundSynth {
    const val SAMPLE_RATE = 22050

    enum class Wave { SQUARE, TRIANGLE, NOISE }

    /**
     * Makes one note or sweep.
     * @param f0 pitch at the start, in Hertz (vibrations per second)
     * @param f1 pitch at the end. Different from f0 = a slide up or down.
     * @param seconds how long it lasts
     * @param volume 0.0 to 1.0
     * @param duty for SQUARE only: 0.5 is a full buzz, smaller is thinner and nasal
     * @param decay how quickly it fades out: 1 = fades to silence by the end, 0 = no fade
     */
    fun tone(
        wave: Wave,
        f0: Float,
        f1: Float = f0,
        seconds: Float,
        volume: Float = 0.5f,
        duty: Float = 0.5f,
        decay: Float = 1f,
    ): FloatArray {
        val n = (seconds * SAMPLE_RATE).toInt()
        val out = FloatArray(n)
        val noise = Random(1234) // same seed = the noise sounds the same every run
        val attack = (0.003f * SAMPLE_RATE).toInt().coerceAtLeast(1) // tiny fade-in avoids clicks
        var phase = 0f
        var held = 0f
        for (i in 0 until n) {
            val t = i.toFloat() / n
            val freq = f0 + (f1 - f0) * t
            phase += freq / SAMPLE_RATE
            val wrapped = phase >= 1f
            if (wrapped) phase -= 1f

            val raw = when (wave) {
                Wave.SQUARE -> if (phase < duty) 1f else -1f
                Wave.TRIANGLE -> 4f * abs(phase - 0.5f) - 1f
                Wave.NOISE -> {
                    // A new random value each time the "phase" wraps. Higher freq = hissier noise.
                    if (wrapped || i == 0) held = noise.nextFloat() * 2f - 1f
                    held
                }
            }
            val envelope = (1f - t * decay).coerceAtLeast(0f) * min(1f, (i + 1f) / attack)
            out[i] = raw * volume * envelope
        }
        return out
    }

    /** A gap of silence. */
    fun silence(seconds: Float): FloatArray = FloatArray((seconds * SAMPLE_RATE).toInt())

    /** Plays the parts one after another. */
    fun sequence(vararg parts: FloatArray): FloatArray {
        val out = FloatArray(parts.sumOf { it.size })
        var pos = 0
        for (p in parts) {
            p.copyInto(out, pos)
            pos += p.size
        }
        return out
    }

    /** Plays the parts at the same time, each starting at its own offset (in samples). */
    fun mix(length: Int, vararg parts: Pair<Int, FloatArray>): FloatArray {
        val out = FloatArray(length)
        for ((offset, samples) in parts) mixInto(out, offset, samples)
        return out
    }

    /** Adds [samples] into [dst] starting at [offset]. Anything past the end is dropped. */
    fun mixInto(dst: FloatArray, offset: Int, samples: FloatArray) {
        for (i in samples.indices) {
            val j = offset + i
            if (j >= dst.size) break
            dst[j] += samples[i]
        }
    }

    /** Converts samples to the 16-bit numbers the speaker wants, scaled by [gain] and clipped. */
    fun toPcm(samples: FloatArray, gain: Float = 1f): ShortArray = ShortArray(samples.size) {
        (samples[it] * gain).coerceIn(-1f, 1f).times(32767f).toInt().toShort()
    }

    /** Pitch in Hertz of a MIDI note number (69 = A above middle C = 440 Hz). */
    fun midiToHz(note: Int): Float = 440f * 2f.pow((note - 69) / 12f)
}
