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
    'k' to 0xFF2A2840.toInt(),  // Bomb black
    'b' to 0xFF22202B.toInt(),  // Hair and beard black
    'h' to 0xFF5A5870.toInt(),  // Hair shine
    'j' to 0xFFFFF0A0.toInt(),  // Blond shine
    'p' to 0xFF34323F.toInt(),  // Suit black
    'r' to 0xFFA82A35.toInt(),  // Tie shade
    'a' to 0xFF3A1410.toInt(),  // Mahogany darkest
    'c' to 0xFF5C2218.toInt(),  // Mahogany dark
    'd' to 0xFF7A2E1F.toInt(),  // Mahogany
    'e' to 0xFFA8503A.toInt()  // Mahogany light
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

// Victory soldier - Win screen close-up: bent-over skateboard crouch, black beard, side-parted hair, soda in one hand, other fist gripping a selfie stick. The stick points at the viewer (first-person selfie view): the scene draws it from the fist down to the bottom of the screen, thicker as it comes closer. 2 frames (wink and fizz).
val WIN_SOLDIER = listOf(
    Sprite(
        "................................K....KKKK...................................",
        ".............................KKKsKKKKbbbbKKK................................",
        ".......................KK..KKbbsbbbbbbbbbbbbK...............................",
        "......................KhhKKbbbbsbhhhbbbbbbbbbK..............................",
        ".......................KKhhbbbsbbbbbhhhbbbbbbK..............................",
        ".......................KbbbhbbsbbbbbbbbhhhbbbK..............................",
        ".......................KbbbbbshbhbhbhbbbbbbbK...............................",
        "......................KbbbbbbsbbbbbbbbhbhbhbhK..............................",
        "......................KbbbbbsbbbbbbbbbbbbbbbbK..............................",
        "......................KbbKKKsbbbbbbbbbbbbbbbbbK.............................",
        "......................KbbKSSKbbbbbbbbbbbbbbbbbK.............................",
        "...........K...K......KbbKSSSKKKKbbbbbbbbbbKbbK.............................",
        "..........KLKKKLKK....KbbKSSSSbbSKKKKbbbbbKKbbK.............................",
        "...........KKLKKKLK...KbKSSbbbSSSSSSSKKKKKSKbK.......KKKK...................",
        "..........KRRRRRKK....KbKSSSSSSSSSSSSSSSSSSKbK.....KKSSSSKK.................",
        "..........KRRRRRK.....KbKSSSWKWSSSSSSSWKWSSKbK....KSSSSSSSSK................",
        "...........KtttK......KbKSSSWKWSSSSSSSWKWSSKbK...KSSSSSSSSSSK...............",
        "...........KLttK.......KSSSSSSSSSSSSSSSSSSSSK....KSSSSSSSSSSK...............",
        "...........KLttK.......KSSSSSSSSSSsSSSSSSSSSK....KSSSSSSSSSSK...............",
        "..........KtttttK......KbbbSSSSSSSsSSSSSSSbbK....KSSSSSSSSSSK...............",
        ".........KtttttttK.....KbbbSbbbbbbbbbbbbbSbbK....KSSSSSSSSSSK...............",
        "........KtLNttttttK....KbbbSbbbbSSSSSbbbbSbbK.....KSSSSSSSSK................",
        "........KtLNttttttK.....KbbbbKKKKKKKKKKKbbbK.......KKSSSSKK.................",
        "........KtLtttttttK.....KbbbbKWWWWWWWWWKbbbK......KttKKKKTNK................",
        "........KRRRRRRRRRK......KbbbWKKRRRRRKKWbbK.......KttttTTNNK................",
        "........KRRRRRRRRRK.......KbbbbbbbbbbbbbbK........KTttTTTNNK................",
        "........KWWWRRRWWWK........KKbbbbbbbbbbKK.........KTTTTTNNNNK...............",
        "........KRWWWWWWWRK..........KKKKKKKKKK...........KTTTTTNNNNK...............",
        "........KRRRRRRRRRK..........KssssssssK...........KTTTTTNNNNK...............",
        "........KRRRRRRRRRK..........KssssssssK......K...KKTTTTTTTTTK...............",
        "........KtLSSSStttK...........KssssssssKKKKKKTKKKTTTTTTTTTTTK...............",
        "........KSSSSSSSStK..KKKK.....KssssssssKNTTTKKTTTTTTTTTTTTTTK...............",
        ".......KSSSSSSSSSSKKKTTTTK....KssssssssKNTTKTTTTTTTTTTTTTTTTTK..............",
        ".......KSSsssssssSKNNTTttKKKKKKssssssssKNTTKtTTTTTTTTTTTTTTTTK..............",
        ".......KSSSSSSSSSSKNKKKKKttTTTKssssssssKTTKtttTTTTTTTTTTTTTTK...............",
        ".......KSSSSSSSSSSKKTTtttttTTTTKssssssKTTTKtttTTTTTTTTTTTTTTK...............",
        ".......KSSSSSSSSSSKTTtttttTTTNNTKssssKTTTtKtttTTTTTTTTTTTTTK................",
        "........KSSSSSSSStKTTttttTTTNNNNTKKKKTTTttKttTTTNNNTTTTTTKK.................",
        "........KttSSSStttKTTttttTTNNNNNTTTTTTTTtttKtTTNNNNTTTTKK...................",
        ".........KKKKKKKKKTTTtttTTNNNNNTTTTTTTTttttKTTNNNNNTTKK.....................",
        "........KTTTTTTTKTTTTTTTTTNNNNNTTtttttTTTTTTKKNNNNKKK.......................",
        "........KTTTTTTTKTTTTTTTTTNNNNTTtttttTTTTTTTTNKKKK..........................",
        "........KTTTTTTTKTTTTTTTTTNNNTTttttttTTTTTTTTNNNNK..........................",
        "........KTTTTTTKTTTTTTTTTTTTTTTtttttTTTNNNTTTTNTK...........................",
        ".......KNNTTTTTKTTTTTTTTTTTTTTTttttTTNNNNNTTTTTTK...........................",
        ".......KNNNTTTTKTTTTTTTTTTTTTTTttttTTNNNNNTTTTTK............................",
        ".......KNNNTTTTKTTTTTTTTTTTTTTTTtTTTNNNNNTTTtttK............................",
        ".......KNNTTTttKTTTTTTTTTTTTTTTTTTTTNNNNNTTtttK.............................",
        ".......KNNTTtttKTTTTTTTTTTTTTTTTTTTTNNNNTTtttK..............................",
        ".......KNTTttttKTTTTTTTTTTTTTTTTTTTTNNNTTttttK..............................",
        ".......KTTtttttKTTTTTTTTTTTTTTTTTTTTTTTTTtttK...............................",
        "........KTtttttKTNNNTTTTTTTTTTTTTTTTTTTTTtttK...............................",
        ".........KKKKKKKTNNNNTTTTTTTTTTTTTTTTTTTTttK................................",
        "...............KNNNNNTTTTTTTTTTTTTTTTTTTTTtK................................",
        "...............KNNNNTTTttTTTTTTTTTTTTTTTTTK.................................",
        "...............KNNNNTTttttTTTTTTTTTTTTTTTTK.................................",
        "...............KNNNTTtttttTTTTTTTTTTTTTTTK..................................",
        "..............KTTTTTtttttTTKKKKKKKKKKKTTKTKK................................",
        "..............KTKKKKKKKKKKKKKmmmmmmmmmKTKTTTKK..............................",
        ".............KKKmmmmmmmKMMMMMKmmmmmmmmKKTTTTTTK.............................",
        "............KtKKmmmmmmmKMMMMMKmmmmmmmmKKTTTTTTTKK...........................",
        "...........KttKKmmmmmmmKMMMMMKmmmmmmmKKTTTTTTTTTTKK.........................",
        "..........KtttKmmmmmmmmKMMMMMKmmmmmmmKTTTTTTTTTTTTTKK.......................",
        ".........KTtttKmmmmmmmmmKKKKKmmmmmmmmKTTTTTTTTTTTTTTTK......................",
        "........KTttttKmmmmmmmmmmmK..KKKKKKKKTNNTTTTTTTTTTTTTK......................",
        ".......KTTtttttKKKKKKKKKKK.....KtttTTNNNTTTTTTTTTTTTTTK.....................",
        ".......KTTTtttTTNNNNNTTK........KKTTNNNNNTTTTTTTTTTTTTK.....................",
        "......KTTTTTTTTTNNNNNTK...........KKKNNNTTTttTTTTTTTTTK.....................",
        "......KTTTTTTTTNNNNNTK...............KKNTTtttTTTTTTTTtK.....................",
        "......KTTTTTTTTNNNNTK..................KKKttttTTTTTTTK......................",
        "......KTTTTTTTTTNNKK.....................KtttTTTTTTTTK......................",
        ".......KTTTTTTTTTTK......................KtttTTTNNTTK.......................",
        ".......KTTTTTTTTTTTK...................KKKKKKKKKKKTK........................",
        "........KTTKKKKKKKKKK.................KmmmmmmmmmmmK.........................",
        ".........KKmmmmmmmmmmK................KmmmmmmmmmmmK.........................",
        "..........KmmmmmmmmmmK................KmmmmmmmmmmmK.........................",
        "..........KmmmmmmmmmmK................KmmmmmmmmmmmKKKKKKKKKKKKKKKK..........",
        "..........KmmmmmmmmmmKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKRRRRRRRRRRRRRRRK.........",
        "........KKKKKKKKKKKKKKRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRK.........",
        ".......KRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRK.........",
        ".......KRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRKKKKKKKKKKKKKKK..........",
        ".......KRRRRRRRRRRRRRRKKKKKKKKKKKKKKKKKKKKKKKKKKKKK.........................",
        "........KKKKKKKKKKKKKK......................................................"
    ),
    Sprite(
        "................................K....KKKK...................................",
        ".............................KKKsKKKKbbbbKKK................................",
        ".......................KK..KKbbsbbbbbbbbbbbbK...............................",
        "......................KhhKKbbbbsbhhhbbbbbbbbbK..............................",
        ".......................KKhhbbbsbbbbbhhhbbbbbbK..............................",
        ".......................KbbbhbbsbbbbbbbbhhhbbbK..............................",
        ".......................KbbbbbshbhbhbhbbbbbbbK...............................",
        "......................KbbbbbbsbbbbbbbbhbhbhbhK..............................",
        "......................KbbbbbsbbbbbbbbbbbbbbbbK..............................",
        "......................KbbKKKsbbbbbbbbbbbbbbbbbK.............................",
        "......................KbbKSSKbbbbbbbbbbbbbbbbbK.............................",
        ".............K..K.....KbbKSSSKKKKbbbbbbbbbbKbbK.............................",
        "..........K.KLKKLK....KbbKSSSSbbSKKKKbbbbbKKbbK.............................",
        ".........KLKLKLKK.....KbKSSbbbSSSSSSSKKKKKSKbK.......KKKK...................",
        "..........KRRRRRK.....KbKSSSSSSSSSSSSSSSSSSKbK.....KKSSSSKK.................",
        "..........KRRRRRK.....KbKSSSSSSSSSSSSSWKWSSKbK....KSSSSSSSSK................",
        "...........KtttK......KbKSSSbbbSSSSSSSWKWSSKbK...KSSSSSSSSSSK...............",
        "...........KLttK.......KSSSSSSSSSSSSSSSSSSSSK....KSSSSSSSSSSK...............",
        "...........KLttK.......KSSSSSSSSSSsSSSSSSSSSK....KSSSSSSSSSSK...............",
        "..........KtttttK......KbbbSSSSSSSsSSSSSSSbbK....KSSSSSSSSSSK...............",
        ".........KtttttttK.....KbbbSbbbbbbbbbbbbbSbbK....KSSSSSSSSSSK...............",
        "........KtLNttttttK....KbbbSbbbbSSSSSbbbbSbbK.....KSSSSSSSSK................",
        "........KtLNttttttK.....KbbbbKKKKKKKKKKKbbbK.......KKSSSSKK.................",
        "........KtLtttttttK.....KbbbbKWWWWWWWWWKbbbK......KttKKKKTNK................",
        "........KRRRRRRRRRK......KbbbWKKRRRRRKKWbbK.......KttttTTNNK................",
        "........KRRRRRRRRRK.......KbbbbbbbbbbbbbbK........KTttTTTNNK................",
        "........KWWWRRRWWWK........KKbbbbbbbbbbKK.........KTTTTTNNNNK...............",
        "........KRWWWWWWWRK..........KKKKKKKKKK...........KTTTTTNNNNK...............",
        "........KRRRRRRRRRK..........KssssssssK...........KTTTTTNNNNK...............",
        "........KRRRRRRRRRK..........KssssssssK......K...KKTTTTTTTTTK...............",
        "........KtLSSSStttK...........KssssssssKKKKKKTKKKTTTTTTTTTTTK...............",
        "........KSSSSSSSStK..KKKK.....KssssssssKNTTTKKTTTTTTTTTTTTTTK...............",
        ".......KSSSSSSSSSSKKKTTTTK....KssssssssKNTTKTTTTTTTTTTTTTTTTTK..............",
        ".......KSSsssssssSKNNTTttKKKKKKssssssssKNTTKtTTTTTTTTTTTTTTTTK..............",
        ".......KSSSSSSSSSSKNKKKKKttTTTKssssssssKTTKtttTTTTTTTTTTTTTTK...............",
        ".......KSSSSSSSSSSKKTTtttttTTTTKssssssKTTTKtttTTTTTTTTTTTTTTK...............",
        ".......KSSSSSSSSSSKTTtttttTTTNNTKssssKTTTtKtttTTTTTTTTTTTTTK................",
        "........KSSSSSSSStKTTttttTTTNNNNTKKKKTTTttKttTTTNNNTTTTTTKK.................",
        "........KttSSSStttKTTttttTTNNNNNTTTTTTTTtttKtTTNNNNTTTTKK...................",
        ".........KKKKKKKKKTTTtttTTNNNNNTTTTTTTTttttKTTNNNNNTTKK.....................",
        "........KTTTTTTTKTTTTTTTTTNNNNNTTtttttTTTTTTKKNNNNKKK.......................",
        "........KTTTTTTTKTTTTTTTTTNNNNTTtttttTTTTTTTTNKKKK..........................",
        "........KTTTTTTTKTTTTTTTTTNNNTTttttttTTTTTTTTNNNNK..........................",
        "........KTTTTTTKTTTTTTTTTTTTTTTtttttTTTNNNTTTTNTK...........................",
        ".......KNNTTTTTKTTTTTTTTTTTTTTTttttTTNNNNNTTTTTTK...........................",
        ".......KNNNTTTTKTTTTTTTTTTTTTTTttttTTNNNNNTTTTTK............................",
        ".......KNNNTTTTKTTTTTTTTTTTTTTTTtTTTNNNNNTTTtttK............................",
        ".......KNNTTTttKTTTTTTTTTTTTTTTTTTTTNNNNNTTtttK.............................",
        ".......KNNTTtttKTTTTTTTTTTTTTTTTTTTTNNNNTTtttK..............................",
        ".......KNTTttttKTTTTTTTTTTTTTTTTTTTTNNNTTttttK..............................",
        ".......KTTtttttKTTTTTTTTTTTTTTTTTTTTTTTTTtttK...............................",
        "........KTtttttKTNNNTTTTTTTTTTTTTTTTTTTTTtttK...............................",
        ".........KKKKKKKTNNNNTTTTTTTTTTTTTTTTTTTTttK................................",
        "...............KNNNNNTTTTTTTTTTTTTTTTTTTTTtK................................",
        "...............KNNNNTTTttTTTTTTTTTTTTTTTTTK.................................",
        "...............KNNNNTTttttTTTTTTTTTTTTTTTTK.................................",
        "...............KNNNTTtttttTTTTTTTTTTTTTTTK..................................",
        "..............KTTTTTtttttTTKKKKKKKKKKKTTKTKK................................",
        "..............KTKKKKKKKKKKKKKmmmmmmmmmKTKTTTKK..............................",
        ".............KKKmmmmmmmKMMMMMKmmmmmmmmKKTTTTTTK.............................",
        "............KtKKmmmmmmmKMMMMMKmmmmmmmmKKTTTTTTTKK...........................",
        "...........KttKKmmmmmmmKMMMMMKmmmmmmmKKTTTTTTTTTTKK.........................",
        "..........KtttKmmmmmmmmKMMMMMKmmmmmmmKTTTTTTTTTTTTTKK.......................",
        ".........KTtttKmmmmmmmmmKKKKKmmmmmmmmKTTTTTTTTTTTTTTTK......................",
        "........KTttttKmmmmmmmmmmmK..KKKKKKKKTNNTTTTTTTTTTTTTK......................",
        ".......KTTtttttKKKKKKKKKKK.....KtttTTNNNTTTTTTTTTTTTTTK.....................",
        ".......KTTTtttTTNNNNNTTK........KKTTNNNNNTTTTTTTTTTTTTK.....................",
        "......KTTTTTTTTTNNNNNTK...........KKKNNNTTTttTTTTTTTTTK.....................",
        "......KTTTTTTTTNNNNNTK...............KKNTTtttTTTTTTTTtK.....................",
        "......KTTTTTTTTNNNNTK..................KKKttttTTTTTTTK......................",
        "......KTTTTTTTTTNNKK.....................KtttTTTTTTTTK......................",
        ".......KTTTTTTTTTTK......................KtttTTTNNTTK.......................",
        ".......KTTTTTTTTTTTK...................KKKKKKKKKKKTK........................",
        "........KTTKKKKKKKKKK.................KmmmmmmmmmmmK.........................",
        ".........KKmmmmmmmmmmK................KmmmmmmmmmmmK.........................",
        "..........KmmmmmmmmmmK................KmmmmmmmmmmmK.........................",
        "..........KmmmmmmmmmmK................KmmmmmmmmmmmKKKKKKKKKKKKKKKK..........",
        "..........KmmmmmmmmmmKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKRRRRRRRRRRRRRRRK.........",
        "........KKKKKKKKKKKKKKRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRK.........",
        ".......KRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRK.........",
        ".......KRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRKKKKKKKKKKKKKKK..........",
        ".......KRRRRRRRRRRRRRRKKKKKKKKKKKKKKKKKKKKKKKKKKKKK.........................",
        "........KKKKKKKKKKKKKK......................................................"
    )
)
val WIN_SOLDIER_GRIP = Pair(55, 18)

