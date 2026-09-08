# MARGAI — Implementation Tracker

> Lives at `docs/TRACKER.md`. Update at the end of EVERY session (part of the 20-min
> close). Claude Code may tick boxes only when the day's ✅ acceptance check passed.
> Rules: a day is DONE only if committed + acceptance passed. Slipped days move down,
> never disappear. New ideas go to PARKED, reviewed Sundays.

---

## 📊 Status dashboard (update weekly)

| Field | Value |
|---|---|
| Current phase | PHASE 0 — Foundations (Week 1) complete 2026-09-08 · PHASE 1 — Auth & identity (Week 2) starts at D7 |
| Current day | D6 done · 2026-09-08 (buffer: TECH_PLAN §0.3 dispositions closed, §14 checked 25/25 against DECISIONS, README + credential-path drift fixed, 3 spec-auditor findings fixed; **Week-1 gate PASS** as a demo script; branch `d6-week1-gate`, 4 commits, PR #5 to be opened by the founder) · next: D7 OTP request/verify + rate limits + tokens (PHASE 1, Week 2) |
| Days completed / total | 6 / 84 |
| Schedule delta | on track |
| Last week's gate | **Week-1 🚩 PASS** 2026-09-08 — repo, environment, plan, schema and AI seam each demonstrated in-session (transcript in the D6 day log); the only open item, the live proof on the Anthropic profiles, is an account matter, not a build one |
| Eval suite pass rate | placeholder PASS with 0 fixtures (suite arrives D23; gate ≥97%) |
| Cache hit rate | — |
| Blockers | none for the build. Two open items on the AWS account (founder; not build blockers before D14, the first live VISION day): (1) the **Anthropic models are refused with 403 `INVALID_PAYMENT_INSTRUMENT`** (AWS Marketplace subscription needs a valid payment method; AWS support ticket open since 2026-09-07) — the tier defaults stay Anthropic, the D5 smoke was proven on Amazon Nova Lite instead; when the ticket clears, rerun `cd server && BEDROCK_LIVE=1 ./mvnw test -Dtest=BedrockSmokeTest -Dsurefire.failIfNoSpecifiedTests=false`, then close TECH_PLAN §13.2 item 1's live proof and confirm or drop the Nova price row (DECISIONS 2026-09-08 D5 row). (2) The local CLI session is the account **root** user via `aws login` — F8 creates a non-root IAM Identity Center or IAM identity with Bedrock permissions (TECH_PLAN §7.4; DECISIONS 2026-09-08 D6 row names the interim path). All four §13.2 console checks are closed (D4/D5 day logs): CHEAP/VISION Haiku 4.5, REASON Sonnet 4.6 via `global.` profiles, EMBED `cohere.embed-multilingual-v3` in-region, batch minimum 100; Sonnet 5 / Opus 5 / Opus 4.8 gated → F8 allowlist request. Toolchain on this machine: JDK 25, Flutter 3.47.2, Android SDK 36 + emulator |

---

## PHASE 0 — Foundations (Week 1) · Module M0

- [x] **D1** Claude Code scaffolding (CLAUDE.md, settings, 3 gate scripts, rules, agents, commands) · ✅ gates block bad commit + secret write — done 2026-09-02, all five acceptance tests passed (see day log)
- [x] **D2** Local env: Docker Postgres 18+pgvector, Spring Boot 4 boots, Flutter shell on device, CI green · ✅ fresh clone → running <15 min — done 2026-09-03, acceptance PASS (35 s warm, ≈14.5 min cold), PR CI green (founder-verified), merged (see day log)
- [x] **D3** Claude Code full technical plan reviewed & approved · ✅ plan committed to docs/ — done 2026-09-04, docs/TECH_PLAN.md v1.0 APPROVED with 8 founder decisions (§0.5), three spec-auditor passes (see day log)
- [x] **D4** Core schema migrations (users, profiles, syllabus, config) + seed script · ✅ reversible migrations — done 2026-09-06, acceptance PASS (compose-db schema dump matches TECH_PLAN §2.2–§2.4 column by column; `MigrationReversibilityTest` green), PR #3 merged by the founder 2026-09-06 (merge commit 3f77d6f) (see day log)
- [x] **D5** AiClient seam + FakeAiClient + cost ledger + one live Bedrock smoke call · ✅ app runs fully on fake — done 2026-09-08 (built 2026-09-06 on `d5-ai-seam`, 15 commits), acceptance PASS: (a) fake chain + boot on the compose db; (b) live smoke on Bedrock `apac.amazon.nova-lite-v1:0` — two `ok` rows, real token counts, 5,976-token cache write then read, forced tool honoured; the Anthropic-profile proof waits for the AWS billing ticket (see day log); PR #4 merged by the founder 2026-09-08 (merge commit 0b70047, CI green after the test-order fix)
- [x] **D6** Buffer / overflow — done 2026-09-08: TECH_PLAN §0.3 dispositions closed and §14 checked against DECISIONS.md (25/25; the §12.1 D6 deliverables), root README + live-smoke credential wording brought in line with D3.4 and the D5 path, three spec-auditor findings fixed; 4 commits on `d6-week1-gate` (see day log)
- [x] **🚩 WEEK-1 GATE:** repo, env, plan, schema, AI seam in place — **PASS** 2026-09-08, run as a literal demo script (evidence in the D6 day log)

## PHASE 1 — Auth & identity (Week 2) · M1

- [ ] **D7** OTP request/verify + rate limits + tokens · ✅ curl happy path
- [ ] **D8** Login screens (auto-read OTP, retry, change number) · ✅ real device, mobile data
- [ ] **D9** Unhappy paths (10-failure checklist) · ✅ all graceful
- [ ] **D10** Profile-on-first-login, language, logout, token rotation · ✅ persistence + clean logout
- [ ] **D11** DLT live check / delivery metrics · ✅ OTP success metric visible
- [ ] **D12** Buffer
- [ ] **🚩 WEEK-2 GATE:** a stranger's phone signs in first try

## PHASE 2 — Content pipeline v1 (Weeks 3–4) · M3

- [ ] **D13** Taxonomy CSV loaded + prerequisite graph + archetype drafts · ✅ no cycles
- [ ] **D14** NCERT extraction pilot (2 books, EN) · ✅ 20-paragraph spot check
- [ ] **D15** All EN books extracted · ✅ coverage report/book
- [ ] **D16** Hindi ingest + EN↔HI alignment · ✅ 20 aligned pairs checked
- [ ] **D17** Embeddings + hybrid retrieval harness · ✅ 15 concept queries hit right paragraphs
- [ ] **D18** Buffer (extraction mess) · **🚩 WEEK-3 GATE:** NCERT searchable EN+HI
- [ ] **D19** PYQ ingest + tagging · ✅ counts match official papers
- [ ] **D20** AI solutions (1 subject) + verification wired · ✅ founder 50-Q audit #1
- [ ] **D21** Solutions all subjects + distractor maps · ✅ 50-Q audit #2 under threshold
- [ ] **D22** Weightage & difficulty stats → nodes · ✅ top-10 chapters sanity check
- [ ] **D23** Anchor linking + eval suite v1 (~60 Q) · ✅ harness runs
- [ ] **D24** Buffer · **🚩 WEEK-4 GATE:** solved/tagged/anchored PYQ bank + eval in CI

## PHASE 3 — Onboarding & first plan (Week 5) · M2 + M4v0

- [ ] **D25** Interview Q1–Q3 (chat UI + persistence) + batch-position self-report for coaching students (D3 decision 3) · ✅ back/edit works
- [ ] **D26** Syllabus grid + hours sliders + goal/target (+optional category) · ✅ interview <5 min
- [ ] **D27** DOB + minors parent-consent OTP sent at the DOB step; onboarding completes regardless; "consent pending" state on Profile + re-prompt at gated moments (D3 decision 8) · ✅ photo doubts and uploads blocked until consent; text features and the first plan work
- [ ] **D28** Scorecard capture → extract → confirm → delete · ✅ 3 sample cards correct; storage empty after
- [ ] **D29** 12th-marksheet + batch-timetable doc_types (D3 decision 3) + deterministic first plan + reveal screen · ✅ end-to-end new user
- [ ] **D30** Notification permission moment + morning notif skeleton
- [ ] **🚩 WEEK-5 GATE:** install → plan < 5 min, cold demo on fresh device

## PHASE 4 — Practice engine (Week 6) · M5

- [ ] **D31** Session backend (band+relevance selection, server judging) · ✅ no correct answer in any payload before that question is answered (D3 decision 1a)
- [ ] **D32** Practice UI (timer, verdict, solution, anchor chip) · ✅ smooth on mid-range phone
- [ ] **D33** Session summary + event stream · ✅ timing data in DB
- [ ] **D34** Offline cache + outbox sync, offline pack per Option A (D3 decision 1b) · ✅ airplane-mode test + the pack is the only pre-answer carrier
- [ ] **D35** Diagnostic test (30-Q adaptive) → ability estimates, plus `kind=mock` sessions (D3 decision 2; may slip into D36) · ✅ shifts a seeded plan
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
- [ ] **D47** Eval → ~150 Q + pre-commit eval gate for AI changes · ✅ gate blocks failing prompt
- [ ] **D48** Buffer · **🚩 WEEK-8 GATE:** hero demo-ready; eval ≥ target; audit list empty/ticketed

## PHASE 6 — Notebook, SRS & nightly brain (Weeks 9–10) · M7 + M8

- [ ] **D49** Error capture + cause classification + one-tap correction · ✅ diagnosed in minutes; overrides stick
- [ ] **D50** Notebook UI (summary/entries/cause chips + free cap) · ✅ matches wireframe
- [ ] **D51** SRS 3/10/25 + variant selection (real Q preferred, else generate+verify) · ✅ day-3 variants appear
- [ ] **D52** Healed flow + ✓ gallery + Danger Zones · ✅ healing demo
- [ ] **D53** Patterns engine v1 (plain-language insights) · ✅ fires only with enough data
- [ ] **D54** Buffer + mock autopsy (per-mark classification, gamble score, pace map — D3 decision 2; may slip) · **🚩 WEEK-9 GATE:** capture→diagnose→resurface→heal end-to-end
- [ ] **D55** Nightly snapshot + deterministic candidate blocks · ✅ sensible plans for 5 synthetic students
- [ ] **D56** AI selection + reasons + mentor note + validated output + fallback · ✅ no planless morning possible
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
- [ ] **D73** Analytics funnels + crash triage flow · ✅ dashboards on real test traffic
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
| F1 | Razorpay KYC + DLT SMS template | W1 D1 | ☐ not started | long lead time |
| F2 | NCERT licensing letter sent | W1 | ☐ | follow-up cadence: monthly |
| F3 | Educator review of backbone booked | by W5 | ☐ | needed W8 |
| F4 | Beta recruitment playbook + group scouting | W10–13 | ☐ | 2–3 Telegram groups |
| F5 | Marketing site copy + deploy | W11 | ☐ | |
| F6 | Trademark search (Class 41 + 9) for final name | anytime | ☐ | before public launch |
| F7 | Domain + social handles for final name | anytime | ☐ | MARGAI = working name |
| F9 | DPDP legal review of the minors' consent flow (TECH_PLAN §0.5 item 8, §9.6): consent OTP at the DOB step, gated photo doubts and uploads until consent | before D27 ideally; before beta at the latest | ☐ | not a build blocker; may tighten the gating |
| F8 | AWS beta stack (Terraform) per TECH_PLAN §7.6 — accepted at D3 (decision 5). Claude drafts Terraform in a separate infra session profile (plan allowed, apply denied), created when the first milestone is due; founder runs every apply | by D5 (Bedrock access), D14, D28, D55, D70 | ☐ accepted 2026-09-04 | PLAN has no infra day (TECH_PLAN §0.4 #1); console checks §13.2 before D5. Bedrock access confirmed 2026-09-06 for Haiku 4.5 + Sonnet 4.6; optional AWS Sales allowlist request for the Claude 5 family / Opus 4.x (REASON upgrade path, not a blocker). 2026-09-07: the account's payment instrument blocks the Marketplace subscription for the Anthropic models (D5 blocker) — fix in Billing; and the local CLI session is the root user — create a non-root identity (IAM Identity Center or an IAM user) with Bedrock permissions for daily use before more live work (§7.4, §9.2) |

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
Tomorrow's first task: founder pushes d6-week1-gate and opens PR #5 (docs + comments; CI runs the
  full server job). Then D7 — OTP request/verify + rate limits + tokens (PLAN D7 ✅ curl happy path)
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
- a reusable week-gate demo script (`scripts/week-gate.sh`) · 2026-09-08 · the Week-1 gate ran as commands + a pasted transcript; revisit if the weekly shape grows tedious
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
- continuity re-onboarding after a result that falls short (SPEC §7.2 Fork B) and NCERT-style seed generation to ≥30 questions/topic (SPEC §9.3), NTA-trap mining (SPEC §9.4) · 2026-09-04 · unscheduled per TECH_PLAN §12.2; seed generation and trap mining proposed for the D24 buffer

---

## ⚠️ Slippage log

| Day | Planned | Actual | Reason | Recovery |
|---|---|---|---|---|
