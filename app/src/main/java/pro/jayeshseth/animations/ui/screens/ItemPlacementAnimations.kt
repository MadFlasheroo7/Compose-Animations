package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedColumn

@Composable
fun ItemPlacementAnimation(
    navToTrippyBlinders: () -> Unit,
    navToSlideInOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    StatusBarAwareThemedColumn(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        InteractiveButton(
            text = "Slide In/Out",
            onClick = navToSlideInOut,
        )
        InteractiveButton(
            text = "Fidget Toy",
            onClick = navToTrippyBlinders,
        )
        InteractiveButton(
            text = "Trippy Blinders",
            onClick = navToTrippyBlinders,
        )
    }
}