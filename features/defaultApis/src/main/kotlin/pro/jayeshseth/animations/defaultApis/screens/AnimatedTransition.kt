package pro.jayeshseth.animations.defaultApis.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.defaultApis.animations.animatedContent.AdvancedIncrementCounter
import pro.jayeshseth.animations.defaultApis.animations.animatedContent.BasicIncrementCounter

@Composable
fun AnimatedTransition() {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                BasicIncrementCounter()
                AdvancedIncrementCounter()
            }
        }
    }
}