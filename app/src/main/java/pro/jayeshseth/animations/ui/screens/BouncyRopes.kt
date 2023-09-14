package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun BouncyRope() {
    var startCoOrdinate by remember {
        mutableStateOf(Offset(0f, 200f))
    }
    var endCoOrdinate by remember {
        mutableStateOf(Offset(300f, 200f))
    }
    val midPoint by remember {
        derivedStateOf {
            val distance = (endCoOrdinate.x - startCoOrdinate.x)
            Offset(
                (endCoOrdinate.x - startCoOrdinate.x) / 2f,
                endCoOrdinate.y + distance
            )
        }
    }
    val path = remember {
        Path()
    }
    val midPointAnimated = animateOffsetAsState(
        midPoint,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    val color = MaterialTheme.colorScheme.tertiary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Bouncy Rope",
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Canvas(
            modifier = Modifier
                .offset {
                    IntOffset(
                        startCoOrdinate.x.roundToInt(),
                        startCoOrdinate.y.roundToInt()
                    )
                }
                .pointerInput("drag") {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        startCoOrdinate += dragAmount
                    }
                }) {
            val radius = 15.dp.toPx()
            drawCircle(color, radius = radius)
        }

        Canvas(
            modifier = Modifier
                .offset {
                    IntOffset(
                        endCoOrdinate.x.roundToInt(),
                        endCoOrdinate.y.roundToInt()
                    )
                }
                .pointerInput("drag") {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        endCoOrdinate += dragAmount
                    }
                }) {
            val radius = 15.dp.toPx()
            drawCircle(color, radius = radius)
        }

        Canvas(modifier = Modifier) {
            path.reset()
            path.moveTo(startCoOrdinate.x, startCoOrdinate.y)
            path.quadraticBezierTo(
                midPointAnimated.value.x,
                midPointAnimated.value.y,
                endCoOrdinate.x,
                endCoOrdinate.y
            )

            drawPath(path, color, style = Stroke(8.dp.toPx()))
        }
    }
}