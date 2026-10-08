package com.example.retroshooter.config

import kotlin.math.min
import kotlin.math.pow

/**
 * The numbers for one level. [forLevel] builds them from the formulas in
 * [GameConfig], so changing a growth value there changes every level.
 *
 * To make one special level (a boss level, say) later, return a hand-made
 * LevelSettings for that level number from [forLevel].
 */
data class LevelSettings(
    val level: Int,
    val planeSpeed: Float,
    val planeHp: Int,
    val bombIntervalSeconds: Float,
    val bombFallSpeed: Float,
    val musicBpm: Int,
) {
    companion object {
        fun forLevel(level: Int): LevelSettings {
            val steps = level - 1
            return LevelSettings(
                level = level,
                planeSpeed = min(
                    GameConfig.PLANE_SPEED_MAX,
                    GameConfig.PLANE_SPEED_BASE * GameConfig.PLANE_SPEED_GROWTH.pow(steps),
                ),
                planeHp = GameConfig.PLANE_HP_BASE + steps / GameConfig.PLANE_HP_EVERY_N_LEVELS,
                bombIntervalSeconds = maxOf(
                    GameConfig.BOMB_INTERVAL_MIN,
                    GameConfig.BOMB_INTERVAL_BASE * GameConfig.BOMB_INTERVAL_FACTOR.pow(steps),
                ),
                bombFallSpeed = min(
                    GameConfig.BOMB_FALL_SPEED_MAX,
                    GameConfig.BOMB_FALL_SPEED_BASE * GameConfig.BOMB_FALL_SPEED_GROWTH.pow(steps),
                ),
                musicBpm = GameConfig.MUSIC_BASE_BPM + GameConfig.MUSIC_BPM_PER_LEVEL * steps,
            )
        }
    }
}
