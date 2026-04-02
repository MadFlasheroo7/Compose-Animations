package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.jayeshseth.animations.core.model.EasterEggRepository

private class LocalStorageEasterEggRepository : EasterEggRepository {

    private val _hasEasterEggTriggered = MutableStateFlow(loadFromStorage())
    override val hasEasterEggEgged: Flow<Boolean> = _hasEasterEggTriggered

    private fun loadFromStorage(): Boolean {
        return localStorage.getItem(CAN_PLAY_SHADER)?.toBooleanStrictOrNull() ?: false
    }

    override suspend fun markTriggered() {
        localStorage.setItem(CAN_PLAY_SHADER, true.toString())
        _hasEasterEggTriggered.value = true
    }
}

private var easterEggInstance: LocalStorageEasterEggRepository? = null

@Composable
actual fun rememberEasterEggRepository(): EasterEggRepository {
    return remember {
        easterEggInstance ?: LocalStorageEasterEggRepository().also { easterEggInstance = it }
    }
}
