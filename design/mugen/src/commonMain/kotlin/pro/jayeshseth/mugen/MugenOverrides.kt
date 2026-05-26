package pro.jayeshseth.mugen

import androidx.compose.runtime.Immutable
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.locals.MugenTextDefaults
import pro.jayeshseth.mugen.look.MugenLook

/**
 * One-shot theme-level overrides. Use the [build] DSL to override per-component
 * defaults without writing a new Look.
 *
 * Example:
 * ```
 * MugenTheme(
 *     look = MugenLooks.Material,
 *     overrides = MugenOverrides.build {
 *         button { it.copy(minHeight = 56.dp) }
 *     },
 * ) { … }
 * ```
 *
 * For sub-tree overrides, prefer `CompositionLocalProvider(LocalMugenButtonDefaults …)`
 * directly — `MugenOverrides` is for theme-wide setup.
 */
@Immutable
class MugenOverrides internal constructor(
    internal val button: ((MugenButtonDefaults) -> MugenButtonDefaults)? = null,
    internal val card: ((MugenCardDefaults) -> MugenCardDefaults)? = null,
    internal val text: ((MugenTextDefaults) -> MugenTextDefaults)? = null,
) {
    companion object {
        /** A pass-through overrides instance — uses the active Look's defaults unchanged. */
        val Empty: MugenOverrides = MugenOverrides()

        /** DSL entry point. */
        fun build(block: Builder.() -> Unit): MugenOverrides =
            Builder().apply(block).build()
    }

    class Builder internal constructor() {
        private var buttonTransform: ((MugenButtonDefaults) -> MugenButtonDefaults)? = null
        private var cardTransform: ((MugenCardDefaults) -> MugenCardDefaults)? = null
        private var textTransform: ((MugenTextDefaults) -> MugenTextDefaults)? = null

        /** Transform the per-Look button defaults. */
        fun button(transform: (MugenButtonDefaults) -> MugenButtonDefaults) {
            buttonTransform = transform
        }

        fun card(transform: (MugenCardDefaults) -> MugenCardDefaults) {
            cardTransform = transform
        }

        fun text(transform: (MugenTextDefaults) -> MugenTextDefaults) {
            textTransform = transform
        }

        internal fun build(): MugenOverrides =
            MugenOverrides(buttonTransform, cardTransform, textTransform)
    }
}

/**
 * Internal: derive the per-component defaults for the given [look], applying any [overrides].
 * Used by [MugenTheme].
 */
internal fun MugenLook.applyButtonDefaults(overrides: MugenOverrides): MugenButtonDefaults {
    val base = defaultButtonDefaults(this)
    return overrides.button?.invoke(base) ?: base
}

internal fun MugenLook.applyCardDefaults(overrides: MugenOverrides): MugenCardDefaults {
    val base = defaultCardDefaults(this)
    return overrides.card?.invoke(base) ?: base
}

internal fun MugenLook.applyTextDefaults(overrides: MugenOverrides): MugenTextDefaults {
    val base = defaultTextDefaults(this)
    return overrides.text?.invoke(base) ?: base
}
