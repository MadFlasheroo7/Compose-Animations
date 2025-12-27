package pro.jayeshseth.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalUriHandler
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.utils.BASE_URL
import pro.jayeshseth.animations.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val urlHandler = LocalUriHandler.current
            AnimationsTheme {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
                NavGraph { urlHandler.openUri("$BASE_URL/$it") }
//                }
            }
        }
    }
}

