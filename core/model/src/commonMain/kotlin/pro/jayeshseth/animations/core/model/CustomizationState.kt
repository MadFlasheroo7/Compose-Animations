package pro.jayeshseth.animations.core.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CustomizationState(
    val backgroundBlur: Dp = 12.dp,
    val headingColorArgb: Int = Color.White.toArgb(),
    val primaryColorArgb: Int = Color.White.toArgb(),
    val primaryButtonAccentColorArgb: Int = Color.White.toArgb(),
    val buttonAccentColorArgb: Int = Color.Cyan.toArgb(),
    val accentColorArgb: Int = Color.Cyan.toArgb(),
    val backgroundType: BackgroundType = BackgroundType.Shader.entries.first(),
)