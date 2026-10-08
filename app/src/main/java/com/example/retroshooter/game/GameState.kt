package com.example.retroshooter.game

/** The screens the game moves between. */
enum class GameState {
    /** Title screen, waiting for a tap. */
    TITLE,

    /** The fight. */
    PLAYING,

    /** The plane was just destroyed; a short pause before the next level (or the win screen). */
    LEVEL_CLEAR,

    /** The soldier ran out of lives. */
    GAME_OVER,

    /** All levels cleared. */
    WON,
}
