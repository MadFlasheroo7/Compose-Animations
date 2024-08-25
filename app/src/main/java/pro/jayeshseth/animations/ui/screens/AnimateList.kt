package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pro.jayeshseth.animations.ui.composables.ListItem
import pro.jayeshseth.animations.util.CatItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SwipeRefresh() {
    var catList by remember {
        mutableStateOf(
            listOf(
                CatItem(1, "Luna"),
                CatItem(2, "Simba"),
                CatItem(3, "Shadow"),
                CatItem(4, "Whiskers"),
                CatItem(5, "Mittens"),
                CatItem(6, "Cleo"),
                CatItem(7, "Salem"),
                CatItem(8, "Tiger"),
                CatItem(9, "Gizmo"),
                CatItem(10, "Felix"),
            )
        )
    }

    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            coroutineScope.launch {
                delay(500)
                catList = catList.shuffled()
            }
        },
    )

    Box(
        modifier = Modifier.pullRefresh(
            state = refreshState,
            enabled = true
        ),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            items(catList, key = { it.id }) { item ->
                ListItem(
                    catImage = item.catImage,
                    catName = item.catName,
                    modifier = Modifier.animateItem(
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                )
            }
        }
        PullRefreshIndicator(
            refreshing = false,
            state = refreshState,
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}

