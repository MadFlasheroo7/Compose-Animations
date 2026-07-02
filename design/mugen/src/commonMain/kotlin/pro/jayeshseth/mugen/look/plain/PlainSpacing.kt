package pro.jayeshseth.mugen.look.plain

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenSpacing

/** Plain spacing scale — standard 4dp-grid rhythm. */
@Immutable
class PlainSpacing(
    override val none: Dp = 0.dp,
    override val xs: Dp = 4.dp,
    override val sm: Dp = 8.dp,
    override val md: Dp = 12.dp,
    override val lg: Dp = 16.dp,
    override val xl: Dp = 24.dp,
    override val xxl: Dp = 32.dp,
) : MugenSpacing
