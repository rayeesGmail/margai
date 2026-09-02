---
description: Load week N of docs/PLAN.md (days 6N-5..6N + the week gate) and start a plan-first build session
argument-hint: <week 1-14>
---

Start week $ARGUMENTS of the MARG AI build. Week N covers days D(6N-5) to D(6N) of docs/PLAN.md §3
and ends with that week's 🚩 gate. Do this, in order:

1. Read docs/PLAN.md §3 for week $ARGUMENTS: list each day's scope and its ✅ acceptance check,
   and the week gate. Read docs/TRACKER.md: the status dashboard, which of these days are already
   ticked, the PARKED list and the slippage log.
2. List the docs/SPEC.md sections that govern this week's features (cite numbers), plus the
   docs/DEV_SPEC.md reference sections and any D3-approved design in docs/ that applies.
3. Plan first — do not code until the plan is approved. Break the week's first unticked day into
   ≤8 tasks, each with the test that proves it. Cite SPEC section numbers per task. Surface any
   conflict between the docs instead of resolving it silently.
4. After approval, execute task by task: tests accompany every service-layer change; run
   `cd server && ./mvnw verify` / `cd app && flutter analyze && flutter test` after each task;
   run `cd eval && ./run.sh` after any prompt, routing or retrieval change; commit per task with a
   conventional message (`git add`, then `git commit`, separately).
5. Before the day's final commit run the `spec-auditor` subagent on the diff; fix findings.
6. Run the day's ✅ acceptance check literally and paste the evidence. Tick the day in
   docs/TRACKER.md only if it is committed AND the check passed; add a day-log entry; move new
   ideas to PARKED; record slips in the slippage log.
7. On the week's last day, run the 🚩 gate as a demo script and record PASS / PARTIAL / FAIL.

One day-scope in flight at a time. Never end the session uncommitted.
