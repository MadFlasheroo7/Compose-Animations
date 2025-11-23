package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.navigation.navigation.NavigationRoutes
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hazeState: HazeState,
    navAction: OnNavAction = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
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
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
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
                color = Color.White.copy(.8f),
                text = item.title,
                onClick = { navAction(item.route) }
            )
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


//private val animationScreens: List<AnimationScreen> by lazy {
//    mutableListOf(
//        AnimationScreen(
//            title = "Default Apis",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//        AnimationScreen(
//            title = "Playground",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//        AnimationScreen(
//            title = "Item Placements",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//        AnimationScreen(
//            title = "Past Easter Eggs",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//        AnimationScreen(
//            title = "Shapes & Morphing",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//        AnimationScreen(
//            title = "Community",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//        AnimationScreen(
//            title = "Shaders",
//            route = DefaultApisRoutes.AnimateVisibilityRoute
//        ),
//
//        /*        AnimationScreen(
//                    title = "Community",
//                    route = NavDestinations.Community.route
//                ),*/
//        AnimationScreen(
//            title = "Shader",
//            route = ShaderRoutes.ShaderGraphRoute
//        ),
//        AnimationScreen(
//            title = "About",
//            route = NavDestinations.AboutScreen
//        ),
//        /*        AnimationScreen(
//                    title = "Past Easter Eggs",
//                    route = NavDestinations.PastEasterEggs.route
//                )*/
//    )
//}



