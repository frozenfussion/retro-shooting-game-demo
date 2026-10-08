package com.example.retroshooter

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.input.TiltFilter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TiltFilterTest {

    /** Feeds the same reading many times so the smoothing settles. */
    private fun settle(x: Float): Float {
        val f = TiltFilter()
        var out = 0f
        repeat(100) { out = f.update(x) }
        return out
    }

    @Test
    fun flatPhoneMeansNoMovement() {
        assertEquals(0f, settle(0f), 0f)
    }

    @Test
    fun tinyTiltInsideTheDeadZoneIsIgnored() {
        assertEquals(0f, settle(0.1f), 0f)
    }

    @Test
    fun bigTiltGivesFullSpeedInTheRightDirection() {
        // Tilting the phone right makes the accelerometer X reading negative.
        val right = settle(-9.81f * 0.9f)
        val left = settle(9.81f * 0.9f)
        val expected = if (GameConfig.TILT_INVERT) -1f else 1f
        assertEquals(expected, right, 0.001f)
        assertEquals(-expected, left, 0.001f)
    }

    @Test
    fun outputNeverLeavesMinusOneToOne() {
        val f = TiltFilter()
        for (x in listOf(-50f, 50f, 0f, 3f, -3f, 100f)) {
            val out = f.update(x)
            assertTrue(out in -1f..1f)
        }
    }
}
