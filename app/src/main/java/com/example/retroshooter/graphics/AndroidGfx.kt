package com.example.retroshooter.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import java.util.IdentityHashMap

/**
 * The real [Gfx] for the phone. It draws onto a small off-screen bitmap
 * ([frame]) that is exactly [width] x [height] game pixels. GameView then
 * stretches that bitmap to fill the screen without smoothing, which is what
 * makes the pixels look big and chunky.
 *
 * Each sprite is turned into an Android Bitmap the first time it is drawn,
 * then reused.
 */
class AndroidGfx(val width: Int, val height: Int) : Gfx {

    val frame: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    private val canvas = Canvas(frame)
    private val paint = Paint().apply {
        isAntiAlias = false
        style = Paint.Style.FILL
    }
    private val spritePaint = Paint().apply {
        isFilterBitmap = false // keep scaled sprites crisp
        isAntiAlias = false
    }
    private val target = Rect()
    private val bitmaps = IdentityHashMap<Sprite, Bitmap>()

    override fun drawSprite(sprite: Sprite, x: Int, y: Int, scale: Int) {
        val bitmap = bitmapFor(sprite)
        if (scale == 1) {
            canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), null)
        } else {
            target.set(x, y, x + bitmap.width * scale, y + bitmap.height * scale)
            canvas.drawBitmap(bitmap, null, target, spritePaint)
        }
    }

    override fun fillRect(x: Int, y: Int, w: Int, h: Int, color: Int) {
        paint.color = color
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + w).toFloat(), (y + h).toFloat(), paint)
    }

    /** Converts a sprite's text rows into a bitmap, looking up each character's color in PALETTE. */
    private fun bitmapFor(sprite: Sprite): Bitmap = bitmaps.getOrPut(sprite) {
        val bitmap = Bitmap.createBitmap(sprite.width, sprite.height, Bitmap.Config.ARGB_8888)
        for (row in 0 until sprite.height) {
            for (col in 0 until sprite.width) {
                val color = PALETTE[sprite.rows[row][col]] ?: 0 // 0 = transparent
                bitmap.setPixel(col, row, color)
            }
        }
        bitmap
    }
}
