# MARG AI — Developer Specification v1.1

**Document status:** The **Product Specification v2.0 (docs/SPEC.md) is the contract**
and supersedes this document wherever they differ. Sections 2–12 below are reference
material — a worked example of one possible implementation — and Claude Code is free
to propose better designs in its Day-3 technical plan. **Section 13 (Claude Code
implementation guide) remains fully authoritative** as the working-agreements source.
Stack versions everywhere: Java latest LTS, Spring Boot 4.x, PostgreSQL 18, current
stable Flutter.


**Product:** AI mentor app for NEET preparation (Android-first)
**Working name:** MARG AI (subject to change; keep all branding in config/strings)
**Audience for this doc:** the implementing developer(s)
**Status:** MVP build spec — everything marked *Phase 2* is explicitly OUT of scope for this build.

---

## 1. Product summary (context for the developer)

MARG AI is a fully digital, AI-only study mentor for NEET aspirants. It does NOT teach
(no lectures, no faculty). It does four things, all driven by one per-student state object:

1. **Plans** — generates an adaptive daily study plan every night, with visible reasoning.
2. **Practices** — serves timed MCQ sessions in-app (PYQs + generated questions).
3. **Solves doubts** — photo/text doubts answered with NCERT-grounded, verified explanations.
4. **Fixes mistakes** — auto-builds an "error notebook" with cause classification and
   spaced-repetition variants.

**Core design rules (apply everywhere):**
- R1. Every statement the app makes about a student must be backed by that student's own data.
- R2. No AI answer reaches a student without grounding (NCERT retrieval) and, for numericals,
  a verification pass.
- R3. Cache first. Every fresh model call must justify why it could not be a cache hit.
- R4. Cheap model by default; expensive model only via the router's difficulty decision.
- R5. Reliability is a feature: OTP works first try, sync never loses data, dashboards never lie.
- R6. Collect only data that feeds a feature. Delete uploaded document images after extraction.
- R7. Honest UX: transparent pricing, one-tap cancel, instant refund, no dark patterns.

**Monetization:** Free tier (full planner, 5 doubt-solves/day, last-30-errors notebook) and
Pro (list ₹499/mo, founding price ₹299/mo, annual ₹2,999) via Razorpay UPI autopay.

---

## 2. System architecture

```
Flutter app (Android)
    │  HTTPS (JSON REST)
    ▼
ALB / Nginx  ──►  Spring Boot API (ECS Fargate or EC2 t4g, ap-south-1)
                      │
        ┌─────────────┼───────────────┬──────────────────┐
        ▼             ▼               ▼                  ▼
   RDS Postgres   Amazon Bedrock   S3 (objects,     External APIs:
   (+ pgvector)   (global infer.   24h-delete       Razorpay, MSG91 (OTP),
                  profiles)        uploads bucket)  FCM (push)
                      ▲
   EventBridge ───► Nightly worker (batch: re-planner, SRS, trajectory)
```

- Single deployable Spring Boot service (modular monolith). No microservices.
- Nightly jobs run 00:30–06:00 IST via EventBridge → either in-process scheduled jobs
  or a short-lived Fargate task. All nightly Bedrock calls use **Batch inference**.
- All model access through Bedrock **global inference profiles** from ap-south-1.

### 2.1 Technology decisions (fixed)

| Concern | Choice |
|---|---|
| App | Flutter (Android first; web/iOS later from same codebase) |
| Backend | Java (latest LTS), Spring Boot 4.x (Web, Data JPA, Security, Scheduler) |
| DB | PostgreSQL 18 on RDS, extensions: `pgvector`, `pg_trgm` |
| AI | Bedrock: Claude Haiku 4.5 (CHEAP tier), Claude Sonnet 4.6/5 (REASON tier), Cohere multilingual embeddings |
| Object storage | S3 (`uploads/` bucket has 24h lifecycle delete) |
| Payments | Razorpay (UPI autopay subscriptions + one-time annual) |
| OTP SMS | MSG91 (or Kaleyra) with DLT-registered templates |
| Push | Firebase Cloud Messaging |
| Analytics | PostHog (self-host later; cloud free tier now) |
| Infra as code | Optional but recommended: Terraform, single small stack |

Model IDs, prompt templates, tier routing, limits, and prices live in **configuration**
(SSM Parameter Store), never hard-coded. Pin exact model versions.

---

## 3. Data model (PostgreSQL)

Naming: snake_case. All tables have `id UUID PK default gen_random_uuid()`,
`created_at`, `updated_at`. Only key columns listed; add sensible indexes noted.

### 3.1 Identity & account

