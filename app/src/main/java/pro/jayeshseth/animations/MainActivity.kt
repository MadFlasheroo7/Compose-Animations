package pro.jayeshseth.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.ui.theme.AnimationsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ) {
                true
            }
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AnimationsTheme {
                val systemUiController = rememberSystemUiController()
                val color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                systemUiController.setStatusBarColor(
                    color = color,
                    darkIcons = false,
                )
                systemUiController.setNavigationBarColor(
                    color = color,
                    navigationBarContrastEnforced = false
                )
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}
//class NavActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setContent {
//            AnimationsTheme {
//                val systemUiController = rememberSystemUiController()
//                val color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
//                systemUiController.setStatusBarColor(
//                    color = color,
//                )
//                systemUiController.setNavigationBarColor(
//                    color = color,
//                    navigationBarContrastEnforced = false
//                )
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    NavGraph()
//                }
//            }
//        }
//    }
//}