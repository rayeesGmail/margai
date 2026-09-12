# Change Spec CS-1 — Collective Intelligence Layer (CIL)

**Status:** Founder-approved product change, issued post-D5. To be integrated by Claude Code
into the existing document system (integration instructions in §9 — SPEC.md amendments are
made on the founder's explicit instruction in this change spec).
**Intent in one line:** the planner (and error diagnosis) should be excellent for a brand-new
student on day 1 by drawing on everything learnable about NEET students *in general* — a
collective prior — and then shift weight to the individual's own data as it accumulates
(personalized posterior).

---

## 1. Product definition

The CIL is a pre-launch content asset plus a planner behavior:

- **Asset:** per-topic "collective records" describing how NEET students in general
  experience that topic — struggle level, realistic pacing, common misconceptions, exam
  momentum, and season effects — compiled from public data before we have users.
- **Behavior:** the nightly planner and day-1 plan read TWO sources: the collective record
  (general) and the student state (individual), blended by a weighting that starts heavily
  collective and shifts to the individual as their data accumulates.

Principles (extend the SPEC's Evidence rule, do not weaken it):
1. **Signals, not content.** We mine public discourse for *patterns about students*
   (which chapters are widely called hard, when slumps happen). We never ingest, store, or
   reproduce other platforms' content, answers, or question banks.
2. **Evidence rule, collective form.** A day-1 claim may cite the collective when it is
   true and sourced: "most students underestimate this chapter" is permitted only if the
   collective record supports it. Individual claims still require individual data.
3. **Honest ramp.** Early copy says the plan sharpens as the app learns the student.
   Collective-backed confidence never masquerades as personal knowledge.
4. **Founder-reviewed editorial.** Mined outputs are drafts; the founder reviews before
   they become planner inputs (same governance as the taxonomy).

## 2. The collective record (per syllabus topic/chapter node)

Fields (exact storage design is Claude Code's; these are the semantic requirements):
- `struggle_score` (0–1): how consistently students report difficulty with this node.
- `pacing_multiplier` (e.g. 1.0–2.5): realistic time vs naive estimate; multiplies the
  node's default learn-minutes for planning.
- `momentum_trend`: NTA's recent emphasis direction for this node (rising/flat/falling),
  derived from PYQ year-over-year frequency.
- `misconceptions[]`: named misconception entries {label, description, example distractor
  patterns, linked PYQ question ids}. Seeds error diagnosis before any user data exists.
- `season_notes[]`: month-relative effects ("commonly deferred to late revision",
  "slump-adjacent: often abandoned in month 4").
- `strategy_notes[]`: aggregated topper/teacher consensus for this node (e.g. "PYQ-first
  chapter", "NCERT-lines-heavy for exam").
- `confidence` (0–1) + `source_summary`: how well-attested the record is; low-confidence
  records are ignored by the planner (Evidence rule).
- All records carry `season_version` (e.g. "2027-prep") for annual refresh.

## 3. Sources and mining rules

| Source | What it yields | Method |
|---|---|---|
| Our PYQ bank (already planned, D19–D23) | momentum_trend; misconception entries from distractor analysis across 15+ years; difficulty corroboration | pipeline batch jobs over `questions` + `distractor_map`; no new external data |
| Public student discourse (open forums, Reddit, Quora, public Telegram/YouTube comments) | struggle_score, pacing_multiplier priors, season_notes | AI-assisted thematic mining of founder-collected excerpts; store derived signals + source counts, never verbatim quotes |
| Public topper/teacher material (published schedules, "important chapters" videos/posts) | strategy_notes, sequencing validation against our prerequisite graph | founder curates a source list; AI aggregates; disagreements with our backbone are flagged for founder review, not auto-applied |
| Official data (NTA syllabus history, cutoff tables — already planned) | target-side calibration; syllabus-change season_notes | existing pipeline assets |

Hard boundaries: no scraping of paywalled or login-gated platforms; no competitor app
content, question banks, or answers; respect robots/ToS for anything fetched; no personal
data about identifiable students; derived signals only, with aggregate source counts.
Founder supplies raw excerpt files under `pipeline/inputs/collective/` (they are inputs like
the taxonomy CSV); the pipeline never crawls the web itself.

## 4. Pipeline additions

New pipeline commands (same conventions as existing §6 commands; idempotent, reports to
`pipeline/reports/`):
- `collective from-pyq` — derives momentum_trend + misconception drafts from the question
  bank. Runs after `pyq` + `stats` + anchors exist (i.e., in the D19–D24 window).
- `collective from-inputs` — mines founder-collected excerpt files into draft
  struggle/pacing/season/strategy signals.
- `collective review` — emits a founder-review sheet (drafts + evidence); founder-edited
  sheet is re-ingested as the approved record set.
- `collective load` — loads approved records with `season_version`.

Cost expectation: CHEAP-tier batch mining; order of a few thousand rupees. Every AI call
through AiClient with ledger rows, as always.

## 5. Planner behavior changes

1. **Two-source read.** The deterministic candidate builder and the AI selection step both
   receive the collective record alongside the student state.
2. **Prior→posterior weighting.** Per node, an "individual evidence level" (function of
   that student's practice volume, diagnostic coverage, and doubt history on the node)
   drives the blend: at zero evidence the collective dominates (~90/10); as evidence
   accumulates the individual dominates. Exact function is Claude Code's to design and must
   be deterministic, explainable, and config-tunable. Individual data always wins conflicts
   at sufficient evidence.
3. **Pacing.** Node learn-minutes = default × pacing_multiplier until the student's own
   measured pace on adjacent nodes replaces it.
4. **Season calendar.** The nightly run consults month-relative season_notes (e.g. month-4
   slump prevalence) to pre-emptively soften volume/tone — before individual slump signals
   fire. Individual signals still override.
5. **Reason lines.** Day-1/low-evidence reasons cite the collective truthfully
   ("carries ~12 marks; most students underestimate it — I've given it extra room").
   Individual-evidence reasons cite the student's data as today. Never blend the two into
   a false personal claim.
6. **Diagnostic acceleration.** Unchanged mechanics; note in copy that the diagnostic is
   the fastest way to shift weight from "students like you" to "you".

## 6. Error-diagnosis bootstrap

`misconceptions[]` seeds the classifier: when a student's wrong option matches a known
misconception pattern for that node, the cause classification may cite it from event one
("classic sign-convention trap — trips most students") with collective attribution.
Individual correction and history still override, exactly as specced.

## 7. Acceptance criteria and metrics

- **Pipeline:** approved collective records exist for ≥ the top-50 weightage nodes with
  confidence ≥ threshold; founder review sheet signed; all records versioned.
- **Planner:** (a) two synthetic day-1 students with different onboarding answers receive
  visibly different, collective-informed plans whose every block reason is backed
  (collective or individual, correctly attributed); (b) the same student with two weeks of
  synthetic history receives plans where individual data demonstrably outweighs the prior
  on practiced nodes; (c) removing the collective records degrades day-1 plans (sanity
  check that the layer is actually load-bearing).
- **Metrics to dashboards:** day-1→day-7 plan-block completion trend (the "visible
  responsiveness" promise), % of reason lines by attribution type, collective-record
  coverage of served blocks.
- **Eval:** collective-attributed claims join the eval suite (a sampled claim must trace
  to its record).

## 8. Explicit non-goals

No live/continuous web mining; no per-student comparisons to named cohorts beyond the
existing anonymous percentile; no use of collective data to inflate trajectory precision;
no delay to current schedule for discourse mining if founder input collection lags — the
from-pyq half alone ships value and the from-inputs half can land in any later buffer.

## 9. Integration instructions for Claude Code

1. **SPEC.md (contract; edits authorized by this change spec):** add "Collective
   intelligence" to §9 (content foundation) as §9.6 using §1–§2 above in product language;
   amend §6.1 (planner) with the two-source/prior-posterior behavior and honest-ramp copy
   rule; extend §10 (personalization charter) with principle: "collective claims are
   attributed as collective; personal claims require personal data."
2. **TECH_PLAN:** add the CIL sections (asset, pipeline commands, planner weighting,
   diagnosis bootstrap, acceptance) in the appropriate places; record schema location and
   the weighting function design are yours to propose within §5's constraints; note the
   two rules extensions in the follow-on edits table.
3. **PLAN/TRACKER:** map work: `collective from-pyq` + record schema into the D19–D24
   window (name it in D22–D24 scopes); `from-inputs`/`review`/`load` into D24 buffer with
   explicit permission to slip to any later buffer; planner two-source behavior into
   D55–D56 scopes (amended 2026-09-12, founder ruling: the day-1 first plan's read of the
   collective record — pacing multipliers, priority, templated collectively-attributed
   reasons, graceful degradation when records are absent or below threshold — goes into
   D29; the evidence-weighted prior→posterior blend stays in D55–D56); diagnosis bootstrap
   into D49; add founder workstream **F11: collect
   public-discourse excerpt files + source list** (needed before `from-inputs`; not
   blocking anything else). Update TRACKER day lines and workstreams accordingly.
4. **DECISIONS.md:** one row for the CIL adoption citing this change spec; one for the
   weighting-function design when you propose it.
5. Store this file as `docs/changes/CS-1-collective-intelligence.md`. Surface any conflict
   with SPEC/TECH_PLAN instead of resolving silently. Plan first; show me the integration
   plan before editing.
