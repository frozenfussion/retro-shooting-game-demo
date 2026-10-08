package com.example.retroshooter.graphics

/**
 * The only drawing tools the game needs. Everything on screen is built from
 * these two calls (text and backgrounds are made of rectangles).
 *
 * The game code only knows this interface. [AndroidGfx] is the real
 * implementation that draws on the phone, and the unit tests use a fake one.
 * That separation is what lets us test the game without a phone.
 *
 * Coordinates are game pixels, with (0, 0) at the top-left corner.
 */
interface Gfx {
    /** Draws a sprite with its top-left corner at (x, y). */
    fun drawSprite(sprite: Sprite, x: Int, y: Int)

    /** Fills a rectangle with a color (0xAARRGGBB). */
    fun fillRect(x: Int, y: Int, w: Int, h: Int, color: Int)
}