**users**
- phone (E.164, unique, indexed), phone_verified_at
- display_name, language ENUM(en, hi, hinglish)
- role ENUM(student, admin)
- status ENUM(active, deleted); deletion = anonymize PII, keep aggregates

**subscriptions**
- user_id FK, plan ENUM(free, pro_monthly, pro_annual)
- provider ('razorpay'), provider_sub_id, status ENUM(active, paused, cancelled, past_due)
- current_period_end, founding_price BOOLEAN
- Note: auto-pause rule after exam date (see §8.6)

### 3.2 Curriculum content (read-mostly)

**syllabus_nodes** — the taxonomy tree + graph
- code (stable string ID, e.g. `PHY.11.ROT` — indexed, used everywhere)
- subject ENUM(physics, chemistry, botany, zoology), class_level (11/12)
- parent_id FK nullable, kind ENUM(subject, unit, chapter, topic)
- name_en, name_hi
- weightage_marks_avg NUMERIC  (computed from PYQ stats)
- default_learn_minutes INT     (pacing default; recalibrated later)
- prerequisites UUID[]          (edges for the plan backbone)

**ncert_paragraphs**
- node_id FK, book_code, chapter_no, section, para_no
- text_en, text_hi, figure_refs JSONB
- embedding vector(1024)        (pgvector; ivfflat index)
- tsv tsvector                  (generated column over text_en + text_hi; GIN index)

**questions**
- source ENUM(pyq, generated), year INT nullable, exam ('NEET','AIPMT') nullable
- node_id FK (primary topic), secondary_node_ids UUID[]
- stem_en, stem_hi, options JSONB [{key:'A',text_en,text_hi}], correct_key CHAR(1)
- solution_md_en, solution_md_hi        (verified step-by-step)
- ncert_anchor_ids UUID[]               (paragraph links)
- difficulty NUMERIC(0..1), avg_time_sec INT
- distractor_map JSONB                   ({'B':'sign_error','C':'unit_slip',...} — feeds diagnosis)
- verified BOOLEAN, audit_status ENUM(auto, founder_ok, flagged)

**cutoffs**
- year, category, quota_scope ('AIQ','MH',...), qualifying_marks, seat_type

### 3.3 Student state & activity (hot tables)

**student_profiles** (1:1 users) — the "student state object" root
- attempt_type ENUM(fresher_2yr, fresher_1yr, dropper, repeater)
- target_year INT, coaching ENUM(none, pw, aakash, allen, unacademy, other)
- hours_weekday NUMERIC, hours_weekend NUMERIC
- target JSONB {goal:'govt_mbbs', category:'OBC', state:'MH'}   (category optional)
- last_score INT nullable, scorecard JSONB nullable (extracted fields ONLY)
- batch_position JSONB {node_code: status, confidence}          (§8.2 layers 1–5)

**chapter_status** (user_id, node_id) UNIQUE
- status ENUM(untouched, ongoing, covered), source ENUM(self_report, inferred, timetable)
- ability_estimate NUMERIC(0..1)      (per-topic difficulty ladder)

**practice_events** — append-only event stream (partition by month at scale)
- user_id, question_id, session_id, chosen_key, is_correct
- time_taken_ms, position_in_session, occurred_at
- Index: (user_id, occurred_at), (question_id)

**error_entries** — the error notebook
- user_id, question_id, practice_event_id
- cause ENUM(concept_gap, silly_slip, time_pressure, gamble, unclassified)
- cause_confidence NUMERIC, student_corrected BOOLEAN
- srs_stage INT (0..3), next_review_at DATE, healed_at nullable
- Index: (user_id, next_review_at), (user_id, healed_at)

**doubts**
- user_id, input_type ENUM(photo, text), raw_text, image_s3_key nullable (deleted async)
- node_id FK nullable, cache_hit BOOLEAN, model_tier ENUM(cheap, reason)
- answer_md, ncert_anchor_ids UUID[], verified BOOLEAN
- reported BOOLEAN, report_note, audit_status

**doubt_cache**
- question_hash (normalized-text hash, unique) + embedding vector(1024)
- canonical answer fields (same shape as doubts answer)
- hit_count INT
- Lookup: exact hash → else vector similarity > 0.93 on same node subtree.

**daily_plans**
- user_id, plan_date UNIQUE(user_id, plan_date)
- blocks JSONB [ {type: learn|practice|revise|mock, node_code, minutes,
    question_ids?, reason_text, status: pending|done|skipped|deferred} ]
- mentor_note TEXT (the visible reasoning line)
- generated_by ENUM(nightly, onboarding, renegotiation)

