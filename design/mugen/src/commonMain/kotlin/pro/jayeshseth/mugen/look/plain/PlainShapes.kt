package pro.jayeshseth.mugen.look.plain

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenShapes

/**
 * Plain shape scale — a standard semantic corner-radius ladder with no bespoke morphing shapes.
 */
@Immutable
class PlainShapes(
    override val xs: CornerBasedShape = RoundedCornerShape(4.dp),
    override val sm: CornerBasedShape = RoundedCornerShape(8.dp),
    override val md: CornerBasedShape = RoundedCornerShape(12.dp),
    override val lg: CornerBasedShape = RoundedCornerShape(16.dp),
    override val xl: CornerBasedShape = RoundedCornerShape(24.dp),
    override val pill: CornerBasedShape = RoundedCornerShape(50),
) : MugenShapes
