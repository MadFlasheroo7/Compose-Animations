package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import animations.composeapp.generated.resources.Res
import animations.composeapp.generated.resources.about
import com.mikepenz.hypnoticcanvas.RuntimeEffect
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.Shader
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.EasterEggDetector
import pro.jayeshseth.animations.core.ui.components.HazedIconButton
import pro.jayeshseth.animations.core.ui.components.HeadingText
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.SocialMedia
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalEasterEggRepository
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.rememberEasterEggPermissionState
import pro.jayeshseth.glowingButton.glowingShadow

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun AboutScreen(hazeState: HazeState, modifier: Modifier = Modifier) {
//    val view = LocalView.current
//    val context = LocalContext.current
//    val activity = LocalActivity.current
    val urlLauncher = LocalUriHandler.current
    val easterEggRepo = LocalEasterEggRepository.current
    val canPlayShader by easterEggRepo?.hasEasterEggEgged?.collectAsState(initial = false)
        ?: remember { mutableStateOf(false) }
    val permissionState = rememberEasterEggPermissionState()
//    val window = activity?.window
//    val prefs = context.commonPrefs
//    val fallbackWindow = (context as Activity).window
//    val controller = WindowInsetsControllerCompat(
//        window ?: fallbackWindow,
//        view
//    )
    var clapCount by remember { mutableStateOf(0) }
    var hasEasterEggTriggered by remember { mutableStateOf(false) }
    var playShader by rememberSaveable { mutableStateOf(false) }
    var showMsg by rememberSaveable { mutableStateOf(false) }
    var isDetecting by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (playShader) Color.Black else Color.Transparent,
        animationSpec = tween(2000),
        label = ""
    )
    val scope = rememberCoroutineScope()
    val val1 = remember { Animatable(0f) }
    val val2 = remember { Animatable(.000000000000f) }
    val val3 = remember { Animatable(0.1f) }
    val About = stringResource(Res.string.about)
    val sourceCode = "https://github.com/MadFlasheroo7/Compose-Animations"
    val performanceReport = "https://animations.jayeshseth.pro/report/"

//    val isPermissionPermanentlyDenied =
//        prefs.collectPrefAsState(PermissionPrefs.RECORD_AUDIO_DENIED, false)
//    val dontShowEverAgain =
//        prefs.collectPrefAsState(PermissionPrefs.PERMISSION_RATIONAL, false)
//    val canPlayShader =
//        prefs.collectPrefAsState(CAN_PLAY_SHADER, false)

//    val permissionState = rememberPermissionState(
//        android.Manifest.permission.RECORD_AUDIO
//    ) {
//        if (it) {
//            showPermissionDialog = false
//            isDetecting = true
//        } else if (isAtleastDeclinedOnce(context, android.Manifest.permission.RECORD_AUDIO)) {
//        } else {
//            scope.launch {
//                prefs.writePref(PermissionPrefs.RECORD_AUDIO_DENIED, true)
//            }
//        }
//    }

    LaunchedEffect(Unit) {
        delay(2000)
//        if (!permissionState.status.isGranted && !dontShowEverAgain.value) {
//            showPermissionDialog = true
//        } else {
//            isDetecting = true
//        }
        if (!canPlayShader) {
            if (!permissionState.isPermissionGranted && !permissionState.dontShowEverAgain) {
                showPermissionDialog = true
            } else {
                isDetecting = true
            }
        }
    }

    LaunchedEffect(permissionState.isPermissionGranted) {
        if (permissionState.isPermissionGranted) {
            showPermissionDialog = false
            isDetecting = true
        }
    }

    LaunchedEffect(hasEasterEggTriggered) {
        if (hasEasterEggTriggered) {
            playShader = true
        }
    }

