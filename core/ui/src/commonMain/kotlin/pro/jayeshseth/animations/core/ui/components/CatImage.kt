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

/**
 * A composable that displays a static image of a cat.
 *
 * This function uses a predefined painter resource for the cat image and applies a default size.
 * The image is cropped to fit the specified size while maintaining its aspect ratio.
 *
 * @param modifier The modifier to be applied to the image. Defaults to [Modifier].
 * @param size The desired size of the image, specified as a [Dp]. Defaults to 200.dp.
 */
@Composable
fun CatImage(modifier: Modifier = Modifier, size: Dp = 200.dp) {
    Image(
        painter = painterResource(AnimIcons.cat),
        contentDescription = "",
        modifier = modifier.size(size),
        contentScale = ContentScale.Crop
    )
}