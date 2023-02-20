package com.example.animations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animations.ui.screens.HomeScreen
import com.example.animations.ui.screens.VisibilityAnimation

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HOME_SCREEN"
    ) {
        composable("HOME_SCREEN") {
            HomeScreen( navToAnimateVisibility = { navController.navigate("ANIMATE_VISIBILITY") })
        }
        composable("ANIMATE_VISIBILITY") {
            VisibilityAnimation()
        }
    }
}