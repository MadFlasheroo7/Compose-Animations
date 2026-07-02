package pro.jayeshseth.mugen.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.Mugen
import pro.jayeshseth.mugen.MugenTheme
import pro.jayeshseth.mugen.components.MugenButton
import pro.jayeshseth.mugen.components.MugenCard
import pro.jayeshseth.mugen.components.MugenText
import pro.jayeshseth.mugen.locals.LocalMugenButtonDefaults
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.look.material.MaterialLook
import pro.jayeshseth.mugen.look.plain.PlainLook

/**
 * The mugen playground — every mugen component, the override pathways, and the
 * bespoke-token inspector all on one scrollable screen.
 *
 * Drop this anywhere inside a [pro.jayeshseth.mugen.MugenTheme] (or use
 * `:samples:mugen-playground` as a runnable host).
 */
@Composable
fun MugenSamplePlayground(
    modifier: Modifier = Modifier,
) {
    val colors = MugenTheme.colors
    val spacing = MugenTheme.spacing
    val typography = MugenTheme.typography
    val look = MugenTheme.look

    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.lg),
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
            .verticalScroll(rememberScrollState())
            .padding(spacing.lg),
    ) {
        PlaygroundHeader(look)
        ComponentGallerySection()
        SubTreeOverrideSection()
        PerCallRendererSection()
        LookInspectorSection(look)
        MotionShowcaseSection(look)
        CapabilitiesInspectorSection()
        Spacer(Modifier.height(spacing.xl))
        MugenText(
            text = "${Mugen.NAME} ${Mugen.VERSION} · codename ${Mugen.CODENAME}",
            style = typography.labelSmall,
            color = colors.onSurfaceVariant,
        )
    }
}

@Composable
private fun PlaygroundHeader(look: MugenLook) {
    val typography = MugenTheme.typography
    val colors = MugenTheme.colors
    val spacing = MugenTheme.spacing
    Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
        MugenText("mugen playground", style = typography.displaySmall.copy(fontWeight = FontWeight.Bold))
        MugenText(
            "active look: ${look.name}",
            style = typography.titleMedium,
            color = colors.accent,
        )
    }
}

@Composable
private fun ComponentGallerySection() {
    val typography = MugenTheme.typography
    val spacing = MugenTheme.spacing
    SectionTitle("Component gallery")
    Row(horizontalArrangement = Arrangement.spacedBy(spacing.md)) {
        MugenButton(onClick = {}) { MugenText("Primary") }
        MugenButton(onClick = {}, enabled = false) { MugenText("Disabled") }
    }
    Spacer(Modifier.height(spacing.sm))
    MugenCard {
        MugenText("MugenCard", style = typography.titleMedium)
        Spacer(Modifier.height(spacing.xs))
        MugenText(
            "A surface rendered by the active look's MugenCardRenderer. Toggle the look to see this change.",
            style = typography.bodyMedium,
        )
    }
}

@Composable
private fun SubTreeOverrideSection() {
    val spacing = MugenTheme.spacing
    SectionTitle("Sub-tree defaults override (LocalMugenButtonDefaults)")
    val current = LocalMugenButtonDefaults.current
    CompositionLocalProvider(
        LocalMugenButtonDefaults provides current.copy(minHeight = 72.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(spacing.md)) {
            MugenButton(onClick = {}) { MugenText("72dp tall") }
            MugenButton(onClick = {}) { MugenText("Same") }
        }
    }
    Spacer(Modifier.height(spacing.xs))
    MugenText(
        "Above two buttons run under a sub-tree-level Defaults override. The Look default isn't touched — buttons outside this block stay at their normal size.",
        style = MugenTheme.typography.bodySmall,
        color = MugenTheme.colors.onSurfaceVariant,
    )
}

@Composable
private fun PerCallRendererSection() {
    val spacing = MugenTheme.spacing
    SectionTitle("Per-call renderer slot")
    Row(horizontalArrangement = Arrangement.spacedBy(spacing.md)) {
        MugenButton(onClick = {}) { MugenText("Look default") }
        MugenButton(
            onClick = {},
            renderer = DebugOutlineButtonRenderer,
        ) { MugenText("Custom renderer") }
    }
    Spacer(Modifier.height(spacing.xs))
    MugenText(
        "The right button overrides the renderer per-call — escape hatch for one-off visuals without touching the active Look.",
        style = MugenTheme.typography.bodySmall,
        color = MugenTheme.colors.onSurfaceVariant,
    )
}

