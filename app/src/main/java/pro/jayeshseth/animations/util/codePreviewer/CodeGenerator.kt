package pro.jayeshseth.animations.util.codePreviewer

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import java.util.regex.Pattern

@Stable
object CodeGenerator {
    private val syntaxRegex = mapOf(
        /**
         * Keywords: fun, val, var, etc.
         */
        "\\b(fun|val|var|if|else|when|for|while|return|print)\\b" to SyntaxColors.keyword,
        /**
         * Numbers
         */
        "\\b\\d+\\b" to SyntaxColors.number,
        /**
         * Strings enclosed in double quotes
         */
        "\"[^\"]*\"" to SyntaxColors.string,
        /**
         * Function names (simple version: word before a parenthesis)
         */
        "\\b(\\w+)(?=\\()" to SyntaxColors.functionName,
        /**
         * Punctuation: braces, parentheses, operators
         */
        "[\\(\\)\\{\\}\\=\\+\\-\\*\\/]" to SyntaxColors.punctuation
    )

    private val syntaxTokens = mutableListOf<SyntaxToken>()

    /**
     * Takes a string of code and returns an AnnotatedString with syntax highlighting.
     * This function tokenizes the code using regex and applies styles.
     */
    fun highlightedCode(code: String): AnnotatedString {
        return buildAnnotatedString {
            syntaxRegex.forEach { (syntax, color) ->
                Pattern.compile(syntax).matcher(code).run {
                    while (find()) {
                        if (start() < end()) {
                            syntaxTokens.add(SyntaxToken(start()..(end() - 1), color))
                        }
                    }
                }
            }


            syntaxTokens.sortBy { it.range.first }

            val appliedSyntaxToken = mutableListOf<SyntaxToken>()
            var lastTokenEnd = -1
            for (token in syntaxTokens) {
                if (token.range.first > lastTokenEnd) {
                    appliedSyntaxToken.add(token)
                    lastTokenEnd = token.range.last
                }
                Log.d(
                    "highlightedCode",
                    "token: $token\nfirst: ${token.range.first}\nlast: ${token.range.last}"
                )
            }

            var lastIndex = 0
            appliedSyntaxToken.forEach { token ->
                if (token.range.first > lastIndex) {
                    withStyle(style = SpanStyle(color = SyntaxColors.default)) {
                        append(code.substring(lastIndex, token.range.first))
                    }
                }
                withStyle(style = SpanStyle(color = token.color)) {
                    Log.d(
                        "highlightedCode style",
                        "val: ${code.substring(token.range)}\ntoken: $token\nfirst: ${token.range.first}\nlast: ${token.range.last}"
                    )
                    append(code.substring(token.range))
                }
                lastIndex = token.range.last + 1
            }
            if (lastIndex < code.length) {
                withStyle(style = SpanStyle(color = SyntaxColors.default)) {
                    append(code.substring(lastIndex))
                }
            }
        }
    }
}