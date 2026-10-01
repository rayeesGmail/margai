# Change Spec CS-4 — Self-Learner Mentor Enhancements

**Status:** Founder-approved product change, issued at ~D25 (2026-09-27). To be integrated by
Claude Code per §8. SPEC.md amendments in §8.1 are authorized by this change spec.
**Intent in one line:** make the app a complete replacement for what offline coaching gives a
self-learning NEET student — structure, teaching support, doubts, ranking, discipline and exam
temperament — while staying the mentor, not a lecture library.
**What this spec does not do:** it contains no implementation design. Pipelines, data models,
UI construction and integrations are Claude Code's to plan (plan first, then build).

---

## 1. Positioning and principles

1. **Primary target: self-learners.** Coaching students remain supported, but when trade-offs
   arise, design for the student preparing without a coaching institute.
2. **We are the mentor, not the classroom.** No lecture library, no recorded courses, no live
   classes. Teaching support comes from short written primers and curated free external
   lectures.
3. **Replace what coaching gives, one need at a time:**

| Coaching provides | Our answer | Status |
|---|---|---|
| Structure (what to study, in what order) | Personal plan, rebuilt nightly | Built (SPEC §6.1) |
| Teaching (concept explanation) | Concept primers + curated free lectures | **New: §3, §4** |
| Doubt clearing | Instant verified doubt solver | Built (SPEC §6.3) |
| Tests and ranking | Past-paper mocks + autopsy; mock percentile | Mocks built (CS-2 §4.7); **percentile new: §6** |
| Discipline and environment | Plan, streaks, slump care; weekly review; presence | **New: §5, §7** |
| Exam temperament | Mock autopsy, exam-season modes; drills | **New: §5.2** |

4. **Copyright rules unchanged.** No verbatim NCERT text; primers are written fresh and
   grounded. External videos are linked or embedded only through YouTube's official player,
   never downloaded or re-hosted.

## 2. Scope summary

| Item | Phase |
|---|---|
| Concept primer per topic | Phase 1 |
| Curated free YouTube lectures per chapter | Phase 1 (link-out in beta; in-app player after the D60 gate) |
| Sunday review with the mentor | Phase 1 |
| Timing and skip-strategy drills | Phase 1 |
| Mock percentile among app users | Immediately after public launch |
| "Studying right now" presence | Phase 2 |

## 3. Concept primers

- **Definition:** for every syllabus topic, a short written explanation (roughly 200–400 words)
  that a self-learner reads before practising — what the concept is, why it matters, the core
  formula or idea, one worked mini-example, and the most common misconception.
- **Grounding and quality:** written from the NCERT retrieval layer in our own words (never
  verbatim), with an NCERT chapter/section reference as a pointer; numerical examples pass the
  standard verification; generated through the content pipeline and sampled by the founder like
  PYQ solutions; covered by the eval suite.
