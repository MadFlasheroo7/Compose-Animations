package pro.jayeshseth.animations

import androidx.compose.ui.window.ComposeUIViewController
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph

fun MainViewController() = ComposeUIViewController {
    val backStack = rememberNavigator(LandingRoutes.Home)
    AnimationsTheme {
        NavGraph(backStack) { }
    }
}