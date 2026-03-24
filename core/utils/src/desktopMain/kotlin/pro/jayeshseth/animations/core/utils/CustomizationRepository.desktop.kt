package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import pro.jayeshseth.animations.core.model.CustomizationRepository
import java.io.File

@Composable
actual fun rememberCustomizationRepository(): CustomizationRepository {
    return remember {
        val dir = File(System.getProperty("user.home"), ".compose-animations")
        dir.mkdirs()
        DataStoreCustomizationRepository(
            getOrCreateDataStore {
                File(dir, "customization.preferences_pb").absolutePath
            }
        )
    }
}