@Composable
private fun LookInspectorSection(look: MugenLook) {
    val spacing = MugenTheme.spacing
    val typography = MugenTheme.typography
    val colors = MugenTheme.colors

    SectionTitle("Bespoke token inspector")
    MugenCard {
        MugenText("base tokens", style = typography.titleSmall)
        Spacer(Modifier.height(spacing.xs))
        ColorSwatchRow("primary", colors.primary)
        ColorSwatchRow("accent", colors.accent)
        ColorSwatchRow("surface", colors.surface)
        ColorSwatchRow("danger", colors.danger)
    }
    Spacer(Modifier.height(spacing.sm))

    when (look) {
        is PlainLook -> MugenCard {
            MugenText("PlainLook tokens", style = typography.titleSmall)
            Spacer(Modifier.height(spacing.xs))
            MugenText("PlainLook has zero bespoke tokens — it is pure core.", style = typography.bodyMedium)
        }
        is HazeLook -> MugenCard {
            MugenText("HazeLook bespoke tokens", style = typography.titleSmall)
            Spacer(Modifier.height(spacing.xs))
            ColorSwatchRow("colors.glow", look.colors.glow)
            ColorSwatchRow("colors.shimmer", look.colors.shimmer)
            ColorSwatchRow("colors.hazeTint", look.colors.hazeTint)
            ColorSwatchRow("colors.innerShadow", look.colors.innerShadow)
            Spacer(Modifier.height(spacing.xs))
            MugenText(
                "shapes.interactiveMorphRest = ${look.shapes.interactiveMorphRest}",
                style = typography.bodySmall,
            )
            MugenText(
                "shapes.interactiveMorphActive = ${look.shapes.interactiveMorphActive}",
                style = typography.bodySmall,
            )
            MugenText(
                "elevation.glowSpread = ${look.elevation.glowSpread}",
                style = typography.bodySmall,
            )
            MugenText(
                "elevation.hazeBlur = ${look.elevation.hazeBlur}",
                style = typography.bodySmall,
            )
        }
        is MaterialLook -> MugenCard {
            MugenText("MaterialLook bespoke tokens", style = typography.titleSmall)
            Spacer(Modifier.height(spacing.xs))
            ColorSwatchRow("colors.tonalSurface", look.colors.tonalSurface)
            ColorSwatchRow("colors.surfaceTint", look.colors.surfaceTint)
            Spacer(Modifier.height(spacing.xs))
            MugenText(
                "elevation.tonalFloating = ${look.elevation.tonalFloating}",
                style = typography.bodySmall,
            )
        }
        else -> MugenCard {
            MugenText("Custom Look: ${look.name}", style = typography.titleSmall)
            Spacer(Modifier.height(spacing.xs))
            MugenText(
                "Implement the inspector branch for your own Look to surface its bespoke tokens here.",
                style = typography.bodySmall,
            )
        }
    }
}

