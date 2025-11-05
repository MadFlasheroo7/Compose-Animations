package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.util.AnimationScreen
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasterEggScreen(navAction: OnNavAction) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    HomeScaffold(
        topAppBarScrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Easter Eggs",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        StatusBarAwareThemedLazyColumn(
            statusBarColor = Color.Transparent,
            modifier = Modifier
        ) {
            item {
                Spacer(Modifier.padding(top = it.calculateTopPadding()))
            }
            items(easterEggs) { animationScreen ->
                InteractiveButton(
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
            route = NavDestinations.PhysicsLayoutAboutScreen
        )
    )
}