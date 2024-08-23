package pro.jayeshseth.animations.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SliderTemplate(
    title: String,
    value: Float,
    step: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    val sliderValue = rememberUpdatedState(value)
    ControllerTemplate(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onBackground,
                ) {
                    val snappedValue = snapSliderValue(valueRange.start, sliderValue.value, step)
                    Text(
                        text = "${snappedValue.roundToInt()}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        content = {
            Slider(
                value = sliderValue.value,
                onValueChange = onValueChange,
                onValueChangeFinished = { },
                valueRange = valueRange,
                steps = getSteps(valueRange, step),
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 12.dp)
                    .height(24.dp),
            )
        }
    )
}

private fun getSteps(valueRange: ClosedFloatingPointRange<Float>, step: Float): Int {
    if (step == 0f) return 0
    val start = valueRange.start
    val end = valueRange.endInclusive
    val steps = ((end - start) / step).toInt()
    require(start + step * steps == end) {
        "value range must be a multiple of step"
    }
    return steps - 1
}

private fun snapSliderValue(start: Float, value: Float, step: Float): Float {
    if (step == 0f) return value
    val distance = value - start
    val stepsFromStart = (distance / step).roundToInt()
    val snappedDistance = stepsFromStart * step
    return start + snappedDistance
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val value = remember {
        mutableFloatStateOf(0f)
    }
    SliderTemplate(
        title = "title",
        value = value.floatValue,
        step = 0.1f,
        onValueChange = {
            value.floatValue = it
        },
        valueRange = 0f..100f
    )
}