- **Languages:** English at launch; Hindi and Hinglish as the Hindi corpus lands.
- **Placement:** inside learn blocks for self-study students ("Read the primer, then 10 recall
  questions"), and reachable from any topic's detail view and from a doubt answer ("Read the
  basics of this topic").
- **Tiering:** all tiers (teaching support is part of care, not a premium).
- **Acceptance:** primers exist for 100% of topics in the top-50 weightage chapters before beta
  and for all topics before public launch; founder sample of 30 primers passes; eval gate green.

## 4. Curated free YouTube lectures

- **Definition:** for each chapter, one or two hand-picked free lectures from YouTube, ideally one
  in Hindi and one in English, shown inside the learn block with title, channel, language, length,
  and optional start times for key sections.
- **No partnerships.** Videos are shown only via link-out (beta) and YouTube's official embedded
  player (after the D60 gate). Rules: official player only, branding and ads untouched, only
  embeddable videos, no downloading, cutting or re-hosting, and no transcript extraction into our
  AI pipeline.
- **Curation data:** a founder-owned list (like the taxonomy CSV) — chapter code, video ID,
  channel, language, length, start-time notes, date checked, curator notes.
- **Curation process:** the pipeline may shortlist candidates per chapter using YouTube's official
  data API (metadata only: title, channel, length, views, date); the founder (or a paid one-off
  helper) makes final picks using the criteria: correct and aligned to the current post-2022
  syllabus, clear teaching, established channel, Hindi and English coverage.
- **Competitor rule:** prefer independent teachers when quality is comparable; use channels of
  competing apps only when clearly the best explanation.
- **Maintenance:** a monthly automated check flags videos that became private, deleted, or
  non-embeddable; a "Was this helpful?" tap under each video feeds curation and the collective-
  intelligence layer.
- **Tiering:** all tiers.
- **Acceptance:** at least one vetted video for every chapter before beta; broken-link check runs
  and reports; helpfulness signal recorded.

## 5. Discipline and exam temperament

### 5.1 Sunday review with the mentor
- **Definition:** a short weekly conversation (about five minutes) inside the plan chat, offered on
  Sunday evening: what went well, what slipped, the week's pattern in plain words, and an agreed
  focus for next week, which the planner then honours.
- **Rules:** every claim backed by the student's own data (Evidence rule); honest and kind about
  missed days (streak-repair framing); skippable without penalty; the agreed focus is visible on
  the next week's plans.
- **Tiering:** all tiers (the deep weekly report remains Pro; the conversation itself is care).
- **Acceptance:** a seeded week of activity produces a review whose statements are all traceable
  to the student's data, and next week's plan reflects the agreed focus.

### 5.2 Timing and skip-strategy drills
- **Definition:** short drills that train exam temperament — pacing against the clock, deciding
  when to skip, and negative-marking discipline — built from past-paper questions and the
  student's own gamble-score history from mock autopsies.
- **Placement:** introduced by the planner mainly in mock season and the final months; available
  on demand.
- **Tiering:** all tiers; the full personalised drill history follows the mock-autopsy tiering
  (summary for Free, full for Pro).
- **Acceptance:** a student with a high gamble score receives targeted skip drills; drill results
  flow into the next mock's autopsy comparison.

## 6. Mock percentile among app users (post-launch)
- **Definition:** after a timed past-paper mock, show the student's percentile among app users who
  took the same paper recently ("better than 68% of students who took NEET 2023 this month").
- **Rules:** shown privately to the student only; no leaderboards, names, or public ranks; shown
  only once a paper has enough attempts for the number to be meaningful (threshold in config);
  framed as calibration, never as judgment.
- **Tiering:** all tiers.
- **Phase:** activates after public launch, when attempt volume is sufficient; the data needed
  must be captured from the first mock onward.
- **Acceptance:** percentile appears only above the attempt threshold; no view exposes other
  students' identities or scores.

## 7. "Studying right now" presence (Phase 2)
- **Definition:** an ambient count on the Today screen ("1,240 droppers are studying right now")
  to recreate the feeling of a classroom without social features.
- **Rules:** aggregate counts only; no chat, profiles, or messaging; hidden when numbers are too
  small to be encouraging.
- **Phase:** Phase 2.

## 8. Integration instructions for Claude Code

### 8.1 SPEC.md (edits authorized by this change spec)
- §2 (who it's for): state self-learners as the primary target.
- §6.1 (planner): add concept primers and curated lectures to learn blocks; add the Sunday review.
- §6.2 (practice) and mock sections: add timing and skip drills; add the mock percentile (post-
  launch).
- §9 (content foundation): add concept primers and the curated-lectures list as content assets.
- §10 (personalization charter): add "teaching support is care, available in all tiers."
- Phase 2 list: add "studying right now" presence.
Touch nothing else in SPEC.

### 8.2 TECH_PLAN
Add sections for the primer pipeline and its eval coverage, the curated-lectures data and monthly
check, the YouTube link-out and embedded-player compliance rules, the Sunday review flow, drills,
and the percentile calculation and privacy rules. Dated notes; do not rewrite history.

### 8.3 PLAN and TRACKER day mappings
- §3 primers: pipeline generation in the D22–D24 content window (top-50 chapters), remaining
  topics in a later buffer before D74; UI in learn blocks at D55–D56.
- §4 curated lectures: founder workstream **F12 (curate chapter videos)** starting now, due
  before D74; link-out UI at D55–D56; in-app player after the D60 gate (beta backlog); monthly
  check at D73.
- §5.1 Sunday review: D58 (plan chat).
- §5.2 drills: D54 (mock autopsy) with planner placement at D59.
- §6 percentile: data capture from D35; display after public launch.
- §7 presence: Phase 2.

### 8.4 DECISIONS.md rows
Self-learners as primary target · no lecture library (primers + curated free lectures instead) ·
YouTube compliance rules and competitor-channel preference · Sunday review as care in all tiers ·
mock percentile private and threshold-gated · presence deferred to Phase 2.

### 8.5 Process
Store this file as `docs/changes/CS-4-self-learner-mentor.md`. Plan first: show the integration
plan before editing. Surface any conflict with SPEC, TECH_PLAN or earlier change specs instead of
resolving silently.
