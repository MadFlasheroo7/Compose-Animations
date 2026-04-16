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