// Victory missile - Launching in the background of the win screen. 2 flame frames.
val MISSILE_BIG = listOf(
    Sprite(
        "...K...",
        "..KRK..",
        "..KRK..",
        ".KRRRK.",
        ".KWWWK.",
        ".KWWWK.",
        ".KRRRK.",
        ".KWWWK.",
        ".KWWWK.",
        "KKWWWKK",
        "KRKmKRK",
        ".KKmKK.",
        "..OOO..",
        "..OYO..",
        "..OYO..",
        "...O...",
        "...Y...",
        "......."
    ),
    Sprite(
        "...K...",
        "..KRK..",
        "..KRK..",
        ".KRRRK.",
        ".KWWWK.",
        ".KWWWK.",
        ".KRRRK.",
        ".KWWWK.",
        ".KWWWK.",
        "KKWWWKK",
        "KRKmKRK",
        ".KKmKK.",
        "..OOO..",
        ".OOYOO.",
        "..OYO..",
        "..OYO..",
        "...O...",
        "...Y..."
    )
)

// Losing pilot head - Game over screen: the pilot with the same face as the soldier, no beard, yellow side-parted hair. 2 frames (grin and laugh). Wobbles in code.
val LOSE_PILOT_HEAD = listOf(
    Sprite(
        "...................K....KKKK............",
        "................KKKsKKKKYYYYKKK.........",
        "..........KK..KKYYsYYYYYYYYYYYYK........",
        ".........KjjKKYYYYsYjjjYYYYYYYYYK.......",
        "..........KKjjYYYsYYYYYjjjYYYYYYK.......",
        "..........KYYYjYYsYYYYYYYYjjjYYYK.......",
        "..........KYYYYYsyYyYyYyYYYYYYYK........",
        ".........KYYYYYYsYYYYYYYYyYyYyYyK.......",
        ".........KYYYYYsYYYYYYYYYYYYYYYYK.......",
        ".........KYYKKKsYYYYYYYYYYYYYYYYYK......",
        ".........KYYKSSKYYYYYYYYYYYYYYYYYK......",
        ".........KYYKSSSKKKKYYYYYYYYYYKYYK......",
        ".........KYYKSSSSyySKKKKyyYYYKKYYK......",
        ".........KYKSSyyySSSSSSSKKKKKSKYK.......",
        ".........KYKSSSSSSSSSSSSSSSSSSKYK.......",
        ".........KYKSSSWKWSSSSSSSWKWSSKYK.......",
        ".........KYKSSSWKWSSSSSSSWKWSSKYK.......",
        "..........KSSSSSSSSSSSSSSSSSSSSK........",
        "..........KSSSSSSSSSSsSSSSSSSSSK........",
        "..........KSSSSSSSSSSsSSSSSSSSSK........",
        "..........KSSSSSSSSSsSsSSSSSSSSK........",
        "...........KSSSSSSSSSSSSSSSSSSK.........",
        "...........KSSSSKKKKKKKKKKKSSSK.........",
        "............KssSKWWWWWWWWWKSSK..........",
        ".............KsSWKKRRRRRKKWSK...........",
        "..............KSSSSSSSSSSSSK............",
        "...............KKssssssssKK.............",
        ".................KKKKKKKK...............",
        "................KssssssssK..............",
        "................KssssssssK..............",
        "................KsssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        "..................KssssssK..............",
        "...................KssssK..............."
    ),
    Sprite(
        "...................K....KKKK............",
        "................KKKsKKKKYYYYKKK.........",
        "..........KK..KKYYsYYYYYYYYYYYYK........",
        ".........KjjKKYYYYsYjjjYYYYYYYYYK.......",
        "..........KKjjYYYsYYYYYjjjYYYYYYK.......",
        "..........KYYYjYYsYYYYYYYYjjjYYYK.......",
        "..........KYYYYYsyYyYyYyYYYYYYYK........",
        ".........KYYYYYYsYYYYYYYYyYyYyYyK.......",
        ".........KYYYYYsYYYYYYYYYYYYYYYYK.......",
        ".........KYYKKKsYYYYYYYYYYYYYYYYYK......",
        ".........KYYKSSKYYYYYYYYYYYYYYYYYK......",
        ".........KYYKSSSKKKKYYYYYYYYYYKYYK......",
        ".........KYYKSSSSyySKKKKyyYYYKKYYK......",
        ".........KYKSSyyySSSSSSSKKKKKSKYK.......",
        ".........KYKSSSSSSSSSSSSSSSSSSKYK.......",
        ".........KYKSSSSbSSSSSSSSSbSSSKYK.......",
        ".........KYKSSSbSbSSSSSSSbSbSSKYK.......",
        "..........KSSSSSSSSSSSSSSSSSSSSK........",
        "..........KSSSSSSSSSSsSSSSSSSSSK........",
        "..........KSSSSSSSSSSsSSSSSSSSSK........",
        "..........KSSSSSSSSSsSsSSSSSSSSK........",
        "...........KSSSSSSSSSSSSSSSSSSK.........",
        "...........KSSSKKKKKKKKKKKKKSSK.........",
        "............KssKWWWWWWWWWWWKSK..........",
        ".............KsKWWWWWWWWWWWKK...........",
        "..............KKWWRRRRRRRWWK............",
        "................KKKRRRRRKKK.............",
        ".................KKKKKKKK...............",
        "................KssssssssK..............",
        "................KssssssssK..............",
        "................KsssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        ".................KssssssssK.............",
        "..................KssssssK..............",
        "...................KssssK..............."
    )
)

