# app/ — Flutter client (Android first)

Flutter stable (3.47) · Riverpod for state · drift/sqlite for the offline store (arrives D34) · ARB strings
for en / hi (hinglish copy is a later pass). Screens are the SPEC §8 catalog; do not invent screens.
Android applicationId `com.margai.app` (see docs/DECISIONS.md).

## Run

```bash
flutter devices                      # a USB-debugging phone, or the AVD from scripts/dev-setup.sh --emulator
flutter run -d <device-id>
```

Toolchain (Android SDK, JDK) is wired by `scripts/dev-setup.sh`; `flutter doctor` should show the
Android toolchain green. Xcode/CocoaPods warnings are expected: iOS is not a target.

## Lint and test

`cd app && flutter analyze && flutter test` — required before every commit (enforced by
`scripts/precommit-gate.sh`).

## Strings

Edit `lib/l10n/app_en.arb` (template) and `app_hi.arb`; `flutter gen-l10n` (also run by every build)
regenerates `lib/l10n/app_localizations*.dart`, which are committed. No hard-coded user-facing strings
in widgets.

Rules that apply here: `.claude/rules/app.md`.
