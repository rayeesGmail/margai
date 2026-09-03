# MARG AI developer toolchain (docs/PLAN.md D2). One-shot install on macOS:
#   brew bundle --no-upgrade
# then run scripts/dev-setup.sh for the SDK packages and Flutter wiring.
# Docker Desktop is assumed present (many machines already have it outside Homebrew).

brew "openjdk@25"                 # server: Java latest LTS (CLAUDE.md stack); keg-only, linked by dev-setup.sh
cask "flutter"                    # app: Flutter stable; puts flutter + dart on PATH
cask "android-commandlinetools"   # sdkmanager; SDK packages (platform-tools, platform, build-tools, emulator) via dev-setup.sh
