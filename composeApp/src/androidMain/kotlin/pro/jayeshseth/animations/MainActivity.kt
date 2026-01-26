package pro.jayeshseth.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalUriHandler
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.utils.BASE_URL
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.navigation.registerAllRoutes


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerAllRoutes()
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavigator(LandingRoutes.Home)
            val urlHandler = LocalUriHandler.current
            AnimationsTheme {
                NavGraph(backStack) { urlHandler.openUri("$BASE_URL/$it") }
            }
        }
    }
}
