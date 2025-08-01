package pro.jayeshseth.animations.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import pro.jayeshseth.animations.ui.components.clipToDeviceCornerRadius
import pro.jayeshseth.animations.ui.defaultApis.infiniteTransistions.InfiniteRotation
import pro.jayeshseth.animations.ui.easterEggs.PhysicsLayoutAboutScreen
import pro.jayeshseth.animations.ui.itemPlacements.FadeItemPlacement
import pro.jayeshseth.animations.ui.itemPlacements.ScaleItemPlacement
import pro.jayeshseth.animations.ui.itemPlacements.SlideItemPlacement
import pro.jayeshseth.animations.ui.itemPlacements.TrippyBlinders
import pro.jayeshseth.animations.ui.playground.animationSpecs.tweenAndSpring.TweenAndSpringScreen
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.AnimateValueAsState
import pro.jayeshseth.animations.ui.screens.AnimatedGestures
import pro.jayeshseth.animations.ui.screens.AnimatedTransition
import pro.jayeshseth.animations.ui.screens.AnimationSpecs
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.EasterEggScreen
import pro.jayeshseth.animations.ui.screens.HomeScreen
import pro.jayeshseth.animations.ui.screens.ItemPlacementAnimation
import pro.jayeshseth.animations.ui.screens.Playground
import pro.jayeshseth.animations.ui.screens.Shaders
import pro.jayeshseth.animations.ui.screens.SwipeRefresh
import pro.jayeshseth.animations.ui.screens.VisibilityAnimation
import pro.jayeshseth.animations.ui.shaders.interstellarSpace.InterstellarShaderScreen
import pro.jayeshseth.animations.ui.shaders.rainbowCircle.RainbowCircle
import pro.jayeshseth.animations.util.OnClickLink

/**
 * Nav3 graph with custom animations and decorators
 */
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
                RainbowCircle(onClickLink)
            }
            entry<NavDestinations.InterstellarShader> {
                InterstellarShaderScreen(onClickLink)
            }
            entry<NavDestinations.Playground> {
                Playground { backStack.add(it) }
            }
            entry<NavDestinations.TweenAndSpring> {
                TweenAndSpringScreen(onClickLink)
            }
            entry<NavDestinations.AnimationSpec> {
                AnimationSpecs { backStack.add(it) }
            }
        }
    )
}