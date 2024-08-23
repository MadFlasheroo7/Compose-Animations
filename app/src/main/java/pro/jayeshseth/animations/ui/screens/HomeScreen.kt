package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pro.jayeshseth.commoncomponents.HomeScaffold
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navToAnimateVisibility: () -> Unit,
    navToAnimateContent: () -> Unit,
    navToAnimateGesture: () -> Unit,
    navToAnimateNav: () -> Unit,
    navToAnimateInfiniteRotation: () -> Unit,
    navToSwipeRefresh: () -> Unit,
    navToBouncyRopes: () -> Unit,
    navToAnimateValueAsState: () -> Unit,
    navToAnimatedListItemPlacement: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    HomeScaffold(
        verticalScrollState = scrollState,
        topAppBarScrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Animations",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) {
        StatusBarAwareThemedColumn(
            statusBarColor = Color.Transparent,
            modifier = Modifier
                .padding(it)
                .navigationBarsPadding()
        ) {
            InteractiveButton(
                text = "Animate Visibility",
                onClick = navToAnimateVisibility,
            )
            InteractiveButton(
                text = "Animated List Item Placement",
                onClick = navToAnimatedListItemPlacement,
            )
            InteractiveButton(
                text = "Animated Content",
                onClick = navToAnimateContent,
            )
            InteractiveButton(
                text = "Animate Value As State",
                onClick = navToAnimateValueAsState
            )
            InteractiveButton(
                text = "Animated Gesture",
                onClick = navToAnimateGesture,
            )
            InteractiveButton(
                text = "Infinite Rotation",
                onClick = navToAnimateInfiniteRotation
            )
            InteractiveButton(
                text = "Swipe To Refresh",
                onClick = navToSwipeRefresh
            )
            InteractiveButton(
                text = "Nav Animation",
                onClick = navToAnimateNav
            )
            InteractiveButton(
                text = "Bouncy Ropes",
                onClick = navToBouncyRopes
            )
            InteractiveButton(
                text = "About",
                onClick = navToBouncyRopes
            )
        }
    }
}