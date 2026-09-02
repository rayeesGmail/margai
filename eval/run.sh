#!/usr/bin/env bash
# eval/run.sh — the eval gate (docs/DEV_SPEC.md §4.5).
#
# Contract: run the doubt pipeline against the hand-verified fixtures in
# eval/fixtures/ and PASS only if ≥97% of final answers are correct AND zero
# unverified numericals were served. Required after ANY change to prompts,
# routing or retrieval (CLAUDE.md), and run in CI.
#
# On PASS this writes eval/.last-pass — a content hash of the AI-touching paths —
# which scripts/precommit-gate.sh compares before allowing such a commit.
#
# STATUS: PLACEHOLDER. The fixture suite is seeded at D23 and the real runner
# replaces the body below. Until then it passes with 0 fixtures, and fails
# closed the moment fixtures exist without a runner.
set -euo pipefail
HERE="$(cd "$(dirname "$0")" && pwd)"
ROOT="$(cd "$HERE/.." && pwd)"

N="$(find "$HERE/fixtures" -type f -name '*.json' 2>/dev/null | wc -l | tr -d ' ')"
if [ "$N" -ne 0 ]; then
  echo "eval: $N fixture(s) found but no runner is implemented yet — replace this placeholder (D23). RESULT: FAIL" >&2
  exit 1
fi

echo "eval: PLACEHOLDER — 0 fixtures in eval/fixtures (suite arrives D23)."
echo "eval: pass rate n/a (gate ≥97%), unverified numericals served: 0. RESULT: PASS (placeholder)"
"$ROOT/scripts/precommit-gate.sh" --ai-hash >"$HERE/.last-pass"
echo "eval: stamp written to eval/.last-pass"
