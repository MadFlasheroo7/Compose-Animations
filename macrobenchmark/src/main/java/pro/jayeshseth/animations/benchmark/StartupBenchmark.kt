package pro.jayeshseth.animations.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startupCold() = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            StartupTimingMetric(),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = CompilationMode.DEFAULT,
    ) {
        pressHome(1000)
        startActivityAndWait()
    }

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startupWarm() = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            StartupTimingMetric(),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = CompilationMode.DEFAULT,
    ) {
        pressHome(1000)
        startActivityAndWait()
    }

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun startupHot() = benchmarkRule.measureRepeated(
        packageName = "pro.jayeshseth.animations",
        metrics = listOf(
            StartupTimingMetric(),
            TraceSectionMetric("BaseInteractiveButton")
        ),
        iterations = 5,
        startupMode = StartupMode.HOT,
        compilationMode = CompilationMode.DEFAULT,
    ) {
        pressHome(1000)
        startActivityAndWait()
    }
}
