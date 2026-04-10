package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.model.CustomizationRepository
import pro.jayeshseth.animations.core.model.CustomizationState

private class LocalStorageCustomizationRepository : CustomizationRepository {

    private val _state = MutableStateFlow(loadFromStorage())
    override val state: Flow<CustomizationState> = _state

    private fun loadFromStorage(): CustomizationState {
        val defaults = CustomizationState()
        val backgroundType = when (localStorage.getItem(DefaultPrefKeys.BACKGROUND_TYPE_KEY)) {
            BackgroundType.TYPE_CANVAS -> BackgroundType.Canvas.fromKey(
                key = localStorage.getItem(DefaultPrefKeys.CANVAS_KEY)
                    ?: DefaultPrefKeys.DOT_GRID_KEY,
                colorArgb = localStorage.getItem(DefaultPrefKeys.CANVAS_COLOR_KEY)?.toIntOrNull()
                    ?: defaults.buttonAccentColorArgb
            )

            BackgroundType.TYPE_IMAGE -> BackgroundType.Image.fromKey(
                key = localStorage.getItem(DefaultPrefKeys.IMAGE_KEY)
                    ?: DefaultPrefKeys.CAT_IMAGE_KEY
            )

            else -> {
                val speed =
                    localStorage.getItem(DefaultPrefKeys.SHADER_SPEED_KEY)?.toFloatOrNull() ?: 0.2f
                val shaderKey = localStorage.getItem(DefaultPrefKeys.SHADER_KEY)
                BackgroundType.Shader.fromKey(shaderKey ?: DefaultPrefKeys.INK_FLOW_KEY, speed)
            }
        }
        return CustomizationState(
            primaryColorArgb = localStorage.getItem(DefaultPrefKeys.PRIMARY_COLOR_KEY)
                ?.toIntOrNull()
                ?: defaults.primaryColorArgb,
            accentColorArgb = localStorage.getItem(DefaultPrefKeys.ACCENT_COLOR_KEY)
                ?.toIntOrNull()
                ?: defaults.accentColorArgb,
            headingColorArgb = localStorage.getItem(DefaultPrefKeys.HEADING_COLOR_KEY)
                ?.toIntOrNull()
                ?: defaults.headingColorArgb,
            primaryButtonAccentColorArgb = localStorage.getItem(DefaultPrefKeys.PRIMARY_BUTTON_ACCENT_COLOR_KEY)
                ?.toIntOrNull()
                ?: defaults.primaryButtonAccentColorArgb,
            buttonAccentColorArgb = localStorage.getItem(DefaultPrefKeys.BUTTON_ACCENT_COLOR_KEY)
                ?.toIntOrNull()
                ?: defaults.buttonAccentColorArgb,
            backgroundBlur = localStorage.getItem(DefaultPrefKeys.BACKGROUND_BLUR_KEY)
                ?.toFloatOrNull()?.dp
                ?: defaults.backgroundBlur,
            backgroundType = backgroundType,
        )
    }

    override suspend fun updateBackgroundBlur(blur: Dp) {
        localStorage.setItem(DefaultPrefKeys.BACKGROUND_BLUR_KEY, blur.toString())
        _state.value = _state.value.copy(backgroundBlur = blur)
    }

    override suspend fun updatePrimaryColor(argb: Int) {
        localStorage.setItem(DefaultPrefKeys.PRIMARY_COLOR_KEY, argb.toString())
        _state.value = _state.value.copy(primaryColorArgb = argb)
    }

    override suspend fun updateAccentColor(argb: Int) {
        localStorage.setItem(DefaultPrefKeys.ACCENT_COLOR_KEY, argb.toString())
        _state.value = _state.value.copy(accentColorArgb = argb)
    }

    override suspend fun updateHeadingColor(argb: Int) {
        localStorage.setItem(DefaultPrefKeys.HEADING_COLOR_KEY, argb.toString())
        _state.value = _state.value.copy(headingColorArgb = argb)
    }

    override suspend fun updatePrimaryButtonAccentColor(argb: Int) {
        localStorage.setItem(DefaultPrefKeys.PRIMARY_BUTTON_ACCENT_COLOR_KEY, argb.toString())
        _state.value = _state.value.copy(primaryButtonAccentColorArgb = argb)
    }

    override suspend fun updateButtonAccentColor(argb: Int) {
        localStorage.setItem(DefaultPrefKeys.BUTTON_ACCENT_COLOR_KEY, argb.toString())
        _state.value = _state.value.copy(buttonAccentColorArgb = argb)
    }

    override suspend fun updateBackground(backgroundType: BackgroundType) {
        when (backgroundType) {
            is BackgroundType.Shader -> {
                localStorage.setItem(
                    DefaultPrefKeys.BACKGROUND_TYPE_KEY,
                    BackgroundType.TYPE_SHADER
                )
                localStorage.setItem(DefaultPrefKeys.SHADER_KEY, backgroundType.key)
                localStorage.setItem(
                    DefaultPrefKeys.SHADER_SPEED_KEY,
                    backgroundType.speed.toString()
                )
            }

            is BackgroundType.Canvas -> {
                localStorage.setItem(
                    DefaultPrefKeys.BACKGROUND_TYPE_KEY,
                    BackgroundType.TYPE_CANVAS
                )
                localStorage.setItem(DefaultPrefKeys.CANVAS_KEY, backgroundType.key)
                localStorage.setItem(
                    DefaultPrefKeys.CANVAS_COLOR_KEY,
                    backgroundType.colorArgb.toString()
                )
            }

            is BackgroundType.Image -> {
                localStorage.setItem(DefaultPrefKeys.BACKGROUND_TYPE_KEY, BackgroundType.TYPE_IMAGE)
                localStorage.setItem(DefaultPrefKeys.IMAGE_KEY, backgroundType.key)
            }
        }
        _state.value = _state.value.copy(backgroundType = backgroundType)
    }

    override suspend fun resetToDefaults() {
        localStorage.clear()
        _state.value = loadFromStorage()
    }
}

private var instance: LocalStorageCustomizationRepository? = null

@Composable
actual fun rememberCustomizationRepository(): CustomizationRepository {
    return remember {
        instance ?: LocalStorageCustomizationRepository().also { instance = it }
    }
}
