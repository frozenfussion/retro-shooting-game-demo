package com.example.retroshooter

import com.example.retroshooter.graphics.*
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Guards the sprite data. If you edit a sprite by hand (or paste a new export
 * from the Sprite Lab) and make a typo, this test tells you exactly which one.
 */
class SpritesTest {

    private val all: Map<String, List<Sprite>> = mapOf(
        "PLANE" to listOf(PLANE),
        "PROP" to PROP,
        "PILOT_HEAD" to PILOT_HEAD,
        "PILOT_BODY" to listOf(PILOT_BODY),
        "SKATER" to listOf(SKATER),
        "ROCKET" to ROCKET,
        "BOMB" to listOf(BOMB),
        "DRONE" to DRONE,
        "PARACHUTE" to listOf(PARACHUTE),
        "CRATE_LIFE" to listOf(CRATE_LIFE),
        "CRATE_AMMO" to listOf(CRATE_AMMO),
        "CRATE_SHIELD" to listOf(CRATE_SHIELD),
        "EXPLOSION" to EXPLOSION,
        "HEART" to listOf(HEART),
        "ICON_MUSIC_ON" to listOf(ICON_MUSIC_ON),
        "ICON_MUSIC_OFF" to listOf(ICON_MUSIC_OFF),
        "SHIELD_BUBBLE" to listOf(SHIELD_BUBBLE),
    )

    @Test
    fun everyPixelCharacterIsInThePalette() {
        for ((name, frames) in all) {
            for ((i, sprite) in frames.withIndex()) {
                for ((row, line) in sprite.rows.withIndex()) {
                    for (ch in line) {
                        assertTrue("$name frame $i row $row uses '$ch' which is not in PALETTE", ch == '.' || PALETTE.containsKey(ch))
                    }
                }
            }
        }
    }

    @Test
    fun animationFramesAreTheSameSize() {
        for ((name, frames) in all) {
            val first = frames.first()
            for (f in frames) {
                assertTrue("$name frames differ in size", f.width == first.width && f.height == first.height)
            }
        }
    }

    @Test
    fun theSpritesTheGameDrawsRelativeToEachOtherStillFit() {
        // The pilot is drawn on top of the plane at fixed offsets (see EnemyPlane.draw).
        assertTrue(PILOT_BODY.width <= PLANE.width - 10)
        assertTrue(PLANE.width == 32 && PLANE.height >= 14)
    }
}
