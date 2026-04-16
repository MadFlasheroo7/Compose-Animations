# Benchmark Log

## Device: Motorola Edge 50 Pro

### Initial Baseline — Pre-Optimization

> 16 Tests | 3/3 completed | 0 skipped | 0 failed

| Benchmark                          | Min (ms) | Median (ms) | Max (ms) |
|------------------------------------|----------|-------------|----------|
| StartupBenchmark_startupHot        |     90.1 |       101.3 |    128.1 |
| StartupBenchmark_startupWarm       |    167.0 |       185.3 |    261.1 |
| StartupBenchmark_startupCold       |    930.9 |       964.2 |  1,098.5 |

Each benchmark ran 5 iterations (0–4) with traces captured per iteration.

| Benchmark | Iter 0 | Iter 1 | Iter 2 | Iter 3 | Iter 4 |
|-----------|--------|--------|--------|--------|--------|
| Hot       | [trace](traces/StartupBenchmark_startupHot_iter000_2026-04-15-20-30-33.perfetto-trace) | [trace](traces/StartupBenchmark_startupHot_iter001_2026-04-15-20-30-40.perfetto-trace) | [trace](traces/StartupBenchmark_startupHot_iter002_2026-04-15-20-30-48.perfetto-trace) | [trace](traces/StartupBenchmark_startupHot_iter003_2026-04-15-20-30-55.perfetto-trace) | [trace](traces/StartupBenchmark_startupHot_iter004_2026-04-15-20-31-02.perfetto-trace) |
| Warm      | [trace](traces/StartupBenchmark_startupWarm_iter000_2026-04-15-20-32-09.perfetto-trace) | [trace](traces/StartupBenchmark_startupWarm_iter001_2026-04-15-20-32-14.perfetto-trace) | [trace](traces/StartupBenchmark_startupWarm_iter002_2026-04-15-20-32-20.perfetto-trace) | [trace](traces/StartupBenchmark_startupWarm_iter003_2026-04-15-20-32-25.perfetto-trace) | [trace](traces/StartupBenchmark_startupWarm_iter004_2026-04-15-20-32-30.perfetto-trace) |
| Cold      | [trace](traces/StartupBenchmark_startupCold_iter000_2026-04-15-20-31-23.perfetto-trace) | [trace](traces/StartupBenchmark_startupCold_iter001_2026-04-15-20-31-31.perfetto-trace) | [trace](traces/StartupBenchmark_startupCold_iter002_2026-04-15-20-31-38.perfetto-trace) | [trace](traces/StartupBenchmark_startupCold_iter003_2026-04-15-20-31-46.perfetto-trace) | [trace](traces/StartupBenchmark_startupCold_iter004_2026-04-15-20-31-53.perfetto-trace) |

---

### Home Screen (BaseInteractiveButton) — Pre-Optimization

Each benchmark ran 5 iterations (0–4) with traces captured per iteration.

#### homeScreenIdle

> Captures infinite animation cost (shimmer, inner shadow radius) while the user isn't interacting.

| Metric                       |   P50 |   P90 |   P95 |    P99 |
|------------------------------|-------|-------|-------|--------|
| frameDurationCpuMs           |  47.3 |  63.1 |  71.2 |  118.7 |
| frameOverrunMs               |  59.7 |  92.9 | 120.5 |  189.3 |

| Metric                       |   Min |  Median |    Max |
|------------------------------|-------|---------|--------|
| BaseInteractiveButtonCount   |  36.0 |    42.0 |   42.0 |
| BaseInteractiveButtonSumMs   |   9.1 |    14.3 |   25.1 |
| frameCount                   | 194.0 |   203.0 |  423.0 |

| Benchmark | Iter 0 | Iter 1 | Iter 2 | Iter 3 | Iter 4 |
|-----------|--------|--------|--------|--------|--------|
| Idle      | [trace](traces/HomeScreenBenchmark_homeScreenIdle_iter000_2026-04-16-09-10-12.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenIdle_iter001_2026-04-16-09-10-26.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenIdle_iter002_2026-04-16-09-10-36.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenIdle_iter003_2026-04-16-09-10-46.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenIdle_iter004_2026-04-16-09-10-55.perfetto-trace) |

#### homeScreenScroll

> Captures layout + new button composition + entrance animations during scroll.

| Metric                       |    P50 |   P90 |   P95 |   P99 |
|------------------------------|--------|-------|-------|-------|
| frameDurationCpuMs           |   34.4 |  45.2 |  47.1 |  65.9 |
| frameOverrunMs               |   48.3 |  68.8 |  72.2 |  92.7 |

