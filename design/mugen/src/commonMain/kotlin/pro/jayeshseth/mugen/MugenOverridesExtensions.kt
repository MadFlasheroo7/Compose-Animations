package pro.jayeshseth.mugen

import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.locals.MugenTextDefaults

/** Transform the per-Look button defaults in [MugenOverrides.Builder]. */
fun MugenOverrides.Builder.button(transform: (MugenButtonDefaults) -> MugenButtonDefaults) {
    override(transform)
}

/** Transform the per-Look card defaults in [MugenOverrides.Builder]. */
fun MugenOverrides.Builder.card(transform: (MugenCardDefaults) -> MugenCardDefaults) {
    override(transform)
}

/** Transform the per-Look text defaults in [MugenOverrides.Builder]. */
fun MugenOverrides.Builder.text(transform: (MugenTextDefaults) -> MugenTextDefaults) {
    override(transform)
}
