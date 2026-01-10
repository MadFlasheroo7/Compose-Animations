package pro.jayeshseth.animations.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures the "desktop" JVM target for a Kotlin Multiplatform project.
 *
 * This extension function applies the following configuration:
 * - Adds a JVM target named "desktop".
 * - Sets the a`jvmTarget` for all compilations within the "desktop" target to `JvmTarget.JVM_17`.
 */
internal fun Project.configureDesktopTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        jvm("desktop") {
            compilations.all {
                compileTaskProvider.configure {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }
            }
        }
    }
}