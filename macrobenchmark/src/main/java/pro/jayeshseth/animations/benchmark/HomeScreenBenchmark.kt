package pro.jayeshseth.animations.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
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

    /**
     * Measures frame timing during idle on the home screen.
     * This captures the cost of infinite animations in BaseInteractiveButton
     * (shimmer, inner shadow radius, etc.) running while the user isn't interacting.
     */
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun homeScreenIdle() = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.DEFAULT,
    ) {
        startActivityAndWait()
        // Let the home screen settle and infinite animations run
        device.wait(Until.hasObject(By.textContains("Default Apis")), 5_000)
        // Measure idle frames for 3 seconds — captures infinite animation cost
        Thread.sleep(3_000)
    }

    /**
     * Measures frame timing while scrolling the home screen button list.
     * This captures the combined cost of BaseInteractiveButton rendering,
     * entrance animations (AnimateButtonScale), and layout during scroll.
     */
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun homeScreenScroll() = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.DEFAULT,
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.textContains("Default Apis")), 5_000)

        val list = device.findObject(By.scrollable(true))
        if (list != null) {
            list.setGestureMargin(device.displayWidth / 5)
            repeat(3) {
                list.scroll(Direction.DOWN, 1f)
                Thread.sleep(500)
            }
            repeat(3) {
                list.scroll(Direction.UP, 1f)
                Thread.sleep(500)
            }
        }
    }

    /**
     * Measures frame timing during the initial load of the home screen.
     * Captures the entrance animation burst (scale spring + blur tween)
     * across all visible buttons + all BaseInteractiveButton setup cost.
     */
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun homeScreenInitialRender() = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = CompilationMode.DEFAULT,
    ) {
        startActivityAndWait()
        // Wait for home screen content to appear
        device.wait(Until.hasObject(By.textContains("Default Apis")), 5_000)
        // Let initial entrance animations complete
        Thread.sleep(2_000)
    }
}