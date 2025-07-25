package pro.jayeshseth.animations.ui.composables

import android.icu.text.DecimalFormat
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import pro.jayeshseth.animations.util.ComposeFriendlyFloat
import kotlin.math.roundToInt

@Composable
fun SliderTemplate(
    title: String,
    value: ComposeFriendlyFloat,
    step: ComposeFriendlyFloat,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    roundToInt: Boolean = true,
    titlePadding: PaddingValues = PaddingValues(),
    style: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val sliderValue = rememberUpdatedState(value)
    val snappedFloatValue =
        rememberUpdatedState(snapSliderValue(valueRange.start, sliderValue.value(), step()))
    ControllerTemplate(
        modifier = modifier,
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(titlePadding),
            ) {
                Text(
                    text = title,
                    style = style,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onBackground,
                ) {
                    Text(
                        text = "${if (roundToInt) snappedFloatValue.value.roundToInt() else snappedFloatValue.value}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        content = {
            Slider(
                value = sliderValue.value(),
                onValueChange = onValueChange,
                onValueChangeFinished = { },
                valueRange = valueRange,
                steps = getSteps(valueRange, step()),
                interactionSource = interactionSource,
                modifier = Modifier
                    .padding(top = 2.dp, bottom = 12.dp)
                    .height(40.dp),
            )
        }
    )
    Rebugger(
        composableName = "SliderTemplate",
        trackMap = mapOf(
            "title" to title,
            "value" to value,
            "step" to step,
            "onValueChange" to onValueChange,
            "valueRange" to valueRange,
            "roundToInt" to roundToInt,
            "titlePadding" to titlePadding,
            "style" to style,
        )
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
    return DecimalFormat("#.##").format(start + snappedDistance).toFloat()
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val value = remember {
        mutableFloatStateOf(0f)
    }
    SliderTemplate(
        title = "title",
        value = { value.floatValue },
        step = { 0.1f },
        onValueChange = {
            value.floatValue = it
        },
        valueRange = 0f..100f
    )
}