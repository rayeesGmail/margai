#!/usr/bin/env bash
# precommit-gate.sh — Claude Code PreToolUse hook on Bash; acts only on `git commit`.
# Also runnable by hand:  scripts/precommit-gate.sh
#                         scripts/precommit-gate.sh --ai-hash   (print AI-content hash)
#
# Checks, in order (docs/DEV_SPEC.md §13.3, CLAUDE.md hard rules):
#   1. no credential-like content in the change set   (scripts/detect-secrets.sh)
#   2. no todo-style markers in added code lines       (CLAUDE.md: raise them in session)
#   3. AI-touching paths (prompts/, routing, retrieval, eval fixtures) require
#      eval/run.sh to have PASSED on exactly this content (eval/.last-pass stamp)
#   4. server: ./mvnw verify        — SKIPPED with a loud warning while server/mvnw is absent
#   5. app:    flutter analyze      — SKIPPED with a loud warning while app/pubspec.yaml is absent
#
# The change set is the union of staged, unstaged and untracked (non-ignored)
# files, because `git add … && git commit …` has nothing staged when this hook
# fires. Exit 2 blocks the commit; any internal error also exits 2 (fail closed).
set -Euo pipefail
trap 'echo "precommit-gate: internal error at line $LINENO — failing closed" >&2; exit 2' ERR

ROOT="${CLAUDE_PROJECT_DIR:-$(git rev-parse --show-toplevel 2>/dev/null || pwd)}"
cd "$ROOT"

AI_PATHS='^server/src/main/resources/prompts/|^server/.*/ai/|(^|/)[^/]*(router|routing|retriev)[^/]*(/|$)|^eval/fixtures/.+\.(json|jsonl|ya?ml|csv)$'
CODE_EXT='\.(java|kt|kts|dart|py|sh|sql|ya?ml|json|xml|properties|gradle|st|toml|arb|ts|js)$'

sha256() { if command -v sha256sum >/dev/null 2>&1; then sha256sum; else shasum -a 256; fi; }

# Content hash of every tracked or untracked (non-ignored) AI-touching file.
ai_hash() {
  git ls-files -co --exclude-standard | { grep -E "$AI_PATHS" || true; } | sort -u \
  | while IFS= read -r f; do
      [ -f "$f" ] && printf '%s %s\n' "$f" "$(sha256 <"$f" | cut -d' ' -f1)"
    done | sha256 | cut -d' ' -f1
}

if [ "${1:-}" = "--ai-hash" ]; then ai_hash; exit 0; fi

# ---- hook filtering: only act on `git commit` ---------------------------------
INPUT=""
if [ ! -t 0 ]; then INPUT="$(cat || true)"; fi
if [ -n "$INPUT" ] && command -v jq >/dev/null 2>&1; then
  TOOL="$(jq -r '.tool_name // empty' <<<"$INPUT")"
  if [ -n "$TOOL" ]; then
    [ "$TOOL" = "Bash" ] || exit 0
    CMD="$(jq -r '.tool_input.command // empty' <<<"$INPUT")"
    grep -Eq '(^|[^A-Za-z0-9_])git([ ]+-[^ ]+)*[ ]+commit([^A-Za-z0-9_]|$)' <<<"$CMD" || exit 0
  fi
fi

say() { printf '%s\n' "$*" >&2; }
FAIL=0

if git rev-parse --verify -q HEAD >/dev/null 2>&1; then BASE=HEAD; else BASE="$(git hash-object -t tree /dev/null)"; fi
CHANGED="$({ git diff --name-only "$BASE"; git diff --cached --name-only; git ls-files --others --exclude-standard; } | sort -u)"

say "── precommit gate ────────────────────────────────────────────"

# 1. secrets
if scripts/detect-secrets.sh --scan-diff; then
  say "✅ secrets      clean"
else
  say "❌ secrets      credential-like content in the change set (see above)"; FAIL=1
fi

