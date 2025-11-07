package pro.jayeshseth.animations.defaultApis.screens

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
import pro.jayeshseth.animations.core.model.AnimationContent
import pro.jayeshseth.animations.core.model.AnimationItem
import pro.jayeshseth.animations.core.model.DURATION
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.ui.components.AnimationCard
import pro.jayeshseth.animations.defaultApis.animations.animateValue.AnimateColorAsState
import pro.jayeshseth.animations.defaultApis.animations.animateValue.AnimateDpAsState
import pro.jayeshseth.animations.defaultApis.animations.animateValue.AnimateFloatAsState
import pro.jayeshseth.animations.defaultApis.animations.animateValue.AnimateOffsetAsState
import pro.jayeshseth.animations.defaultApis.utils.BASE_FEATURE_ROUTE
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
            source = "${BASE_FEATURE_ROUTE}/animations/animateValue/AnimateFloat.kt"
        ) { AnimateFloatAsState(it) },
        AnimationItem(
            title = "Animate Dp As State",
            source = "${BASE_FEATURE_ROUTE}/animations/animateValue/AnimateDp.kt"
        ) { AnimateDpAsState(it) },
        AnimationItem(
            source = "${BASE_FEATURE_ROUTE}/animations/animateValue/AnimateOffset.kt"
        ) { AnimateOffsetAsState() },
        AnimationItem(
            title = "Animate Color As State",
            source = "${BASE_FEATURE_ROUTE}/animations/animateValue/AnimateColor.kt"
        ) { AnimateColorAsState(it) }
    )
}