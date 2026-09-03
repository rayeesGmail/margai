# MARGAI — Implementation Tracker

> Lives at `docs/TRACKER.md`. Update at the end of EVERY session (part of the 20-min
> close). Claude Code may tick boxes only when the day's ✅ acceptance check passed.
> Rules: a day is DONE only if committed + acceptance passed. Slipped days move down,
> never disappear. New ideas go to PARKED, reviewed Sundays.

---

## 📊 Status dashboard (update weekly)

| Field | Value |
|---|---|
| Current phase | PHASE 0 — Foundations (Week 1) |
| Current day | D1 done · 2026-09-02 (session closed just after midnight, 2026-09-03) · next: D2 |
| Days completed / total | 1 / 84 |
| Schedule delta | on track |
| Last week's gate | n/a — Week-1 gate is due at D6 |
| Eval suite pass rate | placeholder PASS with 0 fixtures (suite arrives D23; gate ≥97%) |
| Cache hit rate | — |
| Blockers | none. D2 prerequisites on this machine: Flutter stable is not installed; JDK is 21, Spring Boot 4 on latest LTS needs 25 |

---

## PHASE 0 — Foundations (Week 1) · Module M0

- [x] **D1** Claude Code scaffolding (CLAUDE.md, settings, 3 gate scripts, rules, agents, commands) · ✅ gates block bad commit + secret write — done 2026-09-02, all five acceptance tests passed (see day log)
- [ ] **D2** Local env: Docker Postgres 18+pgvector, Spring Boot 4 boots, Flutter shell on device, CI green · ✅ fresh clone → running <15 min
- [ ] **D3** Claude Code full technical plan reviewed & approved · ✅ plan committed to docs/
- [ ] **D4** Core schema migrations (users, profiles, syllabus, config) + seed script · ✅ reversible migrations
- [ ] **D5** AiClient seam + FakeAiClient + cost ledger + one live Bedrock smoke call · ✅ app runs fully on fake
- [ ] **D6** Buffer / overflow
- [ ] **🚩 WEEK-1 GATE:** repo, env, plan, schema, AI seam in place

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

- [ ] **D25** Interview Q1–Q3 (chat UI + persistence) · ✅ back/edit works
- [ ] **D26** Syllabus grid + hours sliders + goal/target (+optional category) · ✅ interview <5 min
- [ ] **D27** DOB + minors parent-consent OTP · ✅ uploads blocked until consent
- [ ] **D28** Scorecard capture → extract → confirm → delete · ✅ 3 sample cards correct; storage empty after
- [ ] **D29** 12th-marksheet variant + deterministic first plan + reveal screen · ✅ end-to-end new user
- [ ] **D30** Notification permission moment + morning notif skeleton
- [ ] **🚩 WEEK-5 GATE:** install → plan < 5 min, cold demo on fresh device

## PHASE 4 — Practice engine (Week 6) · M5

- [ ] **D31** Session backend (band+relevance selection, server judging) · ✅ no answers in client payload
- [ ] **D32** Practice UI (timer, verdict, solution, anchor chip) · ✅ smooth on mid-range phone
- [ ] **D33** Session summary + event stream · ✅ timing data in DB
- [ ] **D34** Offline cache + outbox sync · ✅ airplane-mode test
- [ ] **D35** Diagnostic test (30-Q adaptive) → ability estimates · ✅ shifts a seeded plan
- [ ] **D36** Buffer · **🚩 WEEK-6 GATE:** practice loop incl. offline + diagnostic

## PHASE 5 — Doubt solver (Weeks 7–8) · M6 ⭐

- [ ] **D37** Text path: normalize → cache → cheap-tier grounded answer · ✅ 10 doubts, right anchors
- [ ] **D38** Photo path (vision extraction) · ✅ 10 printed Qs faithful
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
- [ ] **D54** Buffer · **🚩 WEEK-9 GATE:** capture→diagnose→resurface→heal end-to-end
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

---

## ⚠️ Slippage log

| Day | Planned | Actual | Reason | Recovery |
|---|---|---|---|---|
