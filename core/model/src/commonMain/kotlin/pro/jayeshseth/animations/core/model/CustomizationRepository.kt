package pro.jayeshseth.animations.core.model

import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.flow.Flow

interface CustomizationRepository {
    val state: Flow<CustomizationState>
    suspend fun updateBackgroundBlur(blur: Dp)
    suspend fun updateHeadingColor(argb: Int)
    suspend fun updatePrimaryColor(argb: Int)
    suspend fun updateAccentColor(argb: Int)
    suspend fun updatePrimaryButtonAccentColor(argb: Int)
    suspend fun updateButtonAccentColor(argb: Int)
    suspend fun updateBackground(backgroundType: BackgroundType)
    suspend fun resetToDefaults()
}
