# Game Plan

Working plan for the retro shooting game demo. Updated as decisions are made.

**Designed and directed by:** Aziz
**Developed by:** Claude (Anthropic)

## Concept

An 80s pixel-art Android game. Blue enemy prop plane versus a skateboarding soldier. Whoever runs out of life first loses.

- **Enemy plane** (blue) crosses the top of the screen, left to right, dropping bombs. A laughing pilot in a grey suit with yellow hair sits in the cockpit, head wobbling.
- **Player** is a bearded soldier in brown camo on a skateboard, with a handheld rocket launcher. Moves by tilting the phone. Taps to fire.
- **Rockets** are limited. The player does not have infinite ammo.
- **Lives:** player has 3 lives. Plane has its own HP. Whoever hits zero first loses.
- **Supply drone** flies by periodically and drops parachute crates: +1 life, rockets, or shield. The player collects them by catching them.
- **Background** follows the phone's clock: dawn (05-08), day (08-17), evening (17-20), night (otherwise).
- **Saves:** high score and stats first. Mid-match save/resume is a stretch goal.

## Decisions so far

| Topic | Decision |
|---|---|
| Platform | Native Android, Kotlin, opened and run from Android Studio |
| Rendering | Custom `SurfaceView` game loop, no game engine, low internal resolution scaled up with no smoothing |
| Input | Accelerometer tilt, with dead zone and smoothing. Tested on a real phone |
| Art | Sprites defined in code as character grids, so they are editable. Designed in `tools/sprite-lab.html` |
| Code style | Heavily commented and modular, written for students to learn from |
| Sound | Generated in code, no audio files |
| Docs | README with architecture, how to play, and credits (Aziz as designer/director, Claude as developer) written at the end |

## Sprites

14 sprites, listed in `tools/sprite-lab.html`: enemy plane, propeller (2 frames), pilot head (2 frames), pilot suit, skater soldier, rocket (2 frames), bomb, supply drone (2 frames), parachute, three crates, explosion (3 frames), HUD heart.

The Sprite Lab exports Kotlin (`Sprites.kt`) so edits to the characters drop straight into the game. Screenshots of the tool are in `docs/screenshots/` and will be used in the student handouts.

## Settings file (developer only)

A single Kotlin file, `GameConfig.kt`, holds every tuning value. The player never sees it. The developer changes a value, rebuilds, and the game behaves differently. Every value gets a comment saying what it does.

Planned settings:

- **Drone:** how often it appears, which crate types it can carry, crate fall speed.
- **Bombs:** how often the plane drops them, fall speed.
- **Difficulty:** difficulty level (easy / normal / hard) and how fast difficulty ramps up during a match (acceleration).
- **Player:** starting lives, starting rockets, tilt sensitivity, tilt dead zone.
- **Plane:** starting HP, flight speed.
- **Time of day:** hour thresholds for dawn, day, evening and night.

Details to settle when we start coding. Anything that feels like a "magic number" in the game goes here instead.

## Screenshots

- `docs/screenshots/sprite-lab-1-scene-and-sprites.png`: live scene, sprite shelf, editor.
- `docs/screenshots/sprite-lab-2-palette-and-export.png`: palette and Kotlin export.

## Next steps

1. Aziz approves the characters and plan.
2. Create the Android Studio project (Kotlin).
3. Build the game in small modules: sprites, game loop, input, entities, collisions, HUD, config, saves.
4. Test on Aziz's phone.
5. Write the README.
6. Student handouts, using the saved screenshots.
