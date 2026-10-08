package com.example.retroshooter.input

import com.example.retroshooter.config.GameConfig
import kotlin.math.abs
import kotlin.math.sign

/**
 * Turns raw accelerometer readings into a steering value from -1 (full left)
 * to +1 (full right).
 *
 * Three steps, all tunable in GameConfig:
 *  1. Smoothing: shaky hands would make the soldier jitter, so we blend each
 *     new reading with the previous ones.
 *  2. Dead zone: tiny tilts are ignored so the soldier can stand still.
 *  3. Scaling: tilt grows from 0 at the dead zone to 1 at "full speed" tilt.
 *
 * This class does not use Android, so it is covered by unit tests.
 */
class TiltFilter {
    private var smoothed = 0f

    /**
     * @param accelX the accelerometer's X reading in m/s^2. With the phone upright in
     * portrait, X is positive when the left edge is lower (a tilt to the left).
     */
    fun update(accelX: Float): Float {
        // Gravity is about 9.81 m/s^2, so this turns the reading into "how much of gravity is sideways".
        var tilt = -accelX / GRAVITY
        if (GameConfig.TILT_INVERT) tilt = -tilt

        smoothed += (tilt - smoothed) * GameConfig.TILT_SMOOTHING

        val size = abs(smoothed)
        if (size < GameConfig.TILT_DEAD_ZONE) return 0f
        val scaled = (size - GameConfig.TILT_DEAD_ZONE) / (GameConfig.TILT_FULL_SPEED - GameConfig.TILT_DEAD_ZONE)
        return sign(smoothed) * scaled.coerceAtMost(1f)
    }

    /** Forget the smoothing history (used when the app resumes). */
    fun reset() {
        smoothed = 0f
    }

    private companion object {
        const val GRAVITY = 9.81f
    }
}
