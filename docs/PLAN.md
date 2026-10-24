# MARG AI — Implementation Plan (4 hours/day)

**Pace:** 4 focused hours/day, 6 days/week (Sunday off or overflow) → ~24 hrs/week.
**Builder:** you + Claude Code (you drive, review, and decide; Claude Code plans and writes).
**Total:** 14 weeks (≈84 working days) from empty repo to a 50-student closed beta,
plus 6 weeks of beta operation. Timeline assumes the Product Spec v2.0 is the contract.
*Amended 2026-10-19 (founder's calendar ruling, option (c); DECISIONS 2026-10-19):* plus **4** weeks of beta
operation, waves from D79 (≈2026-12-30), public launch ≈2027-02-02; a closed test of 12–15 adult testers from
≈D65 (the founder's), placed on D66 (≈2026-12-15) by Claude for the founder to overrule.

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
- **Floating — phone OTP live** *(founder decision 2026-10-01; placed at D48 under his rule of 2026-10-24 —
  the day Claude's application of it, for him to overrule)*:
  phone OTP by SMS is the only sign-in method (SPEC §5.7). The MSG91 adapter on the two DLT
  templates (login, parent consent), number entry with OTP auto-read, and email OTP moved behind
  its flag land in the first buffer after F1's DLT templates are approved *(superseded 2026-10-24 by the
  placement below)* — and **D60 is the
  decision point**: if the templates are not live by then, the beta runs on the email fallback
  flag (which needs F10's SES production access). The same item builds the phone attach for an
  account begun on the email fallback (`POST /me/phone/otp/request|verify`, TECH_PLAN §15.1) and
  gives D27's consent flow its live SMS proof. ✅ *A real phone signs in by SMS first try; the
  email path only with its flag on; an email-begun account attaches a phone and stays one user,
  and a number already in use is refused, never merged (CS-2 §10, SPEC §5.7).*
  *Amended 2026-10-24 (the founder; DECISIONS 2026-10-24): F1's DLT registration is done, and the founder's own
  curl test checks DLT delivery as soon as Jio approves the dev-key template, so what is left is ordinary code. It
  lands in the first buffer after D27 that is not already committed, provided that is before D60 — no slip for
  it, and a day before D27 (D26a) only as the fallback, if no such buffer existed. D27 builds the consent flow with
  SMS faked, as planned, and the consent flow is proven live on that buffer day. The work runs on MSG91's Flow
  API, the server still generating, storing and expiring the code; the app uses smart_auth's SMS Retriever with
  the SMS User Consent fallback and logs the app hash in debug builds; auto-read is never tested on Internal app
  sharing builds. One DLT template per signing key: the dev-key template now; the Play-key template from the
  app-signing certificate of one internal-testing-track upload made as soon as the Play account exists (F14) —
  Google generates that key — its text to the founder the same day; until Jio approves it, closed testers sign in
  through the SMS User Consent fallback or by typing the code, so it does not block D66.* *Claude's application,
  for the founder to overrule: that buffer is **D48** — D36 carries the NCERT table pass and D42 the 30-answer
  audit, the compression retro-fit and any D38 overflow; nothing is committed to D48 itself. With D36 and D48 both
  taken, D72 is the only plain buffer left before D74 for the items allowed to slip to "a later buffer" (CS-1's
  collective review and load, the primers beyond the top 50, the errata backfill). D48's consent proof also needs
  the parent-consent DLT template, still being drafted, approved by then; if it is not, the check moves with the
  proof to the next buffer after its approval that is not already committed, D60 at the latest, and D48 ticks on
  the rest (for him to rule).* *Answered 2026-10-24 (the founder; DECISIONS): the upload key is made on the
  Play-account day, before the internal-testing upload, the keystore and its passwords outside the repo, backed up
  in two places (F20), read from an untracked key.properties; and the upload does not wait for F21 — the
  applicationId becomes `com.hauslalearning.app` now, F21 keeping only the display name.* *(Claude: Play refuses a
  debug-signed bundle, hence the key that day; that first upload fixes the applicationId; the change is 3a5e680.)*

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
  *Amended 2026-10-18 (founder; DECISIONS 2026-10-18): bio12 is D16's last Hindi book, in a 24-hour
  timebox; the Hindi editions of the four Chemistry books move to the beta period (§4); D16 ticks when
  bio12 closes.*
- **D17 —** Embeddings + hybrid retrieval over the whole corpus, on the pin settled at D15
  (amended 2026-09-19); the test harness widened to it. ✅ *15 hand-written concept queries
  return the right paragraphs.* *Amended 2026-10-18 (founder; DECISIONS 2026-10-18): at least one
  of the ≥5 Hindi queries (TECH_PLAN §6.4) is in each subject, Chemistry included.* *Amended 2026-10-19
  (founder; DECISIONS 2026-10-19): ticks if the query-rewrite test in D18's buffer meets the 2026-10-18 bar
  with fusion on (the question and its English search version fused, never the rewrite alone) and the four
  held-out queries hit at least 3 of 4.* *Amended 2026-10-19 evening (founder's rulings A and B; DECISIONS
  2026-10-19): a held-out "hit" is "in the answer set"; grounding is the higher similarity of the question and
  its search version, with `grounded_via` logged for every retrieval; ticks if a rerun at temperature 0, the run
  of record, meets the bar under A and B and the hand-check of every query grounded only through its search
  version passes.*
- **D18 —** Buffer for extraction mess (there will be some). **Week-3 gate:** NCERT
  layer searchable in both languages. *Added 2026-10-01 (CS-2 §4.11): Bedrock configured as a
  completion fallback behind a flag — config only; embeddings stay on Bedrock as their primary.*
  *Amended 2026-10-19 (founder; DECISIONS 2026-10-19): carries the standalone query-rewrite test, fused and
  unfused, plus the English-rewrite diagnostic; the gate reads the fused run with the unfused numbers beside it;
  if the rewrite work spills, the gate moves and the move goes in the slippage log.*
  *Amended 2026-10-18 (founder; DECISIONS 2026-10-18): the gate reads “searchable in both languages
  for the six books with Hindi text; for Chemistry, Hindi queries reach the English rows”.*
  *Amended 2026-10-20 (founder's rulings on the D18 plan and the same day's change of plan; DECISIONS
  2026-10-20): T0 comes first — the primary tiers move to the 5.5 models (CHEAP `claude-haiku-5-5`,
  REASON `claude-sonnet-5-5`), smoked with one call per tier on the direct API and judged against the
  current model on the same sets: the D17 acceptance and held-out sets rerun fused with Haiku 5.5 writing
  the search versions, on the same bar, that run becoming the gate's run of record (the whole query file
  runs, so the baseline and diagnostic sets stay in it — Claude's reading, DECISIONS 2026-10-20), and every
  prompt in use rerun on its eval set, each judged against its score of record (D17's run of record for the
  query rewrite; Claude's reading, DECISIONS 2026-10-20); a regression or a failed bar stops for the founder's choice, no
  prompt edited to pass. The gate is then read book by book: each book-level gap in D17's run of record gets
  one fresh probe query (six: phy12-part1 Hindi, phy11-part2 English, chem11-part2 English, and
  Devanagari Hindi reaching the English rows for chem11-part2, chem12-part1 and chem12-part2), written
  with its answer key from the book rows and committed before the run, in the held-out set, never used
  for tuning; a probe miss is diagnosed and stops for the founder's ruling. The Bedrock fallback is no
  longer config only: the Bedrock client is brought up to per-tier request settings; its ids mirror the
  new tiers (`global.anthropic.claude-haiku-5-5`, `global.anthropic.claude-sonnet-5-5`, an `in.*`
  profile if one appears), with prices including cache rates in a DECISIONS row, a startup warning when
  a tier's primary model changes but its fallback id does not, and sequential batch under the fallback;
  it is ready after the founder's smoke of one call per runtime tier (CHEAP and REASON). The
  closed-book errata backfill waits for a later buffer.*
  *Amended 2026-10-20 (founder's ruling "tick D18 with T1 carried"; the wording Claude's; DECISIONS
  2026-10-20): D18 ticks with T1 carried — the founder's smoke was refused by Bedrock for this account
  (the agreement for Claude Haiku 5.5 and Haiku 4.5 reads NOT_AVAILABLE; the cause is with AWS), so the
  fallback's readiness waits on the founder's AWS Support case and the same smoke reruns when AWS lifts
  the refusal.*
- **D19 —** PYQ ingest: papers loaded, question records created, tagging pass.
  ✅ *Counts per year/subject match official papers.* *Amended 2026-10-20 (the founder's rulings on the D19 plan,
  `neet-pyq-sources/README.md` §1; DECISIONS 2026-10-20): D19 ticks when its tasks T1–T8 are done and 2025 code 45
  is loaded with every section count matching; every other paper of the founder's pack (F18) loads as its own
  evidence line with the same check — loading these papers is D19's scope, not overflow, and anything that spills
  past D22 goes to the slippage log; only official booklets in paper order and NTA's final keys are used, never a
  compilation or a publisher's key.*
- **D20 —** AI solution generation (batch) for one subject; verification pass wired.
  ✅ *50-question audit sample: you personally check; note error rate.* *Amended 2026-10-20 (the founder's D19
  ruling 5; DECISIONS 2026-10-20): every disagreement between the AI solution and NTA's key first triggers a check of
  that question's transcription against the page image, before anything else is judged, and the transcription
  errors found this way are logged per paper. For a key mirrored by a third party (README §2, tier 3), a cluster of
  disagreements on one paper triggers a second-copy check of the key (README §2).* *And (the founder, 2026-10-20;
  DECISIONS): that check reads the page image the extraction call read, `pages/pyq/{paper}/{page}.png`, its sha256
  checked against the page's line in `extract/pyq/{paper}.jsonl` (for 2025 code 45, a re-render marked as one —
  not provably the bytes its calls read; Claude's row, DECISIONS 2026-10-20).* *And (the founder, 2026-10-21;
  DECISIONS): the coaching file with solutions (`…Aakash-CROSSCHECK-ONLY.pdf`) is kept out of D20 and D21 entirely, and
  neither it nor the PW code-45 retype is ever shown to a model that generates content.* *And (the founder,
  2026-10-21; DECISIONS): a character a review listed as "cause unsettled" that touches the keyed answer, a number, a
  unit, a sign or a formula goes to the founder when the Hindi column, the second official copy and this
  solve-vs-key check together still cannot settle it.* *And (the founder, 2026-10-21; DECISIONS): a question whose
  topic NCERT has cut but the NMC syllabus still lists is marked "no NCERT anchor", so its solution never cites an
  NCERT page that doesn't exist. (Claude's, same DECISIONS, for the founder to overrule: the mark is a tag ruling's
  `anchor: none` in `pipeline/inputs/pyq-tag-rulings.yaml`; 2025 code 45's Q95 and Q133 are solved once the D24 buffer
  has brought their pages into the corpus. Surfaced with it: CLAUDE.md hard rule 2 and DEV_SPEC R2 allow no AI
  solution without NCERT retrieval grounding, so until the founder rules, D20 generates no solution for a question
  marked "no NCERT anchor" and lists it for him.)* *Decided 2026-10-22 (the founder; DECISIONS): D20 and D21 write no
  AI solution for a question with `anchor: none` — hard rule 2 stands.* *Added 2026-10-23 (the founder; DECISIONS):
  D20 also skips a question marked `anchor: pending` ("not yet in the corpus": its topic printed in the current book
  but not yet held in the app — a table, a figure, or pages not yet ingested); for a ruled anchor — a paragraph
  address in the rulings file, in either subject's book (2024 S6 Q1 and Q55) — D20's solver puts that paragraph into
  the retrieved set; 2024 S6 Q10 grounds on phy11-part1 §6.9 ¶12, and 2025 code 45's Q17 on §6.12.1 with §6.9 ¶12
  (the k ∝ R scaling of a uniformly expanding sphere is reasoning from taught principles), solved and verified as
  normal.*
  *(Claude's, same DECISIONS: the ruled-anchor change is a retrieval change, so it passes the eval gate — `eval/run.sh`
  and its prompt-changelog line.)* *Added 2026-10-24 (the founder; DECISIONS): the solver records the grounding kind of
  every solution it writes — stated, derived, elimination or plain word — as the pre-D20 batch's rulings record it in
  their notes, so a solution reached only by elimination is known to verification and to the doubt explanations.*
- **D21 —** Solutions for remaining subjects (batch overnight); distractor-map
  generation. ✅ *Second 50-question audit; error rate < agreed threshold.*
- **D22 —** Weightage & difficulty statistics computed → syllabus nodes updated; the
  `collective_records` migration and `collective from-pyq` momentum per node (CS-1 §2, §4;
  TECH_PLAN §2.3, §6.3 — added 2026-09-12).
  ✅ *Top-10 weightage chapters match known NEET wisdom (sanity check).* *Amended 2026-10-20 (the founder's D19
  rulings, README §1): D22 runs on the papers loaded by then and reruns when all are in, its report naming the papers
  used (the tick rule); weightage considers recency — the 2024 NMC syllabus revision makes older papers less
  representative — and Claude proposes the weighting here (ruling 3).* *Added 2026-10-01
  (CS-4 §3; at risk): the `concept_primers` migration and `primers generate` begin for the top-50
  weightage chapters (TECH_PLAN §15.7), staged on one chapter first.* *Added 2026-10-21 (the founder; DECISIONS): a
  question whose tag is marked `review` (below the tag floor, 0.5) counts toward no topic's weightage until he rules.*
  *And (the founder, 2026-10-22; DECISIONS): a question marked "no NCERT anchor" does count toward its topic's
  weightage — NTA asked it.* *And (the founder, 2026-10-22; DECISIONS): a multi-topic question shares its weightage
  credit across its primary and secondary tags, not the primary alone. (Proposed by Claude, same DECISIONS, for the
  founder to rule: one unit a question — 1.0 to a lone primary, 0.7/0.3 with one secondary, 0.6/0.2/0.2 with two; a
  chapter-coded tag credits the chapter.)*
- **D23 —** Anchor-linking questions↔NCERT; seed the eval suite v1 (~60 questions
  across subjects). ✅ *Eval harness runs and reports.* *Amended 2026-10-18 (founder; DECISIONS
  2026-10-18): the eval suite carries Hindi questions in every subject.* *Amended 2026-10-19 (founder's
  rulings A–D on the D17 rewrite run; DECISIONS 2026-10-19): the eval also (a) checks answer correctness
  where the key chunk ranks low in the answer set (a01 at rank 12, h01 at 6 are the examples), rank and top-3
  staying diagnostics, not gates; (b) carries at least one equation-heavy Hindi question per subject, and
  revisits the Hindi vector column on what it shows (h03 is the known miss); (c) compares fused and unfused
  retrieval on English questions, and if fusion lowers any English result the English search-version flag
  goes off; (d) evaluates `query_rewrite` v2's drift guard (no topic the question does not contain) on this
  eval set, not on D17's queries.* *Amended 2026-10-20 (founder's T0 ruling; DECISIONS 2026-10-20): the
  eval also (e) measures drift in the search version — its length, the rate of added topics, and book
  names in it (Haiku 5.5 drifts more than 4.5: a mean of 21.4 words against 12.6); (f) says how it handles
  run-to-run variance, the 5.5 models running only at temperature 1 — the founder's default is two runs per
  set, judged on the worse run, both reported; Claude's proposal, for the founder's approval at D23's plan:
  every eval set runs twice on the same build, each bar line and each regression check (a query leaving
  the answer set, a failing bar line — the clarified rule) is judged on the worse of the two runs, line by
  line — which differs from judging the whole worse run: a line fails if it fails on either run — both runs'
  reports are committed, a line that holds on one run and fails on the other is named unstable in the
  report, and no third run breaks a tie; (g) reviews the shared `_protocol` fragments against 5.5 behaviour
  once eval fixtures exist, any change judged on this set. Every new prompt from here on is written for
  the 5.5 models.*
- **D24 —** Buffer + **Week-4 gate:** solved, tagged, anchored PYQ bank + eval suite
  running in CI. *Amended 2026-10-20 (the founder's D19 tick rule, README §1; DECISIONS 2026-10-20): the gate
  needs every sourced paper loaded and any missing paper logged (F18).* In the buffer (CS-1 §4, §7, §9.3; added 2026-09-12): `collective from-pyq`
  misconception drafts from the distractor maps; `collective from-inputs` over the F11
  excerpt files if they exist; `collective review` (the founder-review sheet) and
  `collective load` of the founder-edited sheet — the last three with explicit permission
  to slip to any later buffer (CS-1 §8, §9.3), so the Week-4 gate never waits on a founder
  review. ✅ *travels with `collective load`, here or in the buffer it slips to: approved
  collective records exist for the top-50 weightage nodes at or above the confidence
  threshold; the review sheet is founder-signed; every record carries `season_version`.*
  *Added 2026-10-01 (CS-4 §3; at risk): primers for the top-50 chapters finish here and `primers sample`
  writes the founder's 30-primer sheet; the remaining topics go to a later buffer before D74.*
  *Added 2026-10-21 (the founder; DECISIONS), under the slippage rule (content-pipeline work in buffers only): the
  Biology unit-opening essays and biography pages come into the corpus, English and Hindi (~₹250) — 2025 code 45's
  Q95 and Q133 are answered from them. Proposed with it (Claude, for the founder to rule; DECISIONS 2026-10-21): table
  coverage — about 145 NCERT tables, none of them in the corpus today, at ≈₹1,300.* *Approved 2026-10-22 (the
  founder; DECISIONS): table coverage, both editions, ~₹1,300 plus the eval gate and a retrieval rerun; tables stored
  so retrieval can hit individual rows; Biology first, then Chemistry, then Physics; what does not fit D24 beside the
  Biology unit openers moves to the next buffer under the slippage rule.* *The split (Claude's estimate, same
  DECISIONS, for the founder to overrule): D24 builds the table pass and runs it on Biology, both editions (18
  tables); Chemistry and Physics move to D36 — logged there and in TRACKER's slippage log — and if Biology's tables
  do not fit D24 either, they move with them.* *Added 2026-10-23 (the founder; DECISIONS): the table pass also reads
  the printed text of Figs 6.9–6.10 (bio12 ch 6, the geological eras), 5.16 and 13.8 (bio11 ch 5 and 13), here with
  Biology's tables (Claude's placement), lifting the `pending` mark of the questions that need them (2024 S6 Q193).*
  *And (the founder, 2026-10-23; DECISIONS): Biology's Table 4.1 lifts 2024 S6 Q189's `pending` mark, and the unit
  openers lift 2025 code 45's Q95 and Q133's.* *Added 2026-10-24 (the founder; DECISIONS): the figure reading also
  covers chem11-part1 Fig. 3.2 (the periodic table; 2023 G6 Q51) and the labels of phy12-part2 Fig. 10.12(b) (G6 Q24),
  with D36's Chemistry and Physics tables (Claude's placement).*

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
  with the floating phone-OTP item, where the MSG91 adapter lands (D48 since 2026-10-24, Claude's application of
  the founder's rule). ✅ adds: with the beta flag on,
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
  question is answered; judging server-side (TECH_PLAN §0.4 #4, decided at D3).* *Amended 2026-10-20 (the
  founder's D19 ruling 4; DECISIONS 2026-10-20): the PYQ figures recorded at D19 are cropped and checked in a
  buffer before D31, and no figure question is served without its crop.* *And (Claude's, DECISIONS 2026-10-20, for
  the founder to overrule): `accepted_keys` and `answer_status` are key carriers like `correct_key`, a
  multiple-answer question is judged on `accepted_keys`, and a tag's reason is checked for a hint at the answer
  before D31 serves the question.* *(Superseded in part 2026-10-21 (the founder; DECISIONS): a tag reason goes through the
  answer-hint check only if a surface could show one. Claude confirmed that none can — nothing outside the pipeline
  reads `questions.tagging` (same DECISIONS).)* *And (Claude's, DECISIONS 2026-10-21, for the founder to overrule): so is every
  figure description, before it serves as alt text, and the extraction's flags stored with the question.* *And (the founder, 2026-10-20; DECISIONS): the crop tool works from the saved page
  images, `pages/pyq/{paper}/{page}.png`.* *Proposed 2026-10-20 (the founder's instruction, building on Claude's
  recommendation: logged now, its details settled when the crop work starts; DECISIONS): each `figures` entry stores its crop box in PDF coordinates
  (page + box), so any crop can be re-cut; display crops are cut from the PDF at a higher resolution than the
  150-dpi reading images (up to a scanned booklet's native resolution), so small labels stay sharp on a phone;
  crops are stored as `figures/pyq/{paper}/{question}-{stem|optionN}.{png|webp}`, sized and compressed for the
  offline day-plan download, and travel in it; the figure's written description doubles as alt text (accessibility,
  and the fallback when an image fails to load); and the rule stands — a figure question is never served without
  its crop.* *And (the founder, 2026-10-21; DECISIONS): for 2025 code 45, each figure is cropped from whichever
  official copy is cleaner at that spot — the bilingual JBIG2 booklet or the English-only CCITT copy, whose coaching
  logo overlaps some figures — and only what is still illegible at phone size is redrawn.* *(Claude's reading, same
  DECISIONS, for the founder to overrule: the founder's "at D31" is ruling 4's crop work before D31; the CCITT copy has
  no saved page images, so its crops come from its own scan, drawn without the logo overlays.)* *And (the founder,
  2026-10-21; DECISIONS): a PYQ's misprints kept as printed carry "a flag the app can show as an 'as printed in the
  original paper' note, like the NCERT errata notes", each with a one-line description of the intended reading.
  (Claude's, same DECISIONS, for the founder to overrule: held in `questions.as_printed` (V10); the notes travel with
  the question in the session payload — they are not a key carrier — after the same answer-hint check as the figure
  descriptions.)* *And (the founder, 2026-10-21; DECISIONS): only an entry classed `show` — a misprint that could change
  how a student reads the question — is displayed; a `record_only` slip is kept and recorded, not surfaced.* *And
  (the founder, 2026-10-21; DECISIONS): a Hindi line for `show` entries before D40 serves the note. (Claude's, same
  DECISIONS, for the founder to overrule: written in this crop work — which, if the crop design is adopted as logged,
  amends every approved paper file with its crop boxes anyway — as a Hindi note beside the English one on each `show`
  entry, required from then.)* *And (the founder, 2026-10-21; DECISIONS): a question whose tag is marked `review` is
  not picked for topic practice until he rules; full past-paper mocks still serve it (D35).* *And (the founder,
  2026-10-22; DECISIONS): a question marked "no NCERT anchor" is never picked for topic practice. (Claude's, same
  DECISIONS, for the founder to overrule: the mark gets a column here, the first reader that filters on it.)*
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
  full NEET 2018–2026 past papers (*since 2026-10-22, SPEC §7.1: the papers in the PYQ bank, currently 2019–2026*) as timed mocks (`kind = mock`, TECH_PLAN §0.5 item 2), out-of-syllabus
  questions excluded from the score; every mock result captured for the later private percentile.
  *Noted 2026-10-20 (D19): the bank's span is NEET 2019–2026, 2018 only if a fitting key turns up (README §6); SPEC
  §7.1's "2018–2026" is put to the founder (decided 2026-10-22: "the NEET papers in the PYQ bank (currently
  2019–2026)"; DECISIONS); and a mock refuses a paper whose marking is not yet sourced (Claude's
  reading of the founder's gap-4 rider, for the founder to overrule; DECISIONS 2026-10-20).* *Noted 2026-10-21 (the
  founder; DECISIONS): out of syllabus is SPEC §7.1's amended definition, and a question whose tag is marked `review`
  is still served in a full past-paper mock.* *And (the founder, 2026-10-22; DECISIONS): a mock serves a question
  marked "no NCERT anchor" with the official key only and one line, "This topic isn't in your current NCERT book;
  answer as per NTA's key." (in English and Hindi from the ARB files — Claude's, same DECISIONS).*
  ✅ adds: at least one full past paper runs end to end as a timed mock (CS-2 §10).*
