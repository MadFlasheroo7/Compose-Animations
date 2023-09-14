package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.jayeshseth.animations.ui.composables.InteractiveButton
import pro.jayeshseth.animations.util.DURATION

@Composable
fun AnimateValueAsState() {
    LazyColumn {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp)
                    .animateContentSize(tween(400, easing = LinearEasing))
            ) {
                AnimateFloatAsState()
                AnimateDpAsState()
                AnimateOffsetAsState()
                AnimateColorAsState()
            }
        }
    }
}

@Composable
fun AnimateFloatAsState() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Animate Float As State",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            var enabled by remember { mutableStateOf(true) }

            val alpha: Float by animateFloatAsState(
                if (enabled) 1f else 0.5f,
                animationSpec = tween(DURATION, easing = LinearEasing)
            )
            Box(
                Modifier
                    .size(200.dp)
                    .graphicsLayer(alpha = alpha)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = { enabled = !enabled })
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

@Composable
fun AnimateDpAsState() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Animate Dp As State",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            var enabled by remember { mutableStateOf(true) }

            val size by animateDpAsState(
                if (enabled) 200.dp else 100.dp,
                animationSpec = tween(DURATION, easing = LinearEasing)
            )
            Box(
                Modifier
                    .size(size)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = { enabled = !enabled })
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

@Composable
fun AnimateOffsetAsState() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Animate Offset As State",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            var enabled by remember { mutableStateOf(true) }

            val offset by animateIntOffsetAsState(
                if (enabled) IntOffset(0, 0) else IntOffset(100, -100),
                animationSpec = tween(DURATION, easing = LinearEasing)
            )
            Box(
                Modifier
                    .offset {
                        offset
                    }
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = { enabled = !enabled })
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

@Composable
fun AnimateColorAsState() {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Animate Color As State",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            var enabled by remember { mutableStateOf(true) }

            val color by animateColorAsState(
                if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary,
                animationSpec = tween(1000, easing = LinearEasing)
            )
            Box(
                Modifier
                    .size(200.dp)
                    .background(color)
                    .clickable(onClick = { enabled = !enabled })
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