**mentor_messages** (chat with planner/mentor)
- user_id, direction ENUM(user, mentor), text, intent ENUM(negotiate_plan, checkin, other)

**wellbeing_signals**
- user_id, date, mood ENUM(good, ok, low) nullable (one-tap), inferred_slump BOOLEAN

**trajectory_snapshots**
- user_id, week_start, predicted_min INT, predicted_max INT, inputs JSONB

### 3.4 Ops

**ai_calls** (cost ledger — REQUIRED)
- user_id nullable, feature ENUM(doubt, plan, classify, report, verify, pipeline)
- model_id, tier, input_tokens, output_tokens, cached_input_tokens, batch BOOLEAN
- cost_inr NUMERIC (computed at insert from a price table in config)
- Index: (feature, created_at), (user_id, created_at)
- Daily rollup powers the cost dashboard + billing alarm (§10.5).

**audit_queue** — flagged answers/reports for founder review; resolution updates
`questions`/`doubt_cache` and, if needed, invalidates cache rows.

---

## 4. AI layer specification

### 4.1 AiClient interface (Java)

All model access goes through ONE interface. No feature code calls Bedrock directly.

```java
public interface AiClient {
    SolveResult solveDoubt(DoubtInput in, RetrievalContext ctx, Tier tier);
    VerifyResult verifyNumerical(String question, String proposedSolution); // REASON tier
    Classification classifyError(PracticeEventCtx ctx);                     // CHEAP
    RoutedTier routeDifficulty(String normalizedQuestion);                  // CHEAP
    PlanResult generatePlan(StudentStateSnapshot s, PlanBackboneSlice b);   // CHEAP, batch
    String mentorMessage(MessageIntent intent, StudentStateSnapshot s);     // CHEAP
    ExtractedDoc extractDocument(DocType type, byte[] image);               // CHEAP vision
    float[] embed(String text);                                            // Cohere
}
```

- Implementations: `BedrockAiClient` (prod), `FakeAiClient` (tests, returns fixtures).
- Model IDs per method come from config: e.g. `ai.tier.cheap=global.anthropic.claude-haiku-4-5`,
  `ai.tier.reason=global.anthropic.claude-sonnet-4-6` (verify exact available IDs in console).
- Every call writes an `ai_calls` row (tokens from the API response).
- Timeouts: 20s real-time, 10min batch. Retries: 2 with jitter; on final failure return a
  typed error the UI can render honestly ("I couldn't solve this right now — try again").

### 4.2 Doubt-solving pipeline (the hero path)

```
input (photo|text)
  → if photo: extractDocument(QUESTION) → normalized question text + detected diagram desc
  → normalize (strip whitespace/numbering, lowercase for hash)
  → CACHE: exact hash hit? → serve (log hit)
      else embed → vector search doubt_cache (cos > 0.93, same subject) → serve
  → routeDifficulty → CHEAP or REASON
  → RETRIEVAL: hybrid search ncert_paragraphs
      (top 4 by vector + top 4 by tsquery, rerank, dedupe, cap ~2.5k tokens)
      + up to 2 linked PYQs with verified solutions
  → PROMPT (cached prefix: system + format instructions; variable: question + retrieval)
  → answer generated in user's language (en/hi/hinglish)
  → if numerical/quantitative: verifyNumerical (independent re-solve; on mismatch:
      one regeneration with both attempts in context; still mismatch → honest fallback UI
      + audit_queue entry; NEVER serve unverified numericals)
  → persist doubts row, write doubt_cache (only verified answers), tag node_id,
      update chapter weak-signal in student state
  → response contract (JSON): { steps_md, ncert_anchor: {code, display}, nta_trap_md?,
      followups: [q1, q2], language }
```

**Free-tier limit:** 5 solves/day (cache hits count as 0.5 toward the limit).
Enforced server-side; limit state returned in every solve response for the UI meter.

**Answer style contract (prompt requirement):** steps first, one concept sentence,
NCERT anchor line, optional NTA-trap note (only if a linked PYQ supports it — R1),
max ~350 words, no meta-talk, match user language.

### 4.3 Nightly re-planner

Runs 00:30 IST for every user active in last 14 days (batch API, CHEAP tier).

Inputs (assembled per user as `StudentStateSnapshot`): profile, chapter_status,
last 7 days of plan block outcomes, practice accuracy/speed by node, open error_entries
due for review, doubt topics of the week, wellbeing_signals, streak, days-to-exam,
backbone slice (next nodes per prerequisites + weightage + batch_position).

