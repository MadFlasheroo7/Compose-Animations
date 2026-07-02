package pro.jayeshseth.mugen.look.plain

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import pro.jayeshseth.mugen.tokens.MugenColors

/**
 * Plain color palette — neutral, accessible dark-on-light / light-on-dark values with no
 * brand character. Works as a sensible baseline for any custom [pro.jayeshseth.mugen.look.MugenLook].
 */
@Immutable
class PlainColors(
    override val primary: Color = Color(0xFF6200EE),
    override val onPrimary: Color = Color(0xFFFFFFFF),
    override val accent: Color = Color(0xFF03DAC6),
    override val onAccent: Color = Color(0xFF000000),
    override val surface: Color = Color(0xFF121212),
    override val onSurface: Color = Color(0xFFE1E1E1),
    override val surfaceVariant: Color = Color(0xFF1E1E1E),
    override val onSurfaceVariant: Color = Color(0xFFAAAAAA),
    override val outline: Color = Color(0xFF3A3A3A),
    override val danger: Color = Color(0xFFCF6679),
    override val warning: Color = Color(0xFFFFB74D),
    override val success: Color = Color(0xFF81C784),
) : MugenColors