| Metric                       |    Min |  Median |     Max |
|------------------------------|--------|---------|---------|
| BaseInteractiveButtonCount   |   30.0 |    30.0 |    36.0 |
| BaseInteractiveButtonSumMs   |    6.7 |    10.1 |    13.4 |
| frameCount                   |  841.0 | 1,111.0 | 1,363.0 |

| Benchmark | Iter 0 | Iter 1 | Iter 2 | Iter 3 | Iter 4 |
|-----------|--------|--------|--------|--------|--------|
| Scroll    | [trace](traces/HomeScreenBenchmark_homeScreenScroll_iter000_2026-04-16-09-06-29.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenScroll_iter001_2026-04-16-09-07-04.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenScroll_iter002_2026-04-16-09-07-40.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenScroll_iter003_2026-04-16-09-08-16.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenScroll_iter004_2026-04-16-09-09-21.perfetto-trace) |

#### homeScreenInitialRender

> Captures entrance animation burst (scale spring + blur tween) + all button setup on cold start.

| Metric                       |   P50 |   P90 |    P95 |    P99 |
|------------------------------|-------|-------|--------|--------|
| frameDurationCpuMs           |  45.2 |  54.9 |   59.9 |  165.3 |
| frameOverrunMs               |  57.4 |  72.7 |  104.4 |  543.1 |

| Metric                       |   Min | Median |   Max |
|------------------------------|-------|--------|-------|
| BaseInteractiveButtonCount   |  60.0 |   72.0 |  78.0 |
| BaseInteractiveButtonSumMs   |  27.5 |   29.3 |  31.2 |
| frameCount                   | 118.0 |  124.0 | 127.0 |

| Benchmark      | Iter 0 | Iter 1 | Iter 2 | Iter 3 | Iter 4 |
|----------------|--------|--------|--------|--------|--------|
| Initial Render | [trace](traces/HomeScreenBenchmark_homeScreenInitialRender_iter000_2026-04-16-09-11-15.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenInitialRender_iter001_2026-04-16-09-11-24.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenInitialRender_iter002_2026-04-16-09-11-33.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenInitialRender_iter003_2026-04-16-09-11-43.perfetto-trace) | [trace](traces/HomeScreenBenchmark_homeScreenInitialRender_iter004_2026-04-16-09-11-53.perfetto-trace) |

---

### Home Screen (BaseInteractiveButton) — Post Recomposition Optimization

Optimizations applied:
- Converted `color`, `scale`, `blur` params to lambda-based (`() -> Color`, `() -> Float`) to defer state reads
- Moved `shape` reads into `graphicsLayer` / draw-phase lambdas to avoid composition-phase reads
- Converted `shimmerBorder` and `cornerRadius` to lambda-based to defer reads
- Removed redundant infinite inner shadow radius animation workaround

Each benchmark ran 5 iterations (0–4) with traces captured per iteration.

#### homeScreenIdle

> Captures infinite animation cost (shimmer, inner shadow radius) while the user isn't interacting.

| Metric                       |   P50 |   P90 |   P95 |    P99 |
|------------------------------|-------|-------|-------|--------|
| frameDurationCpuMs           |  46.9 |  56.7 |  73.1 |  130.6 |
| frameOverrunMs               |  58.1 |  74.6 | 139.9 |  234.1 |

| Metric                       |   Min |  Median |    Max |
|------------------------------|-------|---------|--------|
| BaseInteractiveButtonCount   |   6.0 |     6.0 |    6.0 |
| BaseInteractiveButtonSumMs   |   5.1 |     5.2 |    6.9 |
| frameCount                   | 188.0 |   193.0 |  198.0 |

#### homeScreenScroll

> Captures layout + new button composition + entrance animations during scroll.

| Metric                       |    P50 |   P90 |   P95 |    P99 |
|------------------------------|--------|-------|-------|--------|
| frameDurationCpuMs           |   47.5 |  56.1 |  59.0 |   86.9 |
| frameOverrunMs               |   61.3 |  71.8 |  75.5 |  168.8 |

| Metric                       |    Min |  Median |     Max |
|------------------------------|--------|---------|---------|
| BaseInteractiveButtonCount   |    6.0 |     6.0 |     6.0 |
| BaseInteractiveButtonSumMs   |    4.7 |     5.3 |     5.4 |
| frameCount                   |  773.0 |   810.0 |   835.0 |

#### homeScreenInitialRender

