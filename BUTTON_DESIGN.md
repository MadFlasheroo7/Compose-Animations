# Mugen Button Architecture Proposal

This document outlines three architectural options for extending the Mugen component library to support new button variants and custom button components (such as a `wobblyButton`).

---

## Option 1: Component Variants via a Variant Parameter

Use a single `MugenButton` component and pass a `MugenButtonVariant` enum to the renderer.

### Implementation Outline

1. **Define the Variants:**
   ```kotlin
   enum class MugenButtonVariant {
       Filled,
       Outlined,
       Text,
       Wobbly
   }
   ```

2. **Update the Renderer Contract:**
   ```kotlin
   interface MugenButtonRenderer {
       @Composable
       fun Render(
           variant: MugenButtonVariant,
           state: MugenButtonState,
           defaults: MugenButtonDefaults,
           modifier: Modifier,
           onClick: () -> Unit,
           content: @Composable RowScope.() -> Unit,
       )
   }
   ```

3. **Drawing in Concrete Renderers:**
   Concrete implementations (e.g. `HazeButtonRenderer` or `MaterialButtonRenderer`) handle the rendering of each variant internally.

---

## Option 2: Extensible Renderer Registry (Map-based)

Introduce a generic extension map inside the main `MugenLook` interface, allowing looks to expose custom renderers without modifying the core API.

### Implementation Outline

1. **Update `MugenLook`:**
   ```kotlin
   interface MugenLook {
       // ... other tokens & renderers ...
       val extensions: Map<String, Any> get() = emptyMap()
   }
   ```

2. **Register Custom Renderers in Concrete Looks:**
   ```kotlin
   class MyCustomLook : MugenLook {
       override val extensions = mapOf(
           "wobbly_button_renderer" to MyCustomWobblyButtonRenderer(this)
       )
   }
   ```

3. **Resolve in Component:**
   ```kotlin
   @Composable
   fun MugenWobblyButton(...) {
       val activeRenderer = renderer 
           ?: (MugenTheme.look.extensions["wobbly_button_renderer"] as? MugenWobblyButtonRenderer)
           ?: DefaultWobblyButtonRenderer(MugenTheme.look)
   }
   ```

---

## Option 3: Companion CompositionLocal (Modular Extension)

Keep custom/extension buttons entirely separate from the core `MugenButton` code by defining dedicated `CompositionLocal` providers and default configs.

### Implementation Outline

1. **Define Dedicated Defaults:**
   ```kotlin
   data class MugenWobblyButtonDefaults(
       val minHeight: Dp = 48.dp,
       val renderer: MugenWobblyButtonRenderer? = null,
   )

   val LocalMugenWobblyButtonDefaults = staticCompositionLocalOf { MugenWobblyButtonDefaults() }
   ```

2. **Create a Theme Wrapper Helper:**
   ```kotlin
   @Composable
   fun MugenWobblyTheme(
       look: MugenLook = MugenTheme.look,
       content: @Composable () -> Unit
   ) {
       val wobblyDefaults = remember(look) {
           val renderer = when (look) {
               is HazeLook -> HazeWobblyButtonRenderer(look)
               is MaterialLook -> MaterialWobblyButtonRenderer(look)
               else -> DefaultWobblyButtonRenderer(look)
           }
           MugenWobblyButtonDefaults(renderer = renderer)
       }
       CompositionLocalProvider(
           LocalMugenWobblyButtonDefaults provides wobblyDefaults,
           content = content
       )
   }
   ```
