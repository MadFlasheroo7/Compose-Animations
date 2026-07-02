package pro.jayeshseth.mugen.look.plain

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenElevation

/** Plain elevation — standard shadow dp values, no bespoke glow or blur tokens. */
@Immutable
class PlainElevation(
    override val none: Dp = 0.dp,
    override val raised: Dp = 2.dp,
    override val floating: Dp = 8.dp,
    override val modal: Dp = 24.dp,
) : MugenElevation
