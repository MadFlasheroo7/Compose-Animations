package pro.jayeshseth.animations.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures the Kotlin/JS and Kotlin/Wasm targets for a web browser environment.
 *
 * This extension function applies the necessary configuration to a Gradle `Project`
 * to set up both JavaScript (JS) and WebAssembly (Wasm) targets for a Compose Multiplatform project.
 *
 * For both `js` and `wasmJs` targets, it:
 * - Enables the browser toolchain.
 * - Sets up the webpack configuration to produce a unified output file named "composeApp.js".
 * - Declares an executable binary, making the targets runnable.
 */
@OptIn(ExperimentalWasmDsl::class)
internal fun Project.configureWebTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        js {
            browser {
                commonWebpackConfig {
                    outputFileName = "composeApp.js"
                }
            }
            binaries.executable()
        }
        wasmJs {
            browser {
                commonWebpackConfig {
                    outputFileName = "composeApp.js"
                }
            }
            binaries.executable()
        }
    }
}