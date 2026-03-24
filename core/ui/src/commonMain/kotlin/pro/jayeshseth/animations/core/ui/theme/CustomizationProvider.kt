package pro.jayeshseth.animations.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import pro.jayeshseth.animations.core.model.CustomizationRepository
import pro.jayeshseth.animations.core.model.CustomizationState

val LocalCustomizationState = compositionLocalOf { CustomizationState() }
val LocalCustomizationRepository = compositionLocalOf<CustomizationRepository?> { null }
