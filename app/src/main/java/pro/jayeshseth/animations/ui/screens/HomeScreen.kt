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
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.util.AnimationScreen
import pro.jayeshseth.animations.util.OnNavAction
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navAction: OnNavAction) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    HomeScaffold(
        topAppBarScrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Animations",
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
            items(animationScreens) { animationScreen ->
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
 * list of animation screens
 */
private val animationScreens: List<AnimationScreen> by lazy {
    mutableListOf(
        AnimationScreen(
            title = "Animate Visibility",
            route = NavDestinations.AnimateVisibility
        ),
        AnimationScreen(
            title = "Animate List Item Placement",
            route = NavDestinations.AnimatedListItemPlacement
        ),
        AnimationScreen(
            title = "Animate Content",
            route = NavDestinations.AnimateContent
        ),
        AnimationScreen(
            title = "Animate Value As State",
            route = NavDestinations.AnimateValueAsState
        ),
        AnimationScreen(
            title = "Animated Gesture",
            route = NavDestinations.AnimateGesture
        ),
        AnimationScreen(
            title = "Infinite Rotation",
            route = NavDestinations.InfiniteRotation
        ),
        AnimationScreen(
            title = "Swipe To Refresh",
            route = NavDestinations.SwipeRefresh
        ),
        AnimationScreen(
            title = "Nav Animation",
            route = NavDestinations.AnimateNavGraph
        ),
        AnimationScreen(
            title = "Bouncy Ropes",
            route = NavDestinations.BouncyRope
        ),
        /*        AnimationScreen(
                    title = "Community",
                    route = NavDestinations.Community.route
                ),*/
        AnimationScreen(
            title = "Shader",
            route = NavDestinations.Shaders
        ),
        AnimationScreen(
            title = "Playground",
            route = NavDestinations.Playground
        ),
        AnimationScreen(
            title = "About",
            route = NavDestinations.AboutScreen
        ),
        /*        AnimationScreen(
                    title = "Past Easter Eggs",
                    route = NavDestinations.PastEasterEggs.route
                )*/
    )
}