#!/usr/bin/env bash
# dev-setup.sh — idempotent local toolchain setup for macOS (docs/PLAN.md D2).
#
#   scripts/dev-setup.sh              # JDK 25, Flutter stable, Android SDK, Flutter wiring
#   scripts/dev-setup.sh --emulator   # additionally: emulator + Android 36 arm64 image + an AVD
#
# Fresh clone → running stack (README.md): this script, then `docker compose up -d --wait db`,
# `cd server && ./mvnw verify`, `cd app && flutter run`. Re-run any time; every step is a no-op
# once done. Nothing here needs sudo. Docker Desktop and Homebrew are prerequisites.
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
SDK_ROOT="${ANDROID_HOME:-/opt/homebrew/share/android-commandlinetools}"
SDK_PACKAGES=("platform-tools" "platforms;android-36" "build-tools;36.0.0")
EMULATOR_PACKAGES=("emulator" "system-images;android-36;google_apis;arm64-v8a")
AVD_NAME="margai_android36"
WANT_EMULATOR=0
[ "${1:-}" = "--emulator" ] && WANT_EMULATOR=1

say() { printf '\n▶ %s\n' "$*"; }
need() { command -v "$1" >/dev/null 2>&1 || { echo "missing prerequisite: $1 — $2" >&2; exit 1; }; }

need brew   "install Homebrew from https://brew.sh"
need docker "install Docker Desktop from https://docs.docker.com/desktop/setup/install/mac-install/"

say "Homebrew packages (Brewfile)"
brew bundle --no-upgrade --file="$ROOT/Brewfile"

say "JDK 25 visible to /usr/libexec/java_home (user-level link)"
JVM_DIR="$HOME/Library/Java/JavaVirtualMachines"
mkdir -p "$JVM_DIR"
ln -sfn "$(brew --prefix openjdk@25)/libexec/openjdk.jdk" "$JVM_DIR/openjdk-25.jdk"
/usr/libexec/java_home -v 25 >/dev/null

say "Android SDK licenses and packages"
(yes || true) | sdkmanager --licenses >/dev/null
sdkmanager --install "${SDK_PACKAGES[@]}"
if [ "$WANT_EMULATOR" -eq 1 ]; then
  sdkmanager --install "${EMULATOR_PACKAGES[@]}"
  if ! avdmanager list avd -c 2>/dev/null | grep -qx "$AVD_NAME"; then
    echo no | avdmanager create avd -n "$AVD_NAME" -k "${EMULATOR_PACKAGES[1]}" -d pixel_6a >/dev/null
  fi
fi

say "Flutter wiring"
flutter config --android-sdk "$SDK_ROOT" >/dev/null

say "Toolchain summary"
java -version 2>&1 | head -1
flutter --version | head -1
docker info --format 'Docker {{.ServerVersion}}' 2>/dev/null || echo "Docker daemon not running — start Docker Desktop"
"$SDK_ROOT/platform-tools/adb" version | head -1
[ "$WANT_EMULATOR" -eq 1 ] && echo "AVD: $AVD_NAME  (start with: flutter emulators --launch $AVD_NAME)"
flutter doctor
