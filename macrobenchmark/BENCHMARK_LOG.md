# Startup Benchmark Log

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