package pro.jayeshseth.animations.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * shared compose configuration plugin
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