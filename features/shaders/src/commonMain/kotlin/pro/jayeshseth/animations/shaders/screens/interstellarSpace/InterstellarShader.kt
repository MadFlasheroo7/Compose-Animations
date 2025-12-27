package pro.jayeshseth.animations.shaders.screens.interstellarSpace

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.AnimationTabs
import pro.jayeshseth.animations.core.model.Fields
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.model.animationTabsList
import pro.jayeshseth.animations.core.model.lazyNavBarPadding
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.CodeBlockWithLineNumbers
import pro.jayeshseth.animations.core.ui.components.CopyIconButton
import pro.jayeshseth.animations.core.ui.components.HazedIconButton
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.SliderTemplate
import pro.jayeshseth.animations.core.ui.components.TabContent
import pro.jayeshseth.animations.core.ui.components.TabsRow
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.shaders.utils.BASE_FEATURE_ROUTE

const val DURATION = 450

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
val boundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = DURATION
        initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds at DURATION
    }
}

@Composable
fun InterstellarShaderScreen(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    InterstellarShader(
        hazeState,
        onClickLink,
        modifier
    )
//    } else {
//        FeatureUnavailableScreen("Feature Unavailable for api below ${Build.VERSION_CODES.TIRAMISU}")
//    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun InterstellarShader(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan
) {
//    val view = LocalView.current
//    val activity = LocalActivity.current
//    val context = LocalContext.current
//    val window = activity?.window
//    val fallbackWindow = (context as Activity).window
//    val shader = remember { InterstellarSpaceShader() }
//    val shaderBrush = remember { derivedStateOf { ShaderBrush(shader) } }
    val pagerState = rememberPagerState { animationTabsList().size }
    val scope = rememberCoroutineScope { Dispatchers.Default }
    var showOverlay by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "time_transition")
    var touchPosition by remember { mutableStateOf(0f to 0f) }
    val sliderInteractionSource = remember { MutableInteractionSource() }
    val isSliderDragged by sliderInteractionSource.collectIsDraggedAsState()

    val opacity by animateFloatAsState(
        targetValue = if (isSliderDragged) 0.5f else 1f,
    )
//    val controller = WindowInsetsControllerCompat(
//        window ?: fallbackWindow,
//        view
//    )
    var interstellarSpaceState by remember {
        mutableStateOf(
            InterstellarSpaceState()
        )
    }

    val animatedSpeed by animateFloatAsState(
        targetValue = interstellarSpaceState.speed,
        animationSpec = tween(1000)
    )

    val animatedBrightness by animateFloatAsState(
        targetValue = interstellarSpaceState.brightness,
        animationSpec = tween(1000)
    )

    val animatedForm by animateFloatAsState(
        targetValue = interstellarSpaceState.formuparam,
        animationSpec = tween(1000)
    )

    val animatedStepSize by animateFloatAsState(
        targetValue = interstellarSpaceState.stepsize,
        animationSpec = tween(1000)
    )

    val animatedZoom by animateFloatAsState(
        targetValue = interstellarSpaceState.zoom,
        animationSpec = tween(1000)
    )

    val animatedTile by animateFloatAsState(
        targetValue = interstellarSpaceState.tile,
        animationSpec = tween(1000)
    )

    val animatedDarkMatter by animateFloatAsState(
        targetValue = interstellarSpaceState.darkmatter,
        animationSpec = tween(1000)
    )

    val animatedDistFading by animateFloatAsState(
        targetValue = interstellarSpaceState.distFading,
        animationSpec = tween(1000)
    )

    val animatedSaturation by animateFloatAsState(
        targetValue = interstellarSpaceState.saturation,
        animationSpec = tween(1000)
    )


    val time = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            tween(100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "time"
    )

