package pro.jayeshseth.animations.defaultApis.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.model.lazyNavBarPadding
import pro.jayeshseth.animations.core.model.lazyStatusBarPadding
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes

@Composable
fun DefaultApisLanding(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
//    color: Color = Color.Cyan,
    navAction: OnNavAction
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        lazyStatusBarPadding()
        items(defaultApiAnimations) {
            InteractiveButton(
                hazeState = hazeState,
//                color = color,
                text = it.title,
                height = 100.dp
            ) {
                navAction(it.route)
            }
        }
        lazyNavBarPadding()
    }
}

/**
 * list of default api animations.
 */
private val defaultApiAnimations: List<AnimationScreen> by lazy {
    listOf(
        AnimationScreen(
            title = "Animate Visibility",
            route = DefaultApisRoutes.AnimateVisibilityRoute
        ),
        AnimationScreen(
            title = "Animate Value As State",
            route = DefaultApisRoutes.AnimateValueAsStateRoute
        ),
        AnimationScreen(
            title = "Animate Content",
            route = DefaultApisRoutes.AnimateContentRoute
        ),
        AnimationScreen(
            title = "Animate Gesture",
            route = DefaultApisRoutes.AnimateGestureRoute
        ),
        AnimationScreen(
            title = "Infinite Rotation",
            route = DefaultApisRoutes.InfiniteRotationRoute
        )
    )
}


@Preview
@Composable
private fun PreviewAnimationCard() {
    val hazeState = rememberHazeState()
    AnimationsTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState)
                    .shaderBackground(InkFlow, speed = 0.2f)

            )

            DefaultApisLanding(
                hazeState = hazeState
            ) {}
        }
    }
}