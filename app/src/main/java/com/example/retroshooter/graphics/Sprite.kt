package com.example.retroshooter.graphics

/**
 * A sprite is a small picture made of text.
 *
 * Every string is one row of pixels and every character is one pixel. The
 * character is looked up in [PALETTE] (see Sprites.kt) to find its color, and
 * "." means transparent. So this is a tiny 3x2 sprite:
 *
 *     Sprite("RRR", "R.R")
 *
 * All rows must be the same length. The Sprite Lab web tool writes these for you.
 */
class Sprite(vararg val rows: String) {
    val width: Int = rows[0].length
    val height: Int = rows.size

    init {
        require(rows.all { it.length == width }) { "All rows of a sprite must have the same length" }
    }
}
