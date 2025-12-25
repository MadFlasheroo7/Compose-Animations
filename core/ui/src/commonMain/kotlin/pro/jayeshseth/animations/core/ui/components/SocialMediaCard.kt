package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import org.jetbrains.compose.resources.painterResource
import pro.jayeshseth.animations.core.ui.icons.AnimIcons

@Composable
fun SocialMedia(hazeState: HazeState, modifier: Modifier = Modifier) {
    val urlLauncher = LocalUriHandler.current
    val mastadon = "https://androiddev.social/@mad_flasher"
    val github = "https://github.com/MadFlasheroo7"
    val twitter = "https://x.com/Madflasheroo7"
    val linkedin = "https://www.linkedin.com/in/jayesh-seth/"
    val medium = "https://medium.com/@jayeshseth"
    val realogs = "https://blog.realogs.in/author/jayesh/"
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .hazeEffect(
                hazeState,
                cardStyle
            )
            .padding(12.dp)
    ) {
        Text(
            "Let's Connect",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
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
                    tint = Color.White,
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
                    tint = Color.White,
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
                    tint = Color.White,
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
                    tint = Color.White,
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
                    tint = Color.White,
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
                    tint = Color.White,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(12.dp)
                )
            }
        }
    }
}
