package pro.jayeshseth.animations.playground.screens.tweenAndSpring

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.AnimationTabs
import pro.jayeshseth.animations.core.model.DampingRatioList
import pro.jayeshseth.animations.core.model.EasingList
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.model.StiffnessList
import pro.jayeshseth.animations.core.model.animationTabsList
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.LocalSharedTransitionScope
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.TabContent
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration
import pro.jayeshseth.animations.core.ui.utils.currentDeviceConfiguration
import pro.jayeshseth.animations.playground.components.CodePreview
import pro.jayeshseth.animations.playground.components.PreviewGrid
import pro.jayeshseth.animations.playground.model.TweenAndSpringSpecState
import pro.jayeshseth.animations.playground.model.TweenNSpringSpec


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AnimatedContentScope.WideTweenAndSpring(
    replay: Boolean,
    hazeState: HazeState,
    configPagerState: PagerState,
    previewPagerState: PagerState,
    state: TweenAndSpringSpecState,
    previewTabs: List<PlaygroundPreviewTabs>,
    onReplayClick: () -> Unit,
    onTabClick: (index: Int) -> Unit,
    onClickLink: OnClickLink,
    selectedIndex: Int,
    onSpecChange: (Int) -> Unit,
    onStateUpdate: (TweenAndSpringSpecState) -> Unit,
    onMouseUp: () -> Unit,
    onMouseDown: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")

    val deviceConfiguration = currentDeviceConfiguration()

    print(deviceConfiguration)
    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(tween(300))
                .animateBounds(sharedTransitionScope)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
//                    .fillMaxSize()
            ) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                    ),
                    title = {
                        Text(
                            text = "Tween And Spring",
                            fontSize = 35.sp,
                            fontWeight = FontWeight(750),
                            textAlign = TextAlign.Center,
                            fontFamily = syneFontFamily()
                        )
                    }
                )

                when {
                    deviceConfiguration == DeviceConfiguration.MOBILE_LANDSCAPE -> {
                        HorizontalPager(
                            state = previewPagerState,
//                            modifier = Modifier.weight(1f)
                        ) { page ->
                            when (previewTabs[page]) {
                                PlaygroundPreviewTabs.Chart -> {
                                    AnimationChart(
                                        state = state,
                                        replay = replay,
                                        animationType = state.selectedSpec
                                    )
                                }

                                PlaygroundPreviewTabs.Preview -> {
                                    PreviewGrid(
                                        state = state,
                                        replay = replay,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .safeContentPadding()
                                    )
                                }
                            }
                        }
                    }

                    else -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                        ) {
                            AnimationChart(
                                state = state,
                                replay = replay,
                                animationType = state.selectedSpec,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                            )

                            PreviewGrid(
                                state = state,
                                replay = replay,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                                    .safeContentPadding()
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }

                InteractiveButton(
                    hazeState = hazeState,
                    text = "Replay Animation",
                    height = 45.dp,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 12.dp),
                    onClick = onReplayClick
                )
            }

            TabContent(
                color = color,
                modifier = Modifier.weight(1f),
                hazeState = hazeState,
                tabsList = animationTabsList(),
                selectedIndex = configPagerState.currentPage,
                tabComponent = { index, tab ->
                    AnimatedTab(
                        onClick = {
                            onTabClick(index)
                        },
                    ) {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = tab.title,
                            tint = LocalContentColor.current
                        )
                    }
                }
            ) {
                HorizontalPager(
                    state = configPagerState,
                    modifier = Modifier.fillMaxSize()
                        .pointerInput(Unit) { // 1. Use pointerInput scope
                            awaitPointerEventScope {
                                while (true) {
                                    // 2. Await the next event
                                    val event = awaitPointerEvent()
                                    event.buttons.isPrimaryPressed

                                    // 3. Check if it is a Scroll event (Mouse Wheel or Trackpad)
                                    if (event.type == PointerEventType.Scroll) {
                                        val change = event.changes.first()
                                        val delta = change.scrollDelta


                                        delta.y

                                        // delta.x = Horizontal scroll (Shift+Wheel or Swipe Left/Right)
                                        // delta.y = Vertical scroll (Wheel or Swipe Up/Down)

                                        println("Scrolled: $delta")
                                        if (delta.y >= 1.0f)
                                            onMouseUp()
                                        else
                                            onMouseDown()

                                        // Optional: Consume the event so parents don't scroll
                                        change.consume()
                                    }
                                }
                            }
                        }

                ) { page ->
                    when (animationTabsList()[page]) {
                        AnimationTabs.Settings -> {
                            Configurations(
                                state = state,
                                hazeState = hazeState,
                                selectedIndex = selectedIndex,
                                onSpecChange = onSpecChange,
                                onStateUpdate = onStateUpdate
                            )
                        }

                        AnimationTabs.Code -> {
                            CodePreview(hazeState, state)
                        }

                        AnimationTabs.Source -> {
                            LinksButtons(hazeState, onClickLink)
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun WideTweenAndSpringPreview() {
    val previewTabs = PlaygroundPreviewTabs.entries
    val configPagerState = rememberPagerState { animationTabsList().size }
    val previewPagerState = rememberPagerState { previewTabs.size }
    val easingList = EasingList
    val dampingRatioList = DampingRatioList
    val stiffnessList = StiffnessList
    var state by remember {
        mutableStateOf(
            TweenAndSpringSpecState(
                specs = TweenNSpringSpec.entries,
                selectedSpec = TweenNSpringSpec.Tween,
                durationMillis = 1000,
                delayMillis = 0,
                easingList = easingList,
                easing = easingList[0],
                dampingRatioList = dampingRatioList,
                dampingRatio = dampingRatioList[0],
                stiffnessList = stiffnessList,
                stiffness = stiffnessList[0],
                cubicA = 0.1f,
                cubicB = 0.2f,
                cubicC = 0.3f,
                cubicD = 0.4f,
            )
        )
    }
    var replay by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    ShaderPreviewContent { hazeState ->
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this
            ) {
                AnimatedContent(
                    targetState = true,
                    transitionSpec = {
                        fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                    }
                ) { _ ->
                    WideTweenAndSpring(
                        replay = replay,
                        hazeState = hazeState,
                        configPagerState = configPagerState,
                        previewPagerState = previewPagerState,
                        state = state,
                        previewTabs = previewTabs,
                        onReplayClick = { replay = !replay },
                        onTabClick = {},
                        onClickLink = {},
                        selectedIndex = selectedIndex,
                        onSpecChange = { selectedIndex = it },
                        onStateUpdate = { state = it },
                        onMouseUp = {},
                        onMouseDown = {}
                    )
                }
            }
        }
    }
}
