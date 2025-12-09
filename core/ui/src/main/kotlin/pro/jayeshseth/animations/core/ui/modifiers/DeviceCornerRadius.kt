package pro.jayeshseth.animations.core.ui.modifiers

import android.os.Build
import android.view.RoundedCorner
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

/**
 * Modifier to clip to the device's corner radius if the device exposes it.
 */
@Composable
fun Modifier.clipToDeviceCornerRadius(): Modifier {
    val defaultRadius = 0.dp
    val view = LocalView.current
    val rootInsets = view.rootWindowInsets
    val density = LocalDensity.current

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val topLeftCorner = rootInsets.getRoundedCorner(RoundedCorner.POSITION_TOP_LEFT)
        val topRightCorner = rootInsets.getRoundedCorner(RoundedCorner.POSITION_TOP_RIGHT)
        val bottomLeftCorner = rootInsets.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_LEFT)
        val bottomRightCorner = rootInsets.getRoundedCorner(RoundedCorner.POSITION_BOTTOM_RIGHT)

        val topLeftRadius = topLeftCorner?.radius
        val topRightRadius = topRightCorner?.radius
        val bottomLeftRadius = bottomLeftCorner?.radius
        val bottomRightRadius = bottomRightCorner?.radius

        return with(density) {
            clip(
                RoundedCornerShape(
                    topStart = topLeftRadius?.toDp() ?: 0.dp,
                    topEnd = topRightRadius?.toDp() ?: 0.dp,
                    bottomStart = bottomLeftRadius?.toDp() ?: 0.dp,
                    bottomEnd = bottomRightRadius?.toDp() ?: 0.dp
                )
            )
        }
    } else {
        return clip(RoundedCornerShape(defaultRadius))
    }
}