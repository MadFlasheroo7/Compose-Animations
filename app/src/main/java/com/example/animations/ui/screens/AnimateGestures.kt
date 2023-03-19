package com.example.animations.ui.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.animations.ui.composables.CatImage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AnimatedGestures() {
    Column {
        TapGesture()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TapGesture() {
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        val position = awaitPointerEventScope {
                            awaitFirstDown().position
                        }
                        Log.d("Tap Position: ", "$position")
                        launch {
                            // Animate to the tap position.
                            offset.animateTo(
                                position,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioHighBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                    }
                }
            },
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Tap Any Where On Screen")
            }
        )
        CatImage(
            size = 150.dp,
            modifier = Modifier
                .offset { offset.value.toIntOffset() }
                .clip(CircleShape)
        )
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())