//    if (!view.isInEditMode) {
//        DisposableEffect(Unit) {
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//
//            onDispose {
//                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
//                controller.show(WindowInsetsCompat.Type.systemBars())
//            }
//        }
//    }


    SharedTransitionLayout {
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        touchPosition = change.position.x to change.position.y
                    }
                }
                .drawWithCache {
                    onDrawBehind {
//                        shader.updateResolution(size)
//                        shader.updateTime({ time.value })
//                        shader.updateMouse({ touchPosition.first }, { touchPosition.second })
//                        shader.updateBrightness({ animatedBrightness })
//                        shader.updateDarkmatter({ animatedDarkMatter })
//                        shader.updateDistFading { animatedDistFading }
//                        shader.updateFormuparam { animatedForm }
//                        shader.updateSaturation { animatedSaturation }
//                        shader.updateSpeed { animatedSpeed }
//                        shader.updateStepsize { animatedStepSize }
//                        shader.updateTile { animatedTile }
//                        shader.updateZoom { animatedZoom }
//                        drawRect(shaderBrush.value)
                    }
                }
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = {
                        when {
                            showControls && showOverlay -> showControls = false
                            else -> {
                                showOverlay = !showOverlay
//                                if (controller.systemBarsBehavior == WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) {
//                                    controller.show(WindowInsetsCompat.Type.systemBars())
//                                    controller.systemBarsBehavior =
//                                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
//                                } else {
//                                    controller.hide(WindowInsetsCompat.Type.systemBars())
//                                    controller.systemBarsBehavior =
//                                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                                }
                            }
                        }

                    }
                )
        ) {
            AnimatedVisibility(
                visible = showOverlay,
                enter = slideInVertically {
                    it
                } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                )
            }

            AnimatedVisibility(
                visible = showOverlay,
                enter = slideInVertically {
                    it
                } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                AnimatedContent(
                    targetState = showControls,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    if (it) {
                        TabContent(
                            hazeState = hazeState,
                            color = color,
                            tabsList = animationTabsList(),
                            selectedIndex = pagerState.currentPage,
                            tabComponent = { index, tab ->
                                AnimatedTab(
                                    isSelected = pagerState.currentPage == index,
                                    modifier = Modifier
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState("shared_inner_background"),
                                            animatedVisibilityScope = this@AnimatedContent,
                                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                            boundsTransform = boundsTransform
                                        ),
                                    onClick = {
                                        scope.launch(Dispatchers.Main) {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    },
                                ) {
                                    Icon(
                                        painter = painterResource(tab.icon),
                                        contentDescription = tab.title,
                                        tint = LocalContentColor.current,
                                        modifier = Modifier
                                            .then(
                                                if (tab == AnimationTabs.Settings) Modifier.sharedBounds(
                                                    sharedContentState = rememberSharedContentState(
                                                        "shared_config_icon"
                                                    ),
                                                    animatedVisibilityScope = this@AnimatedContent,
                                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                                    boundsTransform = boundsTransform

                                                ) else Modifier
                                            )
//                                                .animateContentSize()
//                                                .animateEnterExit()
//                                                .animateBounds(this@SharedTransitionLayout)
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight(.5f)
                                .graphicsLayer {
                                    alpha = opacity
                                }
                        ) {
                            HorizontalPager(
                                state = pagerState,
                            ) { page ->
                                when (animationTabsList()[page]) {
                                    AnimationTabs.Settings -> {
                                        Configurations(
                                            hazeState = hazeState,
                                            interactionSource = sliderInteractionSource,
                                            interstellarSpaceState = { interstellarSpaceState },
                                            onInterstellarSpaceStateChange = {
                                                interstellarSpaceState = it
                                            }
                                        )
                                    }

                                    AnimationTabs.Code -> {
                                        CodePreview(hazeState, interstellarSpaceState)
                                    }

                                    AnimationTabs.Source -> {
                                        LinksButtons(hazeState, onClickLink)
                                    }
                                }
                            }
                        }
                    } else {
                        HazedIconButton(
                            hazeState = hazeState,
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("shared_background"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    boundsTransform = boundsTransform

                                )
                                .padding(16.dp)
                                .navigationBarsPadding(),
                            onClick = {
                                showControls = !showControls
                            }
                        ) {
                            Icon(
                                painter = painterResource(AnimIcons.settings),
                                contentDescription = "",
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState("shared_config_icon"),
                                        animatedVisibilityScope = this@AnimatedContent,
                                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                        boundsTransform = boundsTransform
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Configurations(
    hazeState: HazeState,
    interstellarSpaceState: () -> InterstellarSpaceState,
    onInterstellarSpaceStateChange: (InterstellarSpaceState) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val sliderData = slidersList(interstellarSpaceState()) { onInterstellarSpaceStateChange(it) }

    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 10.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets(
                    left = WindowInsets.safeGestures.getLeft(
                        LocalDensity.current,
                        LayoutDirection.Ltr
                    ),
                    right = WindowInsets.safeGestures.getRight(
                        LocalDensity.current,
                        LayoutDirection.Ltr
                    ),
                )
            )
    ) {
        items(sliderData) { data ->
            when (data) {
                is Fields.SliderData -> {
                    SliderTemplate(
                        title = data.title,
                        hazeState = hazeState,
                        value = { data.value },
                        step = { data.step },
                        onValueChange = { value -> data.onValueChange(value) },
                        valueRange = data.valueRange,
                        roundToInt = data.roundToInt,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        interactionSource = interactionSource,
                        onIncrement = {
                            data.onValueChange(data.value + 1) //TODO fix this and all the incs and decs
                        },
                        onDecrement = {
                            data.onValueChange(data.value - 1)
                        },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                is Fields.ToggleData -> {
                    Toggler(
                        hazeState = hazeState,
                        title = data.title,
                        checked = data.value,
                        onCheckedChanged = data.onValueChange,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
        lazyNavBarPadding()
    }
}

@Stable
private fun slidersList(
    sliderData: InterstellarSpaceState,
    onSliderDataChange: (InterstellarSpaceState) -> Unit
): List<Fields> {
    return listOf(
        Fields.SliderData(
            title = "Speed",
            value = sliderData.speed,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(speed = it))
            },
            valueRange = -1f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Brightness",
            value = sliderData.brightness,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(brightness = it))
            },
            valueRange = -0.03f..0.03f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Form",
            value = sliderData.formuparam,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(formuparam = it))
            },
            valueRange = 0f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Step Size",
            value = sliderData.stepsize,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(stepsize = it))
            },
            valueRange = -0.5f..0.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Zoom",
            value = sliderData.zoom,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(zoom = it))
            },
            valueRange = -50f..50f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Tile",
            value = sliderData.tile,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(tile = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Dark Matter",
            value = sliderData.darkmatter,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(darkmatter = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Dist Fading",
            value = sliderData.distFading,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(distFading = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Saturation",
            value = sliderData.saturation,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(saturation = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        )
    )
}

@Composable
private fun CodePreview(
    hazeState: HazeState,
    interstellarSpaceState: InterstellarSpaceState,
    modifier: Modifier = Modifier
) {
    val tabsList = remember { mutableStateListOf("AGSL", "Compose") }
    val selectedTab = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboard.current

    var code by remember(selectedTab.intValue) {
        mutableStateOf(
            if (selectedTab.intValue == 0) {
                InterstellarShaderCode(interstellarSpaceState).shaderCode()
            } else {
                InterstellarShaderCode(interstellarSpaceState).composeCode()
            }
        )
    }
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        stickyHeader {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                CopyIconButton(
                    hazeState = hazeState,
                    onClick = {
//                        scope.launch {
//                            clipboardManager.setClipEntry(
//                                clipEntry = ClipEntry(
//                                    clipData = ClipData(
//                                        ClipDescription(
//                                            "interstellar Shader Code",
//                                            arrayOf("text/plain")
//                                        ),
//                                        ClipData.Item(code)
//                                    )
//                                )
//                            )
//                        }
                    },
                    modifier = Modifier.fillMaxHeight()
                )
                TabsRow(
                    tabsList = tabsList,
                    selectedIndex = selectedTab.intValue,
                    modifier = Modifier.padding(start = 16.dp),
                    hazeState = hazeState,
                    tabComponent = { index, tabTitle ->
                        AnimatedTab(
                            isSelected = selectedTab.intValue == index,
                            onClick = {
                                selectedTab.intValue = index
                            },
                        ) {
                            Text(tabTitle, overflow = TextOverflow.Ellipsis, maxLines = 1)
                        }
                    }
                )
            }
        }

        item {
            val text = code.split("\n")
            CodeBlockWithLineNumbers(
                text, modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets(
                            left = WindowInsets.safeGestures.getLeft(
                                LocalDensity.current,
                                LayoutDirection.Ltr
                            ),
                            right = WindowInsets.safeGestures.getRight(
                                LocalDensity.current,
                                LayoutDirection.Ltr
                            ),
                        )
                    )
            )
        }

        lazyNavBarPadding()
    }
}

@Composable
private fun LinksButtons(
    hazeState: HazeState,
    onClickLink: OnClickLink,
) {
    val urlLauncher = LocalUriHandler.current
    val blog = "https://blog.realogs.in/composing-pixels/"
    val shaderSource =
        "https://shaders.skia.org/?id=e0ec9ef204763445036d8a157b1b5c8929829c3e1ee0a265ed984aeddc8929e2"
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            InteractiveButton(
                text = "Blog",
                hazeState = hazeState,
                height = 70.dp,
                onClick = { urlLauncher.openUri(blog) }
            )
            InteractiveButton(
                text = "Shader Source",
                hazeState = hazeState,
                height = 70.dp,
                onClick = { urlLauncher.openUri(shaderSource) }
            )
            InteractiveButton(
                text = "Github",
                hazeState = hazeState,
                height = 70.dp,
                onClick = { onClickLink("$BASE_FEATURE_ROUTE/screens/interstellarSpace/InterstellarShader.kt") },
            )
        }
    }
}


@Preview
@Composable
private fun PreviewInterstellarSpace() {
    MaterialTheme {
        InterstellarShaderScreen(rememberHazeState(), {})
    }
}