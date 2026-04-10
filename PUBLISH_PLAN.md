# Plan: Publish CMP Web Builds + Fix OOM + Debug/Prod Build Guide

## Context
The project is a Compose Multiplatform app targeting Android, iOS, Desktop, and Web (JS + WASM). The goals are:
1. Fix `OutOfMemoryError: Java heap space` when running WASM distribution build
2. Deploy WASM to `animations.jayeshseth.pro` and JS to `js.animations.jayeshseth.pro`
3. Understand debug vs production build commands for all platforms

---

## Step 1: Fix OutOfMemoryError for WASM Distribution

**File:** `gradle.properties` (lines 9, 20)

- Increase Gradle daemon heap: `-Xmx4096M` → `-Xmx8192M` (line 9)
- Increase Kotlin daemon heap: `-Xmx4096M` → `-Xmx8192M` (line 20)
- Enable parallel builds: uncomment `org.gradle.parallel=true` (line 13)

**Why 8GB:** WASM production builds run Binaryen optimization which is very memory-intensive. 4GB is insufficient. 8GB works for local dev; CI will use `GRADLE_OPTS=-Xmx6g` to fit within GitHub Actions runner limits (~7GB RAM).

---

## Step 2: GitHub Actions Workflow for Web Deployment

GitHub Pages only supports one site per repo, so both approaches are provided:

### Approach A: GitHub Pages (path-based, single workflow)

WASM at root (`/`) → `animations.jayeshseth.pro`
JS at `/js/` path → `js.animations.jayeshseth.pro` redirects here

**New file:** `.github/workflows/deploy-web-ghpages.yml`

```yaml
name: Deploy Web (GitHub Pages)

on:
  push:
    branches: [main]
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

concurrency:
  group: "pages"
  cancel-in-progress: true

jobs:
  build-wasm:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: gradle/actions/setup-gradle@v4
      - name: Build WASM distribution
        run: ./gradlew :composeApp:wasmJsBrowserDistribution
        env:
          GRADLE_OPTS: "-Xmx6g"
      - uses: actions/upload-artifact@v4
        with:
          name: wasm-dist
          path: composeApp/build/dist/wasmJs/productionExecutable/

  build-js:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: gradle/actions/setup-gradle@v4
      - name: Build JS distribution
        run: ./gradlew :composeApp:jsBrowserDistribution
        env:
          GRADLE_OPTS: "-Xmx6g"
      - uses: actions/upload-artifact@v4
        with:
          name: js-dist
          path: composeApp/build/dist/js/productionExecutable/

  deploy:
    needs: [build-wasm, build-js]
    runs-on: ubuntu-latest
    environment:
      name: github-pages
      url: ${{ steps.deployment.outputs.page_url }}
    steps:
      - uses: actions/download-artifact@v4
        with:
          name: wasm-dist
          path: site/
      - uses: actions/download-artifact@v4
        with:
          name: js-dist
          path: site/js/
      - name: Add CNAME
        run: echo "animations.jayeshseth.pro" > site/CNAME
      - uses: actions/configure-pages@v5
      - uses: actions/upload-pages-artifact@v3
        with:
          path: site/
      - id: deployment
        uses: actions/deploy-pages@v4
```

**Manual setup required:**
- DNS: `animations.jayeshseth.pro` → CNAME to `<username>.github.io`
- DNS: `js.animations.jayeshseth.pro` → CNAME/redirect to `animations.jayeshseth.pro/js/`
- Repo Settings > Pages > Source: "GitHub Actions"
- Repo Settings > Pages > Custom domain: `animations.jayeshseth.pro`

---

### Approach B: Cloudflare Pages (true subdomain isolation)

Each subdomain gets its own Cloudflare Pages project with independent deployments.

**New file:** `.github/workflows/deploy-web-cloudflare.yml`

