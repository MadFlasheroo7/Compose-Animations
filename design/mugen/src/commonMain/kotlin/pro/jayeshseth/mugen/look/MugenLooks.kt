package pro.jayeshseth.mugen.look

import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.look.material.MaterialLook

/**
 * Registry of built-in mugen Looks. Use `MugenLooks.Haze` / `MugenLooks.Material`
 * for quick access; instantiate directly (`HazeLook(...)`, `MaterialLook(...)`) when you
 * need to override token defaults.
 */
object MugenLooks {
    val Haze: MugenLook get() = HazeLook()
    val Material: MugenLook get() = MaterialLook()

    /** All built-in looks, in display order. Useful for sample / playground pickers. */
    val builtIn: List<MugenLook>
        get() = listOf(Haze, Material)
}
