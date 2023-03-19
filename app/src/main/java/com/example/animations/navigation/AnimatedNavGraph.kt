package com.example.animations.navigation

import androidx.compose.animation.AnimatedContentScope
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
import com.example.animations.ui.screens.AnimatedNav
import com.example.animations.ui.screens.animatedNav.ScaleNav
import com.example.animations.ui.screens.animatedNav.SlideNav
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavGraph() {
    val animatedNavController = rememberAnimatedNavController()
    AnimatedNavHost(
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
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(400, easing = LinearEasing)
                )
            }
        ) {
            SlideNav()
        }
    }
}