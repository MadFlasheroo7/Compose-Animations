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

    @Test
    fun startupColdNone() = startup(CompilationMode.None(), StartupMode.COLD)

    @Test
    fun startupColdPartial() = startup(CompilationMode.Partial(), StartupMode.COLD)

    @Test
    fun startupWarmPartial() = startup(CompilationMode.Partial(), StartupMode.WARM)

    @Test
    fun startupHotPartial() = startup(CompilationMode.Partial(), StartupMode.HOT)

    @OptIn(ExperimentalMetricApi::class)
    private fun startup(compilationMode: CompilationMode, startupMode: StartupMode) = 
        benchmarkRule.measureRepeated(
            packageName = "pro.jayeshseth.animations",
            metrics = listOf(
                StartupTimingMetric(),
                TraceSectionMetric("BaseInteractiveButton")
            ),
            iterations = 5,
            startupMode = startupMode,
            compilationMode = compilationMode,
        ) {
            pressHome(1000)
            startActivityAndWait()
        }
}
