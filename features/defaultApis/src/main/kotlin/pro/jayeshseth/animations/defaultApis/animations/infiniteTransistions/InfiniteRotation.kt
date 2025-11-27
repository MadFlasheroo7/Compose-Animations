package pro.jayeshseth.animations.defaultApis.animations.infiniteTransistions

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme

@Composable
fun InfiniteRotation(
    hazeState: HazeState,
) {
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    var fanSpeed by remember {
        mutableFloatStateOf(360f)
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .clip(RoundedCornerShape(16.dp))
                .hazeEffect(
                    hazeState,
                    cardStyle
                )
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fan",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
            val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
            val angle by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = fanSpeed,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 360, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ), label = "infinite transition"
            )
            Icon(
                painter = painterResource(AnimIcons.fan),
                contentDescription = "Fan",
                modifier = Modifier
                    .size(200.dp)
                    .rotate(angle)
                    .dropShadow(RoundedCornerShape(16.dp)){
                        this.color = Color.White.copy(.2f)
                        this.radius = 100f
                    },
            )
            Slider(
                value = fanSpeed,
                onValueChange = { fanSpeed = it },
                valueRange = 100f..360f,
                steps = 3
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

            InfiniteRotation(hazeState)
        }
    }
}