package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Sealed hierarchy representing the different kinds of app background.
 *
 * Each variant ([Shader], [Canvas], [Image]) follows the same pattern:
 * - An **interface** declaring identity ([key], [displayName]), preview metadata
 *   ([previewColors]), an optional user-configurable parameter, and a self-rendering
 *   [@Composable] [Content] method.
 * - A **companion registry** populated once at startup via [BuiltInShaders.register].
 *   The registry provides [entries] for the picker UI and [fromKey] for restoring
 *   persisted selections.
 */
sealed interface BackgroundType {

    /**
     * A GPU-accelerated **SKSL** or **AGSL** shader background
     */
    interface Shader : BackgroundType {
        /** Unique identifier used for persistence (e.g. "ink_flow"). */
        val key: String

        /** Title shown in the shader picker UI. */
        val displayName: String

        /** List of **ARGB** colors for the preview gradient card. */
        val previewColors: List<Int>

        /** Playback speed multiplier for the shader animation. */
        val speed: Float

        /** Returns a copy of this shader with a different [speed]. */
        fun withSpeed(speed: Float): Shader

        /** Actual shader composable to render as background.*/
        @Composable
        fun Content(modifier: Modifier)

        companion object {
            /**
             * Central shader catalog, stored by unique [Shader.key].
             *
             * Populated once at app startup by **[BackgroundRegistrar]** (via [initializeApp]).
             *
             * Read by:
             * - [entries] — the shader picker UI enumerates all available shaders
             * - [fromKey] — the persistence layer restores a saved shader by its key
             */
            private val registry = linkedMapOf<String, Shader>()

            /** Adds [shader] to the catalog. Called once per shader at startup. */
            fun register(shader: Shader) {
                registry[shader.key] = shader
            }

            /** All registered shaders. */
            val entries: List<Shader>
                get() {
                    check(registry.isNotEmpty()) {
                        "Shader registry is empty — was initializeApp() called before accessing shaders?"
                    }
                    return registry.values.toList()
                }

            /**
             * Looks up a shader by its [key] and applies the given [speed].
             * Falls back to the first registered shader if [key] is unknown.
             */
            fun fromKey(key: String, speed: Float = 0.2f): Shader =
                (registry[key] ?: entries.first()).withSpeed(speed)
        }
    }

    /**
     * A Compose [androidx.compose.foundation.Canvas]-drawn background pattern.
     *
     * Each implementation draws an animated pattern (e.g. gradient sweep, dot grid)
     * and accepts a user-configurable [colorArgb] that tints the pattern.
     */
    interface Canvas : BackgroundType {
        /** Unique identifier used for persistence (e.g. "gradient_sweep"). */
        val key: String

        /** Title shown in the canvas picker UI. */
        val displayName: String

        /** List of **ARGB** colors for the preview gradient card. */
        val previewColors: List<Int>

        /** User-selected ARGB tint color for the pattern. */
        val colorArgb: Int

        /** Returns a copy of this canvas pattern with a different [colorArgb]. */
        fun withColor(colorArgb: Int): Canvas

        /** Actual canvas composable to render as background.*/
        @Composable
        fun Content(modifier: Modifier)

        companion object {
            /**
             * Central canvas catalog, stored by unique [Canvas.key].
             *
             * Populated once at app startup by **[BackgroundRegistrar]** (via [initializeApp]).
             *
             * Read by:
             * - [entries] — the canvas picker UI enumerates all available canvas.
             * - [fromKey] — the persistence layer restores a saved canvas by its key.
             */
            private val registry = linkedMapOf<String, Canvas>()

            /** Adds [canvas] to the catalog. Called once per canvas at startup. */
            fun register(canvas: Canvas) {
                registry[canvas.key] = canvas
            }

            /** All registered canvas. */
            val entries: List<Canvas>
                get() {
                    check(registry.isNotEmpty()) {
                        "Canvas registry is empty — was initializeApp() called before accessing canvases?"
                    }
                    return registry.values.toList()
                }

            /**
             * Looks up a canvas by its [key] and applies the given [colorArgb].
             * Falls back to the first registered pattern if [key] is unknown.
             */
            fun fromKey(key: String, colorArgb: Int = 0xFF00FFFF.toInt()): Canvas =
                (registry[key] ?: entries.first()).withColor(colorArgb)
        }
    }

    /**
     * A static image background.
     *
     * Unlike [Shader] and [Canvas], image backgrounds have no user-configurable
     * parameter — the user simply picks one of the registered images(atleast for now ).
     */
    interface Image : BackgroundType {
        /** Unique identifier used for persistence (e.g. "cat"). */
        val key: String

        /** Title shown in the image picker UI. */
        val displayName: String

        /** List of **ARGB** colors for the preview gradient card. */
        val previewColors: List<Int>

        /** Actual image composable to render as background.*/
        @Composable
        fun Content(modifier: Modifier)

        companion object {
            /**
             * Central image catalog, stored by unique [Image.key].
             *
             * Populated once at app startup by **[BackgroundRegistrar]** (via [initializeApp]).
             *
             * Read by:
             * - [entries] — the image picker UI enumerates all available images.
             * - [fromKey] — the persistence layer restores a saved image by its key.
             */
            private val registry = linkedMapOf<String, Image>()

            /** Adds [image] to the catalog. Called once per image at startup. */
            fun register(image: Image) {
                registry[image.key] = image
            }

            /** All registered images. */
            val entries: List<Image>
                get() {
                    check(registry.isNotEmpty()) {
                        "Image registry is empty — was initializeApp() called before accessing images?"
                    }
                    return registry.values.toList()
                }

            /**
             * Looks up an image by its [key].
             * Falls back to the first registered image if [key] is unknown.
             */
            fun fromKey(key: String): Image =
                registry[key] ?: entries.first()
        }
    }

    companion object {
        /** Persistence tag for [Shader] backgrounds. */
        const val TYPE_SHADER = "shader"

        /** Persistence tag for [Canvas] backgrounds. */
        const val TYPE_CANVAS = "canvas"

        /** Persistence tag for [Image] backgrounds. */
        const val TYPE_IMAGE = "image"
    }
}
