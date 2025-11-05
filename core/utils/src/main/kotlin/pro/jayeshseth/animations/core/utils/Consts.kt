package pro.jayeshseth.animations.core.utils

val isDebug = BuildConfig.DEBUG

val branch = if (isDebug) "beta" else "main"

val BASE_URL =
    "https://github.com/MadFlasheroo7/Compose-Animations/tree/$branch/app/src/main/java/pro/jayeshseth/animations/ui"

const val DURATION = 1000