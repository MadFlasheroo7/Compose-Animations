package pro.jayeshseth.animations.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.R

@Composable
fun CatImage(modifier: Modifier = Modifier, size: Dp = 200.dp) {
    Image(
        painter = painterResource(
            id = R.drawable.cat
        ),
        contentDescription = "",
        modifier = modifier.size(size),
        contentScale = ContentScale.Crop
    )
}