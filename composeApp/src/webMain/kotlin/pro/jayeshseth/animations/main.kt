package pro.jayeshseth.animations

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.browser.window
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
fun main() {
    ComposeViewport {
        // TODO temp test
        val backStack = rememberNavigator(
            LandingRoutes.Home,
            onNavigate = {route->
                window.history.pushState(null, "", route.toString())
                println("onNavigate $route")
            }
        )
        window.onpopstate = {
            it.preventDefault()
            backStack.navBack()
        }
        AnimationsTheme {
            NavGraph(backStack) { }
        }
    }
}