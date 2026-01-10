package pro.jayeshseth.animations.core.ui.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

/**
 * A Composable function that determines and returns the current [DeviceConfiguration]
 * based on the window size.
 *
 * This function uses [currentWindowAdaptiveInfo] to get the [WindowSizeClass] and then
 * maps it to a specific [DeviceConfiguration] enum (e.g., MOBILE_PORTRAIT, TABLET_LANDSCAPE).
 * It's useful for creating adaptive UIs that respond to changes in screen size and orientation.
 *
 * @return The current [DeviceConfiguration] for the window.
 * @see DeviceConfiguration
 */
@Composable
fun currentDeviceConfiguration(): DeviceConfiguration {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
}

/**
 * Represents different device configurations based on screen size and orientation.
 *
 * This enum class categorizes the device's display into common form factors like mobile, tablet,
 * and desktop, considering both portrait and landscape orientations. It provides a simplified
 * abstraction over the more granular `WindowSizeClass`.
 *
 * @property MOBILE_PORTRAIT A typical phone in portrait orientation.
 * @property MOBILE_LANDSCAPE A typical phone in landscape orientation.
 * @property TABLET_PORTRAIT A tablet in portrait orientation.
 * @property TABLET_LANDSCAPE A tablet in landscape orientation.
 * @property DESKTOP A large screen, typical of a desktop or a large tablet in landscape.
 */
enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    val isMobile: Boolean
        get() = this in listOf(MOBILE_PORTRAIT, MOBILE_LANDSCAPE)

    val isWideScreen: Boolean
        get() = this in listOf(TABLET_LANDSCAPE, DESKTOP)

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            return with(windowSizeClass) {
                when {
                    minWidthDp < WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_MEDIUM_LOWER_BOUND -> MOBILE_PORTRAIT

                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp < HEIGHT_DP_MEDIUM_LOWER_BOUND -> MOBILE_LANDSCAPE

                    minWidthDp in WIDTH_DP_MEDIUM_LOWER_BOUND..WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_EXPANDED_LOWER_BOUND -> TABLET_PORTRAIT

                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp in HEIGHT_DP_MEDIUM_LOWER_BOUND..HEIGHT_DP_EXPANDED_LOWER_BOUND -> TABLET_LANDSCAPE

                    else -> DESKTOP
                }
            }
        }
    }
}