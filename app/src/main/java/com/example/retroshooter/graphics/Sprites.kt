package com.example.retroshooter.graphics

// Generated from Sprite Lab. Edit by hand or paste a fresh export.

// Palette: one character = one color (0xAARRGGBB). "." is transparent.
val PALETTE: Map<Char, Int> = mapOf(
    'K' to 0xFF15122E.toInt(),  // Outline
    'W' to 0xFFF6F1E6.toInt(),  // White
    'B' to 0xFF2F6FE4.toInt(),  // Plane blue
    'D' to 0xFF1D3F99.toInt(),  // Deep blue
    'L' to 0xFF8FC3FF.toInt(),  // Light blue
    'Y' to 0xFFFFD93B.toInt(),  // Hair yellow
    'y' to 0xFFD9A21B.toInt(),  // Hair shade
    'S' to 0xFFF4BC8E.toInt(),  // Skin
    's' to 0xFFCF8D62.toInt(),  // Skin shade
    'U' to 0xFF4A4F66.toInt(),  // Suit
    'u' to 0xFF70769A.toInt(),  // Suit light
    'R' to 0xFFE63946.toInt(),  // Red
    'O' to 0xFFFF9A1F.toInt(),  // Orange
    'T' to 0xFFA56A3A.toInt(),  // Camo brown
    't' to 0xFF5E3B1F.toInt(),  // Camo dark
    'N' to 0xFFCFA66B.toInt(),  // Camo sand
    'M' to 0xFFA3A9B8.toInt(),  // Metal
    'm' to 0xFF5B6174.toInt(),  // Metal dark
    'g' to 0xFF2C6F3C.toInt(),  // Crate green
    'k' to 0xFF2A2840.toInt()  // Bomb black
)

// Enemy plane - WWII-style blue fighter, faces right. Wing, roundel and tail are all editable. Pilot is drawn on top.
val PLANE = Sprite(
    "RRK.............................",
    "RRRK............................",
    "KRRRK...........................",
    "KRRRRK.................KK.......",
    ".KRRRRKKKKKKKKKKKKKKKKKLLK......",
    "..KRRRBBBBBBBBBBBBBBBBLLLLKKK...",
    "...KKBBBBBBBBBBBBBBBBBBBBBMMMKKK",
    "..KBBBBBBBBBBBBBBBBBBBBBBMMMMMMM",
    ".KKLLLLLLLLLLLLLLLLLLLLLLMMMMMMm",
    "KBBBBDDDDDDDDDDDDDDDDDDDDMMMMMmm",
    "BBBBBBBBDDDDDDDDKKKKKKKKKDMMMKKK",
    "KDDDDDDKKKKKDKKKLLLLLLLLKDDKK...",
    ".KKKKKK..KKKKRRLLLLLLLLK.KK.....",
    "......KKKLLRWWWRLLLLLKK.........",
    "....KKLLLLLLRRRLLKKKK...........",
    "...KBBBBBBKKKKKKK...............",
    "...KKKKKKK....KKK...............",
    ".............KMMMK..............",
    "..............KKK..............."
)

// Propeller - 2 frames. Swap them every few ticks so it spins.
val PROP = listOf(
    Sprite(
        ".K.",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        ".K."
    ),
    Sprite(
        "...",
        "...",
        ".M.",
        ".M.",
        "KMK",
        "KMK",
        "KMK",
        "KMK",
        ".M.",
        ".M.",
        "...",
        "..."
    )
)

// Pilot head - 2 frames: grin and open-mouth laugh. Wobble it in code.
val PILOT_HEAD = listOf(
    Sprite(
        "...yYYYYY...",
        ".yYYYYYYYYK.",
        "yYYYYYYYYYKK",
        ".yYYYYYYSSSK",
        "..yYYYYSKSSK",
        "...yYYYSSSSK",
        "....YYSSSSsK",
        ".....KSKKKsK",
        "......KKKKK."
    ),
    Sprite(
        "..yYYYYYY...",
        "yYYYYYYYYYK.",
        ".yYYYYYYYYKK",
        "yyYYYYYYSSSK",
        ".yYYYYYSKSSK",
        "..yYYYYSSSSK",
        "...YYYSSSSsK",
        ".....KSKRRsK",
        "......KKRRK."
    )
)

