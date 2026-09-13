package pro.jayeshseth.mugen.locals

import androidx.compose.runtime.Immutable
import kotlin.reflect.KClass

/**
 * Typesafe heterogeneous container holding component defaults registered by a [pro.jayeshseth.mugen.look.MugenLook].
 */
@Immutable
class MugenDefaultsRegistry(
    private val defaults: Map<KClass<*>, Any> = emptyMap(),
) {
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(clazz: KClass<T>): T? = defaults[clazz] as? T

    class Builder {
        private val map = mutableMapOf<KClass<*>, Any>()

        fun <T : Any> register(clazz: KClass<T>, default: T) {
            map[clazz] = default
        }

        inline fun <reified T : Any> register(default: T) {
            register(T::class, default)
        }

        fun build(): MugenDefaultsRegistry = MugenDefaultsRegistry(map)
    }

    companion object {
        val Empty = MugenDefaultsRegistry(emptyMap())
    }
}

/**
 * DSL builder for creating a [MugenDefaultsRegistry].
 */
inline fun mugenDefaults(builder: MugenDefaultsRegistry.Builder.() -> Unit): MugenDefaultsRegistry =
    MugenDefaultsRegistry.Builder().apply(builder).build()
