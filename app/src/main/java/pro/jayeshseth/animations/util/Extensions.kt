package pro.jayeshseth.animations.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import java.util.Locale

/**
 * Converts a float to a simplified scientific notation string (e.g., 300.0 -> "3e2").
 */
fun Float.toExponent(): String {

//    fun toSimpleExponent(number: Double): String {
    // Handle the zero case separately, as DecimalFormat might format it oddly.
    if (this == 0.0f) {
        return "0e0"
    }

    // 1. Use DecimalFormat to get a standard scientific notation string.
    // The pattern "0.0E0" ensures we get one digit before the decimal.
    val decimalFormat = java.text.DecimalFormat("0.0E0")
    val formattedString = decimalFormat.format(this) // This will produce something like "3.0E2"

    // 2. Split the string into the number part (mantissa) and the exponent part.
    val parts = formattedString.split("E")
    var mantissa = parts[0]
    val exponent = parts[1].toInt() // The exponent as an integer

    // 3. Clean up the mantissa. If it ends with ".0", remove it.
    if (mantissa.endsWith(".0")) {
        mantissa = mantissa.substring(0, mantissa.length - 2)
    }

    // 4. Combine the cleaned-up parts into the final desired format.
    return "${mantissa}e${exponent}"
//    }
}

fun Float.round(): String {
    return String.format(locale = Locale.getDefault(), "%.2f", this)
}

fun LazyListScope.lazyNavBarPadding() {
    item { Spacer(Modifier.navigationBarsPadding()) }
}

fun LazyListScope.lazyStatusBarPadding() {
    item { Spacer(Modifier.statusBarsPadding()) }
}