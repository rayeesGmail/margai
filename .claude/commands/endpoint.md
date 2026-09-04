---
description: Scaffold controller + service + repository + MockMvc test for one API endpoint per the contract
argument-hint: <METHOD /path, e.g. "POST /doubts">
---

Scaffold the endpoint `$ARGUMENTS` in server/.

1. Find its contract: the D3-approved API document in docs/ if it exists, otherwise
   docs/DEV_SPEC.md §5. Quote the request/response shape, auth requirement, rate limit and whether
   it is marked idempotent. If the endpoint is not in the contract, stop and say so.
2. Plan the files (controller, service, repository/entities touched, request/response records,
   MockMvc test, any Flyway migration via the `db-migrator` subagent) and wait for approval.
3. Write the MockMvc test first: happy path, auth failure, validation failure returning the error
   envelope `{error: {code, message_en, message_user_lang}}`, and — where the contract marks it —
   a replayed `Idempotency-Key` returning the original result.
4. Implement to green. Rules that always apply: `correct_key` never in a response before that
   student's answer to the question is recorded server-side (TECH_PLAN §3.1 carriers only); money endpoints
   idempotent and webhook signatures verified; any AI call through `AiClient` with an `ai_calls`
   row; copy returned in both en and the user's language; no secrets or model IDs in code.
5. Run `cd server && ./mvnw verify`; if the endpoint touches prompts/routing/retrieval also run
   `cd eval && ./run.sh`. Commit with a conventional message.
