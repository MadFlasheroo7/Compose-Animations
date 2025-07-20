package pro.jayeshseth.animations.ui.easterEggs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.apuri.physicslayout.lib.BodyConfig
import de.apuri.physicslayout.lib.PhysicsLayout
import de.apuri.physicslayout.lib.physicsBody
import de.apuri.physicslayout.lib.simulation.rememberSimulation
import pro.jayeshseth.animations.R
import pro.jayeshseth.animations.ui.screens.GravitySensor
import pro.jayeshseth.animations.ui.screens.SocialMedia
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.SystemBarAwareThemedColumn

/**
 * About Screen Easter Egg
 * When long pressed the source code button physics was applied with the help of [PhysicsLayout],
 * to each word of the string(character was too heavy handle).
 *
 * [PhysicsLayout](https://github.com/KlassenKonstantin/ComposePhysicsLayout/tree/main) is built by [KlassenKonstantin](https://github.com/KlassenKonstantin)
 * > **NOTE** - THIS WILL CRASH ON LOW END HARDWARE
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun PhysicsLayoutAboutScreen(modifier: Modifier = Modifier) {
    val simulation = rememberSimulation()
    val About = stringResource(R.string.about)
    var bool by rememberSaveable { mutableStateOf(false) }
    val aboutList by remember { mutableStateOf(About.split(" ")) }
    val urlLauncher = LocalUriHandler.current
    val mmodifier = remember(bool) {
        derivedStateOf {
            Modifier
                .then(
                    if (bool) Modifier
                        .physicsBody() else Modifier
                )
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
                        "DO NOT PRESS BUTTON\n(Your device might not be able to handle it 🫣)" else "\uD83D\uDE44",
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
                        text = "Animate",
                        onClick = { bool = !bool },
                        modifier = Modifier
                            .weight(1f)
                            .physicsBody(bodyConfig = BodyConfig(isStatic = true))
                    )
                }
            }
        }
    }
}
