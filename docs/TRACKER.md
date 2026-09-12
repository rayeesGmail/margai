# MARGAI — Implementation Tracker

> Lives at `docs/TRACKER.md`. Update at the end of EVERY session (part of the 20-min
> close). Claude Code may tick boxes only when the day's ✅ acceptance check passed.
> Rules: a day is DONE only if committed + acceptance passed. Slipped days move down,
> never disappear. New ideas go to PARKED, reviewed Sundays.

---

## 📊 Status dashboard (update weekly)

| Field | Value |
|---|---|
| Current phase | **PHASE 2 — Content pipeline v1 (Weeks 3–4, D13–D24, M3)**, D13 done 2026-09-12, D14 next (the NCERT extraction pilot, the first live VISION day — the Anthropic 403 and the F8 bucket in the Blockers row); PHASE 1 — Auth & identity (Week 2, D7–D12) closed at the Week-2 gate 2026-09-09, running on **email OTP** until the DLT template (F1) exists (founder ruling 2026-09-08, DECISIONS) |
| Current day | D13 done · 2026-09-12 (built 2026-09-10 → 12 on `d13-taxonomy`: the four founder inputs drafted from the NEET (UG) 2026 syllabus and reviewed by the founder in full — all seven checklist items closed 2026-09-11 — 516 nodes, 104 edges, 4 tracks / 744 steps, 40 cut-off rows; then the `pipeline` module in six task commits: picocli commands under the `pipeline` profile, `CurriculumImport` in `curriculum.api`, strict readers, run reports with the input's SHA-256; **acceptance PASS** run literally against a fresh database `margai_d13` in the compose container: `taxonomy load`, `taxonomy prerequisites`, `backbone load`, `cutoffs load` all exit 0 with 516 / 104 / 744 / 40 rows, no cycle by the loader's Kahn check and by an independent SQL walk, the tree queryable by code and path over psql, an idempotent re-run changes nothing; reports committed under `pipeline/reports/`; server tests 356 (was 319), app untouched; spec-auditor on the build: see the day log) · next: D14 — NCERT extraction pilot, 2 books EN (✅ 20-paragraph spot check): TECH_PLAN §6.1 VISION extraction over page images, §6.3 `ncert register|render|extract|load`, `books.yaml` (§6.2), the S3 `content/` home (F8) or the ignored `pipeline/data/`, the Bedrock 403 (Blockers) and the D3 text-extraction escape hatch, the PUA decoding for `keph107.pdf` (PARKED), the D4 seed decision (PARKED) · 2026-09-12 after the D13 close: change spec **CS-1 (Collective Intelligence Layer)** integrated into SPEC, TECH_PLAN, PLAN and this tracker (day log "CS-1"); it adds scope to D22, D24, D29, D35, D47, D49, D55, D56, D73 and founder workstream F11, and changes nothing before D22; the founder's three rulings of 2026-09-12 (SPEC §6.1 sentence, the D29 read with graceful degradation, the momentum/strategy consumers) are applied |
| Days completed / total | 13 / 84 |
| Schedule delta | on track (the build); one founder item slipped — F10's SES production access was due "before D12" and is still open (slippage log) |
| Last week's gate | **Week-2 🚩 PASS** 2026-09-09 — a stranger's email signs in first try on the AVD (fresh install of the `db98a49` build, one code typed once, Today, reopen still signed in, `first_attempt_rate=1.000`) and the D9 runbook's ten rows re-run clean on the same build (transcript in the D12 day log, table in the runbook); carried, not failed: the phone channel (F1), mobile data (F8), an unverified stranger's inbox (F10 production access). Week-1 🚩 PASS 2026-09-08 stands (D6 day log) |
| Eval suite pass rate | placeholder PASS with 0 fixtures (suite arrives D23; gate ≥97%) |
| Cache hit rate | — |
| Blockers | none for the build (the phone-over-mobile-data half of D8's ✅ waits for a public endpoint, F8 — named as carried in the Week-2 gate verdict; F10's SES live proof passed 2026-09-09 — real email delivery works to verified recipients; production access is the remaining F10 step before an unverified stranger's inbox, also carried in the verdict). Two open items on the AWS account (founder; not build blockers before D14, the first live VISION day): (1) the **Anthropic models are refused with 403 `INVALID_PAYMENT_INSTRUMENT`** (AWS Marketplace subscription needs a valid payment method; AWS support ticket open since 2026-09-07) — the tier defaults stay Anthropic, the D5 smoke was proven on Amazon Nova Lite instead; when the ticket clears, rerun `cd server && BEDROCK_LIVE=1 ./mvnw test -Dtest=BedrockSmokeTest -Dsurefire.failIfNoSpecifiedTests=false`, then close TECH_PLAN §13.2 item 1's live proof and confirm or drop the Nova price row (DECISIONS 2026-09-08 D5 row). ~~(2) The local CLI session is the account **root** user via `aws login`~~ — **closed 2026-09-12**: F8 enabled IAM Identity Center and created the profile `margai` (Bedrock invoke, the content bucket, SES send), and the D5 smoke passed on it — the daily identity is no longer root (TECH_PLAN §7.4; the DECISIONS 2026-09-08 D6 row closed with it). All four §13.2 console checks are closed (D4/D5 day logs): CHEAP/VISION Haiku 4.5, REASON Sonnet 4.6 via `global.` profiles, EMBED `cohere.embed-multilingual-v3` in-region, batch minimum 100; Sonnet 5 / Opus 5 / Opus 4.8 gated → F8 allowlist request. Toolchain on this machine: JDK 25, Flutter 3.47.2, Android SDK 36 + emulator |

---

## PHASE 0 — Foundations (Week 1) · Module M0

- [x] **D1** Claude Code scaffolding (CLAUDE.md, settings, 3 gate scripts, rules, agents, commands) · ✅ gates block bad commit + secret write — done 2026-09-02, all five acceptance tests passed (see day log)
- [x] **D2** Local env: Docker Postgres 18+pgvector, Spring Boot 4 boots, Flutter shell on device, CI green · ✅ fresh clone → running <15 min — done 2026-09-03, acceptance PASS (35 s warm, ≈14.5 min cold), PR CI green (founder-verified), merged (see day log)
- [x] **D3** Claude Code full technical plan reviewed & approved · ✅ plan committed to docs/ — done 2026-09-04, docs/TECH_PLAN.md v1.0 APPROVED with 8 founder decisions (§0.5), three spec-auditor passes (see day log)
- [x] **D4** Core schema migrations (users, profiles, syllabus, config) + seed script · ✅ reversible migrations — done 2026-09-06, acceptance PASS (compose-db schema dump matches TECH_PLAN §2.2–§2.4 column by column; `MigrationReversibilityTest` green), PR #3 merged by the founder 2026-09-06 (merge commit 3f77d6f) (see day log)
- [x] **D5** AiClient seam + FakeAiClient + cost ledger + one live Bedrock smoke call · ✅ app runs fully on fake — done 2026-09-08 (built 2026-09-06 on `d5-ai-seam`, 15 commits), acceptance PASS: (a) fake chain + boot on the compose db; (b) live smoke on Bedrock `apac.amazon.nova-lite-v1:0` — two `ok` rows, real token counts, 5,976-token cache write then read, forced tool honoured; the Anthropic-profile proof waits for the AWS billing ticket (see day log); PR #4 merged by the founder 2026-09-08 (merge commit 0b70047, CI green after the test-order fix)
- [x] **D6** Buffer / overflow — done 2026-09-08: TECH_PLAN §0.3 dispositions closed and §14 checked against DECISIONS.md (25/25; the §12.1 D6 deliverables), root README + live-smoke credential wording brought in line with D3.4 and the D5 path, three spec-auditor findings fixed; 4 commits on `d6-week1-gate` (see day log); PR #5 merged by the founder 2026-09-08 (merge commit 11e50bb)
- [x] **🚩 WEEK-1 GATE:** repo, env, plan, schema, AI seam in place — **PASS** 2026-09-08, run as a literal demo script (evidence in the D6 day log)

## PHASE 1 — Auth & identity (Week 2) · M1

- [x] **D7** OTP request/verify + rate limits + tokens · ✅ curl happy path — done 2026-09-08, acceptance PASS (literal curl transcript in the day log: email request → sandbox code → verify → tokens with `sub/role/lang/jti` → refresh → reuse revokes the family; 429 + `Retry-After` for the cooldown and the hourly cap; phone refused while email-only; hash in the db, code only on the sandbox logger); **email OTP per the founder's D7 ruling** (DECISIONS row 1 of 2026-09-08, exit = F1); branch `d7-otp-auth`, 11 commits, PR #6 merged by the founder 2026-09-08 (merge commit af3adb3)
- [x] **D8** Login screens (auto-read OTP, retry, change number) · ✅ real device, mobile data — done 2026-09-09 (built 2026-09-08/09 on `d8-login-screens`, 9 commits): email entry, code entry with the sixth digit submitting, resend after the server's cooldown, change email, honest offline state with Retry, three-locale copy for every error and reason code, the `core/` foundation (ApiClient + envelope, token store, auth state, language mapper, router guard, theme); **acceptance PASS on the AVD against the local server with the sandbox inbox** — the founder's reading (plan question 1): the AVD is the device until a public endpoint exists, the mobile-data half is carried on the Week-2 gate line; 8 screenshots + db rows in the day log; spec-auditor PASS with 8 MINOR, all fixed on the branch; *D7 ruling: email first, SMS auto-read (`smart_auth`) waits for F1*; PR #7 merged by the founder 2026-09-09 (merge commit 55aea8b)
- [x] **D9** Unhappy paths (10-failure checklist) · ✅ all graceful — done 2026-09-09 (branch `d9-unhappy-paths`, 7 code commits): server — a per-identifier advisory lock ends the simultaneous-first-login race (the D7 known edge), `ClientTimeFilter` turns `X-Client-Time` into MDC + WARN + `auth.clock_skew`, `OtpStartup` retires pending codes on an ephemeral-pepper restart, `otp_challenges.created_at` now comes from `IstClock` (a §11.1 finding: the cooldown vanished under a movable clock), `AuthUnhappyPathsTest` pins the seven server rows; app — the entry step honours cooldowns per destination ("Send code in 57 min"), a different address lifts them, `CERTIFICATE` copy names the phone clock, `body`/`content_type` reasons render, Retry after any non-envelope answer; **acceptance PASS**: `docs/runbooks/login-failure-checklist.md`, ten rows with tests + AVD observations (19 screenshots, accessibility-tree driven) and curl transcripts for rows 6–8 (see day log); server 281 tests, app 168; spec-auditor PASS with 4 MINOR, all fixed; the PR's first CI run caught a clock-precision drift, fixed at `IstClock`; PR #8 merged by the founder 2026-09-09 (merge commit ba270af)
- [x] **D10** Profile-on-first-login, language, logout, token rotation · ✅ persistence + clean logout — done 2026-09-09 (branch `d10-account-basics`, 7 task commits + the audit fix + the residuals commit + the docs commit): server — the `student_profiles` row from `signIn` (find-or-create under the identifier lock), a new account's language from the verify call's `Accept-Language`, `POST /auth/logout` (authenticated, the caller's family, 204 either way; the reuse alarm narrowed to rotated-out tokens), no bearer read on the public routes, `PrincipalArgumentResolver` in common, `GET /me` `{user, profile}`, `PATCH /me` with one-pass reason codes; app — single-flight refresh on `AUTH_EXPIRED` (`SessionRefresher`), no bearer on the public routes, `meProvider` (once per sign-in, no auto-retry), `SettingsNotifier` (switch: server → stored user → locale → one rotation; logout: best-effort server, unconditional device), the locale follows the account, `/profile` with the switch and logout; **acceptance PASS on the AVD** against port 8082 with a 30-s access token: sign in → kill 2 min later → reopen lands signed in with the family rotated; Profile → हिन्दी re-renders in Hindi, `users.language = hi`, a third rotation; लॉग आउट → a fresh login screen, every token revoked, kill + reopen stays signed out; curl second device: `Accept-Language: hi-Latn` seeds `hinglish`, `/me`, `PATCH {language: fr}` → `language.invalid`, logout 204 ×2, the dead refresh → `AUTH_INVALID`, a stale bearer ignored, no bearer → `AUTH_REQUIRED` (9 screenshots + rows + log lines in the day log); spec-auditor FAIL → one MAJOR fixed in 70527b6 (an access token from a previous server key is now replaced through one refresh instead of stranding the student — proved on the device with a server restart) and six MINOR fixed or recorded, re-audit PASS with three residuals closed; server 306 tests, app 242; *rotation + reuse detection were live since D7 — D10 added the app's refresh and the device proof*; PR #9 merged by the founder 2026-09-09 (merge commit cf0cd2a)
- [x] **D11** DLT live check / delivery metrics · ✅ OTP success metric visible — done 2026-09-09 (branch `d11-otp-metrics`, 7 task commits + the audit fix + the docs commit): the DLT half skipped by PLAN's own "if F1 approved" (F1 ☐; the MSG91 adapter moves to F1's day); server — `otp.failed{channel}` and `otp.verified{channel, first_attempt}` (SPEC §11's numerator), `countExpiredUnverified` (codes that died unverified — the "never arrived" proxy), `auth.api.OtpMetrics` → `OtpDeliveryReport` per channel with `success_rate` and `first_attempt_rate` since the instance started, the **`ops` module** opened with `GET /admin/metrics/otp` (`@PreAuthorize` admin; `common` now renders a method-security refusal as `FORBIDDEN` instead of a 500), `/actuator/metrics` admin-only, the hourly `key=value` delivery-rate line (`margai.auth.otp.report-every`; scheduling on in `common`); **acceptance PASS on the AVD + curl** against port 8082 with a 1-minute report and 40-s codes: a clean login, a wrong-then-right login, a code left to die, the founder-style admin flag by hand + a fresh login → `GET /admin/metrics/otp` email `sent 5 · verified 4 · first attempt 3 · wrong 1 · expired unverified 1 · success 0.8 · first attempt 0.6`, the same on the actuator and in the 16:54:18 log line; a student → 403 `FORBIDDEN`, no token → 401 (4 screenshots + rows in the day log); spec-auditor PASS with 3 MINOR, all fixed; server 319 tests (was 306), app untouched; *no live SMS check: F1 has not landed*; PR #10 merged by the founder 2026-09-09 (merge commit 714af01)
- [x] **D12** Buffer — done 2026-09-09: `scripts/ui.sh` (the D9–D11 device-proof driver, in the tree with an app/README section), the eval gate's router alternative scoped to `server/` and `eval/`, the 200 founder-placed input PDFs git-ignored; 4 chore commits on `d12-week2-gate` + the audit fix + the docs commits, no feature code; the PARKED log-sender refusal checked and re-routed to F8 (see day log); PR #12 merged by the founder 2026-09-09 (merge commit 4301296)
- [x] **🚩 WEEK-2 GATE:** a stranger's phone signs in first try — **PASS** 2026-09-09 on the founder's approved reading (plan question 1; the spec-auditor argued PARTIAL and the founder kept PASS at the close, day log), ticking what was proved — the build's half — read as "a stranger's email" (DECISIONS D7 row 1): a never-seen address on a fresh install of the `db98a49` build, one code typed once → Today, reopen still signed in, `first_attempt_rate=1.000` on the reporter line; the D9 runbook's ten rows re-run on the same build, every one as written (runbook "D12 run", day log). *Carried, named — not failed: the phone channel (F1); the "real device over mobile data" half of D8's ✅ (F8 — until then a USB phone via `adb reverse`, app/README, or the AVD); an unverified stranger's inbox (F10 production access; the 2026-09-09 SES live proof to a verified recipient is the real-inbox evidence)*

## PHASE 2 — Content pipeline v1 (Weeks 3–4) · M3

- [x] **D13** Taxonomy CSV loaded + prerequisite graph + archetype drafts · ✅ no cycles — done 2026-09-12, merged as PR #13 (9e0e930) (drafted 2026-09-10 from the syllabus PDFs, founder review complete 2026-09-11 with all seven checklist items closed, built 2026-09-12): `pipeline/inputs/` (516 nodes, 104 edges, 4 tracks / 744 steps, 40 cut-offs), the `pipeline` module with `taxonomy load|prerequisites`, `backbone load`, `cutoffs load` under the `pipeline` profile and `CurriculumImport` in `curriculum.api`; **acceptance PASS** on a fresh database — all four commands exit 0, no cycle by the loader's Kahn check and by an independent SQL walk, the tree queryable by code and path, idempotent re-run; reports in `pipeline/reports/2026-09-12-*.md`; server tests 356 (day log); branch `d13-taxonomy`, PR pending the founder's review and push
- [ ] **D14** NCERT extraction pilot (2 books, EN) · ✅ 20-paragraph spot check
- [ ] **D15** All EN books extracted · ✅ coverage report/book
- [ ] **D16** Hindi ingest + EN↔HI alignment · ✅ 20 aligned pairs checked
- [ ] **D17** Embeddings + hybrid retrieval harness · ✅ 15 concept queries hit right paragraphs
- [ ] **D18** Buffer (extraction mess) · **🚩 WEEK-3 GATE:** NCERT searchable EN+HI
- [ ] **D19** PYQ ingest + tagging · ✅ counts match official papers
- [ ] **D20** AI solutions (1 subject) + verification wired · ✅ founder 50-Q audit #1
- [ ] **D21** Solutions all subjects + distractor maps · ✅ 50-Q audit #2 under threshold
- [ ] **D22** Weightage & difficulty stats → nodes + `collective_records` migration + `collective from-pyq` momentum (CS-1) · ✅ top-10 chapters sanity check
- [ ] **D23** Anchor linking + eval suite v1 (~60 Q) · ✅ harness runs
- [ ] **D24** Buffer: `collective from-pyq` misconceptions; `from-inputs` (if F11 has delivered), `review`, `load` — these three may slip to any later buffer (CS-1 §8, §9.3) · ✅ with `load`, wherever it runs: approved records for the top-50 weightage nodes ≥ threshold, sheet founder-signed, all versioned · **🚩 WEEK-4 GATE:** solved/tagged/anchored PYQ bank + eval in CI

## PHASE 3 — Onboarding & first plan (Week 5) · M2 + M4v0

- [ ] **D25** Interview Q1–Q3 (chat UI + persistence) + batch-position self-report for coaching students (D3 decision 3) · ✅ back/edit works
- [ ] **D26** Syllabus grid + hours sliders + goal/target (+optional category) · ✅ interview <5 min
- [ ] **D27** DOB + minors parent-consent OTP sent at the DOB step; onboarding completes regardless; "consent pending" state on Profile + re-prompt at gated moments (D3 decision 8) · ✅ photo doubts and uploads blocked until consent; text features and the first plan work
- [ ] **D28** Scorecard capture → extract → confirm → delete · ✅ 3 sample cards correct; storage empty after
- [ ] **D29** 12th-marksheet + batch-timetable doc_types (D3 decision 3) + deterministic first plan + reveal screen; the first plan reads the collective records (pacing, priority, attributed templated reasons) and degrades gracefully without them (founder ruling 2026-09-12; copy honest about the ramp — CS-1 §1) · ✅ end-to-end new user, records populated and empty
- [ ] **D30** Notification permission moment + morning notif skeleton
- [ ] **🚩 WEEK-5 GATE:** install → plan < 5 min, cold demo on fresh device

## PHASE 4 — Practice engine (Week 6) · M5

- [ ] **D31** Session backend (band+relevance selection, server judging) · ✅ no correct answer in any payload before that question is answered (D3 decision 1a)
- [ ] **D32** Practice UI (timer, verdict, solution, anchor chip) · ✅ smooth on mid-range phone
- [ ] **D33** Session summary + event stream · ✅ timing data in DB
- [ ] **D34** Offline cache + outbox sync, offline pack per Option A (D3 decision 1b) · ✅ airplane-mode test + the pack is the only pre-answer carrier
- [ ] **D35** Diagnostic test (30-Q adaptive) → ability estimates (intro copy: the fastest shift from “students like you” to “you” — CS-1 §5.6), plus `kind=mock` sessions (D3 decision 2; may slip into D36) · ✅ shifts a seeded plan
- [ ] **D36** Buffer · **🚩 WEEK-6 GATE:** practice loop incl. offline + diagnostic

## PHASE 5 — Doubt solver (Weeks 7–8) · M6 ⭐

- [ ] **D37** Text path: normalize → cache → cheap-tier grounded answer · ✅ 10 doubts, right anchors
- [ ] **D38** Photo path (vision extraction); minors without consent get CONSENT_REQUIRED on photo doubts (D3 decision 8) · ✅ 10 printed Qs faithful
- [ ] **D39** Router + reasoning tier + numerical verification + honest fallback · ✅ unverified never renders
- [ ] **D40** Answer UI per contract (steps/anchor/trap/follow-ups/report) · ✅ matches spec wireframe
- [ ] **D41** Cache write (verified only) + semantic near-match + metrics · ✅ instant repeat answer
- [ ] **D42** Buffer + 30-answer founder audit · **🚩 WEEK-7 GATE:** text+photo+verify working
- [ ] **D43** EN/HI/Hinglish answer behavior · ✅ 3-language read natural
- [ ] **D44** Free limits (5/day, cached=½) + meter + graceful limit screen · ✅ IST day-boundary math
- [ ] **D45** Doubt → state write-back visible in next plan · ✅ reason line appears
- [ ] **D46** Doubt history + follow-up threading · ✅ context kept
- [ ] **D47** Eval → ~150 Q + pre-commit eval gate for AI changes + the `claim` fixture kind (CS-1 §7; from the D29 templated reasons, AI lines at D56) · ✅ gate blocks failing prompt
- [ ] **D48** Buffer · **🚩 WEEK-8 GATE:** hero demo-ready; eval ≥ target; audit list empty/ticketed

## PHASE 6 — Notebook, SRS & nightly brain (Weeks 9–10) · M7 + M8

- [ ] **D49** Error capture + cause classification seeded with approved `misconceptions[]` (CS-1 §6) + one-tap correction · ✅ diagnosed in minutes; overrides stick
- [ ] **D50** Notebook UI (summary/entries/cause chips + free cap) · ✅ matches wireframe
- [ ] **D51** SRS 3/10/25 + variant selection (real Q preferred, else generate+verify) · ✅ day-3 variants appear
- [ ] **D52** Healed flow + ✓ gallery + Danger Zones · ✅ healing demo
- [ ] **D53** Patterns engine v1 (plain-language insights) · ✅ fires only with enough data
- [ ] **D54** Buffer + mock autopsy (per-mark classification, gamble score, pace map — D3 decision 2; may slip) · **🚩 WEEK-9 GATE:** capture→diagnose→resurface→heal end-to-end
- [ ] **D55** Nightly snapshot (two sources: collective record + student state, evidence-level weighting, pacing multiplier — CS-1 §5.1–§5.3) + deterministic candidate blocks · ✅ sensible plans for 5 synthetic students; CS-1 §7 (a)(b)(c)
- [ ] **D56** AI selection + attributed reasons (collective vs individual, never blended — CS-1 §5.5) + season prior (§5.4) + mentor note + validated output + fallback + AI-reason `claim` eval fixtures (§7) · ✅ no planless morning possible
- [ ] **D57** Batch run all users + morning deep-link notification · ✅ 2 devices, 2 different 7 AM plans
- [ ] **D58** Streaks + trajectory card + plan-negotiation chat v1 · ✅ "wedding weekend" rebalances
- [ ] **D59** Slump rules + light-day + mood chip · ✅ 3 dark days → gentler plan
- [ ] **D60** Buffer · **🚩 WEEK-10 GATE:** full loop unattended 3 real days on own account

## PHASE 7 — Money & trust (Week 11) · M9 + M10

- [ ] **D61** Razorpay subscribe (mandate + annual) + webhooks + sync · ✅ test purchase on device
- [ ] **D62** Paywall triggers ×4 + honest paywall screen · ✅ once-per-context; "Not now"=48h silence
- [ ] **D63** Cancel (2 taps) + 7-day auto-refund + exam auto-pause · ✅ zero-touch refund in test
- [ ] **D64** Export (PDF+JSON) + deletion + doc-deletion verify job + legal pages · ✅ export & delete demo
- [ ] **D65** Per-user AI budget breaker + spend alarms + cost dashboard · ✅ runaway loop trips breaker
- [ ] **D66** **🚩 WEEK-11 GATE:** money loop + all trust promises demonstrably true

## PHASE 8 — Hardening & polish (Weeks 12–13) · M11

- [ ] **D67** Hinglish/Hindi copy pass + mentor-voice audit · ✅ sign-off sheet
- [ ] **D68** Notifications final (2/day cap, quiet hours, exam protocol) · ✅ caps never exceeded
- [ ] **D69** Performance pass on cheap phone · ✅ p95 targets met
- [ ] **D70** Failure drills (DB restore, Bedrock outage, webhook replay) · ✅ scripted & passing
- [ ] **D71** Security checklist (auth, IDOR, rate limits, deps) · ✅ findings fixed
- [ ] **D72** Buffer · **🚩 WEEK-12 GATE:** boringly reliable
- [ ] **D73** Analytics funnels + crash triage flow + the CS-1 §7 metrics (completion trend, reasons by attribution, collective coverage) · ✅ dashboards on real test traffic
- [ ] **D74** Play Store listing + data-safety + internal track · ✅ installable from track
- [ ] **D75** Beta tooling (invites, admin peek, audit-review screen, feedback link) · ✅ flag review in 2 taps
- [ ] **D76** Seed cache: top ~500 predicted doubts batch-solved · ✅ hit-rate head start measured
- [ ] **D77** Full dress rehearsal (one student-day on prod) · ✅ punch list produced
- [ ] **D78** Punch-list burn-down · **🚩 WEEK-13 GATE:** beta build signed off

## PHASE 9 — Beta launch (Week 14) · M12

- [ ] **D79** Wave 1 (20 droppers) onboarded; live funnel watch
- [ ] **D80** Wave 2 recruiting + daily audits/fixes
- [ ] **D81** Wave 3 → 50 students
- [ ] **D82** Cohort review #1 (activation, first-doubt, report rate, cache, cost/user)
- [ ] **D83** Pricing conversations ×10 (₹299 vs ₹499 notes)
- [ ] **D84** **🚩 BETA GATE + retro:** go/no-go + weeks 15–20 ops plan written

---

## 🧑‍💼 Founder workstreams (parallel, evenings/Sundays)

| ID | Task | Start | Status | Notes |
|---|---|---|---|---|
| F1 | Razorpay KYC + DLT SMS template | W1 D1 | ☐ not started | long lead time. 2026-09-08 (D7): DLT registration needs a registered company, so login runs on **email OTP** until F1 lands (DECISIONS D7 row 1); when it does: add `sms` to `margai.auth.otp.channels`, the MSG91 adapter (~~D11~~ — D11 ran on 2026-09-09 without F1, so the adapter and the DLT live check move to the day F1 lands; the `sms` channel, `OtpSender` port and per-channel metrics are ready for it), the phone-attach flow (PARKED) |
| F10 | **SES for the OTP email channel** (D7 ruling): in the SES console, ap-south-1, verify a sender identity (address or domain); while the account is in the SES sandbox also verify the recipient addresses you test with; request production access before the first stranger (D12) or beta at the latest. Then run the founder-only live proof in `server/README.md` "Auth" (`MARGAI_AUTH_OTP_SENDER=ses MARGAI_AUTH_OTP_EMAIL_FROM=…`) | before D8's device login ideally; before D12 | ◐ 2026-09-08: the founder already has SES-verified email identities — sender covered; while sandboxed they double as the test recipients · **✅ live proof PASS 2026-09-09** (founder-run, transcript pasted in session): server on 8081 with `MARGAI_AUTH_OTP_SENDER=ses` and the verified sender, default region ap-south-1; `POST /auth/otp/request {email}` to a verified Gmail recipient (s***@gmail.com) → 200, request id 311fd2a1-…, challenge 1358d17a-…, channel email; the code arrived in the real inbox (no sandbox logger with the SES sender); `POST /auth/otp/verify` → 200 with access + refresh tokens, `expires_in` 900, `is_new_user: true`, user 49d2b89e-… — SES delivery and the identity region are settled | remaining: request production access before D12 so strangers' inboxes work (sandbox = verified recipients only); SSM keys `otp/sender`, `otp/email_from`, `otp/channels` (TECH_PLAN §7.3) at F8 · 2026-09-09 (D12): the Week-2 gate ran on the sandbox inbox with this live proof as the real-inbox evidence; **production access is the one open step before an unverified stranger's inbox, and overdue against this row's own "before D12" date** — named as carried in the gate verdict; slippage log |
| F2 | NCERT licensing letter sent | W1 | ☐ | follow-up cadence: monthly |
| F3 | Educator review of backbone booked | by W5 | ☐ | needed W8 · 2026-09-11 (D13): the draft to review exists — `pipeline/inputs/archetypes.yaml` (4 tracks, 744 steps) with the track windows and the weightage-first list explained in `pipeline/inputs/README.md`; the founder's sniff test passed, so booking the educator is the open step · booking in progress (founder, 2026-09-11) |
| F4 | Beta recruitment playbook + group scouting | W10–13 | ☐ | 2–3 Telegram groups |
| F5 | Marketing site copy + deploy | W11 | ☐ | |
| F6 | Trademark search (Class 41 + 9) for final name | anytime | ☐ | before public launch |
| F7 | Domain + social handles for final name | anytime | ☐ | MARGAI = working name |
| F9 | DPDP legal review of the minors' consent flow (TECH_PLAN §0.5 item 8, §9.6): consent OTP at the DOB step, gated photo doubts and uploads until consent | before D27 ideally; before beta at the latest | ☐ | not a build blocker; may tighten the gating |
| F11 | **Collect public-discourse excerpt files + a source list** for the collective intelligence layer (CS-1 §3, `docs/changes/CS-1-collective-intelligence.md`): excerpts from open forums, public comments and published topper/teacher material as `pipeline/inputs/collective/excerpts/*.md` (per file: source, date collected, node codes; the directory is git-ignored — list each file with its SHA-256 in `pipeline/inputs/collective/manifest.md`, DECISIONS 2026-09-12) with `sources.csv`; within CS-1's hard boundaries — nothing paywalled or login-gated, no competitor content or question banks, robots/ToS respected, no identifiable students. The pipeline reads the files and never crawls | any time from now; ideally before the D24 buffer | ☐ added 2026-09-12 | needed only before `collective from-inputs`; blocks nothing else — `from-pyq`, `review` and `load` run without it, and `from-inputs` may slip to any later buffer (CS-1 §8; TECH_PLAN §12.2) |
| F8 | AWS beta stack (Terraform) per TECH_PLAN §7.6 — accepted at D3 (decision 5). Claude drafts Terraform in a separate infra session profile (plan allowed, apply denied), created when the first milestone is due; founder runs every apply | by D5 (Bedrock access), D14, D28, D55, D70 | ☐ accepted 2026-09-04 | PLAN has no infra day (TECH_PLAN §0.4 #1); console checks §13.2 before D5. Bedrock access confirmed 2026-09-06 for Haiku 4.5 + Sonnet 4.6; optional AWS Sales allowlist request for the Claude 5 family / Opus 4.x (REASON upgrade path, not a blocker). 2026-09-07: the account's payment instrument blocks the Marketplace subscription for the Anthropic models (D5 blocker) — fix in Billing; and the local CLI session is the root user — create a non-root identity (IAM Identity Center or an IAM user) with Bedrock permissions for daily use before more live work (§7.4, §9.2). 2026-09-08 (D7): the ALB must keep `xff_header_processing.mode = append` — the app keys rate limits and `request_ip` on the *last* `X-Forwarded-For` hop (§1.5 step 1); the task role needs `ses:SendEmail` on the F10 identity (§7.4); SSM gains `otp/sender`, `otp/email_from`, `otp/channels` (§7.3). 2026-09-09 (D12): the Terraform variable behind `otp/sender` needs a validation that refuses `log` — the sandbox sender must never reach a deployed task, and a startup refusal in the server was rejected at D12 because most test contexts boot without a profile (PARKED). **2026-09-12: the D5 milestone's last piece landed and half of D14's** — IAM Identity Center enabled (which made this account an AWS Organization management account), a user plus a custom permission set (Bedrock invoke on `*` because the `global.` inference profiles route across regions, the content bucket, `ses:SendEmail`), the laptop profile `margai`, and the D5 smoke re-run on it green; the server needed the SDK `sso` + `ssooidc` modules to resolve the profile (DECISIONS 2026-09-12 F8). Done by hand in the console, not Terraform — the full stack is D55, and the D55 session imports or recreates these two. Remaining for D14: the `margai-beta-content` bucket and the source-PDF upload |

---

## 📈 Beta metrics scoreboard (Weeks 15–20, fill weekly)

| Week | D7 ret. (≥35%) | First-doubt 48h (≥40%) | Report rate (<1%) | OTP (≥98%) | Crash-free (≥99.5%) | Cache (≥55%) | Paying | AI ₹/Pro | AI ₹/free |
|---|---|---|---|---|---|---|---|---|---|
| W15 | | | | | | | | | |
| W16 | | | | | | | | | |
| W17 | | | | | | | | | |
| W18 | | | | | | | | | |
| W19 | | | | | | | | | |
| W20 | | | | | | | | | |

**Public-launch exit criteria:** all thresholds green + ≥15 organic-feeling payments.

---

## 📝 Day log (append newest on top)

```
Side task · 2026-09-12 · F8's non-root identity, a D14 prerequisite (no PLAN day)
The D14 prerequisites were read out of this tracker at the founder's ask and three needed a founder
  decision: the VISION model (the Anthropic 403), the source-PDF home (the F8 content bucket), and
  the non-root identity. The founder took the last two; this entry covers the identity, done by
  hand in the console rather than Terraform — §7.6 puts the full stack at D55, and the D5 milestone
  was built the same way, so the D55 Terraform session imports or recreates it.
Built (founder-run): IAM Identity Center enabled in ap-south-1 — which turns this standalone account
  into an AWS Organization management account, flagged before the click — a user, and a custom
  permission set carrying Bedrock invoke/batch on `*`, create-and-read-write on `margai-beta-content`,
  and `ses:SendEmail` for the F10 proof. Bedrock is scoped to `*` deliberately: the tier defaults are
  `global.` cross-region inference profiles, which authorise against the profile ARN *and* the
  foundation-model ARN in whichever region the call routes to, so §7.4's scoped ARN list is written
  for the ECS task role (D55) and not for the laptop. Laptop profile `margai` via `aws configure sso`.
Server change: `sso` + `ssooidc` joined `signin` as runtime dependencies in server/pom.xml. The first
  smoke under the new profile failed with "To use Sso related properties in the 'margai' profile, the
  'sso' service module must be on the class path" — `signin` (D5) serves an `aws login` session, not
  an `sso_session`, and the AWS CLI resolves the same profile with its own resolver, which is why
  `aws sts get-caller-identity --profile margai` had already succeeded. `./mvnw verify` green
  (360 tests, BedrockSmokeTest skipped as designed), then the live re-run green: two `ok` ai_calls
  rows on `apac.amazon.nova-lite-v1:0`, 29 in / 20 out, 5,976 cache written then read, 1 paisa each.
Docs: server/README.md, the BedrockSmokeTest and BedrockConfiguration javadocs and the pom comment
  name the `margai` profile; the DECISIONS 2026-09-08 D6 row closed on its own terms; one new
  DECISIONS row for the two SDK modules; F8 and the Blockers cell updated.
Open after this: the content bucket half of F8's D14 milestone (`margai-beta-content` + the 672 MB
  source upload), and the Anthropic 403 — still the one thing that decides how D14's VISION
  extraction runs. Noted in passing: a `.aws.dev.credentials` path matches no .gitignore pattern
  (the file does not exist; static keys are forbidden by §7.4 anyway), and `./mvnw verify` counts
  360 tests where the D13 line records 356 — reconcile at D14.
```

```
CS-1 · 2026-09-12 · DOCS (not a PLAN day) · change spec CS-1 — Collective Intelligence Layer — integrated
Founder-issued change spec docs/changes/CS-1-collective-intelligence.md (committed verbatim first as
  b685f46, renamed to the §9.5 path in the closing commit). The integration plan was shown before
  any edit and approved as written with the recommended option on each of its five questions:
  (1) the SPEC §1 Evidence rule gains one sentence so §9.6's collective claims do not contradict it;
  (2) CS-1 §7's metrics live in TECH_PLAN §10.2/§10.3 and §4.10, not SPEC §11; (3) the file is
  renamed by git mv in the integration; (4) misconception → question links stay inside the JSONB,
  validated by collective load, no join table; (5) one commit per document. Commits on d13-taxonomy
  after the D13 close: ee1b494 SPEC — §9.6 Collective intelligence (§1–§2 of CS-1 in product
  language), §6.1 "Two sources" with the honest-ramp copy rule, charter principle §10.9, the §1
  sentence; each amendment a DECISIONS row citing CS-1 §9.1, plus the CIL-adoption row (§9.4).
  c38d8e1 TECH_PLAN — §2.3 collective_records (node × season × status, JSONB lists), §2.8/§2.9 the
  D22 migration and the pipeline_collective feature, §4.1 CollectiveMineTask (CHEAP; from-pyq is
  deterministic), §4.5 the two-source snapshot and the prior-and-posterior weighting (saturating
  evidence level e over practice/diagnostic/doubts, linear weight 0.10 → 0.95, individual alone at
  e ≥ 0.6, collective attribution below w = 0.5, all under margai.planner.collective.*, per-node
  numbers in the snapshot), pacing multiplier, season prior in ModeResolver, attributed reason lines
  and the validator rule, §4.6 the classifier's misconception seed, §4.10 claim fixtures from D47,
  §6.2 the pipeline/inputs/collective/ inputs, §6.3 collective from-pyq | from-inputs | review |
  load, §6.5 momentum_trend, §10.2/§10.3 the three metrics, §0.2 the two rules follow-ons (D24
  pipeline.md, D56 ai-layer.md), §12.2 from-inputs may slip; two DECISIONS rows (record shape,
  weighting function). Then PLAN — D22, D24 (with the CS-1 §7 pipeline ✅), D47, D49, D55 (with
  §7 a–c in the ✅), D56, D73 scopes, each marked "added 2026-09-12"; this TRACKER — the same day
  lines, founder workstream F11 (excerpt files + source list; needed only before from-inputs),
  the CIL PARKED row retired, the D13 open item closed, this entry. Surfaced, not resolved
  silently, at planning: the §9.5 file path vs the attached name; SPEC §1 vs §9.6 (the sentence);
  the metrics' home; the D22 migration outside PLAN's D22 text; CS-1 is not on the SPEC §12
  exclusion list, so no exclusion was lifted. Nothing under server/, app/ or eval/ changed; the
  eval stamp was not needed. Founder-side after this: review and push d13-taxonomy; F11 whenever
  convenient; F3 booking; the D14 prerequisites unchanged.
spec-auditor on the integration, run after the five commits (a deviation from "audit before the
  day's final commit" — the fixes are a sixth commit): FAIL → 1 MAJOR fixed (CS-1 §9.3 gives
  `review` and `load` the slip permission too; the integration had pinned them to D24 and made the
  founder-signed sheet a Week-4 gate criterion — the permission is now on all three and the CS-1 §7
  pipeline acceptance travels with `load`, wherever it runs) and 11 MINOR fixed (claim fixtures need
  attributed reasons, so the `claim` kind is defined at D47 and populated from D56; CS-1 §5.6's
  diagnostic copy and the honest-ramp reveal mapped to D35 and D29; the command order stated once —
  from-pyq's momentum half after stats at D22, the rest after anchors; CS-1 §4's "after anchors"
  is not an input dependency and §9.3 names D22; `collective_records` owned by `curriculum`; the
  two config roots in §11.5; the season prior reads the nodes in play; `confidence` computed by
  `load` from source counts, lowered but never raised in the sheet; `measured_accuracy` with no
  attempts is `1 − struggle_score`; the SPEC §1 DECISIONS row cites the founder's decision-1
  approval, not CS-1 §9.1; excerpt files ignored by git with a manifest (DECISIONS); the §12.1
  map). Three findings are the founder's to rule on, not fixed:
Open for the founder (CS-1): (1) SPEC §6.1's unamended sentence "Every block carries a one-line
  reason drawn from the student's data (Evidence rule)" now contradicts the Two-sources paragraph
  two lines below it — proposed per-edit amendment: "…drawn from the student's data or, attributed
  as such, from the collective record (Evidence rule; §9.6)"; needs the founder's instruction, not
  edited. (2) Does the D29 first plan already read the collective record? CS-1 §1 says the day-1
  plan reads two sources; §9.3 maps the read to D55–D56 — recommended: yes at D29 (pacing,
  priority, collective-attributed templated reasons at zero evidence), the evidence-level weighting
  at D55; PLAN D29 unchanged until ruled. (3) `momentum_trend` and `strategy_notes` have no consumer
  in CS-1 §5 — TECH_PLAN §4.5 proposes momentum as a priority factor and a citable reason, strategy
  notes as block order and copy; confirm or strike.
Rulings 2026-09-12 (the founder, same day; three DECISIONS rows; the closing commit): (1) approved as
  proposed — SPEC §6.1's sentence amended on the founder's per-edit instruction, the only SPEC edit
  outside CS-1 §9.1 besides the §1 sentence; (2) yes at D29, with two riders — graceful degradation
  when records are absent or below threshold (from-pyq-only or none → a sound plan with default
  minutes and plain weightage-based reasons), and CS-1 §9.3 amended to name the D29 read, the first
  edit to the change spec since its verbatim commit; (3) the §4.5 consumers stand for both fields
  with the standard riders (above threshold only, attributed as collective, never over a
  prerequisite edge or an individual signal). Downstream touched and applied: `plan_blocks` gains
  `attribution` in the D29 migration V14 (§2.7, §2.9); the D29 ✅ runs with the records table
  populated and empty; the `claim` eval fixtures are populated from D47 after all — the D29
  templated reasons are attributed lines — with the AI lines joining at D56 (§4.10, PLAN/TRACKER
  D47 and D56); §12.1's D25–D30 row; the dashboard's CS-1 note names D29 and D35. Nothing before
  D22 moves; the D14 prerequisites are unchanged. Open for the founder (CS-1): nothing.
Merged to main with PR #13 (d13-taxonomy) by the founder 2026-09-12, merge commit 9e0e930; SPEC v2.0
  on main now carries the CS-1 amendments (§1, §6.1, §9.6, §10.9).
```

```
D13 · 2026-09-10 → 2026-09-12 · DONE · PHASE 2 — Content pipeline v1 (taxonomy + prerequisite graph + archetype drafts)
Session 1 (branch d13-taxonomy): the four founder inputs of TECH_PLAN §6.2 drafted from the syllabus
  PDFs for the founder's review — the bounded design approved as written (12 numbered decisions, the
  recommended option each). syllabus-2025 and syllabus-2026 diffed: identical content (50 NTA units),
  the 2026 file adds NMC cover letters; the 79 NCERT chapter titles verified from the books.
  pipeline/inputs/taxonomy.csv 516 nodes (4 subjects, 55 units, 83 chapters incl. 3 syllabus-only +
  the Unit 8 split, 374 topics); prerequisites.csv 103 chapter edges, acyclic; archetypes.yaml 4
  tracks / 744 steps (learn by chapter, revision by unit, mocks by subject; prerequisite-ordered,
  timing-checked across streams); cutoffs.csv 35 NTA qualifying rows 2019–2025. syllabus/*.pdf
  ignored with syllabus/manifest.md; 6 DECISIONS rows. Deviation from the approved design, item 8:
  biology prerequisite edges cross botany↔zoology where the discipline does (DECISIONS conventions
  row, README). Files reached the tree through the Write/Edit tools (the generator wrote to the
  scratchpad; repo copies diffed byte-identical before the audit).
  spec-auditor on the change set: FAIL → 1 MAJOR fixed (within a week the steps were sequenced by node
  code, so two chapters preceded their prerequisite on the day scale — the generator now orders a
  stream's chapters topologically inside the week and checks every edge at sequence granularity) and
  10 MINOR fixed (D25→D26 cross-references, the Unit 8 section numbers were 8.6–8.8 and are 8.8–8.10
  per the chapter PDF and no longer sit in a student-facing name, DECISIONS cited by content not row
  number, the dashboard phase cell, the .gitignore comment and the S3 content/ home deferred to D14,
  the fresher_1yr mock cadence text, track names now the SPEC §5.1 interview labels, the step
  conventions as a DECISIONS row, name_hi provenance stated as recall, the 2024 cut-off source names
  the revised 26 July notice). Not verified by the auditor and still open: the Hindi names (native
  reader), the cut-off values against the notices.
Session 2 · 2026-09-11 · the founder's review of the drafts, checklist items 1–3 CLOSED (commit
  75f4661 reviewed): (1) botany/zoology split approved as drafted, the prevalent coaching convention,
  Ecology under botany confirmed via the ORGPOP → ECOSYS → BIODIV chain; (2) the three syllabus-only
  chapters and the Unit 8 split all kept — EXPSKILL's ten experiments matter (NTA asks one or two a
  year), PBLOCK's two topics match the slimmed syllabus, the GOC → GOCTECH edge is wired; (3) topic
  granularity approved with no merges — every two-topic chapter matches the rationalised 2022
  edition and the deleted chapters (Solid State, Polymers, Transport in Plants, Digestion) are absent.
  DECISIONS rows amended with the closures; README checklist items 1–3 struck. Later the same day:
  (5) all 103 edges approved, nothing removed, one addition — BOT.11.CLASSIF → ZOO.11.ANIMALK, the
  mirror of Classification → Plant Kingdom (104 edges; this is the edge dropped on 2026-09-10 for
  cross-stream timing, so the generator now lets a prerequisite inherit the priority of its dependants
  and delays a chapter to its cross-stream prerequisite's week — Classification moves from week 10–15
  to week 2 in the dropper and repeater, Animal Kingdom follows it in the same week, 0 warnings, the
  archetypes regenerated); (6) track windows pass the founder's sniff test, F3 decides — booking the
  educator review is the founder's open step (F3 row). Items 4 and 7 stay open: Hindi names (native
  reader), cut-off values plus the 2026 and seat-type rows.
Session 3 · 2026-09-11 · FOUNDER REVIEW COMPLETE — all seven checklist items resolved (the founder's
  message of 2026-09-11): (4) Hindi names approved for D13 on the sampled units and chapters, the
  full-column native-reader skim parked for before D26 with the topic translations; (7) the seven
  drafted cut-off years verified against the official notices, five 2026 qualifying rows added from
  the NTA result notice of 16 July 2026 (Re-NEET of 21 June; general 213, EWS 213, OBC/SC/ST 177 —
  founder-supplied, the notice postdates Claude's knowledge, recorded as given), seat-type rows stay
  founder-sourced for around D58 (PARKED); the biology-split DECISIONS row carries the founder's
  Kota/Allen-convention wording; a new DECISIONS row for D58: 2026 is an outlier season and the
  trajectory feature anchors to a smoothed reference, not the latest year — the founder's "CIL
  season-note" and "from-inputs" terms were recorded verbatim; at the D13 close the founder expanded
  CIL to Collective Intelligence Layer, absent from SPEC and TECH_PLAN, so PARKED; "from-inputs" is
  still undefined. Surfaced instead of resolved: the four loader commands the founder asked to run
  do not exist yet — building them is the remaining D13 work, plan presented for approval.
Session 4 · 2026-09-12 · the build — 7 tasks approved as written (the D2 shape), one commit each on
  d13-taxonomy: 090baf1 task 1 the pipeline module (picocli command tree, the pipeline profile without
  a web server, the JVM exits with picocli's code; the security chain became a servlet-only bean);
  20b73f6 task 2 the four input readers (RFC 4180 CSV via Commons CSV, Jackson YAML, refusals with file
  and line); 4142194 task 3 CurriculumImport in curriculum.api with loadTaxonomy and loadPrerequisites
  — Kahn's algorithm over the whole database graph inside the transaction, orphans reported never
  deleted, the D4 seed's stale shapes proved as orphans; bd8aa04 task 4 loadBackbone and loadCutoffs
  (stale step sequences removed, step-phase conventions enforced); e5c23eb task 5 the run report
  pipeline/reports/<date>-<command>.md with the input's SHA-256, written for failed runs too; f501a50
  task 6 the §0.2 rule edit and the pipeline/README run instructions. Tests 319 (D12) → 356 after
  task 5 (+37: 10 at task 1, 27 over tasks 2–5), 0 failures, 1 skipped; ./mvnw verify green after
  every task. Surprise: one full verify failed with
  "FATAL: sorry, too many clients already" although the targeted run was green — the shared
  Testcontainers Postgres hit its default 100 connections when three profile-scoped test contexts
  joined (each caches a pool of 10); max_connections raised to 300 in TestcontainersConfiguration.
Acceptance ✅ "Taxonomy queryable; graph has no cycles" — PASS, run literally 2026-09-12 (first at
  09:04 IST; the committed reports are the 09:20 run on a recreated empty database, after the
  spec-auditor's fixes below — the report's input path repo-relative so a committed report reads the
  same on every machine, the backbone report widened, the cycle-check cell reading as a mechanism).
  A fresh database margai_d13 created in the compose container (the developer db keeps its seed until
  D14), `./mvnw -q -DskipTests package`, then from server/ with DB_URL=jdbc:postgresql://localhost:5432/margai_d13:
    java -jar target/server-0.1.0-SNAPSHOT.jar --spring.profiles.active=pipeline taxonomy load
      → read 516 nodes, inserted 516, orphans none, exit 0 (subjects/units/chapters/topics: physics
        1/20/29/134, chemistry 1/20/22/109, botany 1/9/20/79, zoology 1/6/12/52)
    … taxonomy prerequisites → read 104 edges, inserted 104, 104 edges over 83 nodes, cycle none, exit 0
    … backbone load → 4 tracks / 744 steps inserted (210/166/178/190), chapters in no track none, exit 0
    … cutoffs load → 40 rows inserted, 5 per year 2019–2026, orphans none, exit 0
  Reports committed: pipeline/reports/2026-09-12-{taxonomy-load,taxonomy-prerequisites,backbone-load,
  cutoffs-load}.md, input SHA-256 3c204a5c…, dc17b8da…, e23a162c…, c6569a36….
  Queryable, over `docker compose exec db psql -d margai_d13` (the 09:04 load; the 09:08 and 09:20
  re-loads produced the same counts): kind counts subject 4 / unit 55 /
  chapter 83 / topic 374; the chapters of PHY.U07 by join — PHY.11.ELAST (90 min), PHY.11.FLUID (225),
  PHY.11.THERMP (180), class 11, Hindi names present; the path BOT.12.INHERIT.MENDEL < BOT.12.INHERIT <
  BOT.U07 < BOT by a recursive CTE.
  No cycles, twice: the loader's Kahn check ("cycle | none" in the report) and an independent SQL
  recursive walk — 0 nodes reachable from themselves over 104 edges (52 sources, 75 targets); the
  longest chains are 9 (PHY.11.UNITS → PHY.12.EMW and → PHY.12.AC, CHE.11.BASICS → CHE.12.AMINES).
  Tracks: 4, each learning all 83 chapters, last weeks 96/44/40/40. Cut-offs 2026: general/ews 213,
  obc/sc/st 177 with the Re-NEET source.
  Idempotent re-run of all four (reports to the scratchpad): 0 inserted, 516 unchanged; 104 already
  present; 744 unchanged; 40 unchanged; every exit 0.
Deviations from the approved plan: the "fresh compose database" became a second database inside the
  running container rather than a `down -v`, so the developer's seeded db and its D7–D12 test accounts
  survive; a shell slip (zsh does not word-split "$cmd") sent one-word commands on the first attempt —
  four usage errors, nothing loaded, re-run correctly. Decisions: 7 DECISIONS rows dated 2026-09-12
  (servlet-only chain, picocli core, `--inputs` default, strict readers, orphans reported, steps follow
  the file, the run report). Parked: the D4 seed's fate (D14), a `--prune` option, report cost lines.
spec-auditor on the build, before this commit: FAIL → 1 MAJOR and 11 MINOR, all fixed. MAJOR: the
  inputs README claimed the loader re-implements every generator check — the loader now also refuses
  duplicate sibling sort orders, a topic whose class level differs from its chapter's, a chapter
  learned twice by one track and a chapter learned before a prerequisite the same track learns
  (sequence granularity, the drafts' session-1 MAJOR now guarded in the loader), and the README names
  the two checks that stay with the generator. MINOR: the `pipeline → common :: api` edge recorded
  (DECISIONS + TECH_PLAN §1.3); the "never delete" headline narrowed to nodes, edges, tracks and
  cut-offs; the backbone report widened to every subject, unit and chapter no step names plus orphan
  tracks (§6.3 "nodes not in any track"); every failure now leaves a report, unexpected ones a stack
  trace too, both paths tested; the cycle cell reads "passed (Kahn's remainder empty; a remainder
  fails the run)" instead of a bare "none"; the readers' bounds are sanity limits, not exam facts;
  structured log lines at start and end of every command; the max_connections departure recorded as
  a DECISIONS row; the 360-line service split into TaxonomyImporter, PrerequisiteImporter,
  BackboneImporter, CutoffImporter behind the CurriculumImportService facade; this audit recorded
  here. Four tests added for the new refusals and failure paths: 356 → 360.
Open for the founder: ~~what the Collective Intelligence Layer's "from-inputs" step is (CIL itself
  expanded and PARKED at the close)~~ — answered the same day by change spec CS-1 (the CS-1 block
  above); the F3 booking (in progress); ~~the PR from d13-taxonomy~~.
PR #13 from d13-taxonomy merged to main by the founder 2026-09-12 (merge commit 9e0e930, 20 commits:
  the D13 inputs and loaders plus the CS-1 integration and its rulings); D13 closed on main.
```

```
D12 · 2026-09-09 · PHASE 1 — Auth & identity (buffer + 🚩 Week-2 gate: a stranger's email signs in first try)
Plan approved as written (7 tasks, 5 conflicts surfaced, 5 closing questions → the recommended option
  each: PASS under the D7/D8 readings with the carried halves named; buffer tasks 1–3 all in; the
  AI_PATHS scoping approved as a policy edit; the input PDFs ignored; no in-session real-inbox leg,
  F10's live proof cited). The PARKED "refuse the log sender outside local/test" was checked and
  dropped before the plan: 12 of the 14 @SpringBootTest classes boot without a profile, so the belt
  moves to the F8 Terraform variable behind SSM otp/sender (F8 row, PARKED row).
Shipped (branch d12-week2-gate, 4 chore commits + the audit fix 020e1cd + this docs commit; no
  feature code, migration, prompt or app change — SPEC untouched): c499c71 scripts/ui.sh, the D9–D11 scratchpad driver in
  the tree (tree, tap by label, field, type, shot, launch, kill, clear; adb / UI_SHOTS / UI_PKG from
  the environment; app/README "Device proofs", the root README scripts row); 75af5a2
  precommit-gate.sh — the eval gate's router alternative scoped to server/ and eval/ (a scratch
  app/lib/core/router/gate_demo.dart in the change set → "✅ eval-gate no AI-touching paths changed"
  in a hand run of the gate; stamp refreshed with eval/run.sh, placeholder PASS); db98a49
  .gitignore — *.pdf under ncert/ and pyq/ (200 files, 672 MB; git status clean, the two manifests
  still tracked); 0341dc1 ui.sh backspace [n] for a kept wrong code. Suites at the close: server
  319 tests, 0 failures, 1 skipped (the Bedrock smoke); app 242, all passed.
Acceptance: 🚩 WEEK-2 GATE PASS — verdict rule fixed in the approved plan (PASS = leg A and leg B;
  the three carried halves named, not failed). Leg A, the stranger: AVD margai_android36 (Android
  16), the debug APK built from db98a49 with API_BASE_URL=10.0.2.2:8082 and installed 22:24:53;
  SERVER_PORT=8082 MARGAI_AUTH_OTP_REPORT_EVERY=1m ./mvnw spring-boot:run (local profile, sandbox
  sender, ephemeral secrets) started 22:24:37; stranger-d12@example.com had no users row (count 0;
  9 users and 25 challenges before the run); pm clear → the login screen 22:25:39 → the address →
  Send code 22:25:48 → the code screen ("New code in 25s" disabled) → the sandbox line's code
  707062 typed once at 22:26:01 → Today "You're in. Signed in as stranger-d12@example.com." at
  22:26:07 (shots a-00…a-03); force-stop 22:26:31 → reopen 22:26:37 lands on Today (a-04). db:
  users 3c2e4b02-… student / en / active, created 16:56:02Z; one student_profiles row at the same
  instant; the challenge attempts 0, verified_at 16:56:01Z; one live refresh-token family. Reporter
  line 22:26:37: sent=1 send_failed=0 verified=1 first_attempt=1 wrong_codes=0
  expired_unverified=0 success_rate=1.000 first_attempt_rate=1.000 — SPEC §11's number, 100 % for
  the stranger. Leg B, the runbook's ten rows on the same build (docs/runbooks
  /login-failure-checklist.md "D12 run", one line per row): rows 1, 2, 4, 5, 9, 10, 3 on the AVD
  through scripts/ui.sh, 7, 6, 8 by curl; three server instances (the first for 1, 2, 4, 5, 6 and
  the stop that makes row 9; the second, 22:34:04, for 9c, 7, 8; the third, 22:35:16 with
  MARGAI_AUTH_OTP_TTL=40s, for 10 and 3), each under the 10/hour per-address otp/request cap that
  the AVD and curl share (8 / 6 / 2 requests, no limit raised); every row as the runbook says, no
  regression; 18 screenshots + 3 server logs in the session scratchpad. After the run: 11 users,
  35 challenges; the founder's 8081 SES server untouched throughout; 8082 stopped.
spec-auditor (branch diff + the uncommitted doc notes): FAIL — 3 MAJOR, 8 MINOR. [MAJOR] the
  dashboard's Current day row still said "D11 done" beside 12/84 → rewritten. [MAJOR] the PASS
  label: the auditor read PLAN D12 as written ("a stranger's phone" — someone else's device,
  network and inbox) and argued PARTIAL: what was proved is a fresh account signing in first try on
  the developer's AVD with the sandbox inbox; the SES live proof shows delivery to a verified
  recipient, not to the unverified inbox the sandbox refuses; and F10's own "before D12" date
  passed unmet → the label stays PASS as the founder's approved reading (plan question 1), the
  gate line now says it ticks the build's half, F10's slip is in the slippage log and the schedule
  row, and the PARTIAL argument was surfaced at the close — the founder kept PASS (2026-09-09,
  in session, before pushing the branch). [MAJOR] the
  morning's case-sensitivity note misdiagnosed the gap: its example (DoubtRouter.java) exists
  nowhere; the real router, ai.tasks.DifficultyRouter, is caught by the ai/ alternative anyway,
  while TECH_PLAN §1.3's curriculum.api.ParagraphRetrievalRepository — which §1.3 says the rule
  covers — never matched `retriev` and lands at D17, before the D23 deferral → fixed in 020e1cd
  (either case), the DECISIONS row and PARKED corrected, a dated D12 row in TECH_PLAN §0.2. MINOR,
  all fixed in 020e1cd or here: the runbook's "no auth change since D10" was false (D11 changed
  auth and common — a D11 entry now sits under "Runs after D9"); eval/README described the
  unscoped rule; ui.sh's usage omitted dump; tap grepped the whole tree line (a label could hit a
  class, a flag or a bounds value — desc and text only now); a failed uiautomator dump served the
  previous window's file (removed first, an error now); UI_SHOTS defaulted to the working tree the
  gate scans ($TMPDIR/margai-ui now) and backspace 0 broke on bash 3.2; F10's overdue date was
  unsaid beside "on track" (slippage row, schedule row, F10 row); TECH_PLAN §4.10's "parameter
  change" has never been covered by AI_PATHS (pre-existing → PARKED). Its unverifiable items closed
  here: repo files changed only through Write/Edit (the scratch gate_demo.dart removed with rm);
  every transcript fact is in this log with its time; git status clean after the ignore (only PDFs
  and .DS_Store under ncert/ and pyq/); the founder approved the verdict rule and "no in-session
  real-inbox leg" as plan questions 1 and 5; the hand run of the gate with the scratch router file
  printed "no AI-touching paths changed"; the attribution trailers are on every commit.
Doc conflicts surfaced in the plan (none blocked): PLAN D12 "a stranger's phone" vs DECISIONS D7
  (email until F1) → read as email, the phone channel carried; PLAN D8 "real device over mobile
  data" vs F8 ☐ → carried per the founder's D8 reading (the AVD is the device until a public
  endpoint); a real stranger's inbox vs the SES sandbox (F10 production access ☐) → the sandbox
  logger in session, the 2026-09-09 live proof cited the D6 way; PLAN §1 "Fridays end with the
  gate" vs a Wednesday D12 → day numbering (settled at D6); the PARKED log-sender refusal vs 12
  profile-less test contexts → not built, the belt at F8.
Spec-silent choices: 3 DECISIONS rows dated 2026-09-09 · D12 (the driver's home + no gate script,
  the AI_PATHS scope, the PDF ignore).
Parked: the log-sender belt → F8's Terraform variable (row updated); "0 minutes" seen again in the
  row-3 sandbox line, still parked; AI_PATHS vs router/retrieval parameters in config (TECH_PLAN
  §4.10 "every prompt or parameter change" — the auditor's finding, decide at D23). Closed: the two
  ui.sh rows, the AI_PATHS row, and a case-insensitive-router row opened this morning with a
  made-up example (DoubtRouter.java) and closed by the audit fix the same day (below).
Surprise: (1) The per-IP otp/request cap (10/hour) is the real budget of a device re-run: the AVD
  (10.0.2.2) and curl both arrive from 127.0.0.1, so the ten rows were ordered to fit each server
  instance under it rather than raising the limit. (2) After a kill inside the resend cooldown the
  reopened app starts on an empty entry step — the address is not kept across a process death
  (D9 typed it again too); the runbook's "a fresh app that does not know it shows the countdown
  from the 429" is exactly what happened. (3) Instance 2's boot retired 4 pending codes (rows 4a,
  4c, 5b and 6 — the exhausted row-1 code kept its own expiry, it is not "live"), instance 3's boot
  1 (the row-9 code): the D9 row-10 line, twice. (4) The row-3 code's death showed on instance 3's
  last reporter line as expired_unverified=1 — the D11 "never arrived" proxy working. (5) The first
  uiautomator dump after launch showed the splash, the login tree came on the second — the D11
  note holds and is in ui.sh's header now. (6) A grep filter on "tries" hid the singular "1 try
  left." for a moment; the screenshot settled it — read the tree unfiltered when a line seems
  missing.
Tomorrow's first task: D13 — taxonomy CSV loaded + prerequisite graph + archetype drafts (✅ no
  cycles): PLAN Week 3 / PHASE 2 (M3); TECH_PLAN §6.2 founder inputs, §6.3 the taxonomy, backbone
  and cutoffs commands, §2.3 tables, §1.2 pipeline profile; the §0.2 D13 rule edit to
  .claude/rules/pipeline.md; en/ NCERT grounds first (the Hindi Chanakya→Unicode step and the
  U+F0xx decoding are PARKED findings for D14/D16). Founder items: F10 production access before a
  real stranger's inbox; the AWS billing ticket for the Anthropic profiles (dashboard).
PR #12 from d12-week2-gate merged to main by the founder 2026-09-09 (merge commit 4301296); D12 and
  Week 2 closed on main.
```

```
Side task · 2026-09-09 · NCERT inputs manifests for the Phase-2 content pipeline (no PLAN day)
ncert/2022-ed/{en,hi}/manifest.md completed from the books' own edition and contents pages: title,
  revised-edition month, latest reprint, chapter range, completeness, and a text check over every
  page of every file (PyMuPDF; the Hindi prelims and two English books rendered to images and read).
  All 20 books complete against their contents pages. Three example rows corrected (phy11-part2 is
  the January 2023 revised edition; phy11-part1's latest reprint April 2026; bio12's January 2026).
Findings → PARKED: the ten Hindi books are legacy Walkman-Chanakya glyph text with no Unicode map
  (zero Devanagari from any extractor; Hindi ingest needs a Chanakya→Unicode step or OCR, en/ is the
  grounding source until then); en/phy11-part1's Gravitation chapter and prelims extract as U+F0xx
  private-use codepoints (subtract 0xF000). The 672 MB of PDFs stay untracked.
PR #11 from ncert-manifests merged to main by the founder 2026-09-09 (merge commit cc86be7).
```

```
D11 · 2026-09-09 · PHASE 1 — Auth & identity (OTP delivery metrics: the success metric visible; email only, F1 not landed)
Plan approved as written (8 tasks, 10 spec-silent choices, 11 doc notes, 5 closing questions → the
  recommended option each: a d11-otp-metrics branch + PR; the report's window = the process lifetime;
  /actuator/metrics exposed admin-only beside the admin JSON route; the reporter hourly with 1 min
  for the demo; the JSON log encoder parked). PLAN D11's DLT half ("if F1 approved; else stay
  sandbox") skipped by its own condition — F1 ☐, and "sandbox" today is the D7 email path whose SES
  live proof passed this morning (F10); the MSG91 adapter the D7 DECISIONS row named for D11 moves
  to F1's day (F1 row) — founder-gated, not a slip. Server only; no app change, no migration, no AI
  path, SPEC untouched; every task test-first (the failing run before the code).
Shipped (branch d11-otp-metrics, 7 task commits + the audit fix + this docs commit): dca07bc auth —
  otp.failed{channel}, otp.verified{channel, first_attempt} (SPEC §11's numerator; OtpServiceTest
  +1, 2 assertions moved to the tagged meters); 2f3109f auth — OtpChallengeRepository
  .countExpiredUnverified(channel, purpose, since, now): codes that reached expiry with verified_at
  null, the "never arrived" proxy until SES events (AuthConstraintsTest +1, seven rows in a
  ten-year-ahead window); 657e9c2 auth — the auth.api named interface: OtpMetrics →
  OtpDeliveryReport(since, channels[OtpChannelReport]) from the registry + the db count, rates to
  three decimals and absent when sent = 0, every channel in enum order (OtpMetricsServiceTest 3);
  9fdb89a ops — the ops module (common :: api, auth :: api), AdminMetricsController GET
  /api/v1/admin/metrics/otp with @PreAuthorize("hasRole('ADMIN')"), and common's ApiExceptionHandler
  mapping AccessDeniedException → FORBIDDEN (a method-security refusal was a logged "bug" and a 500
  before — §9.3 did not work end to end; AdminMetricsControllerTest 2, ModularityTest pins ops);
  8cb049c auth — management exposure health,metrics; the chain gates /actuator/** beyond health on
  ROLE_ADMIN (SecurityChainTest +2: anonymous 401, student 403 envelope, admin 200 with the meter
  names; the admin route's refusal over the real chain); 1039eea auth + common — OtpDeliveryReporter,
  one key=value INFO line per enabled channel, margai.auth.otp.report-every, SchedulingConfiguration
  in common (OtpDeliveryReporterTest 2; the three AuthProperties.Otp call sites); 79701d6 test —
  OtpMetricsFlowTest, the ✅ in test form (clean / wrong-then-right / left to die → deltas +3 +2 +1 +1
  +1 on the report and the same count on the actuator; a clock a day ahead so the shared database's
  other rows stay outside the window); ffc1a9b fix after the audit (below). Server 319 tests (was
  306; 1 skipped = the Bedrock smoke), app 242 untouched.
Acceptance: ✅ PASS — PLAN D11 "OTP success metric visible", run on the AVD margai_android36 (Android
  16, the D10 APK — its baked API_BASE_URL is 10.0.2.2:8082, checked in the kernel blob, so no
  rebuild) against SERVER_PORT=8082 with MARGAI_AUTH_OTP_REPORT_EVERY=PT1M (pre-fix notation)
  MARGAI_AUTH_OTP_TTL=40s and the sandbox sender, started 16:48:18 IST (report since =
  2026-09-09T11:18:18Z; 8081 still held by the founder's SES server, left alone); driven by the D10
  ui.sh from the session scratchpad (4 screenshots there). 16:49:18 the first reporter line, all
  zeros, rates n/a. (a) pm clear → d11-clean@example.com → Send code 16:50:49 (sandbox code 721180)
  → the six digits → /today "Signed in as d11-clean@example.com" 16:51:13 (d11-01). (b) Profile → Log
  out → d11-retry@example.com → code 16:51:51 (293184) → 000000 at 16:52:05 → "That code didn't
  match. Try once more." + "4 tries left.", digits kept (d11-02) → field cleared, the right code →
  /today 16:52:15 (d11-03). (c) Log out → d11-lost@example.com → code 16:52:43 (670761), expiry
  16:53:23, never typed (d11-04). Then the founder-style admin: update users set role = 'admin' where
  email = 'd11-clean@example.com' over compose psql (fe945618-…), a curl login at 16:53:22 →
  is_new_user false, user.role admin, the JWT claims {role: admin, lang: en}; a curl login for
  d11-student@example.com → is_new_user true, role student. 16:53:37 GET /admin/metrics/otp with the
  admin bearer → {since: 2026-09-09T11:18:18.423535Z, channels: [{sms: all 0, no rates}, {email:
  sent 5, send_failed 0, verified 4, verified_first_attempt 3, wrong_codes 1, expired_unverified 1,
  success_rate 0.8, first_attempt_rate 0.6}]}; the student bearer → 403 {FORBIDDEN, "You can't do
  that here."} (message_user_lang in English: the principal's lang=en wins over Accept-Language: hi
  on an authenticated route, §3.8); no bearer → 401 AUTH_REQUIRED. Actuator with the admin bearer:
  otp.verified{channel=email,first_attempt=true} COUNT 3.0, otp.failed{channel=email} 1.0; the
  student → 403 FORBIDDEN envelope; no token → 401; /actuator/health still public. Reporter lines
  16:51:18 sent=1 verified=1 first_attempt=1 · 16:52:18 sent=2 verified=2 first_attempt=1
  wrong_codes=1 success_rate=1.000 first_attempt_rate=0.500 · 16:53:18 sent=3 verified=2
  expired_unverified=0 (the lost code had 5 s left) success_rate=0.667 · 16:54:18 and 16:55:18
  sent=5 send_failed=0 verified=4 first_attempt=3 wrong_codes=1 expired_unverified=1
  success_rate=0.800 first_attempt_rate=0.600. db: five otp_challenges rows (attempts 0/1/0/0/0,
  verified t/t/f/t/t — d11-lost expired 16:53:23 untried); users d11-clean admin, d11-retry and
  d11-student student. The 8082 server stopped afterwards; the founder's 8081 untouched.
spec-auditor (branch diff + the uncommitted doc notes): PASS with 3 MINOR — [MINOR] reportEvery bound
  and validated but never read; @Scheduled read the raw placeholder with its own ISO-8601-only
  parser, so a value Boot accepts (1h) could fail context start → fixed in ffc1a9b: the reporter
  schedules itself on the TaskScheduler at ApplicationReadyEvent from the record (one source of
  truth, §11.5), yml 1h, demo 1m (OtpDeliveryReporterTest +1); [MINOR] the §10.3 note said "per
  channel" where the code writes per enabled channel → "per enabled channel"; [MINOR] the scheduling
  javadoc cited §1.2 unamended → a dated line in §1.2's api row (every profile carries the scheduler
  thread; each schedule stays with its module). Residuals confirmed: no PII on the new lines (the
  reporter line pinned verbatim), expired_unverified consistent across query / javadoc / README /
  DECISIONS (it also counts a challenge exhausted by five wrong codes once its TTL passes — read it
  beside wrong_codes), the process-lifetime window holds for every number, the flow test's clock and
  deltas are not flaky while tests run sequentially, hasRole('ADMIN') matches ROLE_ADMIN. Its
  unverifiable items closed here: the 7 task commits touch server/ only, docs/SPEC.md untouched,
  verify green on every commit, TRACKER was dirty (the F1 row and PARKED edits, this commit).
Doc conflicts surfaced in the plan (none blocked): PLAN D11 "DLT template live check" vs F1 ☐ →
  skipped by PLAN's own clause; PLAN "dashboard stub" vs §10.3 dashboards at D73 → the admin JSON
  view + the actuator, dated note; §1.3/§3.7 ops routes at D65 → the module opened at D11, dated
  notes; §10.4 alarm otp.failed / otp.sent vs SPEC §11 "first attempt" → the alarm counts wrong-code
  attempts, first_attempt_rate is SPEC §11's number, dated note, the alarm text left to D73; §9.3
  @PreAuthorize vs common's catch-all → the FORBIDDEN mapping; DECISIONS D2/D7 actuator rows → amended
  by the D11 row; §10.1 JSON logs / the server rule vs no encoder in the tree → PARKED; DECISIONS D7
  "a delivery failure deletes the row" → send_failed counter-only → the window decision; §1.3 jobs
  owns the sweepers' schedules vs the reporter in auth → decision; §1.2 api row (the auditor) →
  amended; the untracked pyq/ (a NEET 2020 paper, 4.9 MB) and, mid-session, ncert/ (2026-ed Class 11
  Chemistry Part 1, 13 files, 59 MB) in the repo root — not ignored, left out of every commit; the
  pipeline rule keeps source PDFs in S3 content/ and pipeline/data/ is the ignored local spot —
  founder to place them (D14/D18 inputs?).
Spec-silent choices: 7 DECISIONS rows dated 2026-09-09 · D11.
Parked: the logstash JSON encoder (F8/D73); SES bounce/complaint/delivery events via SNS (F8);
  otp.time_to_verify and otp.resent; a ?hours= window once CloudWatch holds the counters; an admin
  bootstrap (D75); ui.sh — fourth day from a scratchpad (commit at the gate if D12 drives the AVD).
Surprise: (1) A @PreAuthorize refusal was a 500 — found while planning the first admin route: the
  exception passes the chain's denied handler and lands in common's catch-all; §9.3's rule had never
  been exercised. (2) Right after am start — even after force-stop + pm clear — the first uiautomator
  dump returned the previous window (the old signed-in landing); the second dump showed the splash.
  Dump twice before believing a stale-looking screen. (3) The debug APK carries its --dart-define
  next to the default in the kernel blob, so an installed build's port is checkable without a
  rebuild. (4) curl -w "HTTP %{http_code}" piped into jq breaks jq on the status line — print the
  status separately. (5) The auditor caught the two-parser split on report-every that the plan's
  "ISO-8601 so the same value drives @Scheduled" had rationalised; the record is the source now.
  (6) A grep for the sandbox line right after the Send-code tap raced the log flush once; the next
  dump showed the code step and the line was there.
Tomorrow's first task: D12 — buffer + the Week-2 🚩 gate, "a stranger's email signs in first try":
  the D9 runbook's ten rows re-run on the AVD (the D10 note), the D8 mobile-data half carried on F8,
  the gate as a demo script with PASS / PARTIAL / FAIL, ui.sh's fate; F10's SES production access is
  the one founder item before a real stranger.
PR #10 from d11-otp-metrics merged to main by the founder 2026-09-09 (merge commit 714af01);
  D11 closed on main.
```

```
D10 · 2026-09-09 · PHASE 1 — Auth & identity (account basics: profile on first login, language, logout, token rotation)
Plan approved as written (8 tasks, 12 spec-silent decisions, 12 doc notes, 5 closing questions → the
  recommended option each: a new account's language from the verify call's Accept-Language; tokens
  rotated right after a language switch; logout clears the device even offline; the D9 runbook's
  ten-row AVD re-run left to the D12 gate; the founder's uncommitted .claude/settings.json edit left
  out of the day's commits). The Principal argument resolver moved from task 3 to task 2, where
  logout first needed it.
Shipped (branch d10-account-basics, 7 code commits + the audit fix + this docs commit): 47e309c
  account — AccountService.signIn finds-or-creates the student_profiles row under the identifier lock
  (pre-D10 accounts heal on their next login) and a brand-new account starts in the verify call's
  Accept-Language (OtpService.verify / AuthController pass it through; AccountServiceTest +3,
  AccountConcurrencyTest, AuthFlowTest, OtpServiceTest +1, AuthControllerTest +1); 4b0963a auth —
  POST /auth/logout (authenticated; TokenService.logout revokes the presented token's family when it
  is the caller's, no-op otherwise, 204 either way), the reuse alarm narrowed to rotated-out tokens
  (a token revoked without a successor is a stale session → AUTH_INVALID quietly), a
  BearerTokenResolver that reads no bearer on PUBLIC_ROUTES, common's PrincipalArgumentResolver +
  WebConfiguration (TokenServiceTest +3, AuthControllerTest +3, SecurityChainTest +2, AuthFlowTest
  +1); 90f4015 account — GET /me {user, profile} (Me, ProfileSummary in account.api; MeController
  in account.web; AUTH_INVALID for a deleted account or a missing profile; MeControllerTest 3,
  AccountServiceTest +2); a99708b account — PATCH /me (ProfileUpdate typed; MePayloads.UpdateBody
  checks the raw body in one pass with reason codes — <field>.invalid from the enum, time.invalid,
  not_blank/size/decimal_min/decimal_max — absent = unchanged; setters on User and StudentProfile;
  MeControllerTest +3, AccountServiceTest +2, AccountFlowTest: login → /me → PATCH hi → /me hi →
  refresh carries lang=hi and errors speak Hindi → logout → refresh dead); da88727 app — ApiClient
  single-flight refresh on 401 AUTH_EXPIRED through a handler (retry once with the new bearer), no
  bearer on /auth/otp/* and /auth/refresh, PATCH, 204 → {}; SessionRefresher (one in-flight refresh,
  rotated pair stored beside the user, AUTH_INVALID on the refresh clears the device, offline keeps
  the session); AuthRepository.refresh/logout, TokensResult, AuthNotifier.updateUser,
  apiAdapterProvider (api_client +10, session_refresher 6, api_wiring 2, repository +3,
  auth_notifier +2); 7d2991a app — features/account: Me/Profile (lenient), AccountRepository,
  MeNotifier (once per sign-in, null signed out, reload, replace, Riverpod auto-retry off),
  SettingsNotifier.setLanguage (server → stored user → locale → one rotation) and logout
  (best-effort server, unconditional device); LocaleNotifier follows the account (settings_notifier
  9, me_provider 5, account repository 5, locale_provider 3); 20137e5 app — ProfileScreen at
  /profile (identity line, the three languages each in its own language, the §6.11 note, honest
  failure lines, Log out), Today's bar action + meProvider watch, OneHandPage.appBar, ARB ×3 +11
  keys (profile_screen 4 states × 3 locales + 2 intents, today_placeholder +3, guard +1,
  settings_notifier +2, app_test +3); 70527b6 fix(app) after the audit (below) and one more small
  commit for the re-audit's residuals. Server 306 tests (was 281; 1 skipped = the Bedrock smoke),
  app 242 (was 168); no AI path, no migration (entities
  gained setters only), SPEC untouched; every task test-first (the failing run before the code —
  compile-level red where the API was new, behavioural red for the rest).
Acceptance: ✅ PASS — PLAN D10 "Kill app, reopen → still logged in; logout → clean state", run on the
  AVD margai_android36 (Android 16) against SERVER_PORT=8082 with MARGAI_AUTH_JWT_ACCESS_TTL=30s and
  the sandbox sender (8081 was still held by the founder's SES server from the morning, left alone),
  driven from the session scratchpad through uiautomator dump / tap by label (ui.sh); 9 screenshots
  there. (1) pm clear → d10-acceptance@example.com → code 190213 from the sandbox line → the sixth
  digit submitted → /today at 15:30:32; db: users 9ae24489-… en active, student_profiles 1 row at
  intro, refresh family 6e0af051-… 1 row (device_label margai/0.1.0+1 android). (2) force-stop at
  15:32:33 — the 30-s token and the 60-s skew long gone — reopen at 15:32:41 → splash → /today with
  no login; db: the family now 2 rows, the first replaced_by_id set and last_used_at 15:32:42, a new
  live row (rotation on the reopen). (3) Profile → हिन्दी at 15:33:54 → the whole screen in Hindi
  (title, note, वापस जाएं, लॉग आउट; shot d10-06); db users.language = hi; a third family row at
  15:33:50 (the rotation right after the switch). (4) लॉग आउट at 15:34:04 → the entry screen, empty,
  in English again (the device suggestion); server log "logout: family 6e0af051-… revoked (1 live
  token(s))"; db 3 rows, 0 live; force-stop + reopen → the entry screen (clean state). (5) curl
  second device d10-curl@example.com with Accept-Language: hi-Latn → user.language hinglish; GET /me
  → {user, profile{is_minor false, onboarding_step intro, morning_notification_time 07:00:00, streaks
  0}}; PATCH {language: fr} → 400 details.language language.invalid with Hinglish copy; logout → 204,
  again → 204; refresh with that token → 401 AUTH_INVALID; refresh with a stale bearer attached →
  the body decided (AUTH_INVALID, not AUTH_EXPIRED); GET /me without a bearer → 401 AUTH_REQUIRED in
  Hindi; logout without a bearer → 401; db: 1 profile, 1 token, 0 live; log: "logout: family
  cc43dad7-… revoked (1 live token(s))" then "(0 live token(s))". (6) after the audit fix, the
  restart path the auditor called unverifiable: d10-restart@example.com signed in on server instance
  A at 15:42:19; instance A killed 15:42:41, instance B up 15:42:45 with a new ephemeral JWT key;
  force-stop + reopen at 15:44:26 → /today with no failure line; db: the family rotated at 15:44:26
  (the previous-key access token was AUTH_INVALID on /me, the refresh token still good, one refresh
  healed it); no reuse alarm, no ERROR. Not run: the ten-row runbook on the AVD (D12, per question 4).
spec-auditor (branch diff): FAIL — [MAJOR] AUTH_INVALID ended the session only on the refresh path;
  on /me it sat as an error state with a Retry that re-ran the dead call, so a stored access token
  signed by a previous server key (a local restart with the ephemeral secret, a rotation past
  secret-previous) would strand the student on Today until Profile → Log out, while the refresh
  token beside it was still good and never used → fixed in 70527b6: ApiClient treats AUTH_INVALID on
  an authenticated call like AUTH_EXPIRED (one refresh, one retry) and a retry still refused calls
  the new onSessionLost (AuthNotifier.signOut) — the account, not the token; api_client +3,
  api_wiring +2 (a previous-key token replaced, an unservable account signed out), and step 6 above
  on the device. [MINOR ×6] the /me failure line offered Retry on every failure → gated by
  isEnvelope like the switch (profile_screen +1); seven new PATCH /me reason codes without ARB copy
  → unreachable from the app today, deferred to D25/D64 in the PATCH DECISIONS row + PARKED; a
  flow test in account importing auth.internal doubles → recorded as accepted test-only drift
  (DECISIONS); state_code accepts any two letters → PARKED for D22 (cutoffs); the language note
  presupposed content the mentor has not made → reworded as a rule in all three ARBs; the D7
  users-row decision not cited as amended → cited, and the D7 row carries the amendment. The
  DECISIONS row and the §3.2/§5.4 notes drafted before the audit described the intended behaviour,
  not the shipped one — rewritten to what ships now. Re-audit (scoped to the fix + the docs): PASS —
  all seven findings closed or deliberately recorded, the rewritten row and notes confirmed against
  api_client.dart / session_refresher.dart; three MINOR residuals closed in the follow-up commit:
  the landing's envelope-means-no-Retry branch had no test (today_placeholder +1), the day log
  carried a forward-reference placeholder (this sentence replaces it), AuthNotifier.signOut's
  comment named one caller where there are now three. Unverifiable items closed by the run: the
  restart path (step 6), verify/analyze/test green on every commit, the eval stamp refreshed once
  for the router path.
Doc conflicts surfaced in the plan (none blocked): PLAN D10 "token rotation" vs D7 (live since D7) →
  the app's refresh + the device proof; TECH_PLAN §3.7 /me five keys vs their producers' days →
  {user, profile} now (dated note); §1.5 step 3 (logout not public) vs DEV_SPEC §5 (no logout at
  all) → TECH_PLAN; §5.4 silent on the bearer on public routes → decision + note; §5.5 "the chosen
  value wins" vs AccountService's language = en → the Accept-Language seed; SPEC §5.1 "Language
  confirm" (D25) vs §6.11's switch → the switch only; PLAN D10 ✅ "reopen → still logged in" was true
  at D8 for a fresh token → read with an expired one (the 30-s TTL); the D9 runbook's re-run rule vs
  the day → pins on every commit, the AVD rows at D12 (runbook note); app.md "no logic in widgets" →
  state getters; CLAUDE.md "ARB (en/hi)" → three (known); the AuthNotifier/Accounts javadocs →
  updated; Spring's bearer filter on permitAll routes → decision + SecurityChainTest pin.
Spec-silent choices: 16 DECISIONS rows dated 2026-09-09 · D10.
Parked: DELETE /me/devices on logout (D30); per-request account checks for a still-valid access
  token after logout/deletion (D64); the app-wide Riverpod retry policy; ui.sh under scripts/ if D12
  drives the AVD; state_code against the state list (D22); ARB copy for the seven reason codes.
Surprise: (1) Riverpod 3 retries a failed AsyncNotifier build on its own with a backoff — the first
  me_provider test saw getMe called twice; off for meProvider (one honest Retry). (2) Spring's
  BearerTokenAuthenticationFilter rejects a bad token even on a permitAll route: without the
  public-route BearerTokenResolver the app's own stale token would have 401'd /auth/refresh.
  (3) Top-level Riverpod providers that reference each other inside closures need declared types
  (Dart's inference reports a circularity). (4) A refresh with a logged-out token tripped D7's reuse
  alarm in a test — the alarm now means a rotated-out token only, as §3.2 words it. (5) The
  RadioGroup API (Flutter 3.32+) has a required onChanged, so a busy screen disables the tiles.
  (6) The Hinglish copy pushed the /me Retry below the 600-px test fold and the tap hit Log out;
  the fetch failure now sits under the identity line and the test scrolls first. (7) The precommit
  gate's router regex tripped on app/lib/core/router again — eval/run.sh refreshed the stamp (still
  PARKED). (8) The re-build for the audit fix ran from the repo root ("No pubspec.yaml") and its
  `| tail -1` hid the exit code, so the first restart demo re-installed the old APK and failed —
  the failure was real and the fixed build then passed; build from app/ and never pipe a build.
Tomorrow's first task: D11 — F1 (DLT) has not landed, so no live SMS check; email delivery-rate
  logging on top of D7's otp.sent/verified/failed/send_failed counters (a per-channel success ratio),
  and the OTP metrics dashboard stub (TECH_PLAN §10.2/§10.3); ✅ the OTP success metric visible —
  locally through the actuator/metrics endpoint or a log line, since CloudWatch waits for F8.
PR #9 from d10-account-basics merged to main by the founder 2026-09-09 (merge commit cf0cd2a);
  D10 closed on main.
```

```
D9 · 2026-09-09 · PHASE 1 — Auth & identity (unhappy-path hardening, the 10-failure checklist)
Plan approved as written (8 tasks, 8 spec-silent decisions, 9 doc notes, 4 closing questions → the
  recommended option each: retire pending codes only when the pepper is ephemeral; a client-only
  CERTIFICATE code with "check your phone's date and time" copy; AVD observations for the app-visible
  rows + curl for the server-only ones; the checklist lives at docs/runbooks/login-failure-checklist.md).
Shipped (branch d9-unhappy-paths, 7 code commits + this docs commit): ab3133f account — simultaneous
  first logins serialised on a per-identifier pg_advisory_xact_lock in AccountService.signIn (the D7
  known edge: 7 of 8 threads hit users_email_key before it; AccountConcurrencyTest, 5 rounds × 8
  threads → one row, one is_new); f395a5e auth — ClientTimeFilter on /api/v1/auth/* (X-Client-Time →
  MDC client_skew_s; one WARN + auth.clock_skew{band} past margai.auth.clock-skew-warn = 2m; never
  echoed; a FilterRegistrationBean so @WebMvcTest slices stay unaware); 182f9f7 auth — OtpStartup
  retires every pending challenge on ApplicationReadyEvent when AuthKeys reports the pepper ephemeral
  (OtpChallengeRepository.retireLive); c8dc70f AuthUnhappyPathsTest (expiry, cooldown with the exact
  wait, the hourly cap then the window passing, malformed bodies as reason codes, two parallel
  verifies → one account, a skewed clock counted on /auth and ignored on /actuator, a verify flood 429
  before the service) + the finding that otp_challenges.created_at came from Hibernate's VM clock
  while the cooldown and cap compare with IstClock (§11.1) — now stamped from the app clock;
  36d1907 app — LoginState.canRequest / longWait / resendMinutes, requestCode a no-op inside a
  cooldown, the ticker on the entry step only while one is pending, "Send code in Ns / N min", "New
  code in N min", ARB ×3 (sendCodeIn, sendCodeInMinutes, resendInMinutes), the D8 whole-app test walks
  the cooldown after Change email; 3651cf3 app — ApiFailure.certificate (dio badCertificate,
  TlsException) with its own copy in three locales, body/content_type reasons render the authored
  "update the app" line, Retry after any non-envelope failure; 54e8566 app — a different address lifts
  the client-side cooldown (found on the AVD, row 5), resendSeconds rounds up; + the spec-auditor
  follow-up commit (below). Server 280 tests (was 263), app 168 (was 145); no AI path, no migration,
  SPEC untouched; every task test-first (each new test watched failing before its code).
Acceptance: ✅ PASS — docs/runbooks/login-failure-checklist.md: ten rows, each with trigger, server
  answer, screen state, pinning tests and today's evidence. Run: AVD margai_android36 driven through
  `uiautomator dump` (taps by label; 19 screenshots in the session scratchpad) against SERVER_PORT=8082
  + the sandbox sender; curl for rows 6–8. (1) wrong ×4 → "4/3/2/1 tries left." with the server line,
  digits kept, Verify live; (2) the 5th → the attempts line alone, field + Verify disabled, "Send a new
  code" live; (3) server with MARGAI_AUTH_OTP_TTL=40s, the right code 45 s later → "isn't valid any
  more — ask for a new one"; (4) resend → "New code in 26s"; app force-stopped and reopened inside the
  cooldown, same address → the 429 rendered as the server line + a disabled countdown; Change email
  right after a send → address kept, "Send code in 20s"; (5) the 4th code within the hour → "Too many
  codes requested…" + "Send code in 57 min" disabled; a different address frees the button and sends;
  (6) curl X-Client-Time 3 h ahead → 200, header not echoed, server WARN "client clock is 10799 s ahead
  of ours on POST /api/v1/auth/otp/request (request_id=fda9dd2a-…)"; (7) "  TWINS-…@EXAMPLE.COM  " inside
  the cooldown of twins-…@example.com → 429 (one destination); two codes 31 s apart, two verifies in
  parallel → both 200, user 05df08ab-…, is_new_user once, users count 1; (8) `{"email":` → 400
  {body: malformed}, text/plain → {content_type: unsupported}, challenge_id "not-a-uuid" →
  {body: malformed}, no Java names; (9) server stopped → "You're offline… nothing you typed is lost."
  + Retry on the code step (digits kept) and on the entry step (address kept); Retry after the restart
  re-sent the request and reached the code screen; (10) the restarted server logged "retired 1 pending
  OTP code(s): the OTP pepper is per process…" and the old code answered OTP_EXPIRED on Retry. Port
  8081 was held by the founder's 09:14 server from the F10 SES proof (still running, on classes
  recompiled underneath it since) — left alone; the run used 8082.
spec-auditor (branch diff + the docs): PASS with 4 MINOR, all fixed on the branch before this docs
  commit — (1) a 429 RATE_LIMITED on verify (the per-address verify bucket) was mapped onto the resend
  cooldown, a D8 conflation the checklist tail described incompletely → verify leaves the resend clock
  alone, the notifier test and the checklist say so, a clause in the D9 cooldown DECISIONS row;
  (2) otp_challenges.updated_at still came from @UpdateTimestamp while created_at moved to IstClock →
  set from the app clock by every mutation; (3) TECH_PLAN §1.5 and §10.1 disagreed with the §3.1
  note on the filter order and the MDC keys → dated notes in both; (4) a stale "two client-only
  codes" comment in ApiFailure → three. The one unverifiable item worth closing — no Spring-context
  test of the boot path — got OtpStartupFlowTest (the proxied bean retires a pending code, verify
  answers OTP_EXPIRED). Left as recorded: the device observations live outside the repo.
CI (the PR's first run, founder-pasted log): 2 failures in AuthUnhappyPathsTest on the Linux runner
  only — Retry-After 21 for 20 and 3508 for 3507. Root cause reproduced on the Mac by starting the
  shared test clock on a sub-microsecond instant: Linux JDKs hand out nanosecond instants, Postgres
  rounds them up to the next microsecond, so created_at read back a fraction later than the clock
  that wrote it and the rounded-up wait crossed a second. Fix at the clock, not the test:
  IstClock.now() truncates to microseconds (TIMESTAMPTZ precision), IstClockTest pins it, and the
  flow clock now starts on 999,999,999 ns on purpose so the condition stays covered everywhere;
  verify 281 tests green (DECISIONS row, dated §11.1 note).
Doc conflicts surfaced in the plan (none blocked): PLAN D9 "resend limits / duplicate accounts" vs the
  D7 server halves → the app side, the race fix and the proofs; TECH_PLAN §3.1 / JwtService "clock-skew
  diagnostics, D9" vs the JWT's server-clock tolerance → kept separate, dated §3.1 note; §5.4 one
  client-only code → three (dated note); §10.2 gains auth.clock_skew; §3.4 row notes the client-side
  enforcement; §2.2 notes created_at from IstClock and the retirement; DEV_SPEC §6 "queue" → D8's one
  Retry, widened to any non-envelope failure; SPEC §3/§8 SMS + auto-read → the D7 ruling stands;
  app.md "no logic in widgets" → the seconds-vs-minutes label is a display branch over state getters.
Spec-silent choices: 8 DECISIONS rows dated 2026-09-09 · D9.
Parked: a reusable device driver under scripts/ (the uiautomator-by-label loop worked first time);
  the OTP email reads "expires in 0 minutes" for a sub-minute TTL (demo-only; format seconds).
Surprise: (1) @CreationTimestamp is VM time — mixing it with IstClock made the cooldown vanish under a
  movable clock, which is how AuthUnhappyPathsTest found the §11.1 gap; (2) uiautomator dump sees
  Flutter's semantics (labels as content-desc, fields as EditText), but `input text` only lands in a
  focused field and a verify round trip drops focus — tap the field and MOVE_END first; (3) a background
  `flutter build` launched from the repo root fails on "No pubspec.yaml", so the pre-fix APK was
  reinstalled once before the fix showed; (4) the founder's morning SES server still held 8081;
  (5) after the push: Linux nanosecond instants vs Postgres microseconds turned a 20-second wait into
  21 on CI — the clock now truncates (see the CI paragraph above).
Tomorrow's first task: D10 — POST /auth/logout (revoke the family), the empty student_profiles row on
  first login, GET /me and PATCH /me (language), the app's single-flight refresh interceptor
  (AUTH_EXPIRED → refresh once and replay, AUTH_INVALID → sign out) and the logout action; ✅ kill and
  reopen → still signed in, logout → clean state (TECH_PLAN §3.2, §3.7 account, §5.4).
PR #8 from d9-unhappy-paths merged to main by the founder 2026-09-09 (merge commit ba270af) after
  the second CI run went green; D9 closed on main.
```

```
D8 · 2026-09-08 → 2026-09-09 · PHASE 1 — Auth & identity (Flutter login screens)
Plan approved as written (8 tasks, 9 spec-silent decisions, 7 doc notes, 3 closing questions → the
  recommended option each: the AVD login against the local server is the ✅ reading until F8 gives a
  public endpoint; the /today placeholder is the signed-in landing; package_info_plus supplies
  X-App-Version). Email entry per the D7 ruling; SMS auto-read waits for F1. The day ran across the
  evening of the 8th and the morning of the 9th; the schedule delta is unchanged.
Shipped (branch d8-login-screens, 9 commits: 4a93ebd foundation, 702c3da ApiClient, 6c94ba8 token
  store + auth state, 5df8767 auth repository, e9265e4 LoginNotifier, 1b3ccf8 screens + router + copy,
  e10d2c9 field clear, 69a155a spec-auditor follow-ups, + the docs commit): core/ — AppConfig
  (--dart-define API_BASE_URL, default http://10.0.2.2:8081), AppLanguage (en|hi|hinglish ↔ Locale ↔
  Accept-Language) + localeProvider, AppTheme (M3, bottom-anchored 52 dp action), ApiClient (dio,
  /api/v1, 10 s connect / 30 s receive, X-Request-Id v4 UUID, X-App-Version, X-Client-Time,
  Accept-Language, bearer; envelope → ApiFailure with reason codes / attempts_left / retry_after /
  request_id; connection and timeout errors → OFFLINE, a non-envelope answer → MALFORMED), TokenStore
  (flutter_secure_storage 10.x, one blob) + AuthNotifier (unknown / SignedOut / SignedIn),
  FailureCopy (envelope copy → ARB by code; every D7 reason code), FailureLine, OneHandPage, go_router
  with the §5.3 guard as a pure function; features/auth — models (the D7 wire shapes), AuthRepository,
  LoginState / LoginNotifier (request, verify, resend after resend_after_s or retry_after_s, the sixth
  digit submits, change email, Retry after an offline failure, the once-a-second ticker owned by the
  notifier only while a cooldown runs, a sign-out starts the flow over), Splash, LoginScreen (/login),
  OtpScreen (/login/otp); features/planner — TodayPlaceholderScreen (/today); ARB en / hi / hi_Latn
  (46 keys each, parity enforced by a test); Android: INTERNET in the main manifest, debug-only
  cleartext to 10.0.2.2 / localhost / 127.0.0.1. 145 app tests (was 1): client 21, repository 5,
  notifier 20, both screens per state × 3 locales, guard, copy coverage, ARB parity, FailureLine and
  the placeholders per locale, the whole flow through the real router. Server untouched (verify green
  on every commit); no AI path touched (the eval stamp was re-run once, see Surprise 2).
Acceptance: ✅ PASS on the AVD margai_android36 (the founder's reading, plan question 1) against
  SERVER_PORT=8081 with the sandbox sender; APK built with --dart-define=API_BASE_URL=
  http://10.0.2.2:8081 (194 MB debug), driven by adb input + screencap (8 screenshots in the session
  scratchpad): (1) login screen — headline, intro, Email, Send code pinned at the bottom; the keyboard
  pushes the button up (adjustResize); (2) founder@example.com → Send code → server log "[sandbox
  email] to f***@example.com — Your MARG AI sign-in code is 565608"; (3) code screen — "I've sent a
  6-digit code to founder@example.com", numeric field, Verify, "New code in 26s", Change email;
  (4) typing the six digits submitted → "You're in. Signed in as founder@example.com." (server: "otp
  verified … new user: false" — the D7 curl user); (5) pm clear → new code 638204 → wrong code 000000 →
  "4 tries left." + the server's "That code didn't match. Try once more." in the error box, Verify
  still enabled; (6) server killed → "Send a new code" after the cooldown → "You're offline. Check your
  connection and retry — nothing you typed is lost." + Retry, digits and attempts kept, the old
  challenge on screen; (7) server restarted → Retry → new code 892847, countdown reset to 27 s, failure
  cleared (the stale digits stayed → e10d2c9); (8) Change email → entry screen with the email kept.
  Database (compose): users 555ef46d-… founder@example.com, en, student, active; 3 otp_challenges
  today (email, login; verified t / attempts 1 / fresh; request_ip 127.0.0.1); refresh_tokens family
  7b7107d9-… device_label "margai/0.1.0+1 android" — X-App-Version end to end. The raw email never
  appears in the server log (0 hits); the code appears only on margai.otp.sandbox. Not run: a phone
  over mobile data (no public endpoint; carried on the Week-2 gate line, the USB path is in
  app/README).
spec-auditor (branch diff): PASS with 8 MINOR — (1) stale digits after a resend → fixed in e10d2c9
  before the report landed; (2) "try once more" beside "no tries left" on the fifth wrong code → the
  attempts line alone carries the remedy ("— ask for a new one"), the failure is dropped; (3) "a
  missing ARB key fails a test" was not enforced (gen-l10n falls back to English silently) →
  arb_parity_test compares key sets, placeholders and plural cases across the three files;
  (4) TodayPlaceholder, INTERNAL with a request id and Splash untested per locale → three test files;
  (5) the resend decision and the clock choice lived in OtpScreen → LoginState.canResend /
  resendSeconds, the notifier owns the ticker subscription; (6) tickerProvider never disposed →
  autoDispose, subscribed only from a sent code until the flow leaves the code step; (7) signOut left
  loginProvider on a spent challenge → the notifier listens to authStateProvider and starts over;
  (8) docs wording (bearer read per call, 127.0.0.1, pubspec "en, hi") → corrected. All in 69a155a.
  Unverifiable items closed by the device run (login on a device; analyze + test green) or left as
  recorded: Keystore backing of flutter_secure_storage 10.x defaults (a platform fact, no test),
  PackageInfo awaited before runApp (device matrix at D74), the Hindi register (D67).
Doc conflicts surfaced in the plan (none blocked): PLAN D8 "number entry, auto-read, change-number"
  and SPEC §5/§8 auto-read vs the D7 ruling → email, no auto-read; TECH_PLAN §5.7 smart_auth at D8 →
  dated note moves it to the F1 day; PLAN D8 ✅ "real device over mobile data" vs no public endpoint →
  AVD proof + gate-line carry (founder question 1); §5.4 "connectivity fallback" vs §5.7
  connectivity_plus at D34 → dio error mapping now; §12.1 refresh interceptor in the D7–D12 row vs
  PLAN D10 "token rotation" → D10; CLAUDE.md "ARB (en/hi)" (DEV_SPEC §13.2 verbatim) vs app.md /
  §5.5 three locales → three; DEV_SPEC §6 "queue + clear errors" → no lost input, one Retry, no queue.
Spec-silent choices: 6 DECISIONS rows dated 2026-09-09 · D8 (offline from the failing request,
  failure copy precedence, no lost input / no queue, client plumbing, routing + the /today
  placeholder, Android build incl. the flutter_secure_storage 10.x pin).
Parked: narrow the gate's AI_PATHS to server/; the mobile-data proof (F8); flutter_secure_storage
  11.x + platforms;android-37.0 in dev-setup; a small adb driver for device proofs.
Surprise: (1) flutter_secure_storage 11 needs compileSdk 37 and Android 17 ships only as
  platforms;android-37.0 — AGP 9.1.0 fails with "Failed to find target with hash string
  'android-37'"; pinned 10.x (compileSdk 36). (2) The precommit gate's (router|routing|retriev) regex
  matches the Flutter router directory; the placeholder eval stamp clears it. (3) Riverpod 3 pauses a
  provider's own ref.listen subscriptions while nothing listens to that provider — notifier tests
  need a container.listen keep-alive, exactly what the screen provides in the app. (4) pumpAndSettle
  never settles on an indeterminate LinearProgressIndicator — busy states pump one frame. (5) The soft
  keyboard moves the bottom-anchored button; scripted taps need a screenshot first.
Tomorrow's first task: D9 — unhappy-path hardening, the 10-failure checklist (wrong code ×5,
  expiry, resend cooldown and the hourly cap, clock skew via X-Client-Time, duplicate accounts and the
  simultaneous-first-login race noted at D7, malformed body, offline on each step, a dead code after
  restart since secrets are per boot) — most render through today's FailureLine; the RATE_LIMITED
  countdown on the entry step and the INTERNAL reference want a device check.
PR #7 from d8-login-screens merged to main by the founder 2026-09-09 (merge commit 55aea8b); D8
  closed on main.
```

```
D7 · 2026-09-08 · PHASE 1 — Auth & identity (OTP request/verify + rate limits + tokens)
Founder ruling at the plan review: SMS OTP is not possible yet — the DLT template (F1) needs a
  registered company. Three choices, taken from the options offered: (1) email joins phone as a
  VERIFIED login identifier (an unverified phone + an emailed code would let anyone claim another
  person's number); (2) delivery through AWS SES v2 over the SDK default chain, no secrets;
  (3) a temporary deviation — SPEC §3/§5 untouched, DECISIONS row with exit condition F1, dated
  in-place TECH_PLAN amendments, TRACKER notes, new founder workstream F10. The plan was then
  approved as written; its four closing questions took the recommended option each (disabled
  channel → VALIDATION_FAILED on the field; sender failure → row deleted + INTERNAL; missing
  secrets → random per boot + WARN; exhausted attempts → OTP_EXPIRED, no new §3.3 code).
Shipped (branch d7-otp-auth, 11 commits: 34ff524 V6 + entities, 108c0c4 common web foundation,
  c5bffb3 security chain + JWT, 2072cf6 rate limits, f9c613b tokens + account port, f4f4951
  OtpService, 387ab4e SES sender, fef25ed controller + flow tests, 6a96db6 spec-auditor fixes,
  5f054ea re-audit residual, + the docs commit): V6 auth (users.email + identifier check, otp_challenges channel/destination,
  refresh_tokens); common.api ErrorCode / sealed ApiException / ErrorEnvelope / ErrorResponses /
  Messages / RequestLanguage / Principal (+ Language, UserRole moved in), RequestIdFilter,
  ApiExceptionHandler, messages_{en,hi,hinglish} for all 21 codes + OTP mail copy; auth: HS256
  JwtService (15 min, sub/role/lang/jti, previous key while configured), stateless chain with
  envelope-writing entry point, PrincipalContextFilter (MDC user_id/jti), Bucket4j RateLimitFilter
  (10/h per address on request, 60/min per address on verify+refresh, 60/min per user), TokenService
  (256-bit opaque refresh, SHA-256 at rest, per-device families, sliding 30 d, reuse revokes the
  family), OtpService (channels gate, 30-s cooldown, 3/h per destination, store-then-send with
  cleanup, 5 attempts under a row lock, sign-in + tokens), Identifiers (Indian mobiles → E.164,
  emails lowercased, masks), OtpSender port with LoggingOtpSender (sandbox on logger
  margai.otp.sandbox) and SesOtpSender (auth.internal.email, only importer of the SES SDK),
  AuthController (POST /auth/otp/request {phone}|{email}, /auth/otp/verify, /auth/refresh);
  account.api Accounts/LoginIdentifier/UserSummary/SignIn + AccountService. Config margai.auth.*,
  margai.limits.*; blank secrets → ephemeral + WARN. 262 tests (was 146), 0 failures, 1 skipped
  (Bedrock smoke); flutter analyze/test unchanged and green; no AI path touched (eval stamp intact).
Acceptance: ✅ PASS — "Happy path via curl", run literally on the compose db (SERVER_PORT=8081,
  sandbox sender), transcript: Flyway "Migrating schema public to version 6 - auth" … "now at
  version v6"; WARN margai.auth.jwt.secret / otp.pepper not set (ephemeral); health UP.
  POST /api/v1/auth/otp/request {"email":"Founder@Example.com"} → 200 X-Request-Id 118ddb08-…
  {"challenge_id":"50b686be-…","resend_after_s":30,"channel":"email"}; server log
  "[sandbox email] to f***@example.com — Your MARG AI sign-in code is 444771. It expires in 5
  minutes." POST /otp/verify → 200 {"expires_in":900,"is_new_user":true,"user":{"id":"555ef46d-…",
  "email":"founder@example.com","language":"en","role":"student"},"access_token":"eyJhbGciOiJIUzI1NiJ9…",
  "refresh_token":"Hq7vmsrMsgPJ…"}; JWT claims {sub 555ef46d-…, role student, lang en, iat/exp 900 s
  apart, jti b0686133-…}. POST /refresh → 200 new pair. Negative demo: old refresh token again →
  401 AUTH_INVALID, the fresh one dead with it (family revoked); {"phone":"9876543210"} → 400
  VALIDATION_FAILED details.phone (channel not enabled); no token on /api/v1/probe/whoami with
  Accept-Language: hi → 401 AUTH_REQUIRED "जारी रखने के लिए साइन इन करें।"; same email inside 30 s →
  429 Retry-After: 30 OTP_RATE_LIMITED; after two more codes 31 s apart, the 4th → 429
  Retry-After: 3507. psql: 3 otp_challenges rows (email, founder@example.com, login, code_hash
  281648ed256d…, attempts 0, verified t/f/f, request_ip ::1); users row 555ef46d-… email set, phone
  NULL, en, student, active; 2 refresh_tokens in family c5bcbd3c-… device_label curl/acceptance,
  both revoked, first replaced; flyway_schema_history 6 auth success. The code 444771 appears once
  in the whole server log, on margai.otp.sandbox; auth INFO lines show f***@example.com only;
  0 ERROR lines. Server stopped cleanly.
spec-auditor (branch diff + docs): FAIL — [MAJOR] English prose in details values (SPEC §3,
  TECH_PLAN §3.1) → details now carry reason codes (phone.invalid, email.invalid,
  identifier.required/one_only, channel.unavailable, code.digits, not_blank/not_null/min/size,
  body: malformed, content_type: unsupported) rendered by the app's ARB; [MAJOR] first
  X-Forwarded-For hop is client-chosen behind an ALB in append mode → last hop; [MINOR] /error
  route unrecorded → §1.5 + DECISIONS; [MINOR] no anonymous bucket on verify/refresh → 60/min per
  address; [MINOR] §1.1/§1.2/§2.9/§7.2/§7.4/§7.7/§0.4 still SMS-only → dated amendments + §0.4
  item 10; [MINOR] no completeness test for error copy, 10 codes without copy → copy authored,
  MessageCatalogTest. All fixed in 6a96db6. Re-audit (scoped to the six): PASS, every finding
  closed; one MINOR residual — the reason-code rule judged "code, not prose" by the absence of a
  space in the interpolated message, which a non-English validator bundle (Accept-Language: ja)
  could defeat — closed in 5f054ea by deriving the code from the constraint's raw message template
  instead (locale-independent), with a `ja` case in ApiEnvelopeTest; a stale test javadoc fixed. Unverifiable by the auditor and left as recorded requirements: the ALB's append mode (F8),
  D8's ARB entries for every reason code.
Doc conflicts surfaced (none blocked): SPEC §3/§5/§8 screen 1 (SMS, auto-read) vs the DLT
  reality → founder ruling above, SPEC untouched; TECH_PLAN §2.2 users.phone NOT NULL while active
  → V6 "phone or email"; §1.4/ArchUnit banned the whole SDK outside bedrock → scoped per service;
  §3.7 "creates users + profile at D10" → users at D7 (JWT sub), profile D10; DEV_SPEC §5 verify
  {phone, code} → TECH_PLAN {challenge_id, code}; §3.3 OTP_INVALID/EXPIRED at 401 → followed;
  §9.2 ".env" → exported env vars; "secret_previous for 15 min" → while configured; §3.4 names
  Bucket4j → bucket4j_jdk17-core + Caffeine; PLAN D8 auto-read / D11 DLT / gate "phone" → email
  readings in the PHASE 1 list; §9.1 "codes never logged" vs the sandbox line → binds the
  service, the sandbox logger is the inbox.
Spec-silent choices: 11 DECISIONS rows dated 2026-09-08 · D7 (the deviation + exit, SES, Principal
  in common.api, ephemeral secrets, identifier normalisation + hash, OTP outcomes, sandbox logger,
  users row at D7, sliding rotation, rate-limit keys + /error + ArchUnit scope, copy + reason codes).
Parked: phone-attach by OTP once F1 lands; email canonicalisation (Gmail dots/plus); logout +
  revoke-on-deletion scheduling note; refuse the log sender outside local/test.
Known edges (not blocking, noted): two simultaneous first logins for one new email race the
  partial unique index into a 500 (client retries); an access token stays valid up to 15 min after
  account deletion (D64 decides whether to check per request); LoggingOtpSender is the default when
  MARGAI_AUTH_OTP_SENDER is unset — F8's SSM seeding must set ses (PARKED startup refusal).
Surprise: (1) MessageFormat only doubles apostrophes when arguments are passed — one authoring
  rule needs alwaysUseMessageFormat. (2) Spring's JwtTimestampValidator judges expiry on its own
  clock: wire the app clock or fixed-clock tests silently pass/fail with wall time. (3) Bean
  Validation names Java fields and speaks English — both must be translated at the envelope
  (wire names + reason codes) or the contract leaks Java into the app. (4) A @WebMvcTest in
  another package cannot import common's package-private beans; include them by a scan filter.
  (5) A wait-loop with sleep must run in the background in this harness.
Tomorrow's first task: D8 — Flutter login screens for email (entry, code entry with retry and
  change-email, offline-tolerant errors, the reason codes → ARB strings), on the emulator against
  SERVER_PORT=8081 (base URL http://10.0.2.2:8081, TECH_PLAN §5.4); the real-device check reads the
  code from the sandbox log unless F10 is done. The founder's F10 (SES identity + test recipients).
PR #6 from d7-otp-auth merged to main by the founder 2026-09-08 (merge commit af3adb3); D7 closed
  on main.
```

```
D6 · 2026-09-08 · PHASE 0 — Foundations (buffer + Week-1 gate)
Shipped (branch d6-week1-gate, 4 commits, docs and comments only — no feature code, migration or
  prompt change): (1) TECH_PLAN §0.3 dispositions closed (the §12.1 D6 row): the §2.1 row records
  console checks #1/#4, the §6.1 row records Option A (§0.5 item 1b, clause + test at D34), §4.9's
  embed bullet records check #4, and a closing note under the §0.3 table states what stays
  scheduled (eval arrangement at D23, Anthropic live proof after the AWS ticket). §14 checked
  against DECISIONS.md: 25 of 25 rows present. (2) Root README names docs/TECH_PLAN.md (missing
  since D3) and describes pipeline/ per D3.4; server README, BedrockSmokeTest and
  BedrockConfiguration javadocs and the pom comment say "SDK default chain (aws login session or
  AWS_PROFILE)" instead of "AWS SSO profile", with a pointer to the identity TECH_PLAN §7.4
  intends (F8) and a DECISIONS D6 row. (3) spec-auditor follow-ups (below).
Acceptance: 🚩 WEEK-1 GATE PASS — run as a demo script, verdict rule fixed in the approved plan
  (PASS = every pillar demonstrated in-session; the founder-run Bedrock call is cited from D5):
  repo — git status clean; bash -n scripts/*.sh eval/run.sh OK; detect-secrets --scan-tree exit 0;
    scripts/precommit-gate.sh with a scratch server/GateDemo.java holding a marker → "❌ todo-markers
    … ⛔ COMMIT BLOCKED" while secrets/eval/server/app stayed ✅; file removed → "✅ precommit gate
    passed" (secrets, todo-markers, eval-gate, mvnw verify, flutter analyze all ✅).
  environment — compose db healthy (PostgreSQL 18.6, up 5 days); cd server && ./mvnw verify: 136 run,
    0 failures, 0 errors, 1 skipped (BedrockSmokeTest without BEDROCK_LIVE) in 30 classes; cd app &&
    flutter analyze "No issues found", flutter test 1/1; SERVER_PORT=8081 ./mvnw spring-boot:run on
    the compose db → profile local, Flyway "Schema public is up to date" (V5), "AiClient chain:
    ledger > breaker > tier-policy > schema > retry > fake", "Started MargaiApplication in 2.268
    seconds", /actuator/health {"status":"UP", db UP}, graceful shutdown on kill; AVD
    margai_android36 booted, flutter build apk --debug (9.4 s), adb install + am start -W
    com.margai.app/.MainActivity "Status: ok", screencap shows "MARG AI" (scratch screenshot).
  plan — TECH_PLAN line 3 "APPROVED 2026-09-04 by the founder"; CLAUDE.md precedence list has it at
    position 3; §0.3 closed today; §14 25/25 in DECISIONS (70 rows).
  schema — compose db flyway_schema_history: V1 extensions, V2 identity, V3 curriculum core, V4
    chapter status, R test taxonomy ×2, V5 ai calls, all success; 9 tables + flyway_schema_history;
    extensions vector 0.8.6, pg_trgm 1.6; MigrationReversibilityTest 1/1 inside verify.
  AI seam — 94 tests green across 19 ai classes (AiSeamFlowTest 5, ledger 8, breaker 5, tier policy
    6, schema 5, retry 6, structured output 6, fake 7, prompt registry 5, cost 7, properties 5,
    Bedrock client 6, Converse mapper 4, documents 2, ai_calls constraints 8 + repository 2, route
    decision 4, on-demand batch 3) plus ArchitectureTest 4, ModelIdLiteralTest 2, ModularityTest 2;
    the chain log line above; D5's live rows on Bedrock (Nova Lite, two ok rows, 5,976-token cache
    write then read) stand as cited — the same proof on the Anthropic profiles is the open account
    item in the dashboard.
  eval + audit — cd eval && ./run.sh: placeholder PASS, 0 fixtures, stamp written (the /evalgate
    half of DEV_SPEC §13.7 item 7); spec-auditor on git diff main...d6-week1-gate: PASS with 3
    MINOR findings, all fixed in e5633d1 — (1) the §0.3 closure note misattributed completeBatch to a
    "D5 amendment" of DECISIONS D3.18 (git history: the row named it at the D3 commit; the §14
    table row is the abbreviation); (2) "every verdict is final" over-closed the §4.5 row (D23) and
    the Anthropic live proof — both now named; (3) the "default chain" wording replaced "SSO
    profile" without a pointer to §7.4/F8 or a record — README pointer, pom comment, DECISIONS row.
Founder decisions: the plan was approved as written; its four closing questions took the
  recommended option each — PASS rule with the D5 citation, emulator run in the gate, negative gate
  demo, "SSO" wording fixed in the README + two javadocs only (TECH_PLAN §1.2/§7.4/§7.6 keep the
  intended identity).
Doc conflicts surfaced in the plan (none blocked): DEV_SPEC §13.7 item 7 cites "SPEC §10" for the
  weekly acceptance criteria (SPEC §10 is the personalization charter; the criteria were DEV_SPEC
  §10, superseded by PLAN) — reading applied: the gate is PLAN §3's 🚩 line, as /week step 7 says;
  DEV_SPEC stays historical. PLAN D6 has no ✅ of its own — ticked on the gate verdict plus the
  §12.1 D6 deliverables. PLAN §1 "Fridays end with the gate" vs a Tuesday D6 — day numbering
  governs. TECH_PLAN §13.2 item 1 "closed" vs the reopened live proof — consistent, both named.
Spec-silent choices (process, recorded here): the gate is commands + a pasted transcript, no
  committed gate script; §0.3 rows keep their D3 verdict words and gain the settlement; developer
  credentials documented as the SDK default chain (DECISIONS D6 row).
Parked: a reusable week-gate script if the transcript shape grows tedious; adb on PATH via
  dev-setup.sh (today only the full platform-tools path works).
Surprise: (1) A settlement note is easy to get subtly wrong from the §14 table alone — the
  spec-auditor caught a misattribution that only git history settles; keep checking DECISIONS
  provenance with git log -S, not by reading. (2) flutter emulators --launch from a background
  shell survives and boots in ≈ 60 s; adb install + am start -W + screencap is a deterministic
  device proof with no interactive flutter run. (3) A clean gate run costs ≈ 1 min (mvnw verify
  ≈ 40 s warm). (4) The compose db's ai_calls is empty: the D5 smoke ran on Testcontainers, so a
  live row on the compose db needs a BEDROCK_LIVE=1 API run once an endpoint calls SmokeTask.
PR #5 from d6-week1-gate merged to main by the founder 2026-09-08 (merge commit 11e50bb); Week 1
  closed on main.
Tomorrow's first task: D7 — OTP request/verify + rate limits + tokens (PLAN D7 ✅ curl happy path)
  from TECH_PLAN §3.2 tokens, §3.4 rate limits, §3.7 auth endpoints, §2.2/§2.9 V6 auth tables,
  §9.1; DEV_SPEC §5 as reference; the /endpoint skill for controller + service + MockMvc test;
  the fake SMS adapter in the local profile (§1.2). When the AWS ticket clears: the Anthropic-profile
  smoke rerun and the Nova price row (dashboard).
```

```
D5 · 2026-09-06 · PHASE 0 — Foundations
Shipped (branch d5-ai-seam, 9 commits, ≈ 93 files): the AI seam of TECH_PLAN §4.1. ai module
  (allowed common :: api, curriculum :: api; api + tasks named interfaces). V5 ai_calls drafted by
  db-migrator from §2.8 verbatim (append-only, rollback block) + AiCall entity/repository with the
  IST-day spend sums. ai.api: AiClient (complete, completeBatch as the §4.11 on-demand loop, embed),
  AiRequest (+ repair), AiResponse (+ attempts), Usage, AiFeature ×20, Tier, RouteDecision with its
  three factories, RouterVerdict, PromptRef, ImagePart, AiCallContext, EmbedRequest, Repair,
  AiClientInfo, the typed failures. margai.ai.* config validated at startup (tier ids, embed model,
  prices-json, usd-inr 90, budgets ₹25 user / ₹500 global per IST day, batch minimum 100,
  max-output-tokens, call-timeout 20 s, prompt versions; every configured model must be priced).
  PromptRegistry over StringTemplate 4 group files prompts/<name>.v<N>.stg (system = the cached
  prefix, user) plus _protocol.v1.stg fragments; smoke.v1 with a ≈ 5,500-token NEET syllabus prefix
  for the cache proof. StructuredOutput (victools 5 + networknt 3 on Jackson 3: snake_case, required
  unless Optional, no extras) shared by the forced Bedrock tool and the validator. FakeAiClient with
  fixtures (case by variable or deterministic hash, _ failure cases, .repaired.json, realistic usage
  with a simulated prompt cache, the configured model id, deterministic unit embeddings). Decorators
  Retrying (2 jittered retries on throttling/5xx), SchemaValidating (one repair turn), TierPolicy,
  BudgetBreaker (user + global), Ledger (row per outcome, cost at insert, ai.calls / ai.cost.paise /
  ai.latency / ai.attempts). Chain ledger > breaker > tier-policy > schema > retry > fake|bedrock.
  BedrockAiClient + ConverseRequestMapper (system + cachePoint, forced tool with the record schema,
  images, repair turns, temperature 0) + Documents + BedrockConfiguration (@Profile bedrock, SDK
  retries off, configured region and timeout) + InvokeModel embeddings (Cohere and Titan shapes).
  MargaiApplication adds the bedrock profile on BEDROCK_LIVE=1. SmokeTask. ArchitectureTest
  (ArchUnit: the SDK only in ai.internal.bedrock, AiClient only inside ai, router(...) only from
  DifficultyRouter, controllers in web) + ModelIdLiteralTest. BedrockSmokeTest (founder-run).
  Rule edit per §0.2 (ai-layer.md, three lines), server README "AI seam" section, 20 DECISIONS
  rows, prompt-changelog rows, the precommit gate now scans .stg. 136 tests in 30 classes (1 skipped
  by design), ./mvnw verify ≈ 25 s warm.
Acceptance: PASS (2026-09-08) —
  (a) "App runs fully on FakeAiClient": PASS. ./mvnw verify green (136 tests); AiSeamFlowTest drives
      SmokeTask through the whole chain on the fake (ok row with tokens and cost, breaker row for a
      capped user, TierPolicyException row, a row that survives a rolled-back caller transaction);
      SERVER_PORT=8081 ./mvnw spring-boot:run on the compose db: Flyway "Migrating schema public to
      version 5 - ai calls", "AiClient chain: ledger > breaker > tier-policy > schema > retry > fake",
      "Started MargaiApplication in 2.025 seconds", /actuator/health {"status":"UP", db UP}.
  (b) "one live call logged with token counts": PASS on 2026-09-08 10:35 IST, founder-run
      BedrockSmokeTest with BEDROCK_LIVE=1 and MARGAI_AI_TIER_CHEAP=apac.amazon.nova-lite-v1:0
      (the Anthropic profiles are refused by the account, see below). Chain logged
      "ledger > breaker > tier-policy > schema > retry > bedrock". The two ai_calls rows:
        smoke | apac.amazon.nova-lite-v1:0 | smoke v1 | ok | in=29 out=20 cache_read=0
          cache_write=5976 | 1347 ms | 1 paise
        smoke | apac.amazon.nova-lite-v1:0 | smoke v1 | ok | in=29 out=20 cache_read=5976
          cache_write=0 | 650 ms | 1 paise
      Proven: credentials, region, the Converse mapping, the forced tool (the record came back with
      the requested numbers), real token counts, a 5,976-token cached prefix written on the first
      call and read on the second, input tokens excluding cache tokens (the cost formula's
      assumption), one ledger row per call. Open: the same proof on the Anthropic profiles
      (TECH_PLAN §13.2 item 1) once the account's Marketplace subscription is unblocked.
      The road there, 2026-09-07/08: run 1 failed before AWS — the `aws login` session
      (login_session in the default profile) needs the SDK signin module; added at runtime scope
      (be867be). Run 2 reached Bedrock: 403 AccessDeniedException "INVALID_PAYMENT_INSTRUMENT …
      AWS Marketplace subscription for this model cannot be completed" — the account's payment
      method, not code; a CLI converse on the same model fails identically; AWS support ticket
      raised by the founder. A Bedrock API key (AWS_BEARER_TOKEN_BEDROCK) was tried; it expired
      and, while exported, overrides the login session for every Bedrock call ("Bearer Token has
      expired" even after aws login) — unset. A direct Anthropic API fallback was considered and
      declined (SPEC §3, CLAUDE.md stack line; nothing needs a live model before D14). A CLI
      converse on Nova Lite succeeded, so the smoke ran on it with a diagnostic price row
      (1e0bed2). Run 3 on Nova was green on every assertion but cost: 0.06 / 0.38 paise per call
      rounded to zero → cost_paise now rounds up (3b68d91, DECISIONS). Run 4 green. The failed
      runs also proved the failure path: permanent classification, no retries, ledger rows
      status=error with codes SdkClientException and AccessDeniedException (§4.13 "every outcome").
  spec-auditor on the branch diff: PASS, 10 minor findings. Fixed in 2b5d3d9: model-facing text
  moved to prompts/_protocol.v1.stg; embed tokens header → body → estimate, never zero; Optional
  record components optional/nullable in the schema (the D37 router verdict would otherwise have
  failed its own schema); ArchUnit rule ≡ rule text; changelog header .stg; the gate scans .stg;
  attempts counted (ai.attempts). Recorded in DECISIONS rather than changed: one ledger row per
  request with retries/repair folded (§4.1), the AiRequest/PromptRef shape, breaker at ≥ cap, the
  §4.11 constants.
Founder decisions: the plan was approved as written; the five closing questions took the
  recommended option each — usd_inr 90, global cap ₹500/day, two smoke calls, all three rule-file
  lines, victools + networknt.
Doc conflicts surfaced in the plan (none blocked): DEV_SPEC §3.4 ai_calls and §4.1 AiClient vs
  TECH_PLAN (TECH_PLAN wins, §0.3); ai-layer.md's three stale lines (edited today per §0.2 + Q4);
  PLAN "one live call" vs §13.2's second-call cache proof (two calls; DECISIONS); Haiku 4.5's cache
  minimum not in the retrievable Bedrock cards (prefix sized above 4,096 tokens; the second smoke row
  is the evidence either way); TECH_PLAN's CHEAP|REASON casing vs the D4 lowercase rule (D4 rule
  applied); the gate's extension list lacked stg (fixed today rather than at D23);
  "BEDROCK_LIVE=1 profile" wording vs an env var that adds the profile (DECISIONS).
Deviations from the approved plan, recorded in DECISIONS: .stg group files (plan said .st) and a
  _protocol fragment group; AiRequest.repair, name-only PromptRef, the InnerAiClient wrapper so
  only the bedrock package imports the SDK; RouteDecision.router(...) confined by ArchUnit, not the
  compiler; completeBatch as an interface default; timeouts not retried; Optional components.
Parked: retry count / backoff / on-demand concurrency as margai.ai.* config; per-attempt ledger
  rows if ops ever needs them.
Surprise: (1) Boot 4.1 is on Jackson 3 (tools.jackson); victools 5.0.0 and networknt 3.0.7 are the
  Jackson-3 ports and worked first time, but Jackson 3 hides SnakeCaseStrategy.translate (own
  helper). (2) detect-secrets refuses any identifier containing the word TOKEN that holds a string
  value, even a response-header name; the Bedrock input-count header constant was renamed to
  INPUT_COUNT_HEADER (memory note hook-quirks). (3) ST4's lexer trips on a value expression right
  before the closing >> of a template: put templates on their own lines. (4) Jackson node classes
  differ after a Document round trip (IntNode vs LongNode) while the JSON is identical — compare
  text. (5) ArchUnit 1.4.2 was already on the test classpath via Modulith. (6) ≈ 5,200 insertions
  for a "seam" day: the chain, the Bedrock mapping and their tests are the bulk, as §4.1 implied.
  (7) The live path needed two things the plan did not foresee: the SDK signin module for an
  `aws login` session, and a payment instrument the account turned out not to have — a CLI probe
  that worked on 2026-09-06 stopped working the next day. (8) An exported Bedrock API key silently
  overrides the login session for the CLI too. (9) Sub-paisa calls exist (Nova Lite) and HALF_UP
  hid them from the breaker. (10) The cached prefix is 5,976 tokens, above the ≈ 5,500 estimate.
  (11) PR #4's first CI run was red: the GitHub runner ordered the test classes differently and
  AiCallRepositoryTest's global-spend assertion counted rows AiSeamFlowTest had committed into the
  shared per-JVM test database (3,833 vs 1,250 paise). Global aggregates in slice tests are now
  asserted as deltas against a baseline; user sums already used fresh users.
PR #4 from d5-ai-seam merged to main by the founder 2026-09-08 (merge commit 0b70047): CI red on
  the first run (test order, surprise 11), green after acd679c; the server job's first AWS SDK
  download passed without AWS access, the live smoke skipped as designed.
Tomorrow's first task: D6 — buffer + the Week-1 gate as a demo script ("repo, env, plan, schema,
  AI seam in place"), TECH_PLAN §0.3 dispositions closed and §14 checked against DECISIONS.md
  (§12.1 D6 row). When the AWS ticket clears: rerun the smoke on the Anthropic profile and close
  §13.2 item 1's live proof; confirm or drop the Nova price row.
```

```
D4 · 2026-09-06 · PHASE 0 — Foundations
Shipped (branch d4-core-schema, 8 commits): V1 extensions (vector, pg_trgm), V2 identity (users,
  student_profiles), V3 curriculum_core (syllabus_nodes, syllabus_prerequisites, archetype_tracks,
  archetype_track_steps, cutoffs), V4 chapter_status — column-complete per TECH_PLAN §2.2–§2.4,
  VARCHAR + CHECK enumerations, ON DELETE RESTRICT, named constraints/indexes, a -- ROLLBACK: …
  -- END ROLLBACK block in every header. Drafted by the db-migrator agent in one call and reviewed
  column by column; no rewrite was needed. db/seed/R__test_taxonomy.sql (2 subjects, 2 units,
  6 chapters, 2 topics, 4 prerequisite edges, one dropper track with 7 steps, 3 synthetic cutoffs;
  fixed UUIDs, idempotent upserts) behind the local and test profiles; ./mvnw spring-boot:run
  activates local via the Maven plugin. Eight JPA entities + Spring Data repositories in the
  account/curriculum/practice internal packages, shared value enums (AttemptType, Category) in
  common.api; lowercase enum constants = DB/wire codes. Spring Modulith 2.1.1 with per-module
  allowedDependencies (§1.4) + ModularityTest; TestcontainersConfiguration (one container per JVM,
  one database per active-profile set); MigrationReversibilityTest (§8.2; also rejects PostgreSQL
  enum types, D3.5); SeedTaxonomyTest (graph acyclic); three @DataJpaTest constraint slices; the
  boot test now proves ddl-auto: validate for every entity and seed absence without a profile.
  30 tests in 7 classes; a full ./mvnw verify takes ≈7 s warm on this Mac (one shared container).
  Rule edits per §0.2 (server.md, db-migrator.md incl. the enum-line fix),
  9 DECISIONS rows, server README + db/migration README rewritten.
Acceptance: PASS —
  (a) "schema matches approved plan": validate green for all eight entities in every Spring test;
      compose db after spring-boot:run (local): flyway_schema_history V1–V4 + R test taxonomy,
      8 tables, extensions vector + pg_trgm; information_schema/pg_constraint/pg_indexes dump
      compared line by line with §2.2–§2.4 — every column, type, default, CHECK list, partial
      unique, foreign key and index present. Additions beyond the plan text, all harmless:
      syllabus_nodes class_level CHECK (11|12), cutoffs_category_check,
      syllabus_prerequisites_to_node_id_idx.
  (b) "migrations reversible": MigrationReversibilityTest — empty database → V1–V4 → rollback
      blocks newest first → only flyway_schema_history remains, only plpgsql among extensions,
      no sequences/views. Green.
  spec-auditor on the branch diff: PASS, 3 minor findings, all fixed before the close —
  (1) the seed upserted ON CONFLICT (code) while rows carry fixed ids, so editing a code would
  have broken re-application → upserts now key on the id, seed's unexecuted rollback block
  removed, DECISIONS row and db-migrator.md reworded — proven on the compose db, which already
  held the old seed: the changed checksum re-ran the repeatable migration through the id-keyed
  upserts (second "test taxonomy" history row, still 12 nodes / 7 steps); (2) MigrationReversibilityTest split undo
  SQL on ';' although the convention says one statement per line → header blocks run line by
  line, U-files are handed to the driver whole; (3) five constraints had no slice test
  (class_level CHECK, prerequisite and step foreign keys, unique track code, chapter_status node
  FK) → seven tests added, one violation each (a second violation in the same test only sees
  "current transaction is aborted"); 30 tests in 7 classes.
Founder decisions: the plan was approved as written; its four closing questions were answered
  with the recommended option each — scope incl. chapter_status, lowercase enum codes,
  db-migrator drafts the DDL, all four rule-edit lines.
Doc conflicts surfaced in the plan (none blocked): db-migrator.md still allowed PostgreSQL enum
  types vs D3.5 (fixed today); DEV_SPEC §3 vs TECH_PLAN §2 column differences (TECH_PLAN wins by
  §0.3, no action); PLAN D4 one-liner vs TECH_PLAN §2.9 adding chapter_status (in scope).
Deviation from the approved plan, recorded: the `common` module exists from D4, not D5 —
  AttemptType and Category are stored by both account and curriculum, which §1.4 forbids from
  depending on each other; DECISIONS row. ArchUnit still waits for D5 (DECISIONS row).
Parked: none new.
Surprise: (1) Spring Boot stops a @ServiceConnection container bean whenever a context closes —
  including one that failed to start — so the shared-container pattern must keep the container
  outside Spring's lifecycle (DynamicPropertyRegistrar). (2) Contexts with different Flyway
  locations cannot share one database: the test profile's applied R__ migration fails validation
  in a no-profile context → one database per active-profile set inside the container.
  (3) PathMatchingResourcePatternResolver throws on a `classpath:` root that does not exist yet
  (db/rollback/); `classpath*:` tolerates it. (4) Hibernate 7.4 validated every mapping first time:
  Instant ↔ TIMESTAMPTZ, CHAR(2) via @JdbcTypeCode(CHAR), JSONB as String, TIME ↔ LocalTime,
  a record as @EmbeddedId. (5) Modulith 2.x has getIdentifier(), not getName(), on ApplicationModule.
PR #3 from d4-core-schema merged to main by the founder 2026-09-06 (merge commit 3f77d6f) — the
  first CI run that downloads Spring Modulith and runs the Testcontainers suite on a GitHub
  runner; CI green (founder-verified 2026-09-06).
Tomorrow's first task: D5 from TECH_PLAN §4.1 (AiClient v2 + FakeAiClient +
  decorator chain), §4.8 ledger and breaker, §2.8 ai_calls as V5 (append-only: no updated_at),
  §1.2 bedrock profile, the D5 rule edit (§0.2: ai-layer.md RouteDecision wording), ArchUnit's
  first rule (only ai imports the Bedrock SDK), and the one live smoke call — which needs console
  checks #1, #2, #4 closed by the founder first.
```

```
D3 · 2026-09-03/04 · PHASE 0 — Foundations
Shipped (branch d3-tech-plan, 10 commits): docs/TECH_PLAN.md — Technical Plan v1.0, ≈1,950 lines.
  §0 precedence, DEV_SPEC §2–12 disposition table, nine surfaced conflicts/gaps. §1 modular monolith:
  17 modules with owned tables and an acyclic dependency graph, run modes as Spring profiles, request
  lifecycle, nightly execution, cross-module events. §2 data model: ≈45 tables, the D4 slice column-
  complete, one Flyway migration per PLAN day (V1–V31), retention and deletion. §3 API: token model,
  envelope + code catalog, rate limits, idempotency, polling until D69, every endpoint with its PLAN
  day and shape. §4 AI: AiClient v2 (two primitives + decorator chain), RouteDecision for REASON, the
  11-stage doubt pipeline with the enforcement point of each hard rule, limits and fair use, nightly
  planner with deterministic candidates and fallback, classification, SRS variants, ledger and
  breaker, hybrid retrieval, two-layer eval harness, Bedrock specifics, prompts, hard-rule map. §5
  Flutter: layers, Riverpod without codegen, go_router, dio, Hinglish as hi_Latn, drift outbox with
  the offline-verdict options. §6 content pipeline: Java module under the pipeline profile, VISION
  extraction, commands mapped to D13–D23. §7 AWS beta stack, SSM layout, IAM, backups, F8 timeline,
  cost. §8–§11 testing, security/DPDP, observability, conventions. §12 PLAN mapping and gaps. §13
  risks, console checks, single-instance assumptions, eight founder decisions. §14 25 decisions for
  DECISIONS.md.
Acceptance: PASS — TECH_PLAN v1.0 APPROVED by the founder 2026-09-04 (status line flipped, commit
  on d3-tech-plan). spec-auditor: pass 1 FAIL (1 blocker: the correct_key rule had been silently
  narrowed; 5 major; ~15 minor), pass 2 FAIL (2 major; ~20 minor), pass 3 PASS (14 minor wording
  items, all folded in). Three rounds, ≈60 findings addressed.
Founder decisions at approval (TECH_PLAN §0.5), one line each:
  1a correct_key reading ACCEPTED — never sent before that student's answer is recorded server-side;
     CLAUDE.md rule 1, server.md, endpoint.md, spec-auditor.md and PLAN D31 ✅ reworded today.
  1b Offline verdicts: OPTION A — pack carries judging data for the student's own day only,
     obfuscated (best effort), wiped after sync; server re-judging authoritative; D34 adds the
     clause + the only-pre-answer-carrier test.
  2  Mocks SCHEDULED at D35 (kind=mock), autopsy in D54's buffer; slips to the slippage log.
  3  Batch sync: self-report at D25, timetable doc_type at D29; weekly confirm card PARKED.
  4  PostHog error tracking ACCEPTED within the three-SDK rule; revisit D73.
  5  F8 infra workstream ACCEPTED (§7.6 timeline); Terraform drafted in a separate infra session
     profile (plan allowed, apply denied) created when the first milestone is due; founder applies.
  6  Java pipeline CONFIRMED with the D14 escape hatch.
  7  Founder runs the four console checks before D5; PG17 fallback is a versions change (touch
     points listed in TECH_PLAN §13.2, incl. SPEC §3).
  8  Minors: consent OTP inside the onboarding flow; until consent, CONSENT_REQUIRED covers photo
     doubts and documents (text stays available) — decision D3.28.
Post-approval exchange (three readings surfaced before executing, all ruled by the founder):
  decision 8 reading ACCEPTED with two tightenings — consent OTP fired at the DOB step, "consent
  pending" state on Profile + re-prompt at gated moments; legal review = workstream F9, not a
  blocker. DEV_SPEC §13.2 divergence ACCEPTED as handled (live CLAUDE.md wins; note records it).
  PG17 fallback protocol: SPEC §3 is amended only by the founder, or by Claude on an explicit
  per-edit instruction, with a DECISIONS.md row citing the console finding; contract edits are never
  bundled into task work — now a CLAUDE.md session rule. If PG18 is on RDS Mumbai the branch evaporates.
Conflict resolutions recorded (TECH_PLAN §0.4): #1 infra day → F8; #2 batch inference conditional on
  the minimum, on-demand below it; #3 eval gate = founder-launched live run + committed stamp, CI
  verifies the stamp and runs the fake layer (D23); #4 correct_key per decisions 1a/1b; #5 pipeline in
  Java; #6 streaming at D69; #7 five unscheduled features per decisions 2–3 (seed generation, trap
  mining, continuity still unscheduled); #8 CLAUDE.md precedence line added; #9 rule rewordings on
  their days (D4, D5, D13). DEV_SPEC §13.2 keeps the original hard-rule sentence; CLAUDE.md's note
  records the divergence (D1 precedent).
Doc conflicts surfaced (TECH_PLAN §0.4): PLAN has no infrastructure day (→ proposed F8); Bedrock
  batch-inference minimum vs "all nightly calls batch"; the eval gate cannot run live in CI; the
  correct_key wording vs SPEC §6.2/§6.4 verdicts (online reading proposed for approval; offline
  Option A/B open); pipeline module placement vs pipeline.md; streaming at D69 by PLAN precedence;
  five SPEC features with no PLAN day (batch sync, seed generation, trap mining, mocks + autopsy,
  continuity re-onboarding); CLAUDE.md precedence slot; three rule sentences to reword on their days.
Verified: Flutter gen-l10n accepts app_hi_Latn.arb (scratch project, Flutter 3.47.2) — Hinglish needs
  no custom plumbing.
Parked: see PARKED (uuidv7, ai_calls partitioning, staging env, golden tests, Crashlytics fallback,
  auto-deploy on main, second-instance upgrades).
Surprise: the plan came out at ≈1,950 lines against a 900–1,200 estimate; the schema and endpoint
  catalogs are the bulk. The spec-auditor's first pass caught a hard rule being narrowed without a
  §0.4 entry — keep the audit-before-handover habit for design documents, not just code.
Tomorrow's first task: founder pushes d3-tech-plan and opens PR #2 (CI: guardrails + secret scan
  only matter for docs). Then D4 core schema straight from TECH_PLAN §2.2–§2.4 and §2.9 (V1–V4 +
  the db/seed test taxonomy), the reversibility test (§8.2) and the first Modulith boundary test
  (§8.1), plus the D4 rule edit from §0.2 (append-only tables carry no updated_at).
Console check #3 closed (2026-09-04, after PR #2 merged): PG 18.6 + t4g.small + pgvector 0.8.1
  confirmed; checks #1, #2, #4 (Bedrock models/IDs, batch minimum, embeddings access) still
  pending before D5. Recorded in DECISIONS.md (D4 row), TECH_PLAN §13.2 item 3 and the new
  docs/runbooks/f8-infrastructure.md smoke stub; the PG17 fallback was never applied.
```

```
D2 · 2026-09-03 · PHASE 0 — Foundations
Shipped (branch d2-local-env, 3 commits + this tracker update): Brewfile + scripts/dev-setup.sh
  (JDK 25 via the openjdk@25 formula, Flutter 3.47.2 stable, Android cmdline-tools + SDK 36,
  optional emulator/AVD; idempotent, no sudo). server/: Spring Boot 4.1.0 on Java 25, Maven
  wrapper 3.9.14, webmvc + data-jpa (validate) + Flyway (empty location) + actuator health; boot
  test on Testcontainers pgvector/pgvector:pg18. app/: flutter create (Android, --empty),
  applicationId com.margai.app, Riverpod ProviderScope, ARB en/hi + generated l10n, widget test.
  Root README (fresh-clone path), docs/DECISIONS.md (9 spec-silent choices).
Acceptance: PASS locally —
  fresh clone → db healthy 5 s → mvnw verify 6 s (1 test) → flutter analyze + test + debug APK
  21 s → server health {"status":"UP", db UP} 2 s: 35 s total with warm caches. Cold installs
  measured today: brew bundle 4m37s, SDK packages 3m30s, first Gradle build 4m23s, pgvector
  pull 29 s, Maven deps ≈1.5 min → ≈14.5 min end to end on this connection, inside the bound.
  Shell runs on the Android 36 emulator (screenshot: "MARG AI"; com.margai.app resumed).
  spec-auditor on the full diff: PASS, one minor finding (a document conflict logged in
  DECISIONS.md) fixed by moving it here; jq added to the Brewfile on its advice (hooks need it).
  CI: PR #1 from d2-local-env green (founder-verified 2026-09-03) — first real run of the server
  and app jobs; merged to main by the founder (merge commit 5e97f35).
Doc conflict surfaced: DEV_SPEC §13.8 (bootstrap prompt) scaffolds server/ with "first migration
  = users + subscriptions"; PLAN D2 is environment only and PLAN D4 owns the first migrations
  (docker-compose.yml already says so). Resolved by PLAN precedence in the approved D2 plan;
  DEV_SPEC §13.8 stays as the historical bootstrap text. D3 design item: Hinglish is not a
  BCP-47 locale, so the ARB strategy for the third language needs a decision.
Parked: Android CLI migration (sdkmanager deprecated); libpq so `psql -h localhost` works;
  gh CLI in the Brewfile; Gradle native-access flag on JDK 25.
Surprise: Testcontainers 2.x renamed its artifacts (testcontainers-postgresql, package
  org.testcontainers.postgresql). Homebrew `openjdk` 25.0.2 was already installed but invisible
  to java_home — that is why D1 saw "JDK 21"; a user-level symlink fixes it without sudo.
  Gradle 9.3.1 / AGP 9.1 build on JDK 25, so one JDK suffices (no flutter --jdk-dir).
  flutter create's Kotlin template carries TODO comments that the gate rejects — removed.
  Another project's Keycloak holds 127.0.0.1:8080 on this Mac; SERVER_PORT=8081 works (README).
  psql is not installed although settings allow it — use `docker compose exec -T db psql`.
Tomorrow's first task: D3 — the full technical plan from SPEC (architecture, data model, API
  surface, AI pipeline) for a whole-session review; decide the Hinglish ARB locale strategy in it.
```

```
D1 · 2026-09-02 · PHASE 0 — Foundations
Shipped: git repo on main (6 commits). docs renamed to SPEC / DEV_SPEC / PLAN / TRACKER.
  CLAUDE.md (DEV_SPEC §13.2 verbatim + precedence + session rules). .claude/settings.json
  (permissions + hooks). scripts/precommit-gate.sh, block-paths.sh, detect-secrets.sh.
  eval/run.sh placeholder with content-hash stamp. Rules ×4, agents ×2 (spec-auditor,
  db-migrator), commands ×3 (/week, /endpoint, /evalgate). docker-compose db
  (pgvector pg18). GitHub Actions CI (guardrails, server, app, eval). Skeleton READMEs.
Acceptance: PASS —
  (a) commit with a todo-marker file + an unstamped prompts/ change → BLOCKED (both
      reasons listed); after eval/run.sh the same change passes the gate.
  (b) fake AWS access key via the Write tool → BLOCKED; via a shell heredoc → BLOCKED.
  (c) Write to infra/prod/main.tf → BLOCKED by hook; Write to the dotenv file → refused
      by the permission deny rule before the hook ran.
  (d) `aws s3 ls` → PERMISSION DENIED (aws CLI is installed locally, so a real test).
  (e) five clean commits passed through the live gate.
Parked: git-native pre-commit hook; protect scripts/ + settings.json from agent edits;
  release-checklist skill (DEV_SPEC §13.1).
Surprise: DEV_SPEC §13.3 is not valid settings JSON (comments, matcher form,
  PostToolUse cannot block) — translated, rationale in commit ddb25ac. Bash bypasses the
  Write/Edit hooks, so the hooks now cover Bash too; side effect: any shell command that
  merely mentions a protected path is blocked (lone `git commit`/`git log` exempted).
  CLAUDE.md §13.2 "SPEC §3–5" citations pointed at Developer-Spec sections; now read DEV_SPEC.
Tomorrow's first task: D2 — install Flutter stable and JDK 25 (local is 21), then the
  Spring Boot 4 skeleton with ./mvnw so the gate's SKIPPED warnings disappear.
```

```
D— · <date> · <phase>
Shipped:
Acceptance: PASS/FAIL —
Parked:
Surprise:
Tomorrow's first task:
```

---

## 🅿️ PARKED (Sunday review only)

- _idea · date · one line_
- git-native pre-commit hook (`core.hooksPath` → scripts/precommit-gate.sh) · 2026-09-02 · today only Claude's commits are gated; the human's own commits bypass the gate
- protect scripts/ and .claude/settings.json from agent edits after D1 · 2026-09-02 · the policed agent can currently edit its own policy; commit review by the human is the only control
- `.claude/skills/release-checklist/` (DEV_SPEC §13.1: migration check, eval gate, changelog) · 2026-09-02 · not in D1 scope; needed before Week 11 (money) at the latest
- migrate scripts/dev-setup.sh from `sdkmanager` to the new Android CLI · 2026-09-03 · sdkmanager prints a deprecation notice; still works (2026-09-08: `flutter build apk` also warns "understands SDK XML versions up to 3 but … version 4 was encountered" — cosmetic, same cause)
- put `platform-tools` (adb) on PATH from scripts/dev-setup.sh · 2026-09-08 · today only the full `/opt/homebrew/share/android-commandlinetools/platform-tools/adb` path works; device proofs script it by hand
- a reusable week-gate demo script (`scripts/week-gate.sh`) · 2026-09-08 · the Week-1 gate ran as commands + a pasted transcript; revisit if the weekly shape grows tedious · 2026-09-09 (D12): the Week-2 gate ran as `scripts/ui.sh` calls + curl + a transcript — the driver is the reusable part, the gate's shape differs each week (DECISIONS D12); still no gate script
- `brew "libpq"` so the allowed `psql -h localhost *` command exists locally · 2026-09-03 · today sessions use `docker compose exec -T db psql`
- `brew "gh"` so sessions can read CI run status after the human pushes · 2026-09-03 · optional; web UI works
- silence Gradle's JDK 25 native-access warning (`--enable-native-access=ALL-UNNAMED` in gradle.properties) · 2026-09-03 · cosmetic
- `uuidv7()` primary keys (native in PostgreSQL 18) for append-only tables · 2026-09-04 · better index locality on `practice_events`/`ai_calls`; irrelevant at beta volume (TECH_PLAN D3.11)
- month partitioning of `ai_calls` and `practice_events` · 2026-09-04 · when volume asks for it (TECH_PLAN §2.8)
- a staging environment between local and beta · 2026-09-04 · not before public launch (TECH_PLAN §7.1)
- Flutter golden tests · 2026-09-04 · widget tests per state suffice for now (TECH_PLAN §8.4)
- Crashlytics if PostHog error tracking proves insufficient on Android · 2026-09-04 · would amend the three-SDK rule (TECH_PLAN §13.4)
- automatic deploy on merge to main · 2026-09-04 · manual `workflow_dispatch` until D72 (TECH_PLAN §7.4)
- second-API-task upgrades: Valkey-backed rate limits, ShedLock for the dispatcher, SQS for async listeners · 2026-09-04 · only when a second task exists (TECH_PLAN §13.3)
- weekly batch-confirm card ("Did your batch finish Rotational Motion?", SPEC §6.7 layer 4) · 2026-09-04 · founder decision 3 at D3: parked until the beta contains coaching students; self-report (D25) and timetable photo (D29) are scheduled
- Cohere Rerank 3.5 (Mumbai on-demand, $2 per 1,000 queries of ≤100 chunks) as a rerank stage after hybrid retrieval fusion (TECH_PLAN §4.9) · 2026-09-06 · seen on the pricing page during console check #4; only if the D17 ✅ 15-query check or the D23 eval shows fusion alone missing the right paragraphs
- retry count, backoff base and on-demand batch concurrency as `margai.ai.*` config instead of the §4.11 design constants in `RetryingAiClient` / `OnDemandBatch` · 2026-09-06 · spec-auditor D5 finding; only if ops needs to tune them without a deploy
- per-attempt `ai_calls` rows (one per retry / repair attempt) instead of one row per request with `ai.attempts` · 2026-09-06 · D5 keeps TECH_PLAN §4.1's one-row-per-request; revisit if cost analysis needs attempt granularity
- attach a phone number to an email-identified account by phone OTP (and the reverse) once F1's DLT template exists · 2026-09-08 · D7 ruling: email joins phone as a verified identifier; the merge/attach flow is not scheduled; needs `POST /me/phone/request|verify` or similar and a rule for an email account meeting an existing phone account
- email canonicalisation beyond lowercase (Gmail dots and plus tags, IDN) · 2026-09-08 · D7 stores emails trimmed + lowercased only; two spellings of one Gmail inbox would be two accounts
- `POST /auth/logout` and "revoke every family on deletion" · 2026-09-08 · scheduled D10 / D64 (TECH_PLAN §3.2); the family revocation primitive exists since D7 (`RefreshTokenRepository.revokeFamily`)
- silence or rate-limit the `margai.otp.sandbox` logger in AWS · 2026-09-08 · today the sandbox sender is selected by `margai.auth.otp.sender = log` and simply must not be the value in a deployed environment; a startup refusal of `log` outside `local`/`test` would be the belt to the braces · 2026-09-09 (D12): checked and not built — 12 of the 14 `@SpringBootTest` classes boot without a profile, so a profile-keyed refusal breaks the test contexts; the belt is a validation on the F8 Terraform variable behind SSM `otp/sender` (must be `ses`), noted on the F8 row
- continuity re-onboarding after a result that falls short (SPEC §7.2 Fork B) and NCERT-style seed generation to ≥30 questions/topic (SPEC §9.3), NTA-trap mining (SPEC §9.4) · 2026-09-04 · unscheduled per TECH_PLAN §12.2; seed generation and trap mining proposed for the D24 buffer
- ~~narrow `scripts/precommit-gate.sh`'s `AI_PATHS` to `server/` and `eval/`~~ · 2026-09-09 · D8: the app's `lib/core/router/` and `test/core/router/` matched `(router|routing|retriev)` and demanded the eval stamp; the placeholder stamp cleared it, but the rule is about the AI difficulty router · **closed 2026-09-09 (D12, 75af5a2)**: the router alternative is scoped to `server/` and `eval/` (DECISIONS D12)
- ~~a case-insensitive router alternative in `AI_PATHS` (`[Rr]outer`…)~~ · 2026-09-09 · found at D12 and first parked for D23 with a made-up example; the spec-auditor showed the real miss was TECH_PLAN §1.3's `curriculum.api.ParagraphRetrievalRepository` (capital R, outside `ai/`, due with D17) · **closed 2026-09-09 (D12 audit fix)**: the alternative matches either case
- `AI_PATHS` does not cover router/retrieval *parameters* in config (`margai.ai.tier.*`, the §4.9 `k_vector` / `k_text` / `token_cap`) although TECH_PLAN §4.10 says "every prompt or parameter change" needs the run · 2026-09-09 · spec-auditor D12 MINOR, pre-existing; decide at D23 whether the `margai.ai` block of `application.yml` joins the hash or moves to its own file
- "real device over mobile data" for the login ✅ (PLAN D8) · 2026-09-09 · needs a public endpoint (F8 beta stack); the AVD proof stands until then, a USB phone can use `adb reverse` (app/README); tracked on the Week-2 gate line · 2026-09-09 (D12): named as carried in the Week-2 gate verdict; the first public endpoint (F8) re-runs leg A of the gate on a phone over mobile data
- `flutter_secure_storage` back to 11.x, and `platforms;android-37.0` in `scripts/dev-setup.sh` · 2026-09-09 · D8 pinned 10.x because AGP 9.1.0 cannot resolve Android 17's minor-versioned platform (DECISIONS D8 Android row); lift when the Flutter template's `compileSdk` passes 36
- ~~a reusable device-proof driver under `scripts/` (list the accessibility tree, tap by label, clear + type, screenshot)~~ · 2026-09-09 · D8's screencap-then-tap loop cost a mis-tap; D9 drove the whole checklist from the session scratchpad through `adb shell uiautomator dump` (Flutter labels appear as `content-desc`, fields as `EditText`; refocus a field before typing after a round trip) — worth committing if a third day needs it · **closed 2026-09-09 (D12, c499c71 + 0341dc1)**: `scripts/ui.sh`, documented in app/README "Device proofs" (DECISIONS D12)
- the OTP email says "expires in 0 minutes" when `margai.auth.otp.ttl` is under a minute (`otp.email.body` formats whole minutes) · 2026-09-09 · seen only with the D9 demo TTL of 40 s; production stays at 5 m — format seconds below a minute if a short TTL is ever configured (seen again in the D12 row-3 evidence; still cosmetic, still parked)
- `DELETE /me/devices/{token}` on logout (TECH_PLAN §3.7, D30) · 2026-09-09 · D10's logout revokes the token family only; the FCM device row does not exist before D30 — wire the device delete into `SettingsNotifier.logout` then
- per-request account checks for a still-valid access token after logout or deletion (the D7 known edge, pinned as documented behaviour in `AuthFlowTest.logoutRevokesTheFamilyButNotTheAccessTokenAlreadyIssued`) · 2026-09-09 · a logged-out device's access token opens routes for ≤ 15 min; D64 decides whether `/me`-class routes re-check the account
- Riverpod 3 automatic retry policy for the app as a whole · 2026-09-09 · D10 switched it off for `meProvider` only (one honest Retry, the D8 discipline); decide globally — `ProviderScope(retry:)` — before the next `AsyncNotifier` that talks to the network (D25 onboarding, D29 Today)
- ~~the reusable device-proof driver (`ui.sh`: tree, tap by label, field, type, shot, launch, kill)~~ · 2026-09-09 · used again at D10 from the session scratchpad — third day in a row; commit it under `scripts/` at the Week-2 gate if D12 drives the AVD too · **closed 2026-09-09 (D12)**: it did, and it is (`scripts/ui.sh`, the row above)
- `state_code` checked against the state list on the server (`PATCH /me` accepts any two letters today; the D25 picker is the only guard) · 2026-09-09 · spec-auditor D10 MINOR; do it when `cutoffs` land (D22) and the list exists in one place
- ARB copy for the seven `PATCH /me` reason codes (`language|goal|category|state_code.invalid`, `time.invalid`, `decimal_min`, `decimal_max`) · 2026-09-09 · unreachable from the app until the D25 / D64 screens send those fields; the D8 fallback line renders meanwhile (DECISIONS D10 PATCH row)
- the logstash JSON encoder for the server log (TECH_PLAN §10.1 "Logback with the logstash JSON encoder → stdout → CloudWatch Logs"; the server rule's "structured JSON logs") · 2026-09-09 · not in the tree since D2; the D11 delivery-rate line is plain text with `key=value` pairs that Logs Insights parses either way — add the encoder with a plain `local` profile at F8/D73, when something reads JSON
- SES delivery events (bounce, complaint, delivery) through an SES configuration set → SNS → the API, so `otp.send_failed` and `expired_unverified` stop being the only delivery signals · 2026-09-09 · needs the F8 stack (a topic and an endpoint); until then the D11 report's `expired_unverified` is the "never arrived" proxy
- `otp.time_to_verify{channel}` (a timer from `created_at` to `verified_at`) and `otp.resent{channel}` (a request whose previous code for that destination is still unverified) · 2026-09-09 · two cheap delivery-latency signals not asked for by PLAN D11; add when the D73 dashboard wants a latency panel
- the D11 report's window is the process lifetime · 2026-09-09 · right while one API task runs and CloudWatch is absent; when the counters flow to CloudWatch (F8/D73) decide whether `GET /admin/metrics/otp` grows a `?hours=` database window (then `send_failed` would need a row per failed delivery — the D7 row deletes it) or simply points at the dashboard
- an admin bootstrap (a seed or a CLI that flags the founder's row) · 2026-09-09 · today `users.role = 'admin'` is set by hand over psql (TECH_PLAN §3.7 "flagged by hand"), as the D11 ✅ did; D75's admin routes decide whether a `pipeline` command or an SSM-listed email does it
- a Chanakya→Unicode step (or OCR) before any Hindi NCERT chunk is embedded · 2026-09-09 · found while writing `ncert/2022-ed/hi/manifest.md`: all ten Hindi books are selectable text set in the legacy 8-bit Walkman-Chanakya fonts with no ToUnicode map, so extraction yields glyph codes and zero Devanagari across 1,976 pages; the Phase-2 content pipeline (D13+) grounds on `en/` until this exists, and the manifest's "text OK" column stays ✗ until a converted sample passes a native-reader check
- topic-level `name_hi` for the 374 taxonomy topics (batch translation through the CHEAP tier + a native-reader check), and a final native-reader skim of the full unit and chapter `name_hi` column · 2026-09-10 · the D13 draft fills Hindi for subjects, units and chapters only; needed before the D26 syllabus grid shows topics in Hindi · 2026-09-11 (D13 review): the sampled unit and chapter Hindi reviewed and approved for D13; the full-column skim stays parked for before D26
- cut-off seat-type rows the founder must source: every `govt_mbbs` / `private_mbbs` / `bds` closing-marks row by year, category and quota scope (MCC and state counselling) · 2026-09-10 · the D13 draft carries only the NTA qualifying cut-offs · 2026-09-11: the 2026 qualifying rows landed at the review (Re-NEET, 16 July 2026 notice; an outlier season, DECISIONS D13 row for D58); seat-type rows are not needed before the trajectory work around D58
- replace or reconcile the D4 `db/seed` test taxonomy (`PHY.11.MECH`, `CHE.11.PHYS`, `PHY.11.KIN`, `CHE.11.MOLE`, four edges, one track, three cut-offs) now that the real inputs exist · 2026-09-12 · a local database that carries the seed shows them as loader orphans (`CurriculumImportSeedTest` pins the shape); `SeedTaxonomyTest` and the constraint tests rely on the seed's fixed UUIDs — decide at D14 whether the seed becomes a subset of the real taxonomy or those tests fixture their own rows
- a `--prune` option for the loaders (delete orphans that nothing references) · 2026-09-12 · today orphans are reported and left in place (DECISIONS 2026-09-12); only needed if a renamed chapter must go
- cost lines in the run reports from the AI ledger · 2026-09-12 · the D13 reports carry counts only; `ncert extract` (D14) is the first command that spends
- ~~a Collective Intelligence Layer (CIL) carrying season notes — the 2026 cancellation and Re-NEET is the first candidate — built by a "from-inputs" step~~ · 2026-09-12 · parked at the D13 close as an idea in neither SPEC nor TECH_PLAN · **retired the same day**: the founder issued change spec CS-1 (`docs/changes/CS-1-collective-intelligence.md`), integrated as SPEC §9.6/§6.1/§10.9, TECH_PLAN §2.3/§4.5/§6.3 and the D22/D24/D47/D49/D55/D56/D73 scopes; "from-inputs" is `collective from-inputs` over the F11 excerpt files; the 2026 Re-NEET season note is the first `season_notes` candidate for `collective from-inputs` (also carried by the D58 DECISIONS row)
- private-use-area decoding in the ingest text extractor · 2026-09-09 · `ncert/2022-ed/en/phy11-part1/keph107.pdf` (Gravitation, 7 of 17 pages) and its prelims extract as U+F020–U+F0FF (cp1252 byte + 0xF000); subtract 0xF000 or Chapter 7 loses those pages — the only English file affected (every page of every file was scanned)

---

## ⚠️ Slippage log

| Day | Planned | Actual | Reason | Recovery |
|---|---|---|---|---|
| F10 (founder item, not a build day) | SES production access "before the first stranger (D12)" | still open at D12 (2026-09-09) | not requested yet; the Week-2 gate ran on the sandbox inbox with the 2026-09-09 verified-recipient live proof as its real-inbox evidence, the gap named as carried | request it before any unverified stranger; then re-run the gate's leg A to a real unverified inbox and note it on the gate line |
