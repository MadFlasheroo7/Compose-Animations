package pro.jayeshseth.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.ui.theme.AnimationsTheme

const val BASE_URL =
    "https://github.com/MadFlasheroo7/Compose-Animations/tree/main/app/src/main/java/pro/jayeshseth/animations/ui"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val urlHandler = LocalUriHandler.current
            AnimationsTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph {
                        urlHandler.openUri("$BASE_URL/$it")
                    }
                }
            }
        }
    }
}

//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalClipboardManager
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import java.util.regex.Pattern
//
//const val BASE_URL = "https://github.com/MadFlasheroo7/Compose-Animations/tree/main/app/src/main/java/pro/jayeshseth/animations/ui"
//
//// Main Activity: Entry point of the application
//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Set the content to our main Composable screen
//        setContent {
//            AnimationsTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    CodeGeneratorScreen()
//                }
//            }
//        }
//    }
//}

//// --- Main Screen Composable with Tab Layout ---
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CodeGeneratorScreen() {
//    // State for the two numbers controlled by sliders
//    var number1 by remember { mutableFloatStateOf(1f) }
//    var number2 by remember { mutableFloatStateOf(2f) }
//
//    // State for managing which tab is selected
//    var tabIndex by remember { mutableIntStateOf(0) }
//    val tabs = listOf("Controls", "Generated Code")
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Compose Code Generator") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        }
//    ) { paddingValues ->
//        Column(modifier = Modifier.padding(paddingValues)) {
//            // TabRow to switch between Controls and Code View
//            TabRow(selectedTabIndex = tabIndex) {
//                tabs.forEachIndexed { index, title ->
//                    Tab(
//                        text = { Text(title) },
//                        selected = tabIndex == index,
//                        onClick = { tabIndex = index }
//                    )
//                }
//            }
//            // Content of the selected tab
//            when (tabIndex) {
//                0 -> ControlsTab(
//                    number1 = number1,
//                    onNumber1Change = { number1 = it },
//                    number2 = number2,
//                    onNumber2Change = { number2 = it }
//                )
//
//                1 -> CodeViewerTab(number1 = number1.toInt(), number2 = number2.toInt())
//            }
//        }
//    }
//}
//
//// --- Tab 1: Sliders to control the numbers ---
//@Composable
//fun ControlsTab(
//    number1: Float,
//    onNumber1Change: (Float) -> Unit,
//    number2: Float,
//    onNumber2Change: (Float) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
        // Slider for the first number
//        Text(
//            text = "First Number: ${number1.toInt()}",
//            style = MaterialTheme.typography.headlineSmall
//        )
//        Slider(
//            value = number1,
//            onValueChange = onNumber1Change,
//            valueRange = 0f..100f,
//            steps = 99
//        )
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Slider for the second number
//        Text(
//            text = "Second Number: ${number2.toInt()}",
//            style = MaterialTheme.typography.headlineSmall
//        )
//        Slider(
//            value = number2,
//            onValueChange = onNumber2Change,
//            valueRange = 0f..100f,
//            steps = 99
//        )
//    }
//}

// --- Tab 2: Displays the generated, highlighted code ---
//@Composable
//fun CodeViewerTab(number1: Int, number2: Int) {
//    // Dynamically generate the code string based on slider values
//    val generatedCode = """
//    fun main() {
//        val a = $number1
//        val b = "$number2"
//        print("$number1 + $number2 = ${number1 + number2}")
//    }
//    """.trimIndent()
//
//    val context = LocalContext.current
//    val clipboardManager = LocalClipboardManager.current
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF2B2B2B)) // Dark background for code
//            .padding(16.dp)
//    ) {
//        Column {
//            // Display the code with syntax highlighting
//            Text(
//                text = highlightCode(code = generatedCode),
//                fontFamily = FontFamily.Monospace,
//                fontSize = 14.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//            )
//
//            // Button to copy the code to the clipboard
//            Button(
//                onClick = {
//                    clipboardManager.setText(AnnotatedString(generatedCode))
//                    Toast.makeText(context, "Code copied!", Toast.LENGTH_SHORT).show()
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp)
//            ) {
//                // We are using a built-in drawable for the icon for simplicity.
//                // In a real app, you would add a vector asset.
//                Icon(
//                    painter = painterResource(id = android.R.drawable.ic_menu_edit),
//                    contentDescription = "Copy Code",
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Copy Code")
//            }
//        }
//    }
//}
//
//
//// --- Syntax Highlighting Logic ---
//
//// Define colors for different code elements
//object SyntaxColors {
//    val keyword = Color(0xFFCC7832)
//    val string = Color(0xFF6A8759)
//    val number = Color(0xFF6897BB)
//    val comment = Color(0xFF808080)
//    val punctuation = Color(0xFFA9B7C6)
//    val functionName = Color(0xFFFFC66D)
//    val default = Color(0xFFA9B7C6)
//}
//
//// Data class to hold a tokenized part of the code
//private data class Token(val range: IntRange, val color: Color)

