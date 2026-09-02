# app/ — Flutter client (Android first)

Flutter current stable · Riverpod for state · drift/sqlite for the offline store · ARB strings
for en / hi / hinglish. Screens are the SPEC §8 catalog; do not invent screens.

Scaffolded at **D2** (PLAN.md). Until `pubspec.yaml` exists here, `scripts/precommit-gate.sh` skips
the app check with a loud warning. Lint and test: `cd app && flutter analyze && flutter test`.

Rules that apply here: `.claude/rules/app.md`.
