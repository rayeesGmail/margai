#!/usr/bin/env bash
# block-paths.sh — Claude Code PreToolUse hook for Write | Edit | Bash.
#
# Policy (docs/DEV_SPEC.md §13.3): Claude may build and test freely but may NOT
# touch infra/prod paths, dotenv files, key material or credential stores.
# Exit 2 blocks the tool call; stderr is shown to Claude. Any internal error
# also exits 2 (fail closed) — a policy engine must never fail open.
#
# Also enforces the db-migrator subagent scope (DEV_SPEC §13.5): when the hook
# input says agent_type == db-migrator, only server/src/main/resources/db/** may
# be written.
#
# Stdin: the hook JSON ({tool_name, tool_input, agent_type, cwd, ...}).
set -Eeuo pipefail
trap 'echo "block-paths: internal error at line $LINENO — failing closed" >&2; exit 2' ERR

if ! command -v jq >/dev/null 2>&1; then
  echo "block-paths: jq is required (brew install jq). Failing closed." >&2
  exit 2
fi

INPUT="$(cat || true)"
[ -n "$INPUT" ] || exit 0   # not invoked as a hook

TOOL="$(jq -r '.tool_name // empty' <<<"$INPUT")"
AGENT="$(jq -r '.agent_type // empty' <<<"$INPUT")"
ROOT="${CLAUDE_PROJECT_DIR:-$(jq -r '.cwd // "."' <<<"$INPUT")}"

block() {
  printf 'BLOCKED by scripts/block-paths.sh: %s\n' "$1" >&2
  exit 2
}

# Word-boundary helpers (portable across GNU and BSD grep; \b is not).
W='(^|[^A-Za-z0-9_])'
E='([^A-Za-z0-9_]|$)'

# ---- file paths (Write / Edit / NotebookEdit) --------------------------------
PROTECTED_SEGMENT='(^|/)(infra|terraform|prod|production|secrets)(/|$)'
DOTENV='(^|/)\.env(\.[A-Za-z0-9_-]+)?$'
DOTENV_OK='(^|/)\.env\.example$'
KEYFILE='\.(pem|key|p12|jks|pfx)$|(^|/)id_(rsa|ed25519|ecdsa|dsa)(\.pub)?$'

check_path() {
  local p="$1" rel
  case "$p" in
    "$ROOT"/*) rel="${p#"$ROOT"/}" ;;
    *)         rel="$p" ;;
  esac

  # Credential stores outside the repo.
  case "$p" in
    "$HOME"/.aws/*|"$HOME"/.ssh/*|"$HOME"/.gnupg/*|~/.aws/*|~/.ssh/*|~/.gnupg/*|/etc/*)
      block "credential/system path is off-limits: $p" ;;
  esac

  if [ "$AGENT" = "db-migrator" ] && ! [[ "$rel" =~ ^server/src/main/resources/db/ ]]; then
    block "db-migrator may only write under server/src/main/resources/db/ (got: $rel) — DEV_SPEC §13.5"
  fi
  if [[ "$rel" =~ $DOTENV ]] && ! [[ "$rel" =~ $DOTENV_OK ]]; then
    block "dotenv file ($rel) — Claude never writes .env*; secrets live in SSM, local values are human-edited. Only .env.example is allowed."
  fi
  if [[ "$rel" =~ $PROTECTED_SEGMENT ]]; then
    block "infra/prod/secrets path ($rel) is human-only (DEV_SPEC §13.3)."
  fi
  if [[ "$rel" =~ $KEYFILE ]]; then
    block "key material ($rel) is never written by Claude."
  fi
  return 0
}

case "$TOOL" in
  Write|Edit|MultiEdit|NotebookEdit)
    P="$(jq -r '.tool_input.file_path // .tool_input.notebook_path // empty' <<<"$INPUT")"
    [ -n "$P" ] && check_path "$P"
    ;;

  Bash)
    CMD="$(jq -r '.tool_input.command // empty' <<<"$INPUT")"
    [ -n "$CMD" ] || exit 0

    # A lone `git commit` / `git log` cannot read or write files, but its
    # message text may legitimately mention .env (this policy's own commits
    # do). Exempt it — unless it is compound, or reads a message file
    # (-F/--file/-t/--template), which could smuggle a secret path in.
    if grep -Eq '^[ ]*git[ ]+(commit|log)([^A-Za-z0-9_]|$)' <<<"$CMD" \
       && ! grep -Eq '(&&|\|\||;|\||`|\$\()' <<<"$CMD" \
       && ! grep -Eq "${W}(-F|--file|-t|--template)" <<<"$CMD"; then
      exit 0
    fi

    STRIPPED="${CMD//.env.example/}"

    # Any mention of a dotenv file is blocked outright: Bash would otherwise
    # bypass the Read(.env*) deny rule and the Write/Edit hook.
    if grep -Eq "${W}\\.env${E}" <<<"$STRIPPED"; then
      block "shell command references a dotenv file. Never read or write .env* (only .env.example is allowed)."
    fi
    if grep -Eq "${W}secrets/|~/\\.(aws|ssh|gnupg)|\\\$HOME/\\.(aws|ssh|gnupg)|/\\.(aws|ssh|gnupg)/|/etc/(passwd|shadow|sudoers)" <<<"$CMD"; then
      block "shell command references a secrets/ or credential-store path."
    fi
    # Writes into infra/prod paths or key files (reading terraform files is
    # fine). Two shapes: a redirect whose TARGET is a protected path, or a
    # write verb followed by a protected path within the same simple command.
    # (A bare '>' anywhere is not enough: commit trailers like <a@b.c> have one.)
    PROT_DIR='(infra|terraform|prod|production)/'
    PROT_FILE="(\\.(pem|key|p12|jks|pfx)${E}|(^|/)id_(rsa|ed25519|ecdsa|dsa)${E})"
    REDIRECT_INTO=">{1,2}[ ]*[\"']?([^ \"'>]*/)?(${PROT_DIR}|[^ \"'>/]*${PROT_FILE})"
    VERB_INTO="${W}(tee|cp|mv|rm|touch|dd|ln|install|truncate|chmod|chown|sed)${E}[^|;&]*(${W}${PROT_DIR}|${PROT_FILE})"
    if grep -Eq "$REDIRECT_INTO" <<<"$CMD" || grep -Eq "$VERB_INTO" <<<"$CMD"; then
      block "shell command writes into an infra/prod path or key file (human-only, DEV_SPEC §13.3)."
    fi
    ;;
esac

exit 0
