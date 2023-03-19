package com.example.animations.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InteractiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 90.dp,
    padding: PaddingValues = PaddingValues(12.dp),
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed || isHovered

    val animateColor by animateColorAsState(
        targetValue = if (buttonInteracted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(500, easing = LinearEasing)
    )
    val buttonDp by animateDpAsState(
        targetValue = if (buttonInteracted) 5.dp else 100.dp,
        animationSpec = tween(500)
    )
    Button(
        shape = RoundedCornerShape(size = buttonDp),
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .padding(padding),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(containerColor = animateColor),
        onClick = onClick
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}