// Losing pilot suit - Black suit, white shirt, red tie. Sits behind the desk.
val LOSE_PILOT_BODY = Sprite(
    "........................KKKKKKKKKKKKKKKKKKKKKK........................",
    "......................KKppWWWWWWWWWWWWWWWWWWppKK......................",
    ".....................KphppWWWWWWWWWWWWWWWWWWppphK.....................",
    "..................KKKpphpppWWWWWWWWWWWWWWWWpppphpKK...................",
    "...............KKKppKppphppWWWWWWWWWWWWWWWWppphppKpKKK................",
    "..............KpppppKppphppWWWWWWRRRrWWWWWWppphppKppppKK..............",
    ".............KphpppKpppphpppWWWWRRRRrRWWWWpppphpppKpppphK.............",
    "........K...KppphppKppppphppWWWWRRRRrRWWWWppphppppKppphppK...K........",
    ".......KhK.KppppphKpppppphppWWWWKKKKKKWWWWppphpppppKphppppK.KhK.......",
    "......KphpKpppppphKpppppphppKWWWRRRRRRWWWKppphpppppKhhpppppKphpK......",
    ".....KpphKpppppppKhhpppppphppWWWRRRRRRWWWppphpppppphKpppppppKhppK.....",
    "....KpppKhpppppppKphpppppphppWWWRRRRrRWWWppphpppppphKppppppphKpppK....",
    "...KpppKphpppppppKpphppppphppWWWRRRRrRWWWppphppppphpKppppppphpKpppK...",
    "..KpppKpphppppppKpppphppppphppWRRRRRrRRWppphppppphpppKpppppphppKpppK..",
    "..KppKpppphpppppKpKKppppppphppWRRRRRrRRWppphpppppppppKppppphppppKppK..",
    "..KppKpppphpppKKKKRRKpppppphppWRRRRRrRRWppphppppppppKpppppphppppKppK..",
    "..KppKpppphpKKRRRRRRKppppppphpKRRRRRrRKppphpppppppppKpppppphppppKppK..",
    "..KppKpppppKRRRRWWWWKppppppphppKRRRRrRKppphppppppppKpppppphpppppKppK..",
    "..KppKpppppKWWWWWWWWKppppppphppKRRRRrRKppphppppppppKppppppppppppKppK..",
    "..KppKpppppKWWWWWWWWKpppppppphpKRRRRrRKpphppppppppKpppppppppppppKppK..",
    "..KppKpppppKWWWWWWKKKpppppppphppKRRRRKppphpppppppKppppppppppppppKppK..",
    "..KppKpppppKWWKKKKppKpppppppphppKRRRRKppphpppppppKppppppppppppppKppK..",
    ".KpppKppppppKKpppppppKpppppppphpKRRRRKpphpppppppKpppppppppppppppKpppK.",
    ".KpppKpppppppppppppppKpppppppphppKRRKppphpppppppKpppppppppppppppKpppK.",
    ".KpppKppppppppppppppppKppppppphppKRRKppphppppppKppppppppppppppppKpppK.",
    ".KpppKppppppppppppppppKpppppppphpKRRKpphpppppppKppppppppppppppppKpppK.",
    ".KpppKpppppppppppppppppKppppppphpKRRKpphppppppKpppppppppppppppppKpppK.",
    ".KpppKpppppppppppppppppKpppppppphpKKpphpppppppKpppppppppppppppppKpppK.",
    ".KpppKppppppppppppppppppKppppppphpKKpphppppppKppppppppppppppppppKpppK.",
    ".KpppKppppppppppppppppppKppppppphpKKpphppppppKppppppppppppppppppKpppK.",
    ".KppKppppppppppppppppppppKppppppphKpphppppppKpppppppppppppppppppKpppK.",
    ".KppKpppppppppppppppppppppKpppppphKpphpppppKpppppppppppppppppppppKppK.",
    ".KppKpppppppppppppppppppppKpppppphKKphpppppKpppppppppppppppppppppKppK.",
    ".KppKppppppppppppppppppppppKpppppKhRKpppppKppppppppppppppppppppppKppK.",
    ".KppKppppppppppppppppppppppKpppKKpKKhKKpppKppppppppppppppppppppppKppK.",
    "KpppKpppppppppppppppppppppppKpKppphphppKpKpppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppKppppppppppKppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK",
    "KpppKpppppppppppppppppppppppppppppphhppppppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK",
    "KpppKpppppppppppppppppppppppppppppphhppppppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK",
    "KpppKppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppKpppK"
)

