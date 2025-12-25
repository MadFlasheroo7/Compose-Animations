package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pro.jayeshseth.animations.core.ui.icons.AnimIcons

@Composable
fun CatImage(modifier: Modifier = Modifier, size: Dp = 200.dp) {
    Image(
        painter = painterResource(AnimIcons.cat),
        contentDescription = "",
        modifier = modifier.size(size),
        contentScale = ContentScale.Crop
    )
}