package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.graphics.CRATE_AMMO
import com.example.retroshooter.graphics.CRATE_LIFE
import com.example.retroshooter.graphics.CRATE_SHIELD
import com.example.retroshooter.graphics.Sprite

/**
 * The kinds of supply crate. Each one knows its picture, how common it is,
 * what sound to play, and what it does to the player.
 *
 * To add a new power-up, add one entry here.
 */
enum class CrateType(
    val sprite: Sprite,
    val weight: Int,
    val sound: Sfx,
) {
    LIFE(CRATE_LIFE, GameConfig.CRATE_WEIGHT_LIFE, Sfx.PICKUP_LIFE) {
        override fun apply(player: Player) {
            player.lives = minOf(GameConfig.LIVES_MAX, player.lives + 1)
        }
    },
    AMMO(CRATE_AMMO, GameConfig.CRATE_WEIGHT_AMMO, Sfx.PICKUP_AMMO) {
        override fun apply(player: Player) {
            player.ammo = minOf(GameConfig.AMMO_MAX, player.ammo + GameConfig.AMMO_PER_CRATE)
        }
    },
    SHIELD(CRATE_SHIELD, GameConfig.CRATE_WEIGHT_SHIELD, Sfx.PICKUP_SHIELD) {
        override fun apply(player: Player) {
            player.shieldSeconds = GameConfig.SHIELD_SECONDS
        }
    };

    /** What the crate gives the player. */
    abstract fun apply(player: Player)
}
