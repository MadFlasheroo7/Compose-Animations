package pro.jayeshseth.mugen.locals

import androidx.compose.runtime.Immutable
import pro.jayeshseth.mugen.MugenOverrides
import pro.jayeshseth.mugen.look.MugenLook
import kotlin.reflect.KClass

/**
 * Lazy, on-demand resolver for component defaults in [pro.jayeshseth.mugen.MugenTheme].
 *
 * Keeps [pro.jayeshseth.mugen.MugenTheme] fixed at a single CompositionLocal regardless of
 * how many components exist (core or third-party).
 */
@Immutable
class MugenDefaultsContainer(
    val look: MugenLook,
    val overrides: MugenOverrides,
) {
    private val cache = mutableMapOf<KClass<*>, Any>()

    /**
     * Resolves defaults for component class [clazz].
     * Priority:
     * 1. Look-registered defaults ([look.defaults.get(clazz)])
     * 2. Theme overrides ([overrides])
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> resolve(clazz: KClass<T>): T {
        return cache.getOrPut(clazz) {
            val base = look.defaults.get(clazz)
                ?: error("Look '${look.name}' does not provide defaults for ${clazz.simpleName}. Please register it in your Look's defaults.")
            overrides.get(base)
        } as T
    }

    inline fun <reified T : Any> resolve(): T = resolve(T::class)
}
