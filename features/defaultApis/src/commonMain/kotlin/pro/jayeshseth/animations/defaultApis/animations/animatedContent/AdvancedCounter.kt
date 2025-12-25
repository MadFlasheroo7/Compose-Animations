package pro.jayeshseth.animations.defaultApis.animations.animatedContent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.utils.BASE_URL
import pro.jayeshseth.animations.defaultApis.utils.BASE_FEATURE_ROUTE

val AdvanceCounterPath =
    "$BASE_URL/${BASE_FEATURE_ROUTE}/animations/animatedContent/AdvancedCounter.kt"

data class Digit(val singleDigit: Char, val fullNumber: Int, val place: Int) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Digit -> singleDigit == other.singleDigit
            else -> super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return singleDigit.hashCode()
    }
}

operator fun Digit.compareTo(other: Digit): Int {
    return fullNumber.compareTo(other.fullNumber)
}

@Composable
fun AdvancedIncrementCounter(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val urlLauncher = LocalUriHandler.current
    var count by remember { mutableIntStateOf(0) }
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .hazeEffect(
                hazeState,
                cardStyle
            )
            .padding(18.dp, 4.dp, 18.dp, 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Advanced Animated Counter",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .basicMarquee()
                    .weight(1f),
            )
            IconButton({ urlLauncher.openUri(AdvanceCounterPath) }, modifier = Modifier) {
                Icon(Icons.Rounded.Link, contentDescription = "link icon")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            count.toString().reversed()
                .mapIndexed { index, char ->
                    _root_ide_package_.pro.jayeshseth.animations.defaultApis.animations.animatedContent.Digit(
                        char,
                        count,
                        index
                    )
                }
                .reversed()
                .forEach { digit ->
                    AnimatedContent(
                        targetState = digit,
                        transitionSpec = {
//                            if (targetState compareTo initialState) {
                            if (true) {
                                // Enter Transition            Exit Transition
                                slideInVertically { -it } togetherWith (slideOutVertically { it } + fadeOut())
                            } else {
                                // Exit Transition
                                slideInVertically { it } togetherWith (slideOutVertically { -it } + fadeOut())
                            }
                        },
                        label = "text animation"
                    ) { updatedCount ->
                        Text(
                            text = "${updatedCount.singleDigit}",
                            fontSize = 90.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InteractiveButton(
                hazeState = hazeState,
                text = "Increase",
                onClick = { count++ },
//                    onLongPress = { count++ },
                modifier = Modifier.weight(1f)
            )
            InteractiveButton(
                hazeState = hazeState,
                text = "Decrease",
                onClick = { count-- },
//                    onLongPress = { count-- },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

//@Preview
//@Composable
//private fun PreviewIncrementCard() {
//    val hazeState = rememberHazeState()
//    AnimationsTheme {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .hazeSource(state = hazeState)
//                    .shaderBackground(InkFlow, speed = 0.2f)
//            )
//
//            _root_ide_package_.pro.jayeshseth.animations.defaultApis.animations.animatedContent.AdvancedIncrementCounter(
//                hazeState = hazeState,
//            )
//        }
//    }
//}