package pro.jayeshseth.animations.core.navigation.scenesAndStrategies

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import pro.jayeshseth.animations.core.navigation.scenesAndStrategies.BasicTwoPaneScene.Companion.PRIMARY_CONTENT_KEY
import pro.jayeshseth.animations.core.navigation.scenesAndStrategies.BasicTwoPaneScene.Companion.SECONDARY_CONTENT_KEY
import pro.jayeshseth.animations.core.ui.utils.COMPACT_WIDTH

/**
 * A Basic Two [Scene] that divides contents in 40:60 split
 */
class BasicTwoPaneScene<T : Any>(
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>,
    val primaryContent: NavEntry<T>,
    val secondaryContent: NavEntry<T>
) : Scene<T> {
    override val entries: List<NavEntry<T>>
        get() = listOf(primaryContent, secondaryContent)
    override val content: @Composable (() -> Unit)
        get() = {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.weight(.4f)) { primaryContent.Content() }
                Column(
                    modifier = Modifier.weight(.6f)
                ) {
                    AnimatedContent(
                        targetState = secondaryContent,
                        contentKey = { entry -> entry.contentKey },
                        transitionSpec = {
                            slideInHorizontally(initialOffsetX = { it }).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { -it }
                                )
                            )
                        }
                    ) { entry ->
                        entry.Content()
                    }
                }
            }
        }

    companion object {
        const val PRIMARY_CONTENT_KEY = "primaryContent"
        const val SECONDARY_CONTENT_KEY = "secondaryContent"

        /**
         * Helper function to add metadata to a [NavEntry] indicating it can be displayed
         * in the primary pane of a [BasicTwoPaneScene].
         */
        fun primaryPane() = mapOf(PRIMARY_CONTENT_KEY to true)

        /**
         * Helper function to add metadata to a [NavEntry] indicating it can be displayed
         * in the secondary pane of a the [BasicTwoPaneScene].
         */
        fun secondaryPane() = mapOf(SECONDARY_CONTENT_KEY to true)
    }
}

@Composable
fun <T : Any> rememberBasicTwoPaneSceneStrategy(): BasicTwoPaneSceneStrategy<T> {
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
//    val configuration = currentDeviceConfiguration()
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    val sizePx = windowInfo.containerSize

    val widthDp = with(density) { sizePx.width.toDp() }
//    val heightDp = with(density) { sizePx.height.toDp() }
    return remember(widthDp) {
//        println("rememberBasicTwoPaneSceneStrategy $widthDp")
        BasicTwoPaneSceneStrategy(widthDp)
    }
}


class BasicTwoPaneSceneStrategy<T : Any>(val width: Dp) : SceneStrategy<T> {
    var isSceneActive by mutableStateOf(false)
        private set

    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
//        println(
//            "isWide ${width}"
//        )
//        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) {
//            isSceneActive = false
//            return null
//        }
        if (width < COMPACT_WIDTH) {
            isSceneActive = false
            return null
        }
//        println(entries)
//        val hasPrimary = entries.any { it.metadata.containsKey(PRIMARY_CONTENT_KEY) }
//        val hasSecondary =
//            entries.lastOrNull()?.metadata?.containsKey(SECONDARY_CONTENT_KEY) == true


        val primaryContent =
            entries.findLast { it.metadata.containsKey(PRIMARY_CONTENT_KEY) }

        val secondaryContent = entries.lastOrNull()
            ?.takeIf { it.metadata.containsKey(SECONDARY_CONTENT_KEY) }

        println("isSceneActive $secondaryContent")
//        isSceneActive = primaryContent != null && secondaryContent != null

        if (primaryContent == null || secondaryContent == null) {
            return null
        }
        val sceneKey = primaryContent.contentKey
        return BasicTwoPaneScene(
            key = sceneKey,
            previousEntries = entries.dropLast(1),
            primaryContent = primaryContent,
            secondaryContent = secondaryContent
        )
    }
}
