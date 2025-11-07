package pro.jayeshseth.animations.core.model.codePreviewer

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

// TODO work on custom impl code prev
@Stable
object SyntaxColors {
    val keyword = Color(0xFFCC7832)
    val string = Color(0xFF6A8759)
    val number = Color(0xFF6897BB)
    val comment = Color(0xFF808080)
    val punctuation = Color(0xFFA9B7C6)
    val functionName = Color(0xFFFFC66D)
    val default = Color(0xFFA9B7C6)
}

@Stable
data class SyntaxToken(val range: IntRange, val color: Color)