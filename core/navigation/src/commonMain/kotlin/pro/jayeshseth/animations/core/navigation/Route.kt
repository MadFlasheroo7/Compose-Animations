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
 * It uses a custom [RouteSerializer] to enable serialization
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
@Serializable(RouteSerializer::class)
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
class RouteSerializer : KSerializer<Route> {

    companion object Companion {
        // Registry to hold the serializers
        private val routeRegistry = mutableMapOf<String, KSerializer<out Route>>()
        private val registryLock = SynchronizedObject()

        /**
         * Register a route explicitly.
         * MUST be called for every route on iOS/Native.
         */
        fun <T : Route> registerRoute(kClass: KClass<T>, serializer: KSerializer<T>) {
            synchronized(registryLock) {
                val qualifiedName = kClass.simpleName
                    ?: error("Cannot register route without qualified name")
                routeRegistry[qualifiedName] = serializer
            }
        }

        // Helper for cleaner syntax: RouteSerializer.register<HomeScreen>()
        inline fun <reified T : Route> register() {
            registerRoute(T::class, serializer<T>())
        }
    }

    override val descriptor = buildClassSerialDescriptor("PolymorphicRoute") {
        element<String>("type")
        element("payload", buildClassSerialDescriptor("RoutePayload"))
    }

    override fun deserialize(decoder: Decoder): Route {
        return decoder.decodeStructure(descriptor) {
            var className: String? = null
            var route: Route? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> className = decodeStringElement(descriptor, 0)
                    1 -> {
                        val name = requireNotNull(className) { "Type must be decoded before payload" }
                        val serializer = synchronized(registryLock) {
                            routeRegistry[name]
                        } ?: error("Route '$name' is not registered. Call RouteSerializer.register<$name>() at app startup.")

                        route = decodeSerializableElement(descriptor, 1, serializer)
                    }
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            route ?: error("Route payload not found")
        }
    }

    override fun serialize(encoder: Encoder, value: Route) {
        encoder.encodeStructure(descriptor) {
            val qualifiedName = value::class.simpleName
                ?: error("Cannot serialize route without qualified name")

            // 1. Look up the serializer in the registry
            val serializer = synchronized(registryLock) {
                routeRegistry[qualifiedName]
            }

            // 2. On JVM, we can fallback to magic. On iOS, we MUST fail if not found.
            @Suppress("UNCHECKED_CAST")
            val finalSerializer = (serializer as? KSerializer<Route>)
                ?: try {
                    // Fallback for Android/JVM only
                    value::class.serializer() as KSerializer<Route>
                } catch (e: Exception) {
                    // This creates the error message you are seeing on iOS
                    throw SerializationException(
                        "Serializer for '${qualifiedName}' not found. " +
                                "On iOS, you must explicitly call RouteSerializer.register<${value::class.simpleName}>() at startup.", e
                    )
                }

            encodeStringElement(descriptor, 0, qualifiedName)
            encodeSerializableElement(descriptor, 1, finalSerializer, value)
        }
    }
}