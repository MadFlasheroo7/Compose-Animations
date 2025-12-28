package pro.jayeshseth.animations

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph

fun main() = application {
    val backStack = rememberNavigator(LandingRoutes.Home)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Animations",
        onKeyEvent = { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown &&
                keyEvent.key == Key.DirectionLeft &&
                keyEvent.isAltPressed
            ) {
                backStack.navBack()
                true
            } else {
                false
            }
        },
    ) {
        AnimationsTheme {
            NavGraph(backStack) { }
        }
    }
}