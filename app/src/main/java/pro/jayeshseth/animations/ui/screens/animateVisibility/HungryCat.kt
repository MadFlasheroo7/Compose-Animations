package pro.jayeshseth.animations.ui.screens.animateVisibility

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.ui.composables.CatImage
import pro.jayeshseth.animations.ui.composables.InteractiveButton
import pro.jayeshseth.animations.R

@Composable
fun HungryCat() {
    val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.angry_cat)
    Card {
        var isVisible by remember { mutableStateOf(true) }
        val randNum = mutableListOf(-200..200).random()
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