# MARG AI — Implementation Plan (4 hours/day)

**Pace:** 4 focused hours/day, 6 days/week (Sunday off or overflow) → ~24 hrs/week.
**Builder:** you + Claude Code (you drive, review, and decide; Claude Code plans and writes).
**Total:** 14 weeks (≈84 working days) from empty repo to a 50-student closed beta,
plus 6 weeks of beta operation. Timeline assumes the Product Spec v2.0 is the contract.

---

## 1. How a 4-hour day works (the operating rhythm)

Agentic building changes where your hours go. A default day:

- **0:00–0:20 — Reload & intent.** Read yesterday's commit log + today's target from this
  plan. Open the Claude Code session with the day's goal.
- **0:20–0:50 — Plan review.** Claude Code proposes the task plan (plan mode). You approve,
  trim, or redirect. *This half hour is your highest-leverage time.*
- **0:50–3:10 — Build loop.** Claude Code executes task-by-task; you review diffs as they
  land, answer its questions, spot-check behavior on device/emulator.
- **3:10–3:40 — Verify.** Run the day's acceptance check (each day below has one).
  Tests green, commit, note anything for CLAUDE.md.
- **3:40–4:00 — Close.** Update the day log (5 lines), park tomorrow's first task.

**Rules for part-time sanity:** one module in flight at a time · never end a day
uncommitted · anything you corrected twice goes into CLAUDE.md · Fridays end with the
week's gate (below) even if a feature slips · when a day's scope doesn't fit, cut scope,
not the verify step.

---

## 2. Module map & dependency order

```
M0 Foundations ─► M1 Auth ─► M2 Onboarding ─► M4 Planner v0 ─► M5 Practice
                                   │                               │
M3 Content pipeline (runs parallel, feeds everything) ─────────────┤
                                   ▼                               ▼
                              M6 Doubt solver ◄── needs M3 NCERT   M7 Notebook+SRS
                                   │                               │
                                   └──────────► M8 Nightly brain ◄─┘
                                                     │
                        M9 Billing & paywall ── M10 Trust/privacy/docs
                                                     │
                                          M11 Hardening ─► M12 Beta
```

Founder-only parallel workstreams (not coding hours; do in evenings/Sundays):
**F1** Razorpay KYC + DLT SMS template (start week 1 — long lead times) ·
**F2** NCERT licensing letter (week 1) · **F3** Educator review of plan backbone
(book by week 5, needed week 8) · **F4** Beta recruitment groundwork (weeks 10–13) ·
**F5** Marketing site copy (week 11). *Later workstreams F6–F11 and, added 2026-10-01 with
CS-2 to CS-6, F12 (curate chapter videos), F13 (pricing-evidence review), F14 (season-one entity
realignment) and F15 (a low-cost Android test phone) are tracked in TRACKER's founder table. F1's
DLT registration is possible under the Udyam proprietorship (founder, 2026-10-01).*

---

## 3. Phase plan, day by day

### PHASE 0 — Foundations (Week 1, Days 1–6) — Module M0

- **D1 — Scaffolding session.** Run the bootstrap prompt: repo layout, CLAUDE.md,
  settings.json + the three gate scripts (pre-commit, path-block, secret-scan),
  rules files, subagents, slash commands. ✅ *Gate scripts demonstrably block a bad
  commit and a secret write.*
- **D2 — Local environment.** Docker Postgres 18 (+vector extension), Spring Boot 4
  skeleton boots, Flutter app shell runs on your device, CI pipeline green on a
  hello-world PR. ✅ *Fresh clone → running stack in <15 min.*
- **D3 — Architecture plan review.** Claude Code produces its full technical plan from
  the Product Spec (architecture, data model, API surface, AI pipeline design). You
  spend the whole session reviewing/challenging it. ✅ *Approved plan committed to docs/.*
- **D4 — Core schema.** First migrations: users, profiles, syllabus nodes, config
  tables. Seed script for a test taxonomy. ✅ *Schema matches approved plan; migrations
  reversible.*
- **D5 — AiClient seam.** The single AI interface + fake implementation + cost-ledger
  table + Bedrock connectivity smoke test (one real call, then off). ✅ *App runs fully
  on FakeAiClient; one live call logged with token counts.*
- **D6 — Buffer/overflow + week gate.** Close loose ends. **Week-1 gate:** repo,
  environment, plan, schema, AI seam all in place.

