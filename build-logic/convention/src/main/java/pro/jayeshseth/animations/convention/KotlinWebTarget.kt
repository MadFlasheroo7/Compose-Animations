package pro.jayeshseth.animations.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@OptIn(ExperimentalWasmDsl::class)
internal fun Project.configureWebTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        js {
            browser()
            binaries.executable()
        }
        wasmJs {
            browser()
            binaries.executable()
        }
    }
}