///**
// * Takes a string of code and returns an AnnotatedString with syntax highlighting.
// * This function tokenizes the code using regex and applies styles.
// */
//fun highlightCode(code: String): AnnotatedString {
//    return buildAnnotatedString {
//        // Regex patterns for different code elements
//        val patterns = mapOf(
//            // Keywords: fun, val, var, etc.
//            "\\b(fun|val|var|if|else|when|for|while|return|print)\\b" to SyntaxColors.keyword,
//            // Numbers
//            "\\b\\d+\\b" to SyntaxColors.number,
//            // Strings enclosed in double quotes
//            "\"[^\"]*\"" to SyntaxColors.string,
//            // Function names (simple version: word before a parenthesis)
//            "\\b(\\w+)(?=\\()" to SyntaxColors.functionName,
//            // Punctuation: braces, parentheses, operators
//            "[\\(\\)\\{\\}\\=\\+\\-\\*\\/]" to SyntaxColors.punctuation
//        )
//
//        val tokens = mutableListOf<Token>()
//
//        // Find all matches for each pattern and add them to a list of tokens
//        patterns.forEach { (pattern, color) ->
//            Pattern.compile(pattern).matcher(code).run {
//                while (find()) {
//                    tokens.add(Token(start()..end(), color))
//                }
//            }
//        }
//
//        // Sort tokens by their starting position to handle them in order
//        tokens.sortBy { it.range.first }
//
//        // Keep track of non-overlapping tokens to apply styles correctly
//        val appliedTokens = mutableListOf<Token>()
//        var lastTokenEnd = -1
//        for (token in tokens) {
//            if (token.range.first > lastTokenEnd) {
//                appliedTokens.add(token)
//                lastTokenEnd = token.range.last -1
//            }
//        }
//
//
//        // Build the AnnotatedString
//        var lastIndex = 0
//        appliedTokens.forEach { token ->
//            // Append text before the current token with default color
//            if (token.range.first > lastIndex) {
//                withStyle(style = SpanStyle(color = SyntaxColors.default)) {
//                    append(code.substring(lastIndex, token.range.first))
//                }
//            }
//            // Append the token itself with its specific color
//            withStyle(style = SpanStyle(color = token.color)) {
//                append(code.substring(token.range))
//            }
//            lastIndex = token.range.last
//        }
//        // Append any remaining text after the last token
//        if (lastIndex < code.length) {
//            withStyle(style = SpanStyle(color = SyntaxColors.default)) {
//                append(code.substring(lastIndex))
//            }
//        }
//    }
//}

///**
// * Takes a string of code and returns an AnnotatedString with syntax highlighting.
// * This function tokenizes the code using regex and applies styles.
// */
//fun highlightCode(code: String): AnnotatedString {
//    return buildAnnotatedString {
//        // Regex patterns for different code elements
//        val patterns = mapOf(
//            // Keywords: fun, val, var, etc.
//            "\\b(fun|val|var|if|else|when|for|while|return|print)\\b" to SyntaxColors.keyword,
//            // Numbers
//            "\\b\\d+\\b" to SyntaxColors.number,
//            // Strings enclosed in double quotes
//            "\"[^\"]*\"" to SyntaxColors.string,
//            // Function names (simple version: word before a parenthesis)
//            "\\b(\\w+)(?=\\()" to SyntaxColors.functionName,
//            // Punctuation: braces, parentheses, operators
//            "[\\(\\)\\{\\}\\=\\+\\-\\*\\/]" to SyntaxColors.punctuation
//        )
//
//        val tokens = mutableListOf<Token>()
//
//        // Find all matches for each pattern and add them to a list of tokens
//        patterns.forEach { (pattern, color) ->
//            Pattern.compile(pattern).matcher(code).run {
//                while (find()) {
//                    // FIX 1: Create a proper inclusive IntRange.
//                    // matcher.end() is an exclusive index, so subtract 1 for an inclusive range.
//                    if (start() < end()) { // Ensure the range is valid
//                        tokens.add(Token(start()..(end() - 1), color))
//                    }
//                }
//            }
//        }
//
//        // Sort tokens by their starting position to handle them in order
//        tokens.sortBy { it.range.first }
//
//        // FIX 2: Correctly filter out overlapping tokens.
//        val appliedTokens = mutableListOf<Token>()
//        var lastTokenEnd = -1 // Tracks the INCLUSIVE end index of the last applied token.
//        for (token in tokens) {
//            // A token is valid if it starts after the previous one has ended.
//            if (token.range.first > lastTokenEnd) {
//                appliedTokens.add(token)
//                lastTokenEnd = token.range.last
//            }
//        }
//
//        // Build the AnnotatedString
//        var lastIndex = 0
//        appliedTokens.forEach { token ->
//            // Append text before the current token with default color
//            if (token.range.first > lastIndex) {
//                withStyle(style = SpanStyle(color = SyntaxColors.default)) {
//                    append(code.substring(lastIndex, token.range.first))
//                }
//            }
//            // Append the token itself with its specific color. This is now safe.
//            withStyle(style = SpanStyle(color = token.color)) {
//                append(code.substring(token.range))
//            }
//            // FIX 3: Update lastIndex to point to the character AFTER the current token.
//            lastIndex = token.range.last + 1
//        }
//        // Append any remaining text after the last token
//        if (lastIndex < code.length) {
//            withStyle(style = SpanStyle(color = SyntaxColors.default)) {
//                append(code.substring(lastIndex))
//            }
//        }
//    }
//}
