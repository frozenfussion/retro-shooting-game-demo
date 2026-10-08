package com.example.retroshooter

import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.config.LevelSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelsTest {
    private val levels = (1..GameConfig.LEVEL_COUNT).map { LevelSettings.forLevel(it) }

    @Test
    fun everyLevelIsFasterOrEqualAndNeverEasier() {
        for (i in 1 until levels.size) {
            val a = levels[i - 1]
            val b = levels[i]
            assertTrue("plane speed must not drop", b.planeSpeed >= a.planeSpeed)
            assertTrue("bombs must not get rarer", b.bombIntervalSeconds <= a.bombIntervalSeconds)
            assertTrue("bombs must not get slower", b.bombFallSpeed >= a.bombFallSpeed)
            assertTrue("plane HP must not drop", b.planeHp >= a.planeHp)
            assertTrue("music must not slow down", b.musicBpm >= a.musicBpm)
            assertTrue("more bombs must be aimed, not fewer", b.bombAimChance >= a.bombAimChance)
            assertTrue("crates must sway more", b.crateSway > a.crateSway)
        }
    }

    @Test
    fun levelOneUsesTheBaseValues() {
        val one = levels.first()
        assertEquals(GameConfig.PLANE_SPEED_BASE, one.planeSpeed, 0.001f)
        assertEquals(GameConfig.PLANE_HP_BASE, one.planeHp)
    }

    @Test
    fun levelOneKeepsTheGentleStart() {
        val one = levels.first()
        assertEquals(GameConfig.BOMB_INTERVAL_BASE, one.bombIntervalSeconds, 0.001f)
        assertEquals(GameConfig.BOMB_FALL_SPEED_BASE, one.bombFallSpeed, 0.001f)
        assertEquals(GameConfig.BOMB_AIM_CHANCE_BASE, one.bombAimChance, 0.001f)
        assertEquals(GameConfig.CRATE_SWAY_BASE, one.crateSway, 0.001f)
    }

    @Test
    fun laterLevelsDropMuchMoreBombsAndCratesSwayMuchMore() {
        val last = levels.last()
        assertTrue(last.bombIntervalSeconds < levels.first().bombIntervalSeconds / 3)
        assertTrue(last.crateSway > levels.first().crateSway * 3)
    }

    @Test
    fun valuesStayInsideTheirLimits() {
        for (l in levels) {
            assertTrue(l.planeSpeed <= GameConfig.PLANE_SPEED_MAX)
            assertTrue(l.bombFallSpeed <= GameConfig.BOMB_FALL_SPEED_MAX)
            assertTrue(l.bombIntervalSeconds >= GameConfig.BOMB_INTERVAL_MIN)
            assertTrue(l.bombAimChance <= GameConfig.BOMB_AIM_CHANCE_MAX)
        }
    }

    @Test
    fun theLastLevelIsMuchFasterThanTheFirst() {
        assertTrue(levels.last().planeSpeed > levels.first().planeSpeed * 2)
    }
}
