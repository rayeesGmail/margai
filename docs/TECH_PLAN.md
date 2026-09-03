# MARG AI — Technical Plan v1.0 (D3)

**Status:** DRAFT — under founder review (PLAN D3, 2026-09-03). Becomes APPROVED when the founder
says so; the status line is the only thing that changes at that moment.
**Derived from:** docs/SPEC.md v2.0 (the contract). docs/DEV_SPEC.md §2–§12 was read as a worked
example and is confirmed, amended or replaced section by section in §0.3 below.
**Cite as:** `TECH_PLAN §n`. Rules, agents and slash commands that say "the D3-approved plan /
schema / API document in docs/" mean this file.

---

## 0. How to read this document

### 0.1 Precedence once approved

1. docs/SPEC.md — product contract. Wins every conflict.
2. docs/TECH_PLAN.md (this file) — how the product is built: architecture, data, API, AI, app,
   pipeline, infra, testing, conventions.
3. docs/DEV_SPEC.md §13 — working agreements (authoritative, unchanged).
4. docs/DEV_SPEC.md §2–§12 — reference only; where it differs from this file, this file wins.
5. docs/PLAN.md — the schedule; docs/TRACKER.md — live state; docs/DECISIONS.md — spec-silent choices.

Where this plan is silent, CLAUDE.md applies: choose the boring, maintainable option and record it
in DECISIONS.md. Where this plan turns out to conflict with SPEC, say so in the session and fix the
plan — never the behaviour.

### 0.2 What this plan changes about the repo today

Nothing in code. It fixes the shape that D4 onwards fills in: package layout (§1), the migration
schedule (§2.9), the endpoint contract (§3), the AI seam (§4.1) and the app skeleton (§5). It also
adds one line to CLAUDE.md's precedence list on approval, and its §14 is copied into DECISIONS.md.

### 0.3 Disposition of DEV_SPEC §2–§12

