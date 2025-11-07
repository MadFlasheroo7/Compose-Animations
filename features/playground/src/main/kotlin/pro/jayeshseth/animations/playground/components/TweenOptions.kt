package pro.jayeshseth.animations.playground.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.core.ui.components.DropDownTemplate
import pro.jayeshseth.animations.core.ui.components.SliderTemplate
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.playground.model.TweenAndSpringSpecState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TweenOptions(
    state: TweenAndSpringSpecState,
    onStateUpdate: (TweenAndSpringSpecState) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
    ) {
        Toggler(
            title = "Custom Easing",
            checked = state.useCustomEasing,
            onCheckedChanged = { onStateUpdate(state.copy(useCustomEasing = it)) },
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedContent(
            targetState = state.useCustomEasing,
            modifier = Modifier.fillMaxWidth(),
            transitionSpec = {
                slideInVertically { -it } togetherWith (slideOutVertically { -it })
            }
        ) {
            if (it) {
                Column {
                    SliderTemplate(
                        title = "Cubic A",
                        roundToInt = false,
                        value = { state.cubicA },
                        onValueChange = {
                            onStateUpdate(
                                state.copy(cubicA = it)
                            )
                        },
                        step = { 0.1f },
                        valueRange = 0f..2f,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    SliderTemplate(
                        title = "Cubic B",
                        roundToInt = false,
                        value = { state.cubicB },
                        onValueChange = {
                            onStateUpdate(
                                state.copy(cubicB = it)
                            )
                        },
                        step = { 0.1f },
                        valueRange = 0f..2f,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    SliderTemplate(
                        title = "Cubic C",
                        roundToInt = false,
                        value = { state.cubicC },
                        onValueChange = {
                            onStateUpdate(
                                state.copy(cubicC = it)
                            )
                        },
                        step = { 0.1f },
                        valueRange = 0f..2f,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    SliderTemplate(
                        title = "Cubic D",
                        roundToInt = false,
                        value = { state.cubicD },
                        onValueChange = {
                            onStateUpdate(
                                state.copy(cubicD = it)
                            )
                        },
                        step = { 0.1f },
                        valueRange = 0f..2f,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }


            } else {
                DropDownTemplate(
                    value = state.easing.name,
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    onDismissRequest = { expanded = false },
                    title = {
                        Text(
                            text = "Easing",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    content = {
                        state.easingList.forEach { easing ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        easing.name,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                onClick = {
                                    onStateUpdate(state.copy(easing = easing))
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
        SliderTemplate(
            title = "Duration Millis",
            value = { state.durationMillis.toFloat() },
            onValueChange = {
                onStateUpdate(
                    state.copy(durationMillis = it.toInt())
                )
            },
            step = { 1f },
            valueRange = 0f..5000f,
            titlePadding = PaddingValues(vertical = 10.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
        SliderTemplate(
            title = "Delay Millis",
            value = { state.delayMillis.toFloat() },
            onValueChange = {
                onStateUpdate(
                    state.copy(delayMillis = it.toInt())
                )
            },
            step = { 1f },
            valueRange = 0f..2000f,
            titlePadding = PaddingValues(vertical = 10.dp),
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}