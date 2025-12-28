package pro.jayeshseth.animations.core.ui.modifiers

// TODO migrate to madifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Modified glowing shadow modifier from madifiers lib, ideally add support to that modifier itself 🤷
 *
 * Draws a **Canvas** behind the modified content giving it a glowing effect.
 *
 * @param color color of the glowing shadow
 * @param borderRadius border radius of the glowing shadow
 * @param blurRadius glow radius of the shadow
 * @param offsetX X offset of the shadow
 * @param offsetY Y offset of the shadow
 * @param spread spread radius of the shadow
 */
expect fun Modifier.glowingShadow(
    color: Color,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    spread: Dp = 0.dp,
): Modifier