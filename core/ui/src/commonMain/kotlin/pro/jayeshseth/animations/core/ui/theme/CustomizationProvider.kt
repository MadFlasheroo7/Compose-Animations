package pro.jayeshseth.animations.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import pro.jayeshseth.animations.core.model.CustomizationRepository
import pro.jayeshseth.animations.core.model.CustomizationState
import pro.jayeshseth.animations.core.model.EasterEggRepository

val LocalCustomizationState = compositionLocalOf { CustomizationState() }
val LocalCustomizationRepository = compositionLocalOf<CustomizationRepository?> { null }
val LocalEasterEggRepository = compositionLocalOf<EasterEggRepository?> { null }
