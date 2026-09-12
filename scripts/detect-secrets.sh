#!/usr/bin/env bash
# detect-secrets.sh — credential scanner (docs/DEV_SPEC.md §13.3).
#
# Modes
#   hook (stdin JSON from .claude/settings.json):
#     PreToolUse  Write|Edit|Bash  scans the content about to be written / the
#                                  shell command. Exit 2 BLOCKS the tool call.
#     PostToolUse Write|Edit|Bash  scans the written file / the repo diff.
#                                  Exit 2 cannot undo the write; it warns Claude
#                                  and precommit-gate.sh blocks the commit.
#   --scan-diff    staged + working-tree changes + untracked files (precommit gate)
#   --scan-tree    every tracked file (CI)
#   --scan-file F  one file
#
# Exit 2 on any hit, 0 when clean, 2 on internal error (fail closed).
set -Eeuo pipefail
trap 'echo "detect-secrets: internal error at line $LINENO — failing closed" >&2; exit 2' ERR

ROOT="${CLAUDE_PROJECT_DIR:-$(git rev-parse --show-toplevel 2>/dev/null || pwd)}"
cd "$ROOT"

# name|flags|ERE   (flags: "i" = case-insensitive, "-" = none)
# Every pattern is written so that it does not match its own source text.
PATTERNS=(
  'AWS_ACCESS_KEY_ID|-|(^|[^A-Z0-9])(AKIA|ASIA|AGPA|AIDA|AROA|AIPA|ANPA|ANVA|A3T[A-Z0-9])[A-Z0-9]{16}([^A-Z0-9]|$)'
  'AWS_SECRET_ACCESS_KEY|i|aws.{0,30}secret.{0,30}[=:][ ]*["'"'"']?[A-Za-z0-9/+=]{40}([^A-Za-z0-9/+=]|$)'
  'PRIVATE_KEY_BLOCK|-|-----BEGIN [A-Z ]*PRIVATE KEY-----'
  'GITHUB_TOKEN|-|(gh[pousr]_[A-Za-z0-9]{36,}|github_pat_[A-Za-z0-9_]{22,})'
  'SLACK_TOKEN|-|xox[baprs]-[A-Za-z0-9-]{10,}'
  'GOOGLE_API_KEY|-|AIza[0-9A-Za-z_-]{35}'
  'RAZORPAY_KEY|-|rzp_(live|test)_[A-Za-z0-9]{10,}'
  'MODEL_PROVIDER_KEY|-|sk-ant-[a-z0-9]{3,12}-[A-Za-z0-9_-]{24,}'
  'EMBED_PROVIDER_KEY|i|cohere[a-z0-9_-]{0,24}[ ]*[=:][ ]*["'"'"']?[A-Za-z0-9]{30,}'
  'JWT|-|eyJ[A-Za-z0-9_-]{10,}\.eyJ[A-Za-z0-9_-]{10,}\.[A-Za-z0-9_-]{10,}'
  'GENERIC_SECRET_ASSIGNMENT|i|(api[_-]?key|apikey|secret|token|passwd|password|auth[_-]?key)[a-z0-9_]{0,20}[ ]*[=:][ ]*["'"'"'][^"'"'"' ]{16,}["'"'"']'
)

HITS=0

