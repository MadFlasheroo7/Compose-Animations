package pro.jayeshseth.animations.defaultApis.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import pro.jayeshseth.animations.core.navigation.Route

//private fun EntryProviderBuilder<Route>.featureASection(
//    onSubRouteClick: () -> Unit,
//    onDialogClick: () -> Unit,
//    onOtherClick: () -> Unit,
//) {
//    entry<RouteA> {
//        ContentRed("Route A title") {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Button(onClick = onSubRouteClick) {
//                    Text("Go to A1")
//                }
//                Button(onClick = onDialogClick) {
//                    Text("Open dialog D")
//                }
//                Button(onClick = onOtherClick) {
//                    Text("Go to E")
//                }
//            }
//        }
//    }
//    entry<RouteA1> { ContentPink("Route A1 title") }
//    entry<RouteE> { ContentBlue("Route E title") }
//}