Deterministic pre-computation in Java (NOT the model): candidate block list —
due SRS reviews (capped 40% of day), weak-node practice, next backbone learn node,
time budget from hours_*. The model's job: select/order blocks to fit the budget,
write `reason_text` per block and the day's `mentor_note`, adjust for slump
(low mood/shrinking sessions → lighter day, protect streak).

Output validated against JSON Schema; on validation failure fall back to the
deterministic plan with templated reasons (the app must NEVER have a planless morning — R5).

Slump detection (deterministic, before the model): 3-day trailing session minutes
< 40% of user's 14-day median OR accuracy drop >15pts → set inferred_slump.

### 4.4 Error classification

On each wrong `practice_event` (async, within minutes, CHEAP):
inputs = distractor_map[chosen_key], time_taken vs user's node median, position_in_session,
user history on node, question difficulty vs ability_estimate.
Output = cause + confidence. confidence < 0.6 → cause=unclassified and the UI may ask the
one-tap question ("Knew it / Guessed / Ran out of time"). Student correction always wins
and sets student_corrected=true (feeds future prompts for this user).

SRS: stages at +3, +10, +25 days (config). Review = a *variant* question: prefer another
verified question on same node+distractor concept; else generate one (REASON, verified,
audit_status=auto) and save to `questions`. Correct at stage 3 → healed_at set.

### 4.5 Evaluation & quality gates

- `eval/` suite: ~200 hand-verified questions (JSON fixtures) across subjects/difficulty.
- CI job runs the doubt pipeline against the suite on ANY change to prompts, models,
  routing, or retrieval. Gate: ≥97% correct final answers, 0 unverified numericals served.
- Weekly founder audit: 20 random served answers + all audit_queue items.
- Model/prompt changes ship behind a config flag; instant rollback = config revert.

---

## 5. API design (REST, JSON)

Base: `/api/v1`. Auth: short-lived JWT (15 min) + refresh token (30 d, rotating),
issued after OTP verification. All endpoints per-user rate-limited (default 60/min).

**Auth**
- `POST /auth/otp/request` {phone} → 200 (MSG91 send; 3/hour/phone)
- `POST /auth/otp/verify` {phone, code} → {jwt, refresh, is_new_user}
- `POST /auth/refresh`

**Onboarding**
- `GET  /onboarding/state` → next interview step + syllabus grid payload
- `POST /onboarding/answers` (idempotent, partial saves)
- `POST /onboarding/scorecard` (multipart) → extracted fields for confirmation
- `POST /onboarding/scorecard/confirm` {fields} → persists; image queued for delete
- `POST /onboarding/complete` → generates first plan synchronously (<6 s)

**Plan**
- `GET  /plan/today` (also returns yesterday if unfinished + streak + trajectory card)
- `POST /plan/blocks/{blockId}/status` {done|skipped|deferred}
- `POST /plan/negotiate` {text} → mentor reply + (optionally) revised plan
- `GET  /plan/week`

**Practice**
- `POST /practice/sessions` {block_id} → {session_id, questions[] (no correct_key!)}
- `POST /practice/sessions/{id}/answers` {question_id, chosen_key, time_taken_ms}
    → {is_correct, solution_md, anchor} (server judges; events persisted)
- `POST /practice/sessions/{id}/finish` → summary

**Doubts**
- `POST /doubts` (multipart photo | {text}) → solve response + remaining_today
- `POST /doubts/{id}/followup` {text}
- `POST /doubts/{id}/report` {note}
- `GET  /doubts?cursor=`

**Notebook**
- `GET  /notebook/summary` (counts by subject/cause, patterns line, free-tier cap notice)
- `GET  /notebook/entries?filter=`
- `POST /notebook/entries/{id}/correct-cause` {cause}

**Wellbeing / signals**
- `POST /signals/mood` {good|ok|low}
- `POST /signals/batch-position` {node_code, status}

**Billing**
- `GET  /billing/status`
- `POST /billing/subscribe` {plan} → Razorpay order/mandate params for SDK
- `POST /billing/webhook` (Razorpay; verify signature; idempotent)
- `POST /billing/cancel` → immediate, one screen, no retention flow (R7)

**Account**
- `DELETE /account` → anonymize + schedule data purge (30 d), confirmation required

Error envelope: `{error: {code, message_en, message_user_lang}}`. All destructive or
paid actions idempotent via `Idempotency-Key` header.

---

## 6. Flutter app — screens & behaviors

Navigation: bottom bar — **Today | Practice | Doubts | Notebook | Profile**.

1. **Splash/Login** — phone + OTP. Auto-read OTP (SMS Retriever). Retry + "change number".
   Must survive flaky network: queue + clear errors. (R5: this screen is sacred.)
