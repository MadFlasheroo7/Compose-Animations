package pro.jayeshseth.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.ui.theme.AnimationsTheme

const val BASE_URL =
    "https://github.com/MadFlasheroo7/Compose-Animations/tree/main/app/src/main/java/pro/jayeshseth/animations/ui"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val urlHandler = LocalUriHandler.current
            AnimationsTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph {
                        urlHandler.openUri("$BASE_URL/$it")
                    }
                }
            }
        }
    }
}