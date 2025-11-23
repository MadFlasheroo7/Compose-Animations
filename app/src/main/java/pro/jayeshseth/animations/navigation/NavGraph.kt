package pro.jayeshseth.animations.navigation

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import androidx.palette.graphics.Palette
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.BlackCherryCosmos
import com.mikepenz.hypnoticcanvas.shaders.BubbleRings
import com.mikepenz.hypnoticcanvas.shaders.GoldenMagma
import com.mikepenz.hypnoticcanvas.shaders.GradientFlow
import com.mikepenz.hypnoticcanvas.shaders.Heat
import com.mikepenz.hypnoticcanvas.shaders.IceReflection
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import com.mikepenz.hypnoticcanvas.shaders.OilFlow
import com.mikepenz.hypnoticcanvas.shaders.PurpleLiquid
import com.mikepenz.hypnoticcanvas.shaders.RainbowWater
import com.mikepenz.hypnoticcanvas.shaders.Stage
import com.mikepenz.hypnoticcanvas.shaders.Stripy
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.components.toColor
import pro.jayeshseth.animations.core.ui.modifiers.clipToDeviceCornerRadius
import pro.jayeshseth.animations.defaultApis.navigation.defaultApis
import pro.jayeshseth.animations.easterEggs.navigation.easterEggs
import pro.jayeshseth.animations.itemPlacements.navigation.itemPlacements
import pro.jayeshseth.animations.navigation.navigation.navigation
import pro.jayeshseth.animations.playground.navigation.playground
import pro.jayeshseth.animations.shaders.navigation.shaders
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.HomeScreen

/**
 * Nav3 graph with custom animations and decorators
 */
@Composable
fun NavGraph(
    onClickLink: OnClickLink
) {
    val hazeState = rememberHazeState()
    val backStack = rememberNavigator(NavDestinations.Home)
    val entryWithClippedBackgroundDecorator = navEntryDecorator<Any> { entry ->
        Box(
            Modifier
                .clipToDeviceCornerRadius()
        ) {
            entry.Content()
        }
    }
    var palette by remember { mutableStateOf<Palette?>(null) }

    val gl = rememberGraphicsLayer()
    var bitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    val shaders = remember {
        listOf(
            BlackCherryCosmos,
            OilFlow,
            InkFlow,
            Heat(),
            GoldenMagma,
            BubbleRings,
            Stage,
            IceReflection,
            RainbowWater,
            GradientFlow,
            PurpleLiquid,
            Stripy()
        )
    }
    var currentShaderIndex by remember { mutableStateOf(0) }

    LaunchedEffect(currentShaderIndex) {
        bitmap = gl.toImageBitmap()
    }

    LaunchedEffect(bitmap, currentShaderIndex) {
        palette = withContext(Dispatchers.Default) {
            if (bitmap != null) {
                val sb = bitmap!!.asAndroidBitmap().copy(
                    Bitmap.Config.ARGB_8888,
                    false
                )
                Palette.from(sb)
                    .generate()
            } else null
        }
    }
    Box(
        Modifier.drawWithCache {
            onDrawWithContent {
                gl.record {
                    this@onDrawWithContent.drawContent()
                }
                drawLayer(gl)
            }
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .blur(12.dp)
                .shaderBackground(shaders[currentShaderIndex], .7f)
        )

//        bitmap?.let {
//            Image(
//                bitmap = it,
//                contentDescription = "",
//                modifier = Modifier.size(200.dp)
//            )
//        }
    }
    NavDisplay(
        backStack = backStack.backStack,
        onBack = { backStack.navBack() },
        modifier = Modifier
            .clickable {
                currentShaderIndex = (currentShaderIndex + 1) % shaders.size
            },
        entryDecorators = listOf(
            entryWithClippedBackgroundDecorator,
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NavDestinations.Home> {
                HomeScreen(
                    color = palette?.lightVibrantSwatch?.toColor()
                        ?: palette?.lightMutedSwatch?.toColor()
                        ?: palette?.mutedSwatch?.toColor()
                        ?: palette?.vibrantSwatch?.toColor()
                        ?: palette?.dominantSwatch?.toColor()
                        ?: palette?.darkVibrantSwatch?.toColor()
                        ?: palette?.darkMutedSwatch?.toColor()
                        ?: Color.Cyan,
                    hazeState = hazeState
                ) { route ->
                    backStack.navigate(route)
                }
            }
            // Feature Graphs
            defaultApis(onClickLink, hazeState)
            playground(
                hazeState = hazeState,
                onClickLink = onClickLink,
                onNavAction = { backStack.navigate(it) })
            itemPlacements(
                hazeState = hazeState,
                onClickLink = onClickLink,
                onNavAction = { backStack.navigate(it) })
            shaders(
                hazeState = hazeState,
                onClickLink = onClickLink,
                onNavAction = { backStack.navigate(it) })
            easterEggs(hazeState = hazeState) { backStack.navigate(it) }
            navigation(hazeState = hazeState)

            entry<NavDestinations.BouncyRope> {
                BouncyRope()
            }
            entry<NavDestinations.AboutScreen> {
                AboutScreen(hazeState)
            }
            // TODO add community
            entry<NavDestinations.Community> {
                AboutScreen(hazeState)
            }
        }
    )
}