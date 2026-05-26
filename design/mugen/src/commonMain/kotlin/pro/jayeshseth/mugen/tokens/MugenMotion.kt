package pro.jayeshseth.mugen.tokens

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.runtime.Immutable

/**
 * The universal motion contract: a small palette of duration- and spring-based specs
 * named by *intent*, not by milliseconds.
 *
 * Looks may add bespoke motion specs (e.g. `HazeMotion.shimmerSweep` for the
 * signature shimmer animation, `HazeMotion.cornerMorph` for the press-time corner morph).
 */
@Immutable
interface MugenMotion {
    /** Quick, snappy reactions (~150ms). Tap feedback, hover. */
    val fast: FiniteAnimationSpec<Float>

    /** Standard transitions (~300ms). Most state changes. */
    val standard: FiniteAnimationSpec<Float>

    /** Slow, deliberate transitions (~500ms). Theme swaps, large layout reflows. */
    val slow: FiniteAnimationSpec<Float>

    /** Snappy spring with minimal overshoot. */
    val snappy: SpringSpec<Float>

    /** Playful spring with visible overshoot. */
    val bouncy: SpringSpec<Float>

    /** Subdued, slow-settling spring. */
    val gentle: SpringSpec<Float>
}
