package com.example.retroshooter.game

import com.example.retroshooter.audio.Sfx
import com.example.retroshooter.audio.SoundPlayer
import com.example.retroshooter.config.GameConfig
import com.example.retroshooter.config.LevelSettings
import com.example.retroshooter.graphics.Gfx
import java.util.Random

/**
 * The game world: every entity that exists right now, plus the rules for how
 * they affect each other (collisions, spawning, levels).
 *
 * The world knows nothing about Android. It is plain Kotlin, which is why the
 * unit tests can play whole levels on a computer in a fraction of a second.
 */
class World(
    val sound: SoundPlayer,
    private val random: Random = Random(),
) {
    /** Height of the game screen in game pixels. Set by [setScreenHeight]. */
    var screenHeight: Int = GameConfig.SCREEN_HEIGHT_MIN
        private set

    /** The y coordinate where the ground starts. */
    val groundTop: Int get() = screenHeight - GameConfig.GROUND_HEIGHT

    val player = Player()
    var plane: EnemyPlane? = null
        private set
    var drone: Drone? = null
        private set

    val rockets = mutableListOf<Rocket>()
    val bombs = mutableListOf<Bomb>()
    val crates = mutableListOf<Crate>()
    val explosions = mutableListOf<Explosion>()

    var level: Int = 1
        private set

    /** Counts down while the "LEVEL n" banner shows. The plane is frozen until it hits zero. */
    var introSeconds: Float = 0f
        private set

    private var droneTimer = 0f

    init {
        setScreenHeight(screenHeight)
    }

    /** Tells the world how tall the screen is and puts the soldier on the ground. */
    fun setScreenHeight(height: Int) {
        screenHeight = height.coerceIn(GameConfig.SCREEN_HEIGHT_MIN, GameConfig.SCREEN_HEIGHT_MAX)
        player.y = (groundTop - player.height).toFloat()
    }

    /** Resets everything and starts level 1. */
    fun newGame() {
        player.reset()
        crates.clear()
        explosions.clear()
        startLevel(1)
    }

    /** Starts a level: a fresh plane, tougher and faster, and a few extra rockets for the player. */
    fun startLevel(newLevel: Int) {
        level = newLevel
        if (newLevel > 1) {
            player.ammo = minOf(GameConfig.AMMO_MAX, player.ammo + GameConfig.AMMO_PER_LEVEL)
        }
        bombs.clear()
        rockets.clear()
        drone = null
        plane = EnemyPlane(LevelSettings.forLevel(newLevel), random)
        introSeconds = GameConfig.LEVEL_INTRO_SECONDS
        droneTimer = GameConfig.DRONE_FIRST_DELAY
    }

    /** True once the current level's plane has been shot down. */
    val planeDestroyed: Boolean get() = plane?.alive == false

    /** True when the soldier has no lives left. */
    val playerDead: Boolean get() = player.lives <= 0

    /** Advances the whole world by [dt] seconds while the game is being played. */
    fun update(dt: Float) {
        player.update(dt, this)

        // During the "LEVEL n" banner only the soldier and the effects move.
        if (introSeconds > 0f) {
            introSeconds -= dt
            updateQuiet(dt, includePlayer = false)
            return
        }

        plane?.takeIf { it.alive }?.update(dt, this)
        updateDrone(dt)
        for (r in rockets) r.update(dt, this)
        for (b in bombs) b.update(dt, this)
        updateQuiet(dt, includePlayer = false)

        checkCollisions()
        rockets.removeAll { !it.alive }
        bombs.removeAll { !it.alive }
    }

    /**
     * Moves only the harmless things (crates, explosions) and the soldier.
     * Used between levels and on the game over screen.
     */
    fun updateQuiet(dt: Float, includePlayer: Boolean = true) {
        if (includePlayer) player.update(dt, this)
        for (c in crates) c.update(dt, this)
        for (e in explosions) e.update(dt, this)
        crates.removeAll { !it.alive }
        explosions.removeAll { !it.alive }
    }

    private fun updateDrone(dt: Float) {
        drone?.let {
            it.update(dt, this)
            if (!it.alive) drone = null
        }
        droneTimer -= dt
        if (droneTimer <= 0f && drone == null) {
            drone = Drone(
                dropX = 30f + random.nextInt(GameConfig.SCREEN_WIDTH - 60),
                crateType = randomCrateType(),
            )
            droneTimer = GameConfig.DRONE_INTERVAL
        }
    }

    /** Picks a crate type, favouring the ones with a bigger weight in GameConfig. */
    fun randomCrateType(): CrateType {
        val total = CrateType.entries.sumOf { it.weight }
        var roll = random.nextInt(total)
        for (type in CrateType.entries) {
            roll -= type.weight
            if (roll < 0) return type
        }
        return CrateType.AMMO
    }

    private fun checkCollisions() {
        val plane = plane
        // Rockets against the plane.
        if (plane != null && plane.alive) {
            for (r in rockets) {
                if (r.alive && plane.overlaps(r, grow = 1)) {
                    r.alive = false
                    addExplosion(r.x + r.width / 2f, r.y)
                    plane.hit(this)
                    if (!plane.alive) break
                }
            }
        }
        // Bombs against the soldier.
        if (player.alive) {
            for (b in bombs) {
                if (b.alive && b.overlaps(player)) {
                    b.alive = false
                    addExplosion(b.x + b.width / 2f, b.y + b.height / 2f)
                    sound.play(Sfx.EXPLOSION)
                    player.hurt(this)
                }
            }
        }
    }

    /** Spawns an explosion centered on a point. */
    fun addExplosion(centerX: Float, centerY: Float, delay: Float = 0f) {
        explosions.add(Explosion(centerX, centerY, delay))
    }

    /** Called by the plane when it is shot down: a chain of explosions along the wreck. */
    fun onPlaneDestroyed(plane: EnemyPlane) {
        bombs.clear()
        rockets.clear()
        for (i in 0 until 6) {
            addExplosion(
                plane.x + 4f + i * 5f,
                plane.y + 4f + random.nextInt(10),
                delay = i * 0.12f,
            )
        }
        sound.play(Sfx.EXPLOSION)
    }

    /** Fired by a screen tap. */
    fun fireRocket() = player.tryFire(this)

    /** Draws every entity, back to front. */
    fun draw(gfx: Gfx) {
        for (c in crates) c.draw(gfx)
        drone?.draw(gfx)
        plane?.takeIf { it.alive }?.draw(gfx)
        for (b in bombs) b.draw(gfx)
        for (r in rockets) r.draw(gfx)
        player.draw(gfx)
        for (e in explosions) e.draw(gfx)
    }
}
