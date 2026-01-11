package pro.jayeshseth.animations.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
private fun Preview() {
    LazyColumn {
        addPieChart()
    }
}


private fun LazyListScope.addPieChart() {
    val data = listOf(
        PieChartData(
            25f, listOf(Color(0xFFFFAFBD), Color(0xFFFFC3A0)).asGradientChartColor(), label = "25%"
        ),
        PieChartData(
            35f, listOf(Color(0xFFf12711), Color(0xFFf5af19)).asGradientChartColor(), label = "35%"
        ),
        PieChartData(
            20f, listOf(Color(0xFFbc4e9c), Color(0xFFf80759)).asGradientChartColor(), label = "20%"
        ),
        PieChartData(
            10f, listOf(Color(0xFF11998e), Color(0xFF38ef7d)).asGradientChartColor(), label = "10%"
        ),
        PieChartData(
            10f, listOf(Color(0xFF11998e), Color(0xFF385f7d)).asGradientChartColor(), label = "10%"
        ),
    )

    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                onPieChartSliceClick = {
                    println("Clicked on slice with data: $it")
                },
                isDonutChart = false,
                data = { data },
                modifier = Modifier
                    .size(300.dp)
                    .fillParentMaxWidth()
                    .padding(4.dp)
            )
        }
    }
    item {
        Box(
            modifier = Modifier
                .fillParentMaxWidth()
                .size(300.dp)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            PieChart(
                onPieChartSliceClick = {
                    println("Clicked on slice with data: $it")
                },
                isDonutChart = true,
                data = { data },
                modifier = Modifier
                    .size(300.dp)
                    .fillParentMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}


internal const val COMPLETE_CIRCLE_DEGREE = 360
internal const val STRAIGHT_ANGLE = 180

/**
 * Composable function to draw a Pie Chart.
 *
 * @param data Lambda function that returns a list of PieChartData representing the slices of the pie chart.
 * @param modifier optional Modifier to be applied to the PieChart.
 * @param isDonutChart Boolean indicating if the chart should be a donut chart.
 * @param onPieChartSliceClick Lambda function to be called when a slice of the pie chart is clicked.
 */
@Composable
fun PieChart(
    data: () -> List<PieChartData>,
    modifier: Modifier = Modifier,
    isDonutChart: Boolean = false,
    onPieChartSliceClick: (PieChartData?) -> Unit = {}
) {
    PieChartContent(
        data = data,
        isDonutChart = isDonutChart,
        modifier = modifier,
        onPieChartSliceClick = onPieChartSliceClick,
    )
}


// You would have your PieChartData data class defined somewhere
// data class PieChartData(...)

@Composable
private fun PieChartContent(
    data: () -> List<PieChartData>,
    isDonutChart: Boolean = false,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    onPieChartSliceClick: (PieChartData?) -> Unit = {} // Can be null on deselect
) {
    val pieData = data()
    var selectedSlice by remember { mutableStateOf(-1) }
    val totalValue = remember(pieData) { pieData.sumOf { it.value.toDouble() }.toFloat() }
    val proportions = remember(pieData) { pieData.map { it.value / totalValue } }
    val angles = remember(proportions) { proportions.map { 360 * it } }
    val textMeasurer = rememberTextMeasurer()

    // ✨ 1. Animation Setup: Create a list of Animatable floats, one for each slice's scale.
    val animatedScales = remember(pieData) {
        pieData.map { Animatable(1.0f) }
    }
    val enterAnimationProgress = remember { Animatable(0f) }
    // ✨ 2. LaunchedEffect: This coroutine runs whenever `selectedSlice` changes.
    LaunchedEffect(selectedSlice) {
        // Animate all slices to their target scale
        animatedScales.indices.forEach { index ->
            launch { // Launch animations in parallel for a smoother effect
                animatedScales[index].animateTo(
                    targetValue = if (index == selectedSlice) 1.1f else 1.0f, // Scale up selected slice
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }


    // ✨ New: 4. LaunchedEffect for enter animation
    LaunchedEffect(pieData) {
        enterAnimationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 8000, // Duration for the entry animation
                easing = FastOutSlowInEasing
            )
        )
    }


    var startAngle = 0f

    Canvas(
        modifier = modifier
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val clickedAngle = (
                            atan2(
                                y = offset.y - size.height / 2,
                                x = offset.x - size.width / 2
                            ) * STRAIGHT_ANGLE / PI + COMPLETE_CIRCLE_DEGREE
                            ) % COMPLETE_CIRCLE_DEGREE
                    var currentStartAngle = 0f
                    var clickedSliceIndex = -1

                    angles.forEachIndexed { index, sweepAngle ->
                        if (clickedAngle in currentStartAngle..(currentStartAngle + sweepAngle)) {
                            clickedSliceIndex = index
                        }
                        currentStartAngle += sweepAngle
                    }

                    if (clickedSliceIndex != -1) {
                        // ✨ 3. Unselect Logic: If the clicked slice is already selected, set to -1.
                        selectedSlice = if (clickedSliceIndex == selectedSlice) {
                            onPieChartSliceClick(null) // Notify listener of deselection
                            -1
                        } else {
                            onPieChartSliceClick(pieData[clickedSliceIndex])
                            clickedSliceIndex
                        }
                    }
                }
            }
    ) {
        val radius = size.minDimension / 2
        val strokeWidth = radius / 3
        val center = Offset(size.width / 2, size.height / 2)

        angles.forEachIndexed { index, sweepAngle ->
            // ✨ 4. Use Animated Value: Use the value from our Animatable list for the scale.
            val animatedSweepAngle = sweepAngle * enterAnimationProgress.value
            val scale = animatedScales[index].value
            val scaledRadius = radius * scale

            // The rest of your drawing logic remains largely the same
            val angleInRadians = (startAngle + animatedSweepAngle / 2) * PI / STRAIGHT_ANGLE
            val sliceCenter = Offset(
                center.x + (scaledRadius - radius) * cos(angleInRadians).toFloat(),
                center.y + (scaledRadius - radius) * sin(angleInRadians).toFloat()
            )

            drawArc(
                brush = Brush.linearGradient(pieData[index].color.value),
                startAngle = startAngle,
                sweepAngle = animatedSweepAngle,
                useCenter = !isDonutChart,
                size = Size(scaledRadius * 2, scaledRadius * 2),
                topLeft = Offset(sliceCenter.x - scaledRadius, sliceCenter.y - scaledRadius),
                style = if (isDonutChart) Stroke(width = strokeWidth) else Fill
            )

            if (!isDonutChart) {
                val labelAngle = (startAngle + sweepAngle / 2) * PI / STRAIGHT_ANGLE
                val labelRadius = (radius + strokeWidth) / 2
                val labelX = center.x + labelRadius * cos(labelAngle).toFloat()
                val labelY = center.y + labelRadius * sin(labelAngle).toFloat()
                val fontSize = if (index == selectedSlice) 16.sp else 12.sp
                val textLayoutResult = textMeasurer.measure(
                    text = pieData[index].label,
                    style = TextStyle.Default.copy(fontSize = fontSize),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    brush = Brush.linearGradient(pieData[index].labelColor.value),
                    topLeft = Offset(
                        x = labelX - textLayoutResult.size.width / 2,
                        y = labelY - textLayoutResult.size.height / 2,
                    ),
                )
            }
            startAngle += sweepAngle
        }
    }
}

/**
 * Data class representing a single slice of a pie chart.
 *
 * @property value The value of the pie chart slice.
 * @property color The color of the pie chart slice.
 * @property labelColor The color of the label text for the pie chart slice. Defaults to white.
 * @property label The text label for the pie chart slice.
 */
data class PieChartData(
    val value: Float,
    val color: ChartColor,
    val labelColor: ChartColor = Color.White.asSolidChartColor(),
    val label: String,
)


/**
 * A sealed class representing different types of chart colors.
 *
 * @property value A list of colors used for the chart.
 */
sealed class ChartColor(open val value: List<Color> = emptyList()) {
    /**
     * A data class representing a solid color for the chart.
     *
     * @property color The solid color.
     */
    data class Solid(val color: Color) : ChartColor(listOf(color, color))

    /**
     * A data class representing a gradient color for the chart.
     *
     * @property value A list of colors used for the gradient.
     */
    data class Gradient(override val value: List<Color>) : ChartColor(value)
}

/**
 * Extension function to convert a Color to a Solid ChartColor.
 *
 * @return A Solid ChartColor with the given color.
 */
fun Color.asSolidChartColor() = ChartColor.Solid(this)
fun Color.asGradientChartColor() = ChartColor.Gradient(listOf(this, this))

/**
 * Extension function to convert a list of Colors to a Gradient ChartColor.
 *
 * @return A Gradient ChartColor with the given list of colors.
 */
fun List<Color>.asGradientChartColor() = ChartColor.Gradient(this)