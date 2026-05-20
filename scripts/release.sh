#!/usr/bin/env bash
#
# Tags and pushes a release. Detects the version from gradle/libs.versions.toml
# and the channel (production / beta) from the current branch, then creates an
# annotated tag matching the format the GitHub Actions workflows listen for:
#
#   main branch  → compose-animation-X.Y.Z         (release.yml, publish-play-store.yml)
#   beta branch  → compose-animation-beta-X.Y.Z    (release-beta.yml)
#
# Usage:
#   ./scripts/release.sh                # auto-detect channel from branch
#   ./scripts/release.sh production     # force production tag
#   ./scripts/release.sh beta           # force beta tag
#   ./scripts/release.sh --dry-run      # show what would happen, do nothing
#

set -euo pipefail

# --- colors ---------------------------------------------------------------
if [[ -t 1 ]]; then
    BOLD=$'\033[1m'
    DIM=$'\033[2m'
    RED=$'\033[31m'
    GREEN=$'\033[32m'
    YELLOW=$'\033[33m'
    BLUE=$'\033[34m'
    CYAN=$'\033[36m'
    RESET=$'\033[0m'
else
    BOLD='' DIM='' RED='' GREEN='' YELLOW='' BLUE='' CYAN='' RESET=''
fi

log()  { printf '%s\n' "$*"; }
info() { printf '%s%s%s %s\n' "$CYAN" "•" "$RESET" "$*"; }
warn() { printf '%s%s%s %s\n' "$YELLOW" "!" "$RESET" "$*"; }
err()  { printf '%s%s%s %s\n' "$RED"    "✗" "$RESET" "$*" >&2; }
ok()   { printf '%s%s%s %s\n' "$GREEN"  "✓" "$RESET" "$*"; }

abort() { err "$1"; exit 1; }

# --- locate repo root -----------------------------------------------------
REPO_ROOT="$(git rev-parse --show-toplevel 2>/dev/null)" \
    || abort "Not inside a git repository."
cd "$REPO_ROOT"

VERSIONS_FILE="gradle/libs.versions.toml"
[[ -f "$VERSIONS_FILE" ]] || abort "Can't find $VERSIONS_FILE."

# --- parse args -----------------------------------------------------------
DRY_RUN=false
FORCE_CHANNEL=""
for arg in "$@"; do
    case "$arg" in
        --dry-run|-n) DRY_RUN=true ;;
        production|prod) FORCE_CHANNEL="production" ;;
        beta) FORCE_CHANNEL="beta" ;;
        --help|-h)
            awk 'NR>1 { if (/^#/) print; else exit }' "$0" | sed 's/^# \{0,1\}//'
            exit 0
            ;;
        *) abort "Unknown argument: $arg (try --help)" ;;
    esac
done

# --- detect version -------------------------------------------------------
VERSION=$(grep '^version-name\s*=' "$VERSIONS_FILE" | sed 's/.*"\(.*\)".*/\1/')
[[ -n "$VERSION" ]] || abort "Couldn't parse version-name from $VERSIONS_FILE."

VERSION_CODE=$(grep '^version-code\s*=' "$VERSIONS_FILE" | sed 's/.*"\(.*\)".*/\1/')

# --- detect channel from branch ------------------------------------------
BRANCH="$(git rev-parse --abbrev-ref HEAD)"

if [[ -n "$FORCE_CHANNEL" ]]; then
    CHANNEL="$FORCE_CHANNEL"
    info "Channel forced to ${BOLD}$CHANNEL${RESET} via argument."
else
    case "$BRANCH" in
        main)   CHANNEL="production" ;;
        beta)   CHANNEL="beta" ;;
        *)
            warn "On branch '$BRANCH' — not main or beta."
            read -rp "Tag as [p]roduction or [b]eta? " choice
            case "$choice" in
                p|P|production) CHANNEL="production" ;;
                b|B|beta)       CHANNEL="beta" ;;
                *)              abort "Aborted." ;;
            esac
            ;;
    esac
