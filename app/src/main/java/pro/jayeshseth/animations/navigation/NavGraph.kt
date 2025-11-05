package pro.jayeshseth.animations.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.modifiers.clipToDeviceCornerRadius
import pro.jayeshseth.animations.defaultApis.navigation.defaultApis
import pro.jayeshseth.animations.itemPlacements.navigation.itemPlacements
import pro.jayeshseth.animations.playground.navigation.playground
import pro.jayeshseth.animations.ui.easterEggs.PhysicsLayoutAboutScreen
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.EasterEggScreen
import pro.jayeshseth.animations.ui.screens.HomeScreen
import pro.jayeshseth.animations.ui.screens.Shaders
import pro.jayeshseth.animations.ui.shaders.interstellarSpace.InterstellarShaderScreen
import pro.jayeshseth.animations.ui.shaders.rainbowCircle.RainbowCircle

/**
 * Nav3 graph with custom animations and decorators
 */
@Composable
fun NavGraph(onClickLink: OnClickLink) {
    val backStack = rememberNavigator(NavDestinations.Home)
    val entryWithClippedBackgroundDecorator = navEntryDecorator<Any> { entry ->
        Box(
            Modifier
                .clipToDeviceCornerRadius()
                .background(MaterialTheme.colorScheme.background)
        ) {
            entry.Content()
        }
    }
    NavDisplay(
        backStack = backStack.backStack,
        onBack = { backStack.navBack() },
        entryDecorators = listOf(
            entryWithClippedBackgroundDecorator,
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NavDestinations.Home> {
                HomeScreen { route ->
                    backStack.navigate(route)
                }
            }
            defaultApis(onClickLink)
            playground(onClickLink = onClickLink, onNavAction = { backStack.navigate(it) })
            itemPlacements(onClickLink = onClickLink, onNavAction = { backStack.navigate(it) })
            entry<NavDestinations.AnimateNavGraph> {
                AnimatedNavGraph()
            }
//            entry<NavDestinations.SwipeRefresh> {
//                SwipeRefresh()
//            }
            entry<NavDestinations.BouncyRope> {
                BouncyRope()
            }
//            entry<NavDestinations.AnimatedListItemPlacement> {
//                ItemPlacementAnimation(
//                    navToTrippyBlinders = { backStack.navigate(NavDestinations.TrippyBlinders) },
//                    navToSlideInOut = { backStack.navigate(NavDestinations.SlideInOut) },
//                    navToScale = { backStack.navigate(NavDestinations.ScaleItemPlacement) },
//                    navToFade = { backStack.navigate(NavDestinations.FadeItemPlacement) },
//                )
//            }
//            entry<NavDestinations.TrippyBlinders> {
//                TrippyBlinders()
//            }
            entry<NavDestinations.AboutScreen> {
                AboutScreen()
            }
//            entry<NavDestinations.SlideInOut> {
//                SlideItemPlacement(onClickLink)
//            }
//            entry<NavDestinations.ScaleItemPlacement> {
//                ScaleItemPlacement(onClickLink)
//            }
//            entry<NavDestinations.FadeItemPlacement> {
//                FadeItemPlacement(onClickLink)
//            }
            entry<NavDestinations.Community> {
                AboutScreen()
            }
            entry<NavDestinations.PastEasterEggs> {
                EasterEggScreen { backStack.navigate(it) }
            }
            entry<NavDestinations.PhysicsLayoutAboutScreen> {
                PhysicsLayoutAboutScreen()
            }
            entry<NavDestinations.Shaders> {
                Shaders() { backStack.navigate(it) }
            }
            entry<NavDestinations.RainbowCircleShader> {
                RainbowCircle(onClickLink)
            }
            entry<NavDestinations.InterstellarShader> {
                InterstellarShaderScreen(onClickLink)
            }
//            entry<NavDestinations.Playground> {
//                Playground { backStack.navigate(it) }
//            }
//            entry<NavDestinations.TweenAndSpring> {
//                TweenAndSpringScreen(onClickLink)
//            }
//            entry<NavDestinations.AnimationSpec> {
//                AnimationSpecs { backStack.navigate(it) }
//            }
        }
    )
}