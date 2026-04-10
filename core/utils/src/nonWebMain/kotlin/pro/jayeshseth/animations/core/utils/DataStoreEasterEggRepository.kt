package pro.jayeshseth.animations.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import pro.jayeshseth.animations.core.model.EasterEggRepository

internal class DataStoreEasterEggRepository(
    private val dataStore: DataStore<Preferences>
) : EasterEggRepository {

    private companion object Keys {
        val CAN_PLAY_SHADER_KEY = booleanPreferencesKey(CAN_PLAY_SHADER)
    }

    override val hasEasterEggEgged: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[CAN_PLAY_SHADER_KEY] ?: false
    }

    override suspend fun markTriggered() {
        dataStore.edit { it[CAN_PLAY_SHADER_KEY] = true }
    }
}

private var easterEggDataStoreInstance: DataStore<Preferences>? = null

internal fun getOrCreateEasterEggDataStore(producePath: () -> String): DataStore<Preferences> {
    return easterEggDataStoreInstance ?: run {
        androidx.datastore.preferences.core.PreferenceDataStoreFactory
            .createWithPath(
                produceFile = { producePath().toPath() }
            ).also { easterEggDataStoreInstance = it }
    }
}