2. **Onboarding interview** — chat-style steps, syllabus grid (3-state taps),
   language picker, optional scorecard camera flow (frame guide → extracted fields
   review → confirm/edit). Ends with first plan reveal + "See you at 7 AM?" + notif permission.
3. **Today** — date header, streak flame, mentor_note card, 2–4 block cards with
   reason_text and CTA per type; trajectory mini-card (weekly); mood one-tap chip (dismissible).
   Offline: today's plan + current block's questions cached (see §6.1).
4. **Practice session** — timer per question, one-hand answer taps, instant verdict +
   solution sheet (anchor chip → NCERT reference view), progress bar, finish summary.
5. **Doubt solve** — camera-first with gallery/text toggle; crop hint; streaming answer
   render (markdown + LaTeX via flutter_math); anchor chip; follow-up chips;
   report flag; remaining-solves meter for free tier.
6. **Notebook** — summary header (patterns line), filter by subject/cause,
   entry cards (question, your answer vs correct, cause chip — tappable to correct),
   healed section with ✓, "danger zones" view (open errors sorted by weightage).
7. **Paywall** — single screen, triggered contextually (doubt #6, notebook cap, SRS lock).
   Shows ₹499 struck → ₹299 founding, annual ₹2,999 highlighted, honest bullet list,
   cancel/refund promise line. Razorpay SDK checkout.
8. **Profile/Settings** — language, target editor, subscription manage (cancel = 2 taps),
   data export request, delete account, support (WhatsApp/email link), legal links.

### 6.1 Offline & sync
- Local store (drift/sqlite): today's plan, active session questions, unsent
  practice_events/mood taps (outbox pattern with retry), last notebook summary.
- Rule: a student on a train can finish today's practice; events sync later.
  Doubt solving requires network (show friendly offline state).

### 6.2 Notifications (FCM)
- Morning plan (default 07:00, user-adjustable), streak-save (20:30 if 0 blocks done),
  SRS due nudge (max 1/day), weekly trajectory (Sun 19:00).
- Hard cap 2/day. All copy in user language, mentor voice. Deep links to screens.
- Quiet period: exam week per §8.6.

---

## 7. Content pipeline (pre-launch batch jobs)

Separate module `pipeline/` (same repo), run via CLI. Order matters:

1. **Taxonomy load** — `syllabus_nodes` from a reviewed CSV (founder-owned artifact).
2. **NCERT ingest** — official PDFs → extraction (text, LaTeX for equations, figure refs)
   → paragraph records EN+HI aligned → embeddings (batch) → tsvector auto.
   Store source PDFs in S3 `content/` (private). Track NCERT edition in metadata.
3. **PYQ ingest** — official papers → `questions(source=pyq)` → AI solutions
   (REASON, batch) → verification pass → founder audit queue (audit_status) →
   anchor linking (retrieval match) → distractor_map generation (CHEAP, audited on sample).
4. **Stats** — compute weightage_marks_avg, difficulty, avg_time per node → update nodes.
5. **Backbone** — prerequisites CSV + archetype track definitions (YAML in repo,
   educator-reviewed) → loaded to config tables.
6. **Seed generation** — generated questions for top-weightage nodes until each node has
   ≥30 usable questions across difficulty bands (verified path, audit sample 10%).
7. **Cutoffs load** — CSV.

Every pipeline step idempotent and re-runnable (upsert by natural keys).

---

## 8. Feature rules & edge cases (product decisions already made)

8.1 **Plan absence is forbidden** — if nightly job failed, `GET /plan/today` builds the
     deterministic fallback on-the-fly and flags `generated_by=onboarding`.
8.2 **Batch position** — self-report (onboarding) < timetable photo < weekly confirm <
     behavior inference; store per-node confidence; the plan only *mentions* the batch
     when confidence ≥ 0.7 (R1).
8.3 **Language** — user-switchable anytime; answers/plans regenerate in new language
     going forward (no retro-translation of history).
8.4 **Minors** — DOB asked at onboarding; if <18, parent phone captured and consent OTP
     required before any document upload feature unlocks (DPDP). Category field always
     optional with inline "why we ask" note.
8.5 **Fair use (Pro)** — REASON-tier fresh solves soft cap 30/day; beyond → queue as
     batch ("answer in a few minutes") rather than refuse.
8.6 **Exam-season behavior** — config exam_date: T-21d planner shifts to revision-only
     mode; T-3d light-recall mode; T-0 single good-luck message then notification silence
     14 d; subscription auto-pauses at period end after exam (no silent June renewals);
     post-result flows (journey card, export, referral / continuity offer) are Phase 2 —
     but the auto-pause rule ships in MVP.
8.7 **Refunds** — self-serve within 7 days of any charge, automatic via Razorpay API.

---

## 9. Non-functional requirements

- **Performance:** p95 — plan fetch <400 ms; practice answer judge <250 ms;
  cached doubt <1.5 s; fresh CHEAP doubt <8 s; fresh REASON with verify <25 s
  (stream partials to UI).
- **Security:** TLS everywhere; JWT short-lived; IAM roles (no static AWS keys);
  secrets in SSM; OWASP top-10 review before launch; practice answers judged
  server-side only (correct_key never leaves server).
- **Privacy/DPDP:** consent text at signup; minors flow (§8.4); uploaded images deleted
  ≤24 h (S3 lifecycle + async delete after confirm); account deletion ≤30 d;
  data export (JSON + PDF of notebook) self-serve; privacy policy page; no third-party
  ads/SDKs beyond FCM, Razorpay, PostHog.
- **Observability:** structured JSON logs (CloudWatch); request IDs end-to-end;
  dashboards: DAU, funnel (install→first plan→first doubt→D7), cache hit rate,
  ai_calls cost by feature, OTP delivery rate, crash-free %.
- **Cost guardrails:** daily Bedrock spend alarm (config threshold); per-user daily
  AI budget circuit breaker (config, e.g. ₹25/day) → degrade to batch/queue, alert.
- **Backups:** RDS automated (7 d) + weekly logical dump to S3. Restore drill before launch.

---

## 10. Build plan & acceptance criteria (6 weeks)

**W1 — Foundations:** repo mono-structure (`app/`, `server/`, `pipeline/`, `eval/`);
CI; Terraform baseline; auth (OTP happy+unhappy paths); users/profile schema;
taxonomy load. ✅ Accept: login on a real device via MSG91; taxonomy queryable.

**W2 — Onboarding + Plan v0 + Practice:** interview flow; deterministic first plan;
Today screen; practice sessions end-to-end with events. ✅ Accept: new user reaches a
personalized plan <5 min after install and completes a timed 10-question block offline-tolerant.

**W3 — Doubt solver:** full pipeline (extract→cache→route→retrieve→answer→verify→cache);
NCERT ingest done; eval suite v1 passing. ✅ Accept: photo of a printed PYQ returns a
verified, anchored answer; repeat of same question is a cache hit <1.5 s.

**W4 — Notebook + nightly brain:** error capture/classification; SRS; notebook UI;
nightly re-planner replacing v0; slump rules. ✅ Accept: a seeded week of activity
produces a next-day plan whose blocks all carry data-backed reasons; wrong answers
appear in notebook with causes; day-3 variant appears on schedule.

**W5 — Billing + trust + docs:** Razorpay subscribe/cancel/refund/webhooks; free-tier
limits + paywall triggers; scorecard flow with delete; trajectory card; privacy/consent;
marketing site. ✅ Accept: full money loop with test creds incl. cancel + auto refund;
uploaded image provably gone ≤24 h.

**W6 — Hardening + beta:** offline polish; Hinglish copy pass; notifications; dashboards;
load test (200 concurrent solves); security checklist; beta build to 50 students.
✅ Accept: crash-free >99%; eval gate green; cost dashboard live; beta cohort onboarded.

---

## 11. Explicitly OUT of scope (Phase 2 — do not build now)

Video answer generation (programmatic animation + TTS), viva/voice mode, parent digest
web view, external mock-scorecard ingestion, web app, iOS, community/leaderboards beyond
the single percentile line, referral/graduation flows, JEE vertical, NCERT license badge
(pending application), Pro+ tier.

---

## 12. Open items for the founder (not blockers to start)

1. Final product name + domain (config-only change).
2. NCERT licensing application status.
3. Educator review booking for backbone (needed by W4).
4. Confirm exact Bedrock model IDs available in the AWS account (console → Model access).
5. Razorpay KYC + DLT SMS template registration (start W1 — these have lead times).

---

## 13. Claude Code implementation guide

This project will be built with Claude Code. This section defines the repo scaffolding,
memory files, guardrails, and workflow so agentic sessions stay accurate, cheap, and safe.
Principles (from current Claude Code guidance): **CLAUDE.md = short always-on context;
rules = path-scoped knowledge; hooks = enforced policy; skills = procedures; subagents =
delegated side-work.** Memory is context, not enforcement — anything that MUST happen
(tests before commit, no secrets) is a hook or permission, never just a sentence in CLAUDE.md.

### 13.1 Repository layout

```
marg-ai/
├── CLAUDE.md                  # short, always loaded (keep <150 lines)
├── .claude/
│   ├── settings.json          # permissions, hooks
│   ├── rules/                 # path-scoped rule files (loaded on demand)
│   │   ├── server.md          #   applies to server/**
│   │   ├── app.md             #   applies to app/**
│   │   ├── pipeline.md        #   applies to pipeline/**
│   │   └── ai-layer.md        #   applies to server/**/ai/**
│   ├── commands/              # slash commands (prompt templates)
│   │   ├── week.md            #   /week N — loads that week's build plan + acceptance criteria
│   │   ├── endpoint.md        #   /endpoint — scaffold controller+service+test per §5 contract
│   │   └── evalgate.md        #   /evalgate — run eval suite, summarize failures
│   ├── skills/
│   │   └── release-checklist/ # procedural: migration check, eval gate, changelog
│   └── agents/
│       ├── spec-auditor.md    # reviews diffs against THIS spec, read-only tools
│       └── db-migrator.md     # writes Flyway migrations only under server/src/main/resources/db
├── docs/
│   └── SPEC.md                # THIS document, committed verbatim (source of truth)
├── app/          # Flutter
├── server/       # Spring Boot
├── pipeline/     # content jobs
└── eval/         # eval suite fixtures + runner
```

Commit `docs/SPEC.md` first. Every Claude Code session should treat it as the contract;
CLAUDE.md points to it rather than duplicating it (keeps context small).

### 13.2 CLAUDE.md (copy verbatim, then maintain)

```markdown
# MARG AI — working agreements

Product: AI mentor app for NEET (no human faculty). Full contract: docs/SPEC.md.
Read the relevant SPEC section BEFORE implementing a feature; cite section numbers
in your plan. If code and SPEC conflict, say so — do not silently pick one.

## Stack (fixed — do not substitute)
- server/: Java (latest LTS), Spring Boot 4, Maven, Postgres 18 (Flyway migrations), pgvector
- app/: Flutter (Android target), Riverpod, drift for offline
- ai: AWS Bedrock ONLY via AiClient interface (server/.../ai/). Model IDs from config.
- infra: ap-south-1; secrets via SSM; never write AWS keys anywhere

## Commands
- server: `cd server && ./mvnw verify` (must pass before any commit)
- app: `cd app && flutter analyze && flutter test`
- eval gate: `cd eval && ./run.sh` (required after ANY change to prompts/routing/retrieval)
- local db: `docker compose up db` (root compose file)

## Hard rules
- correct_key never leaves the server; answers judged server-side (SPEC §5)
- no AI answer path without retrieval grounding + numerical verification (SPEC §4.2, R2)
- every Bedrock call logs an ai_calls row (SPEC §3.4)
- money endpoints idempotent; Razorpay webhook signature verified (SPEC §5)
- uploaded images: S3 uploads/ bucket only (24h lifecycle) (SPEC R6)
- schema changes ONLY via Flyway migration + matching JPA entity update
- TODOs forbidden in committed code; raise in the session instead

## Style
- small PR-sized commits per task; conventional commit messages
- tests accompany every service-layer change; controller tests via MockMvc
- Flutter: no logic in widgets; state in Riverpod providers; strings in ARB (en/hi)

## Compact instructions
When compacting: preserve API contract changes + rationale, migration list,
open TODOs from the current /week task list, eval gate status. Summarize exploration.
```

### 13.3 .claude/settings.json (permissions + hooks)

Goals: Claude may build/test freely; it may NOT touch prod, secrets, or commit
with failing checks. Enforcement via permissions + PreToolUse/PostToolUse hooks
(hook blocks with exit code 2).

```jsonc
{
  "permissions": {
    "allow": [
      "Bash(./mvnw *)", "Bash(flutter *)", "Bash(docker compose *)",
      "Bash(git status)", "Bash(git diff *)", "Bash(git add *)", "Bash(git commit *)",
      "Bash(psql -h localhost *)", "Read", "Write", "Edit", "Glob", "Grep"
    ],
    "deny": [
      "Bash(aws *)",            // infra changes are human-run (or a separate approved session)
      "Bash(git push *)",       // human pushes after review
      "Read(.env*)", "Read(**/secrets/**)",
      "WebFetch"                // no random web content into context; docs go via you
    ]
  },
  "hooks": {
    "PreToolUse": [
      {
        "matcher": "Bash(git commit *)",
        "hooks": [{ "type": "command",
          "command": "scripts/precommit-gate.sh" }]   // runs mvnw verify + flutter analyze
      },                                              // + blocks if eval-touched w/o evalgate
      {
        "matcher": "Write|Edit",
        "hooks": [{ "type": "command",
          "command": "scripts/block-paths.sh" }]      // blocks writes to infra/prod paths,
      }                                               // .env, anything matching /secret/i
    ],
    "PostToolUse": [
      {
        "matcher": "Write|Edit",
        "hooks": [{ "type": "command",
          "command": "scripts/detect-secrets.sh" }]   // regex scan of the diff; exit 2 on hit
      }
    ]
  }
}
```

Write the three small scripts first (W1, with Claude Code itself); they are the
project's real policy engine.

### 13.4 Path-scoped rules (examples)

`.claude/rules/ai-layer.md` (loads only when touching AI code):
- prompt templates live in `server/src/main/resources/prompts/*.st`; never inline strings
- any change here requires: eval gate run + a line in `docs/prompt-changelog.md`
- REASON-tier calls must pass through DifficultyRouter; direct tier selection is a bug
- cache writes only after `verified=true`

`.claude/rules/app.md`:
- follow SPEC §6 screen list; do not invent screens
- every network call goes through ApiClient with the error envelope of SPEC §5
- offline outbox pattern for practice events (SPEC §6.1) — never fire-and-forget

### 13.5 Subagents

`spec-auditor` (tools: Read, Glob, Grep only; model: inherit): given a diff or feature
name, checks conformance to docs/SPEC.md and reports violations with section refs.
Run it at the end of every /week task before commit — it keeps the main session honest
without polluting context.

`db-migrator` (tools: Read, Write scoped to the migrations dir): drafts Flyway
migrations + rollback notes from an entity-change description. Keeps DDL consistent.

### 13.6 Slash commands

`/week N` → injects: the W{N} scope + acceptance criteria from SPEC §10, the relevant
SPEC sections list, and instructions: *"Plan first (do not code until the plan is
approved), break into ≤8 tasks, after each task run tests, end with spec-auditor pass
and the week's acceptance demo steps."*

