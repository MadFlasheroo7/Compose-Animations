package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.core.model.CustomizationRepository

@Composable
expect fun rememberCustomizationRepository(): CustomizationRepository
