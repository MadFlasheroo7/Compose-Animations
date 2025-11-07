package pro.jayeshseth.animations.shaders.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedColumn

@Composable
fun Shaders(
    modifier: Modifier = Modifier,
    navAction: OnNavAction
) {
    StatusBarAwareThemedColumn(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        InteractiveButton(
            text = "Rainbow Circle",
            onClick = {
                navAction(
                    ShaderRoutes.RainbowShaderRoute
                )
            },
        )
        InteractiveButton(
            text = "Interstellar Space",
            onClick = { navAction(ShaderRoutes.InterstellarShaderRoute) },
        )
    }
}