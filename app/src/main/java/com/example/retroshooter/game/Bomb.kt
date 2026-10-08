package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.graphics.BOMB
import com.example.retroshooter.graphics.Gfx

/** A bomb dropped by the enemy plane. It falls straight down. */
class Bomb(x: Float, y: Float, private val fallSpeed: Float) : Entity(x, y, 7, 10) {

    override fun update(dt: Float, world: World) {
        y += fallSpeed * dt
        // Hit the ground: explode.
        if (y + height >= world.groundTop) {
            alive = false
            world.addExplosion(x + width / 2f, world.groundTop - 6f)
            world.sound.play(Sfx.EXPLOSION)
        }
    }

    override fun draw(gfx: Gfx) {
        gfx.drawSprite(BOMB, x.toInt(), y.toInt())
    }
}
