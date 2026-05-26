package pro.jayeshseth.mugen.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * The universal spacing scale (0, xs, sm, md, lg, xl, xxl). Looks pick their own absolute values.
 */
@Immutable
interface MugenSpacing {
    val none: Dp
    val xs: Dp
    val sm: Dp
    val md: Dp
    val lg: Dp
    val xl: Dp
    val xxl: Dp
}
