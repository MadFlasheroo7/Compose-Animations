package pro.jayeshseth.animations.core.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Link
import animations.core.ui.generated.resources.Res
import animations.core.ui.generated.resources.cat
import animations.core.ui.generated.resources.code
import animations.core.ui.generated.resources.faders
import animations.core.ui.generated.resources.fan
import animations.core.ui.generated.resources.ic_blogger
import animations.core.ui.generated.resources.ic_github
import animations.core.ui.generated.resources.ic_linkedin
import animations.core.ui.generated.resources.ic_mastodon
import animations.core.ui.generated.resources.ic_medium
import animations.core.ui.generated.resources.ic_twitter
import animations.core.ui.generated.resources.link
import animations.core.ui.generated.resources.minus

/**
 * A singleton object that provides easy access to all the icons used throughout the Animations app.
 * This object centralizes icon references, making it easier to manage and reuse them.
 *
 * It includes both custom drawable resources from `Res.drawable` and standard Material icons
 * from `androidx.compose.material.icons`.
 */
object AnimIcons {
    val fan = Res.drawable.fan
    val cat = Res.drawable.cat
    val settings = Res.drawable.faders
    val code = Res.drawable.code
    val source = Res.drawable.link
    val blog = Res.drawable.ic_blogger
    val github = Res.drawable.ic_github
    val linkedIn = Res.drawable.ic_linkedin
    val mastadon = Res.drawable.ic_mastodon
    val medium = Res.drawable.ic_medium
    val twitter = Res.drawable.ic_twitter
    val link = Icons.Rounded.Link
    val add = Icons.Rounded.Add
    val minus = Res.drawable.minus
}