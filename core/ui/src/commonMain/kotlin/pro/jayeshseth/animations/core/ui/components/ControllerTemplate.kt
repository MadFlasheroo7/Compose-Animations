package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A composable that provides a standard layout for a section with a title and content.
 * It arranges the title and content vertically in a [Column].
 * The title is placed in a [Row] that fills the maximum width, allowing for flexible alignment of its children.
 *
 * @param title A composable lambda that defines the title of the section.
 *              It's placed in a `Row` with `horizontalArrangement = Arrangement.SpaceBetween`.
 * @param content A composable lambda that defines the main content of the section, displayed below the title.
 * @param modifier The [Modifier] to be applied to the root [Column] of this component.
 */
@Composable
fun ControllerTemplate(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            title()
        }
        content()
    }
}