### PHASE 1 — Auth & identity (Week 2, Days 7–12) — Module M1

- **D7 —** OTP request/verify backend with the SMS provider sandbox; rate limits;
  token issuance/refresh. ✅ *Happy path via curl.*
- **D8 —** Flutter login screens: number entry, OTP auto-read, retry, change-number,
  offline-tolerant errors. ✅ *Login on a real device over mobile data.*
- **D9 —** Unhappy paths hardening: wrong OTP, expiry, resend limits, clock skew,
  duplicate accounts. ✅ *A test checklist of 10 failure modes all handled gracefully.*
- **D10 —** Account basics: profile record on first login, language setting, logout,
  token rotation. ✅ *Kill app, reopen → still logged in; logout → clean state.*
- **D11 —** DLT template live check (if F1 approved; else stay sandbox), delivery-rate
  logging, OTP metrics dashboard stub. ✅ *OTP success metric visible.*
- **D12 —** Buffer + **Week-2 gate:** a stranger's phone can sign in first try.
- **Floating — phone OTP live** *(founder decision 2026-10-01; placement proposed, to confirm)*:
  phone OTP by SMS is the only sign-in method (SPEC §5.7). The MSG91 adapter on the two DLT
  templates (login, parent consent), number entry with OTP auto-read, and email OTP moved behind
  its flag land in the first buffer after F1's DLT templates are approved — and **D60 is the
  decision point**: if the templates are not live by then, the beta runs on the email fallback
  flag (which needs F10's SES production access). The same item builds the phone attach for an
  account begun on the email fallback (`POST /me/phone/otp/request|verify`, TECH_PLAN §15.1) and
  gives D27's consent flow its live SMS proof. ✅ *A real phone signs in by SMS first try; the
  email path only with its flag on; an email-begun account attaches a phone and stays one user,
  and a number already in use is refused, never merged (CS-2 §10, SPEC §5.7).*

### PHASE 2 — Content pipeline v1 (Weeks 3–4, Days 13–24) — Module M3

*(This phase is heavier on batch jobs than app code — Claude Code writes pipeline
tools; your reviews focus on output quality.)*

- **D13 —** Final syllabus taxonomy CSV (founder-reviewed) loaded; prerequisite edges;
  archetype track definitions drafted. ✅ *Taxonomy queryable; graph has no cycles.*
- **D14 —** NCERT ingest: extraction of 2 pilot books (one Physics, one Bio), EN first;
  paragraph addressing scheme. ✅ *Spot-check 20 random paragraphs against the PDFs.*
- **D15 —** NCERT ingest: remaining EN books; equations/figures handling. The **first**
  verified book is embedded and its retrieval tested before the rest are extracted: the
  embedding pin moves provider, model and width together, so changing any of them
  re-embeds the corpus — ~900 paragraphs is where a wrong pin, chunk or hybrid weighting
  is cheap to find and ~9,000 is not (founder ruling 2026-09-19; DECISIONS, TECH_PLAN
  §4.9 — added 2026-09-19). ✅ *Coverage report: % paragraphs extracted cleanly per book;
  and on that first book, a query → top-passages run, including a Hindi query that reaches
  an English paragraph.*
- **D16 —** Hindi ingest + EN↔HI paragraph alignment. ✅ *20 aligned pairs spot-checked.*
- **D17 —** Embeddings + hybrid retrieval over the whole corpus, on the pin settled at D15
  (amended 2026-09-19); the test harness widened to it. ✅ *15 hand-written concept queries
  return the right paragraphs.*
- **D18 —** Buffer for extraction mess (there will be some). **Week-3 gate:** NCERT
  layer searchable in both languages. *Added 2026-10-01 (CS-2 §4.11): Bedrock configured as a
  completion fallback behind a flag — config only; embeddings stay on Bedrock as their primary.*
- **D19 —** PYQ ingest: papers loaded, question records created, tagging pass.
  ✅ *Counts per year/subject match official papers.*
- **D20 —** AI solution generation (batch) for one subject; verification pass wired.
  ✅ *50-question audit sample: you personally check; note error rate.*
- **D21 —** Solutions for remaining subjects (batch overnight); distractor-map
  generation. ✅ *Second 50-question audit; error rate < agreed threshold.*
- **D22 —** Weightage & difficulty statistics computed → syllabus nodes updated; the
  `collective_records` migration and `collective from-pyq` momentum per node (CS-1 §2, §4;
  TECH_PLAN §2.3, §6.3 — added 2026-09-12).
  ✅ *Top-10 weightage chapters match known NEET wisdom (sanity check).* *Added 2026-10-01
  (CS-4 §3; at risk): the `concept_primers` migration and `primers generate` begin for the top-50
  weightage chapters (TECH_PLAN §15.7), staged on one chapter first.*
- **D23 —** Anchor-linking questions↔NCERT; seed the eval suite v1 (~60 questions
  across subjects). ✅ *Eval harness runs and reports.*
- **D24 —** Buffer + **Week-4 gate:** solved, tagged, anchored PYQ bank + eval suite
  running in CI. In the buffer (CS-1 §4, §7, §9.3; added 2026-09-12): `collective from-pyq`
  misconception drafts from the distractor maps; `collective from-inputs` over the F11
  excerpt files if they exist; `collective review` (the founder-review sheet) and
  `collective load` of the founder-edited sheet — the last three with explicit permission
  to slip to any later buffer (CS-1 §8, §9.3), so the Week-4 gate never waits on a founder
  review. ✅ *travels with `collective load`, here or in the buffer it slips to: approved
  collective records exist for the top-50 weightage nodes at or above the confidence
  threshold; the review sheet is founder-signed; every record carries `season_version`.*
  *Added 2026-10-01 (CS-4 §3; at risk): primers for the top-50 chapters finish here and `primers sample`
  writes the founder's 30-primer sheet; the remaining topics go to a later buffer before D74.*

### PHASE 3 — Onboarding & first plan (Week 5, Days 25–30) — Modules M2 + M4(v0)

- **D25 —** Interview flow backend + chat-style UI: Q1–Q3 (attempt, year, coaching).
  ✅ *Answers persist; back/edit works.*
- **D26 —** Syllabus grid (3-state + weak long-press), hours sliders, goal/target picker
  with optional category + inline why-note. ✅ *Full interview <5 min on device.*
- **D27 —** DOB + minors parent-consent flow: parent phone captured and consent OTP sent at the
  DOB step; onboarding completes regardless; "parent consent pending" state on Profile with a
  re-prompt at gated moments (TECH_PLAN §0.5 item 8, decided at D3). ✅ *Under-18 path blocks
  photo doubts and document uploads until consent; text features and the first plan work.*
  *Amended 2026-10-01 (CS-2 §4.2, founder decision): the beta is recruited 18+, so D27 builds DOB
  with an 18+ beta gate (flag on) **and** the parent-consent flow in full on the SMS template,
  switched off by flag for the beta and on at public launch; any time this frees returns to the
  at-risk days' buffer, not to new scope. An under-18 DOB at the gate stops kindly and hard-deletes
  the account, number and answers at once (founder ruling 2026-10-01) — a purge built here, not D64's
  anonymise-then-purge (TECH_PLAN §15.1); D60 revisits the 18+ rule. The
  consent flow is built against the OTP sender port with SMS faked in tests — its live proof comes
  with the floating phone-OTP item, where the MSG91 adapter lands. ✅ adds: with the beta flag on,
  an under-18 DOB meets the gate and leaves no row that identifies them (the cost ledgers keep their
  rows, unlinked); with the consent flag on, the original ✅ holds.*
- **D28 —** Scorecard upload: capture UI with frame guide → AI extraction → confirm/edit
  screen → delete-after-confirm behavior. ✅ *3 real scorecard photos (found samples)
  extract correctly; storage verifiably empty after.*
- **D29 —** 12th-marksheet variant (shared pattern) + deterministic first-plan
  generator (archetype track + interview inputs) + first-plan reveal screen with target
  line; the first plan reads the approved collective records — pacing multipliers, priority
  from `struggle_score`, templated collectively-attributed reasons — and degrades gracefully
  when records are absent or below the confidence threshold (from-pyq-only records or none:
  a sound plan with default learn minutes and plain weightage-based reasons); the reveal's
  copy is honest about the ramp — the plan sharpens as the app learns the student (SPEC
  §6.1, CS-1 §1 principle 3; founder ruling 2026-09-12 — the evidence-weighted blend arrives
  at D55–D56). ✅ *New user reaches a personalized plan end-to-end, with the records table
  populated and with it empty.*
- **D30 —** Notification permission moment + morning notification skeleton.
  **Week-5 gate:** install → interview → (optional scorecard) → first plan < 5 minutes,
  demoed cold on a fresh device.

### PHASE 4 — Practice engine (Week 6, Days 31–36) — Module M5

- **D31 —** Session backend: block → question set assembly (difficulty band + NEET
  relevance filter), server-side judging. ✅ *No correct answer in any payload before that
  question is answered; judging server-side (TECH_PLAN §0.4 #4, decided at D3).*
- **D32 —** Practice UI: timer, taps, verdict + solution sheet + NCERT anchor chip.
  ✅ *A 10-question timed set feels smooth on a mid-range phone.*
- **D33 —** Session summary (accuracy, speed vs your norm, sent-to-notebook list);
  event stream persisted. ✅ *Events visible in DB with timing data.* *Added 2026-10-01
  (CS-6 §2.2): marking a block skipped offers the optional reason (No time · Too hard · Already
  know this), stored on the block; the planner acts on it at D55.*
- **D34 —** Offline mode: today's blocks + questions cached; outbox sync for results.
  ✅ *Airplane-mode test: complete a session, land, sync.*
- **D35 —** Diagnostic test (30-question adaptive flavor) reusing the session engine;
  ability estimates update chapter status; the intro names the diagnostic as the fastest
  way to shift the weight from “students like you” to “you” (SPEC §6.1, CS-1 §5.6 — added
  2026-09-12). ✅ *Diagnostic shifts a seeded user's plan.* *Added 2026-10-01 (CS-2 §4.7, CS-4 §6):
  full NEET 2018–2026 past papers as timed mocks (`kind = mock`, TECH_PLAN §0.5 item 2), out-of-syllabus
  questions excluded from the score; every mock result captured for the later private percentile.
  ✅ adds: at least one full past paper runs end to end as a timed mock (CS-2 §10).*
- **D36 —** Buffer + **Week-6 gate:** practice loop end-to-end incl. offline + diagnostic.

### PHASE 5 — Doubt solver (Weeks 7–8, Days 37–48) — Module M6 (the hero)

- **D37 —** Text-doubt path v1: normalize → cache lookup → cheap-tier answer with
  retrieval grounding. Write the `doubt_answer` prefix with comfortable margin over the
  cheap tier's 4,096-token cache floor, and treat prompt length as a per-model cost
  variable — the two tiers tokenize the same prefix ~50% apart (`.claude/rules/ai-layer.md`;
  measured 2026-09-12). ✅ *10 typed doubts answered with correct anchors.*
- **D38 —** Photo path: capture UI + vision extraction → same pipeline. ✅ *10 photographed
  printed questions extracted faithfully.* *Added 2026-10-01 (CS-3 §3.1–§3.2): the offline doubt
  queue (captured with no connection, sent automatically later, metered at send) and photo
  compression before upload with a no-regression eval; if D38 is full, both go to the D42 buffer.*
- **D39 —** Difficulty router + reasoning tier + numerical verification (independent
  re-solve; mismatch → regenerate once → honest fallback + audit queue). ✅ *Seeded
  wrong-answer test proves unverified numericals never render.*
- **D40 —** Answer contract UI: steps, anchor chip (opens NCERT reference view),
  NTA-trap note (only when PYQ-backed), follow-up chips, report flag. ✅ *Pixel/copy
  review against spec wireframe.*
- **D41 —** Cache write path (verified only) + semantic near-match; hit metrics.
  ✅ *Same question twice = instant second answer; near-duplicate hits logged.*
- **D42 —** Buffer + mid-module audit: you review 30 real answers across subjects.
  **Week-7 gate:** doubt loop works for text+photo with verification. *Added 2026-10-01 (CS-3
  §3.2): compression retro-fitted to the D28/D29 document captures; any D38 overflow.*
- **D43 —** Language behavior: EN/HI/Hinglish answer generation honoring user setting;
  copy pass on solver strings. ✅ *Same doubt in 3 languages reads naturally.*
- **D44 —** Free-tier limits (5/day, cached=half) + limit meter UI + graceful limit
  screen (paywall teaser, not a wall). Also the copy and routing for the unified Pro
  degradation path: a Pro user is never refused, so both the fair-use cap and the money
  breaker accept and queue, with honest copy per wait — "a few minutes" over the cap,
  tonight over the breaker (founder ruling 2026-09-12; TECH_PLAN §4.4, §4.8). The hard
  stop stays free-tier only. ✅ *Limit math correct across day boundary (IST).* *Added 2026-10-01
  (CS-5 §4): the free allowance is also counted per phone (install id), so switching free accounts
  never multiplies it; Pro on a shared phone is unaffected. ✅ adds: switching between free accounts
  on one phone does not raise that phone's daily free doubts (CS-5 §6.3).*
- **D45 —** Doubt → student-state write-back (concept weak-signals) with visible effect
  in next plan (“because you asked 3 Optics doubts…”). ✅ *Seeded doubts change
  tomorrow's plan with the reason line.*
- **D46 —** Doubt history screen + follow-up threading. ✅ *Follow-ups keep context.*
  *Added 2026-10-01 (CS-2 §4.4, CS-3 §3.1): a thread depth cap that invites a new doubt, no
  general chatbot anywhere, and the “answer ready” notification for a queued offline doubt.*
- **D47 —** Eval suite expansion to ~150 questions; wire the eval gate into pre-commit
  for AI-touching changes; the harness gains the `claim` fixture kind — a collective-attributed
  line and the record it must trace to — first populated from the D29 first plan's templated
  reasons, the AI reason lines joining at D56 (CS-1 §7; TECH_PLAN §4.10 — added 2026-09-12).
  ✅ *Gate demonstrably blocks a prompt change that fails.*
- **D48 —** Buffer + **Week-8 gate:** hero feature demo-ready; eval ≥ target; founder
  audit error list empty or ticketed.

### PHASE 6 — Notebook, SRS & the nightly brain (Weeks 9–10, Days 49–60) — M7 + M8

- **D49 —** Error capture from practice events + cause classification (async) with
  confidence + one-tap student correction; the classifier is seeded with the node's approved
  `misconceptions[]` and may cite one with collective attribution from event one, the
  student's correction and history overriding (CS-1 §6; TECH_PLAN §4.6 — added 2026-09-12).
  ✅ *Wrong answers appear diagnosed within minutes; correction overrides stick.*
- **D50 —** Notebook UI: summary (subject/cause + patterns line), entries, cause chips.
  ✅ *Matches spec wireframe; free-tier 30-cap notice.*
- **D51 —** SRS scheduling (3/10/25) + variant selection (prefer real question on same
  concept/distractor; else generate-and-verify). ✅ *Seeded errors produce day-3 variants.*
- **D52 —** Healed flow (3 correct variants) + Healed ✓ gallery + Danger Zones view
  (open errors × weightage). ✅ *Healing demo with seeded history.*
- **D53 —** Patterns engine v1: the plain-language weekly insights (“31% unit slips…”).
  ✅ *Insights only fire with sufficient data (evidence rule).*
- **D54 —** Buffer + **Week-9 gate:** mistake lifecycle capture→diagnose→resurface→heal
  demonstrated end-to-end. *Added 2026-10-01 (CS-2 §4.7, CS-4 §5.2; at risk): the mock autopsy
  (already scheduled here, TECH_PLAN §0.5 item 2) — summary for Free, full for Pro — and the
  timing and skip-strategy drills built from it. ✅ adds: one full past paper runs mock → autopsy end
  to end (CS-2 §10); a seeded student with a high gamble score gets targeted skip drills, and the
  drill results appear in the next autopsy's comparison (CS-4 §5.2).*
- **D55 —** Nightly re-planner: state snapshot assembly + deterministic candidate blocks
  (SRS dues, weak-node practice, backbone next, hours budget); the snapshot reads two
  sources — the approved collective record and the student state — blended per node by
  the evidence-level weighting, pacing = default × multiplier until the measured pace
  (CS-1 §5.1–§5.3; TECH_PLAN §4.5 — added 2026-09-12). Also the batch lane the nightly run
  is the first caller of: `completeBatch` down the decorator chain, the `batch` and
  batch-price columns in the ledger, and the one-record live probe restored to
  `AiLiveSmokeTest` (TECH_PLAN §4.11; founder ruling 2026-09-12). ✅ *Dry-run outputs sensible
  plans for 5 synthetic students; two day-1 students with different onboarding answers get
  visibly different, collective-informed plans with every block reason backed and
  attributed; the same student with two weeks of synthetic history has individual data
  outweigh the prior on practiced nodes; emptying the records table degrades the day-1
  plans (CS-1 §7 a–c).* *Added 2026-10-01 (CS-6 §2.1–§2.3, §2.5, §5; at risk): continuity, the
  skip-reason responses, load that follows completion, notable moments in the snapshot, and the
  **14-day simulation harness** over six archetypes (required for the D60 gate).*
- **D56 —** AI selection/ordering + reason lines + mentor note; JSON-validated output;
  deterministic fallback (a plan must ALWAYS exist); reasons carry an attribution —
  collective lines cite the record, individual lines the student's data, never blended —
  and the season prior softens volume and tone before individual slump signals fire
  (CS-1 §5.4–§5.5; TECH_PLAN §4.5, SPEC §10.9 — added 2026-09-12); the AI reason
  lines join the eval's `claim` fixtures (CS-1 §7). ✅ *Kill the AI
  mid-run → fallback plan appears; no planless morning possible.* *Added 2026-10-01 (CS-6 §2.4,
  §3; CS-4 §3–§4; at risk): the first-seven-days ramp in the mentor note, reason traceability and
  the generic-phrase check (eval gate here and at D60), and concept primers + curated lectures
  (link-out) inside learn blocks.*
- **D57 —** Batch execution for all active users + morning notification with plan
  deep-link. ✅ *Two devices, different profiles, different 7 AM plans.* *Added 2026-10-01
  (CS-3 §3.3): tomorrow's plan and its questions prefetched in the background after the nightly
  run, so Today opens from local data; the prefetched pack is D34's offline pack for tomorrow's
  own blocks, keys wiped after sync (founder ruling 2026-10-01). ✅ adds: with connectivity at
  night only, the morning Today opens in airplane mode (CS-3 §4), and D34's "the pack is the only
  pre-answer carrier" test passes on the prefetch path.*
- **D58 —** Streaks, weekly trajectory card (humble-early copy), plan negotiation chat
  v1 (reschedule/lighten/swap intents). ✅ *“Wedding this weekend” visibly rebalances
  the week with a trade-off line.* *Added 2026-10-01 (CS-2 §4.5–§4.6, CS-4 §5.1, CS-6 §4; at
  risk): streak repair framing, the Sunday review in the plan chat with its agreed focus, and the
  weekly fit question. ✅ adds: a seeded week of activity produces a review whose every statement
  traces to the student's data, and next week's plan carries the agreed focus (CS-4 §5.1).*
- **D59 —** Slump detection rules + light-day behavior + mood chip wiring.
  ✅ *Simulated 3 dark days → gentler plan + right copy.* *Added 2026-10-01 (CS-2 §4.1, §4.5–§4.6;
  CS-4 §5.2; at risk): the **crisis protocol** behaviour and its eval cases (direct, indirect, Hinglish,
  Hindi; 100% — a beta blocker), the learning-science guards, and drill placement by the planner.*
- **D60 —** Buffer + **Week-10 gate:** the full daily loop (plan→do→re-plan) runs
  unattended for 3 consecutive real days on your own test account. *Amended 2026-10-01 (CS-2 §5,
  CS-6 §5, founder decision): the gate also requires the **14-day planner simulation** to pass over
  six archetypes, showing all six CS-6 §5 properties — clearly different plans for different
  students; every reason traceable and none generic; a reaction within one day to a skip with a
  reason, a mistake cluster and repeated doubts; continuity with no unexplained swings; load
  converging on the real completion pace; the first-week ramp's expected mentor notes — and the
  beta backlog (SPEC §12.1) ships only after it. **DLT decision point:** if F1's DLT templates are
  not live by D60, the beta switches to email OTP through its flag. **The 18+ rule is revisited
  here once:** with the consent template live and F9's legal review clear, the consent flag may go
  on for the later beta waves (founder ruling 2026-10-01).*

### PHASE 7 — Money & trust (Week 11, Days 61–66) — M9 + M10

- **D61 —** Razorpay: subscribe (monthly mandate + annual), webhooks, status sync.
  ✅ *Test-mode full purchase on device.*
- **D62 —** Paywall triggers (doubt #6, notebook cap, SRS lock, weekly-report teaser)
  + the honest paywall screen (₹499 struck → ₹299 founding, ₹2,999 annual). ✅ *Each
  trigger fires exactly once per context; “Not now” = 48h silence.*
  *Added 2026-10-01 (CS-2 §2, §4.8; CS-5 §3.1–§3.2; at risk, D61–D63 together): prices as config —
  list/founding rungs, ₹3,999 struck annual, grandfathering by the stored price, the founding
  switch and GST display as configuration; one active phone per account and the devices screen.
  ✅ adds: the founding/list switch and GST display change by configuration with no release (CS-2
  §10); signing in on phone B signs phone A out, and A's queued offline work syncs when the same
  account next signs in there; the devices screen lists active phones and signs one out on request
  (CS-5 §6.3).*
- **D63 —** Cancel (2 taps, zero retention screens) + 7-day auto-refund + exam-date
  auto-pause rule. ✅ *Cancel→refund runs without human touch in test mode.*
  *Added 2026-10-01 (CS-5 §3.3; with D64; at risk): the account switcher — separate data per
  account. ✅ adds: two accounts on one phone keep fully separate data and subscriptions (CS-5 §6.3).*
- **D64 —** Privacy plumbing: data export (notebook PDF + JSON), account deletion,
  document-deletion verification job, consent texts, legal pages. ✅ *Export a real
  account; delete an account; verify purge schedule.* *Added 2026-10-01 (CS-5 §6.1, CS-2 §4.9,
  §9; at risk): terms (a subscription is personal; the one-active-phone rule in plain words), the
  WhatsApp click-to-chat link and share sheet, the legal entity name as configuration. What the
  share sheet shares in the committed build is open (TECH_PLAN §15.13; TRACKER day log).*
- **D65 —** Per-user AI budget circuit breaker + daily spend alarms + cost dashboard
  (cache rate, cost/feature, cost/user); the breaker wiring includes the Pro queue path
  (accept + queue, never a refusal — TECH_PLAN §4.8, founder ruling 2026-09-12) and the
  re-estimate of §7.7's economics on real ledger data. ✅ *Simulated runaway loop trips the
  breaker — for a free user a hard stop, for a Pro user a queued solve and honest copy.*
  *Added 2026-10-01 (CS-5 §4–§5, founder decision item 4; at risk): per-phone free limits extended,
  the unusual-use ladder (soft message → re-verify → rate limit, no automatic bans), the per-device
  daily OTP cap, and SMS/OTP spend on the cost dashboard. ✅ adds: a fourth free account on one
  phone is blocked with a friendly message (CS-5 §6.3).*
- **D66 —** **Week-11 gate:** money loop + trust promises all demonstrably true.
  (F5: marketing site drafted this week, evenings.)

### PHASE 8 — Hardening & polish (Weeks 12–13, Days 67–78) — M11

- **D67 —** Full Hinglish/Hindi copy pass with a native-speaker read (mentor voice
  audit — no guilt, no fake-human). ✅ *String review sheet signed off.* *Added 2026-10-01 (CS-2
  §4.1, §4.5; CS-6 §2.4): the crisis-protocol copy (founder review), streak-repair copy and the
  first-week ramp's mentor notes.*
- **D68 —** Notification system final: caps (2/day), quiet hours, exam-protocol config.
  ✅ *A day of simulated triggers never exceeds caps.*
- **D69 —** Performance pass: cold start, Today load, solver latency streaming, low-end
  device test. ✅ *Spec p95 targets met on the cheap test phone.* *Added 2026-10-01 (CS-3 §3.4,
  §4): images sized to device and connection; the throttled-network tests (slow 3G, airplane-mode
  doubt, offline practice, compression, prefetch) on the AVD and on the low-cost phone (F15).*
- **D70 —** Failure drills: DB restore from backup, AI provider outage behavior (honest
  errors + queue), payment webhook replay. ✅ *Each drill scripted and passing.*
- **D71 —** Security review checklist (auth, IDOR probes, rate limits, secrets scan,
  dependency audit). ✅ *Checklist committed with findings fixed.* *Added 2026-10-01 (CS-5 §5–§6.3):
  a simulated two-city concurrent session meets the soft message and re-verification, never a ban;
  no permanent hardware identifier collected.*
- **D72 —** Buffer + **Week-12 gate:** app is boringly reliable.
- **D73 —** Analytics funnels (install→plan→doubt→D7; paywall funnel; cache rate) +
  crash reporting triage flow; the CS-1 §7 metrics — day-1→day-7 plan-block completion
  trend, reason lines by attribution, collective-record coverage of served blocks
  (TECH_PLAN §10.2–§10.3 — added 2026-09-12). ✅ *Dashboards live with real test traffic.*
  *Added 2026-10-01 (CS-2 §4.10, CS-3 §5, CS-6 §4, CS-4 §4): learning-outcome metrics, network
  reliability metrics, the personal-fit measures by week of tenure, and the monthly curated-lecture
  check (TECH_PLAN §15.11).*
- **D74 —** Play Store: listing assets, data-safety form, internal testing track upload.
  ✅ *Installable from the testing track.*
- **D75 —** Beta tooling: invite codes, founder admin peek (read-only), audit-queue
  review screen, feedback link in-app. ✅ *You can review flagged answers in 2 taps.*
- **D76 —** Seed the answer cache: batch-solve the top ~500 predicted common doubts
  (from PYQ concept frequency). ✅ *Cache hit-rate head start measured.*
- **D77 —** Full dress rehearsal: you live one complete student day on production infra.
  ✅ *Punch list produced.* *Added 2026-10-01 (CS-3 §4): the throttled-network and real-device tests
  re-run.*
- **D78 —** Punch-list burn-down + **Week-13 gate:** beta build signed off. *Added 2026-10-01
  (CS-2 §4.1, §10; CS-3 §4; CS-4 §3–§4): blocking items — the crisis protocol passes its eval cases;
  the slow-network acceptance holds; primers exist for the top-50 chapters and a vetted lecture for
  every chapter. A primer counts only with its misconception line, which comes from CS-1's approved
  record (TECH_PLAN §15.7), so CS-1's `review` + `load` for the top-50 nodes must land before here.*

### PHASE 9 — Beta launch (Week 14, Days 79–84) — M12

- **D79 —** Recruit wave 1 (Telegram posts per F4 playbook; target 20 droppers).
  Onboard, watch funnels live, hotfix queue. 
- **D80–81 —** Waves 2–3 to 50 students; daily: audit 20 answers, read every feedback
  message, fix top friction. 
- **D82 —** First cohort review: activation %, first-doubt %, answer report rate,
  cache rate, cost/user. Adjust free limits/paywall copy if data says so.
- **D83 —** Pricing conversations with 10 beta students (₹299 vs ₹499 reaction notes).
  *(2026-10-01: the beta itself collects no payment — CS-2 §2.1; these notes feed F13's
  pricing-evidence review.)*
- **D84 —** **Beta gate & retro:** go/no-go criteria for the 6-week beta period; write
  the weeks 15–20 operating plan (daily 1h ops + 3h fixes/Phase-2 prep).

---

## 4. Beta period cadence (Weeks 15–20, still 4h/day)

Daily: 1h operations (audits, support, metrics) + 3h building from the beta backlog.
Weekly: cohort metrics review vs §11 of the Product Spec; one student call.
Exit criteria to public launch: D7 ≥ 35% · report rate < 1% · OTP ≥ 98% ·
crash-free ≥ 99.5% · cache ≥ 55% · at least 15 organic-feeling payments.
*Amended 2026-10-01 (CS-2 §2.1, §5; founder rulings Q7 and of the same day):* the beta is free
(six weeks of Pro, no payments collected), so the payments criterion becomes **≥ 15 beta students
opting into a founding plan to start at launch**, read at F13's pricing-evidence review, and
“OTP ≥ 98%” reads **sign-in success ≥ 98% first attempt**. The beta backlog is SPEC §12.1, in its
order, each behind a flag with its metric live, and only after the D60 gate passed.

---

## 5. Risk register (part-time edition)

- **Content-quality drag (biggest):** solution audits (D20–21) reveal a high error
  rate → add audit days, tighten generation prompts; never ship past the eval gate.
- **Scope creep:** every “small idea” goes to a PARKED.md, reviewed Sundays only.
- **External lead times:** DLT/Razorpay/NCERT are calendar risks, not effort risks —
  start F1/F2 in week 1; sandbox modes keep coding unblocked.
- **Motivation dips (yours):** the daily verify step + visible week gates are your own
  streak system. Miss days? Re-plan the week, don't compress the verify.
- **AI-cost surprises in beta:** circuit breaker (D65) + daily alarm make this a
  bounded nuisance, not a disaster.
- **Calendar note:** count backwards from NEET (early May). A mid-September start →
  beta in December → public launch in January, landing exactly in annual-plan season.
