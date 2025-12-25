package pro.jayeshseth.animations.defaultApis.animations.animatedContent

import androidx.compose.animation.*
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.*
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.utils.BASE_URL
import pro.jayeshseth.animations.defaultApis.utils.BASE_FEATURE_ROUTE

val BasicCounterPath = "$BASE_URL/${BASE_FEATURE_ROUTE}/animations/animatedContent/BasicCounter.kt"

@Composable
fun BasicIncrementCounter(hazeState: HazeState) {
    val urlLauncher = LocalUriHandler.current
    var count by remember { mutableIntStateOf(0) }
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .hazeEffect(
                hazeState,
                cardStyle
            )
            .padding(18.dp, 0.dp, 18.dp, 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Basic Animated Counter",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .basicMarquee()
                    .weight(1f),
            )
            IconButton(
                { urlLauncher.openUri(_root_ide_package_.pro.jayeshseth.animations.defaultApis.animations.animatedContent.BasicCounterPath) },
                modifier = Modifier
            ) {
                Icon(Icons.Rounded.Link, contentDescription = "link icon")
            }
        }


        AnimatedContent(
            targetState = count,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { -it } togetherWith slideOutVertically { it } + fadeOut()
                } else {
                    slideInVertically { it } togetherWith slideOutVertically { -it } + fadeOut()
                }
            }, label = "basic text animation"
        ) { updatedCount ->
            Text(
                text = "$updatedCount",
                fontSize = 90.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            pro.jayeshseth.animations.core.ui.components.InteractiveButton(
                text = "Increase",
                hazeState = hazeState,
                onClick = { count++ },
//                onLongPress = { count++ },
                modifier = Modifier.weight(1f)
            )
            pro.jayeshseth.animations.core.ui.components.InteractiveButton(
                text = "Decrease",
                hazeState = hazeState,
                onClick = { count-- },
//                onLongPress = { count-- },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

//@Preview
@Composable
private fun PreviewIncrementCard() {
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

            BasicIncrementCounter(
                hazeState = hazeState,
            )
        }
    }
}