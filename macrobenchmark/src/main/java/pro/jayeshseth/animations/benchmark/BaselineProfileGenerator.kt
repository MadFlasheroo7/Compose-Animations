package pro.jayeshseth.animations.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = "pro.jayeshseth.animations",
        includeInStartupProfile = true
    ) {
        // 1. App Startup
        startActivityAndWait()
        
        // Wait for UI to be ready
        device.wait(Until.hasObject(By.textContains("Default Apis")), 5_000)
        
        // 2. Journey through the Home Screen (Scrolling)
        val initialList = device.findObject(By.scrollable(true))
        if (initialList != null) {
            initialList.setGestureMargin(device.displayWidth / 5)
            repeat(3) {
                device.findObject(By.scrollable(true))?.scroll(Direction.DOWN, 1f)
                Thread.sleep(300)
            }
        }
    }
}
