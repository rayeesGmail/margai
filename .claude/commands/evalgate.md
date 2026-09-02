---
description: Run the eval suite, report pass rate vs the 97% gate, list failures by subject
allowed-tools: Bash(cd eval && ./run.sh), Bash(./eval/run.sh), Read, Glob, Grep
---

Run the eval gate and report honestly.

1. Run `cd eval && ./run.sh` and capture the full output.
2. Report: fixtures run, pass rate vs the gate (≥97% correct final answers), unverified numericals
   served (must be 0), and the eval/.last-pass stamp status.
3. If there are failures, list them grouped by subject (physics / chemistry / botany / zoology)
   with fixture id, expected vs produced, and the likely cause class (retrieval miss, routing,
   prompt, verification).
4. If the gate FAILS, say `EVAL GATE: FAIL` and do not use sign-off language of any kind — no
   "good enough", no "mostly passing". State what must change and that prompt/routing/retrieval
   changes cannot be committed until it passes.
5. If it PASSES, say `EVAL GATE: PASS` and remind that any prompt change also needs a line in
   docs/prompt-changelog.md.