// Losing pilot hands - Clasped hands resting on the desk, drawn in front of it.
val LOSE_PILOT_HANDS = Sprite(
    "...........................KKKKKKKKKK...........................",
    ".......................KK.KSSSSSSSSSSK.KK.......................",
    "....................KKKWWKSSSSSSSSSSSSKWWKKK....................",
    "................K.KKWWWWWSSSSSSSSSSSSSSWWWWWKK.K................",
    ".............KKKpKWWWWWWSSsSSsSSsSSsSSsSWWWWWWKpKKK.............",
    "...........KKpppKWWWWWWWSSSSSSSSSSSSSSSSWWWWWWWKpppKK...........",
    "........KKKpppppKWWWWWWWSSSSSSSSSSSSSSSSWWWWWWWKpppppKKK........",
    "......KKppppppppKWWWWWWWSSSSSssSSSssSSSSWWWWWWWKppppppppKK......",
    "....KKppppppppppKWWWWWWWKSSSSSSSSSSSSSSWYWWWWWWKppppppppppKK....",
    ".KKKppppppppppppKWWWWWWWWWSSSSSSSSSSSSWWWWWWWWWKppppppppppppKKK.",
    "KpppppppppppppppKWWWWWWWWWKSSSSSSSSSSKWWWWWWWWWKpppppppppppppppK",
    "pppppppppppppppppKWWWWWWKK.KKKKKKKKKK.KKWWWWWWKppppppppppppppppp",
    "ppppppppppppppppppKKKKKK................KKKKKKpppppppppppppppppp",
    "ppppppppppppppppppppppK..................Kpppppppppppppppppppppp",
    "pppppppppppppppppppKKK....................KKKppppppppppppppppppp",
    "pppppppppppppppppKK..........................KKppppppppppppppppp",
    "pppppppppppppppKK..............................KKppppppppppppppp",
    "pppppppppppppKK..................................KKppppppppppppp",
    "pppppppppppKK......................................KKppppppppppp"
)

