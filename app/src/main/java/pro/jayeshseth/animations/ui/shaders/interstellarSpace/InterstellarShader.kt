package pro.jayeshseth.animations.ui.shaders.interstellarSpace

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.RoundedCorner
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.R
import pro.jayeshseth.animations.ui.composables.AnimatedTab
import pro.jayeshseth.animations.ui.composables.CodeBlockWithLineNumbers
import pro.jayeshseth.animations.ui.composables.CopyIconButton
import pro.jayeshseth.animations.ui.composables.FilledIconButton
import pro.jayeshseth.animations.ui.composables.SliderTemplate
import pro.jayeshseth.animations.ui.composables.TabsRow
import pro.jayeshseth.animations.ui.composables.Toggler
import pro.jayeshseth.animations.ui.screens.FeatureUnavailableScreen
import pro.jayeshseth.animations.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.util.AnimationTabs
import pro.jayeshseth.animations.util.Fields
import pro.jayeshseth.animations.util.OnClickLink
import pro.jayeshseth.animations.util.animationTabsList
import pro.jayeshseth.animations.util.lazyNavBarPadding
import pro.jayeshseth.commoncomponents.InteractiveButton

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
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        InterstellarShader(onClickLink, modifier)
    } else {
        FeatureUnavailableScreen("Feature Unavailable for api below ${Build.VERSION_CODES.TIRAMISU}")
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun InterstellarShader(
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val activity = LocalActivity.current
    val context = LocalContext.current
    val window = activity?.window
    val fallbackWindow = (context as Activity).window
    val shader = remember { InterstellarSpaceShader() }
    val shaderBrush = remember { derivedStateOf { ShaderBrush(shader) } }
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
    val controller = WindowInsetsControllerCompat(
        window ?: fallbackWindow,
        view
    )
    var interstellarSpaceState by remember {
        mutableStateOf(
            InterstellarSpaceState()
        )
    }
    val time = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            tween(100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "time"
    )

    if (!view.isInEditMode) {
        DisposableEffect(Unit) {
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())

            onDispose {
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                controller.show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }


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
                        shader.updateResolution(size)
                        shader.updateTime({ time.value })
                        shader.updateMouse({ touchPosition.first }, { touchPosition.second })
                        shader.updateBrightness({ interstellarSpaceState.brightness })
                        shader.updateDarkmatter({ interstellarSpaceState.darkmatter })
                        shader.updateDistFading { interstellarSpaceState.distFading }
                        shader.updateFormuparam { interstellarSpaceState.formuparam }
//                        shader.updateIterations { interstellarSpaceState.iterations }
                        shader.updateSaturation { interstellarSpaceState.saturation }
                        shader.updateSpeed { interstellarSpaceState.speed }
                        shader.updateStepsize { interstellarSpaceState.stepsize }
                        shader.updateTile { interstellarSpaceState.tile }
//                        shader.updateVolSteps { interstellarSpaceState.volSteps }
                        shader.updateZoom { interstellarSpaceState.zoom }
                        drawRect(shaderBrush.value)
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
                                if (controller.systemBarsBehavior == WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE) {
                                    controller.show(WindowInsetsCompat.Type.systemBars())
                                    controller.systemBarsBehavior =
                                        WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
                                } else {
                                    controller.hide(WindowInsetsCompat.Type.systemBars())
                                    controller.systemBarsBehavior =
                                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                                }
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
                        Column(
                            Modifier
                                .graphicsLayer {
                                    alpha = opacity
                                }
                                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                                .fillMaxHeight(.5f)

                        ) {
                            TabsRow(
                                tabsList = animationTabsList(),
                                selectedIndex = pagerState.currentPage,
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState("shared_background"),
                                        animatedVisibilityScope = this@AnimatedContent,
                                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                        boundsTransform = boundsTransform
                                    ),
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
                                            painter = painterResource(id = tab.icon),
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
                                }
                            )
                            HorizontalPager(
                                state = pagerState,
                            ) { page ->
                                when (animationTabsList()[page]) {
                                    AnimationTabs.Settings -> {
                                        Configurations(
                                            interactionSource = sliderInteractionSource,
                                            interstellarSpaceState = { interstellarSpaceState },
                                            onInterstellarSpaceStateChange = {
                                                interstellarSpaceState = it
                                            }
                                        )
                                    }

                                    AnimationTabs.Code -> {
                                        CodePreview(interstellarSpaceState)
                                    }

                                    AnimationTabs.Source -> {
                                        LinksButtons(onClickLink)
                                    }
                                }
                            }
                        }
                    } else {
                        FilledIconButton(
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("shared_background"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    boundsTransform = boundsTransform

                                )
                                .padding(16.dp)
                                .navigationBarsPadding(),
                            innerIconModifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("shared_inner_background"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    boundsTransform = boundsTransform

                                ),
                            onClick = {
                                showControls = !showControls
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.faders),
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
                        value = { data.value },
                        step = { data.step },
                        onValueChange = { value -> data.onValueChange(value) },
                        valueRange = data.valueRange,
                        roundToInt = data.roundToInt,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        interactionSource = interactionSource,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                is Fields.ToggleData -> {
                    Toggler(
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
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(speed = it))
            },
            valueRange = -1f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Brightness",
            value = sliderData.brightness,
            step = 0.00001f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(brightness = it))
            },
            valueRange = -0.03f..0.03f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Form",
            value = sliderData.formuparam,
            step = 0.0001f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(formuparam = it))
            },
            valueRange = 0f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Step Size",
            value = sliderData.stepsize,
            step = 0f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(stepsize = it))
            },
            valueRange = -0.5f..0.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Zoom",
            value = sliderData.zoom,
            step = 0f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(zoom = it))
            },
            valueRange = -50f..50f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Tile",
            value = sliderData.tile,
            step = 0f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(tile = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Dark Matter",
            value = sliderData.darkmatter,
            step = 0f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(darkmatter = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Dist Fading",
            value = sliderData.distFading,
            step = 0f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(distFading = it))
            },
            valueRange = -0.03f..1.5f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Saturation",
            value = sliderData.saturation,
            step = 0f,
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
                    onClick = {
                        scope.launch {
                            clipboardManager.setClipEntry(
                                clipEntry = ClipEntry(
                                    clipData = ClipData(
                                        ClipDescription(
                                            "interstellar Shader Code",
                                            arrayOf("text/plain")
                                        ),
                                        ClipData.Item(code)
                                    )
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxHeight()
                )
                TabsRow(
                    tabsList = tabsList,
                    selectedIndex = selectedTab.intValue,
                    modifier = Modifier.padding(start = 16.dp),
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
                height = 70.dp,
                onClick = { urlLauncher.openUri(blog) }
            )
            InteractiveButton(
                text = "Shader Source",
                height = 70.dp,
                onClick = { urlLauncher.openUri(shaderSource) }
            )
            InteractiveButton(
                text = "Github",
                height = 70.dp,
                onClick = { onClickLink("shaders/interstellarSpace/InterstellarShader.kt") },
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun PreviewInterstellarSpace() {
    AnimationsTheme {
        InterstellarShaderScreen({})
    }
}