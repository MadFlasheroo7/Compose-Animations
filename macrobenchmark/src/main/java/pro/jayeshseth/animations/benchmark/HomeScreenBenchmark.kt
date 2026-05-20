package pro.jayeshseth.animations.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun homeScreenIdleNone() = homeScreenIdle(CompilationMode.None())

    @Test
    fun homeScreenIdlePartial() = homeScreenIdle(CompilationMode.Partial())

    @Test
    fun homeScreenScrollNone() = homeScreenScroll(CompilationMode.None())

    @Test
    fun homeScreenScrollPartial() = homeScreenScroll(CompilationMode.Partial())

    /**
     * Measures frame timing and memory during idle on the home screen.
     */
    @OptIn(ExperimentalMetricApi::class)
    private fun homeScreenIdle(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            FrameTimingMetric(),
            MemoryUsageMetric(MemoryUsageMetric.Mode.Last),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = compilationMode,
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.textContains("Default Apis")), 5_000)
        Thread.sleep(3_000)
    }

    /**
     * Measures frame timing and memory while scrolling the home screen button list.
     */
    @OptIn(ExperimentalMetricApi::class)
    private fun homeScreenScroll(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            FrameTimingMetric(),
            MemoryUsageMetric(MemoryUsageMetric.Mode.Last),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = compilationMode,
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.textContains("Default Apis")), 5_000)

        val initialList = device.findObject(By.scrollable(true))
        if (initialList != null) {
            initialList.setGestureMargin(device.displayWidth / 5)
            repeat(3) {
                device.findObject(By.scrollable(true))?.scroll(Direction.DOWN, 1f)
                Thread.sleep(500)
            }
            repeat(3) {
                device.findObject(By.scrollable(true))?.scroll(Direction.UP, 1f)
                Thread.sleep(500)
            }
        }
    }
}
