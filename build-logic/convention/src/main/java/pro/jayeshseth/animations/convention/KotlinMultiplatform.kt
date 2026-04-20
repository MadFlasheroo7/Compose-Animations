package pro.jayeshseth.animations.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures a Gradle [Project] for Kotlin Multiplatform development.
 *
 * Sets up targets (Desktop, Web, iOS), hierarchy template, and compiler options.
 * The android target and namespace are handled by the `com.android.kotlin.multiplatform.library`
 * plugin via the `androidLibrary {}` DSL in the calling plugin.
 */
internal fun Project.configureKotlinMultiplatform() {
    configureDesktopTarget()
    configureWebTarget()

    extensions.configure<KotlinMultiplatformExtension> {
        jvmToolchain(17)

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = this@configureKotlinMultiplatform.pathToFrameworkName()
            }
        }

        applyHierarchyTemplate()

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
        }
    }
}
