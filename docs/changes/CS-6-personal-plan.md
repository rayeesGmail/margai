# Change Spec CS-6 — Making the Plan Feel Personal

**Status:** Founder-approved product change, issued at ~D25 (2026-09-27). To be integrated by
Claude Code per §7. SPEC.md amendments in §7.1 are authorized by this change spec.
**Intent in one line:** turn "technically personalized" into "this app really knows me" —
because a plan students feel is theirs drives daily retention, and retention decides everything.
**What this spec does not do:** it contains no implementation design. Algorithms, thresholds,
data models and test tooling are Claude Code's to plan (plan first, then build).

---

## 1. The six qualities of a personal plan

Every planner behavior must serve at least one of these, and none may undermine another:

1. **Specific** — every block's reason uses the student's own data.
2. **Responsive** — skips, struggles and doubts visibly change tomorrow, within a day.
3. **Remembering** — the mentor refers back to specific recent moments.
4. **Controllable** — she can push back and the plan listens.
5. **Honest** — it admits what it doesn't yet know; it never overclaims.
6. **Stable** — it changes for good reasons, not randomly; big changes are explained.

Already built or specced (keep): reasons and mentor note (D56), doubt write-back (D45), nightly
re-plan (D55–D57), plan chat (D58), notebook patterns (D53), rough-day care (D59), collective prior
on day 1 (CS-1), diagnostic (D35), student corrections override (charter rule 7).

## 2. New planner rules

### 2.1 Continuity (stability)
- Day-to-day, most of the plan carries forward unless there is a clear, data-backed reason to
  change it. The continuity level is a configuration value (default: a majority of tomorrow's
  blocks continue today's direction).
- Any substantial change (e.g. a subject swapped out, the day's load changed noticeably, a chapter
  moved) must be explained in the mentor note.
- Emergencies that justify larger changes: slump days, negotiated life events, exam-season mode
  switches, and repeated "too hard" signals.

### 2.2 Skip with a reason
- Marking a block "skipped" offers one optional tap: **No time · Too hard · Already know this**.
- Each reason drives a different response:
  - *No time* → load adjustment (§2.3), not a difficulty change.
  - *Too hard* → an easier entry point next time (primer, easier questions, confidence question).
  - *Already know this* → a short check (a few questions); if passed, the topic's ability estimate
    rises and the block is retired; if not, it returns gently.
- Skipping without a reason is always allowed and never penalized.

### 2.3 Load follows reality
- Daily load adapts to the student's actual completion over the recent past, not only the hours
  declared at onboarding.
- When the plan is reduced or increased, the mentor says so honestly (e.g. "I've made your days a
  little lighter, since you've been finishing about 60%").
- Load never drops so low that exam-critical coverage is lost silently; if the gap between her
  real pace and her target grows, the trajectory card and the Sunday review (CS-4 §5.1) say so
  kindly, with options.

### 2.4 The first seven days (designed ramp)
The first week is when a student decides whether the app "gets" her. Each day must show visible
learning, and the mentor note should name what changed:

| Day | Plan is built from | Mentor note shows |
|---|---|---|
| 1 | Onboarding answers + collective prior + scorecard (if any) | Honest start: "Here's day one. I'll learn fast." |
| 2 | + diagnostic results (if taken) | What the diagnostic revealed |
| 3 | + first practice mistakes and doubts | "Your first mistakes told me…" |
| 4–5 | + skip reasons and completion pace | Load and difficulty adjustments |
| 6 | + first mistake-healing returns | First day-3 checks |
| 7 | Full week of evidence | A short "what I learned about you this week" summary |

- If the diagnostic was skipped, day 2 invites it again gently; the ramp continues without it.
- Collective vs personal attribution follows CS-1 throughout.

### 2.5 Remembering out loud
- The nightly snapshot includes a small set of notable recent moments (a hard session, a streak
  milestone, a healed mistake, a negotiated change) so the mentor can reference them naturally.
- References are specific and occasional, not in every block.

## 3. Reason quality guarantees

- **Traceability:** every block reason and every mentor-note claim must be traceable to specific
  data points in the student's history (or, when attributed as such, to the collective record).
  Reasons failing traceability are replaced by the deterministic fallback reason.
- **No generic filler:** reasons that could apply to any student ("practice is important", "stay
  consistent") are rejected by an automated check. Maintain a blocklist of generic phrasing plus a
  specificity check.
- **Eval coverage:** sampled reasons from simulated students join the eval suite with a gate on
  traceability and specificity.

## 4. Measuring whether it feels personal

- **Weekly one-tap question** (after the Sunday review or on the weekly card): "Did this week's plan
  fit you?" — Yes · Mostly · No. A "No" offers one optional follow-up tap: too much · too little ·
  wrong topics · too hard · too easy.
- **Behavioral signals:** block completion rate; share of skips by reason; frequency of plan-chat
  negotiations; share of "already know this" checks passed (a high pass rate means the plan is
  serving known material).
- **Dashboard:** these metrics by week-of-tenure (week 1, week 2, …), so the first-week ramp's effect
  is visible. Targets set after beta baseline.

## 5. Simulation testing

Extend the D55 acceptance (five synthetic students, one night) to a **14-day simulation** per
archetype (fresher 2-year, fresher 1-year, dropper, repeater, plus a low-completion and a
high-achiever variant). The simulation must show:
1. Different students receive clearly different plans.
2. Every reason is traceable (§3) and none is generic.
3. The plan reacts within one day to a skip-with-reason, a cluster of mistakes, and repeated doubts
   on a topic.
4. Continuity holds (§2.1): no unexplained large swings.
5. Load converges toward the simulated student's real completion pace (§2.3).
6. The first-week ramp (§2.4) produces the expected mentor notes.

## 6. Non-goals
No change to the no-planless-morning fallback, the Evidence rule, answer-quality gates, or tiering
(all of this is care, available in every tier). No new social or comparative features.

## 7. Integration instructions for Claude Code

### 7.1 SPEC.md (edits authorized by this change spec)
- §6.1 (planner): add §1 qualities, §2.1 continuity, §2.2 skip reasons, §2.3 load adaptation,
  §2.4 first-week ramp, §2.5 remembering.
- §10 (personalization charter): add "stable, not random" and "the plan fits the life she actually
  has" as principles.
- §11 (success metrics): add §4 measures.
Touch nothing else in SPEC.

### 7.2 TECH_PLAN
Add sections for continuity policy, skip-reason handling, completion-based load adaptation,
the first-week ramp, notable-moments memory, reason traceability and generic-phrase checks, the
weekly fit question, and the 14-day simulation harness. Dated notes; do not rewrite history.

### 7.3 PLAN and TRACKER day mappings
- §2.2 skip reasons: D33 (session/block status UI) with planner handling at D55.
- §2.1 continuity, §2.3 load, §2.5 remembering: D55–D56.
- §2.4 first-week ramp: D56 with copy at D67.
- §3 traceability and generic checks: D56, eval gate at D56/D60.
- §4 weekly question: D58 (with the Sunday review); metrics at D73.
- §5 14-day simulation: D55 (harness), required for the D60 gate.

### 7.4 DECISIONS.md rows
Six qualities as planner design rules · continuity rule · skip reasons · completion-based load ·
designed first-week ramp · reason traceability and generic-phrase rejection · weekly fit question
· 14-day simulation as a D60 gate requirement.

### 7.5 Process
Store this file as `docs/changes/CS-6-personal-plan.md`. Plan first: show the integration plan
before editing. Surface any conflict with SPEC, TECH_PLAN or CS-1 to CS-5 instead of resolving
silently.
