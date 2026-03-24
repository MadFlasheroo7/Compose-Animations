package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily

@Composable
fun HeadingText(
    text: String,
    modifier: Modifier = Modifier,
    autoSize: TextAutoSize = TextAutoSize.StepBased(
        minFontSize = 5.sp,
        maxFontSize = 35.sp,
        stepSize = 1.sp
    ),
    fontSize: TextUnit = 35.sp,
    textAlign: TextAlign = TextAlign.Center,
) {
    val headingColor by animateColorAsState(
        targetValue = Color(LocalCustomizationState.current.headingColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    Text(
        text = text,
        color = headingColor,
        autoSize = autoSize,
        fontSize = fontSize,
        fontWeight = FontWeight(750),
        textAlign = textAlign,
        fontFamily = syneFontFamily(),
        modifier = modifier,
    )
}
