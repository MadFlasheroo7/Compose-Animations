package pro.jayeshseth.animations.masterCustomization.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.AnimationContent
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.AnimationCard
import pro.jayeshseth.animations.core.ui.components.HeadingText
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.LocalSharedTransitionScope
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContentWithSharedTransitionScope
import pro.jayeshseth.animations.core.ui.components.TabContent
import pro.jayeshseth.animations.core.ui.utils.currentDeviceConfiguration
import pro.jayeshseth.animations.masterCustomization.components.BackgroundCustomizationContent
import pro.jayeshseth.animations.masterCustomization.components.DemoDropDown
import pro.jayeshseth.animations.masterCustomization.components.DemoInteractiveButtons
import pro.jayeshseth.animations.masterCustomization.components.DemoSlider
import pro.jayeshseth.animations.masterCustomization.components.DemoSwitch
import pro.jayeshseth.animations.masterCustomization.components.ThemeCustomizationContent
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Background
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Buttons
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.DropDownMenu
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Slider
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Switch
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.entries

@Composable
fun MasterCustomization(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {

//    val customizationState = LocalCustomizationState.current
//    val accentColor = Color(customizationState.buttonAccentColorArgb)

    val pagerState = rememberPagerState { MasterCustomizationTabs.entries.size }
    val pagerState2 = rememberPagerState { StandardPages.entries.size }
    val scope = rememberCoroutineScope { Dispatchers.Default }

    var forward by remember { mutableStateOf(false) }

    var visible by rememberSaveable { mutableStateOf(true) }

    val deviceConfig = currentDeviceConfiguration()
    val isWideScreen = deviceConfig.isWideScreen

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            AnimatedContent(
                targetState = isWideScreen,
                transitionSpec = {
                    fadeIn(tween(400)) togetherWith fadeOut(tween(400)) using
                            SizeTransform { initialSize, targetSize ->
                                tween(400, easing = FastOutSlowInEasing)
                            }
                },
                modifier = modifier.fillMaxSize().then(
                    if (!visible) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                visible = !visible
                            }
                        )
                    } else {
                        Modifier
                    }
                )
            ) { isWide ->
                if (isWide) {
                    WideScreenMasterCustomization(
                        hazeState = hazeState,
                        pagerState = pagerState,
                        visibility = visible,
                        onHideAll = {
                            visible = !visible
                        }
//                        color = accentColor,
//                        onClick = {
//                            scope.launch {
//                                val nextPage = if (forward) {
//                                    if (pagerState2.currentPage < entries.size - 1) {
//                                        pagerState2.currentPage + 1
//                                    } else {
//                                        forward = false // Switch direction
//                                        pagerState2.currentPage - 1
//                                    }
//                                } else {
//                                    if (pagerState2.currentPage > 0) {
//                                        pagerState2.currentPage - 1
//                                    } else {
//                                        forward = true // Switch direction
//                                        pagerState2.currentPage + 1
//                                    }
//                                }
//                                pagerState2.animateScrollToPage(nextPage)
//                            }
//
//                        }
//                        pagerState2 = pagerState2
                    )
                } else {
                    StandardMasterCustomization(
                        hazeState = hazeState,
                        configPagerState = pagerState,
                        visibility = visible,
                        onHideAll = {
                            visible = !visible
                        },
                        pagerState2 = pagerState2,
//                        color = accentColor,
//                        onClick = {
//                            scope.launch {
//                                val nextPage = if (forward) {
//                                    if (pagerState2.currentPage < entries.size - 1) {
//                                        pagerState2.currentPage + 1
//                                    } else {
//                                        forward = false // Switch direction
//                                        pagerState2.currentPage - 1
//                                    }
//                                } else {
//                                    if (pagerState2.currentPage > 0) {
//                                        pagerState2.currentPage - 1
//                                    } else {
//                                        forward = true // Switch direction
//                                        pagerState2.currentPage + 1
//                                    }
//                                }
//                                pagerState2.animateScrollToPage(nextPage)
//                            }

//                        },
                    )
                }
            }
        }
    }
}

