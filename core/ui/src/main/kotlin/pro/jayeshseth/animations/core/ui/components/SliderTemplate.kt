package pro.jayeshseth.animations.core.ui.components

import android.icu.text.DecimalFormat
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.theapache64.rebugger.Rebugger
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.ComposeFriendlyFloat
import pro.jayeshseth.animations.core.model.ComposeFriendlyInt
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderTemplate(
    title: String,
    hazeState: HazeState,
    value: ComposeFriendlyFloat,
    step: ComposeFriendlyInt,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    roundToInt: Boolean = true,
    titlePadding: PaddingValues = PaddingValues(),
    style: TextStyle = LocalTextStyle.current,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HazedIconButton(onClick = onIncrement, hazeState = hazeState) {
                    Icon(
                        imageVector = AnimIcons.add,
                        contentDescription = "increment",
                        tint = Color.White,
                    )
                }
                StretchySlider(
                    value = sliderValue.value,
                    onValueChange = onValueChange,
                    valueRange = valueRange,
                    hazeState = hazeState,
                    interactionSource = interactionSource,
                    steps = step,
                    modifier = Modifier
                        .zIndex(1f)
                        .weight(1f)
                )
                HazedIconButton(onClick = onDecrement, hazeState = hazeState) {
                    Icon(
                        imageVector = ImageVector.vectorResource(AnimIcons.minus),
                        contentDescription = "decrement",
                        tint = Color.White,
                    )
                }
            }
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

@Composable
fun Modifier.negativePadding(horizontal: Dp): Modifier {
    val density = LocalDensity.current
    val px = with(density) {
        horizontal.roundToPx()
    }
    return layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints = constraints.copy(
                minWidth = constraints.minWidth + 2 * px,
                maxWidth = constraints.maxWidth + 2 * px
            )
        )

        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}

private fun snapSliderValue(start: Float, value: Float, step: Int): Float {
    if (step == 0) return value
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
    ShaderPreviewContent {
        SliderTemplate(
            title = "title",
            hazeState = it,
            roundToInt = false,
            value = { value.floatValue },
            step = { 2 },
            onValueChange = {
                value.floatValue = it
            },
            valueRange = 0f..100f,
            onIncrement = {},
            onDecrement = {}
        )
    }
}