// Pilot suit - Grey suit, white collar, red tie. Stays still while the head wobbles.
val PILOT_BODY = Sprite(
    "..KUUWRWUUK.",
    ".KUUUURUUUUK",
    ".KUuUURUUuUK",
    ".KKKKKKKKKK."
)

// Skater soldier - Brown camo, rocket launcher on shoulder, red skateboard.
val SKATER = Sprite(
    "...KKKKKK..KMMK.",
    "..KttttttK.KMmK.",
    "..KtTTTTtK.KMmK.",
    "..KSSSSSSK.KMmK.",
    "..KtKSSKtK.KMmK.",
    "..KttSSttKKKMmK.",
    "...KKttKK..KMmK.",
    "..KTTtTTtTKKMmK.",
    ".KTtTNTTtTTKMmK.",
    ".KTTtTTNtTTSMmK.",
    ".KtTTNTTTtTKKKK.",
    ".KTNTTtTTNTK....",
    "..KTtTTTtTK.....",
    "..KKKKKKKKK.....",
    "..KTTtKKtTTK....",
    "..KTtTK.KTtTK...",
    "..KTNTK.KTTTK...",
    "..KttTK.KtTtK...",
    "..KKKKK.KKKKK...",
    ".KKKKKKKKKKKKKK.",
    "KRRRRRRRRRRRRRRK",
    ".KKKKKKKKKKKKKK.",
    "..KMK......KMK..",
    "..KKK......KKK.."
)

// Rocket - 2 flame frames. Flies straight up.
val ROCKET = listOf(
    Sprite(
        ".K.",
        "KWK",
        "KWK",
        "KRK",
        "KRK",
        "KMK",
        ".O.",
        ".Y."
    ),
    Sprite(
        ".K.",
        "KWK",
        "KWK",
        "KRK",
        "KRK",
        "KMK",
        "OYO",
        ".O."
    )
)

// Bomb - Dropped by the plane. Falls nose-down.
val BOMB = Sprite(
    "K.....K",
    "KK...KK",
    ".KKKKK.",
    ".KkMkkK",
    ".KkMkkK",
    ".KRRRRK",
    ".KkkkkK",
    "..KkkK.",
    "...KK..",
    "...K..."
)

// Supply drone - 2 rotor frames. Drops crates on parachutes.
val DRONE = listOf(
    Sprite(
        "MMMMMM........MMMMMM",
        "..mK............Km..",
        "...KKKKKKKKKKKKKK...",
        "...KMMMMMMMMMMMMK...",
        "..KKmmmmRRRRmmmmKK..",
        "..K.KmmmmmmmmmmK.K..",
        "..K..KKKKKKKKKK..K.."
    ),
    Sprite(
        "..MMMM........MMMM..",
        "..mK............Km..",
        "...KKKKKKKKKKKKKK...",
        "...KMMMMMMMMMMMMK...",
        "..KKmmmmRRRRmmmmKK..",
        "..K.KmmmmmmmmmmK.K..",
        "..K..KKKKKKKKKK..K.."
    )
)

// Parachute - Drawn above a falling crate.
val PARACHUTE = Sprite(
    "..KKKKKKKKK..",
    ".KRRWWRRWWRRK",
    "KRRWWRRRRWWRK",
    ".K..K...K..K.",
    "..K..K.K..K..",
    "...K..K..K...",
    "....K.K.K...."
)

// Crate: +1 life - Heart crate.
val CRATE_LIFE = Sprite(
    "KKKKKKKKKKK",
    "KWWWWWWWWWK",
    "KWRRWWRRWWK",
    "KWRRRRRRRWK",
    "KWRRRRRRRWK",
    "KWWRRRRRWWK",
    "KWWWRRRWWWK",
    "KWWWWRWWWWK",
    "KWWWWWWWWWK",
    "KWWWWWWWWWK",
    "KKKKKKKKKKK"
)

