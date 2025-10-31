package pro.jayeshseth.animations.core.model

sealed class Fields {
    data class SliderData(
        val title: String,
        val value: Float,
        val step: Float,
        val onValueChange: (Float) -> Unit,
        val valueRange: ClosedFloatingPointRange<Float>,
        val roundToInt: Boolean,
    ) : Fields()

    data class ToggleData(
        val title: String,
        val value: Boolean,
        val onValueChange: (Boolean) -> Unit,
    ) : Fields()
}