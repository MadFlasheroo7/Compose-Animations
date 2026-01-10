package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.ComposeFriendlyFloat
import pro.jayeshseth.animations.core.model.ComposeFriendlyInt
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import kotlin.math.roundToInt

/**
 * A customizable slider component wrapped in a template that includes a title,
 * the current value display, and increment/decrement buttons. This component
 * is designed to be visually integrated with a background using a haze effect.
 *
 * It combines a `StretchySlider` with `HazedIconButton`s for a cohesive look and feel.
 * The layout is managed by `ControllerTemplate`.
 *
 * @param title The text to be displayed as the title for the slider.
 * @param hazeState The [HazeState] from the `dev.chrisbanes.haze` library, used to apply a glassmorphism/haze effect to the UI elements.
 * @param value A lambda returning the current value of the slider. Using a lambda (`ComposeFriendlyFloat`) helps ensure stability and recomposition optimization.
 * @param step A lambda returning the number of discrete steps the slider can have. If 0, the slider is continuous. `ComposeFriendlyInt` is used for stability.
 * @param onValueChange A callback that is invoked when the slider's value changes. The new value is passed as a parameter.
 * @param valueRange The range of values the slider can represent, from a minimum to a maximum.
 * @param modifier The [Modifier] to be applied to the `ControllerTemplate` container.
 * @param roundToInt A boolean flag that, when true, rounds the displayed value to the nearest integer.
 * @param titlePadding The padding to be applied around the title row.
 * @param style The [TextStyle] for the title text.
 * @param onIncrement A lambda to be executed when the increment (+) button is clicked.
 * @param onDecrement A lambda to be executed when the decrement (-) button is clicked.
 * @param interactionSource The [MutableInteractionSource] for observing and controlling the slider's interactions.
 */
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
//    val snappedFloatValue =
//        rememberUpdatedState(snapSliderValue(valueRange.start, sliderValue.value(), step()))
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
//                        text = "${if (roundToInt) snappedFloatValue.value.roundToInt() else snappedFloatValue.value}",
                        text = "${if (roundToInt) value().roundToInt() else value()}",
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
                        painter = painterResource(AnimIcons.minus),
                        contentDescription = "decrement",
                        tint = Color.White,
                    )
                }
            }
        }
    )
//    Rebugger(
//        composableName = "SliderTemplate",
//        trackMap = mapOf(
//            "title" to title,
//            "value" to value,
//            "step" to step,
//            "onValueChange" to onValueChange,
//            "valueRange" to valueRange,
//            "roundToInt" to roundToInt,
//            "titlePadding" to titlePadding,
//            "style" to style,
//        )
//    )
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