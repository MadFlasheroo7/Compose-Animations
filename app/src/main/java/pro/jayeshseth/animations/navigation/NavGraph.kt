package pro.jayeshseth.animations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.jayeshseth.animations.ui.screens.AnimateValueAsState
import pro.jayeshseth.animations.ui.screens.AnimatedGestures
import pro.jayeshseth.animations.ui.screens.AnimatedTransition
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.HomeScreen
import pro.jayeshseth.animations.ui.screens.InfiniteRotation
import pro.jayeshseth.animations.ui.screens.SwipeRefresh
import pro.jayeshseth.animations.ui.screens.VisibilityAnimation

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HOME_SCREEN"
    ) {
        composable("HOME_SCREEN") {
            HomeScreen(
                navToAnimateVisibility = { navController.navigate("ANIMATE_VISIBILITY") },
                navToAnimateContent = { navController.navigate("ANIMATE_CONTENT") },
                navToAnimateGesture = { navController.navigate("ANIMATE_GESTURES") },
                navToAnimateNav = { navController.navigate("ANIMATED_NAV_GRAPH") },
                navToAnimateInfiniteRotation = { navController.navigate("INFINITE_ROTATION") },
                navToSwipeRefresh = { navController.navigate("SWIPE_REFRESH") },
                navToBouncyRopes = { navController.navigate("BOUNCY_ROPE") },
                navToAnimateValueAsState = { navController.navigate("ANIMATE_VALUE_AS_STATE") }
            )
        }
        composable("ANIMATE_VISIBILITY") {
            VisibilityAnimation()
        }
        composable("ANIMATE_CONTENT") {
            AnimatedTransition()
        }
        composable("ANIMATE_GESTURES") {
            AnimatedGestures()
        }
        composable("INFINITE_ROTATION") {
            InfiniteRotation()
        }
        composable("ANIMATED_NAV_GRAPH") {
            AnimatedNavGraph()
        }
        composable("SWIPE_REFRESH") {
            SwipeRefresh()
        }
        composable("BOUNCY_ROPE") {
            BouncyRope()
        }
        composable("ANIMATE_VALUE_AS_STATE") {
            AnimateValueAsState()
        }
    }
}