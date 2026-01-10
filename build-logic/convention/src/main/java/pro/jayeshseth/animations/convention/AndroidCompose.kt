package pro.jayeshseth.animations.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


/**
 * Configures Compose for an Android module (application or library).
 * This function applies common Compose settings and dependencies to the given `commonExtension`.
 *
 * It enables the Compose feature in the build, handles packaging options to exclude
 * duplicate license files, and adds essential Compose dependencies.
 *
 * Dependencies added:
 * - Compose Bill of Materials (BOM) for consistent library versions.
 * - Tooling and preview support for `debug` builds, enabling features like @Preview.
 *
 * @param commonExtension The `CommonExtension` from the Android Gradle Plugin to which the configuration is applied.
 *                        This can be for an `application` or `library` module.
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            buildConfig = true
            compose = true
        }

        packaging {
            resources {
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            "implementation"(platform(bom))
            "testImplementation"(platform(bom))

            "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            "debugImplementation"(libs.findLibrary("compose-ui-tooling-preview").get())
            "debugImplementation"( libs.findLibrary("compose-ui-manifest").get())
        }
    }
}