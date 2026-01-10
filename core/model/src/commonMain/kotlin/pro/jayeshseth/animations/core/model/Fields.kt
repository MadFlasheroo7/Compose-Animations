package pro.jayeshseth.animations.core.model

/**
 * A sealed class representing different types of input fields for a user interface.
 * This class is used to model various controls that can be displayed, such as sliders
 * and toggles, each with its own specific set of properties and callbacks.
 */
sealed class Fields {
    /**
     * Represents the data required to configure a slider control.
     *
     * This data class is part of the sealed `Fields` hierarchy and is used to pass
     * all necessary parameters for creating and managing a slider UI component.
     *
     * @property title A descriptive label for the slider.
     * @property value The current value of the slider.
     * @property step The number of discrete increments the slider can be moved.
     * @property onValueChange A callback function invoked when the slider's value changes.
     * @property valueRange The inclusive range of possible values for the slider.
     * @property roundToInt If true, the displayed slider value will be rounded to the nearest integer.
     */
    data class SliderData(
        val title: String,
        val value: Float,
        val step: Int,
        val onValueChange: (Float) -> Unit,
        val valueRange: ClosedFloatingPointRange<Float>,
        val roundToInt: Boolean,
    ) : Fields()

    /**
     * Represents the data required for a toggle switch (like a Switch or Checkbox).
     *
     * @property title The label or text displayed next to the toggle.
     * @property value The current boolean state of the toggle (true for on/checked, false for off/unchecked).
     * @property onValueChange A callback function that is invoked with the new boolean value when the user interacts with the toggle.
     */
    data class ToggleData(
        val title: String,
        val value: Boolean,
        val onValueChange: (Boolean) -> Unit,
    ) : Fields()
}