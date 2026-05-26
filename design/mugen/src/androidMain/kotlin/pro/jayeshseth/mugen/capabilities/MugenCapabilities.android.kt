package pro.jayeshseth.mugen.capabilities

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.systemBars

@Composable
actual fun resolveMugenCapabilities(): MugenCapabilities {
    val view = LocalView.current
    val corner: Dp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val insets: WindowInsetsCompat? = view.let { v ->
            androidx.core.view.ViewCompat.getRootWindowInsets(v)
        }
        val rounded = insets?.toWindowInsets()?.let {
            it.getRoundedCorner(android.view.RoundedCorner.POSITION_TOP_LEFT)?.radius ?: 0
        } ?: 0
        with(LocalView.current.resources.displayMetrics) {
            (rounded / density).dp
        }
    } else {
        0.dp
    }

    return MugenCapabilities(
        supportsRuntimeShaders = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU,
        supportsBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
        supportsInnerShadow = true,
        supportsHaze = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
        deviceCornerRadius = corner,
    )
}
