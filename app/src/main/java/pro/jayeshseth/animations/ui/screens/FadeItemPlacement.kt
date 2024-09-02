package pro.jayeshseth.animations.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import pro.jayeshseth.animations.R
import pro.jayeshseth.animations.navigation.OnClickLink
import pro.jayeshseth.animations.ui.composables.AnimationController
import pro.jayeshseth.animations.ui.composables.AnimationItem
import pro.jayeshseth.animations.ui.composables.Toggler
import pro.jayeshseth.animations.util.AnimationControllerState
import pro.jayeshseth.animations.util.AudioPlayer
import pro.jayeshseth.animations.util.DampingRatioList
import pro.jayeshseth.animations.util.EasingList
import pro.jayeshseth.animations.util.StiffnessList
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

/**
 * Fade item placement animation by fading the opacity of the item
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FadeItemPlacement(
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val fade = remember { mutableStateOf(true) }
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
                showShadow = true,
                shepardTone = true,
                initialValue = 0f,
                initialValueRange = -1f..1f,
                tweenDuration = 300,
                selectedIndex = 0,
                easingList = easingList,
                easing = selectedEasing,
                dampingRatioList = dampingRatioList,
                dampingRatio = selectedDampingRatio,
                stiffnessList = stiffnessList,
                stiffness = selectedStiffness,
                initialValueSteps = 0.1f
            )
        )
    }

    if (state.value.shepardTone) {
        LaunchedEffect(lazyListState.isScrollInProgress) {
            if (lazyListState.isScrollInProgress) {
                AudioPlayer.play(context, R.raw.shepard_tone)
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
                onClickLink("screens/SlideItemPlacement.kt")
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
                    "Fade"
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
                    FadeAnimationController(
                        state = state.value,
                        onStateUpdate = { state.value = it },
                        fade = fade.value,
                        onFadeChanged = { fade.value = it }
                    )
                }
            }
            items(100000) {
                FadeListItem(
                    index = it,
                    fade = fade.value,
                    isVibration = state.value.vibrationEffect,
                    initialValue = state.value.initialValue,
                    tweenDuration = state.value.tweenDuration,
                    showShadow = state.value.showShadow,
                    easing = state.value.easing.first,
                    isTween = state.value.selectedIndex == 0,
                    stiffness = state.value.stiffness.first,
                    dampingRatio = state.value.dampingRatio.first,
                )
            }
        }
    }
}

@Composable
private fun FadeAnimationController(
    state: AnimationControllerState,
    onStateUpdate: (AnimationControllerState) -> Unit,
    fade: Boolean,
    onFadeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimationController(
        state = state,
        onStateUpdate = onStateUpdate,
        modifier = modifier,
        roundToInt = false,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Toggler(
                    title = "Fade",
                    checked = fade,
                    onCheckedChanged = onFadeChanged
                )
            }
        },
    )
}

@Composable
private fun FadeListItem(
    index: Int,
    initialValue: Float,
    fade: Boolean,
    showShadow: Boolean,
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
                targetValue = 1f,
                animationSpec = tween(tweenDuration, easing = easing)
            )
        } else {
            animatedProgress.animateTo(
                targetValue = 1f,
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
                if (fade) alpha = animatedProgress.value
            }
    )
}