- **D36 —** Buffer + **Week-6 gate:** practice loop end-to-end incl. offline + diagnostic. *Moved here 2026-10-22
  from D24 under the slippage rule (the founder's table ruling; the split Claude's, DECISIONS 2026-10-22): the NCERT
  table pass on Chemistry, English — its Hindi following the four Chemistry Hindi books in the beta period — then
  Physics, both editions, each table stored so retrieval can hit its rows; the gate never waits on it (Claude's, same
  DECISIONS).* *Added 2026-10-24 (the founder's figures, Claude's placement; DECISIONS): with them, the printed text of
  chem11-part1 Fig. 3.2 and the labels of phy12-part2 Fig. 10.12(b), lifting 2023 G6 Q51's and Q24's `pending` marks.*

### PHASE 5 — Doubt solver (Weeks 7–8, Days 37–48) — Module M6 (the hero)

- **D37 —** Text-doubt path v1: normalize → cache lookup → cheap-tier answer with
  retrieval grounding. Write the `doubt_answer` prefix with comfortable margin over the
  cheap tier's 4,096-token cache floor, and treat prompt length as a per-model cost
  variable — the two tiers tokenize the same prefix ~50% apart (`.claude/rules/ai-layer.md`;
  measured 2026-09-12). ✅ *10 typed doubts answered with correct anchors.* *Amended 2026-10-20
  (founder's T0 ruling, point 7; the sizing correction is Claude's check under point 4, for the founder to
  overrule; DECISIONS 2026-10-20): `doubt_answer` is written for the 5.5
  models; the cheap tier's cache floor is 512 tokens on Haiku 5.5 (TECH_PLAN §4.11), not 4,096, and the
  ~50% figure compared Haiku 4.5 with Sonnet 5, so both lines above describe the old tiers.* *Added 2026-10-23
  (the founder; DECISIONS; SPEC §6.3): a doubt with no NCERT grounding gets an honest reply — variant A where the
  topic lookup list says the current book doesn't teach the topic, variant B otherwise — with what the app can do
  instead, from `docs/copy/doubt-no-grounding.md` (English approved; Hindi after his D64 review); the topic lookup
  list is drafted by Claude from the 2026-10-23 note's diff for the founder's approval before this day; such a doubt
  never counts against the free doubts.* *(Claude's, same DECISIONS: the Hinglish is reviewed with the Hindi at D64.)*
  *And (the founder, 2026-10-23; DECISIONS): the "We're adding …" line only where a source of that kind is planned —
  the earlier-edition line or the Lab Manual line; the free-doubts line only to a Free student; the syllabus chip
  dropped, variant A keeping its "Explain {nearby concept}" chip.*
- **D38 —** Photo path: capture UI + vision extraction → same pipeline. ✅ *10 photographed
  printed questions extracted faithfully.* *Added 2026-10-01 (CS-3 §3.1–§3.2): the offline doubt
  queue (captured with no connection, sent automatically later, metered at send) and photo
  compression before upload with a no-regression eval; if D38 is full, both go to the D42 buffer.*
- **D39 —** Difficulty router + reasoning tier + numerical verification (independent
  re-solve; mismatch → regenerate once → honest fallback + audit queue). ✅ *Seeded
  wrong-answer test proves unverified numericals never render.*
- **D40 —** Answer contract UI: steps, anchor chip (opens NCERT reference view),
  NTA-trap note (only when PYQ-backed), follow-up chips, report flag. ✅ *Pixel/copy
  review against spec wireframe.* *Amended 2026-10-18 (founder; DECISIONS 2026-10-18): the
  reference view shows the paragraph in the student's textbook edition; the English paragraph where
  the Hindi is not loaded (no note) or not printed (one line, “This paragraph is only in the English
  edition”, in the student's language, to Hindi-edition readers); a per-book “Hindi loaded” marker.*
  *Build note 2026-10-18 (founder; DECISIONS 2026-10-17, the correction rule as revised 2026-10-18): the
  anchor view never presents corrected text as the book's own words; it either shows the printed text
  with an errata note, or marks the corrected span as corrected. The same principle holds for the Phase 2
  errata feature.* *And (the founder, 2026-10-23; DECISIONS): an anchor from the NCERT extension carries one line under
  the chip — "From the earlier NCERT edition — not in your current book" / "From the NCERT Lab Manual — not in your
  textbook" — and the reference view shows such a paragraph with that line; built with the extension's code in the
  beta build days (Claude's placement), not here.* *And (the founder, 2026-10-21; DECISIONS): a PYQ's misprint kept as printed carries "a flag the
  app can show as an 'as printed in the original paper' note, like the NCERT errata notes"; the display rule is logged
  here and at D31; and (the founder, 2026-10-21, his change to Claude's draft below) only for a misprint that could
  change how a student reads the question — the entries classed `show`; a `record_only` slip is kept and recorded, not
  surfaced. (Claude's display rule as the founder adopted it, same DECISIONS: one line under the question for each `show`
  entry — the label "As printed in the original paper" from the ARB (en/hi) and the entry's note,
  English until the parked Hindi pass gives it a Hindi line (*superseded in part 2026-10-21: the founder's Hindi line
  for `show` entries before D40 — written in D31's crop work, Claude's — so the note is in the student's language here*) — wherever a PYQ appears, before and after answering: the
  practice item (D32), the mock (D35), this answer view, and every other screen that shows one; the question's text stays the paper's own words, never
  corrected in place, by the errata principle above; the note quotes no more of the paper than the printed span and
  its intended reading.)*
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
  on one phone does not raise that phone's daily free doubts (CS-5 §6.3).* *Added 2026-10-23 (the founder;
  DECISIONS): a doubt the app cannot ground never counts against the free doubts.*
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
  audit error list empty or ticketed. *Added 2026-10-24 (the founder's rule; this day Claude's application of it,
  for him to overrule; DECISIONS): phone OTP live (the floating item after D12) lands in this buffer, the first
  after D27 not already committed, and proves D27's consent flow live there (the founder). ✅ as the floating
  item's, plus D27's consent OTP reaching a real phone.* *Claude's note, for him to rule: that last check needs the
  parent-consent template, still being drafted, approved by D48; if it is not, the check moves with the proof to the
  next buffer after its approval that is not already committed, D60 at the latest, and D48 ticks on the rest.*

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
  *Amended 2026-10-19 (founder's calendar ruling; DECISIONS 2026-10-19): the founder's review of the
  crisis-protocol copy moves here from D67, so D59's copy reaches him by D64 — one of the two things the
  closed test waits for (D66, Claude's placement of the founder's ≈D65).* *Added 2026-10-23 (the founder;
  DECISIONS): his native-reader review of the no-grounding doubt reply's two Hindi variants
  (`docs/copy/doubt-no-grounding.md`) before they ship — with its two Hinglish variants (Claude's placement).*
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
  *Amended 2026-10-19 (founder's calendar ruling, option (c); DECISIONS 2026-10-19; this day chosen by
  Claude, since it carries only the gate while D65 is ⚠): D74's Play work comes here — listing assets,
  data-safety form, and the upload to the **closed** testing track with TECH_PLAN §7.6's release signing key
  and production API URL (TECH_PLAN §7.6 updated to match on 2026-10-24; the key is an upload key, made on the
  Play-account day before the first, internal-testing upload — the founder's answer of 2026-10-24, DECISIONS; that
  the same key signs this upload is Claude's reading) — and the **closed test** opens: 12–15 adult testers the founder recruits
  personally, each under a written tester agreement (F16). It opens only once D59's crisis protocol passes
  every eval case and the founder has reviewed its copy (D64); it does not wait for the legal review (F9)
  or the slow-network test, which still block D79 — the closed test is not "the beta" of SPEC §6.6 and §3.1.
  If the crisis protocol is not passing, the closed test slips; it never runs without it. If the Play
  account is personal, Play's tester rule needs 14 days of it, which then end ≈2026-12-29, before wave 1; an
  organisation account (F14) is exempt (Claude's reading of the calendar report). ✅ adds: installable from
  the closed track.* *Added 2026-10-23 (the founder; DECISIONS): the closed test runs without the NCERT extension,
  built in the beta's build days; the questions it would anchor stay key-only.*

### PHASE 8 — Hardening & polish (Weeks 12–13, Days 67–78) — M11

- **D67 —** Full Hinglish/Hindi copy pass with a native-speaker read (mentor voice
  audit — no guilt, no fake-human). ✅ *String review sheet signed off.* *Added 2026-10-01 (CS-2
  §4.1, §4.5; CS-6 §2.4): the crisis-protocol copy (founder review), streak-repair copy and the
  first-week ramp's mentor notes.* *Amended 2026-10-19 (DECISIONS 2026-10-19): the crisis-protocol copy
  review moved to D64, ahead of the closed test.*
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
  ✅ *Installable from the testing track.* *Amended 2026-10-19 (DECISIONS 2026-10-19): the listing, the
  data-safety form and the first upload moved to D66 with the closed test; D74 refreshes the build on the
  track the beta installs from (Claude's choice, for the founder to overrule; DECISIONS 2026-10-19).* *Since
  2026-10-24 (the founder's answer 4; DECISIONS): the first upload is the internal-testing upload on the
  Play-account day (F14), for the Play-key OTP template, not D66's.*
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
  the weeks 15–20 operating plan (daily 1h ops + 3h fixes/Phase-2 prep). *Amended 2026-10-19 (founder's
  calendar ruling; DECISIONS 2026-10-19): a **4-week** beta period, weeks 15–18, public launch ≈2027-02-02.*

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
*Amended 2026-10-18 (founder; DECISIONS 2026-10-18):* the Hindi editions of the four Chemistry books,
deferred from D16, come first in the beta period's build hours, ahead of the §12.1 backlog and
without waiting on the D60 gate condition, each after its decoder-check trigger ruling; and the exit
criteria to public launch add **all Hindi books loaded and closed**.
*Amended 2026-10-19 (founder's calendar ruling, option (c); DECISIONS 2026-10-19):* the beta period is
**four weeks** (weeks 15–18, after D84), with Pro at no cost until public launch (the SPEC §6.9 amendment
the founder instructed),
public launch ≈2027-02-02; the ≈2027-02-16 six-week fallback is removed. **"All Hindi books loaded and
closed" is no longer an exit criterion to public launch; it becomes the gate for Hindi-medium marketing**
(the SPEC §9 amendment the founder instructed). The Chemistry Hindi books still come first in the beta period's build days
(*superseded 2026-10-23: they come third, in the order below*), and any
content-pipeline work runs only in beta build days or buffers, never on core days (slippage-log rule).
*Amended 2026-10-23 (founder; DECISIONS 2026-10-23) — a founder-approved reordering of the beta build days, not a
scope trade or an overrun:* the build days run in this order — (1) the NCERT extension's code (migration V12, the
assembler's margin rule, the anchor's source field, the labels, the fixtures), its English earlier-edition sections
and the backfill of the questions they anchor; (2) the English Lab Manuals; (3) the four Chemistry Hindi books, any
of which may slip past public launch under SPEC §9's allowance (Hindi questions on Chemistry grounded in the English
text meanwhile); (4) the §12.1 backlog. The extension's Hindi follows launch, with English grounding meanwhile; its
earlier-edition sections come into the beta if days remain. F19's files are due by 2027-01-05 (D84), before the
first build day.

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
  *Amended 2026-10-19 (founder's calendar ruling; DECISIONS 2026-10-19): after the 24-day content-pipeline
  overrun, a closed test from ≈D65 (placed on D66, ≈2026-12-15, by Claude for the founder to overrule) → beta
  waves from 2026-12-30 → public launch ≈2027-02-02 — which Claude reads as still inside SPEC §6.9's "from
  January, annual is front-and-center" (left to the founder in the calendar report).*
