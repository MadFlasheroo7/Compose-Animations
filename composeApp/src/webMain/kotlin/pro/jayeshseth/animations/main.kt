package pro.jayeshseth.animations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.ui.screens.HomeScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        // TODO temp test
        val hazeState =  rememberHazeState()
        AnimationsTheme {
            Box(
                Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState)
                    .blur(12.dp)
                    .shaderBackground(InkFlow, .2f)
            )
            HomeScreen(
                hazeState =hazeState,
                color = Color.Cyan
            ){

            }
        }
//        NavGraph()
    }
}