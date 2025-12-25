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


fun LazyListScope.lazyNavBarPadding() {
    item { Spacer(Modifier.navigationBarsPadding()) }
}

fun LazyListScope.lazyStatusBarPadding() {
    item { Spacer(Modifier.statusBarsPadding()) }
}

fun LazyListScope.lazySpacer(size: Dp) {
    item { Spacer(Modifier.size(size)) }
}