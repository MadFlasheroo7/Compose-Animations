package pro.jayeshseth.animations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.AnimateValueAsState
import pro.jayeshseth.animations.ui.screens.AnimatedGestures
import pro.jayeshseth.animations.ui.screens.AnimatedTransition
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.HomeScreen
import pro.jayeshseth.animations.ui.screens.InfiniteRotation
import pro.jayeshseth.animations.ui.screens.ItemPlacementAnimation
import pro.jayeshseth.animations.ui.screens.ScaleItemPlacement
import pro.jayeshseth.animations.ui.screens.SlideItemPlacement
import pro.jayeshseth.animations.ui.screens.SwipeRefresh
import pro.jayeshseth.animations.ui.screens.TrippyBlinders
import pro.jayeshseth.animations.ui.screens.VisibilityAnimation

typealias OnClickLink = (path: String) -> Unit

@Composable
fun NavGraph(
    onClickLink: OnClickLink
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavDestinations.Home.route
    ) {
        composable(NavDestinations.Home.route) {
            HomeScreen(
                navAction = { route ->
                    navController.navigate(route)
                }
            )
        }
        composable(NavDestinations.AnimateVisibility.route) {
            VisibilityAnimation(onClickLink)
        }
        composable(NavDestinations.AnimateContent.route) {
            AnimatedTransition()
        }
        composable(NavDestinations.AnimateGesture.route) {
            AnimatedGestures()
        }
        composable(NavDestinations.InfiniteRotation.route) {
            InfiniteRotation()
        }
        composable(NavDestinations.AnimateNavGraph.route) {
            AnimatedNavGraph()
        }
        composable(NavDestinations.SwipeRefresh.route) {
            SwipeRefresh()
        }
        composable(NavDestinations.BouncyRope.route) {
            BouncyRope()
        }
        composable(NavDestinations.AnimateValueAsState.route) {
            AnimateValueAsState(onClickLink)
        }
        composable(NavDestinations.SlideInOut.route) {
            SlideItemPlacement(onClickLink)
        }
        composable(NavDestinations.AnimatedListItemPlacement.route) {
            ItemPlacementAnimation(
                navToTrippyBlinders = { navController.navigate(NavDestinations.TrippyBlinders.route) },
                navToSlideInOut = { navController.navigate(NavDestinations.SlideInOut.route) },
                navToScale = { navController.navigate(NavDestinations.ScaleItemPlacement.route) },
            )
        }
        composable(NavDestinations.ScaleItemPlacement.route) {
            ScaleItemPlacement(onClickLink)
        }
        composable(NavDestinations.TrippyBlinders.route) {
            TrippyBlinders()
        }
        composable(NavDestinations.AboutScreen.route) {
            AboutScreen()
        }
    }
}