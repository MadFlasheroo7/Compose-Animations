package pro.jayeshseth.animations.ui.screens

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import pro.jayeshseth.animations.R
import pro.jayeshseth.animations.ui.composables.DropDownTemplate
import pro.jayeshseth.animations.ui.composables.SliderTemplate
import pro.jayeshseth.animations.ui.composables.Toggler
import pro.jayeshseth.animations.util.DampingRatioList
import pro.jayeshseth.animations.util.EasingList
import pro.jayeshseth.animations.util.StiffnessList
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlideItemPlacement(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val translateX = remember { mutableStateOf(true) }
    val vibrationEffect = remember { mutableStateOf(true) }
    val translateY = remember { mutableStateOf(false) }
    val initialValue = remember { mutableFloatStateOf(300f) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var tweenDuration by remember { mutableIntStateOf(300) }
    var showShadow by remember { mutableStateOf(true) }
    val easingList = remember { EasingList }
    var selectedEasing by remember { mutableStateOf(easingList[0]) }
    val dampingRatioList = remember { DampingRatioList }
    var selectedDampingRatio by remember { mutableStateOf(dampingRatioList[0]) }
    val stiffnessList = remember { StiffnessList }
    var selectedStiffness by remember { mutableStateOf(stiffnessList[0]) }

    HomeScaffold(
        topAppBarScrollBehavior = scrollBehavior,
        modifier = modifier,
        actions = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
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
                    AnimationController(
                        translateX = translateX.value,
                        onTranslateXChanged = { translateX.value = it },
                        translateY = translateY.value,
                        onTranslateYChanged = { translateY.value = it },
                        initialValue = initialValue.floatValue,
                        onInitialValueChange = { initialValue.floatValue = it },
                        vibrationEffect = vibrationEffect.value,
                        onVibrationEffectChanged = { vibrationEffect.value = it },
                        initialValueRange = -1000f..1000f,
                        selectedIndex = selectedIndex,
                        onAnimationSpecClick = { index -> selectedIndex = index },
                        easingList = easingList,
                        selectedEasing = selectedEasing.second,
                        onEasingClick = { selectedEasing = it },
                        tweenDuration = tweenDuration,
                        onTweenDurationChange = { tweenDuration = it },
                        showShadow = showShadow,
                        onShowShadowChanged = { showShadow = it },
                        stiffness = stiffnessList,
                        selectedStiffness = selectedStiffness.second,
                        onStiffnessClick = { selectedStiffness = it },
                        dampingRatio = dampingRatioList,
                        selectedDampingRatio = selectedDampingRatio.second,
                        onDampingRatioClick = { selectedDampingRatio = it },
                    )
                }
            }
            items(100000) {
                ListItem(
                    index = it,
                    isTranslateX = translateX.value,
                    isTranslateY = translateY.value,
                    isVibration = vibrationEffect.value,
                    initialValue = initialValue.floatValue,
                    tweenDuration = tweenDuration,
                    showShadow = showShadow,
                    easing = selectedEasing.first,
                    isTween = selectedIndex == 0,
                    stiffness = selectedStiffness.first,
                    dampingRatio = selectedDampingRatio.first,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationController(
    translateX: Boolean,
    onTranslateXChanged: (Boolean) -> Unit,
    translateY: Boolean,
    onTranslateYChanged: (Boolean) -> Unit,
    vibrationEffect: Boolean,
    onVibrationEffectChanged: (Boolean) -> Unit,
    showShadow: Boolean,
    onShowShadowChanged: (Boolean) -> Unit,
    initialValue: Float,
    onInitialValueChange: (Float) -> Unit,
    initialValueRange: ClosedFloatingPointRange<Float>,
    tweenDuration: Int,
    onTweenDurationChange: (Int) -> Unit,
    selectedIndex: Int,
    onAnimationSpecClick: (Int) -> Unit,
    easingList: List<Pair<Easing, String>>,
    selectedEasing: String,
    onEasingClick: (Pair<Easing, String>) -> Unit,
    dampingRatio: List<Pair<Float, String>>,
    selectedDampingRatio: String,
    onDampingRatioClick: (Pair<Float, String>) -> Unit,
    stiffness: List<Pair<Float, String>>,
    selectedStiffness: String,
    onStiffnessClick: (Pair<Float, String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val animationSpecs = listOf("tween", "spring")
    var expanded by remember { mutableStateOf(false) }
    var dampingRatioExpanded by remember { mutableStateOf(false) }
    var stiffnessExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .animateContentSize(tween(450))
    ) {
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
        Toggler(
            title = "Vibration Effect",
            checked = vibrationEffect,
            onCheckedChanged = onVibrationEffectChanged,
            modifier = Modifier.fillMaxWidth()
        )
        Toggler(
            title = "Shadow",
            checked = showShadow,
            onCheckedChanged = onShowShadowChanged,
            modifier = Modifier.fillMaxWidth()
        )
        SliderTemplate(
            title = "Initial Value",
            value = initialValue,
            step = 0.1f,
            onValueChange = onInitialValueChange,
            valueRange = initialValueRange
        )
        SingleChoiceSegmentedButtonRow {
            animationSpecs.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index, animationSpecs.size),
                    selected = index == selectedIndex,
                    onClick = { onAnimationSpecClick(index) }
                ) {
                    Text(label)
                }
            }
        }
        Crossfade(
            targetState = selectedIndex,
            animationSpec = tween(850, easing = LinearEasing),
            label = "control switcher"
        ) {
            when (it) {
                0 -> {
                    Column {
                        SliderTemplate(
                            title = "Tween Duration",
                            value = tweenDuration.toFloat(),
                            step = 5f,
                            onValueChange = { duration -> onTweenDurationChange(duration.toInt()) },
                            valueRange = 0f..1000f
                        )

                        DropDownTemplate(
                            value = selectedEasing,
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            onDismissRequest = { expanded = false },
                            title = {
                                Text(
                                    text = "Easing",
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            },
                            content = {
                                easingList.forEach { easing ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                easing.second,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            onEasingClick(easing)
                                            expanded = false
                                        },
                                        modifier = Modifier.clip(CircleShape),
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }

                            }
                        )
                    }
                }

                1 -> {
                    Column {
                        DropDownTemplate(
                            value = selectedDampingRatio,
                            expanded = dampingRatioExpanded,
                            onExpandedChange = { dampingRatioExpanded = it },
                            onDismissRequest = { dampingRatioExpanded = false },
                            title = {
                                Text(
                                    text = "Damping Ratio",
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            },
                            content = {
                                dampingRatio.forEach { ratio ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                ratio.second,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            onDampingRatioClick(ratio)
                                            dampingRatioExpanded = false
                                        },
                                        modifier = Modifier.clip(CircleShape),
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }

                            }
                        )
                        DropDownTemplate(
                            value = selectedStiffness,
                            expanded = stiffnessExpanded,
                            onExpandedChange = { stiffnessExpanded = it },
                            onDismissRequest = { stiffnessExpanded = false },
                            title = {
                                Text(
                                    text = "Stiffness",
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            },
                            content = {
                                stiffness.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                it.second,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            onStiffnessClick(it)
                                            stiffnessExpanded = false
                                        },
                                        modifier = Modifier.clip(CircleShape),
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                    )
                                }

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    index: Int,
    initialValue: Float,
    isTranslateX: Boolean,
    isTranslateY: Boolean,
    showShadow: Boolean,
    isTween: Boolean,
    isVibration: Boolean,
    tweenDuration: Int,
    easing: Easing,
    dampingRatio: Float,
    stiffness: Float,
    modifier: Modifier = Modifier
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
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(8.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(12.dp)
                if (showShadow) shadowElevation = animatedProgress.value
                if (showShadow) spotShadowColor = shadowColor
                if (showShadow) ambientShadowColor = shadowColor
                if (isTranslateX) translationX = animatedProgress.value
                if (isTranslateY) translationY = animatedProgress.value
            }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.cat),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "meow",
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}