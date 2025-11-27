package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.LazyIntrinsicGrid
import pro.jayeshseth.animations.core.ui.components.PrimaryInteractiveButton
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes
import pro.jayeshseth.animations.easterEggs.navigation.EasterEggsRoutes
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hazeState: HazeState,
    color: Color,
    navAction: OnNavAction = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val updateTransition = updateTransition(color)
    val transitionColor by updateTransition.animateColor { it }


    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = "Animations",
                        fontSize = 45.sp,
                        fontWeight = FontWeight(750),
                        fontFamily = syneFontFamily
                    )
                }
            )
        },
    ) {
        LazyIntrinsicGrid(
            items = animationScreens,
            columns = 2,
            contentPadding = it,
            span = { 2 },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { item ->
            PrimaryInteractiveButton(
                hazeState = hazeState,
                color = Color.White,
                text = item.title,
                onClick = { navAction(item.route) }
            )
        }
    }
}

/**
 * list of animation screens
 */
//private val animationScreens: List<AnimationScreen> by lazy {
//    mutableListOf(
//        AnimationScreen(
//            title = "Animate Visibility",
//            route = DefaultApisRoutes.AnimateVisibilityRoute,
//            color = Color(0xFFFF1100)
//        ),
//        AnimationScreen(
//            title = "Animate List Item Placement",
//            route = ItemPlacementRoutes.ListItemPlacementRoute,
//            color = Color(0xFFFF0055)
//        ),
//        AnimationScreen(
//            title = "Playground",
//            route = PlaygroundRoutes.PlaygroundLandingRoute,
//            color = Color(0xFFDB00FF)
//        ),
//        AnimationScreen(
//            title = "Animate Content",
//            route = DefaultApisRoutes.AnimateContentRoute,
//            color = Color(0xFF673AB7)
//        ),
//        AnimationScreen(
//            title = "Animate Value As State",
//            route = DefaultApisRoutes.AnimateValueAsStateRoute,
//            color = Color(0xFF00FFEA)
//        ),
//        AnimationScreen(
//            title = "Animated Gesture",
//            route = DefaultApisRoutes.AnimateGestureRoute,
//            color = Color(0xFFFFFFFF)
//        ),
//        AnimationScreen(
//            title = "Infinite Rotation",
//            route = DefaultApisRoutes.InfiniteRotationRoute,
//            color = Color(0xFFFFFC00)
//        ),
//        AnimationScreen(
//            title = "Swipe To Refresh",
//            route = ItemPlacementRoutes.SwipeRefreshRoute,
//            color = Color(0xFFFF00FA)
//        ),
//        AnimationScreen(
//            title = "Nav Animation",
//            route = NavigationRoutes.Nav2GraphRoute,
//            color = Color(0xFF00FFEB)
//        ),
//        AnimationScreen(
//            title = "Bouncy Ropes",
//            route = NavDestinations.BouncyRope,
//            color = Color(0xFF00FF08)
//        ),
//        /*        AnimationScreen(
//                    title = "Community",
//                    route = NavDestinations.Community.route
//                ),*/
//        AnimationScreen(
//            title = "Shader",
//            route = ShaderRoutes.ShaderGraphRoute,
//            color = Color(0xFF88FF00)
//        ),
//        AnimationScreen(
//            title = "About",
//            route = NavDestinations.AboutScreen,
//            color = Color(0xFFCDDC39)
//        ),
//        /*        AnimationScreen(
//                    title = "Past Easter Eggs",
//                    route = NavDestinations.PastEasterEggs.route
//                )*/
//    )
//}


private val animationScreens: List<AnimationScreen> by lazy {
    mutableListOf(
        AnimationScreen(
            title = "Default Apis",
            route = DefaultApisRoutes.DefaultApisLanding
        ),
        AnimationScreen(
            title = "Playground",
            route = PlaygroundRoutes.PlaygroundLandingRoute
        ),
        AnimationScreen(
            title = "Item Placements",
            route = ItemPlacementRoutes.ListItemPlacementRoute
        ),
        AnimationScreen(
            title = "Past Easter Eggs",
            route = EasterEggsRoutes.EasterEggsLandingRoute
        ),
        AnimationScreen(
            title = "Shaders",
            route = ShaderRoutes.ShaderGraphRoute
        ),
        /*        AnimationScreen(
                    title = "Shapes & Morphing",
                    route = DefaultApisRoutes.AnimateVisibilityRoute
                ),*/
        AnimationScreen(
            title = "Canvas",
            route = DefaultApisRoutes.AnimateVisibilityRoute
        ),
        AnimationScreen(
            title = "Community",
            route = DefaultApisRoutes.AnimateVisibilityRoute
        ),

        /*        AnimationScreen(
                    title = "Community",
                    route = NavDestinations.Community.route
                ),*/
        AnimationScreen(
            title = "About",
            route = NavDestinations.AboutScreen
        ),
    )
}