fi

if [[ "$CHANNEL" == "production" ]]; then
    TAG="compose-animation-$VERSION"
else
    TAG="compose-animation-beta-$VERSION"
fi

# --- safety checks --------------------------------------------------------
log ""
log "${BOLD}Release plan${RESET}"
log "${DIM}────────────────────────────────────${RESET}"
printf '  %-14s %s\n' "Branch:"       "$BRANCH"
printf '  %-14s %s\n' "Channel:"      "$CHANNEL"
printf '  %-14s %s\n' "Version name:" "$VERSION"
printf '  %-14s %s\n' "Version code:" "$VERSION_CODE"
printf '  %-14s %s%s%s\n' "Tag:"      "$BOLD" "$TAG" "$RESET"
log ""

# Working tree must be clean
if ! git diff --quiet || ! git diff --cached --quiet; then
    warn "Working tree has uncommitted changes:"
    git status --short
    log ""
    read -rp "Continue anyway? [y/N] " confirm
    [[ "$confirm" =~ ^[Yy]$ ]] || abort "Aborted — commit or stash first."
fi

# Local must be in sync with remote branch (no unpushed commits)
if git rev-parse "@{u}" >/dev/null 2>&1; then
    UNPUSHED="$(git log @{u}.. --oneline 2>/dev/null || true)"
    if [[ -n "$UNPUSHED" ]]; then
        warn "You have unpushed commits on $BRANCH:"
        log "$UNPUSHED"
        log ""
        read -rp "Push them first? [Y/n] " confirm
        if [[ ! "$confirm" =~ ^[Nn]$ ]]; then
            if [[ "$DRY_RUN" == true ]]; then
                info "[dry-run] would push: git push origin $BRANCH"
            else
                git push origin "$BRANCH"
                ok "Pushed branch."
            fi
        fi
    fi
else
    warn "Branch $BRANCH has no upstream — tag will push but branch won't."
fi

# Tag must not already exist
if git rev-parse "$TAG" >/dev/null 2>&1; then
    err "Tag $TAG already exists locally."
    log "  ${DIM}Delete with: git tag -d $TAG${RESET}"
    log "  ${DIM}Then bump version in $VERSIONS_FILE and re-run.${RESET}"
    exit 1
fi

if git ls-remote --tags origin "$TAG" | grep -q "$TAG"; then
    err "Tag $TAG already exists on remote."
    log "  ${DIM}Delete with: git push origin :refs/tags/$TAG${RESET}"
    exit 1
fi

# --- create + push --------------------------------------------------------
read -rp "Create and push tag ${BOLD}$TAG${RESET}? [y/N] " go
[[ "$go" =~ ^[Yy]$ ]] || abort "Aborted."

if [[ "$CHANNEL" == "production" ]]; then
    MESSAGE="Release $VERSION (versionCode $VERSION_CODE)"
else
    MESSAGE="Beta release $VERSION (versionCode $VERSION_CODE)"
fi

if [[ "$DRY_RUN" == true ]]; then
    info "[dry-run] git tag -a $TAG -m \"$MESSAGE\""
    info "[dry-run] git push origin $TAG"
    ok "Dry run complete — no changes made."
    exit 0
fi

git tag -a "$TAG" -m "$MESSAGE"
ok "Created annotated tag $TAG."

git push origin "$TAG"
ok "Pushed $TAG to origin."

log ""
log "${GREEN}${BOLD}Done.${RESET} Workflows triggered:"
if [[ "$CHANNEL" == "production" ]]; then
    log "  • release.yml             → builds + GitHub Release"
    log "  • publish-play-store.yml  → uploads to Play Store production"
else
    log "  • release-beta.yml        → builds beta + GitHub prerelease"
fi
log ""
log "${DIM}Watch: gh run watch  (or open the Actions tab)${RESET}"