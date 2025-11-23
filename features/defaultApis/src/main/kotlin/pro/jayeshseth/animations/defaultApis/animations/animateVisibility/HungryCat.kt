package pro.jayeshseth.animations.defaultApis.animations.animateVisibility

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.ui.components.CatImage
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.media.AnimMedia
import pro.jayeshseth.animations.core.utils.BASE_URL

val HungryCatPath = "$BASE_URL/animations/animateVisibility/HungryCat.kt"

/**
 * A hungry cat is a angry cat 🐈😾
 */
@Composable
fun HungryCat(
    hazeState: HazeState,
) {
    val urlLauncher = LocalUriHandler.current
    val mediaPlayer = MediaPlayer.create(LocalContext.current, AnimMedia.HungryCat)
    var isVisible by remember { mutableStateOf(true) }
    val randNum = mutableListOf(-200..200).random()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Hungry Cat",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .basicMarquee()
                    .weight(1f),
            )
            IconButton({ urlLauncher.openUri(HungryCatPath) }, modifier = Modifier) {
                Icon(Icons.Rounded.Link, contentDescription = "link icon")
            }
        }
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
            hazeState = hazeState,
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
        )
    }
}