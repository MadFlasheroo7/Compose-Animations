package pro.jayeshseth.animations.core.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.model.CustomizationRepository
import pro.jayeshseth.animations.core.model.CustomizationState

internal class DataStoreCustomizationRepository(
    private val dataStore: DataStore<Preferences>
) : CustomizationRepository {

    private companion object Keys {
        val BACKGROUND_BLUR = floatPreferencesKey(DefaultPrefKeys.BACKGROUND_BLUR_KEY)
        val PRIMARY_COLOR = intPreferencesKey(DefaultPrefKeys.PRIMARY_COLOR_KEY)
        val HEADING_COLOR = intPreferencesKey(DefaultPrefKeys.HEADING_COLOR_KEY)
        val ACCENT_COLOR = intPreferencesKey(DefaultPrefKeys.ACCENT_COLOR_KEY)
        val BUTTON_ACCENT_COLOR = intPreferencesKey(DefaultPrefKeys.BUTTON_ACCENT_COLOR_KEY)
        val PRIMARY_BUTTON_ACCENT_COLOR =
            intPreferencesKey(DefaultPrefKeys.PRIMARY_BUTTON_ACCENT_COLOR_KEY)
        val SHADER_KEY = stringPreferencesKey(DefaultPrefKeys.SHADER_KEY)
        val SHADER_SPEED = floatPreferencesKey(DefaultPrefKeys.SHADER_SPEED_KEY)
        val BACKGROUND_TYPE = stringPreferencesKey(DefaultPrefKeys.BACKGROUND_TYPE_KEY)
        val CANVAS_KEY = stringPreferencesKey(DefaultPrefKeys.CANVAS_KEY)
        val CANVAS_COLOR = intPreferencesKey(DefaultPrefKeys.CANVAS_COLOR_KEY)
        val IMAGE_KEY = stringPreferencesKey(DefaultPrefKeys.IMAGE_KEY)
    }

    override val state: Flow<CustomizationState> = dataStore.data.map { prefs ->
        val defaults = CustomizationState()
        val backgroundType = when (prefs[BACKGROUND_TYPE]) {
            BackgroundType.TYPE_CANVAS -> BackgroundType.Canvas.fromKey(
                key = prefs[CANVAS_KEY] ?: DefaultPrefKeys.DOT_GRID_KEY,
                colorArgb = prefs[CANVAS_COLOR] ?: defaults.buttonAccentColorArgb
            )

            BackgroundType.TYPE_IMAGE -> BackgroundType.Image.fromKey(
                key = prefs[IMAGE_KEY] ?: DefaultPrefKeys.CAT_IMAGE_KEY
            )

            else -> {
                val speed = prefs[SHADER_SPEED] ?: 0.2f
                val shaderKey = prefs[SHADER_KEY]
                BackgroundType.Shader.fromKey(shaderKey ?: DefaultPrefKeys.INK_FLOW_KEY, speed)
            }
        }
        CustomizationState(
            backgroundBlur = prefs[BACKGROUND_BLUR]?.dp ?: defaults.backgroundBlur,
            primaryColorArgb = prefs[PRIMARY_COLOR] ?: defaults.primaryColorArgb,
            headingColorArgb = prefs[HEADING_COLOR] ?: defaults.headingColorArgb,
            accentColorArgb = prefs[ACCENT_COLOR] ?: defaults.accentColorArgb,
            buttonAccentColorArgb = prefs[BUTTON_ACCENT_COLOR] ?: defaults.buttonAccentColorArgb,
            primaryButtonAccentColorArgb = prefs[PRIMARY_BUTTON_ACCENT_COLOR]
                ?: defaults.primaryButtonAccentColorArgb,
            backgroundType = backgroundType,
        )
    }

    override suspend fun updateBackgroundBlur(blur: Dp) {
        dataStore.edit { it[BACKGROUND_BLUR] = blur.value }
    }

    override suspend fun updatePrimaryColor(argb: Int) {
        dataStore.edit { it[PRIMARY_COLOR] = argb }
    }

    override suspend fun updateAccentColor(argb: Int) {
        dataStore.edit { it[ACCENT_COLOR] = argb }
    }

    override suspend fun updateHeadingColor(argb: Int) {
        dataStore.edit { it[HEADING_COLOR] = argb }
    }

    override suspend fun updateButtonAccentColor(argb: Int) {
        dataStore.edit { it[BUTTON_ACCENT_COLOR] = argb }
    }

    override suspend fun updatePrimaryButtonAccentColor(argb: Int) {
        dataStore.edit { it[PRIMARY_BUTTON_ACCENT_COLOR] = argb }
    }

    override suspend fun updateBackground(backgroundType: BackgroundType) {
        dataStore.edit { prefs ->
            when (backgroundType) {
                is BackgroundType.Shader -> {
                    prefs[BACKGROUND_TYPE] = BackgroundType.TYPE_SHADER
                    prefs[SHADER_KEY] = backgroundType.key
                    prefs[SHADER_SPEED] = backgroundType.speed
                }

                is BackgroundType.Canvas -> {
                    prefs[BACKGROUND_TYPE] = BackgroundType.TYPE_CANVAS
                    prefs[CANVAS_KEY] = backgroundType.key
                    prefs[CANVAS_COLOR] = backgroundType.colorArgb
                }

                is BackgroundType.Image -> {
                    prefs[BACKGROUND_TYPE] = BackgroundType.TYPE_IMAGE
                    prefs[IMAGE_KEY] = backgroundType.key
                }
            }
        }
    }

    override suspend fun resetToDefaults() {
        dataStore.edit {
            it.clear()
        }
    }
}

private var dataStoreInstance: DataStore<Preferences>? = null

internal fun getOrCreateDataStore(producePath: () -> String): DataStore<Preferences> {
    return dataStoreInstance ?: run {
        androidx.datastore.preferences.core.PreferenceDataStoreFactory
            .createWithPath(
                produceFile = { producePath().toPath() }
            ).also { dataStoreInstance = it }
    }
}
