package pro.jayeshseth.animations.ui.defaultApis.animateValue

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.BASE_URL
import pro.jayeshseth.animations.core.model.DURATION
import pro.jayeshseth.commoncomponents.InteractiveButton

const val OffsetPath = "${BASE_URL}/animations/animateValue/AnimateOffset.kt"

@Composable
fun AnimateOffsetAsState() {
    val urlLauncher = LocalUriHandler.current
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Animate Offset As State",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .basicMarquee()
                        .weight(1f),
                )
                IconButton({ urlLauncher.openUri(OffsetPath) }, modifier = Modifier) {
                    Icon(Icons.Rounded.Link, contentDescription = "link icon")
                }
            }

            var enabled by remember { mutableStateOf(true) }

            val offset by animateIntOffsetAsState(
                if (enabled) IntOffset(0, 0) else IntOffset(100, -100),
                animationSpec = tween(DURATION, easing = LinearEasing), label = "animate offset"
            )
            Box(
                Modifier
                    .offset {
                        offset
                    }
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            InteractiveButton(
                text = "Animate",
                onClick = { enabled = !enabled },
                padding = PaddingValues(top = 12.dp),
                height = 60.dp,
            )
        }
    }
}