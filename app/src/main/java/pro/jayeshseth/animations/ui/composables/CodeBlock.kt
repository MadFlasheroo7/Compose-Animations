package pro.jayeshseth.animations.ui.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theapache64.rebugger.Rebugger
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import pro.jayeshseth.animations.util.codePreviewer.CodeGenerator

@Composable
fun CodeBlockWithLineNumbers(text: List<String>, modifier: Modifier = Modifier) {
    val maxDigits = text.size.toString().length
    val sampleIndexText = "9".repeat(maxDigits)

    // Measure the width of the widest index ("999" for 3-digit lines)
    val textMeasurer = rememberTextMeasurer()
    val indexWidth = textMeasurer.measure(
        text = AnnotatedString(sampleIndexText),
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp
        )
    ).size.width

    SelectionContainer {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            text.mapIndexed { index, line ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DisableSelection {
                        Box(
                            modifier = Modifier
                                .width(with(LocalDensity.current) { indexWidth.toDp() }),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Text(
                        text = CodeGenerator.highlightedCode(
                            line,
                            if (isSystemInDarkTheme()) CodeThemeType.Monokai else CodeThemeType.Default
                        ),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
    Rebugger(
        composableName = "code preview",
        trackMap = mapOf(
            "code" to text,
            "maxDigit" to maxDigits,
            "indexWidth" to indexWidth,
            "sampleIndexText" to sampleIndexText

        )
    )
}
