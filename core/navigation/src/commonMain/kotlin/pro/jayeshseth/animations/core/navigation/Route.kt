package pro.jayeshseth.animations.core.navigation

import androidx.compose.runtime.Immutable
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
 * data object HomeScreen : Route
 *
 * @Serializable
 * data class ProfileScreen(val userId: String) : Route
 * ```
 */
@Immutable
@Serializable(RouteSerializer::class)
interface Route

@Immutable
interface SecondaryRoute : Route


/**
 * Extension to check if a route should be treated as secondary content
 */
val Route.isSecondary: Boolean
    get() = this is SecondaryRoute

/**
 * A custom [KSerializer] for handling polymorphic serialization of [Route] subclasses.
 *
 * Routes are identified by their [kotlinx.serialization.descriptors.SerialDescriptor.serialName] —
 * a compile-time string constant the kotlinx-serialization plugin embeds in bytecode.
 * Using `serialName` (rather than `KClass.simpleName` or `qualifiedName`) makes the registry key:
 *  - R8-safe: string literals are never renamed
 *  - KMP-safe: every target (JVM/Native/JS) returns the same value
 *  - collision-free: serial names are fully qualified by default
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
class RouteSerializer : KSerializer<Route> {

    companion object Companion {
        // Registration is a startup-only, single-threaded operation (MainActivity.onCreate →
        // initializeApp() → registerAllRoutes(), before setContent). After that the maps are
        // read-only on the main thread (Compose composition). No synchronization needed.
        private val serializerBySerialName = mutableMapOf<String, KSerializer<out Route>>()
        private val serializerByClass = mutableMapOf<KClass<out Route>, KSerializer<out Route>>()

        /**
         * Register a route explicitly.
         * MUST be called for every route on iOS/Native.
         */
        fun <T : Route> registerRoute(kClass: KClass<T>, serializer: KSerializer<T>) {
            val serialName = serializer.descriptor.serialName
            serializerBySerialName[serialName] = serializer
            serializerByClass[kClass] = serializer
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
            var serialName: String? = null
            var route: Route? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> serialName = decodeStringElement(descriptor, 0)
                    1 -> {
                        val name = requireNotNull(serialName) { "Type must be decoded before payload" }
                        val serializer = serializerBySerialName[name]
                            ?: error("Route with serialName '$name' is not registered. Call RouteSerializer.register<...>() at app startup.")

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
            @Suppress("UNCHECKED_CAST")
            val finalSerializer = (serializerByClass[value::class] as? KSerializer<Route>)
                ?: try {
                    // Fallback for Android/JVM only — on iOS/Native this throws.
                    value::class.serializer() as KSerializer<Route>
                } catch (e: Exception) {
                    throw SerializationException(
                        "Serializer for '${value::class.simpleName}' not found. " +
                                "Call RouteSerializer.register<${value::class.simpleName}>() at startup.",
                        e,
                    )
                }

            encodeStringElement(descriptor, 0, finalSerializer.descriptor.serialName)
            encodeSerializableElement(descriptor, 1, finalSerializer, value)
        }
    }
}