package pro.jayeshseth.animations.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.jayeshseth.animations.ui.screens.AnimatedNav
import pro.jayeshseth.animations.ui.screens.animatedNav.ScaleNav
import pro.jayeshseth.animations.ui.screens.animatedNav.SlideNav

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavGraph() {
    val animatedNavController = rememberNavController()
    NavHost(
        navController = animatedNavController,
        startDestination = "ANIMATED_NAV"
    ) {
        composable(
            route = "ANIMATED_NAV",
            enterTransition = {
                fadeIn(tween(durationMillis = 400, easing = LinearEasing))
            },
            exitTransition = {
                fadeOut(tween(durationMillis = 400, easing = LinearEasing))
            }
        ) {
            AnimatedNav(
                scaleNav = { animatedNavController.navigate("SCALE_NAV") },
                slideNav = { animatedNavController.navigate("SLIDE_NAV") },
            )
        }
        composable(
            route = "SCALE_NAV",
            enterTransition = {
                scaleIn(animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow
                ))
            },
            exitTransition = {
                scaleOut(animationSpec = tween(400, easing = LinearEasing))
            }
        ) {
            ScaleNav()
        }
        composable(
            route = "SLIDE_NAV",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            }
        ) {
            SlideNav()
        }
    }
}