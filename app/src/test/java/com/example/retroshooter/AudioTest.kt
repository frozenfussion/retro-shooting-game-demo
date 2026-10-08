package com.example.retroshooter

import com.example.retroshooter.audio.Music
import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.audio.SoundEffects
import com.example.retroshooter.audio.SoundSynth
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.config.LevelSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AudioTest {

    @Test
    fun toneHasTheRequestedLength() {
        val t = SoundSynth.tone(SoundSynth.Wave.SQUARE, 440f, seconds = 0.5f)
        assertEquals((0.5f * SoundSynth.SAMPLE_RATE).toInt(), t.size)
    }

    @Test
    fun toneStaysWithinVolume() {
        for (wave in SoundSynth.Wave.entries) {
            val t = SoundSynth.tone(wave, 300f, 900f, 0.2f, volume = 0.5f)
            assertTrue(t.all { it in -0.5f..0.5f })
            assertTrue("wave $wave is silent", t.any { it != 0f })
        }
    }

    @Test
    fun middleCIsTheRightPitch() {
        assertEquals(440f, SoundSynth.midiToHz(69), 0.01f)
        assertEquals(880f, SoundSynth.midiToHz(81), 0.05f)
    }

    @Test
    fun everySoundEffectHasSoundInIt() {
        for (sfx in Sfx.entries) {
            val samples = SoundEffects.build(sfx)
            assertTrue("$sfx is empty", samples.isNotEmpty())
            assertTrue("$sfx is silent", samples.any { it != 0f })
            assertTrue("$sfx is longer than 2 seconds", samples.size < 2 * SoundSynth.SAMPLE_RATE)
        }
    }

    @Test
    fun musicLoopsAreExactForEveryLevelTempo() {
        for (level in 1..GameConfig.LEVEL_COUNT) {
            val bpm = LevelSettings.forLevel(level).musicBpm
            val loop = Music.build(bpm)
            assertEquals(Music.loopSamples(bpm), loop.size)
            assertTrue(loop.any { it != 0f })
        }
    }

    @Test
    fun pcmConversionClipsInsteadOfWrapping() {
        val pcm = SoundSynth.toPcm(floatArrayOf(2f, -2f, 0f))
        assertEquals(32767, pcm[0].toInt())
        assertEquals(-32767, pcm[1].toInt())
        assertEquals(0, pcm[2].toInt())
    }
}