`/endpoint {name}` → scaffolds controller + service + repository + MockMvc test matching
the SPEC §5 contract (envelope, auth, idempotency where marked).

`/evalgate` → runs eval suite, prints pass rate vs the 97% gate, lists failures grouped
by subject, and refuses sign-off language if the gate fails.

### 13.7 Session workflow (how to actually drive the build)

1. **One week-scope per session-family.** Start with `/week 3`, review Claude's plan in
   plan mode, approve, let it execute task by task. Don't mix weeks in one session.
2. **Explore → plan → code → verify.** For anything nontrivial ask Claude to read the
   relevant SPEC sections and existing code first and present a plan; approving plans is
   where you add the most value as reviewer.
3. **Small commits, human pushes.** Claude commits per task (gate-enforced); you review
   `git log -p` and push. Never let a session end with uncommitted work.
4. **Keep context lean.** Point Claude at files/sections; don't paste the whole spec into
   chat (it's on disk). Use `/compact` at natural boundaries; the compact instructions in
   CLAUDE.md preserve what matters. If a session drifts, start fresh — CLAUDE.md + SPEC
   + git history reconstitute state cheaply.
5. **Update memory on every correction.** When you correct Claude twice about the same
   thing, that sentence belongs in CLAUDE.md or a rules file — with # shorthand or by
   editing the file. Memory should grow from real friction, not speculation.
6. **Claude never holds real credentials.** Local dev uses docker Postgres + FakeAiClient
   by default; a `BEDROCK_LIVE=1` profile (human-launched) enables real Bedrock calls in
   dev, with the per-day cost circuit breaker (SPEC §9) active even in dev.
7. **Fridays:** run the week's acceptance criteria from SPEC §10 as a literal demo script;
   spec-auditor + /evalgate green before calling the week done.

### 13.8 First session bootstrap prompt (copy-paste to start the project)

> Read docs/SPEC.md fully. Then: (1) create the repo layout from §13.1 including
> CLAUDE.md (§13.2 verbatim), settings.json (§13.3) and the three gate scripts,
> rules files (§13.4), subagents (§13.5) and commands (§13.6); (2) set up the root
> docker-compose with Postgres 18 + pgvector; (3) scaffold server/ (Spring Boot 4,
> Maven, Flyway, first migration = users + subscriptions from §3.1) and app/
> (Flutter, Riverpod, drift, ARB i18n en/hi); (4) wire CI (GitHub Actions: mvnw verify,
> flutter analyze/test, eval placeholder). Plan first; do not code until I approve.
