package pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import pro.jayeshseth.animations.core.model.AnimationControllerState
import pro.jayeshseth.animations.core.model.AudioPlayer
import pro.jayeshseth.animations.core.model.DampingRatioList
import pro.jayeshseth.animations.core.model.EasingList
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.model.StiffnessList
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.core.ui.media.AnimMedia
import pro.jayeshseth.animations.itemPlacements.components.AnimationController
import pro.jayeshseth.animations.itemPlacements.components.AnimationItem
import pro.jayeshseth.animations.itemPlacements.utils.BASE_FEATURE_ROUTE
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

/**
 * Slide item placement animation by translating X and Y position of the item
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlideItemPlacement(
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val translateX = remember { mutableStateOf(true) }
    val translateY = remember { mutableStateOf(false) }
    val easingList = remember { EasingList }
    val selectedEasing by remember { mutableStateOf(easingList[0]) }
    val dampingRatioList = remember { DampingRatioList }
    val selectedDampingRatio by remember { mutableStateOf(dampingRatioList[0]) }
    val stiffnessList = remember { StiffnessList }
    val selectedStiffness by remember { mutableStateOf(stiffnessList[0]) }
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current

    val state = remember {
        mutableStateOf(
            AnimationControllerState(
                vibrationEffect = true,
                showShadow = false,
                shepardTone = false,
                initialValue = 300f,
                initialValueRange = -1000f..1000f,
                blurValue = 200f,
                blurValueRange = 0f..500f,
                blurEffect = true,
                tweenDuration = 300,
                selectedIndex = 0,
                easingList = easingList,
                easing = selectedEasing,
                dampingRatioList = dampingRatioList,
                dampingRatio = selectedDampingRatio,
                stiffnessList = stiffnessList,
                stiffness = selectedStiffness,
                initialValueSteps = 0.1f,
                blurValueSteps = 0.1f,
                delay = 1000
            )
        )
    }

    if (state.value.shepardTone) {
        LaunchedEffect(lazyListState.isScrollInProgress) {
            if (lazyListState.isScrollInProgress) {
                AudioPlayer.play(context, AnimMedia.shepardTone)
            } else {
                AudioPlayer.stop()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            AudioPlayer.stop()
        }
    }

    HomeScaffold(
        topAppBarScrollBehavior = scrollBehavior,
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = {
                onClickLink("$BASE_FEATURE_ROUTE/screens/listItemPlacements/SlideItemPlacement.kt")
            }) {
                Icon(imageVector = Icons.Rounded.Link, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = null)
            }
        },
        title = {
            if (!isVisible) {
                Text(
                    "Slide In / Out"
                )
            } else {
                Text(
                    "Animation Controller"
                )
            }
        }
    ) {
        StatusBarAwareThemedLazyColumn(
            state = lazyListState,
            statusBarColor = Color.Transparent
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = it.calculateTopPadding()))
            }
            item {
                AnimatedVisibility(
                    isVisible,
                    enter = slideInVertically(tween(500)),
                    exit = slideOutVertically(tween(500)),
                ) {
                    SlideAnimationController(
                        state = state.value,
                        onStateUpdate = { state.value = it },
                        translateX = translateX.value,
                        onTranslateXChanged = { translateX.value = it },
                        translateY = translateY.value,
                        onTranslateYChanged = { translateY.value = it },
                    )
                }
            }
            items(100000) {
                SlideListItem(
                    index = it,
                    isTranslateX = translateX.value,
                    isTranslateY = translateY.value,
                    blurValue = state.value.blurValue,
                    doBlur = state.value.blurEffect,
                    isVibration = state.value.vibrationEffect,
                    initialValue = state.value.initialValue,
                    tweenDuration = state.value.tweenDuration,
                    showShadow = state.value.showShadow,
                    easing = state.value.easing.easing,
                    isTween = state.value.selectedIndex == 0,
                    stiffness = state.value.stiffness.stiffness,
                    dampingRatio = state.value.dampingRatio.dampingRatio,
                )
            }
        }
    }
}

@Composable
private fun SlideAnimationController(
    state: AnimationControllerState,
    onStateUpdate: (AnimationControllerState) -> Unit,
    translateX: Boolean,
    onTranslateXChanged: (Boolean) -> Unit,
    translateY: Boolean,
    onTranslateYChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimationController(
        state = state,
        onStateUpdate = onStateUpdate,
        modifier = modifier,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Toggler(
                    title = "Translate X",
                    checked = translateX,
                    onCheckedChanged = onTranslateXChanged
                )
                Toggler(
                    title = "Translate Y",
                    checked = translateY,
                    onCheckedChanged = onTranslateYChanged
                )
            }
        },
    )
}

@Composable
private fun SlideListItem(
    index: Int,
    initialValue: Float,
    blurValue: Float,
    isTranslateX: Boolean,
    isTranslateY: Boolean,
    showShadow: Boolean,
    doBlur: Boolean,
    isTween: Boolean,
    isVibration: Boolean,
    tweenDuration: Int,
    easing: Easing,
    dampingRatio: Float,
    stiffness: Float,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = isSystemInDarkTheme()
) {
    val context = LocalContext.current
    val animatedProgress = remember { Animatable(initialValue) }
    val animatedBlur = remember { Animatable(blurValue) }
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    val shadowColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(index) {
        if (isVibration) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator!!.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
            }
        }
        if (isTween) {
            animatedProgress.animateTo(
                targetValue = 0f,
                animationSpec = tween(tweenDuration, easing = easing)
            )
        } else {
            animatedProgress.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness,
                )
            )
        }
    }
    LaunchedEffect(index) {
        if (doBlur && isTween) {
            animatedBlur.animateTo(
                targetValue = 0f,
                animationSpec = tween(tweenDuration, easing = easing)
            )
        } else {
            animatedBlur.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness,
                )
            )
        }

    }
    AnimationItem(
        modifier = modifier
            .padding(8.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(12.dp)
                if (showShadow) shadowElevation = animatedProgress.value
                if (showShadow && isDarkMode) spotShadowColor = shadowColor
                if (showShadow && isDarkMode) ambientShadowColor = shadowColor
                if (isTranslateX) translationX = animatedProgress.value
                if (isTranslateY) translationY = animatedProgress.value
                if (doBlur && animatedBlur.value > 0f) {
                    renderEffect = BlurEffect(
                        animatedBlur.value,
                        animatedBlur.value,
                        TileMode.Decal
                    )
                }
            }
    )
}