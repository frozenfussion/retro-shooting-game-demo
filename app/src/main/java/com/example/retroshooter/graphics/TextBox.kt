package com.example.retroshooter.graphics

/** One line of text for [TextBox]: the words, the color and the size (1 = small, 2 = double, ...). */
class TextLine(val text: String, val color: Int, val scale: Int = 1)

/**
 * Draws lines of centered text inside a solid black box with a thin white border.
 * A plain dark box keeps the text readable on any background: bright day sky, sunset, or night.
 */
object TextBox {
    private const val PADDING = 6
    private const val GAP = 4

    /** Draws the box with its top edge at [top] and returns the box height. */
    fun draw(gfx: Gfx, centerX: Int, top: Int, vararg lines: TextLine): Int {
        val textWidth = lines.maxOf { PixelFont.width(it.text, it.scale) }
        val textHeight = lines.sumOf { PixelFont.GLYPH_HEIGHT * it.scale } + GAP * (lines.size - 1)
        val boxWidth = textWidth + PADDING * 2
        val boxHeight = textHeight + PADDING * 2
        val left = centerX - boxWidth / 2

        gfx.fillRect(left - 1, top - 1, boxWidth + 2, boxHeight + 2, Colors.WHITE) // border
        gfx.fillRect(left, top, boxWidth, boxHeight, Colors.BLACK)                  // box

        var y = top + PADDING
        for (line in lines) {
            PixelFont.drawCentered(gfx, line.text, centerX, y, line.color, line.scale)
            y += PixelFont.GLYPH_HEIGHT * line.scale + GAP
        }
        return boxHeight
    }
}
