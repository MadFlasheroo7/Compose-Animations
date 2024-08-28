package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.util.AnimationContent
import pro.jayeshseth.commoncomponents.InteractiveButton

@Composable
fun AnimationCard(
    index: Int,
    animationContent: AnimationContent,
    onClickLink: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(500f) }

    LaunchedEffect(index) {
        animatedProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(500, easing = EaseInOutCubic)
        )
    }
    Card(modifier = modifier
        .graphicsLayer { translationX = animatedProgress.value }
    ) {
        var isVisible by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp, 0.dp, 18.dp, 18.dp)
                .animateContentSize(tween(500)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (animationContent.title != null) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = animationContent.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .basicMarquee()
                            .weight(1f),
                    )
                    IconButton(onClickLink, modifier = Modifier) {
                        Icon(Icons.Rounded.Link, contentDescription = "link icon")
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
                    text = "Animate",
                    onClick = { isVisible = !isVisible },
                    padding = PaddingValues(top = 12.dp),
                    height = 60.dp,
                )
            }
        }
    }
}