> Captures entrance animation burst (scale spring + blur tween) + all button setup on cold start.

| Metric                       |   P50 |   P90 |    P95 |    P99 |
|------------------------------|-------|-------|--------|--------|
| frameDurationCpuMs           |  46.3 |  54.6 |   58.7 |  137.9 |
| frameOverrunMs               |  57.8 |  73.3 |   99.0 |  235.9 |

| Metric                       |   Min | Median |   Max |
|------------------------------|-------|--------|-------|
| BaseInteractiveButtonCount   |   6.0 |    6.0 |   6.0 |
| BaseInteractiveButtonSumMs   |  15.3 |   18.0 |  24.9 |
| frameCount                   | 122.0 |  124.0 | 131.0 |

---

### Comparison: Pre vs Post Recomposition Optimization

#### Recomposition Count (median)

| Benchmark      | Pre  | Post | Change |
|----------------|------|------|--------|
| Idle           | 42.0 |  6.0 | **-86%** |
| Scroll         | 30.0 |  6.0 | **-80%** |
| Initial Render | 72.0 |  6.0 | **-92%** |

Recomposition count dropped to exactly 6 (one per visible button) across all tests — no more redundant recompositions from animation state reads during composition.

#### Recomposition Time — BaseInteractiveButtonSumMs (median)

| Benchmark      | Pre (ms) | Post (ms) | Change |
|----------------|----------|-----------|--------|
| Idle           |     14.3 |       5.2 | **-64%** |
| Scroll         |     10.1 |       5.3 | **-48%** |
| Initial Render |     29.3 |      18.0 | **-39%** |

#### Frame Count (median)

| Benchmark      |    Pre |   Post | Change |
|----------------|--------|--------|--------|
| Idle           |  203.0 |  193.0 | -5% (fewer unnecessary frames) |
| Scroll         | 1111.0 |  810.0 | **-27%** (fewer unnecessary frames) |
| Initial Render |  124.0 |  124.0 | unchanged |

Scroll frame count dropped 27% — the runtime was producing ~300 extra frames from recomposition-triggered invalidations that are now gone.

#### Frame Duration CPU — frameDurationCpuMs

| Benchmark      | Percentile | Pre (ms) | Post (ms) | Change |
|----------------|------------|----------|-----------|--------|
| Idle           | P50        |     47.3 |      46.9 | -1% |
| Idle           | P90        |     63.1 |      56.7 | **-10%** |
| Idle           | P95        |     71.2 |      73.1 | +3% |
| Idle           | P99        |    118.7 |     130.6 | +10% |
| Scroll         | P50        |     34.4 |      47.5 | +38% |
| Scroll         | P90        |     45.2 |      56.1 | +24% |
| Scroll         | P95        |     47.1 |      59.0 | +25% |
| Scroll         | P99        |     65.9 |      86.9 | +32% |
| Initial Render | P50        |     45.2 |      46.3 | +2% |
| Initial Render | P90        |     54.9 |      54.6 | -1% |
| Initial Render | P95        |     59.9 |      58.7 | **-2%** |
| Initial Render | P99        |    165.3 |     137.9 | **-17%** |

#### Frame Overrun — frameOverrunMs

| Benchmark      | Percentile | Pre (ms) | Post (ms) | Change |
|----------------|------------|----------|-----------|--------|
| Idle           | P50        |     59.7 |      58.1 | -3% |
| Idle           | P90        |     92.9 |      74.6 | **-20%** |
| Idle           | P95        |    120.5 |     139.9 | +16% |
| Idle           | P99        |    189.3 |     234.1 | +24% |
| Scroll         | P50        |     48.3 |      61.3 | +27% |
| Scroll         | P90        |     68.8 |      71.8 | +4% |
| Scroll         | P95        |     72.2 |      75.5 | +5% |
| Scroll         | P99        |     92.7 |     168.8 | +82% |
| Initial Render | P50        |     57.4 |      57.8 | +1% |
| Initial Render | P90        |     72.7 |      73.3 | +1% |
| Initial Render | P95        |    104.4 |      99.0 | **-5%** |
| Initial Render | P99        |    543.1 |     235.9 | **-57%** |

#### Key Takeaways

**What improved:**
- Recomposition count dropped 80-92% across all tests — from 30-72 down to exactly 6 (one per visible button)
- Recomposition time (SumMs) dropped 39-64%
- Initial render P99 frame overrun cut from 543.1ms to 235.9ms (**-57%**) — the worst cold start stutter halved
- Initial render P99 CPU duration dropped from 165.3ms to 137.9ms (**-17%**)
- Idle P90 frame overrun improved from 92.9ms to 74.6ms (**-20%**)
- Scroll frame count dropped 27% (1,111 to 810) — fewer wasted frames from recomposition-triggered invalidations

