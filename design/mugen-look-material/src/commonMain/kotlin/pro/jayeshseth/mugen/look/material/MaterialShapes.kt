package pro.jayeshseth.mugen.look.material

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenShapes

@Immutable
class MaterialShapes(
    val CircleShape: Shape = RoundedCornerShape(50.dp),
    override val xs: CornerBasedShape = RoundedCornerShape(4.dp),
    override val sm: CornerBasedShape = RoundedCornerShape(8.dp),
    override val md: CornerBasedShape = RoundedCornerShape(12.dp),
    override val lg: CornerBasedShape = RoundedCornerShape(16.dp),
    override val xl: CornerBasedShape = RoundedCornerShape(28.dp),
    override val pill: CornerBasedShape = RoundedCornerShape(100.dp),
) : MugenShapes
