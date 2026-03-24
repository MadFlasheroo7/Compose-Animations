package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import pro.jayeshseth.animations.core.model.CustomizationRepository

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCustomizationRepository(): CustomizationRepository {
    return remember {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )!!.path!!
        DataStoreCustomizationRepository(
            getOrCreateDataStore {
                "$directory/customization.preferences_pb"
            }
        )
    }
}
