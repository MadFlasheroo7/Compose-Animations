#!/usr/bin/env bash
#
# Build → install → launch the Android app on a connected device.
# Interactive selector if run with no args, or pass flags for non-interactive runs.
#
# Usage:
#   ./scripts/install.sh                              # interactive
#   ./scripts/install.sh debug                        # quick: debug variant, default options
#   ./scripts/install.sh release --clean --logcat     # release + clear data + tail logs
#
# Variants:
#   debug      installed as pro.jayeshseth.animations.debug (label: Animations-beta)
#   release    signed + R8-minified production build
#   benchmark  release-like but unminified, signed with debug key (for profiling)
#
# Options (combine freely):
#   --clean              clear app data (pm clear) before launch
#   --uninstall          uninstall existing app first (fixes signature mismatch)
#   --cold               force-stop before launch — true cold start
#   --time               print startup timings (am start -W: TotalTime, WaitTime)
#   --logcat             stream filtered logcat after launch (Ctrl-C to stop)
#   --compile-profile    apply speed-profile ART compilation after install
#                        (mimics what users get after a few sessions)
#   --skip-build         install from existing APK without rebuilding
#   --device <serial>    pick a specific device (default: first connected)
#   --dry-run            print commands, don't execute
#

set -euo pipefail

# --- colors ---------------------------------------------------------------
if [[ -t 1 ]]; then
    BOLD=$'\033[1m'; DIM=$'\033[2m'; RED=$'\033[31m'; GREEN=$'\033[32m'
    YELLOW=$'\033[33m'; CYAN=$'\033[36m'; RESET=$'\033[0m'
else
    BOLD='' DIM='' RED='' GREEN='' YELLOW='' CYAN='' RESET=''
fi

info() { printf '%s•%s %s\n' "$CYAN"   "$RESET" "$*"; }
warn() { printf '%s!%s %s\n' "$YELLOW" "$RESET" "$*"; }
err()  { printf '%s✗%s %s\n' "$RED"    "$RESET" "$*" >&2; }
ok()   { printf '%s✓%s %s\n' "$GREEN"  "$RESET" "$*"; }
step() { printf '\n%s▸%s %s%s%s\n' "$CYAN" "$RESET" "$BOLD" "$*" "$RESET"; }
abort(){ err "$1"; exit 1; }

# --- repo root ------------------------------------------------------------
REPO_ROOT="$(git rev-parse --show-toplevel 2>/dev/null)" \
    || abort "Not inside a git repository."
cd "$REPO_ROOT"

# --- defaults -------------------------------------------------------------
VARIANT=""
CLEAN=false
UNINSTALL=false
COLD=false
TIMING=false
LOGCAT=false
COMPILE_PROFILE=false
SKIP_BUILD=false
DEVICE=""
DRY_RUN=false

# Per-variant config (applicationId + Gradle install task)
declare -A APP_ID=(
    [debug]="pro.jayeshseth.animations.debug"
    [release]="pro.jayeshseth.animations"
    [benchmark]="pro.jayeshseth.animations"
)
declare -A INSTALL_TASK=(
    [debug]=":androidApp:installDebug"
    [release]=":androidApp:installRelease"
    [benchmark]=":androidApp:installBenchmark"
)
LAUNCH_ACTIVITY="pro.jayeshseth.animations.MainActivity"

# --- parse args -----------------------------------------------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        debug|release|benchmark) VARIANT="$1" ;;
        --clean)            CLEAN=true ;;
        --uninstall)        UNINSTALL=true ;;
        --cold)             COLD=true ;;
        --time|--timing)    TIMING=true ;;
        --logcat)           LOGCAT=true ;;
        --compile-profile)  COMPILE_PROFILE=true ;;
        --skip-build)       SKIP_BUILD=true ;;
        --device)           DEVICE="$2"; shift ;;
        --dry-run|-n)       DRY_RUN=true ;;
        --help|-h)
            awk 'NR>1 { if (/^#/) print; else exit }' "$0" | sed 's/^# \{0,1\}//'
            exit 0 ;;
        *) abort "Unknown argument: $1 (try --help)" ;;
    esac
    shift
