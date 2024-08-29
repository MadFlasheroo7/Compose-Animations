package pro.jayeshseth.animations.util

import androidx.compose.runtime.Composable

const val DURATION = 1000

data class AnimationItem(
    val title: String? = null,
    val source: String,
    val content: @Composable (trigger: Boolean) -> Unit
)


//@Composable
//fun ldd(modifier: Modifier = Modifier) {
//    LazyColumn {
//        items(animations) { animation->
//            AnimationCard(
//
//                animationScreen = AnimationScreen(
//                    title = animation.title,
//                    content = {
//                        animation.content(it)
//                    }
//                )
//            )
//        }
//    }
//}