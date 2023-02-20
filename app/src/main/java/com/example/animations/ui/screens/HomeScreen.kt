package com.example.animations.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animations.R
import com.example.animations.ui.composables.InteractiveButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navToAnimateVisibility: () -> Unit) {
    Column() {
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
//                .windowInsetsPadding(WindowInsets.safeContent),
        ) {
            InteractiveButton(
                text = "Animate Visibility",
                onClick = navToAnimateVisibility
            )
            InteractiveButton(
                text = "Animated Content",
                onClick = navToAnimateVisibility
            )
            InteractiveButton(
                text = "Animate List Items ",
                onClick = navToAnimateVisibility
            )
            InteractiveButton(
                text = "Bouncy Ropes",
                onClick = navToAnimateVisibility
            )
            InteractiveButton(
                text = "Swipe To Dismiss",
                onClick = navToAnimateVisibility
            )
            InteractiveButton(
                text = "Third Party Animations",
                onClick = navToAnimateVisibility
            )

//            VisibilityAnimation()
        }
    }
}