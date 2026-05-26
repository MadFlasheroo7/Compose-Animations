package pro.jayeshseth.mugen.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

/**
 * The universal typography contract — a Material-flavoured type scale.
 * Looks may add bespoke styles (e.g. a `displayMega` for HazeLook, or `code` for a developer tool Look).
 */
@Immutable
interface MugenTypography {
    val displayLarge: TextStyle
    val displayMedium: TextStyle
    val displaySmall: TextStyle

    val titleLarge: TextStyle
    val titleMedium: TextStyle
    val titleSmall: TextStyle

    val bodyLarge: TextStyle
    val bodyMedium: TextStyle
    val bodySmall: TextStyle

    val labelLarge: TextStyle
    val labelMedium: TextStyle
    val labelSmall: TextStyle
}
