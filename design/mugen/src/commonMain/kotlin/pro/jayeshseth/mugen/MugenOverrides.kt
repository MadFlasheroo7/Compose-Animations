package pro.jayeshseth.mugen

import androidx.compose.runtime.Immutable
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.locals.MugenTextDefaults
import pro.jayeshseth.mugen.look.MugenLook
import kotlin.reflect.KClass

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
class MugenOverrides private constructor(
    private val transforms: Map<KClass<out Any>, (Any) -> Any>
) {
    /**
     * Resolves the overridden defaults for the given [base] object.
     * Returns [base] unchanged if no override is registered.
     */
    fun <T : Any> get(base: T): T {
        val transform = transforms[base::class] ?: return base
        @Suppress("UNCHECKED_CAST")
        return transform(base) as T
    }

    companion object {
        /** A pass-through overrides instance — uses the active Look's defaults unchanged. */
        val Empty = MugenOverrides(emptyMap())

        /** DSL entry point. */
        fun build(block: Builder.() -> Unit): MugenOverrides =
            Builder().apply(block).build()
    }

    class Builder internal constructor() {
        private val transforms = mutableMapOf<KClass<out Any>, (Any) -> Any>()

        /** Registers a type-safe transformer override for component defaults of type [T]. */
        fun <T : Any> override(clazz: KClass<T>, transform: (T) -> T) {
            transforms[clazz] = { transform(it as T) }
        }

        /** Registers a type-safe transformer override for component defaults of type [T]. */
        inline fun <reified T : Any> override(noinline transform: (T) -> T) {
            override(T::class, transform)
        }

        internal fun build(): MugenOverrides = MugenOverrides(transforms.toMap())
    }
}


