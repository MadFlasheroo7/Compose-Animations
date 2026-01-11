package pro.jayeshseth.animations.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import animations.core.ui.generated.resources.*
import org.jetbrains.compose.resources.Font

@Composable
fun syneFontFamily() = FontFamily(
    Font(Res.font.syne_regular, FontWeight.Normal),
    Font(Res.font.syne_medium, FontWeight.Medium),
    Font(Res.font.syne_bold, FontWeight.Bold),
    Font(Res.font.syne_semi_bold, FontWeight.SemiBold),
    Font(Res.font.syne_extra_bold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
@Composable
fun Typography() = Typography(
    bodyLarge = TextStyle(
        fontFamily = syneFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)