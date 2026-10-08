# Game Plan

Working plan for the retro shooting game demo. Updated as decisions are made.

**Designed and directed by:** Aziz
**Developed by:** Claude (Anthropic)

## Concept

An 80s pixel-art Android game. Blue enemy prop plane versus a skateboarding soldier. Whoever runs out of life first loses.

- **Enemy plane** (blue) crosses the top of the screen, left to right, dropping bombs. A laughing pilot in a grey suit with yellow hair sits in the cockpit, head wobbling.
- **Player** is a bearded soldier in brown camo on a skateboard, with a handheld rocket launcher. Moves by tilting the phone. Taps to fire.
- **Rockets** are limited. The player does not have infinite ammo.
- **Lives:** player has 3 lives. Plane has its own HP. If the player runs out of lives, the game is over. If the plane's HP hits zero, the next level starts.
- **Supply drone** flies by periodically and drops parachute crates: +1 life, rockets, or shield. The player collects them by catching them.
- **Background** follows the phone's clock: dawn (05-08), day (08-17), evening (17-20), night (otherwise).
- **Levels instead of score:** no score. Progress is the level reached. Destroying the plane starts the next level, and everything gets faster each level (plane, bombs, drone). Level 10 is the last: beat it and the player wins. Game over screen shows the level reached.
- **Sound:** retro "old school MIDI" style chiptune effects and background music, all generated in code. A button switches the music on and off.
- **No saved games** for now. Possible later feature.

## Decisions so far

| Topic | Decision |
|---|---|
| Platform | Native Android, Kotlin, opened and run from Android Studio |
| Rendering | Custom `SurfaceView` game loop, no game engine, low internal resolution scaled up with no smoothing |
| Input | Accelerometer tilt, with dead zone and smoothing. Tested on a real phone |
| Art | Sprites defined in code as character grids, so they are editable. Designed in `tools/sprite-lab.html` |
| Code style | Heavily commented and modular, written for students to learn from |
| Sound | Chiptune effects and looping music generated in code with `AudioTrack`, no audio files. Music on/off button. A Sound Lab page may be built to preview the sounds first |
| Saved games | Skipped for now |
| Progress | 10 levels, speed rises each level, no score. Level 10 ends the game with a win |
| Docs | README with architecture, how to play, and credits (Aziz as designer/director, Claude as developer) written at the end |

## Sprites

23 sprites, listed in `tools/sprite-lab.html`: enemy plane, propeller (2 frames), pilot head (2 frames), pilot suit, skater soldier, rocket (2 frames), bomb, supply drone (2 frames), parachute, three crates, explosion (3 frames), HUD heart, music icons, shield bubble, plus the splash screen art: victory soldier, victory missile, losing pilot (head, suit, hands) and the mahogany desk.

The Sprite Lab exports Kotlin (`Sprites.kt`) so edits to the characters drop straight into the game. Screenshots of the tool are in `docs/screenshots/` and will be used in the student handouts.

## Settings file (developer only)

A single Kotlin file, `GameConfig.kt`, holds every tuning value. The player never sees it. The developer changes a value, rebuilds, and the game behaves differently. Every value gets a comment saying what it does.

Planned settings:

- **Drone:** how often it appears, which crate types it can carry, crate fall speed and how much crates sway (grows with the level).
- **Bombs:** how often the plane drops them, fall speed, how many are aimed at the soldier (all grow with the level).
- **Difficulty:** difficulty level (easy / normal / hard), number of levels (10), and how much faster each level gets (the speed curve and its maximum).
- **Player:** starting lives, starting rockets, tilt sensitivity, tilt dead zone.
- **Plane:** starting HP, flight speed.
- **Time of day:** hour thresholds for dawn, day, evening and night.

Details to settle when we start coding. Anything that feels like a "magic number" in the game goes here instead.

## Built to grow

Aziz wants to add features later (saved games, a boss, more). The code is organised so these slot in without rewrites:

- **Entities:** plane, drone, bomb, rocket, crate and player all share one `Entity` base (update, draw, hitbox). A new enemy or boss is a new class.
- **Game states:** Title, Playing, Paused and GameOver are separate states. A save/resume screen or a boss intro becomes a new state.
- **Data-driven spawning:** what spawns and when comes from lists in `GameConfig.kt`, not hard-coded logic. A boss is a new entry.
- **Registries:** sprites, sounds and crate types are looked up by name. Adding one means adding one line.
- **Levels** are described by a table in `GameConfig.kt` (speeds, bomb rate, plane HP). A boss level or an upgraded aircraft is a new row or a new field.

