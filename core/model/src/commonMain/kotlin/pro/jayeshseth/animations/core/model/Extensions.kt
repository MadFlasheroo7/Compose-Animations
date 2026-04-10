package pro.jayeshseth.animations.core.model

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * Converts a float to a simplified scientific notation string (e.g., 300.0 -> "3e2").
 */
fun Float.toExponent(): String {
    // Handle the zero case separately
    if (this == 0.0f) {
        return "0e0"
    }

    // Get the exponent using logarithm
    val exponent = floor(log10(abs(this.toDouble()))).toInt()

    // Calculate the mantissa
    val mantissa = this / 10.0.pow(exponent.toDouble()).toFloat()

    // Format mantissa: remove ".0" if it's a whole number
    val mantissaStr = if (mantissa % 1 == 0f) {
        mantissa.toInt().toString()
    } else {
        mantissa.toString()
    }

    return "${mantissaStr}e${exponent}"
}

fun Float.round(): String {
    // Multiply by 100, round, then divide by 100
    val rounded = kotlin.math.round(this * 100) / 100

    // Format to 2 decimal places
    return buildString {
        if (rounded < 0) append('-')
        val absValue = abs(rounded)
        val intPart = absValue.toInt()
        val decPart = ((absValue - intPart) * 100).toInt()

        append(intPart)
        append('.')
        if (decPart < 10) append('0')
        append(decPart)
    }
}


/**
 * Adds a spacer item to a [LazyListScope] that provides padding for the navigation bars.
 * This is useful for preventing content at the end of a lazy list from being obscured
 * by system navigation gestures or bars.
 *
 * Example usage:
 * ```
 * LazyColumn {
 *     // ... other items
 *     lazyNavBarPadding()
 * }
 * ```
 */
fun LazyListScope.lazyNavBarPadding() {
    item { Spacer(Modifier.navigationBarsPadding()) }
}

/**
 * Adds a spacer item to a `LazyListScope` that applies padding to accommodate the system status bars.
 *
 * This is a convenience function for adding a spacer at the top of a lazy list (`LazyColumn`, `LazyRow`)
 * to prevent content from being obscured by the status bar, especially when using edge-to-edge display.
 *
 * Example usage:
 * ```
 * LazyColumn {
 *     lazyStatusBarPadding()
 *     // ... other items
 * }
 * ```
 */
fun LazyListScope.lazyStatusBarPadding() {
    item { Spacer(Modifier.statusBarsPadding()) }
}

/**
 * Adds a [Spacer] as an item in a [LazyListScope] with a specified size.
 * This is a convenience function to easily add fixed-size spacing between items in a lazy list.
 *
 * @param size The size of the spacer. This will be applied to both width and height.
 */
fun LazyListScope.lazySpacer(size: Dp) {
    item { Spacer(Modifier.size(size)) }
}