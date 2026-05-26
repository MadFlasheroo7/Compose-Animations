package pro.jayeshseth.mugen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.locals.MugenTextDefaults
import pro.jayeshseth.mugen.look.MugenLook

/**
 * Per-Look default factories for the per-component Defaults data classes. A Look's
 * tokens get the first say at what the component-shape values should be (size, padding,
 * shape); [MugenOverrides] then transforms these before they reach the locals.
 *
 * Internal — consumers override via locals or MugenOverrides, not by replacing these.
 */
internal fun defaultButtonDefaults(look: MugenLook): MugenButtonDefaults = MugenButtonDefaults(
    minHeight = 48.dp,
    minWidth = 64.dp,
    contentPadding = PaddingValues(horizontal = look.spacing.lg, vertical = look.spacing.md),
    shape = look.shapes.lg,
)

internal fun defaultCardDefaults(look: MugenLook): MugenCardDefaults = MugenCardDefaults(
    contentPadding = PaddingValues(look.spacing.lg),
    shape = look.shapes.lg,
)

internal fun defaultTextDefaults(@Suppress("unused") look: MugenLook): MugenTextDefaults =
    MugenTextDefaults()
