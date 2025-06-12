package pro.jayeshseth.animations.util.codePreviewer

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString

@Stable
object CodeGenerator {
    private val language = CodeLang.Dart
    private val parser = PrettifyParser()

    fun highlightedCode(
        code: String,
        codeThemeType: CodeThemeType = CodeThemeType.Monokai,
    ): AnnotatedString {
        val theme = codeThemeType.theme()

        return parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = language,
            code = code
        )
    }
}