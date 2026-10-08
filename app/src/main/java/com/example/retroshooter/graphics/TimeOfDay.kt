package com.example.retroshooter.graphics

import com.example.retroshooter.config.GameConfig

/** The four looks of the background. */
enum class TimeOfDay {
    DAWN, DAY, EVENING, NIGHT;

    companion object {
        /** Picks the look for a clock hour (0 to 23), using the hours in GameConfig. */
        fun forHour(hour: Int): TimeOfDay = when {
            hour >= GameConfig.NIGHT_START_HOUR || hour < GameConfig.DAWN_START_HOUR -> NIGHT
            hour >= GameConfig.EVENING_START_HOUR -> EVENING
            hour >= GameConfig.DAY_START_HOUR -> DAY
            else -> DAWN
        }
    }
}