done

# --- device selection -----------------------------------------------------
mapfile -t DEVICES < <(adb devices | awk 'NR>1 && $2=="device" {print $1}')

if [[ ${#DEVICES[@]} -eq 0 ]]; then
    abort "No device connected. Plug in or start an emulator first."
fi

if [[ -n "$DEVICE" ]]; then
    [[ " ${DEVICES[*]} " == *" $DEVICE "* ]] \
        || abort "Device '$DEVICE' not in: ${DEVICES[*]}"
elif [[ ${#DEVICES[@]} -eq 1 ]]; then
    DEVICE="${DEVICES[0]}"
else
    echo "Multiple devices connected:"
    for i in "${!DEVICES[@]}"; do
        MODEL=$(adb -s "${DEVICES[i]}" shell getprop ro.product.model 2>/dev/null | tr -d '\r')
        printf "  [%d] %s  ${DIM}(%s)${RESET}\n" "$((i+1))" "${DEVICES[i]}" "$MODEL"
    done
    read -rp "Pick device [1-${#DEVICES[@]}]: " idx
    DEVICE="${DEVICES[$((idx-1))]}" || abort "Invalid selection."
fi

ADB=(adb -s "$DEVICE")

# --- interactive variant selector ----------------------------------------
choose() {
    local prompt="$1"; shift
    local -a opts=("$@")
    if command -v fzf >/dev/null 2>&1; then
        printf '%s\n' "${opts[@]}" | fzf --prompt="$prompt " --height=10 --reverse
    else
        echo "$prompt"
        for i in "${!opts[@]}"; do
            printf "  [%d] %s\n" "$((i+1))" "${opts[i]}"
        done
        read -rp "Pick [1-${#opts[@]}]: " idx
        echo "${opts[$((idx-1))]}"
    fi
}

if [[ -z "$VARIANT" ]]; then
    echo "${BOLD}Compose Animations install helper${RESET}"
    echo "Device: $DEVICE"
    echo ""

    VARIANT_LINE=$(choose "Variant:" \
        "debug      — Animations-beta, debuggable, fast iterate" \
        "release    — production, signed, R8-minified" \
        "benchmark  — release-like but unminified, profilable")
    VARIANT="${VARIANT_LINE%% *}"
    [[ -n "$VARIANT" ]] || abort "No variant chosen."

    # quick toggles via single multi-pick prompt
    echo ""
    echo "Pre-install options (toggle with y/n, Enter = no):"
    read -rp "  Uninstall existing app first?    [y/N] " a && [[ "$a" =~ ^[Yy]$ ]] && UNINSTALL=true
    read -rp "  Clear app data before launch?    [y/N] " a && [[ "$a" =~ ^[Yy]$ ]] && CLEAN=true
    read -rp "  Force-stop for true cold start?  [y/N] " a && [[ "$a" =~ ^[Yy]$ ]] && COLD=true

    echo ""
    echo "Post-install options:"
    read -rp "  Apply speed-profile compilation? [y/N] " a && [[ "$a" =~ ^[Yy]$ ]] && COMPILE_PROFILE=true
    read -rp "  Show startup timing?             [y/N] " a && [[ "$a" =~ ^[Yy]$ ]] && TIMING=true
    read -rp "  Tail logcat after launch?        [y/N] " a && [[ "$a" =~ ^[Yy]$ ]] && LOGCAT=true
fi

# --- validate variant -----------------------------------------------------
[[ -n "${APP_ID[$VARIANT]:-}" ]] || abort "Unknown variant: $VARIANT"
PKG="${APP_ID[$VARIANT]}"
TASK="${INSTALL_TASK[$VARIANT]}"

# --- plan summary ---------------------------------------------------------
step "Plan"
printf "  %-18s %s\n" "Device:"   "$DEVICE"
printf "  %-18s %s\n" "Variant:"  "$VARIANT"
printf "  %-18s %s\n" "Package:"  "$PKG"
printf "  %-18s %s\n" "Activity:" "$LAUNCH_ACTIVITY"
[[ "$SKIP_BUILD"      == true ]] && printf "  %-18s %s\n" "Build:"    "${YELLOW}skipped${RESET}"
[[ "$UNINSTALL"       == true ]] && printf "  %-18s %s\n" "Pre:"      "uninstall existing"
[[ "$CLEAN"           == true ]] && printf "  %-18s %s\n" "Pre:"      "clear app data"
[[ "$COMPILE_PROFILE" == true ]] && printf "  %-18s %s\n" "Post-inst:" "compile speed-profile"
[[ "$COLD"            == true ]] && printf "  %-18s %s\n" "Launch:"   "force-stop first (cold)"
[[ "$TIMING"          == true ]] && printf "  %-18s %s\n" "Launch:"   "with timing"
[[ "$LOGCAT"          == true ]] && printf "  %-18s %s\n" "After:"    "tail logcat"
echo ""

# --- runner helper --------------------------------------------------------
run() {
    if [[ "$DRY_RUN" == true ]]; then
        printf '  %s$%s %s\n' "$DIM" "$RESET" "$*"
    else
        "$@"
    fi
}

# --- step 1: uninstall (optional) -----------------------------------------
if [[ "$UNINSTALL" == true ]]; then
    step "Uninstalling existing app"
    if "${ADB[@]}" shell pm list packages 2>/dev/null | grep -q "package:$PKG\b"; then
        run "${ADB[@]}" uninstall "$PKG"
        ok "Uninstalled $PKG"
    else
        info "$PKG not installed, skipping uninstall"
    fi
fi

# --- step 2: build + install ---------------------------------------------
if [[ "$SKIP_BUILD" == false ]]; then
    step "Building + installing ($VARIANT)"
    run env ANDROID_SERIAL="$DEVICE" ./gradlew "$TASK"
    ok "Installed $PKG"
fi

# --- step 3: clear app data (optional) -----------------------------------
if [[ "$CLEAN" == true ]]; then
    step "Clearing app data"
    run "${ADB[@]}" shell pm clear "$PKG"
fi

# --- step 4: apply baseline profile compilation (optional) ---------------
if [[ "$COMPILE_PROFILE" == true ]]; then
    step "Applying speed-profile ART compilation"
    run "${ADB[@]}" shell cmd package compile -m speed-profile -f "$PKG"
    ok "Compilation applied (speed-profile)"
fi

# --- step 5: force-stop for cold start (optional) ------------------------
if [[ "$COLD" == true ]]; then
    step "Forcing cold start"
    run "${ADB[@]}" shell am force-stop "$PKG"
fi

# --- step 6: launch -------------------------------------------------------
step "Launching"
if [[ "$TIMING" == true ]]; then
    run "${ADB[@]}" shell am start -W -n "$PKG/$LAUNCH_ACTIVITY"
else
    run "${ADB[@]}" shell am start -n "$PKG/$LAUNCH_ACTIVITY" >/dev/null
fi
ok "Launched $PKG/$LAUNCH_ACTIVITY"

# --- step 7: logcat (optional, foreground until Ctrl-C) ------------------
if [[ "$LOGCAT" == true ]]; then
    step "Streaming logcat (Ctrl-C to stop)"
    if [[ "$DRY_RUN" == true ]]; then
        info "[dry-run] would tail logcat for $PKG"
    else
        PID=$("${ADB[@]}" shell pidof "$PKG" | tr -d '\r')
        if [[ -n "$PID" ]]; then
            "${ADB[@]}" logcat --pid="$PID"
        else
            warn "Could not resolve PID for $PKG — tailing unfiltered"
            "${ADB[@]}" logcat
        fi
    fi
fi

echo ""
ok "${GREEN}${BOLD}Done.${RESET}"