Candidate later features: a boss on some levels, upgraded aircraft per level, saved games, new crate types, more enemy types.

## Screenshots

- `docs/screenshots/sprite-lab-1-scene-and-sprites.png`: live scene, sprite shelf, editor.
- `docs/screenshots/sprite-lab-2-palette-and-export.png`: palette and Kotlin export.
- `docs/screenshots/sprite-lab-3-evening-scene-new-plane.png`: evening scene with the WWII-style plane.

## Build setup (compatibility)

| Item | Value | Why |
|---|---|---|
| Android Gradle Plugin | 8.13.2 | Works in Android Studio Otter 2 (supports AGP up to 8.13) and Rabbit 1 (supports AGP 7.1 to 9.4) |
| Gradle | 8.13 (wrapper included) | Required by AGP 8.13 |
| Kotlin | 2.3.21 | Compatible with AGP 8.13 |
| compileSdk / targetSdk | 35 | One step behind the newest release, as requested |
| minSdk | 26 (Android 8.0) | Runs on old phones, and adaptive icons (the pixel-art logo) start here |
| Java | 17 | Needed by AGP 8.13. Android Studio's bundled JDK is fine |
| Libraries | None | Plain Kotlin and the Android framework only, so students can read everything |

## Code layout

`app/src/main/java/com/example/retroshooter/`

- `MainActivity.kt`: connects the pieces, full-screen, lifecycle.
- `config/`: `GameConfig.kt` (all developer settings) and `Levels.kt` (per-level numbers).
- `graphics/`: `Sprites.kt` (generated by the Sprite Lab), `Sprite`, `Gfx` and `AndroidGfx`, `PixelFont`, `Background`, `TimeOfDay`.
- `game/`: `Game` (screens and rules), `World` (entities and collisions), `Entity` and its subclasses (`Player`, `EnemyPlane`, `Bomb`, `Rocket`, `Drone`, `Crate`, `Explosion`), `Hud`, `VictoryScreen`, `GameOverScreen`.
- `audio/`: `SoundSynth` (the chip), `SoundEffects` (recipes), `Music`, `AudioEngine`, `SoundPlayer`.
- `input/`: `TiltSensor`, `TiltFilter`.
- `engine/`: `GameView` (draws and takes touches) and `GameThread` (the loop).

The `game/`, `audio/` synth code and `graphics/` data are plain Kotlin with no Android calls, so `app/src/test/` plays the whole game on a computer.

## Status

- First playable version written. It builds, passes its 34 unit tests and lint shows only intentional warnings.
- Not yet run on a real phone. Tilt direction, rocket feel and difficulty need Aziz's hands-on test. Tilt direction is flipped with `TILT_INVERT` in `GameConfig.kt` if needed.
- Title text is a placeholder (`GAME_TITLE` in `GameConfig.kt`).
- App icon: Aziz's logo redrawn as pixel art. Edit the grid in `tools/make_icon.py` and run it to regenerate the icon files.
- After the first phone test: title and message text now sit in black boxes, and levels after level 1 ramp up harder (faster plane, more and more aimed bombs, crates that sway more).
- Win and lose splash screens are built into the game (designed in the Sprite Lab first):
  - Win (`VictoryScreen.kt`): close-up soldier in a skateboard crouch, soda in one hand, selfie stick pointing at the viewer in the other, missiles launching behind him.
  - Game over (`GameOverScreen.kt`): the pilot behind a mahogany desk in front of a three-panel window, with a comic bubble saying "YOU'RE A LOSER."
  - Quick way to see them: in `GameConfig.kt` set `LEVEL_COUNT = 1` (win after one level) or `LIVES_START = 1` (lose fast).

## Next steps

1. Aziz tests on his phone and gives feedback.
2. Tune `GameConfig.kt` values.
3. Write the README (architecture, how to play, credits: Aziz as designer/director, Claude as developer).
4. Student handouts, using the saved screenshots (swap in fresh ones with the new plane).
