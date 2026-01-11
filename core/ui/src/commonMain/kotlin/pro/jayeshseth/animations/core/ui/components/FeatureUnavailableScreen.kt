package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * A composable that displays a centered message indicating that a feature is unavailable.
 * This is useful as a placeholder for features that are not yet implemented or are
 * disabled for any reason.
 *
 * The screen consists of a [Box] that fills the maximum available size and centers its content.
 * Inside the box, a [Text] composable displays the provided message with a prominent style.
 *
 * @param message The text message to display on the screen.
 * @param modifier The [Modifier] to be applied to the container [Box]. Defaults to [Modifier].
 */
@Composable
fun FeatureUnavailableScreen(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}