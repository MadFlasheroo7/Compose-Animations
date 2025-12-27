package pro.jayeshseth.animations

import androidx.compose.ui.input.key.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.navigation.NavGraph

fun main() = application {
    val backStack = rememberNavigator(NavDestinations.Home)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Animations",
        onKeyEvent = { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown &&
                keyEvent.key == Key.DirectionLeft &&
                keyEvent.isAltPressed
            ) {

                backStack.navBack() // Execute Back
                true // Consume the event
            } else {
                false
            }
        },
    ) {
        val hazeState = rememberHazeState()
        AnimationsTheme {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .hazeSource(hazeState)
//                    .blur(12.dp)
//                    .shaderBackground(InkFlow, .2f)
//            )
//            HomeScreen(
//                hazeState =hazeState,
//                color = Color.Cyan
//            ){
//
//            }
            NavGraph(backStack) { }
        }
    }
}