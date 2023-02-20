package com.example.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.animations.navigation.NavGraph
import com.example.animations.ui.screens.HomeScreen
import com.example.animations.ui.theme.AnimationsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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