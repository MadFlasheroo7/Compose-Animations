package pro.jayeshseth.animations.core.ui.utils

import androidx.compose.runtime.Composable
import com.theapache64.rebugger.Rebugger
import com.theapache64.rebugger.RebuggerConfig

@Composable
actual fun TrackRecomposition(trackMap: Map<String, Any?>, composableName: String) {
    Rebugger(
        trackMap = trackMap,
        composableName = composableName
    )
}

actual fun initRebugger() {
    RebuggerConfig.init(
        tag = "Rebugger",
        logger = { tag, message -> 
            println("$tag: $message") 
        }
    )
}