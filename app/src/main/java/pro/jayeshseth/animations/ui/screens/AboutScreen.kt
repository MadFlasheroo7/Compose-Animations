package pro.jayeshseth.animations.ui.screens

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import de.apuri.physicslayout.lib.BodyConfig
import de.apuri.physicslayout.lib.PhysicsLayout
import de.apuri.physicslayout.lib.physicsBody
import de.apuri.physicslayout.lib.simulation.rememberSimulation
import pro.jayeshseth.animations.R
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.SystemBarAwareThemedColumn

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val simulation = rememberSimulation()
    val About = stringResource(R.string.about)
    var bool by rememberSaveable { mutableStateOf(false) }
    val aboutList by remember { mutableStateOf(About.split(" ")) }
    val urlLauncher = LocalUriHandler.current
    val sourceCode = "https://github.com/MadFlasheroo7/Compose-Animations"
    val mmodifier = remember(bool) {
        derivedStateOf {
            Modifier
                .then(if (bool) Modifier.physicsBody() else Modifier)
        }
    }
    GravitySensor { (x, y) ->
        simulation.setGravity(Offset(-x, y).times(3f))
    }
    SystemBarAwareThemedColumn(
        systemBarsColors = Color.Transparent,
    ) {
        PhysicsLayout(
            simulation = simulation,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = "\uD83D\uDC96",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = mmodifier.value
                )
                Text(
                    text = " Animations ",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.physicsBody(bodyConfig = BodyConfig(isStatic = true))
                )
                Text(
                    text = "\uD83D\uDC96",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = mmodifier.value
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 100.dp)
            ) {
                Spacer(Modifier.statusBarsPadding())
                FlowRow(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    aboutList.map {
                        Text(
                            text = "$it ",
                            modifier = mmodifier.value,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            SocialMedia(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 200.dp)
                    .navigationBarsPadding()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 30.dp)
                    .navigationBarsPadding()
                    .animateContentSize()
            ) {
                Text(
                    text = if (!bool)
                        "DO NOT LONG PRESS BUTTON\n(Your device might not be able to handle it 🫣)" else "\uD83D\uDE44",
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.animateContentSize()
                ) {
                    AnimatedVisibility(
                        visible = bool,
                        enter = slideInHorizontally(),
                        exit = slideOutHorizontally(),
                        modifier = Modifier.weight(1.1f)
                    ) {
                        InteractiveButton(
                            text = "Physics Layout 💖",
                            textModifier = Modifier.align(Alignment.CenterVertically),
                            onClick = { urlLauncher.openUri("https://github.com/KlassenKonstantin/ComposePhysicsLayout/tree/main") },
                        )
                    }
                    InteractiveButton(
                        text = "Source Code",
                        onClick = { urlLauncher.openUri(sourceCode) },
                        onLongClick = { bool = !bool },
                        modifier = Modifier
                            .weight(1f)
                            .physicsBody(bodyConfig = BodyConfig(isStatic = true))
                    )
                }
            }
        }
    }
}

@Composable
fun SocialMedia(modifier: Modifier = Modifier) {
    val phyMod = Modifier.physicsBody(bodyConfig = BodyConfig(isStatic = true))
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
                modifier = phyMod
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
                        painter = painterResource(R.drawable.ic_github),
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
                        painter = painterResource(R.drawable.ic_twitter),
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
                        painter = painterResource(R.drawable.ic_linkedin),
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
                        painter = painterResource(R.drawable.ic_mastodon),
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
                        painter = painterResource(R.drawable.ic_blogger),
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
                        painter = painterResource(R.drawable.ic_medium),
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