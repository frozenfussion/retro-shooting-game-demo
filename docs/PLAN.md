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

14 sprites, listed in `tools/sprite-lab.html`: enemy plane, propeller (2 frames), pilot head (2 frames), pilot suit, skater soldier, rocket (2 frames), bomb, supply drone (2 frames), parachute, three crates, explosion (3 frames), HUD heart.

The Sprite Lab exports Kotlin (`Sprites.kt`) so edits to the characters drop straight into the game. Screenshots of the tool are in `docs/screenshots/` and will be used in the student handouts.

## Settings file (developer only)

A single Kotlin file, `GameConfig.kt`, holds every tuning value. The player never sees it. The developer changes a value, rebuilds, and the game behaves differently. Every value gets a comment saying what it does.

Planned settings:

- **Drone:** how often it appears, which crate types it can carry, crate fall speed.
- **Bombs:** how often the plane drops them, fall speed.
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

## Next steps

1. Aziz approves the characters and plan.
2. Create the Android Studio project (Kotlin).
3. Build the game in small modules: sprites, game loop, input, entities, collisions, HUD, config, sound, levels.
4. Test on Aziz's phone.
5. Write the README.
6. Student handouts, using the saved screenshots.
