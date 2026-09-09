# app/ — Flutter client (Android first)

Flutter stable (3.47) · Riverpod for state · go_router · dio through the one `ApiClient` · drift/sqlite
for the offline store (arrives D34) · ARB strings for en / hi / hi_Latn (Hinglish, authored). Screens
are the SPEC §8 catalog; do not invent screens. Android applicationId `com.margai.app` (see
docs/DECISIONS.md). Layout per docs/TECH_PLAN.md §5.1: `lib/core/` (api, auth, config, l10n, router,
theme, widgets) and `lib/features/<feature>/` (models, repository, providers, screens).

## Run

```bash
flutter devices                      # a USB-debugging phone, or the AVD from scripts/dev-setup.sh --emulator
flutter run -d <device-id>
```

Toolchain (Android SDK, JDK) is wired by `scripts/dev-setup.sh`; `flutter doctor` should show the
Android toolchain green. Xcode/CocoaPods warnings are expected: iOS is not a target.

### Against the local server (D8 onwards)

The app talks to `API_BASE_URL` (a `--dart-define`, TECH_PLAN §5.9); the default is the emulator's
host alias on the port the server README uses, `http://10.0.2.2:8081`, so on the AVD nothing needs
passing. Debug builds may speak plain HTTP to that host (`android/app/src/debug/res/xml/
network_security_config.xml`); release builds are HTTPS-only.

```bash
# terminal 1 — the API with the sandbox OTP sender (the code is printed on logger margai.otp.sandbox)
cd server && SERVER_PORT=8081 ./mvnw spring-boot:run
# terminal 2 — the app on the AVD
cd app && flutter run -d emulator-5554                       # or:
flutter build apk --debug --dart-define=API_BASE_URL=http://10.0.2.2:8081 && \
  adb install -r build/app/outputs/flutter-apk/app-debug.apk
```

Sign in with any email: the code is in the server log (`[sandbox email] to y***@… — Your MARG AI
sign-in code is 123456`). For a real inbox start the server with the SES variant in the server
README (`MARGAI_AUTH_OTP_SENDER=ses MARGAI_AUTH_OTP_EMAIL_FROM=<verified identity>`; the
recipient must be verified too while the account is in the SES sandbox, TRACKER F10).

A phone over USB reaches the same server through a reversed port:

```bash
adb reverse tcp:8081 tcp:8081
flutter run -d <phone-id> --dart-define=API_BASE_URL=http://127.0.0.1:8081
```

Mobile data needs a public endpoint (the beta stack, TRACKER F8). The stored session lives in the
Android Keystore and survives a kill and reopen; an expired access token is refreshed on the first
call (D10). Log out from Profile (the person icon on Today) to sign the device out;
`adb shell pm clear com.margai.app` wipes it for a fresh-install test.

### Device proofs

`scripts/ui.sh` drives the app through the accessibility tree instead of by pixel, so a PLAN ✅
that says "on device" and the login-failure runbook (`docs/runbooks/login-failure-checklist.md`)
can be re-run the same way every time: `tree` lists every labelled node with its bounds, `tap
<label>` taps the node whose semantics label or text contains it, `field` focuses a text field,
`type` types into it, `shot <name>` screenshots to `$UI_SHOTS` (default `$TMPDIR/margai-ui`, never
the tree — the commit gate scans untracked files), and `launch` / `kill` / `clear`
start, force-stop or wipe the app. `scripts/ui.sh help` prints the details. It needs `adb` (on
PATH, or `ADB=…`) and `python3`.

```bash
scripts/ui.sh clear && scripts/ui.sh launch
scripts/ui.sh field && scripts/ui.sh type you@example.com && scripts/ui.sh tap 'Send code'
scripts/ui.sh tree                        # the code screen's nodes; dump twice right after launch
UI_SHOTS=/tmp/shots scripts/ui.sh shot 01-code-screen
```

## Lint and test

`cd app && flutter analyze && flutter test` — required before every commit (enforced by
`scripts/precommit-gate.sh`).

## Strings

Edit `lib/l10n/app_en.arb` (template), `app_hi.arb` and `app_hi_Latn.arb` (Hinglish, authored — never
transliterated from the Hindi file); `flutter gen-l10n` (also run by every build) regenerates
`lib/l10n/app_localizations*.dart`, which are committed. No hard-coded user-facing strings in widgets;
widget tests run every screen state in all three locales, so a missing key fails a test. Server error
codes and the reason codes of `VALIDATION_FAILED` details are rendered by `lib/core/l10n/failure_copy.dart`
(TECH_PLAN §3.3): the envelope's own copy first, ARB by code as the fallback.

Rules that apply here: `.claude/rules/app.md`.
