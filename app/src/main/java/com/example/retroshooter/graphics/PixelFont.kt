package com.example.retroshooter.graphics

/**
 * A tiny built-in pixel font, 3 pixels wide and 5 tall, so we need no font files.
 *
 * Each glyph is 5 rows of 3 characters. "#" is a lit pixel. Lower case letters
 * are drawn as capitals. Characters that are not listed are skipped.
 */
object PixelFont {
    const val GLYPH_WIDTH = 3
    const val GLYPH_HEIGHT = 5

    /** Space between letters, in font pixels. */
    private const val SPACING = 1

    private val glyphs: Map<Char, List<String>> = mapOf(
        'A' to listOf(".#.", "#.#", "###", "#.#", "#.#"),
        'B' to listOf("##.", "#.#", "##.", "#.#", "##."),
        'C' to listOf(".##", "#..", "#..", "#..", ".##"),
        'D' to listOf("##.", "#.#", "#.#", "#.#", "##."),
        'E' to listOf("###", "#..", "##.", "#..", "###"),
        'F' to listOf("###", "#..", "##.", "#..", "#.."),
        'G' to listOf(".##", "#..", "#.#", "#.#", ".##"),
        'H' to listOf("#.#", "#.#", "###", "#.#", "#.#"),
        'I' to listOf("###", ".#.", ".#.", ".#.", "###"),
        'J' to listOf("..#", "..#", "..#", "#.#", ".#."),
        'K' to listOf("#.#", "#.#", "##.", "#.#", "#.#"),
        'L' to listOf("#..", "#..", "#..", "#..", "###"),
        'M' to listOf("#.#", "###", "###", "#.#", "#.#"),
        'N' to listOf("##.", "#.#", "#.#", "#.#", "#.#"),
        'O' to listOf(".#.", "#.#", "#.#", "#.#", ".#."),
        'P' to listOf("##.", "#.#", "##.", "#..", "#.."),
        'Q' to listOf(".#.", "#.#", "#.#", "##.", ".##"),
        'R' to listOf("##.", "#.#", "##.", "#.#", "#.#"),
        'S' to listOf(".##", "#..", ".#.", "..#", "##."),
        'T' to listOf("###", ".#.", ".#.", ".#.", ".#."),
        'U' to listOf("#.#", "#.#", "#.#", "#.#", "###"),
        'V' to listOf("#.#", "#.#", "#.#", "#.#", ".#."),
        'W' to listOf("#.#", "#.#", "###", "###", "#.#"),
        'X' to listOf("#.#", "#.#", ".#.", "#.#", "#.#"),
        'Y' to listOf("#.#", "#.#", ".#.", ".#.", ".#."),
        'Z' to listOf("###", "..#", ".#.", "#..", "###"),
        '0' to listOf("###", "#.#", "#.#", "#.#", "###"),
        '1' to listOf(".#.", "##.", ".#.", ".#.", "###"),
        '2' to listOf("##.", "..#", ".#.", "#..", "###"),
        '3' to listOf("##.", "..#", ".#.", "..#", "##."),
        '4' to listOf("#.#", "#.#", "###", "..#", "..#"),
        '5' to listOf("###", "#..", "##.", "..#", "##."),
        '6' to listOf(".##", "#..", "###", "#.#", "###"),
        '7' to listOf("###", "..#", ".#.", ".#.", ".#."),
        '8' to listOf("###", "#.#", "###", "#.#", "###"),
        '9' to listOf("###", "#.#", "###", "..#", "##."),
        ' ' to listOf("...", "...", "...", "...", "..."),
        ':' to listOf("...", ".#.", "...", ".#.", "..."),
        '-' to listOf("...", "...", "###", "...", "..."),
        '!' to listOf(".#.", ".#.", ".#.", "...", ".#."),
        '.' to listOf("...", "...", "...", "...", ".#."),
        '/' to listOf("..#", "..#", ".#.", "#..", "#.."),
        '?' to listOf("##.", "..#", ".#.", "...", ".#."),
        '+' to listOf("...", ".#.", "###", ".#.", "..."),
        '\'' to listOf(".#.", ".#.", "...", "...", "..."),
    )

    /** Width in game pixels of [text] drawn at [scale]. */
    fun width(text: String, scale: Int = 1): Int =
        if (text.isEmpty()) 0 else (text.length * (GLYPH_WIDTH + SPACING) - SPACING) * scale

    /** Draws [text] with its top-left corner at (x, y). Each font pixel becomes a scale x scale square. */
    fun draw(gfx: Gfx, text: String, x: Int, y: Int, color: Int, scale: Int = 1) {
        var cursor = x
        for (ch in text.uppercase()) {
            val glyph = glyphs[ch]
            if (glyph != null) {
                for (row in 0 until GLYPH_HEIGHT) {
                    for (col in 0 until GLYPH_WIDTH) {
                        if (glyph[row][col] == '#') {
                            gfx.fillRect(cursor + col * scale, y + row * scale, scale, scale, color)
                        }
                    }
                }
            }
            cursor += (GLYPH_WIDTH + SPACING) * scale
        }
    }

    /** Draws [text] horizontally centered on [centerX]. */
    fun drawCentered(gfx: Gfx, text: String, centerX: Int, y: Int, color: Int, scale: Int = 1) {
        draw(gfx, text, centerX - width(text, scale) / 2, y, color, scale)
    }
}
