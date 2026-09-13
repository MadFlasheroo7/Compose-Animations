package pro.jayeshseth.animations.ui.screens

import pro.jayeshseth.animations.core.ui.utils.TrackRecomposition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.HeadingText
import pro.jayeshseth.animations.core.ui.components.PrimaryInteractiveButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.layouts.LazyIntrinsicGrid
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalEasterEggRepository
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.DESKTOP
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.MOBILE_LANDSCAPE
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.MOBILE_PORTRAIT
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.TABLET_LANDSCAPE
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.TABLET_PORTRAIT
import pro.jayeshseth.animations.core.ui.utils.currentDeviceConfiguration
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hazeState: HazeState,
    isSceneActivated: Boolean = false,
    navAction: OnNavAction = {}
) {
    TrackRecomposition(
        trackMap = mapOf(
            "hazeState" to hazeState,
            "isSceneActivated" to isSceneActivated,
            "navAction" to navAction
        ),
        composableName = "HomeScreen"
    )
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    val context = LocalContext.current
//    val updateTransition = updateTransition(color)
//    val transitionColor by updateTransition.animateColor { it }
    var isInitialLoad by remember { mutableStateOf(true) }
    val lazyListState = rememberLazyListState()
//    val prefs = context.commonPrefs
//    val isUnlocked =
//        prefs.collectPrefAsState(CAN_PLAY_SHADER, false)

    val easterEggRepo = LocalEasterEggRepository.current
    val isUnlocked by easterEggRepo?.hasEasterEggEgged?.collectAsState(initial = false)
        ?: remember { mutableStateOf(false) }
    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress) {
            isInitialLoad = false
        }
    }

    val deviceConfiguration = currentDeviceConfiguration()

    val ints = remember { MutableInteractionSource() }
    val columns by rememberUpdatedState(
        newValue = when (deviceConfiguration) {
            MOBILE_PORTRAIT -> 1
            MOBILE_LANDSCAPE -> 2
            TABLET_PORTRAIT -> 2
            TABLET_LANDSCAPE -> 3
            DESKTOP -> 3
        }
    )
    val shdp by animateDpAsState(
        if (ints.collectIsTappedAsState(200L).value) 0.dp else 100.dp,
        animationSpec = tween(500)
    )
    val sh = remember(shdp){ RoundedCornerShape(shdp) }
//    val sh =
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
                    HeadingText("Animations")
                }
            )
        },
    ) {
        LazyIntrinsicGrid(
            state = lazyListState,
            items = animationScreens(isUnlocked),
            columns = if (isSceneActivated) 1 else columns,
            contentPadding = it,
            span = { 1 },
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { index, item ->
            Button(
                onClick = {},
//                shape = mugenButtonShape(ints),
                shape = sh,
                interactionSource = ints
            ) {
                Text("${ints.collectIsTappedAsState().value || ints.collectIsPressedAsState().value}", color = Color.Black)
            }
//            AnimateButtonScale(
//                index = index,
//                text = item.title,
//                hazeState = hazeState,
//                isInitialLoad = isInitialLoad,
//                flip = item.flip,
//                onClick = {
//                    navAction(item.route)
//                }
//            )
        }
    }
}

@Composable
fun mugenButtonShape(interactionSource: MutableInteractionSource): Shape {
    val squish = remember { Animatable(0f) }

    LaunchedEffect(interactionSource) {
        var job: Job? = null
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                job?.cancel()
                job = launch {
                    squish.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessHigh))
                    squish.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium))
                }
            }
        }
    }

    val corner = lerp(8.dp, 24.dp, squish.value)
    return RoundedCornerShape(corner)
}

private const val TapPulseDurationMillis = 100L

@Composable
fun InteractionSource.collectIsTappedAsState(
    pulseDurationMillis: Long = TapPulseDurationMillis,
): State<Boolean> {
    val isTapped = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        var resetJob: Job? = null
        interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                resetJob?.cancel()
                isTapped.value = true
                resetJob = launch {
                    delay(pulseDurationMillis)
                    isTapped.value = false
                }
            }
        }
    }
    return isTapped
}

// temp
@Composable
private fun AnimateButtonScale(
    index: Any,
    text: String,
    hazeState: HazeState,
    onClick: () -> Unit,
    isInitialLoad: Boolean,
    flip: Boolean,
    modifier: Modifier = Modifier,
) {
    TrackRecomposition(
        trackMap = mapOf(
            "index" to index,
            "text" to text,
            "hazeState" to hazeState,
            "onClick" to onClick,
            "isInitialLoad" to isInitialLoad,
            "flip" to flip,
            "modifier" to modifier
        ),
        composableName = "AnimateButtonScale"
    )
    val animatedProgress = remember { Animatable(4.5f) }
    val animatedBlur = remember { Animatable(100f) }
    var hasAnimated by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(index) {
        if (!isInitialLoad || hasAnimated) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)

            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
            hasAnimated = true
        } else {
            animatedProgress.snapTo(1f)
        }
    }
    LaunchedEffect(index) {
        animatedBlur.animateTo(
            targetValue = 0f,
            animationSpec = tween(300)
        )
    }
    PrimaryInteractiveButton(
        hazeState = hazeState,
        flip = flip,
        text = text,
        onClick = onClick,
        scale = { animatedProgress.value },
        blur = { animatedBlur.value },
        modifier = modifier
    )
}


private fun animationScreens(isItUnlocked: Boolean): List<AnimationScreen> {
    return listOfNotNull(
        AnimationScreen(
            title = "Default Apis",
            route = DefaultApisRoutes.DefaultApisLanding
        ),
        AnimationScreen(
            title = "Playground",
            route = PlaygroundRoutes.PlaygroundLandingRoute
        ),
        AnimationScreen(
            title = "Item Placements",
            route = ItemPlacementRoutes.ListItemPlacementRoute
        ),
        AnimationScreen(
            title = "Shaders",
            route = ShaderRoutes.ShaderGraphRoute
        ),
        /*        AnimationScreen(
                    title = "Shapes & Morphing",
                    route = DefaultApisRoutes.AnimateVisibilityRoute
                ),*/
        /*        AnimationScreen(
                    title = "Text",
                    route = DefaultApisRoutes.AnimateVisibilityRoute
                ),*/
        AnimationScreen(
            title = "Canvas",
            route = LandingRoutes.BouncyRope
        ),
//        AnimationScreen(
//            title = "Past Easter Eggs",
//            route = DefaultApisRoutes.DefaultApisLanding
//        ),
//        AnimationScreen(
//            title = "Community",
//            route = LandingRoutes.Community
//        ),
        if (isItUnlocked) AnimationScreen(
            title = "Master Customisations",
            route = LandingRoutes.MasterCustomization,
            flip = false // TODO fix inner shadow recomposition
        ) else null,
        AnimationScreen(
            title = "About",
            route = LandingRoutes.AboutScreen
        )
    )
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    AnimationsTheme {
        ShaderPreviewContent { hazeState ->
            HomeScreen(hazeState = hazeState)
        }
    }

}

//@PreviewScreenSizes
//@Composable
//private fun PreviewHomeScreen() {
//    AnimationsTheme {
//        ShaderPreviewContent { hazeState ->
//            HomeScreen(hazeState = hazeState, color = Color.White)
//        }
//    }
//
//}