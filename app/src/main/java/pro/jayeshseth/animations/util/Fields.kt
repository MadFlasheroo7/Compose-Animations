package pro.jayeshseth.animations.util

sealed class Fields {
    data class SliderData(
        val title: String,
        val value: Float,
        val step: Float,
        val onValueChange: (Float) -> Unit,
        val valueRange: ClosedFloatingPointRange<Float>,
        val roundToInt: Boolean,
    ) : Fields()
}