//    if (!view.isInEditMode && bool) {
//        DisposableEffect(bool) {
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//
//            onDispose {
//                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
//                controller.show(WindowInsetsCompat.Type.systemBars())
//            }
//        }
//    }


    print("paly shader ${canPlayShader} ")
    if (!canPlayShader) {
        EasterEggDetector(
            enabled = isDetecting,
            easterEggRepo = easterEggRepo,
            onEasterEggTriggered = {
                clapCount++
                hasEasterEggTriggered = true
            }
        )
    }

    LaunchedEffect(playShader) {
        if (playShader) {
            delay(3500)
            launch {
                val1.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(5000)
                )
            }
            launch {
                val2.animateTo(
                    targetValue = 0.0100f,
                    animationSpec = tween(5000)
                )
            }
            launch {
                val3.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(10_000)
                )
                val2.animateTo(
                    targetValue = .30f,
                    animationSpec = tween(5000)
                )
                showMsg = true
            }
        } else {
            val1.snapTo(0f)
            val2.snapTo(
                targetValue = .000000000000f
            )
            val3.snapTo(
                targetValue = 0.1f
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(.2f))
            .background(backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(top = 25.dp, start = 12.dp, end = 12.dp)
        ) {
//            Box(modifier = Modifier.size(44.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                AnimatedVisibility(
                    visible = !playShader,
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
                        fontWeight = FontWeight.Bold
                    )
                }

                AnimatedVisibility(
                    visible = !playShader,
                    enter = fadeIn(
                        animationSpec = tween(1000)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(1000, delayMillis = 1000)
                    )
                ) {
                    HeadingText(
                        text = " Animations ",
                    )
                }

                AnimatedVisibility(
                    visible = !playShader,
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
                visible = !playShader,
                enter = fadeIn(animationSpec = tween(2000)),
                exit = fadeOut(animationSpec = tween(2000))
            ) {
                HazedIconButton(
                    onClick = { urlLauncher.openUri(performanceReport) },
                    hazeState = hazeState,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Performance report"
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedVisibility(
                visible = !playShader,
                enter = fadeIn(
                    animationSpec = tween(2000)
                ),
                exit = fadeOut(
                    animationSpec = tween(2000)
                ),
            ) {
                Text(
                    text = About,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = syneFontFamily()
                    )
                )
            }
        }

        AnimatedVisibility(
            visible = !playShader,
            enter = fadeIn(
                animationSpec = tween(2000)
            ),
            exit = fadeOut(
                animationSpec = tween(2000)
            )
        ) {
            SocialMedia(hazeState)
        }

        AnimatedVisibility(
            visible = !playShader,
            enter = fadeIn(),
            exit = fadeOut(
                animationSpec = tween(2000)
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                Text(
//                    text = "www",
//                    text = if (permissionState.status.isGranted) "Few Claps 👏 are appreciated ☺️"
//                    else
//                        "Needs Permission :(\nTap to grant permission",
                    text = if (permissionState.isPermissionGranted) "Few Claps \uD83D\uDC4F are appreciated \u263A\uFE0F"
                    else
                        "Needs Permission :(\nTap to grant permission",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.combinedClickable(
                        indication = null,
                        interactionSource = null,
                        onClick = {
//                            showPermissionDialog = !showPermissionDialog
//                            if (!permissionState.status.isGranted) showPermissionDialog = true
                            if (!permissionState.isPermissionGranted) showPermissionDialog = true
                        },
                        onLongClick = {
//                            if (permissionState.status.isGranted) showPermissionDialog = true
                            if (permissionState.isPermissionGranted) showPermissionDialog = true
                        }
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 12.dp)
                        .navigationBarsPadding()
                ) {
                    AnimatedVisibility(
//                        visible = canPlayShader.value,
                        visible = canPlayShader,
                        enter = slideInHorizontally(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow,
                            ),
                        ) {
                            -it
                        },
                        exit = slideOutHorizontally(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow,
                            ),
                        ) { -it },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        InteractiveButton(
                            text = "Play Shader",
                            hazeState = hazeState,
                            height = 80.dp,
                            onClick = { playShader = true },
                        )
                    }
                    InteractiveButton(
                        text = "Source Code",
                        hazeState = hazeState,
                        height = 80.dp,
                        onClick = { urlLauncher.openUri(sourceCode) },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }

    val shader = remember { AboutShader() }
    shader.val1 = val1.value
    shader.val2 = val2.value
    shader.val3 = val3.value

    //TODO Handle fallback
    AnimatedVisibility(
        visible = playShader,
        enter = fadeIn(
            animationSpec = tween(2000, delayMillis = 3000)
        ) + scaleIn(
            animationSpec = tween(800)
        ),
        exit = fadeOut(snap())
    ) {
        Box(Modifier.fillMaxSize().shaderBackground(shader))
    }

    AnimatedVisibility(
        visible = showMsg,
        enter = fadeIn(snap()),
        exit = fadeOut(tween(1000))
    ) {
        TextAnimation(
            text = "Thank You for using the app and finding out the easter egg. \n Hope you liked it and as you were able to complete easter egg, i've unlocked master customization. GO WILD!!✌",
            textStyle = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = syneFontFamily(),
                color = Color.Black
            )
        ) {
            scope.launch {
                delay(2000)
                showMsg = false
                delay(1000)
                val2.animateTo(
                    targetValue = 0f,
                    animationSpec = keyframes {
                        // todo make it more smooth
                        durationMillis = 8000
                        0.001189427f at 3000 using LinearOutSlowInEasing
                    }
                )
                playShader = false
                scope.launch {
//                    prefs.writePref(CAN_PLAY_SHADER, true)
                    easterEggRepo?.markTriggered()
                }
            }
        }
    }

    RationalDialog(
        hazeState = hazeState,
        showPermissionDialog = showPermissionDialog,
//        isPermissionPermanentlyDenied = isPermissionPermanentlyDenied.value,
        isPermissionPermanentlyDenied = permissionState.isPermanentlyDenied,
        onOki = {
//            permissionState.launchPermissionRequest()
//            if (isPermissionPermanentlyDenied.value) {
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                val uri = Uri.fromParts("package", context.packageName, null)
//                intent.data = uri
//                context.startActivity(intent)
//            }
            permissionState.requestPermission()
        },
        onNaa = { showPermissionDialog = false },
        onNeverAgain = {
            showPermissionDialog = false
//                prefs.writePref(PermissionPrefs.PERMISSION_RATIONAL, true)
            permissionState.markDontShowEverAgain()
        }
    )
}

