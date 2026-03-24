package pro.jayeshseth.animations.core.ui.backgrounds.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

/**
 * Image background displaying the bundled cat photograph.
 *
 * The image is rendered full-bleed with [ContentScale.Crop] to cover the
 * entire background area without letterboxing.
 */
data object CatImageBackground : BackgroundType.Image {
    override val key = DefaultPrefKeys.CAT_IMAGE_KEY
    override val displayName = "Cat"
    override val previewColors = listOf(0xFF8D6E63.toInt(), 0xFFBCAAA4.toInt(), 0xFF6D4C41.toInt())

    @Composable
    override fun Content(modifier: Modifier) {
        Image(
            painter = painterResource(AnimIcons.cat),
            contentDescription = displayName,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun CatImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(AnimIcons.cat),
        contentDescription = "Image of ${CatImageBackground.displayName}",
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}