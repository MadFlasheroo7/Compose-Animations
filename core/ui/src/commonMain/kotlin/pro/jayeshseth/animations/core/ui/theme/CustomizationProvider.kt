package pro.jayeshseth.animations.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import pro.jayeshseth.animations.core.model.CustomizationRepository
import pro.jayeshseth.animations.core.model.CustomizationState

val LocalCustomizationState = staticCompositionLocalOf { CustomizationState() }
val LocalCustomizationRepository = staticCompositionLocalOf<CustomizationRepository?> { null }
