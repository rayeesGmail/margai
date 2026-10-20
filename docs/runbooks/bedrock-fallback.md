# Runbook — switching completions to the Bedrock fallback

The completion fallback (CS-2 §4.11, TECH_PLAN §15.12): when Anthropic's own API is down, completions move to
the same Claude models on Amazon Bedrock by a flag and a task restart. There is no automatic failover. Embeddings
never move — they are on Bedrock already (the D15 pin) — and the ledger, the breaker, the tier policy, schema
validation and retries run exactly as on the primary, as do the client's response checks: an answer cut off at
the output-token limit, or a refusal (Bedrock's content filter and guardrail stops included), is refused rather
than served or repaired.

## What it switches

| tier | primary (Anthropic) | fallback (Bedrock, `global.*` profile) |
|---|---|---|
| CHEAP | `claude-haiku-5-5` | `global.anthropic.claude-haiku-5-5` |
| REASON | `claude-sonnet-5-5` | `global.anthropic.claude-sonnet-5-5` |
| VISION | `claude-haiku-5-5` | `global.anthropic.claude-haiku-5-5` |

Each fallback tier calls its primary's model in the primary's request shape (temperature, thinking, effort, tool
choice). The 5.5 models have no `in.*` profile yet; when one appears, the ids move to it (founder, DECISIONS
2026-10-20). Configuration: `margai.ai.fallback-provider`, `margai.ai.fallback.tier.*` and the price rows in
`margai.ai.prices-json`; startup refuses a missing id, a missing price (cache rates included) or a fallback provider
that is the primary, and warns when a fallback id no longer names its tier's primary model — move the fallback id
whenever a primary model changes.

## Before it is called ready

1. **Prices — done 2026-10-20.** The founder read the Bedrock pricing page (Asia Pacific (Mumbai), global
   cross-region inference): Haiku 5.5 0.10 / 0.50, 5-minute cache write 0.125, cache read 0.01, and long
   context 0.50 / 2.50 / 0.625 / 0.05; Sonnet 5.5 2.00 / 10.00, 2.50, 0.10 — USD per million, batch N/A. The
   two `global.anthropic.*` rows in `margai.ai.prices-json` carry exactly these (DECISIONS 2026-10-20). Read the
   page again whenever a fallback id changes.
2. **The smoke** — one call per runtime tier, CHEAP and REASON (founder ruling, DECISIONS 2026-10-20), on the
   founder's laptop with Docker running (the smoke's ledger is a Testcontainers database):

   ```
   aws sso login --profile margai
   cd server && unset AWS_BEARER_TOKEN_BEDROCK && AWS_PROFILE=margai AI_LIVE=1 \
     ./mvnw test -Dtest=BedrockFallbackSmokeTest -Dbedrock.fallback.smoke=true -Dsurefire.failIfNoSpecifiedTests=false
   ```

   It passes when the inner client is `bedrock (fallback)` and each tier writes one `ok` row on its fallback id,
   with tokens, a cached prefix and a cost. A stale `AWS_BEARER_TOKEN_BEDROCK` in the shell shadows the SSO
   profile for Bedrock and survives a refresh — hence the `unset`.

   **First run, 2026-10-20: refused.** Bedrock answered both calls with 403 `AccessDeniedException:
   anthropic.claude-haiku-5-5 is not available for this account … contact AWS Sales` (and the same for Sonnet 5.5).
   It is not IAM: an IAM denial names the caller and the `bedrock:InvokeModel` action. The founder then ran the
   availability check, a free control-plane call, on Haiku 5.5 (Sonnet 5.5 is unchecked):

   ```
   aws bedrock get-foundation-model-availability --model-id anthropic.claude-haiku-5-5 --region ap-south-1 --profile margai
   ```

   It read `authorizationStatus` AUTHORIZED, `entitlementAvailability` AVAILABLE and `regionAvailability` AVAILABLE,
   but `agreementAvailability` NOT_AVAILABLE: no agreement is in place for the model. The same check reads
   NOT_AVAILABLE for Claude Haiku 4.5 (`anthropic.claude-haiku-4-5-20251001-v1`), which answered on 2026-09-06, and
   AVAILABLE for `cohere.embed-v4`, which has served every embedding since 2026-09-20 — so the account can hold and
   pay for a Bedrock agreement; the missing agreement is on both Anthropic models checked, and the refusal was seen on
   the two invoked (Haiku 5.5 and Sonnet 5.5). The console playground gives
   the same AccessDeniedException and offers no use-case form. The cause is unknown; the founder raised an AWS
   Support case the same day (TRACKER F17; DECISIONS 2026-10-20). Before rerunning the smoke, run the check for both
   `anthropic.claude-haiku-5-5` and `anthropic.claude-sonnet-5-5`; while either reads NOT_AVAILABLE the smoke is
   expected not to pass.

Readiness is the smoke (PLAN D18, TECH_PLAN §15.12).

## Before a deployed switch

**The task role (carried to F8).** The ECS task role's Bedrock statement (TECH_PLAN §7.4, amended 2026-10-01) must
allow the two `global.anthropic.*` profiles. F8 creates the task role. The laptop smoke above proves the SSO
identity, not the task role.

## Switching (an Anthropic outage)

1. Set `MARGAI_FLAGS_AI_FALLBACK=true` for the API service and the nightly task (SSM, then the task definition).
2. Restart the ECS service (and let the next nightly run start under the new definition).
3. Check the startup log: `AiClient chain: ledger > breaker > tier-policy > schema > retry > bedrock (fallback)`.
4. Check the ledger: new completion rows in `ai_calls` carry `global.anthropic.claude-*-5-5` model ids, failed
   calls included (embedding rows keep `global.cohere.embed-v4:0`).

Switching back is the same with the flag `false`. The D70 drill exercises both directions.

**Not for pipeline runs.** The content pipeline's VISION commands run on profile-pinned models; with the flag on,
`ncert extract` and `ncert verify` refuse to start (their model guards see the fallback's VISION id). A pipeline
profile that pins VISION also logs the startup warning that `margai.ai.fallback.tier.vision` does not name the
tier's model — expected for that run, and harmless while the flag is off.

## A nightly run under the fallback

The nightly re-plan makes one CHEAP `PlanSelectTask` call per user active in 14 days (TECH_PLAN §4.5 step 5)
through `completeBatch`. Under the fallback that is the on-demand loop at concurrency 4, never a batch submission
(DECISIONS 2026-10-20: "sequential batch under fallback is fine at beta scale", read as that loop — Claude's
reading, for the founder to overrule). At **500 users**, assuming about 5 s a call — assumed, since
`PlanSelectTask` is built at D56 and its answers are expected to run to a few hundred tokens; the T0 smoke's
56-token answers took about 1 s on Haiku 5.5's direct API, and Bedrock's latency is measured by the smoke above:

| | about 5 s a call | every call at the 20 s timeout (worst case) |
|---|---|---|
| concurrency 4 (as built) | **about 10–11 minutes** | about 42 minutes |
| one call at a time | about 42 minutes | about 2 h 47 min |

Retries add to both. The run starts at 00:30 IST and plans are wanted by the morning, so even the worst case fits;
a user whose call fails gets the deterministic plan (`generated_by = fallback`), never a planless morning.
