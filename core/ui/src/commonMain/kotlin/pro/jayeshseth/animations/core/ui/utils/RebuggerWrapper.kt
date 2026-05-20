package pro.jayeshseth.animations.core.ui.utils

import androidx.compose.runtime.Composable

@Composable
expect fun TrackRecomposition(trackMap: Map<String, Any?>, composableName: String = "TrackRecomposition")

expect fun initRebugger()
