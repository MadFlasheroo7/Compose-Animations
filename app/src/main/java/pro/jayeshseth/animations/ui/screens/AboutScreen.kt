package pro.jayeshseth.animations.ui.screens

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.content.getSystemService
import de.apuri.physicslayout.lib.physicsBody
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.delay
import pro.jayeshseth.animations.R
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily

/**
 * A wrapper that cycles through a list of styles infinitely.
 *
 * @param styles The list of TextStyles to cycle through.
 * @param animationSpec The animation spec for the transition (e.g., spring, tween).
 * @param delayMillis How long to stay on a specific style before moving to the next.
 */
@Composable
fun rememberInfiniteTextStyle(
    styles: List<TextStyle>,
    animationSpec: AnimationSpec<Float> = tween(),
    delayMillis: Long = 1500
): State<TextStyle> {
    // Track which style index we are currently on
    var currentIndex by remember { mutableIntStateOf(0) }

    // Use your core logic to animate to the current index
    val animatedStyle = animateTextStyleAsState(
        targetValue = styles[currentIndex],
        animationSpec = animationSpec
    )

    // The infinite loop driver
    LaunchedEffect(Unit) {
        while (true) {
            delay(delayMillis)
            // Move to next index, looping back to 0 at the end
            currentIndex = (currentIndex + 1) % styles.size
        }
    }

    return animatedStyle
}

