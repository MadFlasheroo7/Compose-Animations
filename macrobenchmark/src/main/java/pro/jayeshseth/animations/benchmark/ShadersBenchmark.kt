package pro.jayeshseth.animations.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShadersBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun shadersNavigationAndRenderNone() = shadersNavigationAndRender(CompilationMode.None())

    @Test
    fun shadersNavigationAndRenderPartial() = shadersNavigationAndRender(CompilationMode.Partial())

    @OptIn(ExperimentalMetricApi::class)
    private fun shadersNavigationAndRender(compilationMode: CompilationMode) = 
        benchmarkRule.measureRepeated(
            packageName = "pro.jayeshseth.animations",
            metrics = listOf(
                FrameTimingMetric(),
                MemoryUsageMetric(MemoryUsageMetric.Mode.Last)
            ),
            iterations = 5,
            startupMode = StartupMode.WARM,
            compilationMode = compilationMode,
        ) {
            startActivityAndWait()
            
            // 1. Navigate to Shaders
            val shadersButton = device.findObject(By.textContains("Shaders"))
            shadersButton?.click()
            
            // 2. Wait for Shaders screen to load
            device.wait(Until.hasObject(By.textContains("Shaders")), 5_000)
            
            // 3. Measure rendering performance of the shader
            Thread.sleep(3_000)
        }
}
