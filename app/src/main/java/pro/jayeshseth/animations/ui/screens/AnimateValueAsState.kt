package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.ui.composables.AnimationCard
import pro.jayeshseth.animations.ui.defaultApis.animateValue.AnimateColorAsState
import pro.jayeshseth.animations.ui.defaultApis.animateValue.AnimateDpAsState
import pro.jayeshseth.animations.ui.defaultApis.animateValue.AnimateFloatAsState
import pro.jayeshseth.animations.ui.defaultApis.animateValue.AnimateOffsetAsState
import pro.jayeshseth.animations.util.AnimationContent
import pro.jayeshseth.animations.util.AnimationItem
import pro.jayeshseth.animations.util.DURATION
import pro.jayeshseth.animations.util.OnClickLink
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@Composable
fun AnimateValueAsState(onClickLink: OnClickLink) {
    StatusBarAwareThemedLazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .animateContentSize(
                tween(DURATION, easing = LinearEasing)
            )
    ) {
        item { Spacer(Modifier.statusBarsPadding()) }
        itemsIndexed(animations) { index, animation ->
            AnimationCard(
                index = index,
                onClickLink = { onClickLink(animation.source) },
                animationContent = AnimationContent(
                    title = animation.title,
                    content = {
                        animation.content(it)
                    }
                )
            )
        }
        item { Spacer(Modifier.navigationBarsPadding()) }
    }
}

/**
 * list of value based animation
 */
private val animations: List<AnimationItem> by lazy {
    mutableListOf(
        AnimationItem(
            title = "Animate Float As State",
            source = "animations/animateValue/AnimateFloat.kt"
        ) { AnimateFloatAsState(it) },
        AnimationItem(
            title = "Animate Dp As State",
            source = "animations/animateValue/AnimateDp.kt"
        ) { AnimateDpAsState(it) },
        AnimationItem(
            source = "animations/animateValue/AnimateOffset.kt"
        ) { AnimateOffsetAsState() },
        AnimationItem(
            title = "Animate Color As State",
            source = "animations/animateValue/AnimateColor.kt"
        ) { AnimateColorAsState(it) }
    )
}