@Composable
private fun MotionShowcaseSection(look: MugenLook) {
    val spacing = MugenTheme.spacing
    val typography = MugenTheme.typography
    val colors = MugenTheme.colors

    SectionTitle("Motion Showcase")

    var targetSnappyState by remember { mutableStateOf(false) }
    var targetBouncyState by remember { mutableStateOf(false) }
    var targetGentleState by remember { mutableStateOf(false) }

    val snappyOffset by animateFloatAsState(
        targetValue = if (targetSnappyState) 120f else 0f,
        animationSpec = MugenTheme.motion.snappy
    )
    val bouncyOffset by animateFloatAsState(
        targetValue = if (targetBouncyState) 120f else 0f,
        animationSpec = MugenTheme.motion.bouncy
    )
    val gentleOffset by animateFloatAsState(
        targetValue = if (targetGentleState) 120f else 0f,
        animationSpec = MugenTheme.motion.gentle
    )

    MugenCard {
        MugenText("Interactive Springs (Click to animate)", style = typography.titleSmall)
        Spacer(Modifier.height(spacing.md))

        // Snappy Spring Demo
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            MugenText("Snappy Spring", style = typography.labelMedium, color = colors.onSurfaceVariant)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colors.surfaceVariant, MugenTheme.shapes.sm)
                    .clickable { targetSnappyState = !targetSnappyState }
                    .padding(horizontal = spacing.sm),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    Modifier
                        .size(32.dp)
                        .padding(start = snappyOffset.dp) // using animation state
                        .clip(CircleShape)
                        .background(colors.accent)
                )
            }
        }

        Spacer(Modifier.height(spacing.md))

        // Bouncy Spring Demo
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            MugenText("Bouncy Spring", style = typography.labelMedium, color = colors.onSurfaceVariant)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colors.surfaceVariant, MugenTheme.shapes.sm)
                    .clickable { targetBouncyState = !targetBouncyState }
                    .padding(horizontal = spacing.sm),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    Modifier
                        .size(32.dp)
                        .padding(start = bouncyOffset.dp)
                        .clip(CircleShape)
                        .background(colors.primary)
                )
            }
        }

        Spacer(Modifier.height(spacing.md))

        // Gentle Spring Demo
        Column(verticalArrangement = Arrangement.spacedBy(spacing.xs)) {
            MugenText("Gentle Spring", style = typography.labelMedium, color = colors.onSurfaceVariant)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colors.surfaceVariant, MugenTheme.shapes.sm)
                    .clickable { targetGentleState = !targetGentleState }
                    .padding(horizontal = spacing.sm),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    Modifier
                        .size(32.dp)
                        .padding(start = gentleOffset.dp)
                        .clip(CircleShape)
                        .background(colors.outline)
                )
            }
        }
    }
}

@Composable
private fun CapabilitiesInspectorSection() {
    val spacing = MugenTheme.spacing
    val typography = MugenTheme.typography
    val colors = MugenTheme.colors
    val caps = MugenTheme.capabilities

    SectionTitle("Platform Capabilities")

    MugenCard {
        MugenText("Target Fingerprint Check", style = typography.titleSmall)
        Spacer(Modifier.height(spacing.md))

        CapabilityRow("supportsRuntimeShaders (AGSL)", caps.supportsRuntimeShaders)
        CapabilityRow("supportsBlur (RenderEffect)", caps.supportsBlur)
        CapabilityRow("supportsInnerShadow", caps.supportsInnerShadow)
        CapabilityRow("supportsHaze", caps.supportsHaze)
        
        Spacer(Modifier.height(spacing.xs))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            MugenText("deviceCornerRadius", style = typography.bodyMedium)
            MugenText(
                text = "${caps.deviceCornerRadius.value} dp",
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = colors.accent
            )
        }
    }
}

@Composable
private fun CapabilityRow(label: String, supported: Boolean) {
    val spacing = MugenTheme.spacing
    val typography = MugenTheme.typography
    val colors = MugenTheme.colors

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(vertical = spacing.xs)
    ) {
        MugenText(label, style = typography.bodyMedium)
        MugenText(
            text = if (supported) "SUPPORTED" else "UNSUPPORTED",
            style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = if (supported) colors.success else colors.danger
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    val typography = MugenTheme.typography
    val colors = MugenTheme.colors
    val spacing = MugenTheme.spacing
    Spacer(Modifier.height(spacing.xs))
    MugenText(text, style = typography.titleLarge, color = colors.onSurface)
}

@Composable
private fun ColorSwatchRow(label: String, color: Color) {
    val typography = MugenTheme.typography
    val spacing = MugenTheme.spacing
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        Box(
            Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(color),
        )
        MugenText(label, style = typography.bodyMedium)
        Spacer(Modifier.width(spacing.sm))
        MugenText(
            color.toHexString(),
            style = typography.bodySmall,
            color = MugenTheme.colors.onSurfaceVariant,
        )
    }
}

private fun Color.toHexString(): String {
    val argb = (alpha * 255).toInt().shl(24) or
        (red * 255).toInt().shl(16) or
        (green * 255).toInt().shl(8) or
        (blue * 255).toInt()
    val hex = argb.toUInt().toString(16).padStart(8, '0')
    return "#${hex.uppercase()}"
}
