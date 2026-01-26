package pro.jayeshseth.animations.core.ui.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.DESKTOP
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.MOBILE_LANDSCAPE
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.MOBILE_PORTRAIT
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.TABLET_LANDSCAPE
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.TABLET_PORTRAIT

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
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    val sizePx = windowInfo.containerSize

    val widthDp = with(density) { sizePx.width.toDp() }
    val heightDp = with(density) { sizePx.height.toDp() }

    return DeviceConfiguration.fromWindowInfo(widthDp, heightDp)
}

/**
 * Represents different device configurations based on screen size and orientation.
 *
 * This enum class categorizes the device's display into common form factors like mobile, tablet,
 * and desktop, considering both portrait and landscape orientations.
 *
 * @property MOBILE_PORTRAIT A typical phone in portrait orientation.
 * @property MOBILE_LANDSCAPE A typical phone in landscape orientation.
 * @property TABLET_PORTRAIT A tablet in portrait orientation.
 * @property TABLET_LANDSCAPE A tablet in landscape orientation.
 * @property DESKTOP A large screen, typical of a desktop or a large tablet in landscape.
 * @property isMobile Returns true for [MOBILE_PORTRAIT] or [MOBILE_LANDSCAPE].
 * @property isWideScreen Returns true for [TABLET_LANDSCAPE] or [DESKTOP].
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
        get() = this in listOf(TABLET_PORTRAIT, TABLET_LANDSCAPE, DESKTOP, MOBILE_LANDSCAPE)

    companion object {

        /*        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
                    return with(windowSizeClass) {
                        when {
                            minWidthDp < COMPACT_WIDTH &&
                                    minHeightDp >= COMPACT_HEIGHT -> MOBILE_PORTRAIT

                            minWidthDp >= COMPACT_WIDTH &&
                                    minHeightDp < COMPACT_HEIGHT -> MOBILE_LANDSCAPE

                            minWidthDp in COMPACT_WIDTH..EXPANDED_WIDTH &&
                                    minHeightDp >= EXPANDED_HEIGHT -> TABLET_PORTRAIT

                            minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                                    minHeightDp in HEIGHT_DP_MEDIUM_LOWER_BOUND..HEIGHT_DP_EXPANDED_LOWER_BOUND -> TABLET_LANDSCAPE

                            minWidthDp >= EXPANDED_WIDTH &&
                                    minHeightDp >= EXPANDED_HEIGHT -> DESKTOP

                            else -> MOBILE_PORTRAIT
                        }
                    }
                }
        */

        /**
         * Represents the physical configuration of the device screen based on available width and height.
         * This classification logic prioritizes width for layout structure but uses height to distinguish
         * landscape phones and desktop environments.
         *
         * ### Breakpoint Logic & Mapping
         *
         * | Configuration      | Width Condition | Height Condition | Typical Device              |
         * | :----------------- | :-------------- | :-------------- | :-------------------------- |
         * | **MOBILE_PORTRAIT** | `< 600.dp`      | *Any* | Standard Phone (Portrait)   |
         * | **MOBILE_LANDSCAPE** | `≥ 600.dp`      | `< 480.dp`      | Phone (Landscape)           |
         * | **TABLET_PORTRAIT** | `600..839.dp`   | `≥ 480.dp`      | Tablet (Portrait), Foldable |
         * | **TABLET_LANDSCAPE** | `≥ 840.dp`      | `480..899.dp`   | Tablet (Land), Small Laptop |
         * | **DESKTOP** | `≥ 1200.dp`      | `≥ 1200.dp`      | Large Monitor, Desktop      |
         */
        fun fromWindowInfo(widthDp: Dp, heightDp: Dp): DeviceConfiguration {
            return when {
                widthDp < COMPACT_WIDTH &&
                        heightDp >= COMPACT_HEIGHT -> MOBILE_PORTRAIT

                widthDp >= COMPACT_WIDTH &&
                        heightDp < COMPACT_HEIGHT -> MOBILE_LANDSCAPE

                widthDp < EXPANDED_WIDTH &&
                        heightDp < EXPANDED_HEIGHT -> TABLET_PORTRAIT

                widthDp in COMPACT_WIDTH..EXPANDED_WIDTH &&
                        heightDp >= EXPANDED_HEIGHT -> TABLET_PORTRAIT

                widthDp in EXPANDED_WIDTH..EXTRA_EXPANDED_WIDTH &&
                        heightDp in COMPACT_HEIGHT..EXPANDED_HEIGHT -> TABLET_LANDSCAPE

                widthDp >= EXTRA_EXPANDED_WIDTH &&
                        heightDp >= EXTRA_EXPANDED_HEIGHT -> DESKTOP

                else -> DESKTOP
            }
        }
    }
}


/**
 * A Standard Width for mobile devices in portrait orientation.
 *
 * @see <a href="https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes">Source</a>
 */
val COMPACT_WIDTH = 600.dp

/**
 * A Standard Expanded Width for inner display of foldables.
 *
 * @see <a href="https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes">Source</a>
 */
val EXPANDED_WIDTH = 840.dp

/**
 * Width of very large displays usually tablets or large monitors.
 *
 * @see <a href="https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes">Source</a>
 */
val EXTRA_EXPANDED_WIDTH = 1600.dp

/**
 * A Standard height for mobile devices in landscape orientation.
 *
 * @see <a href="https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes">Source</a>
 */
val COMPACT_HEIGHT = 480.dp

/**
 * A Standard Expanded Height of large displays in portrait orientation.
 *
 * @see <a href="https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes">Source</a>
 */
val EXPANDED_HEIGHT = 900.dp

/**
 * Height of a very large display usually tablets or large monitors.
 *
 * @see <a href="https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes">Source</a>
 */
val EXTRA_EXPANDED_HEIGHT = 1200.dp