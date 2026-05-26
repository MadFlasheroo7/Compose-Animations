package pro.jayeshseth.mugen.playground

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Mugen Playground",
    ) {
        PlaygroundEntry()
    }
}
