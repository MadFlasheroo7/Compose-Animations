package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.PrimaryInteractiveButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.layouts.LazyIntrinsicGrid
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
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
    color: Color,
    navAction: OnNavAction = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    val context = LocalContext.current
    val updateTransition = updateTransition(color)
    val transitionColor by updateTransition.animateColor { it }
    var isInitialLoad by remember { mutableStateOf(true) }
    val lazyListState = rememberLazyListState()
//    val prefs = context.commonPrefs
//    val isUnlocked =
//        prefs.collectPrefAsState(CAN_PLAY_SHADER, false)

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress) {
            isInitialLoad = false
        }
    }

    val deviceConfiguration = currentDeviceConfiguration()

    val columns by rememberUpdatedState(
        newValue = when (deviceConfiguration) {
            MOBILE_PORTRAIT -> 1
            MOBILE_LANDSCAPE -> 2
            TABLET_PORTRAIT -> 3
            TABLET_LANDSCAPE -> 4
            DESKTOP -> 5
        }
    )

    val device by remember(deviceConfiguration) {
        mutableStateOf(
            when (deviceConfiguration) {
                MOBILE_PORTRAIT -> "Mobile Portrait"
                MOBILE_LANDSCAPE -> "Mobile Landscape"
                TABLET_PORTRAIT -> "Tablet Portrait"
                TABLET_LANDSCAPE -> "Tablet Landscape"
                DESKTOP -> "Desktop"
            }
        )
    }
//    Log.d("HomeScreen", "isUnlocked: ${isUnlocked.value}")
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
                        text = device,
                        fontSize = 35.sp,
                        fontWeight = FontWeight(750),
                        fontFamily = syneFontFamily()
                    )
                }
            )
        },
    ) {
        LazyIntrinsicGrid(
            state = lazyListState,
//            items = animationScreens(isUnlocked.value),
            items = animationScreens(true),
            columns = columns,
            contentPadding = it,
            span = { 1 },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { index, item ->
            AnimateButtonScale(
                index = index,
                text = item.title,
                hazeState = hazeState,
                isInitialLoad = isInitialLoad,
                flip = item.flip,
                onClick = {
                    navAction(item.route)
                }
            )
        }
    }
}

@Composable
fun DecimalTextField() {
    val state = rememberTextFieldState()

    // Define the transformation logic
    val decimalTransformation = remember {
        InputTransformation {
            // 'this' is the TextFieldBuffer containing the proposed text.

            // 1. Validate characters: Allow only digits and '.'
            if (!asCharSequence().all { it.isDigit() || it == '.' }) {
                revertAllChanges()
                return@InputTransformation
            }

            // 2. Validate format: Ensure at most one decimal point exists
            if (asCharSequence().count { it == '.' } > 1) {
                revertAllChanges()
            }
        }
    }

    BasicTextField(
        state = state,
        // Apply the transformation here
        inputTransformation = decimalTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        // formatting for visibility
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    )
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
        color = Color.White,
        flip = flip,
        text = text,
        onClick = onClick,
        scale = animatedProgress.value,
        blur = animatedBlur.value,
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
        /*        AnimationScreen(
                    title = "Community",
                    route = DefaultApisRoutes.AnimateVisibilityRoute
                ),*/
        AnimationScreen(
            title = "Past Easter Eggs",
            route = DefaultApisRoutes.DefaultApisLanding
//            route = EasterEggsRoutes.EasterEggsLandingRoute
        ),
        /*        AnimationScreen(
                    title = "Community",
                    route = NavDestinations.Community.route
                ),*/
        if (isItUnlocked) AnimationScreen(
            title = "Master Customisations",
            route = LandingRoutes.AboutScreen,
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
            HomeScreen(hazeState = hazeState, color = Color.White)
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