// Mahogany desk - Executive desk, front view. The pilot sits behind it.
val OFFICE_DESK = Sprite(
    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
    "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
    "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd",
    "dcddddddddddddddddedddddcdddddddcddddddddddddddcdddddddddddedddddddddccddddddddddddddddddddddcddddddedddddcdddddddddcddddddddddddddddddddddcdedcddddddddddddddddddcddddddddddddddddd",
    "dddddddddcddddddddcdddddedddddddcddddddddddddddddddddddcdddddddddeddddddddddddcdddddddddddddcddddddddcddddedddddddddddddddddcddddcdddddddddddddddddeddddddddddddddddddcdddcddddddddd",
    "ddddcddddddddddddcddddddddddddedddddddddccdddddddddddddddddddddcdddddddeddddddcdddddddcddddddddddddddddddddddcddeddcddddddddddddddddcdddddddddddddddddddcedcddddddddddddddddddddddcd",
    "ddcddddddddddddddddddddddcdcddddddddedddddddddddcdddddddddddddddcddddddcdddddeddddddddddddddddcddddddcdddddddddddddddcedddddddddddddddddddcdcddddddddddddddddddedddcdddddddddddcdddd",
    "deddddddddcddcdddddddddddddddddddcddddddddedddddddcdddddcddddddddddddddddddddddcdddedddcddddddddddddddcdddddddddddddddddddddecddddddddddddddddddddddcddddddddddddcdddedddddcdddddddd",
    "dddddddeddddddddddcdddddddddddddddddcddddcddddddedddddddddddddddcddddddddcdddddddddddddcdeddddddddddddddddddddcdddddddddddddddddddeddcdddddddddddddcddddddddcddddddddddddddedddddddc",
    "dddcdddddddddeddddddddcdddcddddddddddddddddddddddcddddeddddcddddddddddddcddddddddddddddddddddddecdddddddddddddddddddddcddddddddddddddcddeddddcddddddddddddddddddddddcdddddcddddddedd",
    "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
    "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
    "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "aaaaadddddddddddddddddddddddddddddddddddddddddddddddddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccadddddddddddddddddddddddddddddddddddddddddddddddddaaaa",
    "aaaaadddddddddddddddddddddddddddddddddddddddddddddddddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccadddddddddddddddddddddddddddddddddddddddddddddddddaaaa",
    "aaaaaddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddccccccdccccccccccccccccdccccccccccccccccdccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccdccccccccccccccccdccccccccccccccccdcccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccyyyyyyycccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccyyyyyyycccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccyyyyyyycccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccyyyyyyycccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccaaaaaaacccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccaaaaaaacccccccccccccccccccddaaaa",
    "aaaaaddccccccdccccccccccccccccdccccccccccccccccdccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccdccccccccccccccccdccccccccccccccccdcccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddaaaa",
    "aaaaadddddddddddddddddddddddddddddddddddddddddddddddddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccadddddddddddddddddddddddddddddddddddddddddddddddddaaaa",
    "aaaaaddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccdccccccccccccccccdccccccccccccccccdcccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddccdccccccccccccccccdccccccccccccccccdccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccyyyyyyycccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccyyyyyyycccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccyyyyyyycccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccyyyyyyycccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccaaaaaaacccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccaaaaaaacccccccccccccccccccddaaaa",
    "aaaaaddcccccdccccccccccccccccdccccccccccccccccdcccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddccdccccccccccccccccdccccccccccccccccdccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddaaaa",
    "aaaaadddddddddddddddddddddddddddddddddddddddddddddddddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccadddddddddddddddddddddddddddddddddddddddddddddddddaaaa",
    "aaaaaddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddccccdccccccccccccccccdccccccccccccccccdccccccddacccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaccccaddcdccccccccccccccccdccccccccccccccccdcccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccyyyyyyycccccccccccccccccccddacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaddcccccccccccccccccccyyyyyyycccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccyyyyyyycccccccccccccccccccddacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaddcccccccccccccccccccyyyyyyycccccccccccccccccccddaaaa",
    "aaaaaddcccccccccccccccccccaaaaaaacccccccccccccccccccddacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaddcccccccccccccccccccaaaaaaacccccccccccccccccccddaaaa",
    "aaaaaddccccdccccccccccccccccdccccccccccccccccdccccccddacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaddcdccccccccccccccccdccccccccccccccccdcccccccccddaaaa",
    "aaaaaddcccccccccccccccccccccccccccccccccccccccccccccddacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaddcccccccccccccccccccccccccccccccccccccccccccccddaaaa",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacccaaaaccccccccccccccccccccccccccccccccccccccccccccccccccccccccaaaaccccaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
)
