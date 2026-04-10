package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import pro.jayeshseth.animations.core.model.EasterEggRepository
import java.io.File

@Composable
actual fun rememberEasterEggRepository(): EasterEggRepository {
    return remember {
        val dir = File(System.getProperty("user.home"), ".compose-animations")
        dir.mkdirs()
        DataStoreEasterEggRepository(
            getOrCreateEasterEggDataStore {
                File(dir, "easteregg.preferences_pb").absolutePath
            }
        )
    }
}