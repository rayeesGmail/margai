# AI provider keys — issue, install, rotate, revoke

Since the 2026-09-12 provider switch (DECISIONS; TECH_PLAN §4.11) model access is direct, so an API
key **is** the auth model for AI: there is no cloud role behind these calls and no SSO session to
expire. Two keys exist, both SSM SecureStrings in a deployed environment and untracked local
environment variables on the founder's laptop (TECH_PLAN §7.3, §9.2):

| Parameter | Read by | Blank means |
|---|---|---|
| `/margai/beta/ai/anthropic/api_key` → `MARGAI_AI_ANTHROPIC_API_KEY` | `AnthropicConfiguration` | the `live` profile refuses to start |
| `/margai/beta/ai/cohere/api_key` → `MARGAI_AI_COHERE_API_KEY` | `CohereConfiguration` | the `live` profile refuses to start |

Neither key appears in `application.yml` (the defaults are empty strings), in a `.env` file that is
tracked, or in a log line. `scripts/detect-secrets.sh` carries a pattern for each provider's key
format and blocks a write that contains one; the commit gate re-scans the change set.

## Issuing a key

1. In the provider's console, create the key in the **workspace** for this project, not the default
   one — a workspace is the unit a spend limit and an alert attach to (F8 checklist), and it is what
   makes per-project usage readable later.
2. Name it for where it runs (`margai-beta-ecs`, `margai-laptop`), so a revocation is a decision
   about one place, never a guess.
3. Give the laptop its own key. Sharing the deployed key with a developer machine means a rotation
   is a deployment, and a leak is both.

## Installing it

Deployed (founder runs these; `aws *` is denied to Claude):

```
aws ssm put-parameter --name /margai/beta/ai/anthropic/api_key --type SecureString \
  --value "<the key>" --overwrite --region ap-south-1
aws ecs update-service --cluster margai-beta --service margai-api --force-new-deployment
```

The task definition maps the parameter to the environment variable; ECS reads SSM at task start, so
a new key reaches the process only on a new task — the forced deployment is part of the rotation,
not an afterthought.

Laptop: put both keys in the untracked `.env.local` (`.env.*` is git-ignored and
`scripts/block-paths.sh` refuses to write it) and source it in the shell that launches a live run:

```
cd server && AI_LIVE=1 ./mvnw test -Dtest=AiLiveSmokeTest -Dsurefire.failIfNoSpecifiedTests=false
```

## Rotating

No key is dual-accepted the way `margai.auth.jwt.secret` is (§9.2): the provider decides what a key
is worth, so the overlap has to come from having two valid keys rather than from our config.

1. Create the replacement key in the same workspace; leave the old one enabled.
2. `put-parameter --overwrite` with the new value, then force a new deployment.
3. Confirm the new tasks answer — `/actuator/health` plus one `ai_calls` row written after the
   deployment timestamp (the ledger is the proof a real call succeeded, not the health check).
4. Only then revoke the old key in the console, and delete its parameter version if it was stored
   anywhere else.

Rotate on the usual triggers: a suspected leak, a laptop change, a contributor leaving, or a
scheduled rotation at each season refresh. A rotation costs one deployment and no user-visible
downtime, so there is no reason to defer one.

## Revoking in a hurry

Revoke in the provider console first — that stops spend immediately and everywhere, including a key
copy we do not know about. The server then fails calls as `AI_UNAVAILABLE`, which the UI renders
honestly (§3.3) and the breaker and ledger record; it does not crash. Replace the key afterwards.

## What a leaked key can cost

Spend, not data: these keys can run model calls on our workspace, they cannot read the database,
S3 or a student's record. The bound on the damage is the console workspace spend limit plus our own
`ai.cost.paise` daily alarm from the ledger (§10.4) — which is why the workspace limit is a founder
checklist item and not optional.
