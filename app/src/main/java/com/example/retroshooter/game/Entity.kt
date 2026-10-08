package com.example.retroshooter.game

import com.example.retroshooter.graphics.Gfx

/**
 * Anything that lives in the game world: the player, the plane, a bomb, a rocket...
 *
 * Every entity has a position and a hitbox ([x], [y], [width], [height]) and two jobs:
 *  - [update]: move and think, called 60 times a second
 *  - [draw]: paint itself
 *
 * To add a new kind of thing to the game (a boss, a new power-up), make a new
 * class that extends Entity and add it to the [World].
 */
abstract class Entity(
    var x: Float,
    var y: Float,
    val width: Int,
    val height: Int,
) {
    /** Set to false to remove the entity. The world sweeps dead entities away. */
    var alive: Boolean = true

    /** Advances the entity by [dt] seconds. */
    abstract fun update(dt: Float, world: World)

    /** Draws the entity. */
    abstract fun draw(gfx: Gfx)

    /** True if the hitboxes of the two entities overlap. [grow] makes this entity's box bigger on every side. */
    fun overlaps(other: Entity, grow: Int = 0): Boolean =
        x - grow < other.x + other.width &&
            x + width + grow > other.x &&
            y - grow < other.y + other.height &&
            y + height + grow > other.y
}