```yaml
name: Deploy Web (Cloudflare Pages)

on:
  push:
    branches: [main]
  workflow_dispatch:

jobs:
  deploy-wasm:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: gradle/actions/setup-gradle@v4
      - name: Build WASM distribution
        run: ./gradlew :composeApp:wasmJsBrowserDistribution
        env:
          GRADLE_OPTS: "-Xmx6g"
      - name: Deploy WASM to Cloudflare Pages
        uses: cloudflare/wrangler-action@v3
        with:
          apiToken: ${{ secrets.CLOUDFLARE_API_TOKEN }}
          accountId: ${{ secrets.CLOUDFLARE_ACCOUNT_ID }}
          command: pages deploy composeApp/build/dist/wasmJs/productionExecutable --project-name=compose-animations-wasm

  deploy-js:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - uses: gradle/actions/setup-gradle@v4
      - name: Build JS distribution
        run: ./gradlew :composeApp:jsBrowserDistribution
        env:
          GRADLE_OPTS: "-Xmx6g"
      - name: Deploy JS to Cloudflare Pages
        uses: cloudflare/wrangler-action@v3
        with:
          apiToken: ${{ secrets.CLOUDFLARE_API_TOKEN }}
          accountId: ${{ secrets.CLOUDFLARE_ACCOUNT_ID }}
          command: pages deploy composeApp/build/dist/js/productionExecutable --project-name=compose-animations-js
```

**Manual setup required:**
- Cloudflare account with two Pages projects: `compose-animations-wasm` and `compose-animations-js`
- Add GitHub secrets: `CLOUDFLARE_API_TOKEN`, `CLOUDFLARE_ACCOUNT_ID`
- Cloudflare DNS: `animations.jayeshseth.pro` → Pages project `compose-animations-wasm`
- Cloudflare DNS: `js.animations.jayeshseth.pro` → Pages project `compose-animations-js`
- Custom domains configured in each Cloudflare Pages project settings

---

## Step 3: Debug vs Production Build Reference

No code changes needed — these are the existing Gradle tasks for each platform.

### Android (already configured with debug/release in `composeApp/build.gradle.kts:9-23`)
| Mode | Command |
|------|---------|
| Debug APK | `./gradlew :composeApp:assembleDebug` |
| Release APK | `./gradlew :composeApp:assembleRelease` |
| Install Debug | `./gradlew :composeApp:installDebug` |
| Debug Bundle | `./gradlew :composeApp:bundleDebug` |
| Release Bundle | `./gradlew :composeApp:bundleRelease` |

Release already has R8/ProGuard enabled.

### iOS (driven by Xcode)
| Mode | Mechanism |
|------|-----------|
| Debug | Xcode > Run (Debug scheme) |
| Release | Xcode > Archive (Release scheme) |

### Desktop (`composeApp/build.gradle.kts:79-91`)
| Mode | Command |
|------|---------|
| Debug run | `./gradlew :composeApp:run` |
| Package DMG | `./gradlew :composeApp:packageDmg` |
| Package MSI | `./gradlew :composeApp:packageMsi` |
| Package Deb | `./gradlew :composeApp:packageDeb` |
| All formats | `./gradlew :composeApp:createDistributable` |

### Web WASM
| Mode | Command | Output |
|------|---------|--------|
| Dev server (hot-reload) | `./gradlew :composeApp:wasmJsBrowserDevelopmentRun` | localhost |
| Dev build | `./gradlew :composeApp:wasmJsBrowserDevelopmentWebpack` | `build/dist/wasmJs/developmentExecutable/` |
| Prod build | `./gradlew :composeApp:wasmJsBrowserDistribution` | `build/dist/wasmJs/productionExecutable/` |

### Web JS
| Mode | Command | Output |
|------|---------|--------|
| Dev server (hot-reload) | `./gradlew :composeApp:jsBrowserDevelopmentRun` | localhost |
| Dev build | `./gradlew :composeApp:jsBrowserDevelopmentWebpack` | `build/dist/js/developmentExecutable/` |
| Prod build | `./gradlew :composeApp:jsBrowserDistribution` | `build/dist/js/productionExecutable/` |

Production web builds include webpack minification, tree-shaking, and (for WASM) Binaryen optimization.

---

## Files to Modify/Create

| File | Action |
|------|--------|
| `gradle.properties` | Edit: increase heap to 8GB, enable parallel |
| `.github/workflows/deploy-web-ghpages.yml` | Create: GitHub Pages workflow (Approach A) |
| `.github/workflows/deploy-web-cloudflare.yml` | Create: Cloudflare Pages workflow (Approach B) |

---

## Verification

1. **OOM fix:** Run `./gradlew :composeApp:wasmJsBrowserDistribution` locally — should complete without OOM
2. **JS build:** Run `./gradlew :composeApp:jsBrowserDistribution` — verify output in `build/dist/js/productionExecutable/`
3. **Dev servers:** Run `wasmJsBrowserDevelopmentRun` and `jsBrowserDevelopmentRun` to test debug builds
4. **CI:** Push to main, verify GitHub Actions workflow runs both builds and deploys