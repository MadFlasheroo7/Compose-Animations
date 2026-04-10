package pro.jayeshseth.animations.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures a Gradle [Project] for Kotlin Multiplatform development.
 *
 * This function applies common configurations for a multiplatform library module, including:
 * - Setting the Android library namespace based on the project's path.
 * - Configuring targets for Android, Desktop (JVM), and Web (JS).
 * - Configuring iOS targets (X64, Arm64, SimulatorArm64) and setting the framework base name.
 * - Applying the Kotlin source set hierarchy template.
 * - Adding common compiler options, such as enabling expect/actual classes and opting into
 *   experimental APIs like `kotlin.RequiresOptIn` and `kotlin.time.ExperimentalTime`.
 */
internal fun Project.configureKotlinMultiplatform() {
    extensions.configure<LibraryExtension> {
        namespace = this@configureKotlinMultiplatform.pathToPackageName()
    }

    configureAndroidTarget()
    configureDesktopTarget()
    configureWebTarget()

    extensions.configure<KotlinMultiplatformExtension> {
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