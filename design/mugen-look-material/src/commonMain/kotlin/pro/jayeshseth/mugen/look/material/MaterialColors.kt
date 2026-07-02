package pro.jayeshseth.mugen.look.material

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import pro.jayeshseth.mugen.tokens.MugenColors

/**
 * Material 3-flavoured colors. Implements [MugenColors] and adds bespoke tonal-surface
 * tokens that only MaterialLook renderers consume.
 */
@Immutable
class MaterialColors(
    // Base contract
    override val primary: Color = Color(0xFF6750A4),
    override val onPrimary: Color = Color(0xFFFFFFFF),
    override val accent: Color = Color(0xFF625B71),
    override val onAccent: Color = Color(0xFFFFFFFF),
    override val surface: Color = Color(0xFFFEF7FF),
    override val onSurface: Color = Color(0xFF1D1B20),
    override val surfaceVariant: Color = Color(0xFFE7E0EC),
    override val onSurfaceVariant: Color = Color(0xFF49454F),
    override val outline: Color = Color(0xFF79747E),
    override val danger: Color = Color(0xFFB3261E),
    override val warning: Color = Color(0xFFFFA726),
    override val success: Color = Color(0xFF2E7D32),

    // Material-only
    /** Tonal surface used by elevated containers. */
    val tonalSurface: Color = Color(0xFFEADDFF),

    /** Surface tint applied on elevated surfaces (typically primary @ low alpha). */
    val surfaceTint: Color = Color(0xFF6750A4),
) : MugenColors
