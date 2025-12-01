package pro.jayeshseth.animations.core.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.commonPrefs: DataStore<Preferences> by preferencesDataStore("commonPrefs")

@Suppress("UNCHECKED_CAST")
inline fun <reified T> prefKey(name: String): Preferences.Key<T> =
    when (T::class) {
        String::class -> stringPreferencesKey(name)
        Int::class -> intPreferencesKey(name)
        Long::class -> longPreferencesKey(name)
        Float::class -> floatPreferencesKey(name)
        Double::class -> doublePreferencesKey(name)
        Boolean::class -> booleanPreferencesKey(name)
        Set::class -> stringSetPreferencesKey(name)
        else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
    } as Preferences.Key<T>

inline fun <reified T> DataStore<Preferences>.readPref(
    name: String,
    default: T
): Flow<T> {
    val key = prefKey<T>(name)
    return data.map { prefs -> prefs[key] ?: default }
}

@Composable
inline fun <reified T> DataStore<Preferences>.collectPrefAsState(
    name: String,
    default: T
): State<T> {
    val flow = remember(name, default) { readPref(name, default) }
    return flow.collectAsStateWithLifecycle(initialValue = default)
}


suspend inline fun <reified T> DataStore<Preferences>.writePref(
    name: String,
    value: T
) {
    val key = prefKey<T>(name)
    edit { prefs ->
        prefs[key] = value
    }
}
