package pro.jayeshseth.animations.core.navigation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.serializer

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
@OptIn(InternalSerializationApi::class)
private class RoutePolymorphicSerializer : KSerializer<Route> {

    override val descriptor =
        buildClassSerialDescriptor("PolymorphicData") {
            element(elementName = "type", serialDescriptor<String>())
            element(elementName = "payload", buildClassSerialDescriptor("Any"))
        }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Route {
        return decoder.decodeStructure(descriptor) {
            val className = decodeStringElement(descriptor, decodeElementIndex(descriptor))
            val classRef = Class.forName(className).kotlin
            val serializer = classRef.serializer()

            decodeSerializableElement(
                descriptor,
                decodeElementIndex(descriptor),
                serializer
            ) as Route
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun serialize(encoder: Encoder, value: Route) {
        encoder.encodeStructure(descriptor) {
            val className = value::class.java.name
            encodeStringElement(descriptor, index = 0, className)
            val serializer = value::class.serializer() as KSerializer<Route>
            encodeSerializableElement(descriptor, index = 1, serializer, value)
        }
    }
}