package pro.jayeshseth.animations.ui.defaultApis.animatedContent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.BASE_URL
import pro.jayeshseth.commoncomponents.InteractiveButton

const val BasicCounterPath = "$BASE_URL/defaultApis2/animatedContent/BasicCounter.kt"

@Composable
fun BasicIncrementCounter() {
    val urlLauncher = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var count by remember { mutableIntStateOf(0) }
        Column(
            modifier = Modifier
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
                IconButton({ urlLauncher.openUri(BasicCounterPath) }, modifier = Modifier) {
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

            Row(Modifier.fillMaxWidth()) {
                InteractiveButton(
                    text = "Increase",
                    onClick = { count++ },
                    onLongPress = { count++ },
                    padding = PaddingValues(top = 12.dp, end = 12.dp),
                    height = 60.dp,
                    modifier = Modifier.weight(1f)
                )
                InteractiveButton(
                    text = "Decrease",
                    onClick = { count-- },
                    onLongPress = { count-- },
                    padding = PaddingValues(top = 12.dp),
                    height = 60.dp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}