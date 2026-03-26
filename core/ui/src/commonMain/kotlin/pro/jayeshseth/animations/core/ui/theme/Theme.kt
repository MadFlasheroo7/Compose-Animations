package pro.jayeshseth.animations.core.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun AnimationsTheme(
    content: @Composable () -> Unit
) {
    val customization = LocalCustomizationState.current
    val animatedPrimary by animateColorAsState(
        targetValue = Color(customization.primaryColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val colorScheme = darkColorScheme(
        primary = animatedPrimary,
    )

    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(
            color = animatedPrimary,
            fontFamily = syneFontFamily()
        )
    ) {
        // TODO Build Custom design system
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}