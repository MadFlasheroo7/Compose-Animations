package pro.jayeshseth.animations.core.navigation

import androidx.compose.runtime.Immutable
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlin.reflect.KClass

typealias OnNavAction = (Route) -> Unit

/**
 * Base class for all navigation routes within the application.
 *
 * This open class serves as a polymorphic base for defining specific navigation destinations.
 * Subclasses of `Route` represent individual screens or navigation paths.
 *
 * It uses a custom [RoutePolymorphicSerializer] to enable serialization
 * of its various concrete subclasses, making it compatible with libraries that
 * require serializable arguments for navigation, such as when saving state
 * or passing complex objects between destinations.
 *
 * Example of a concrete route implementation:
 * ```kotlin
 * @Serializable
 * data object HomeScreen : Route()
 *
 * @Serializable
 * data class ProfileScreen(val userId: String) : Route()
 * ```
 */
@Immutable
@Serializable(with = RoutePolymorphicSerializer::class)
open class Route



/**
 * A custom [KSerializer] for handling polymorphic serialization of [Route] subclasses.
 *
 * This serializer is necessary because the standard polymorphic serialization mechanisms
 * in `kotlinx.serialization` can be complex to set up, especially for passing objects
 * through Android Bundles (e.g., for navigation arguments). This custom implementation
 * provides a simpler, explicit way to serialize and deserialize different `Route` types.
 *
 * Serialization format:
 * It serializes a `Route` object into a structure containing two fields:
 * 1.  `type`: The fully qualified class name of the actual `Route` subclass instance.
 * 2.  `payload`: The actual serialized data of the `Route` object.
 *
 * Deserialization process:
 * It first reads the `type` (class name) to determine which specific `Route` subclass
 * needs to be instantiated. It then uses reflection (`Class.forName`) to get the
 * corresponding `KClass` and its serializer. Finally, it uses this specific serializer
 * to deserialize the `payload` back into the correct `Route` object.
 *
 * This approach allows for type-safe navigation arguments without needing to register
 * every subclass in a `SerializersModule`.
 *
 * @see Route
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
private class RoutePolymorphicSerializer : KSerializer<Route> {

    companion object {
        // Runtime registry that tracks serialized route types
        private val routeRegistry = mutableMapOf<String, KSerializer<out Route>>()

        // Lock for thread-safe registry access (Multiplatform compatible)
        private val registryLock = SynchronizedObject()

        @Suppress("UNCHECKED_CAST")
        fun <T : Route> registerRoute(kClass: KClass<T>) {
            synchronized(registryLock) {
                val qualifiedName = kClass.qualifiedName
                    ?: error("Cannot register route without qualified name")
                if (!routeRegistry.containsKey(qualifiedName)) {
                    routeRegistry[qualifiedName] = kClass.serializer() as KSerializer<out Route>
                }
            }
        }
    }

    override val descriptor = buildClassSerialDescriptor("PolymorphicRoute") {
        element<String>("type")
        element("payload", buildClassSerialDescriptor("RoutePayload"))
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Route {
        return decoder.decodeStructure(descriptor) {
            var className: String? = null
            var route: Route? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> className = decodeStringElement(descriptor, 0)
                    1 -> {
                        val serializer = synchronized(registryLock) {
                            routeRegistry[className]
                                ?: error(
                                    "Route type not registered: $className. " +
                                            "Make sure to serialize an instance of this route at least once before deserializing."
                                )
                        }
                        route = decodeSerializableElement(descriptor, 1, serializer) as Route
                    }

                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            route ?: error("Route payload not found")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun serialize(encoder: Encoder, value: Route) {
        encoder.encodeStructure(descriptor) {
            val qualifiedName = value::class.qualifiedName
                ?: error("Cannot serialize route without qualified name")

            // Auto-register this route type
            synchronized(registryLock) {
                if (!routeRegistry.containsKey(qualifiedName)) {
                    routeRegistry[qualifiedName] = value::class.serializer()
                }
            }

            encodeStringElement(descriptor, 0, qualifiedName)

            val serializer = value::class.serializer() as KSerializer<Route>
            encodeSerializableElement(descriptor, 1, serializer, value)
        }
    }
}