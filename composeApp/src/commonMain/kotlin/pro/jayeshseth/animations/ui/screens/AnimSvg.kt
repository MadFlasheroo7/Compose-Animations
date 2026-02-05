package pro.jayeshseth.animations.ui.screens

import androidx.compose.ui.graphics.vector.PathParser

//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.PathMeasure
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.StrokeJoin
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.vector.PathNode
//import androidx.compose.ui.graphics.vector.PathParser
//import androidx.compose.ui.graphics.vector.toPath
//import androidx.compose.ui.graphics.withSave
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//
//@Composable
//fun AnimatedVector(
//    paths: List<List<PathNode>>, // Accepts the list of layers
//    state: SvgAnimationState,
//    modifier: Modifier = Modifier,
//    originalViewportSize: Size = Size(200f, 200f), // Adjust based on your SVG bounds
//    strokeColor: Color = Color.Black,
//    strokeWidth: Float = 4f
//) {
//    // 1. Pre-calculate Path objects and their lengths once
//    //    We create a lightweight data class to hold the "Baked" data
//    val pathData = remember(paths) {
//        val pMeasure = PathMeasure()
//        val totalLengthList = mutableListOf<Float>()
//        val pathList = paths.map { nodes ->
//            val path = nodes.toPath() // Convert Nodes to Path
//            pMeasure.setPath(path, false)
//            totalLengthList.add(pMeasure.length)
//            path
//        }
//        val totalLength = totalLengthList.sum()
//
//        // Return a structured object
//        MultiPathData(pathList, totalLengthList, totalLength)
//    }
//
//    // 2. Reuse drawing objects
//    val pathMeasure = remember { PathMeasure() }
//    val partialPath = remember { Path() }
//
//    Canvas(modifier = modifier) {
//        val currentGlobalProgress = state.progress.value
//        val totalDrawLength = pathData.totalLength * currentGlobalProgress
//
//        // Calculate Scale to fit Canvas
//        val scaleX = size.width / originalViewportSize.width
//        val scaleY = size.height / originalViewportSize.height
//        val scale = minOf(scaleX, scaleY)
//
//        // Apply scaling
//        drawContext.canvas.withSave {
//            drawContext.canvas.scale(scale, scale)
//
//            var accumulatedLength = 0f
//
//            // 3. Iterate through every layer
//            pathData.paths.forEachIndexed { index, path ->
//                val pathLength = pathData.lengths[index]
//
//                // Determine how much of THIS specific path needs to be drawn
//                // Logic:
//                // If we are past this path, draw full.
//                // If we are before this path, draw nothing.
//                // If we are currently IN this path, calculate segment.
//
//                val startOfPath = accumulatedLength
//                val endOfPath = accumulatedLength + pathLength
//
//                if (totalDrawLength >= endOfPath) {
//                    // Scenario A: We are well past this path -> Draw it fully
//                    drawPath(
//                        path = path,
//                        color = strokeColor,
//                        style = Stroke(
//                            strokeWidth / scale,
//                            cap = StrokeCap.Round,
//                            join = StrokeJoin.Round
//                        )
//                    )
//                } else if (totalDrawLength > startOfPath) {
//                    // Scenario B: We are currently animating this path -> Draw partial
//                    val progressInThisPath = totalDrawLength - startOfPath
//
//                    partialPath.reset()
//                    pathMeasure.setPath(path, false)
//                    pathMeasure.getSegment(0f, progressInThisPath, partialPath, true)
//
//                    drawPath(
//                        path = partialPath,
//                        color = strokeColor,
//                        style = Stroke(
//                            strokeWidth / scale,
//                            cap = StrokeCap.Round,
//                            join = StrokeJoin.Round
//                        )
//                    )
//                }
//                // Scenario C: We haven't reached this path yet -> Do nothing
//
//                accumulatedLength += pathLength
//            }
//        }
//    }
//}
//
//// Helper class to hold pre-calculated data
//private data class MultiPathData(
//    val paths: List<Path>,
//    val lengths: List<Float>,
//    val totalLength: Float
//)

// 1. Ensure your object is accessible (Mocking your input for completeness)
object KfugLogo {
    val layer1 =
        PathParser().parsePathString("M155.964 37.7462L118.774 0.55661L81.5845 37.7462L118.774 74.9358V37.7462C118.774 37.7462 156.605 37.1508 155.964 37.7462Z")
            .toNodes()
    val layer2 =
        PathParser().parsePathString("M178.864 97.8358V45.2574H126.285V97.8358L152.574 71.5466C152.574 71.5466 179.734 97.8358 178.864 97.8358Z")
            .toNodes()
    val layer3 =
        PathParser().parsePathString("M152.574 156.551L189.764 119.362L152.574 82.1722L115.385 119.362H152.574C152.574 119.362 153.17 157.147 152.574 156.551Z")
            .toNodes()
    val layer4 =
        PathParser().parsePathString("M92.4849 179.451H145.063V126.827H92.4849L118.774 153.116C118.774 153.162 92.4849 180.322 92.4849 179.451Z")
            .toNodes()
    val layer5 =
        PathParser().parsePathString("M33.7692 153.162L70.9588 190.352L108.148 153.162L70.9588 115.973V153.162C71.0046 153.162 33.1738 153.758 33.7692 153.162Z")
            .toNodes()
    val layer6 =
        PathParser().parsePathString("M10.8691 93.0726V145.651H63.4933V93.0726L37.2041 119.362C37.2041 119.362 9.99887 93.0726 10.8691 93.0726Z")
            .toNodes()
    val layer7 =
        PathParser().parsePathString("M37.2043 34.357L0.0146484 71.5466L37.2043 108.736L74.3939 71.5466H37.2043C37.2043 71.5466 36.563 33.7616 37.2043 34.357Z")
            .toNodes()
    val layer8 =
        PathParser().parsePathString("M97.2936 64.0812H44.6694V11.457H97.2936L71.0044 37.7462L97.2936 64.0812Z")
            .toNodes()

    // Aggregation list for the UI
    val allLayers = listOf(layer1, layer2, layer3, layer4, layer5, layer6, layer7, layer8)
}
//
//@Composable
//fun BouncyRope() {
//    val animState = rememberSvgAnimationState()
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        // The Animated Logo
//        AnimatedVector(
//            paths = KfugLogo.allLayers, // Pass the list of layers
//            state = animState,
//            modifier = Modifier.size(200.dp), // Size on screen
//            originalViewportSize = Size(200f, 200f), // Size of the SVG coordinate system
//            strokeColor = Color(0xFF4285F4), // Google Blue
//            strokeWidth = 3f
//        )
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Text("Status: ${animState.status}")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//            Button(onClick = {
//                scope.launch { animState.start(durationMillis = 4000) }
//            }) {
//                Text("Start")
//            }
//            Button(onClick = {
//                scope.launch { animState.reset() }
//            }) {
//                Text("Reset")
//            }
//        }
//    }
//}