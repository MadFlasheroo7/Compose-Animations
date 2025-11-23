package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.model.AnimationContent
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme

@OptIn(ExperimentalHazeApi::class)
@Composable
fun AnimationCard(
    index: Int,
    hazeState: HazeState,
    animationContent: AnimationContent,
    onClickLink: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(.3f) }

    LaunchedEffect(index) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = EaseInOutQuad)
        )
    }

    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    var isVisible by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
//            shifting to scale animation
//                translationX = animatedProgress.value
//                clip = true
                shape = RoundedCornerShape(16.dp)
                scaleX = animatedProgress.value
            }
            .clip(RoundedCornerShape(16.dp))
            .hazeEffect(
                hazeState,
                cardStyle
            )
            .padding(18.dp, 4.dp, 18.dp, 18.dp)

    ) {
        if (animationContent.title != null) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = animationContent.title!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .basicMarquee()
                        .weight(1f),
                )
                IconButton(onClickLink, modifier = Modifier) {
                    Icon(AnimIcons.link, contentDescription = "link icon")
                }
            }
        }
        Box(
            Modifier.animateContentSize(tween(500))
        ) {
            animationContent.content(isVisible)
        }
        if (animationContent.title != null) {
            InteractiveButton(
                hazeState = hazeState,
                text = "Animate",
                onClick = {
                    isVisible = !isVisible
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAnimationCard() {
    val hazeState = rememberHazeState()
    AnimationsTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState)
                    .shaderBackground(InkFlow, speed = 0.2f)

            )

            AnimationCard(
                index = 0,
                onClickLink = {},
                hazeState = hazeState,
                modifier = Modifier.padding(12.dp),
                animationContent = AnimationContent(
                    title = "Animate Visibility",
                ) {
                    Box(
                        Modifier
                            .size(100.dp)
                            .background(Color.White)
                    )
                }
            )
        }
    }
}