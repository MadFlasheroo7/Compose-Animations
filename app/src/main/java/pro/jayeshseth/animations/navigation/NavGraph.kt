package pro.jayeshseth.animations.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.AnimateValueAsState
import pro.jayeshseth.animations.ui.screens.AnimatedGestures
import pro.jayeshseth.animations.ui.screens.AnimatedTransition
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.EasterEggScreen
import pro.jayeshseth.animations.ui.screens.HomeScreen
import pro.jayeshseth.animations.ui.screens.ItemPlacementAnimation
import pro.jayeshseth.animations.ui.screens.SwipeRefresh
import pro.jayeshseth.animations.ui.screens.VisibilityAnimation
import pro.jayeshseth.animations.ui.screens.easterEggs.PhysicsLayoutAboutScreen
import pro.jayeshseth.animations.ui.screens.infiniteTransistions.InfiniteRotation
import pro.jayeshseth.animations.ui.screens.itemPlacements.FadeItemPlacement
import pro.jayeshseth.animations.ui.screens.itemPlacements.ScaleItemPlacement
import pro.jayeshseth.animations.ui.screens.itemPlacements.SlideItemPlacement
import pro.jayeshseth.animations.ui.screens.itemPlacements.TrippyBlinders
import pro.jayeshseth.animations.ui.screens.shaders.InterstellarShader
import pro.jayeshseth.animations.ui.screens.shaders.RainbowCircle
import pro.jayeshseth.animations.ui.screens.shaders.Shaders
import pro.jayeshseth.animations.ui.screens.shaders.clipToDeviceCornerRadius

typealias OnClickLink = (path: String) -> Unit
typealias OnNavAction = (NavDestinations) -> Unit

@Composable
fun NavGraph(onClickLink: OnClickLink) {
    val backStack = rememberNavBackStack(NavDestinations.Home)
    val entryWithClippedBackgroundDecorator = navEntryDecorator<Any> { entry ->
        Box(
            Modifier
                .clipToDeviceCornerRadius()
                .background(MaterialTheme.colorScheme.background)
        ) {
            entry.content(entry.key)
        }
    }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            entryWithClippedBackgroundDecorator,
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NavDestinations.Home> {
                HomeScreen { route ->
                    backStack.add(route)
                }
            }
            entry<NavDestinations.AnimateVisibility> {
                VisibilityAnimation(onClickLink)
            }
            entry<NavDestinations.AnimateContent> {
                AnimatedTransition()
            }
            entry<NavDestinations.AnimateGesture> {
                AnimatedGestures()
            }
            entry<NavDestinations.AnimateNavGraph> {
                AnimatedNavGraph()
            }
            entry<NavDestinations.InfiniteRotation> {
                InfiniteRotation()
            }
            entry<NavDestinations.SwipeRefresh> {
                SwipeRefresh()
            }
            entry<NavDestinations.BouncyRope> {
                BouncyRope()
            }
            entry<NavDestinations.AnimateValueAsState> {
                AnimateValueAsState(onClickLink)
            }
            entry<NavDestinations.AnimatedListItemPlacement> {
                ItemPlacementAnimation(
                    navToTrippyBlinders = { backStack.add(NavDestinations.TrippyBlinders) },
                    navToSlideInOut = { backStack.add(NavDestinations.SlideInOut) },
                    navToScale = { backStack.add(NavDestinations.ScaleItemPlacement) },
                    navToFade = { backStack.add(NavDestinations.FadeItemPlacement) },
                )
            }
            entry<NavDestinations.TrippyBlinders> {
                TrippyBlinders()
            }
            entry<NavDestinations.AboutScreen> {
                AboutScreen()
            }
            entry<NavDestinations.SlideInOut> {
                SlideItemPlacement(onClickLink)
            }
            entry<NavDestinations.ScaleItemPlacement> {
                ScaleItemPlacement(onClickLink)
            }
            entry<NavDestinations.FadeItemPlacement> {
                FadeItemPlacement(onClickLink)
            }
            entry<NavDestinations.Community> {
                AboutScreen()
            }
            entry<NavDestinations.PastEasterEggs> {
                EasterEggScreen { backStack.add(it) }
            }
            entry<NavDestinations.PhysicsLayoutAboutScreen> {
                PhysicsLayoutAboutScreen()
            }
            entry<NavDestinations.Shaders> {
                Shaders() { backStack.add(it) }
            }
            entry<NavDestinations.RainbowCircleShader> {
                RainbowCircle()
            }
            entry<NavDestinations.InterstellarShader> {
                InterstellarShader()
            }
        }
    )
}