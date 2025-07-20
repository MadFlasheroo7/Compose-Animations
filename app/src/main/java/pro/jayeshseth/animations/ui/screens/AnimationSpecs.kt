package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.util.OnNavAction
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedColumn

@Composable
fun AnimationSpecs(
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
            text = "Tween And Spring",
            onClick = { navAction(NavDestinations.TweenAndSpring) },
        )
    }
}