package com.example.retroshooter.config

/**
 * GameConfig.kt - THE DEVELOPER SETTINGS FILE.
 *
 * Every number that changes how the game feels lives here. Players never see
 * this file. To tune the game, change a value, press Run, and play.
 *
 * Units:
 *  - Distances are in "game pixels". The game screen is 180 pixels wide and
 *    gets scaled up to fill the phone, which is what gives the chunky look.
 *  - Speeds are game pixels per second.
 *  - Times are in seconds.
 */
object GameConfig {

    // ------------------------------------------------------------------
    // Title
    // ------------------------------------------------------------------
    /** Shown on the title screen. Change it to rename the game. */
    const val GAME_TITLE = "SKY RAID"

    // ------------------------------------------------------------------
    // Screen
    // ------------------------------------------------------------------
    /** The game is always 180 pixels wide. */
    const val SCREEN_WIDTH = 180

    /** Height is adapted to the phone, but kept between these two values. */
    const val SCREEN_HEIGHT_MIN = 320
    const val SCREEN_HEIGHT_MAX = 400

    /** Height of the strip of ground at the bottom of the screen. */
    const val GROUND_HEIGHT = 32

    // ------------------------------------------------------------------
    // Levels: the game gets faster every level
    // ------------------------------------------------------------------
    /** Beat this level and you win the game. */
    const val LEVEL_COUNT = 10

    /** Enemy plane flying speed on level 1, then multiplied each level. */
    const val PLANE_SPEED_BASE = 30f
    const val PLANE_SPEED_GROWTH = 1.20f
    const val PLANE_SPEED_MAX = 150f

    /** Plane HP on level 1, plus 1 extra HP every PLANE_HP_EVERY_N_LEVELS levels. */
    const val PLANE_HP_BASE = 3
    const val PLANE_HP_EVERY_N_LEVELS = 2

    /** Seconds between bombs on level 1. Multiplied by the factor each level (smaller = more bombs). */
    const val BOMB_INTERVAL_BASE = 1.8f
    const val BOMB_INTERVAL_FACTOR = 0.85f
    const val BOMB_INTERVAL_MIN = 0.4f

    /**
     * Chance (0.0 to 1.0) that a bomb is aimed: the plane holds it until it is
     * flying right over the soldier. 0 = every bomb drops at a random spot, 1 = every bomb is aimed.
     * Starts at BASE on level 1 and grows by PER_LEVEL each level, up to MAX.
     */
    const val BOMB_AIM_CHANCE_BASE = 0.5f
    const val BOMB_AIM_CHANCE_PER_LEVEL = 0.05f
    const val BOMB_AIM_CHANCE_MAX = 0.9f

    /** How close (in pixels) the plane must be to the soldier's position to drop an aimed bomb. */
    const val BOMB_AIM_TOLERANCE = 6f

    /** Bomb fall speed on level 1, then multiplied each level. */
    const val BOMB_FALL_SPEED_BASE = 70f
    const val BOMB_FALL_SPEED_GROWTH = 1.12f
    const val BOMB_FALL_SPEED_MAX = 200f

    /** How long the "LEVEL n" banner shows before the plane appears. */
    const val LEVEL_INTRO_SECONDS = 1.5f

    /** Pause after destroying the plane before the next level starts. */
    const val LEVEL_CLEAR_SECONDS = 2.5f

    /** After game over or a win, taps are ignored for this long so nobody skips the screen by accident. */
    const val END_SCREEN_TAP_DELAY = 0.8f

    // ------------------------------------------------------------------
    // Enemy plane
    // ------------------------------------------------------------------
    /** Height range (top of the plane) where the plane flies. It picks a new height each pass. */
    const val PLANE_ALTITUDE_MIN = 34
    const val PLANE_ALTITUDE_MAX = 64

    // ------------------------------------------------------------------
    // Supply drone
    // ------------------------------------------------------------------
    /** Seconds from the start of a level until the first drone arrives. */
    const val DRONE_FIRST_DELAY = 6f

    /** Seconds between drones after that. */
    const val DRONE_INTERVAL = 14f

    const val DRONE_SPEED = 40f
    const val DRONE_ALTITUDE = 96

    /** How fast a crate floats down on its parachute. */
    const val CRATE_FALL_SPEED = 28f

    /**
     * How strongly a falling crate swings left and right. Level 1 starts at BASE and every
     * level adds PER_LEVEL, so crates get harder to catch.
     */
    const val CRATE_SWAY_BASE = 8f
    const val CRATE_SWAY_PER_LEVEL = 4f

    /** How long a crate waits on the ground before it disappears. */
    const val CRATE_GROUND_SECONDS = 5f

    /** Relative chance of each crate type. Bigger number = more common. */
    const val CRATE_WEIGHT_LIFE = 2
    const val CRATE_WEIGHT_AMMO = 5
    const val CRATE_WEIGHT_SHIELD = 3

    // ------------------------------------------------------------------
    // Player (the skateboard soldier)
    // ------------------------------------------------------------------
    const val LIVES_START = 3
    const val LIVES_MAX = 5

    const val AMMO_START = 10
    const val AMMO_MAX = 15

    /** Rockets handed out at the start of every new level. */
    const val AMMO_PER_LEVEL = 5

    /** Rockets in an ammo crate. */
    const val AMMO_PER_CRATE = 5

    /** How long a shield lasts. It also vanishes after absorbing one bomb. */
    const val SHIELD_SECONDS = 8f

    /** Seconds the soldier cannot be hurt after being hit (he blinks). */
    const val INVULNERABLE_SECONDS = 1.5f

    /** Top speed of the skateboard when the phone is tilted fully. */
    const val PLAYER_MAX_SPEED = 140f

    const val ROCKET_SPEED = 260f

    /** Minimum time between two rockets. */
    const val FIRE_COOLDOWN = 0.25f

    // ------------------------------------------------------------------
    // Tilt steering
    // ------------------------------------------------------------------
    /** Set to true if the soldier moves the wrong way on your phone. */
    const val TILT_INVERT = false

    /** Tilt smaller than this is ignored (0.04 is roughly 2 degrees). */
    const val TILT_DEAD_ZONE = 0.04f

    /** Tilt at which the soldier reaches top speed (0.35 is roughly 20 degrees). */
    const val TILT_FULL_SPEED = 0.35f

    /** 0 = very smooth but laggy, 1 = raw and twitchy. */
    const val TILT_SMOOTHING = 0.2f

    // ------------------------------------------------------------------
    // Time of day (the background follows the phone clock, 24 hour clock)
    // ------------------------------------------------------------------
    const val DAWN_START_HOUR = 5
    const val DAY_START_HOUR = 8
    const val EVENING_START_HOUR = 17
    const val NIGHT_START_HOUR = 20

    // ------------------------------------------------------------------
    // Sound
    // ------------------------------------------------------------------
    /** Is the music on the first time the game is installed? */
    const val MUSIC_DEFAULT_ON = true

    /** Music tempo on level 1, and how much faster it gets every level. */
    const val MUSIC_BASE_BPM = 110
    const val MUSIC_BPM_PER_LEVEL = 8

    /** Loudness from 0.0 to 1.0. */
    const val MUSIC_VOLUME = 0.35f
    const val SFX_VOLUME = 0.8f
}