# 2. todo-style markers in added code lines (pattern split so this file never matches itself)
MARK='(^|[^A-Za-z0-9_])(TO''DO|FIX''ME|XX''X|HA''CK)([^A-Za-z0-9_]|$)'
MARK_HITS=""
while IFS= read -r f; do
  [ -n "$f" ] && [ -f "$f" ] || continue
  [[ "$f" =~ $CODE_EXT ]] || continue
  case "$f" in docs/*|.claude/*) continue ;; esac
  if git ls-files --error-unmatch -- "$f" >/dev/null 2>&1; then
    ADDED="$(git diff -U0 "$BASE" -- "$f" | { grep -E '^\+' || true; } | { grep -Ev '^\+\+\+' || true; } | sed 's/^+//')"
  else
    ADDED="$(cat "$f")"
  fi
  H="$({ grep -E "$MARK" <<<"$ADDED" || true; } | head -1 | cut -c1-100)"
  [ -n "$H" ] && MARK_HITS+="   $f: $H"$'\n'
done <<<"$CHANGED"
if [ -z "$MARK_HITS" ]; then
  say "✅ todo-markers none in added code"
else
  say "❌ todo-markers forbidden in committed code — raise them in the session instead (CLAUDE.md):"
  printf '%s' "$MARK_HITS" >&2; FAIL=1
fi

# 3. eval stamp for AI-touching paths
AI_CHANGED="$({ grep -E "$AI_PATHS" <<<"$CHANGED" || true; })"
if [ -n "$AI_CHANGED" ]; then
  WANT="$(ai_hash)"; HAVE="$(cat eval/.last-pass 2>/dev/null || true)"
  if [ "$WANT" = "$HAVE" ]; then
    say "✅ eval-gate    stamp matches current prompts/routing/retrieval content"
  else
    say "❌ eval-gate    AI-touching paths changed but eval/run.sh has not PASSED on this content:"
    printf '%s\n' "$AI_CHANGED" | sed 's/^/   /' >&2
    say "   run:  cd eval && ./run.sh   (DEV_SPEC §4.5 gate: ≥97%, zero unverified numericals)"; FAIL=1
  fi
else
  say "✅ eval-gate    no AI-touching paths changed"
fi

# 4. server build + tests
if [ -x server/mvnw ]; then
  LOG="$(mktemp -t precommit-mvnw)"
  say "▶  server       ./mvnw -B -q verify (log: $LOG)"
  if (cd server && ./mvnw -B -q verify >"$LOG" 2>&1); then
    say "✅ server       mvnw verify passed"
  else
    say "❌ server       mvnw verify FAILED — last 40 lines:"; tail -40 "$LOG" >&2; FAIL=1
  fi
else
  say "⚠️  SKIPPED     server check: server/mvnw is not present yet (arrives D2; this warning must disappear then)"
fi

# 5. app lint
if [ -f app/pubspec.yaml ]; then
  if command -v flutter >/dev/null 2>&1; then
    LOG="$(mktemp -t precommit-flutter)"
    say "▶  app          flutter analyze (log: $LOG)"
    if (cd app && flutter analyze >"$LOG" 2>&1); then
      say "✅ app          flutter analyze passed"
    else
      say "❌ app          flutter analyze FAILED — last 40 lines:"; tail -40 "$LOG" >&2; FAIL=1
    fi
  else
    say "❌ app          app/pubspec.yaml exists but 'flutter' is not on PATH — install Flutter stable"; FAIL=1
  fi
else
  say "⚠️  SKIPPED     app check: app/pubspec.yaml is not present yet (arrives D2; this warning must disappear then)"
fi

say "──────────────────────────────────────────────────────────────"
if [ "$FAIL" -ne 0 ]; then
  say "⛔ COMMIT BLOCKED by scripts/precommit-gate.sh — fix the ❌ items above and retry."
  exit 2
fi
say "✅ precommit gate passed"
exit 0