enum class StandardPages {
    Buttons, Slider, Switch, DropDownMenu, Background
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedContentScope.StandardMasterCustomization(
    hazeState: HazeState,
    configPagerState: PagerState,
    pagerState2: PagerState,
    visibility: Boolean = true,
    onHideAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")
    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(
            animationSpec = tween(2000)
        ),
        exit = fadeOut(
            animationSpec = tween(2000)
        )
    ) {
        with(sharedTransitionScope) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
                    .animateContentSize(tween(300))
            ) {
                // Top Section
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                    ),
                    title = {
                        HeadingText("Master Customization")
                    }
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .sharedBounds(
                            rememberSharedContentState(key = "customization-panel"),
                            this@StandardMasterCustomization,
                            boundsTransform = { _, _ -> tween(300) }
                        )
                ) {
                    CustomizationDemoPager(
                        hazeState = hazeState,
                        demoPagerState = pagerState2,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = this@StandardMasterCustomization,
                        onHideAll = onHideAll,
                        onMouseDown = {
                            scope.launch(Dispatchers.Main) {
                                pagerState2.animateScrollToPage(pagerState2.currentPage - 1)
                            }
                        },
                        onMouseUp = {
                            scope.launch(Dispatchers.Main) {
                                pagerState2.animateScrollToPage(pagerState2.currentPage + 1)
                            }
                        },
                    )
                }

                // Bottom Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .sharedBounds(
                            rememberSharedContentState(key = "tabs-panel"),
                            this@StandardMasterCustomization,
                            boundsTransform = { _, _ -> tween(300) }
                        )
                ) {
                    TabsSection(hazeState, configPagerState)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedVisibilityScope.WideScreenMasterCustomization(
    hazeState: HazeState,
    pagerState: PagerState,
    onHideAll: () -> Unit,
    visibility: Boolean = true
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")

    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(
            animationSpec = tween(2000)
        ),
        exit = fadeOut(
            animationSpec = tween(2000)
        )
    ) {
        with(sharedTransitionScope) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(tween(300))
                    .animateBounds(sharedTransitionScope)
            ) {
                // Left Panel
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .sharedBounds(
                            rememberSharedContentState(key = "customization-panel"),
                            this@WideScreenMasterCustomization,
                            boundsTransform = { _, _ -> tween(300) }
                        )
                ) {
                    Column {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                scrolledContainerColor = Color.Transparent,
                            ),
                            title = {
                                HeadingText("Master Customization")
                            }
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                20.dp,
                                Alignment.CenterVertically
                            ),
                            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                        ) {
                            AnimationCard(
                                index = 0,
                                hazeState = hazeState,
                                onClickLink = {},
                                modifier = Modifier
                                    .padding(32.dp),
                                animationContent = AnimationContent(
                                    title = "Background",
                                    content = {
//                                BackgroundCustomizationContent()
                                    }
                                )
                            )
                            DemoInteractiveButtons(
                                hazeState,
                                modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.InteractiveButtons),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                            )
                            DemoSlider(
                                hazeState, modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Slider),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                            )
                            DemoSwitch(
                                hazeState = hazeState,
                                modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Switch),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
//                        color = color
                            )

                            DemoDropDown(
                                hazeState = hazeState,
                                modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.DropDown),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                            )


                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                InteractiveButton(
                                    hazeState = hazeState,
                                    text = "Hide All",
                                    modifier = Modifier
                                        .wrapContentWidth(unbounded = true)
                                        .width(150.dp),
                                    onClick = onHideAll
                                )
                            }
                        }
                    }
//                CustomizationPager(pagerState2)
                }
//
//            VerticalDivider(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//                    .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
//            )

                // Right Panel
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .sharedBounds(
                            rememberSharedContentState(key = "tabs-panel"),
                            this@WideScreenMasterCustomization,
                            boundsTransform = { _, _ -> tween(300) }
                        )
                ) {
                    TabsSection(hazeState, pagerState)
                }
            }
        }
    }
}

@Composable
private fun CustomizationDemoPager(
    hazeState: HazeState,
    demoPagerState: PagerState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onMouseDown: () -> Unit,
    onMouseUp: () -> Unit,
    onHideAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        HorizontalPager(
            state = demoPagerState,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .aspectRatio(1f)
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
            when (entries[page]) {
                Background -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        InteractiveButton(
                            hazeState = hazeState,
                            text = "Hide All",
                            modifier = Modifier
                                .wrapContentWidth(unbounded = true)
                                .width(150.dp),
                            onClick = onHideAll
                        )
                    }
                }

                Buttons -> {
                    DemoInteractiveButtons(
                        hazeState,
                        modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.InteractiveButtons),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )

                }

                Slider -> {
                    DemoSlider(
                        hazeState = hazeState,
                        modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Slider),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )
                }

                Switch -> {
                    DemoSwitch(
                        hazeState = hazeState,
                        modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Switch),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
//                        color = color
                    )
                }

                DropDownMenu -> {
                    DemoDropDown(
                        hazeState = hazeState,
                        modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.DropDown),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )

                }
            }
        }
    }
}

object MasterCustomizationSharedKeys {
    val InteractiveButtons = "interactive_buttons"
    val Slider = "interactive_buttons"
    val Switch = "interactive_buttons"
    val DropDown = "interactive_buttons"
}

// TODO move to a separate component


@Composable
private fun TabsSection(
    hazeState: HazeState,
    pagerState: PagerState,
//    onClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    TabContent(
        hazeState = hazeState,
        modifier = Modifier.fillMaxSize(),
        tabsList = MasterCustomizationTabs.entries,
        selectedIndex = pagerState.currentPage,
        tabComponent = { index, tab ->
            AnimatedTab(onClick = {
                scope.launch { pagerState.animateScrollToPage(index) }
//                onClick()
            }) {
                Text(tab.name)
            }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (MasterCustomizationTabs.entries[page]) {
                MasterCustomizationTabs.Theme -> ThemeCustomizationContent(hazeState)
                MasterCustomizationTabs.Background -> BackgroundCustomizationContent(hazeState)
            }
        }
    }
}

// CompositionLocal for SharedTransitionScope


enum class MasterCustomizationTabs {
    Theme, Background
}

@Preview
@Composable
private fun PreviewMasterCustomization() {
    ShaderPreviewContent {
        MasterCustomization(it)
    }
}

@Preview
@Composable
private fun PreviewStandardMasterCustomization() {
    ShaderPreviewContentWithSharedTransitionScope { sharedTransitionScope, hazeState ->
        AnimatedContent(true) {
            StandardMasterCustomization(
                hazeState = hazeState,
                configPagerState = rememberPagerState { 6 },
                pagerState2 = rememberPagerState { entries.size },
                onHideAll = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWideScreenMasterCustomization() {
    ShaderPreviewContentWithSharedTransitionScope { sharedTransitionScope, hazeState ->
        AnimatedContent(true) {
            WideScreenMasterCustomization(
                hazeState = hazeState,
                pagerState = rememberPagerState { 6 },
                onHideAll = {}
            )
        }
    }
}