# scan_text LABEL TEXT. Prints one line per hit, redacted, and counts it.
# Takes the text as an argument (never via a pipe): a pipeline would run this
# in a subshell and the HITS counter would be lost (bash 3.2 on macOS has no
# lastpipe).
scan_text() {
  local label="$1" text="$2" entry name flags regex gflags line
  [ -n "$text" ] || return 0
  for entry in "${PATTERNS[@]}"; do
    name="${entry%%|*}"
    regex="${entry#*|}"; flags="${regex%%|*}"; regex="${regex#*|}"
    gflags="-En"; [ "$flags" = "i" ] && gflags="-Eni"
    while IFS= read -r line; do
      [ -n "$line" ] || continue
      HITS=$((HITS + 1))
      printf 'detect-secrets: %-26s %s:%s  %s\n' "$name" "$label" "${line%%:*}" \
        "$(printf '%s' "${line#*:}" | cut -c1-120 | sed -E 's/[A-Za-z0-9\/+=_-]{12,}/<redacted>/g')" >&2
    done < <(grep $gflags -- "$regex" <<<"$text" || true)
  done
}

is_text_file() { [ -f "$1" ] && grep -Iq . "$1" 2>/dev/null; }

scan_file() {
  local f="$1"
  is_text_file "$f" || return 0
  scan_text "$f" "$(cat "$f")"
}

# Added lines only for tracked files; whole file for untracked ones.
scan_diff() {
  local base f
  if git rev-parse --verify -q HEAD >/dev/null 2>&1; then base=HEAD; else base="$(git hash-object -t tree /dev/null)"; fi
  while IFS= read -r f; do
    [ -n "$f" ] && [ -f "$f" ] || continue
    if git ls-files --error-unmatch -- "$f" >/dev/null 2>&1; then
      is_text_file "$f" || continue
      scan_text "$f" "$(git diff -U0 "$base" -- "$f" | { grep -E '^\+' || true; } | { grep -Ev '^\+\+\+' || true; } | sed 's/^+//')"
    else
      scan_file "$f"
    fi
  done < <({ git diff --name-only "$base"; git diff --cached --name-only; git ls-files --others --exclude-standard; } | sort -u)
}

scan_tree() {
  local f
  while IFS= read -r f; do scan_file "$f"; done < <(git ls-files)
}

finish() {
  local how="$1"
  if [ "$HITS" -gt 0 ]; then
    echo "detect-secrets: $HITS credential-like hit(s) — $how" >&2
    exit 2
  fi
  exit 0
}

case "${1:-}" in
  --scan-diff) scan_diff; finish "remove them before committing." ;;
  --scan-tree) scan_tree; finish "the tree must be clean." ;;
  --scan-file) scan_file "$2"; finish "remove them." ;;
  "") ;;
  *) echo "usage: $0 [--scan-diff|--scan-tree|--scan-file F]  (or hook JSON on stdin)" >&2; exit 2 ;;
esac

# ---- hook mode ---------------------------------------------------------------
if ! command -v jq >/dev/null 2>&1; then
  echo "detect-secrets: jq is required (brew install jq). Failing closed." >&2
  exit 2
fi
INPUT="$(cat || true)"
[ -n "$INPUT" ] || exit 0

EVENT="$(jq -r '.hook_event_name // empty' <<<"$INPUT")"
TOOL="$(jq -r '.tool_name // empty' <<<"$INPUT")"

if [ "$EVENT" = "PreToolUse" ]; then
  F="$(jq -r '.tool_input.file_path // "?"' <<<"$INPUT")"
  case "$TOOL" in
    Write)      scan_text "Write($F)"     "$(jq -r '.tool_input.content // .tool_input.file_content // empty' <<<"$INPUT")" ;;
    Edit)       scan_text "Edit($F)"      "$(jq -r '.tool_input.new_string // empty' <<<"$INPUT")" ;;
    MultiEdit)  scan_text "MultiEdit($F)" "$(jq -r '[.tool_input.edits[]?.new_string] | join("\n")' <<<"$INPUT")" ;;
    Bash)       scan_text "Bash"          "$(jq -r '.tool_input.command // empty' <<<"$INPUT")" ;;
  esac
  if [ "$HITS" -gt 0 ]; then
    echo "BLOCKED by scripts/detect-secrets.sh: credential-like content must never be written. Secrets go to SSM; use placeholders like <set-in-ssm> in code and docs." >&2
    exit 2
  fi
  exit 0
fi

if [ "$EVENT" = "PostToolUse" ]; then
  case "$TOOL" in
    Write|Edit|MultiEdit)
      # Backstop for the repo only; scratch files elsewhere (e.g. test
      # fixtures that deliberately contain fake keys) are the PreToolUse
      # scan's business, and it already ran.
      F="$(jq -r '.tool_input.file_path // empty' <<<"$INPUT")"
      case "$F" in "$ROOT"/*) scan_file "$F" ;; esac
      ;;
    Bash) scan_diff ;;
  esac
  if [ "$HITS" -gt 0 ]; then
    echo "WARNING from scripts/detect-secrets.sh: a credential-like string is now in the working tree. Remove it immediately; precommit-gate.sh will block the commit until it is gone." >&2
    exit 2
  fi
  exit 0
fi

exit 0
