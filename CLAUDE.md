# MARG AI — working agreements

Product: AI mentor app for NEET (no human faculty). Full contract: docs/SPEC.md.
Read the relevant SPEC section BEFORE implementing a feature; cite section numbers
in your plan. If code and SPEC conflict, say so — do not silently pick one.

## Stack (fixed — do not substitute)
- server/: Java (latest LTS), Spring Boot 4, Maven, Postgres 18 (Flyway migrations), pgvector
- app/: Flutter (Android target), Riverpod, drift for offline
- ai: direct provider APIs ONLY via AiClient interface (server/.../ai/) — Anthropic for models,
  Cohere for embeddings. Model IDs from config. Bedrock is dormant, not deleted.
- infra: ap-south-1; secrets via SSM; never write AWS keys or provider API keys anywhere

## Commands
- server: `cd server && ./mvnw verify` (must pass before any commit)
- app: `cd app && flutter analyze && flutter test`
- eval gate: `cd eval && ./run.sh` (required after ANY change to prompts/routing/retrieval)
- local db: `docker compose up db` (root compose file)

## Hard rules
- correct_key is never sent before that student's answer to the question is recorded server-side;
  judging is server-side (DEV_SPEC §5, TECH_PLAN §0.4 #4)
- no AI answer path without retrieval grounding + numerical verification (DEV_SPEC §4.2, R2)
- every model call logs an ai_calls row (DEV_SPEC §3.4)
- provider API keys are the auth model for AI: SSM SecureString in a deployed environment, an
  untracked local file on a laptop, never in code, config, logs or a repo file. Rotation:
  docs/runbooks/ai-provider-keys.md
- money endpoints idempotent; Razorpay webhook signature verified (DEV_SPEC §5)
- uploaded images: S3 uploads/ bucket only (24h lifecycle) (DEV_SPEC R6)
- schema changes ONLY via Flyway migration + matching JPA entity update
- TODOs forbidden in committed code; raise in the session instead

## Style
- small PR-sized commits per task; conventional commit messages
- tests accompany every service-layer change; controller tests via MockMvc
- Flutter: no logic in widgets; state in Riverpod providers; strings in ARB (en/hi)

## Compact instructions
When compacting: preserve API contract changes + rationale, migration list,
open TODOs from the current /week task list, eval gate status. Summarize exploration.

---
The block above is DEV_SPEC §13.2 verbatim, except for three approved deviations. Its "SPEC §3–5"
citations now read "DEV_SPEC" (they were written when the Developer Spec was docs/SPEC.md). Hard
rule 1 carries the D3 rewording the founder approved on 2026-09-04 (TECH_PLAN §0.4 #4: SPEC §6.2/§6.4
verdicts show the correct option after an answer is recorded). And the AI stack line, the ai_calls
hard rule and the API-key rule carry the provider switch the founder approved on 2026-09-12
(DECISIONS, TECH_PLAN §4.11): Bedrock is blocked for this account, so model access is direct.
DEV_SPEC §13 keeps its original wording as the historical record.

## Documents and precedence (read before proposing anything)
1. docs/SPEC.md — Product Spec v2.0, **the contract**: behaviour, every screen and rule,
   the Phase-2 exclusion list (§12). Contains no implementation detail by design (§13).
2. docs/DEV_SPEC.md — Developer Spec v1.1. §13 (working agreements) is authoritative;
   §2–12 are reference only, confirmed or replaced section by section in TECH_PLAN §0.3.
3. docs/TECH_PLAN.md — Technical Plan v1.0, approved at D3 (2026-09-04): architecture, data
   model, API, AI pipeline, app, content pipeline, infra, testing, conventions. Cite as
   `TECH_PLAN §n`; where it differs from DEV_SPEC §2–12 it wins. §0.4 lists the surfaced
   conflicts, §0.5 the founder's decisions, §0.2 the rule edits still due on later days.
4. docs/PLAN.md — the 14-week schedule (D1–D84), one ✅ acceptance check per day and a gate
   per week. Supersedes the 6-week plan in DEV_SPEC §10.
5. docs/TRACKER.md — live state. Update at the end of every session; tick a day only when it
   is committed AND its ✅ check passed. New ideas go to its PARKED list, never into scope.

When these disagree, say so out loud and cite both — never silently pick one.

## Session rules (enforced by .claude/settings.json hooks + scripts/, not by prose)
- Plan first for anything nontrivial; one day-scope in flight; never end a session uncommitted.
- Change repo files only with the Write/Edit tools — never via shell redirection, heredocs or
  `sed -i`. The path and secret guards hook Write/Edit fully; Bash is guarded by heuristics only.
- Run `git add` and `git commit` as separate commands; the commit gate inspects the change set.
- Claude commits; the human reviews and pushes. `git push`, `aws *`, `.env*` reads and
  WebFetch are denied. FakeAiClient is the default; a live provider needs a human-launched
  `AI_LIVE=1` profile with the cost breaker active (DEV_SPEC §13.7).
- Phase-2 items (SPEC §12) are out of scope. Where the spec is silent choose the boring,
  maintainable option and record it in docs/DECISIONS.md; where it conflicts, surface it.
- docs/SPEC.md is amended only by the founder, or by Claude on an explicit per-edit instruction in
  that session, each amendment with a DECISIONS.md row citing the finding that forced it. Contract
  edits are never bundled into ordinary task work (ruling at D3 close, 2026-09-04).
- Anything the human corrects twice belongs here or in a `.claude/rules/*.md` file.
