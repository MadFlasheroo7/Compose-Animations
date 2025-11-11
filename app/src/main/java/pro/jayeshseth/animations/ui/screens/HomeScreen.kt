package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.navigation.navigation.NavigationRoutes
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes
import pro.jayeshseth.animations.ui.composables.InteractiveButton
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navAction: OnNavAction) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    HomeScaffold(
        topAppBarScrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Animations",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = syneFontFamily
            )
        }
    ) {
        StatusBarAwareThemedLazyColumn(
            statusBarColor = Color.Transparent,
            modifier = Modifier
        ) {
            item {
                Spacer(Modifier.padding(top = it.calculateTopPadding()))
            }
            items(animationScreens) { animationScreen ->
                InteractiveButton(
                    text = animationScreen.title,
                    onClick = {
                        navAction(animationScreen.route)
                    })
            }
            item { Spacer(Modifier.navigationBarsPadding()) }
        }
    }
}

/**
 * list of animation screens
 */
private val animationScreens: List<AnimationScreen> by lazy {
    mutableListOf(
        AnimationScreen(
            title = "Animate Visibility",
            route = DefaultApisRoutes.AnimateVisibilityRoute
        ),
        AnimationScreen(
            title = "Animate List Item Placement",
            route = ItemPlacementRoutes.AnimatedListItemPlacementRoute
        ),
        AnimationScreen(
            title = "Playground",
            route = PlaygroundRoutes.PlaygroundRoute
        ),
        AnimationScreen(
            title = "Animate Content",
            route = DefaultApisRoutes.AnimateContentRoute
        ),
        AnimationScreen(
            title = "Animate Value As State",
            route = DefaultApisRoutes.AnimateValueAsStateRoute
        ),
        AnimationScreen(
            title = "Animated Gesture",
            route = DefaultApisRoutes.AnimateGestureRoute
        ),
        AnimationScreen(
            title = "Infinite Rotation",
            route = DefaultApisRoutes.InfiniteRotationRoute
        ),
        AnimationScreen(
            title = "Swipe To Refresh",
            route = ItemPlacementRoutes.SwipeRefreshRoute
        ),
        AnimationScreen(
            title = "Nav Animation",
            route = NavigationRoutes.Nav2GraphRoute
        ),
        AnimationScreen(
            title = "Bouncy Ropes",
            route = NavDestinations.BouncyRope
        ),
        /*        AnimationScreen(
                    title = "Community",
                    route = NavDestinations.Community.route
                ),*/
        AnimationScreen(
            title = "Shader",
            route = ShaderRoutes.ShaderGraphRoute
        ),
        AnimationScreen(
            title = "About",
            route = NavDestinations.AboutScreen
        ),
        /*        AnimationScreen(
                    title = "Past Easter Eggs",
                    route = NavDestinations.PastEasterEggs.route
                )*/
    )
}