// Crate: rockets - Green ammo crate.
val CRATE_AMMO = Sprite(
    "KKKKKKKKKKK",
    "KgggggggggK",
    "KggggWggggK",
    "KgggWWWgggK",
    "KgggWRWgggK",
    "KgggWWWgggK",
    "KgggWWWgggK",
    "KggMWWWMggK",
    "KgggOYOgggK",
    "KgggggggggK",
    "KKKKKKKKKKK"
)

// Crate: shield - Blue shield crate.
val CRATE_SHIELD = Sprite(
    "KKKKKKKKKKK",
    "KDDDDDDDDDK",
    "KDWWWWWWWDK",
    "KDWBBBBBWDK",
    "KDWBBBBBWDK",
    "KDWBBLBBWDK",
    "KDDWBBBWDDK",
    "KDDDWBWDDDK",
    "KDDDDWDDDDK",
    "KDDDDDDDDDK",
    "KKKKKKKKKKK"
)

// Explosion - 3 frames: flash, blast, smoke.
val EXPLOSION = listOf(
    Sprite(
        "............",
        "............",
        "............",
        "....OOOO....",
        "...OYYYYO...",
        "...OYWWYO...",
        "...OYWWYO...",
        "...OYYYYO...",
        "....OOOO....",
        "............",
        "............",
        "............"
    ),
    Sprite(
        "............",
        "....O..O....",
        "..O.OOOO.O..",
        "...OOYYOO...",
        "..OOYYYYOO..",
        ".OOYYWWYYOO.",
        ".OOYYWWYYOO.",
        "..OOYYYYOO..",
        "...OOYYOO...",
        "..O.OOOO.O..",
        "....O..O....",
        "............"
    ),
    Sprite(
        "............",
        "............",
        "..M....M....",
        "....M.O.....",
        ".M....M..M..",
        "...O.....O..",
        "..M..M......",
        "....O...M...",
        ".M.....M....",
        "......M..M..",
        "............",
        "............"
    )
)

// HUD heart - Shows remaining lives.
val HEART = Sprite(
    ".RR.RR.",
    "RRRRRRR",
    "RRRRRRR",
    ".RRRRR.",
    "..RRR..",
    "...R..."
)

// Icon: music on - Music button when the music is playing.
val ICON_MUSIC_ON = Sprite(
    "....KKKK.",
    "....KWWWK",
    "....KWKKK",
    "....KWK..",
    "....KWK..",
    "..KKKWK..",
    ".KWWWWK..",
    ".KWWWWK..",
    "..KKKK..."
)

// Icon: music off - Music button when the music is muted.
val ICON_MUSIC_OFF = Sprite(
    "R...KKKK.",
    ".R..KmmmK",
    "..R.KmKKK",
    "...RKmK..",
    "....RmK..",
    "..KKKRK..",
    ".KmmmmR..",
    ".KmmmmKR.",
    "..KKKK..R"
)

// Shield bubble - Dotted bubble drawn around the soldier while the shield is active.
val SHIELD_BUBBLE = Sprite(
    "..........L.L.L.........",
    ".......L.L.L.L.L........",
    "......L.........L.L.....",
    ".....L.............L....",
    "....L...............L...",
    "...L....................",
    "..L.................L...",
    ".....................L..",
    "..L...................L.",
    ".L......................",
    "......................L.",
    ".L.....................L",
    "L.......................",
    ".......................L",
    "L.......................",
    ".......................L",
    "L.......................",
    ".......................L",
    "L.....................L.",
    ".L......................",
    "......................L.",
    ".L...................L..",
    "..L.....................",
    "...L.................L..",
    "....................L...",
    "...L...............L....",
    "....L.............L.....",
    ".....L.L.........L......",
    "........L.L.L.L.L.......",
    ".........L.L.L.........."
)
