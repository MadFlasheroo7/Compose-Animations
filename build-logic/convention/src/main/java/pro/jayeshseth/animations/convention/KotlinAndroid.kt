package pro.jayeshseth.animations.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures common Kotlin and Android settings for an Android Application module.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension
) {
    commonExtension.compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()

    commonExtension.defaultConfig.minSdk = libs.findVersion("min-sdk").get().toString().toInt()

    commonExtension.compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    commonExtension.compileOptions.targetCompatibility = JavaVersion.VERSION_17

    commonExtension.buildFeatures.buildConfig = true

    commonExtension.lint.abortOnError = false

    tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                )
            )
        }
    }
}