@Composable
fun animateTextStyleAsState(
    targetValue: TextStyle,
    animationSpec: AnimationSpec<Float> = spring(),
    finishedListener: ((TextStyle) -> Unit)? = null
): State<TextStyle> {

    val animation = remember { Animatable(0f) }
    var previousTextStyle by remember { mutableStateOf(targetValue) }
    var nextTextStyle by remember { mutableStateOf(targetValue) }

    val textStyleState = remember(animation.value) {
        derivedStateOf {
            lerp(previousTextStyle, nextTextStyle, animation.value)
        }
    }

    LaunchedEffect(targetValue, animationSpec) {
        previousTextStyle = textStyleState.value
        nextTextStyle = targetValue
        animation.snapTo(0f)
        animation.animateTo(1f, animationSpec)
        finishedListener?.invoke(textStyleState.value)
    }

    return textStyleState
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun AboutScreen(hazeState: HazeState = rememberHazeState(), modifier: Modifier = Modifier) {
    val About = stringResource(R.string.about)
    var bool by rememberSaveable { mutableStateOf(false) }
    val aboutList by remember { mutableStateOf(About.split(" ")) }
    val urlLauncher = LocalUriHandler.current
    val sourceCode = "https://github.com/MadFlasheroo7/Compose-Animations"

    val backgroundColor by animateColorAsState(
        targetValue = if (bool) Color.Black else Color.Transparent,
        animationSpec = tween(2000),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .then(
                if (bool) Modifier.clickable(
                    onClick = {
                        bool = !bool
                    }
                ) else Modifier
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 25.dp)
        ) {
            AnimatedVisibility(
                visible = !bool,
                enter = fadeIn(
                    animationSpec = tween(2000)
                ),
                exit = fadeOut(
                    animationSpec = tween(2000)
                )
            ) {
                Text(
                    text = "\uD83D\uDC96",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            AnimatedVisibility(
                visible = !bool,
                enter = fadeIn(
                    animationSpec = tween(1000)
                ),
                exit = fadeOut(
                    animationSpec = tween(1000, delayMillis = 1000)
                )
            ) {
                Text(
                    text = " Animations ",
                    fontSize = 25.sp,
                    fontWeight = FontWeight(750),
                    fontFamily = syneFontFamily
                )
            }

            AnimatedVisibility(
                visible = !bool,
                enter = fadeIn(
                    animationSpec = tween(2000)
                ),
                exit = fadeOut(
                    animationSpec = tween(2000)
                )
            ) {
                Text(
                    text = "\uD83D\uDC96",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        AnimatedVisibility(
            visible = !bool,
            enter = fadeIn(
                animationSpec = tween(2000)
            ),
            exit = fadeOut(
                animationSpec = tween(2000)
            ),
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = About,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = syneFontFamily
                )
            )
        }

        AnimatedVisibility(
            visible = !bool,
            enter = fadeIn(
                animationSpec = tween(2000)
            ),
            exit = fadeOut(
                animationSpec = tween(2000)
            )
        ) {
            SocialMedia()
        }


        AnimatedVisibility(
            visible = !bool,
            enter = fadeIn(),
            exit = fadeOut(
                animationSpec = tween(2000)
            )
        ) {
            InteractiveButton(
                text = "Source Code",
                hazeState = hazeState,
                height = 100.dp,
                onClick = {
                    bool = !bool
//                        urlLauncher.openUri(sourceCode)
                },
                onLongClick = {
//                        bool = !bool
                },
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .navigationBarsPadding()
            )
        }


    }
}

@Composable
fun SocialMedia(modifier: Modifier = Modifier) {
//    val phyMod = Modifier.physicsBody(bodyConfig = BodyConfig(isStatic = true))
    val urlLauncher = LocalUriHandler.current
    val mastadon = "https://androiddev.social/@mad_flasher"
    val github = "https://github.com/MadFlasheroo7"
    val twitter = "https://x.com/Madflasheroo7"
    val linkedin = "https://www.linkedin.com/in/jayesh-seth/"
    val medium = "https://medium.com/@jayeshseth"
    val realogs = "https://blog.realogs.in/author/jayesh/"
    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                "Let's Connect",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
//                modifier = phyMod
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { urlLauncher.openUri(github) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(AnimIcons.github),
                        contentDescription = "github",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(12.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { urlLauncher.openUri(twitter) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(AnimIcons.twitter),
                        contentDescription = "twitter",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(12.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { urlLauncher.openUri(linkedin) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(AnimIcons.linkedIn),
                        contentDescription = "linkedin",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(12.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { urlLauncher.openUri(mastadon) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(AnimIcons.mastadon),
                        contentDescription = "mastadon",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(12.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { urlLauncher.openUri(realogs) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(AnimIcons.blog),
                        contentDescription = "realogs",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(12.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { urlLauncher.openUri(medium) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(AnimIcons.medium),
                        contentDescription = "medium",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GravitySensor(
    onGravityChanged: (List<Float>) -> Unit
) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService<SensorManager>()!!

        val gravitySensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        val gravityListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val (x, y, z) = event.values
                onGravityChanged(listOf(x, y, z))
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }
        }

        sensorManager.registerListener(
            gravityListener,
            gravitySensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sensorManager.unregisterListener(gravityListener)
        }
    }
}


@Composable
fun OptimizedFlowRow(about: String) {
    val characters = remember { about.toList() }
    var selectedIndex by remember { mutableStateOf(-1) }

    LazyRow {
        itemsIndexed(characters) { index, char ->
            key(char) {
                CharacterItem(
                    char = char,
                    isSelected = index == selectedIndex,
                    onClick = { selectedIndex = if (selectedIndex == index) -1 else index }
                )
            }
        }

    }
}

@Composable
fun CharacterItem(
    char: Char,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val modifier = remember(isSelected) {
        derivedStateOf {
            Modifier
                .clickable(onClick = onClick)
                .then(if (isSelected) Modifier.physicsBody() else Modifier)
        }
    }

    Text(
        text = char.toString(),
        modifier = modifier.value
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextReveal(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    initialDelay: Long = 100L,
    letterDelay: Long = 50L,
    animationSpec: FiniteAnimationSpec<Float> = tween(
        durationMillis = 250,
        easing = FastOutSlowInEasing,
    ),
) {
    val zeroSpacingTextStyle = textStyle.copy(letterSpacing = 0.sp)
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = calcSpaceWidth(zeroSpacingTextStyle),
//            alignment = Alignment.CenterHorizontally,
        ),
        verticalArrangement = Arrangement.Center,
    ) {
        val splitText = text.split(" ")
        val wordStartIndices = remember(text) {
            splitText.runningFold(0) { acc, word -> acc + word.length + 1 }.dropLast(1)
        }
        splitText.fastForEachIndexed { index, word ->
            val wordStartIndex = wordStartIndices[index]
            WordReveal(
                word = word,
                initialDelay = initialDelay + wordStartIndex * letterDelay,
                letterDelay = letterDelay,
                textStyle = zeroSpacingTextStyle,
                animationSpec = animationSpec,
            )
        }
    }
}

@Composable
private fun calcSpaceWidth(textStyle: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(textMeasurer) { textMeasurer.measure(" ", textStyle) }
    return with(LocalDensity.current) { textLayoutResult.size.width.toDp() }
}

private enum class AnimationState {
    Idle, Animating, Revealed
}

@Composable
private fun WordReveal(
    word: String,
    initialDelay: Long,
    letterDelay: Long,
    textStyle: TextStyle,
    animationSpec: FiniteAnimationSpec<Float>,
    modifier: Modifier = Modifier,
) {
    var animationState by remember { mutableStateOf(AnimationState.Idle) }

    LaunchedEffect(key1 = word) {
        delay(timeMillis = initialDelay)
        animationState = AnimationState.Animating
    }

    if (animationState == AnimationState.Animating) {
        Row(
            modifier = modifier,
//            verticalAlignment = Alignment.Bottom,
        ) {
            word.forEachIndexed { index, char ->
                var isVisible by remember { mutableStateOf(false) }
                val transition =
                    updateTransition(targetState = isVisible, label = "visibility transition")

                LaunchedEffect(index, transition.currentState, transition.targetState) {
                    if (index == word.indices.last && transition.currentState == transition.targetState && isVisible) {
                        animationState = AnimationState.Revealed
                    }
                }

                LaunchedEffect(key1 = index) {
                    delay(timeMillis = index * letterDelay)
                    isVisible = true
                }

                LetterRevealScaled(
                    letter = char.toString(),
                    transition = transition,
                    animationSpec = animationSpec,
                    textStyle = textStyle,
                )
            }
        }
    } else {
        val alpha = if (animationState == AnimationState.Revealed) 1f else 0f
        Text(
            text = word,
            style = textStyle,
            modifier = Modifier.alpha(alpha),
        )
    }
}

@Composable
private fun LetterRevealScaled(
    letter: String,
    transition: Transition<Boolean>,
    animationSpec: FiniteAnimationSpec<Float>,
    textStyle: TextStyle,
) {
    val alpha by transition.animateFloat(
        label = "alpha animation",
        transitionSpec = { animationSpec },
    ) { visible ->
        if (visible) 1f else 0f
    }

    val scale by transition.animateFloat(
        label = "scale animation",
        transitionSpec = { animationSpec },
    ) { visible ->
        val initialScale = .4f
        if (visible) 1f else initialScale
    }

    Text(
        text = letter,
        modifier = Modifier.graphicsLayer {
            this.alpha = alpha.coerceIn(0f, 1f)
            this.scaleX = scale
            this.scaleY = scale
        },
        style = textStyle,
    )
}