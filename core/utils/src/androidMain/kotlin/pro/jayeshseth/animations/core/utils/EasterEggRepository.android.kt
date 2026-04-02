package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import pro.jayeshseth.animations.core.model.EasterEggRepository

@Composable
actual fun rememberEasterEggRepository(): EasterEggRepository {
    val context = LocalContext.current
    return remember {
        DataStoreEasterEggRepository(
            getOrCreateEasterEggDataStore {
                context.filesDir.resolve("easteregg.preferences_pb").absolutePath
            }
        )
    }
}