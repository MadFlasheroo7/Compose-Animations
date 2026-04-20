package pro.jayeshseth.animations.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configures Compose for an Android module (application or library).
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension
) {
    commonExtension.buildFeatures.buildConfig = true
    commonExtension.buildFeatures.compose = true

    commonExtension.packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")

    dependencies {
        val bom = libs.findLibrary("compose-bom").get()
        "implementation"(platform(bom))
        "testImplementation"(platform(bom))

        "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
        "debugImplementation"(libs.findLibrary("compose-ui-tooling-preview").get())
        "debugImplementation"(libs.findLibrary("compose-ui-manifest").get())
    }
}
