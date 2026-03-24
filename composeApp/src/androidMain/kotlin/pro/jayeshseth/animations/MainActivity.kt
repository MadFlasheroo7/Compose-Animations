package pro.jayeshseth.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import pro.jayeshseth.animations.core.model.CustomizationState
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationRepository
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import pro.jayeshseth.animations.core.utils.BASE_URL
import pro.jayeshseth.animations.core.utils.rememberCustomizationRepository
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.navigation.initializeApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeApp()
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavigator(LandingRoutes.Home)
            val urlHandler = LocalUriHandler.current
            val repo = rememberCustomizationRepository()
            val customizationState by repo.state.collectAsState(initial = CustomizationState())
            CompositionLocalProvider(
                LocalCustomizationState provides customizationState,
                LocalCustomizationRepository provides repo,
            ) {
                AnimationsTheme {
                    NavGraph(backStack) { urlHandler.openUri("$BASE_URL/$it") }
                }
            }
        }
    }
}
