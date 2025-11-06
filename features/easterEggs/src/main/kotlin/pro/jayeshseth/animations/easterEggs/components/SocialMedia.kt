package pro.jayeshseth.animations.easterEggs.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.apuri.physicslayout.lib.BodyConfig
import de.apuri.physicslayout.lib.physicsBody
import pro.jayeshseth.animations.core.ui.icons.AnimIcons

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