| DEV_SPEC | Verdict | Where in this plan | Why |
|---|---|---|---|
| §2 System architecture | Confirmed, amended | §1, §7 | Nightly work runs as a scheduled ECS task from the same image; Bedrock batch inference only above its minimum job size (§0.4 #2) |
| §2.1 Technology decisions | Confirmed | §4.9, §7.3 | Model IDs pinned in config; exact IDs to be confirmed in the console (DEV_SPEC §12 item 4) |
| §3 Data model | Amended | §2 | Enums as text + CHECK; HNSW index; arrays replaced by join tables where a foreign key matters; blocks get their own table; tables added for OTP, refresh tokens, sessions, consent, documents, notifications, idempotency, billing events, usage counters, audit |
| §4.1 AiClient | Replaced | §4.1 | One primitive (`complete`/`embed`) with typed feature tasks around it; ledger, breaker, retry and tier policy as decorators; REASON needs a route decision by construction |
| §4.2 Doubt pipeline | Confirmed, specified | §4.3 | Stages become named components with the enforcement point of each hard rule; polling instead of streaming until D69; cross-language cache hits rendered, not re-solved |
| §4.3 Nightly re-planner | Confirmed, specified | §4.5 | Deterministic candidate builder spelled out; batch mode conditional; exam-season modes computed here |
| §4.4 Error classification | Confirmed | §4.6 | Async listener + sweeper; student correction wins |
| §4.5 Evaluation | Amended | §4.10 | Live eval is human-launched and writes a committed stamp; CI verifies the stamp and runs the fake-client layer (§0.4 #3) |
| §5 API | Confirmed, extended | §3 | Endpoints added for consent, documents, devices, usage, danger zones, trajectory, paywall state, curriculum reads, export, admin |
| §6 Flutter | Confirmed, specified | §5 | Layering, packages, offline outbox, Hinglish locale |
| §6.1 Offline & sync | Confirmed with one open conflict | §5.6, §0.4 #4 | Offline instant verdict vs server-side judging needs a founder decision |
| §6.2 Notifications | Confirmed | §2.7, §4.5, §10 | One `notification_log` table drives cap, quiet periods and dispatch |
| §7 Content pipeline | Amended | §6 | AI-touching steps live in the Java server (profile `pipeline`) so they use `AiClient`; `pipeline/` holds founder-owned inputs and reports |
| §8 Feature rules | Confirmed verbatim | cited where used | They are product decisions consistent with SPEC |
| §9 Non-functional | Confirmed | §8–§10 | Streaming NFR deferred to D69 by PLAN precedence |
| §10 Build plan (6 weeks) | Superseded | docs/PLAN.md | Already stated in CLAUDE.md |
| §11 Out of scope | Confirmed | — | Identical to SPEC §12 |
| §12 Open items | Confirmed, extended | §13 | Adds batch-inference minimum, RDS PG18 availability, infra timeline |

### 0.4 Conflicts and gaps surfaced (not silently resolved)

1. **PLAN has no infrastructure day.** S3 `uploads/` is needed at D28, a deployed API and nightly
   task at D57/D60, backups and drills at D70. DEV_SPEC §10 W1 had "Terraform baseline"; PLAN
   supersedes §10 and dropped it. Proposal: founder workstream **F8** with the milestones in §7.6.
2. **Bedrock batch inference has a minimum job size** (100 records per job as last documented; to be
   confirmed in the console). A ≤50-student beta never reaches it, so "all nightly Bedrock calls use
   batch inference" (DEV_SPEC §2, SPEC §3 "batch processing for nightly jobs") is infeasible at
   beta scale. Proposal (§4.5, §4.11): on-demand calls with bounded concurrency below the threshold,
   batch above it, behind one config flag. The fair-use queue (DEV_SPEC §8.5) is a deferred
   on-demand queue, not a batch job.
3. **The eval gate cannot run in CI the way DEV_SPEC §4.5 describes.** CI holds no AWS credentials
   (DEV_SPEC §13.7: Claude never holds credentials; `aws` is denied) and `eval/.last-pass` is
   gitignored, so CI can neither call Bedrock nor check that a live run happened. Proposal (§4.10):
   the live eval is human-launched and writes a committed stamp; CI verifies the stamp against the
   AI-content hash and runs the fake-client layer of the suite. Decision lands at D23.
4. **Offline practice verdicts vs server-side judging.** SPEC §6.2 wants instant verdicts and offline
   play for today's blocks; SPEC §3 and CLAUDE.md say answers are judged server-side and
   `correct_key` never leaves the server. Both cannot hold for an offline session. Options in §5.6;
   founder decision needed before D31/D34.
5. **DEV_SPEC §7 "separate module `pipeline/`" vs `.claude/rules/pipeline.md` "AI calls go through
   `AiClient`".** Only satisfiable if the pipeline is Java or calls the server. Hence §6.1.
6. **DEV_SPEC §9 streaming vs PLAN D69.** PLAN schedules solver streaming at D69; until then answers
   are synchronous with polling (§3.6, §4.3). Resolved by PLAN precedence, recorded here.
7. **Three SPEC features have no PLAN day:** batch timetable photo and the weekly batch-confirm card
   (SPEC §6.7), NCERT-style seed generation to ≥30 usable questions per topic (SPEC §9.3), and
   in-app full mocks with the autopsy (SPEC §4 Phase 4, §7.1 — not excluded by §12). §12.2 proposes
   where they could land; scheduling them is the founder's call.
8. **CLAUDE.md precedence list has no slot for this plan** while three rules/agents already cite it.
   Fixed on approval (§0.2).

---

## 1. Architecture

### 1.1 System context

```mermaid
flowchart LR
    APP[Flutter app<br/>Android] -- HTTPS JSON /api/v1 --> ALB[ALB + TLS]
    ALB --> API[Spring Boot API<br/>ECS Fargate · profile api]
    SCHED[EventBridge Scheduler<br/>00:30 IST] --> NIGHT[Same image<br/>profile nightly]
    API --> RDS[(RDS PostgreSQL 18<br/>pgvector · pg_trgm)]
    NIGHT --> RDS
    API --> BR[Amazon Bedrock<br/>CHEAP · REASON · VISION · EMBED]
    NIGHT --> BR
    API --> S3U[(S3 uploads<br/>1-day lifecycle)]
    API --> EXT[MSG91 OTP · Razorpay · FCM · PostHog]
    RAZ[Razorpay webhooks] --> ALB
    PIPE[Founder laptop<br/>profile pipeline] --> RDS
    PIPE --> BR
    PIPE --> S3C[(S3 content<br/>PDFs · page images · JSONL)]
```

One deployable. The API, the nightly run, the content pipeline and the eval suite are the same
Spring Boot image started with different profiles (§1.2). No queue, no cache server, no second
service at beta; the places where a second instance would need one are listed in §13.3.

### 1.2 Run modes (Spring profiles)

| Profile | Started by | Does | AI client |
|---|---|---|---|
| `api` (default) | ECS service, `./mvnw spring-boot:run` locally | HTTP API, in-process notification dispatcher, hourly sweepers (image deletion, unclassified errors) | `fake` unless `bedrock` is also active |
| `nightly` | EventBridge → ECS RunTask at 00:30 IST; locally by hand | §4.5 re-plan for every user active in 14 days, weekly trajectory + patterns on Sundays, purge job, ai spend rollup; exits when done | as above |
| `pipeline` | Founder's laptop with AWS SSO, `java -jar server.jar --spring.profiles.active=pipeline <command>` | §6 content commands (picocli) | `bedrock` (human-launched) |
| `eval` | Founder's laptop, `BEDROCK_LIVE=1 ./mvnw -Peval verify` | §4.10 live eval suite | `bedrock` |
| `bedrock` | Added by the environment (`BEDROCK_LIVE=1` locally; task definition in AWS) | Swaps `FakeAiClient` for `BedrockAiClient`; cost breaker stays on | — |
| `local` | Developer default | Compose db, seed migrations (§2.9), fake SMS/FCM/Razorpay adapters that log | `fake` |

### 1.3 Modules and package layout

Modular monolith under `com.margai`. Each module is one top-level package with `api` (public
types other modules may use), `internal` (everything else) and, where it owns HTTP, `web`. Spring
Modulith verifies the boundaries in a test from D4 on (§8.2). A module owns its tables; other
modules read them only through the owning module's `api` package or through events (§1.7).

| Module | Owns (SPEC) | Tables (§2) | Depends on |
|---|---|---|---|
| `common` | error envelope, request id, IST clock, idempotency, pagination, config binding, message catalogs | `idempotency_keys` | — |
| `auth` | OTP login, tokens, rate limits, security filter chain (§8 screen 1) | `otp_challenges`, `refresh_tokens` | common, account |
| `account` | users, student profile, language, settings, minors' consent, export, deletion (§5 DOB/consent, §6.11, §8 screens 4, 13) | `users`, `student_profiles`, `parent_consents`, `user_devices`, `data_export_jobs` | common |
| `curriculum` | syllabus tree, prerequisites, archetype tracks, cutoffs, NCERT books/paragraphs, question bank, topic traps (§9) | `syllabus_nodes`, `syllabus_prerequisites`, `archetype_tracks`, `archetype_track_steps`, `cutoffs`, `ncert_books`, `ncert_paragraphs`, `questions`, `question_topics`, `question_anchors`, `topic_traps`, `topic_trap_evidence` | common |
| `onboarding` | interview state machine, syllabus check-in, first plan trigger (§5, §8 screens 2, 5) | (writes through account + planner apis) | common, account, curriculum, documents, planner |
| `documents` | photograph → read → confirm → delete pattern (§6.8, §8 screen 3) | `document_extractions` | common, account, ai, storage |
| `planner` | Today, blocks, nightly re-plan, negotiation chat, streaks, exam-season modes, batch position (§6.1, §6.7, §8 screens 7, 14) | `daily_plans`, `plan_blocks`, `mentor_messages`, `batch_positions` | common, account, curriculum, ai, notebook.api, practice.api, wellbeing.api |
| `practice` | sessions, question serving, server judging, events, diagnostic (§5.4, §6.2, §8 screens 6, 8) | `practice_sessions`, `practice_session_questions`, `practice_events`, `chapter_status` | common, account, curriculum |
| `doubts` | solve pipeline orchestration, history, follow-ups, reports, free-tier meter (§6.3, §8 screen 9) | `doubts`, `doubt_evidence`, `doubt_cache`, `doubt_daily_usage` | common, account, curriculum, ai, billing.api |
| `notebook` | error capture, classification, SRS, healed, danger zones, patterns (§6.4, §8 screen 10) | `error_entries`, `srs_reviews`, `notebook_patterns` | common, account, curriculum, ai, billing.api |
| `wellbeing` | mood chip, slump inference (§6.6) | `wellbeing_signals` | common, account |
| `trajectory` | weekly predicted band, peer line, deep report (§6.5, §8 screen 11) | `trajectory_snapshots` | common, account, curriculum, practice.api, billing.api |
| `billing` | subscriptions, Razorpay, webhooks, paywall triggers, cancel/refund, auto-pause (§6.9, §8 screen 12) | `subscriptions`, `payments`, `billing_events`, `paywall_impressions` | common, account |
| `notifications` | FCM devices, scheduling, 2/day cap, quiet periods, dispatcher (§6.10) | `notification_log` | common, account |
| `ai` | `AiClient`, Bedrock/Fake, ledger, breaker, router, retrieval, prompts, verification, embeddings (§4) | `ai_calls`, `ai_spend_daily`, `audit_queue` | common, curriculum |
| `storage` | S3 port (uploads, content), signed URLs, deletion | — | common |
| `pipeline` | §6 CLI commands | — | ai, curriculum, storage |
| `ops` | founder admin peek, audit-queue review, cost views (PLAN D75) | — | every `api` package |

`chapter_status` sits in `practice` because ability estimates are written by practice and the
diagnostic; onboarding seeds it through `practice.api`. `audit_queue` sits in `ai` because every
producer of audit items is an AI outcome.

### 1.4 Dependency rules

```mermaid
flowchart TD
    common --> auth & account & curriculum & storage & ai
    account --> auth
    curriculum --> ai
    ai --> documents & doubts & notebook & planner & pipeline
    account --> onboarding & documents & planner & practice & doubts & notebook & wellbeing & trajectory & billing & notifications
    curriculum --> onboarding & planner & practice & doubts & notebook & trajectory & pipeline
    storage --> documents & pipeline
    documents --> onboarding
    planner --> onboarding
    practice --> planner & trajectory
    notebook --> planner
    wellbeing --> planner
    billing --> doubts & notebook & trajectory
```

Rules, enforced by the Modulith test from D4:

- Arrows point from the module that is used to the module that uses it; the graph above is acyclic
  and stays so. A new edge needs a line in DECISIONS.md.
- Only `ai` imports `software.amazon.awssdk.services.bedrock*`. Only `storage` imports S3. Only
  `billing` imports the Razorpay SDK; only `notifications` the FCM client; only `auth` the SMS client.
- Feature modules never read another module's tables directly; they call `<module>.api` or listen to
  events. `ops` is the one exception: read-only queries across `api` packages.
- Controllers live in `<module>.web`, are thin, and map to one service call.

### 1.5 Request lifecycle

1. ALB terminates TLS, forwards to the single task with `X-Forwarded-For`.
2. `RequestIdFilter` takes `X-Request-Id` or mints one; puts `request_id` into the MDC and echoes
   it back.
3. Spring Security (stateless): bearer JWT → principal `{user_id, role, language}`; unauthenticated
   routes are `/auth/otp/*`, `/auth/refresh`, `/billing/webhook`, `/actuator/health`.
4. `RateLimitFilter`: in-process token buckets keyed by user id (or phone/IP for `/auth/*`), limits
   from config (§3.4).
5. `IdempotencyFilter` on routes marked idempotent: replays a stored response for a seen
   `Idempotency-Key` (§3.5).
6. Controller validates the DTO (Bean Validation) and calls one service method.
7. Service runs in one transaction; AI calls happen outside the transaction (§4.2) with their own
   ledger rows.
8. `ApiExceptionHandler` maps any exception to the envelope `{error:{code, message_en,
   message_user_lang}}` (§3.3) with the message catalogs in the principal's language.
9. Response DTOs are records serialised in snake_case; timestamps ISO-8601 UTC; dates are IST
   calendar dates (§11.1).

### 1.6 Nightly execution model

- EventBridge Scheduler fires at 19:00 UTC (00:30 IST) and runs the image as an ECS task with
  `--spring.profiles.active=nightly,bedrock`. The task processes users in a fixed order, commits per
  user, and exits. Re-running it is safe: `daily_plans` is unique on `(user_id, plan_date)` and a
  rerun overwrites only plans it generated itself (`generated_by = nightly`), never a renegotiated one.
- If no plan exists for a user at `GET /plan/today`, the API builds the deterministic fallback on the
  spot and marks it `generated_by = fallback` (DEV_SPEC §8.1). A missing nightly run is visible as
  an alarm (§10.4), never as an empty morning.
- Locally: `./mvnw spring-boot:run -Dspring-boot.run.profiles=local,nightly` runs the same code
  against the compose db with `FakeAiClient`.
- Notifications are not sent by the nightly task. It writes `notification_log` rows with per-user
  send times; the API's in-process dispatcher (every 60 s) sends what is due. One mechanism serves
  the 07:00 plan, the 20:30 streak-save, SRS-due and Sunday trajectory (§2.7).

### 1.7 Cross-module communication

Spring application events, published after commit (`@TransactionalEventListener`) and handled in
the same JVM. They carry ids, not entities.

| Event | Published by | Consumed by | Effect |
|---|---|---|---|
| `PracticeAnswerRecorded` | practice | notebook | wrong answer → `error_entries` row + async classification (§4.6) |
| `PracticeSessionFinished` | practice | planner, trajectory | block status, ability updates already committed; planner marks block done |
| `DoubtSolved` | doubts | planner | weak-signal count per node for the next re-plan ("three Optics doubts") |
| `ErrorCauseUpdated` | notebook | planner | re-learn block candidate when a cause is upgraded |
| `SubscriptionChanged` | billing | doubts, notebook, trajectory | limits and locks re-evaluated on next request |
| `DocumentConfirmed` | documents | onboarding, planner | scorecard/marksheet fields into profile; timetable into batch positions |
| `UserDeleted` | account | every module | anonymise/purge own rows (§9.6) |
| `PlanChanged` | planner | notifications | reschedule the morning notification if the time moved |

Events are at-least-once within the process; every listener is idempotent on its natural key.
If the JVM dies mid-listener the hourly sweeper in the owning module repairs the gap (e.g. wrong
answers without an `error_entries` row).

### 1.8 Screen and feature ownership (coverage check)

| SPEC §8 screen | Owning module | Also touches |
|---|---|---|
| 1 Splash/Login | auth | account (profile on first login) |
| 2 Onboarding interview | onboarding | curriculum (grid), practice.api (chapter status), account |
| 3 Scorecard / marksheet / timetable capture + confirm | documents | onboarding, planner (timetable) |
| 4 Parent consent | account | auth (consent OTP) |
| 5 First-plan reveal | onboarding | planner (deterministic first plan), trajectory (target line) |
| 6 Diagnostic intro + session | practice | planner (day-1 block if deferred) |
| 7 Today | planner | wellbeing (mood chip), trajectory (mini card), notifications |
| 8 Practice session + summary | practice | notebook (sent-to-notebook list) |
| 9 Doubt capture + answer + history | doubts | ai, curriculum (anchor view), billing (meter/paywall) |
| 10 Notebook views | notebook | curriculum (weightage for danger zones), billing (30-error cap) |
| 11 Weekly report | trajectory | notebook (patterns), billing (deep version) |
| 12 Paywall · subscription management | billing | — |
| 13 Profile & settings | account | billing, notifications (time), planner (hours/target edits) |
| 14 Exam-mode variants of Today | planner | notifications (silence protocol) |
| 15 Result flows | — | Phase 2 (SPEC §12); only the auto-pause rule ships, in billing |

Every SPEC §6 feature maps the same way: §6.1 planner · §6.2 practice · §6.3 doubts · §6.4 notebook
· §6.5 trajectory · §6.6 wellbeing · §6.7 planner + documents · §6.8 documents · §6.9 billing ·
§6.10 notifications · §6.11 account + billing.

## 2. Data model

### 2.1 Conventions (apply to every table)

- PostgreSQL 18, snake_case. `id UUID PRIMARY KEY DEFAULT gen_random_uuid()`,
  `created_at TIMESTAMPTZ NOT NULL DEFAULT now()`, `updated_at TIMESTAMPTZ NOT NULL DEFAULT now()`
  (Hibernate `@UpdateTimestamp`). Append-only tables (`practice_events`, `ai_calls`, `billing_events`)
  have no `updated_at`.
- Enumerations are `VARCHAR` columns with a `CHECK (col IN (...))` constraint; Java side
  `@Enumerated(EnumType.STRING)`. Adding a value is a one-line migration; PostgreSQL enum types are
  not used.
- Foreign keys are `ON DELETE RESTRICT`. Student data is anonymised (§9.6), never cascaded away.
- Time: `TIMESTAMPTZ` everywhere (UTC on the wire); `DATE` columns are IST calendar dates and are
  named `ist_date`, `plan_date`, `week_start` etc. (§11.1). Money is `BIGINT` paise.
- Vectors are `vector(1024)` with HNSW cosine indexes; full text is a stored generated `tsvector`
  with a GIN index.
- JSONB is used for shapes the app reads whole and never queries by key (extracted document fields,
  evidence trails, snapshots). Anything queried, joined or constrained gets columns or a join table.
  DEV_SPEC §3's `UUID[]` columns become join tables where a foreign key matters.
- Every table names the PLAN day it lands (§2.9). Column lists below are complete for D4 tables and
  key-complete for the rest (db-migrator fills types and indexes from these).

### 2.2 Identity and account (`account`, `auth`)

**users** — D4
```
id, phone VARCHAR(16) UNIQUE NOT NULL (E.164; NULL after deletion), phone_verified_at TIMESTAMPTZ,
display_name VARCHAR(80), language VARCHAR(8) NOT NULL DEFAULT 'en' CHECK (en|hi|hinglish),
role VARCHAR(16) NOT NULL DEFAULT 'student' CHECK (student|admin),
status VARCHAR(16) NOT NULL DEFAULT 'active' CHECK (active|deleted),
deleted_at TIMESTAMPTZ, purge_after DATE, created_at, updated_at
```
Index: `phone` (unique, partial `WHERE phone IS NOT NULL`).

**student_profiles** (1:1 users) — D4; columns marked † are filled by later days but declared now so
the row shape is stable.
```
id, user_id UUID UNIQUE NOT NULL → users,
attempt_type VARCHAR(16) CHECK (fresher_1yr|fresher_2yr|dropper|repeater),
target_year SMALLINT, coaching_mode VARCHAR(16) CHECK (classroom|online|self_study|mix),
coaching_provider VARCHAR(16) CHECK (pw|aakash|allen|unacademy|other),
hours_weekday NUMERIC(3,1), hours_weekend NUMERIC(3,1),
goal VARCHAR(16) CHECK (govt_mbbs|private_ok|bds_other|qualify),
state_code CHAR(2), category VARCHAR(8) CHECK (general|obc|sc|st|ews),
dob DATE, is_minor BOOLEAN NOT NULL DEFAULT false,
last_neet_year SMALLINT, last_neet_score SMALLINT, last_neet_rank INTEGER,
scorecard JSONB †, board_marks JSONB †,
onboarding_step VARCHAR(24) NOT NULL DEFAULT 'intro', onboarding_completed_at TIMESTAMPTZ,
exam_date DATE †, morning_notification_time TIME NOT NULL DEFAULT '07:00',
current_streak INTEGER NOT NULL DEFAULT 0, longest_streak INTEGER NOT NULL DEFAULT 0,
last_active_ist_date DATE, created_at, updated_at
```
SPEC §5.1 Q1–Q7 map one-to-one: Q1 `attempt_type`, Q2 `target_year`, Q3 `coaching_mode` +
`coaching_provider`, Q4 → `chapter_status`, Q5 hours, Q6 `goal`/`state_code`/`category`
(category optional, DEV_SPEC §8.4), Q7 `last_neet_*` or `scorecard`/`board_marks` via documents.
`scorecard` and `board_marks` hold confirmed fields only (SPEC §5.2): never an image reference.

**parent_consents** — D27: `user_id → users, parent_phone VARCHAR(16), status CHECK
(pending|consented|expired), otp_challenge_id → otp_challenges, consented_at`. Partial unique
`(user_id) WHERE status = 'consented'`.

**otp_challenges** — D7: `phone, purpose CHECK (login|parent_consent), code_hash CHAR(64),
attempts SMALLINT DEFAULT 0, expires_at, verified_at, request_ip INET`. Index `(phone, created_at)`.

**refresh_tokens** — D7: `user_id → users, token_hash CHAR(64) UNIQUE, family_id UUID, expires_at,
revoked_at, replaced_by_id → refresh_tokens, device_label VARCHAR(80), last_used_at`. Index `user_id`,
`family_id`.

**user_devices** — D30: `user_id → users, fcm_token TEXT UNIQUE, platform VARCHAR(8), app_version
VARCHAR(16), last_seen_at`.

**data_export_jobs** — D64: `user_id → users, status CHECK (queued|ready|failed|expired), s3_key,
expires_at, error`.

### 2.3 Curriculum content (`curriculum`, read-mostly)

**syllabus_nodes** — D4
```
id, code VARCHAR(32) UNIQUE NOT NULL  (PHY.11.ROT · PHY.11.ROT.TORQUE; stable, used everywhere),
subject VARCHAR(16) NOT NULL CHECK (physics|chemistry|botany|zoology),
class_level SMALLINT CHECK (11|12), parent_id → syllabus_nodes,
kind VARCHAR(8) NOT NULL CHECK (subject|unit|chapter|topic),
name_en VARCHAR(160) NOT NULL, name_hi VARCHAR(160), sort_order INTEGER NOT NULL,
weightage_marks_avg NUMERIC(6,2) NOT NULL DEFAULT 0  (D22),
default_learn_minutes INTEGER, neet_relevant BOOLEAN NOT NULL DEFAULT true  (SPEC §6.2 "NTA never asked it"),
created_at, updated_at
```
Indexes: `parent_id`, `(subject, kind)`. Constraint: `kind = 'subject'` ⇒ `parent_id IS NULL`.

**syllabus_prerequisites** — D4 (loaded D13): `from_node_id → syllabus_nodes, to_node_id →
syllabus_nodes, PRIMARY KEY (from_node_id, to_node_id), CHECK (from_node_id <> to_node_id)`. The D13
acceptance "graph has no cycles" is a loader check plus a repository test; replaces the
`prerequisites UUID[]` column of DEV_SPEC §3.2.

**archetype_tracks** — D4 (config table): `code VARCHAR(16) UNIQUE CHECK
(fresher_2yr|fresher_1yr|dropper|repeater), name_en, name_hi, weeks SMALLINT, description_md`.
**archetype_track_steps** — D4: `track_id → archetype_tracks, node_id → syllabus_nodes, sequence
INTEGER, phase CHECK (learn|mock|revision), target_week SMALLINT, UNIQUE (track_id, sequence)`.
Loaded from `pipeline/data/archetypes.yaml` (SPEC §9.5; educator review is TRACKER F3).

**cutoffs** — D4 (config table): `year SMALLINT, category VARCHAR(8), quota_scope VARCHAR(8)
(AIQ or state code), seat_type VARCHAR(16) CHECK (govt_mbbs|private_mbbs|bds|qualifying),
qualifying_marks SMALLINT, source VARCHAR(120), UNIQUE (year, category, quota_scope, seat_type)`.

**ncert_books** — D14: `code VARCHAR(16) UNIQUE (keph1 …), subject, class_level, part SMALLINT,
title_en, title_hi, edition_year SMALLINT, s3_key_en, s3_key_hi, pages_en, pages_hi`.

**ncert_paragraphs** — D14 (embedding D17)
```
book_id → ncert_books, chapter_no SMALLINT, section VARCHAR(16) ('7.9'), para_no SMALLINT,
node_id → syllabus_nodes (nullable; set by the D23 anchor pass),
text_en TEXT, text_hi TEXT, figure_refs JSONB, has_equations BOOLEAN,
embedding vector(1024), tsv tsvector GENERATED ALWAYS AS
  (to_tsvector('english', coalesce(text_en,'')) || to_tsvector('simple', coalesce(text_hi,''))) STORED,
extraction JSONB (page numbers, confidence, ai_call_id), UNIQUE (book_id, chapter_no, section, para_no)
```
Indexes: HNSW `embedding vector_cosine_ops`, GIN `tsv`, `node_id`. The unique key is the paragraph
address that anchors display as "Class 11 Physics, Ch 7, §7.9" (SPEC §6.3).

**questions** — D19
```
source VARCHAR(16) CHECK (pyq|generated), exam VARCHAR(8), year SMALLINT, paper_code VARCHAR(16),
question_no SMALLINT, node_id → syllabus_nodes NOT NULL,
stem_en TEXT NOT NULL, stem_hi TEXT, options JSONB NOT NULL ([{key, text_en, text_hi}]),
correct_key CHAR(1) NOT NULL,
solution_md_en TEXT, solution_md_hi TEXT, difficulty NUMERIC(3,2), avg_time_sec INTEGER,
distractor_map JSONB ({"B": "sign_error", …}), embedding vector(1024),
verified BOOLEAN NOT NULL DEFAULT false, audit_status VARCHAR(16) CHECK (auto|founder_ok|flagged),
verification JSONB, generated_from_error_entry_id UUID (variants, D51)
```
Natural key for PYQs: `UNIQUE (source, exam, year, paper_code, question_no)`. Indexes:
`(node_id, difficulty)`, `(source, year)`, partial on `verified`. **`correct_key` is server-only:**
the JPA entity field is `@JsonIgnore`, no response record carries it before an answer is judged, and
a test scans every practice payload for the string (§8.4). The verdict after judging returns it for
that one question only (§3.7).

**question_topics** — D19: `question_id, node_id, PRIMARY KEY (question_id, node_id)` (secondary
topics). **question_anchors** — D23: `question_id, paragraph_id, PRIMARY KEY`.

**topic_traps** — D22: `node_id → syllabus_nodes, note_en TEXT, note_hi TEXT, note_hinglish TEXT`.
**topic_trap_evidence** — D22: `trap_id, question_id, PRIMARY KEY`. A trap without at least one
evidence row is never created (service check + test): the Evidence rule for "How NTA twists this".

### 2.4 Practice (`practice`)

**chapter_status** — D4
```
user_id → users, node_id → syllabus_nodes,
status VARCHAR(16) NOT NULL DEFAULT 'untouched' CHECK (untouched|ongoing|covered),
feels_weak BOOLEAN NOT NULL DEFAULT false,
source VARCHAR(16) NOT NULL CHECK (self_report|inferred|timetable|diagnostic),
ability_estimate NUMERIC(3,2), ability_confidence NUMERIC(3,2), last_signal_at TIMESTAMPTZ,
UNIQUE (user_id, node_id), created_at, updated_at
```
Q4's three states plus long-press weak (SPEC §5.1) write `status` and `feels_weak` with
`source = self_report`; behaviour refines them later.

**practice_sessions** — D31: `user_id, block_id → plan_blocks (nullable), kind CHECK
(block|diagnostic|srs_review|mock), status CHECK (active|finished|abandoned), started_at,
finished_at, summary JSONB`. Index `(user_id, started_at)`.
**practice_session_questions** — D31: `session_id, question_id, position SMALLINT, answered BOOLEAN,
PRIMARY KEY (session_id, question_id)`.
**practice_events** — D33 (append-only): `user_id, session_id, question_id, chosen_key CHAR(1)
(NULL = skipped), is_correct BOOLEAN, time_taken_ms INTEGER, position_in_session SMALLINT,
occurred_at TIMESTAMPTZ, client_event_id UUID UNIQUE, created_at`. Indexes `(user_id, occurred_at)`,
`question_id`, `session_id`. `client_event_id` makes offline outbox replays idempotent (§5.6).

### 2.5 Doubts (`doubts`)

**doubts** — D37: `user_id, input_type CHECK (photo|text), raw_text, normalized_text, question_hash
CHAR(64), language, image_s3_key (NULL once deleted), image_deleted_at, subject, node_id (nullable),
cache_hit BOOLEAN, cache_id → doubt_cache, model_tier CHECK (cheap|reason|none), status CHECK
(answered|pending|queued|unverified_fallback|failed), answer JSONB, verified BOOLEAN,
verification JSONB, parent_doubt_id → doubts (follow-ups, D46), reported BOOLEAN, report_note,
audit_status, latency_ms, created_at, updated_at`. Indexes `(user_id, created_at)`, `question_hash`.
**doubt_evidence** — D37: `doubt_id, question_id, PRIMARY KEY` (PYQs that back the trap note).
**doubt_cache** — D41: `question_hash CHAR(64), language, subject, node_id, canonical_question TEXT,
embedding vector(1024), answer JSONB, verified BOOLEAN NOT NULL CHECK (verified = true),
hit_count INTEGER, last_hit_at, source_doubt_id, invalidated_at, UNIQUE (question_hash, language)`.
HNSW on `embedding`; index `(subject, language)`. The CHECK constraint is the database half of
"cache writes only when verified" (§4.4).
**doubt_daily_usage** — D44: `user_id, ist_date, fresh_count SMALLINT, cached_count SMALLINT,
reason_fresh_count SMALLINT, PRIMARY KEY (user_id, ist_date)`. Free-tier arithmetic (5/day, cached
= ½) and the Pro fair-use cap read one row; the IST day boundary is the key.

### 2.6 Notebook (`notebook`)

**error_entries** — D49: `user_id, question_id, practice_event_id UNIQUE, cause CHECK
(concept_gap|silly_slip|time_pressure|gamble|unclassified), cause_confidence NUMERIC(3,2),
cause_source CHECK (ai|student), student_corrected BOOLEAN, diagnosis JSONB, srs_stage SMALLINT
DEFAULT 0, next_review_ist_date DATE, healed_at, upgraded_at`. Indexes `(user_id, next_review_ist_date)
WHERE healed_at IS NULL`, `(user_id, healed_at)`, `(user_id, question_id)`.
**srs_reviews** — D51: `error_entry_id, stage SMALLINT, variant_question_id → questions,
scheduled_ist_date, practice_event_id, outcome CHECK (correct|wrong|skipped)`.
**notebook_patterns** — D53: `user_id, week_start DATE, kind VARCHAR(32), text_en, text_hi,
text_hinglish, evidence JSONB, sample_size INTEGER, UNIQUE (user_id, week_start, kind)`. A pattern
row exists only when `sample_size` meets the configured minimum (Evidence rule, PLAN D53 ✅).

### 2.7 Planner, wellbeing, trajectory, notifications

**daily_plans** — D29: `user_id, plan_date DATE, generated_by CHECK
(onboarding|nightly|fallback|renegotiation), mode CHECK
(normal|light|revision_only|final_week|exam_eve|silence), mentor_note_md TEXT NOT NULL,
inputs_snapshot JSONB, ai_call_id → ai_calls, version INTEGER, UNIQUE (user_id, plan_date)`.
**plan_blocks** — D29: `plan_id → daily_plans, position SMALLINT, type CHECK
(learn|practice|revise|mock|diagnostic), node_id, minutes SMALLINT, reason_md TEXT NOT NULL
CHECK (length(reason_md) > 0), reason_evidence JSONB, payload JSONB, status CHECK
(pending|done|skipped|deferred) DEFAULT 'pending', status_at, session_id`. Blocks are rows rather than
DEV_SPEC's JSONB array because `POST /plan/blocks/{id}/status` addresses them and streaks count them.
**mentor_messages** — D58: `user_id, direction CHECK (user|mentor), text, intent CHECK
(negotiate_plan|checkin|distress|other), resulting_plan_id`. Index `(user_id, created_at)`.
**batch_positions** — D26: `user_id, node_id, status CHECK (not_started|ongoing|done), source CHECK
(self_report|timetable|inferred|weekly_confirm), confidence NUMERIC(3,2), observed_at,
UNIQUE (user_id, node_id)`. The plan mentions the batch only when `confidence ≥ 0.7`
(DEV_SPEC §8.2).
**wellbeing_signals** — D59 (mood chip earlier, D30): `user_id, ist_date, mood CHECK (good|ok|low),
inferred_slump BOOLEAN, slump_evidence JSONB, UNIQUE (user_id, ist_date)`.
**trajectory_snapshots** — D58: `user_id, week_start, predicted_min SMALLINT, predicted_max
SMALLINT, target_marks SMALLINT, cutoff_id → cutoffs, peer_percentile SMALLINT, insight_md,
inputs JSONB, confidence CHECK (humble|growing|solid), UNIQUE (user_id, week_start)`.
**notification_log** — D30: `user_id, kind CHECK
(morning_plan|streak_save|srs_due|weekly_trajectory|exam_eve|good_luck), scheduled_for TIMESTAMPTZ,
ist_date, status CHECK (scheduled|sent|skipped|failed), skip_reason CHECK (cap|quiet|silence|no_device),
deep_link, payload JSONB, sent_at, provider_message_id, UNIQUE (user_id, kind, ist_date)`. Index
`(status, scheduled_for)`. The 2/day cap is `COUNT(*) WHERE status='sent' AND ist_date=?` at dispatch.

### 2.8 Documents, billing, AI, ops

**document_extractions** — D28: `user_id, doc_type CHECK (neet_scorecard|board_marksheet|batch_timetable),
s3_key, status CHECK (uploaded|extracted|confirmed|discarded|deleted), extracted JSONB,
confirmed JSONB, ai_call_id, image_deleted_at, expires_at`. Index `(status, expires_at)`; the
hourly sweeper deletes S3 objects past `expires_at` (= created + 24 h) as the belt to the lifecycle's
braces.
**subscriptions** — D61: `user_id, plan CHECK (free|pro_monthly|pro_annual), provider, provider_sub_id
UNIQUE, provider_customer_id, status CHECK (pending|active|past_due|paused|cancelled), founding_price
BOOLEAN, current_period_start, current_period_end, cancel_at, paused_at, auto_pause_after DATE`.
Partial unique `(user_id) WHERE status IN ('pending','active','past_due')`.
**payments** — D61: `subscription_id, provider_payment_id UNIQUE, amount_paise BIGINT, currency
CHAR(3), status CHECK (captured|refunded|failed), refund_id, refunded_at, refund_deadline
TIMESTAMPTZ, raw JSONB`.
**billing_events** — D61 (webhook inbox, append-only): `provider_event_id UNIQUE, event_type,
payload JSONB, signature_ok BOOLEAN, processed_at, error`. The unique id is webhook idempotency.
**paywall_impressions** — D62: `user_id, trigger CHECK (doubt_limit|notebook_cap|srs_lock|weekly_report),
context_key VARCHAR(64), outcome CHECK (shown|dismissed|paid), snooze_until, UNIQUE (user_id,
trigger, context_key)`. "Each trigger fires once per context; Not now = 48 h" (PLAN D62 ✅).
**idempotency_keys** — D61 (used by every idempotent route from D7 on): `key VARCHAR(64), user_id,
route, request_hash CHAR(64), response_status SMALLINT, response_body JSONB, expires_at,
PRIMARY KEY (key, user_id)`.
**ai_calls** — D5 (append-only, the cost ledger)
```
id, user_id (nullable), feature VARCHAR(24) NOT NULL CHECK (doubt|doubt_route|doubt_verify|
  doubt_translate|doubt_extract|plan|mentor_message|classify|srs_variant|extract_document|embed|
  pipeline_extract|pipeline_solution|pipeline_verify|pipeline_distractor|pipeline_trap|eval|smoke),
model_id VARCHAR(120) NOT NULL, tier VARCHAR(8) NOT NULL CHECK (cheap|reason|vision|embed),
prompt_name VARCHAR(64), prompt_version SMALLINT,
input_tokens INTEGER, output_tokens INTEGER, cache_read_tokens INTEGER, cache_write_tokens INTEGER,
batch BOOLEAN NOT NULL DEFAULT false, latency_ms INTEGER,
status VARCHAR(16) NOT NULL CHECK (ok|error|timeout|breaker|invalid_output),
error_code VARCHAR(64), cost_paise BIGINT NOT NULL, request_id VARCHAR(64), created_at
```
Indexes `(feature, created_at)`, `(user_id, created_at)`. Written for every call including failures
and fake-client calls; `cost_paise` computed at insert from the price table (§4.8). Month
partitioning is PARKED until volume asks for it.
**ai_spend_daily** — D65: `ist_date, feature, calls INTEGER, cost_paise BIGINT, cache_hits INTEGER,
PRIMARY KEY (ist_date, feature)`; rebuilt by the nightly run.
**audit_queue** — D39: `kind CHECK (doubt_report|verification_failed|grounding_failed|pipeline_flag|
generated_sample|eval_failure), doubt_id, question_id, user_id, reason, payload JSONB, status CHECK
(open|resolved|dismissed), resolution JSONB, resolved_at`. Index `(status, created_at)`. Resolution
may set `doubt_cache.invalidated_at` or `questions.audit_status`.

### 2.9 Migration schedule

Flyway, `V<n>__<snake_name>.sql`, drafted by the db-migrator agent with a `-- ROLLBACK:` block and
`rollback/U<n>__*.sql` where the undo is non-trivial. Numbers are indicative; the agent takes the
next free integer.

| Version | PLAN day | Creates |
|---|---|---|
| V1 `extensions` | D4 | `CREATE EXTENSION IF NOT EXISTS vector, pg_trgm` |
| V2 `identity` | D4 | users, student_profiles |
| V3 `curriculum_core` | D4 | syllabus_nodes, syllabus_prerequisites, archetype_tracks, archetype_track_steps, cutoffs |
| V4 `chapter_status` | D4 | chapter_status |
| V5 `ai_calls` | D5 | ai_calls |
| V6 `auth` | D7 | otp_challenges, refresh_tokens, idempotency_keys |
| V7 `ncert` | D14 | ncert_books, ncert_paragraphs (embedding column nullable; HNSW index created D17 once rows exist) |
| V8 `questions` | D19 | questions, question_topics; `topic_traps`, `topic_trap_evidence` at D22; `question_anchors` at D23 |
| V9 `onboarding` | D25–D28 | parent_consents, batch_positions, document_extractions |
| V10 `plans` | D29 | daily_plans, plan_blocks |
| V11 `notifications` | D30 | user_devices, notification_log, wellbeing_signals (mood only) |
| V12 `practice` | D31–D33 | practice_sessions, practice_session_questions, practice_events |
| V13 `doubts` | D37–D44 | doubts, doubt_evidence, audit_queue (D39), doubt_cache (D41), doubt_daily_usage (D44) |
| V14 `notebook` | D49–D53 | error_entries, srs_reviews, notebook_patterns |
| V15 `planner_brain` | D55–D59 | mentor_messages, trajectory_snapshots, slump columns |
| V16 `billing` | D61–D62 | subscriptions, payments, billing_events, paywall_impressions |
| V17 `privacy` | D64–D65 | data_export_jobs, ai_spend_daily |

Seed data for local and test profiles (the D4 "test taxonomy": a two-subject, six-chapter tree with
prerequisites, one archetype track and three cutoff rows) lives in `db/seed/R__test_taxonomy.sql`, a
Flyway *repeatable* migration in a second location that only the `local` and `test` profiles add to
`spring.flyway.locations`. Production never sees it; real taxonomy arrives through the D13 loader.

Reversibility (D4 ✅ "migrations reversible"): a Testcontainers test applies every migration, runs
the collected rollback scripts in reverse, and asserts only `flyway_schema_history` remains.

### 2.10 Retention and deletion

| Data | Rule | Mechanism |
|---|---|---|
| Uploaded images (doubts, documents) | gone ≤ 24 h; doubts deleted right after extraction; documents right after confirm/discard | S3 delete in the service + `expires_at` sweeper + bucket lifecycle (three layers) |
| Account deletion | immediate logout and anonymisation; purge in 30 days | `users.status = deleted`, phone/name/dob/parent phone nulled, refresh tokens and devices deleted, `purge_after = today + 30`; nightly purge deletes doubts' raw text and images, mentor messages, document extractions, exports; aggregate rows (events, plans, ledger) stay under the tombstone id |
| Data export | notebook PDF + full JSON | job writes to `exports/` in the uploads bucket, 24-hour signed URL |
| OTP challenges | 24 h | nightly purge |
| Idempotency keys | 24 h | nightly purge |
| ai_calls | kept (cost history); user_id nulled on account purge | — |
| Question bank, NCERT text | kept; NCERT text is retrieval-only and is displayed at most one anchored paragraph at a time (SPEC §9.2 "explains and anchors, never republishes") | `GET /curriculum/ncert/{id}` returns one paragraph plus neighbours' addresses, not text |

## 3. API surface

### 3.1 Ground rules

- Base path `/api/v1`, JSON only (multipart for the two image uploads). Field names snake_case;
  timestamps ISO-8601 UTC (`2026-09-03T01:30:00Z`); dates `YYYY-MM-DD` and always IST calendar
  dates; money `{amount_paise, currency}`; ids UUID strings.
- Additive changes only within v1 (new optional fields, new endpoints). A breaking change is a v2
  path, which the MVP does not plan to need.
- Every response carries `X-Request-Id`. Clients send `X-App-Version` and `X-Client-Time`
  (for clock-skew diagnostics in OTP flows, PLAN D9).
- Copy returned to the client is codes plus text in both `en` and the user's language, never text
  alone; generated content (plans, answers) comes in the user's language with a `language` field.
- `correct_key` appears in exactly one response: the judged answer for that one question (§3.7).
  Question payloads before judging never contain it; a test enforces this (§8.4).

### 3.2 Authentication and tokens

- `POST /auth/otp/request` sends a 6-digit code through MSG91 (DLT template); the code is stored
  hashed with a pepper; 5-minute expiry; 5 attempts per challenge; 30-second resend cooldown.
- `POST /auth/otp/verify` returns `{access_token, refresh_token, expires_in, is_new_user, user}`.
  Access token: JWT HS256, 15 minutes, claims `sub` (user id), `role`, `lang`, `jti`. Refresh
  token: opaque 256-bit random, 30 days, stored as SHA-256 in `refresh_tokens`, one family per
  device; each refresh rotates the token and links `replaced_by_id`.
- Reuse of a rotated refresh token revokes the whole family and returns `AUTH_INVALID`; the app
  returns to login (DEV_SPEC §5, PLAN D10 "token rotation").
- `POST /auth/logout` revokes the family. Account deletion revokes every family.
- Admin endpoints (§3.7 ops) require `role = admin`; the founder's user row is flagged by hand.

### 3.3 Error envelope and codes

```json
{ "error": { "code": "DOUBT_LIMIT_REACHED",
             "message_en": "You've used today's 5 free solves.",
             "message_user_lang": "Aaj ke 5 free solves ho gaye.",
             "details": { "resets_at": "2026-09-04T00:00:00+05:30", "paywall_trigger": "doubt_limit" } } }
```

| HTTP | Codes |
|---|---|
| 400 | `VALIDATION_FAILED` (details: field → message), `IMAGE_UNREADABLE`, `IDEMPOTENCY_CONFLICT` (same key, different body) |
| 401 | `AUTH_REQUIRED`, `AUTH_EXPIRED` (refresh now), `AUTH_INVALID` (re-login), `OTP_INVALID`, `OTP_EXPIRED` |
| 403 | `FORBIDDEN`, `CONSENT_REQUIRED` (minor without parent consent, DEV_SPEC §8.4), `PRO_REQUIRED` (details: paywall_trigger) |
| 404 | `NOT_FOUND` |
| 409 | `STATE_CONFLICT` (e.g. answering a finished session, onboarding step out of order) |
| 422 | `DOUBT_LIMIT_REACHED` (details: resets_at), `DOUBT_UNVERIFIED` (the honest fallback, with the audit reference), `NOT_A_QUESTION` (photo has no question) |
| 429 | `RATE_LIMITED` (header `Retry-After`), `OTP_RATE_LIMITED` |
| 502/503 | `AI_UNAVAILABLE` (Bedrock failure after retries, DEV_SPEC §4.1 "couldn't solve this right now"), `AI_BUDGET_EXCEEDED` (breaker; details: degraded mode) |
| 500 | `INTERNAL` (request id in details; never a stack trace) |

Messages come from `messages_{en,hi,hinglish}.properties` in `common` (server-side copy); the
app's ARB files own client copy. Both sides use the same code strings.

### 3.4 Rate limits (per user unless noted; values are config, defaults shown)

| Scope | Limit |
|---|---|
| default, authenticated | 60 requests/min |
| `/auth/otp/request` | 3/hour per phone, 10/hour per IP |
| `/auth/otp/verify` | 5 attempts per challenge |
| `POST /doubts` | 10/min, plus the free-tier and fair-use rules in §4.4 |
| `POST /plan/negotiate` | 10/min |
| `POST /documents` | 6/hour |
| `POST /billing/webhook` | none (signature-verified, idempotent) |

Implemented as in-process token buckets (Bucket4j) keyed by user id, phone or IP. This assumes one
API instance, which is the beta topology; the second-instance path is a shared store (§13.3).

### 3.5 Idempotency

Routes marked **Idem** below require `Idempotency-Key` (client-generated UUID). The filter stores
`(key, user_id) → response` for 24 hours and replays it on a repeat; a repeat with a different body
hash returns `IDEMPOTENCY_CONFLICT`. Practice answers and block-status updates carry a
`client_event_id` in the body instead, because they arrive from the offline outbox in bulk (§5.6).
`POST /billing/webhook` is idempotent on Razorpay's event id (`billing_events`).

### 3.6 Long-running work

Nothing streams before D69. A doubt solve has a 25-second budget; if the pipeline is still
verifying or the request was queued by fair use, the response is `202` with `status: pending|queued`
and the client polls `GET /doubts/{id}` every 2 s with backoff (the same object, eventually
`answered` or `unverified_fallback`). Exports follow the same pattern. Streaming (SSE on the same
routes, flag `margai.flags.streaming`) is the D69 change and does not alter these shapes.

### 3.7 Endpoint catalog

Column **Day** is the PLAN day the endpoint ships. **Auth** is `user` unless noted.

**Auth (`auth`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `POST /auth/otp/request` | D7 | `{phone}` → `{challenge_id, resend_after_s}` | public; SMS provider sandbox until F1 |
| `POST /auth/otp/verify` | D7 | `{challenge_id, code}` → tokens + `user` + `is_new_user` | public; creates `users` + empty `student_profiles` on first login (D10) |
| `POST /auth/refresh` | D7 | `{refresh_token}` → tokens | public; rotation + reuse detection |
| `POST /auth/logout` | D10 | `{refresh_token}` → 204 | |

**Account (`account`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /me` | D10 | → `{user, profile, subscription, limits, consent_state}` | one call on app start |
| `PATCH /me` | D10 | `{language?, display_name?, morning_notification_time?, hours_weekday?, hours_weekend?, goal?, state_code?, category?}` → `me` | language switch regenerates future content only (DEV_SPEC §8.3) |
| `POST /me/devices` | D30 | `{fcm_token, platform, app_version}` → 204 | upsert |
| `DELETE /me/devices/{token}` | D30 | → 204 | on logout |
| `POST /me/consent/request` | D27 | `{parent_phone}` → `{challenge_id}` | minors only |
| `POST /me/consent/verify` | D27 | `{challenge_id, code}` → `{consent_state}` | unlocks `POST /documents` |
| `POST /me/export` | D64 | → `202 {job_id}` | notebook PDF + JSON |
| `GET /me/export/{job_id}` | D64 | → `{status, url?, expires_at?}` | 24-hour signed URL |
| `DELETE /me` **Idem** | D64 | `{confirmation: "DELETE"}` → 202 | anonymise now, purge in 30 days (§2.10) |

**Onboarding (`onboarding`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /onboarding/state` | D25 | → `{step, answers, next_prompt, options, syllabus_grid?}` | grid = nodes by subject with current `chapter_status` |
| `PUT /onboarding/answers/{step}` | D25 | `{answer}` → `state` | upsert per step, back/edit allowed (PLAN D25 ✅) |
| `PUT /onboarding/syllabus` | D26 | `{nodes: [{code, status, feels_weak}]}` → `state` | bulk, skippable |
| `POST /onboarding/complete` | D29 | → `{plan, target_line, diagnostic_offer}` | deterministic first plan, < 6 s, no AI call |

**Documents (`documents`)** — the SPEC §6.8 pattern

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `POST /documents` | D28 | multipart `{type, image}` → `{id, fields: [{name, value, confidence}], promise_copy}` | `CONSENT_REQUIRED` for minors; image ≤ 5 MB; types `neet_scorecard` D28, `board_marksheet` D29, `batch_timetable` unscheduled (§12.2) |
| `POST /documents/{id}/confirm` | D28 | `{fields}` → `{applied_to}` | deletes the image, writes confirmed fields |
| `POST /documents/{id}/discard` | D28 | → 204 | deletes the image |

**Plan (`planner`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /plan/today` | D29 | → `{date, mode, mentor_note, blocks[], yesterday_unfinished[], streak, trajectory_card?, mood_prompt, exam_countdown}` | builds the fallback plan if none exists (DEV_SPEC §8.1) |
| `GET /plan/week` | D58 | → `{days: [{date, blocks_summary, status}]}` | |
| `POST /plan/blocks/{block_id}/status` | D33 | `{status, client_event_id, at}` → `{block, streak}` | outbox-safe |
| `POST /plan/negotiate` | D58 | `{text}` → `{mentor_reply, plan?, trade_off}` | revised plan appears on Today immediately (SPEC §6.1) |
| `GET /plan/messages` | D58 | `?cursor` → page of `mentor_messages` | |
| `POST /plan/batch-position` | D26 | `{node_code, status}` → 204 | self-report layer; weekly-confirm card unscheduled (§12.2) |

**Practice (`practice`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `POST /practice/sessions` | D31 | `{block_id}` or `{kind: "diagnostic"}` → `{session_id, questions: [{id, stem, options, time_limit_s, anchor_hint}], total}` | **no `correct_key`**; band + NEET-relevance filter |
| `POST /practice/sessions/{id}/answers` | D31 | `{question_id, chosen_key?, time_taken_ms, client_event_id}` → `{is_correct, correct_key, solution_md, anchor: {paragraph_id, display}, notebook_entry_id?}` | server judges; the only place `correct_key` is returned |
| `POST /practice/sessions/{id}/finish` | D33 | → `{accuracy, avg_time_ms, norm_delta, sent_to_notebook: [...]}` | |
| `GET /practice/sessions/{id}` | D33 | → session + summary | |
| `GET /practice/offline-pack` | D34 | → today's practice blocks' questions (+ judging data per the §0.4 #4 decision) | cached by drift |
| `POST /practice/diagnostic` | D35 | → session (30 questions, adaptive) | ability estimates update `chapter_status` |

**Doubts (`doubts`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `POST /doubts` **Idem** | D37 (photo D38) | `{text}` or multipart `{image}` → `200 doubt` or `202 {id, status}` | `doubt` = `{id, status, question_text, answer: {steps_md, concept_md, anchor: {paragraph_id, display}, nta_trap_md?, followups[], verified}, language, remaining_today: {fresh_left, weight_used}}` |
| `GET /doubts/{id}` | D37 | → `doubt` | polling target |
| `POST /doubts/{id}/followup` | D46 | `{text}` or `{chip_index}` → `doubt` (child) | keeps parent context |
| `POST /doubts/{id}/report` | D40 | `{note}` → 204 | writes `audit_queue` |
| `GET /doubts` | D46 | `?cursor` → page | history |
| `GET /doubts/usage` | D44 | → `{fresh_left, cached_weight, resets_at, plan}` | meter |

**Notebook (`notebook`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /notebook/summary` | D50 | → `{by_subject, by_cause, patterns_line?, free_cap: {limit: 30, shown, hidden}}` | |
| `GET /notebook/entries` | D50 | `?cursor&subject&cause&state=open|healed` → page of `{question, your_key, correct_key, cause, confidence, srs_stage, next_review}` | correct key is fine here: the question was answered |
| `POST /notebook/entries/{id}/cause` | D49 | `{cause}` → entry | student correction always wins |
| `GET /notebook/danger-zones` | D52 | → open errors ordered by node weightage | |
| `GET /notebook/healed` | D52 | `?cursor` → page | |

**Wellbeing, trajectory (`wellbeing`, `trajectory`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `POST /signals/mood` | D30 | `{mood, ist_date}` → 204 | outbox-safe, upsert |
| `GET /trajectory/weekly` | D58 | → `{band, target, cutoff_line, insight, peer_line?, confidence, deep_report?}` | `deep_report` null for free with a teaser flag |

**Billing (`billing`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /billing/status` | D61 | → `{plan, status, period_end, founding, can_refund_until?}` | |
| `POST /billing/subscribe` **Idem** | D61 | `{plan}` → Razorpay checkout params | mandate for monthly, order for annual |
| `POST /billing/webhook` | D61 | raw body + `X-Razorpay-Signature` → 200 | public; HMAC verified; idempotent on event id |
| `POST /billing/cancel` **Idem** | D63 | → `{status, refund?}` | one call: cancel + automatic refund within 7 days of a charge |
| `GET /billing/paywall` | D62 | `?trigger` → `{offer, show: bool, snooze_until?}` | once per context; Not now = 48 h |
| `POST /billing/paywall/dismiss` | D62 | `{trigger, context_key}` → 204 | |

**Curriculum (`curriculum`)**

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /curriculum/syllabus` | D13 | → tree `{subjects: [{code, name, chapters: [{code, name, topics}]}]}` | ETag; cached by the app |
| `GET /curriculum/ncert/{paragraph_id}` | D40 | → `{display, text (user language), book, chapter, section, neighbours: [addresses]}` | one paragraph at a time (§2.10) |

**Ops (`ops`)** — `role = admin`

| Method, path | Day | Request → response | Notes |
|---|---|---|---|
| `GET /admin/audit-queue` | D75 | `?status&cursor` → page | |
| `POST /admin/audit-queue/{id}/resolve` | D75 | `{action, note}` → item | may invalidate cache rows |
| `GET /admin/users/{id}/peek` | D75 | → read-only state summary | founder peek |
| `GET /admin/costs` | D65 | `?from&to` → `ai_spend_daily` rows | |

Unauthenticated: `/actuator/health` (liveness for the ALB; no details).

### 3.8 Language

The principal's `lang` claim decides `message_user_lang` and the language of generated content;
`Accept-Language` is honoured only on the public auth routes. Changing `language` via `PATCH /me`
re-issues the access token on next refresh and affects new content only (DEV_SPEC §8.3). The three
values `en | hi | hinglish` are the same strings on the server, in the JWT and in the app's locale
mapping (§5.5).

### 3.9 Pagination

`?cursor=<opaque>&limit=<1..50>` → `{items, next_cursor}`; the cursor encodes `(created_at, id)` of
the last item, base64url. No offsets.

### 3.10 Coverage check

| SPEC §8 screen | Reads | Writes |
|---|---|---|
| 1 Login | — | otp request/verify, refresh |
| 2 Interview | onboarding/state, curriculum/syllabus | onboarding/answers, onboarding/syllabus, me (language) |
| 3 Documents | — | documents, confirm, discard |
| 4 Parent consent | me (consent_state) | me/consent/* |
| 5 First-plan reveal | — | onboarding/complete |
| 6 Diagnostic | practice/sessions/{id} | practice/diagnostic, answers, finish |
| 7 Today | plan/today, trajectory/weekly | plan/blocks/{id}/status, signals/mood, plan/negotiate |
| 8 Practice | practice/sessions/{id}, offline-pack | practice/sessions, answers, finish |
| 9 Doubts | doubts, doubts/{id}, doubts/usage, curriculum/ncert/{id} | doubts, followup, report |
| 10 Notebook | notebook/summary, entries, danger-zones, healed | entries/{id}/cause |
| 11 Weekly report | trajectory/weekly | — |
| 12 Paywall · subscription | billing/status, billing/paywall | billing/subscribe, cancel, paywall/dismiss |
| 13 Profile & settings | me, billing/status | me, me/export, me (DELETE), me/devices, auth/logout |
| 14 Exam-mode Today | plan/today (`mode`) | same as 7 |
| 15 Result flows | Phase 2 | — |

## 4. AI pipeline

_Written in task 5._

## 5. Flutter app

_Written in task 6._

## 6. Content pipeline

_Written in task 6._

## 7. Infrastructure (AWS ap-south-1)

Infra code is founder-run: `infra/` is a human-only path (`scripts/block-paths.sh`, DEV_SPEC §13.3)
and `aws *` is denied to Claude. This section is the specification the founder builds from
(Terraform, one small stack), in the order §7.6 gives. Claude may draft Terraform in a separate,
explicitly permitted session; it never applies it.

### 7.1 Environments

| Name | Where | Data | AI | Purpose |
|---|---|---|---|---|
| `local` | developer laptop, docker compose | compose Postgres, seed migrations | `FakeAiClient`; `BEDROCK_LIVE=1` opt-in with the breaker on | every PLAN day's build loop |
| `beta` | AWS ap-south-1, one account | RDS | Bedrock via global inference profiles | the 50-student closed beta and everything from D57 on |

No staging until public launch (PARKED). Local is the pre-production environment; the beta stack is
rebuilt from Terraform if it drifts.

### 7.2 Components

| Component | Choice | Notes |
|---|---|---|
| Network | One VPC, two AZs; public subnets for the ALB and the Fargate tasks; private subnets for RDS | Tasks in public subnets with a security group that only accepts the ALB avoid a NAT gateway (the classic ~₹3k/month surprise). S3 gateway endpoint is free and added. |
| Ingress | ALB, ACM certificate, Route 53 record on the final domain (TRACKER F7) | HTTP → HTTPS redirect; health check `/actuator/health` |
| Compute | ECS Fargate service, 1 task, 1 vCPU / 2 GB, ARM64 (Graviton) | Java 25 with `-XX:MaxRAMPercentage=70`. Rolling deploy with min 100% / max 200% |
| Nightly | ECS RunTask from EventBridge Scheduler, same task definition with the `nightly,bedrock` profiles, 2 vCPU / 4 GB | 60-minute timeout; failure alarm (§10.4) |
| Registry | ECR, one repository, images tagged with the git SHA | lifecycle: keep last 20 |
| Database | RDS PostgreSQL 18, `db.t4g.small`, single-AZ, 20 GB gp3, automated backups 7 days, deletion protection | `pgvector` and `pg_trgm` created by migration V1. PG18 availability on RDS and its pgvector version are a console check (§13.2) |
| Object storage | `margai-beta-uploads`: SSE-S3, block public access, lifecycle expires objects after 1 day, prefixes `uploads/doubts/`, `uploads/documents/`, `exports/`. `margai-beta-content`: source PDFs, page images, JSONL artefacts, weekly logical dumps; versioning on | The hard rule "uploaded images: uploads bucket only" (CLAUDE.md) maps to the first bucket |
| AI | Bedrock model access enabled for the CHEAP, REASON, VISION and EMBED models named in config; global cross-region inference profiles called from ap-south-1 | Data may be processed outside India: disclosed in the privacy copy (SPEC §6.11) |
| Config and secrets | SSM Parameter Store under `/margai/beta/…`; SecureString for secrets | injected into the task as environment variables through the task definition's `secrets` (`valueFrom` SSM ARN). No library, no runtime fetch; a config change is a task restart (~2 min) |
| Scheduling | EventBridge Scheduler: nightly 19:00 UTC; weekly dump Sunday 21:00 UTC | both target ECS RunTask |
| Logs and metrics | CloudWatch Logs (JSON), 30-day retention; CloudWatch metrics from Micrometer; AWS Budgets for the Bedrock daily spend alarm | §10 |
| Push, SMS, payments, analytics | FCM (Firebase project), MSG91 (DLT template via TRACKER F1), Razorpay (KYC via F1), PostHog Cloud | credentials in SSM only |

### 7.3 Configuration and secrets layout

```
/margai/beta/db/url                    String       jdbc:postgresql://…/margai
/margai/beta/db/username               String
/margai/beta/db/password               SecureString
/margai/beta/jwt/secret                SecureString 256-bit, base64
/margai/beta/jwt/secret_previous       SecureString rotation window (§9.2)
/margai/beta/otp/pepper                SecureString
/margai/beta/msg91/auth_key            SecureString
/margai/beta/msg91/template_id         String
/margai/beta/razorpay/key_id           String
/margai/beta/razorpay/key_secret       SecureString
/margai/beta/razorpay/webhook_secret   SecureString
/margai/beta/fcm/service_account_json  SecureString
/margai/beta/posthog/api_key           SecureString
/margai/beta/ai/tier/cheap             String       model id, pinned version
/margai/beta/ai/tier/reason            String
/margai/beta/ai/tier/vision            String
/margai/beta/ai/embed/model            String
/margai/beta/ai/prices_json            String       {model_id: {input, output, cache_read, cache_write} per Mtok, USD}
/margai/beta/ai/usd_inr                String
/margai/beta/ai/budget/user_daily_paise String
/margai/beta/ai/budget/global_daily_paise String
/margai/beta/ai/batch_min_records      String       0 disables batch mode
/margai/beta/limits/…                  String       rate limits, free-tier counts (§3.4, §4.4)
/margai/beta/flags/…                   String       feature flags (§11.6)
/margai/beta/exam/date                 String       NEET date for the season (DEV_SPEC §8.6)
```

Every parameter maps to one `@ConfigurationProperties` field under the `margai.*` prefix (§11.5);
`application.yml` carries the local defaults for non-secret values and nothing for secrets. Model
IDs, prices, limits, flags and prompt versions never appear as code constants (`.claude/rules`).

### 7.4 Identity and access

- Task role: `bedrock:InvokeModel`, `bedrock:InvokeModelWithResponseStream`, `bedrock:CreateModelInvocationJob`
  and read on the batch job, scoped to the configured model ARNs; `s3:GetObject/PutObject/DeleteObject`
  on the two buckets; `logs:*` on its log group; `cloudwatch:PutMetricData`. Nothing else.
- Execution role: ECR pull, SSM `GetParameters` on `/margai/beta/*`, KMS decrypt for SecureStrings.
- GitHub Actions deploy role via OIDC (no long-lived keys): ECR push and `ecs:UpdateService` only.
  Deploy is a `workflow_dispatch` job the founder triggers after merging; automatic deploy on `main`
  is a D72 decision.
- Founder's laptop: AWS SSO profile for `pipeline`, `eval` and the D5 smoke test. No static keys
  anywhere, including `.env` files (CLAUDE.md).

### 7.5 Backups and recovery

- RDS automated snapshots, 7 days, plus a weekly `pg_dump` (custom format) from a scheduled ECS
  task into `margai-beta-content/dumps/`, 8 weeks retained.
- Restore drill at D70: new RDS from snapshot, point a test task at it, run the migration check and
  a read-only smoke; documented as a runbook in `docs/runbooks/`.
- S3 content bucket versioned; uploads bucket is ephemeral by design (nothing to back up).
- Recovery objectives for beta: RPO 24 h (nightly snapshot), RTO 2 h (Terraform re-apply + restore).

### 7.6 Infrastructure timeline (proposed founder workstream F8)

| By PLAN day | Needed for | Build |
|---|---|---|
| D5 | one live Bedrock smoke call | AWS account, Bedrock model access, SSO profile on the laptop |
| D14 | NCERT PDFs in S3 | content bucket |
| D28 | scorecard upload with 24-hour deletion | uploads bucket with the 1-day lifecycle, IAM for local dev |
| D55 | nightly loop, D57 two-device morning plans, D60 three unattended days | the full beta stack: VPC, RDS, ECR, ECS service + scheduled task, ALB + certificate, SSM parameters, log group |
| D64 | export and deletion promises | exports prefix, purge job scheduled |
| D70 | failure drills | backups, alarms, restore runbook |
| D73 | dashboards | CloudWatch dashboard, PostHog project, AWS Budgets alarm |
| D74 | Play Store internal track | release signing key (human-held), `--dart-define` production API URL |

### 7.7 Beta cost estimate (monthly, order of magnitude)

| Item | Estimate |
|---|---|
| Fargate 1 vCPU / 2 GB ARM, 24×7 | ≈ $30 |
| ALB | ≈ $18 |
| RDS `db.t4g.small` + 20 GB | ≈ $28 |
| Nightly task, dumps, S3, CloudWatch, ECR | ≈ $8 |
| Bedrock, 50 active students | bounded by the breaker (50 × ₹25/day ≈ ₹37,500 worst case); expected ₹5k–10k with a 55% cache rate |
| MSG91 OTP, Razorpay fees, PostHog free tier, FCM | usage-based, small |

Roughly ₹7,500 of fixed infrastructure per month plus AI spend that the breaker caps. Figures are
list prices at the time of writing and are re-estimated at D65 with real ledger data.

## 8. Testing strategy

_Written in task 7._

## 9. Security and privacy

_Written in task 7._

## 10. Observability and cost operations

_Written in task 7._

## 11. Cross-cutting conventions

_Written in task 7._

## 12. PLAN mapping and gaps

_Written in task 7._

## 13. Risks and open questions for the founder

_Written in task 7._

## 14. Decisions to record in DECISIONS.md on approval

_Collected as sections land; finalised in task 8._
