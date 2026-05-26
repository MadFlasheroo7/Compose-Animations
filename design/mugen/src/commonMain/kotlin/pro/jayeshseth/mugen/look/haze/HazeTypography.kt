package pro.jayeshseth.mugen.look.haze

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pro.jayeshseth.mugen.tokens.MugenTypography

/**
 * Haze typography defaults. Uses the default system font family; consumers can supply
 * a custom [fontFamily] (e.g. Syne) to brand the entire scale at once.
 */
@Immutable
class HazeTypography(
    fontFamily: FontFamily = FontFamily.Default,
    override val displayLarge: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 57.sp, fontWeight = FontWeight.Bold,
    ),
    override val displayMedium: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 45.sp, fontWeight = FontWeight.Bold,
    ),
    override val displaySmall: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 36.sp, fontWeight = FontWeight.Bold,
    ),
    override val titleLarge: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 22.sp, fontWeight = FontWeight.SemiBold,
    ),
    override val titleMedium: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
    ),
    override val titleSmall: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
    ),
    override val bodyLarge: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Normal,
    ),
    override val bodyMedium: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Normal,
    ),
    override val bodySmall: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 12.sp, fontWeight = FontWeight.Normal,
    ),
    override val labelLarge: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium,
    ),
    override val labelMedium: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 12.sp, fontWeight = FontWeight.Medium,
    ),
    override val labelSmall: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 11.sp, fontWeight = FontWeight.Medium,
    ),
) : MugenTypography