@Composable
fun RationalDialog(
    hazeState: HazeState,
    showPermissionDialog: Boolean,
    isPermissionPermanentlyDenied: Boolean,
    onOki: () -> Unit,
    onNaa: () -> Unit,
    onNeverAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    val cardStyle2 = HazeStyle(
        tint = HazeTint(Color.Cyan.copy(.2f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    val infiniteTransition = rememberInfiniteTransition(label = "dialog pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 30f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = showPermissionDialog,
            enter = scaleIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            ),
            exit = scaleOut(
                animationSpec = tween()
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
                    .padding(horizontal = 30.dp)
//                    .fillMaxWidth(0.75f)      // Takes 85% of screen width
//                    .widthIn(max = 500.dp)
                    .glowingShadow {
                        color = Color.Cyan
                        spread = 18f
                        blurRadius = pulseAlpha
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .hazeEffect(
                        hazeState,
                        cardStyle
                    )
                    .padding(16.dp)
            ) {
                Text(
                    "Permission Required",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    "Microphone access is required For the easter egg.\n !!You can turn off once easter egg is found",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InteractiveButton(
                        text = "Naa am good",
                        hazeState = hazeState,
                        hazeStyle = cardStyle2,
                        onClick = onNaa,
                        modifier = Modifier.weight(1f)
                    )
                    InteractiveButton(
                        text = "Oki",
                        hazeState = hazeState,
                        onClick = onOki,
                        modifier = Modifier.weight(1f)
                    )
                }
                AnimatedVisibility(
                    visible = isPermissionPermanentlyDenied,
                    enter = slideInVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow,
                        )
                    ) {
                        -it / 3
                    },
                    exit = scaleOut(
                        animationSpec = tween()
                    )
                ) {
                    InteractiveButton(
                        text = "Dont Show Again EVER :(",
                        hazeState = hazeState,
                        onClick = onNeverAgain,
                    )
                }
            }
        }
    }
}

private const val TAG = "AboutScreen"

@Preview
@Composable
private fun PreviewAboutScreen() {
    AnimationsTheme {
        ShaderPreviewContent { hazeState ->
            AboutScreen(hazeState)
        }
    }
}

//private fun isAtleastDeclinedOnce(context: Context, permission: String): Boolean {
//    return shouldShowRequestPermissionRationale(context as Activity, permission)
//}

@Composable
private fun TextAnimation(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    charDelay: Long = 50,
    animDuration: Int = 1000,
    onFinished: () -> Unit = {}
) {
    val charAnimations = remember(text) {
        List(text.length) { Animatable(0f) }
    }

    LaunchedEffect(text) {
        charAnimations.forEach { it.snapTo(0f) }
        charAnimations.forEachIndexed { index, animatable ->
            launch {
                delay(index * charDelay)
                animatable.animateTo(1f, tween(animDuration, easing = FastOutLinearInEasing))
                if (index == charAnimations.lastIndex) {
                    onFinished()
                }
            }
        }
    }

    FlowRow(
        itemVerticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        text.split(" ").forEachIndexed { wordIndex, word ->
            Row {
                word.forEachIndexed { charIndexInWord, char ->
                    val originalIndex =
                        text.split(" ").take(wordIndex).sumOf { it.length + 1 } + charIndexInWord
                    val progress = charAnimations[originalIndex].value
                    Text(
                        text = char.toString(),
                        style = textStyle,
                        modifier = Modifier.graphicsLayer {
                            alpha = progress
                            scaleX = progress
                            scaleY = progress
                        }
                    )
                }
            }
            Text(text = " ", style = textStyle)
        }
    }
}


private class AboutShader : Shader {
    override val name = "AboutShader"
    override val authorName = "Compose-Animations"
    override val authorUrl = ""
    override val credit = ""
    override val license = ""
    override val licenseUrl = ""
    override val speedModifier = 0.166f

    var val1: Float = 0f
    var val2: Float = 0f
    var val3: Float = 0.1f

    override val sksl = """
        uniform float2 iResolution;
        uniform float iTime;
        uniform float val1, val2, val3;

        half4 main(float2 fragCoord) {
            float3 c = float3(0.0);
            float l = 0.0;
            float z = iTime;

            for (int i = 0; i < 3; i++) {
                float2 uv = fragCoord.xy / iResolution;
                float2 p = uv;
                p -= 0.5;
                p.x *= iResolution.x / iResolution.y;
                z += 0.0;
                l = length(p);
                uv += p / l * (sin(z) + val1) * abs(sin(l * 9.0 - z - z));
                c[i] = val2 / length(mod(uv, float2(val3)) - 0.5);
            }

            return half4(half3(c / l), 1.0);
        }
    """.trimIndent()

    override fun applyUniforms(
        runtimeEffect: RuntimeEffect,
        time: Float,
        width: Float,
        height: Float
    ) {
        runtimeEffect.setFloatUniform("iResolution", width, height)
        runtimeEffect.setFloatUniform("iTime", time)
        runtimeEffect.setFloatUniform("val1", val1)
        runtimeEffect.setFloatUniform("val2", val2)
        runtimeEffect.setFloatUniform("val3", val3)
    }
}