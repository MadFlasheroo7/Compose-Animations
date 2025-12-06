package pro.jayeshseth.animations.playground.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.DampingRatioOption
import pro.jayeshseth.animations.core.model.EasingOption
import pro.jayeshseth.animations.core.model.StiffnessOption
import pro.jayeshseth.animations.core.ui.components.DropDownTemplate
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.SliderTemplate
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.playground.model.TweenAndSpringSpecState
import pro.jayeshseth.animations.playground.model.TweenNSpringSpec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpringOptions(
    state: TweenAndSpringSpecState,
    hazeState: HazeState,
    onStateUpdate: (TweenAndSpringSpecState) -> Unit,
    modifier: Modifier = Modifier
) {
    var dampingRatioExpanded by remember { mutableStateOf(false) }
    var stiffnessExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
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
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        Toggler(
            title = "Custom Values",
            checked = state.useCustomDampingRatioAndStiffness,
            onCheckedChanged = { onStateUpdate(state.copy(useCustomDampingRatioAndStiffness = it)) },
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedContent(
            targetState = state.useCustomDampingRatioAndStiffness,
            transitionSpec = {
                slideInVertically { -it } togetherWith (slideOutVertically { -it })
            }
        ) { use ->
            if (!use) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    DropDownTemplate(
                        value = state.dampingRatio.name,
                        expanded = dampingRatioExpanded,
                        onExpandedChange = { dampingRatioExpanded = it },
                        onDismissRequest = { dampingRatioExpanded = false },
                        title = {
                            Text(
                                text = "Damping Ratio",
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        content = {
                            state.dampingRatioList.forEach { ratio ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            ratio.name,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    },
                                    onClick = {
                                        onStateUpdate(state.copy(dampingRatio = ratio))
                                        dampingRatioExpanded = false
                                    },
                                    modifier = Modifier.clip(CircleShape),
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }

                        }
                    )
                    DropDownTemplate(
                        value = state.stiffness.name,
                        expanded = stiffnessExpanded,
                        onExpandedChange = { stiffnessExpanded = it },
                        onDismissRequest = { stiffnessExpanded = false },
                        title = {
                            Text(
                                text = "Stiffness",
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        },
                        content = {
                            state.stiffnessList.forEach { stiffness ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            stiffness.name,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    },
                                    onClick = {
                                        onStateUpdate(state.copy(stiffness = stiffness))
                                        stiffnessExpanded = false
                                    },
                                    modifier = Modifier.clip(CircleShape),
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    )
                }
            } else {
                Column {
                    SliderTemplate(
                        title = "Damping Ratio",
                        hazeState = hazeState,
                        roundToInt = false,
                        value = { state.customDampingRatio },
                        onValueChange = {
                            onStateUpdate(
                                state.copy(customDampingRatio = it)
                            )
                        },
                        step = { 0 },
                        valueRange = 0.1f..2f,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        onIncrement = {
                            onStateUpdate(
                                state.copy(customDampingRatio = state.customDampingRatio + 0.1f)
                            )
                        },
                        onDecrement = {
                            onStateUpdate(
                                state.copy(customDampingRatio = state.customDampingRatio - 0.1f)
                            )
                        },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    SliderTemplate(
                        title = "Stiffness",
                        hazeState = hazeState,
                        roundToInt = false,
                        value = { state.customStiffness },
                        onValueChange = {
                            onStateUpdate(
                                state.copy(customStiffness = it)
                            )
                        },
                        step = { 0 },
                        valueRange = 0.1f..10_000f,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        onIncrement = {
                            onStateUpdate(
                                state.copy(customStiffness = state.customStiffness + 100f)
                            )
                        },
                        onDecrement = {
                            onStateUpdate(
                                state.copy(customStiffness = state.customStiffness - 100f)
                            )
                        },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PreviewSpringOptions() {
    ShaderPreviewContent() {
        SpringOptions(
            hazeState = it,
            state = TweenAndSpringSpecState(
                specs = TweenNSpringSpec.entries,
                selectedSpec = TweenNSpringSpec.Tween,
                durationMillis = 1000,
                delayMillis = 0,
                easingList = emptyList(),
                easing = EasingOption(
                    name = "Linear",
                    easing = { it }
                ),
                dampingRatioList = emptyList(),
                dampingRatio = DampingRatioOption(
                    name = "No Damping",
                    dampingRatio = 0f
                ),
                stiffnessList = emptyList(),
                stiffness = StiffnessOption(
                    name = "Medium",
                    stiffness = 500f
                ),
                cubicA = 0.1f,
                cubicB = 0.2f,
                cubicC = 0.3f,
                cubicD = 0.4f,
            ),
            onStateUpdate = {}
        )
    }
}