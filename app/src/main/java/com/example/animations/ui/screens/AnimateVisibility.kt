package com.example.animations.ui.screens

import android.media.MediaPlayer
import android.opengl.Visibility
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animations.R
import com.example.animations.ui.composables.InteractiveButton
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun VisibilityAnimation() {
    LazyColumn(
    ) {
        item() {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
//            .verticalScroll(rememberScrollState())
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp)
                    .animateContentSize(tween(700, easing = LinearEasing))
            ) {
                DefaultAnimation()
                Fade()
                Slide()
                SlideHorizontally()
                SlideVertically()
                Scale()
                ExpandAndShrink()
                ExpandAndShrinkHorizontally()
                ExpandAndShrinkVertically()
                HungryCat()
                RandomSlide()
            }
        }
    }
}

@Composable
fun CatImage(size: Dp = 200.dp) {
    Image(
        painter = painterResource(id = R.drawable.cat),
        contentDescription = "",
        modifier = Modifier.size(size)
    )
}

@Composable
fun DefaultAnimation() {
    Card() {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Default Animation",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(visible = isVisible) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = { isVisible = !isVisible },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp,

            )
        }
    }
}

@Composable
fun Fade() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fade In & Out",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(
                    animationSpec = tween(1500, easing = LinearEasing)
                ),
                exit = fadeOut(
                    animationSpec = tween(1500, easing = LinearEasing)

                )
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = { isVisible = !isVisible },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun Slide() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Slide In & Out",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = slideIn(animationSpec = tween(500, easing = LinearEasing)) {
                    IntOffset(it.width / 4, 100)
                },
                exit = slideOut(animationSpec = tween(500, easing = LinearEasing)) {
                    IntOffset(-180, 50)
                }
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun SlideHorizontally() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Slide In & Out Horizontally",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(animationSpec = tween(500, easing = LinearEasing)) {
                    -it
                },
                exit = slideOutHorizontally(animationSpec = tween(500, easing = LinearEasing)) {
                    it
                }
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun SlideVertically() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Slide In & Out Vertically",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(animationSpec = tween(500, easing = LinearEasing)) {
                    -it / 3
                },
                exit = slideOutVertically(animationSpec = tween(500, easing = LinearEasing)) {
                    it
                }
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Scale() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Scale In & Out",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn(animationSpec = tween(400, easing = LinearEasing)),
                exit = scaleOut(animationSpec = tween(400, easing = LinearEasing))
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun ExpandAndShrink() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Expand In & Shrink Out",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = expandIn(animationSpec = tween(400, easing = LinearEasing)),
                exit = shrinkOut(animationSpec = tween(400, easing = LinearEasing))
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun ExpandAndShrinkHorizontally() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Expand In & Shrink Out Horizontally",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = expandHorizontally(animationSpec = tween(400, easing = LinearEasing)),
                exit = shrinkHorizontally(animationSpec = tween(400, easing = LinearEasing))
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun ExpandAndShrinkVertically() {
    Card {
        var isVisible by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Expand In & Shrink Out Vertically",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = expandVertically(animationSpec = tween(400, easing = LinearEasing)),
                exit = shrinkVertically(animationSpec = tween(400, easing = LinearEasing))
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun HungryCat() {
    val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.angry_cat)
    Card {
        var isVisible by remember { mutableStateOf(true) }
        var randNum = mutableListOf(-200..200).random()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hungry Cat",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = slideIn(animationSpec = tween(500, easing = LinearEasing)) {
                    IntOffset(randNum.random(), -randNum.random())
                },
                exit = slideOut(animationSpec = tween(500, easing = LinearEasing)) {
                    IntOffset(-randNum.random(), randNum.random())
                }
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                    randNum.shuffled()
                    mediaPlayer.seekTo(2000)
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                    } else {
                        mediaPlayer.start()
                    }
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}

@Composable
fun RandomSlide() {
    Card {
        var isVisible by remember { mutableStateOf(true) }
        val randX = (-9999..9999).random()
        val randY = (-9999..9999).random()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Random Slide In & Out",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnimatedVisibility(
                visible = isVisible,
                enter = slideIn(animationSpec = tween(500, easing = LinearEasing)) {
                    IntOffset(randX, randY)
                } + fadeIn(),
                exit = slideOut(animationSpec = tween(500, easing = LinearEasing)) {
                    IntOffset(randX, randY)
                } + fadeOut()
            ) {
                CatImage()
            }
            InteractiveButton(
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp
            )
        }
    }
}
