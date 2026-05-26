package pro.jayeshseth.mugen.look.haze

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import pro.jayeshseth.mugen.tokens.MugenColors

/**
 * Haze-flavoured colors. Implements the universal [MugenColors] contract and adds
 * bespoke tokens that only Haze renderers know about: `glow`, `shimmer`, `hazeTint`,
 * `innerShadow`.
 */
@Immutable
class HazeColors(
    // ----- Base contract -----
    override val primary: Color = Color(0xFFD0BCFF),
    override val onPrimary: Color = Color(0xFF1A1430),
    override val accent: Color = Color(0xFF29B6F6),
    override val onAccent: Color = Color(0xFF051018),
    override val surface: Color = Color(0xFF0E0A1C),
    override val onSurface: Color = Color(0xFFF2ECFF),
    override val surfaceVariant: Color = Color(0xFF15102A),
    override val onSurfaceVariant: Color = Color(0xFFB8B0D8),
    override val outline: Color = Color(0xFF2C2654),
    override val danger: Color = Color(0xFFEF5350),
    override val warning: Color = Color(0xFFFFA726),
    override val success: Color = Color(0xFF66BB6A),

    // ----- Haze-only -----
    /** Color of the outer glowing shadow on interactive surfaces. */
    val glow: Color = Color(0xFFD0BCFF),

    /** Color of the moving shimmer band painted along borders. */
    val shimmer: Color = Color.White.copy(alpha = 0.8f),

    /** Tint of the haze blur layer that lives under glassmorphic surfaces. */
    val hazeTint: Color = Color.Black.copy(alpha = 0.55f),

    /** Color of the inner-shadow pass used on pressed / interacted surfaces. */
    val innerShadow: Color = Color.Black.copy(alpha = 0.45f),
) : MugenColors