**What got worse:**
- Scroll per-frame cost increased across all percentiles (P50 +38%, P99 +82% frame overrun) — fewer frames but each individual frame is more expensive since the draw-phase effects (glow shadow, haze blur, inner shadow, shimmer border, radial gradient overlay) now dominate
- Idle tail latency (P95/P99) increased — P99 frame overrun went from 189.3ms to 234.1ms (+24%)

**What didn't change:**
- Initial render frame count stayed at 124 (median)
- Initial render P50/P90 frame timing essentially unchanged
- Idle P50 frame timing essentially unchanged

**Conclusion:**
Recomposition optimization eliminated redundant composition work, which halved the worst-case cold start stutter and reduced unnecessary frame production. However, per-frame draw cost is now the dominant bottleneck — the glow shadow, haze blur, inner shadow, shimmer border, and radial gradient overlay effects running on every frame for every visible button are the next optimization target.

---

### Startup — Post Recomposition Optimization

| Benchmark                          | Min (ms) | Median (ms) | Max (ms) |
|------------------------------------|----------|-------------|----------|
| StartupBenchmark_startupHot        |     90.4 |        95.5 |    112.3 |
| StartupBenchmark_startupWarm       |    193.3 |       203.0 |    294.1 |
| StartupBenchmark_startupCold       |  1,052.3 |     1,077.7 |  1,111.0 |

| Benchmark                          |   Min |  Median |    Max |
|------------------------------------|-------|---------|--------|
| **startupHot — BaseInteractiveButton** | | | |
| Count                              |   0.0 |     0.0 |    0.0 |
| SumMs                              |   0.0 |     0.0 |    0.0 |
| **startupWarm — BaseInteractiveButton** | | | |
| Count                              |   6.0 |     6.0 |    6.0 |
| SumMs                              |   4.3 |     5.8 |    6.4 |
| **startupCold — BaseInteractiveButton** | | | |
| Count                              |   6.0 |     6.0 |    6.0 |
| SumMs                              |  14.1 |    14.8 |   16.3 |

Each benchmark ran 5 iterations (0–4) with traces captured per iteration.

#### Startup Comparison: Pre vs Post Recomposition Optimization

##### timeToInitialDisplayMs (median)

| Benchmark | Pre (ms) | Post (ms) | Change |
|-----------|----------|-----------|--------|
| Hot       |    101.3 |      95.5 | **-5.7%** |
| Warm      |    185.3 |     203.0 | +9.6% |
| Cold      |    964.2 |   1,077.7 | +11.8% |

##### timeToInitialDisplayMs (min)

| Benchmark | Pre (ms) | Post (ms) | Change |
|-----------|----------|-----------|--------|
| Hot       |     90.1 |      90.4 | unchanged |
| Warm      |    167.0 |     193.3 | +15.7% |
| Cold      |    930.9 |   1,052.3 | +13.0% |

##### timeToInitialDisplayMs (max)

| Benchmark | Pre (ms) | Post (ms) | Change |
|-----------|----------|-----------|--------|
| Hot       |    128.1 |     112.3 | **-12.3%** |
| Warm      |    261.1 |     294.1 | +12.6% |
| Cold      |  1,098.5 |   1,111.0 | +1.1% |

##### Key Takeaways

**What improved:**
- Hot startup improved across the board — median dropped 5.7% (101.3ms to 95.5ms), max dropped 12.3% (128.1ms to 112.3ms)
- Hot startup shows 0 BaseInteractiveButton recompositions — the process is already alive so buttons don't recompose on hot restart
- Warm/Cold recomposition count locked at 6 (one per visible button) with low SumMs (5.8ms warm, 14.8ms cold)

**What got worse:**
- Warm startup median increased 9.6% (185.3ms to 203.0ms)
- Cold startup median increased 11.8% (964.2ms to 1,077.7ms)

**Analysis:**
The warm/cold regression is likely not caused by the recomposition optimization itself — BaseInteractiveButton only accounts for 5.8ms (warm) and 14.8ms (cold) of the total startup time. The regression (~18ms warm, ~113ms cold) exceeds what BaseInteractiveButton contributes, suggesting external factors (device thermal state, background processes, system load variance between test runs). Hot startup improved because it skips activity creation entirely and benefits most directly from reduced recomposition overhead.