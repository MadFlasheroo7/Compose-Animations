package com.example.animations.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animations.ui.composables.InteractiveButton

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
    navToAnimateValueAsState: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Animations in Jetpack Compose",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            ),
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            InteractiveButton(
                text = "Animate Visibility",
                onClick = navToAnimateVisibility,
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
        }
    }
}