package pro.jayeshseth.mugen

/**
 * Top-level identity for the mugen design system.
 *
 * mugen is a Compose Multiplatform design library where the entire visual language
 * (colors, shapes, motion, typography, spacing, elevation, *and* per-component rendering)
 * is swappable on the fly via [pro.jayeshseth.mugen.look.MugenLook].
 */
object Mugen {
    const val NAME: String = "mugen"
    const val VERSION: String = "0.0.0-indev01"
    const val CODENAME: String = "mugen"
}

/**
 * Marks API surfaces that are internal to mugen's implementation. They may change without
 * notice between indev releases and should not be consumed by external code.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal mugen API. It can change without notice between indev releases.",
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS,
)
annotation class MugenInternalApi
