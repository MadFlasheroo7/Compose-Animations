package pro.jayeshseth.mugen.look.material

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pro.jayeshseth.mugen.tokens.MugenTypography

@Immutable
class MaterialTypography(
    fontFamily: FontFamily = FontFamily.Default,
    override val displayLarge: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 57.sp, fontWeight = FontWeight.Normal,
    ),
    override val displayMedium: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 45.sp, fontWeight = FontWeight.Normal,
    ),
    override val displaySmall: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 36.sp, fontWeight = FontWeight.Normal,
    ),
    override val titleLarge: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 22.sp, fontWeight = FontWeight.Normal,
    ),
    override val titleMedium: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 16.sp, fontWeight = FontWeight.Medium,
    ),
    override val titleSmall: TextStyle = TextStyle(
        fontFamily = fontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium,
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
