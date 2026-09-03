# Decisions log

Where docs/SPEC.md and docs/DEV_SPEC.md are silent, CLAUDE.md says: choose the boring, maintainable
option and record it here. One line per decision, newest first. Conflicts between documents are not
recorded here — they are surfaced in the session and resolved in the documents themselves.

Format: `date · day · decision · why · revisit when`

- 2026-09-03 · D2 · One JDK for everything: Flutter's Gradle (9.3.1 / AGP 9.1) builds on the same JDK 25 the server uses, so no `flutter config --jdk-dir` · fewer moving parts in the fresh-clone path · if a Flutter upgrade pins an older Gradle.
- 2026-09-03 · D2 · Actuator exposes only `/actuator/health` with component names, no details · boot proof needs the db component; details wait for auth (D7+) · D7.
- 2026-09-03 · D2 · Android SDK via Homebrew `android-commandlinetools` (no Android Studio); emulator optional through `scripts/dev-setup.sh --emulator` · lighter, scriptable, fits the 15-minute fresh clone · when a contributor needs the IDE.
- 2026-09-03 · D2 · Generated localisations `app/lib/l10n/app_localizations*.dart` are committed · Flutter dropped synthetic l10n packages; CI and IDEs need no extra step · never.
- 2026-09-03 · D2 · Flutter dependencies at D2 are `flutter_riverpod` and `flutter_localizations`/`intl` only; drift arrives with the offline work (D34) · YAGNI · D34.
- 2026-09-03 · D2 · Flyway is wired at D2 with an empty `db/migration` location that carries a README so Flyway's location check passes; the first migration lands at D4 · PLAN D4 owns the first migrations (see the D2 day log for the DEV_SPEC §13.8 wording and how it was resolved) · D4.
- 2026-09-03 · D2 · Server tests use Testcontainers with `pgvector/pgvector:pg18`; the running app uses the compose db · tests never depend on local db state; same image as compose · never.
- 2026-09-03 · D2 · Spring Boot pinned to 4.1.0 on Java 25 from the Homebrew `openjdk@25` formula (no sudo), linked into `~/Library/Java/JavaVirtualMachines` · latest GA of both; CI already pins Java 25 · each Spring Boot minor.
- 2026-09-03 · D2 · Java package `com.margai`; Android applicationId and namespace `com.margai.app` · the specs name no package; three segments is the Android convention · before the first Play upload (D74) or when the final name lands (TRACKER F6/F7) — the applicationId is permanent after upload.
