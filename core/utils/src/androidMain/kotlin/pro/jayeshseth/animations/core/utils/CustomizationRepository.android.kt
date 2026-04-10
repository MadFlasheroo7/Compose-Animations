package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import pro.jayeshseth.animations.core.model.CustomizationRepository

@Composable
actual fun rememberCustomizationRepository(): CustomizationRepository {
    val context = LocalContext.current
    return remember {
        DataStoreCustomizationRepository(
            getOrCreateDataStore {
                context.filesDir.resolve("customization.preferences_pb").absolutePath
            }
        )
    }
}
