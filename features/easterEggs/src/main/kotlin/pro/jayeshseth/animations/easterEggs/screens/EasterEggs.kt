package pro.jayeshseth.animations.easterEggs.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.easterEggs.navigation.EasterEggsRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasterEggScreen(
    hazeState: HazeState,
    navAction: OnNavAction
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = "Easter Eggs",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = syneFontFamily
                    )
                }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
        ) {
            item {
                Spacer(Modifier.padding(top = it.calculateTopPadding()))
            }
            items(easterEggs) { animationScreen ->
                InteractiveButton(
                    hazeState = hazeState,
                    text = animationScreen.title,
                    onClick = {
                        navAction(animationScreen.route)
                    })
            }
            item { Spacer(Modifier.navigationBarsPadding()) }
        }
    }
}

/**
 * list of easter eggs
 */
private val easterEggs: List<AnimationScreen> by lazy {
    mutableListOf(
        AnimationScreen(
            title = "Physics Layout About Screen",
            route = EasterEggsRoutes.PhysicsLayoutAboutRoute
        )
    )
}