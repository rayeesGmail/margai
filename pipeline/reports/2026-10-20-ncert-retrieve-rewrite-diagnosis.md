# D18 T0 — the switch to the 5.5 models: smoke and regression report (stopped for the founder)

Written by Claude beside the run reports `2026-10-20-ncert-retrieve-rewrite.md` (the NEW CHEAP model, Claude
Haiku 5.5, writing `query_rewrite` v1's search versions — the Week-3 gate's new run of record) and
`2026-10-20-ncert-retrieve-rewrite-2.md` (the same-day control on the OLD model, Claude Haiku 4.5 at temperature
0). The founder ran scratchpad b3c73b13 `d18-t0-regression.sh` on the jar built at afc1b9c (T0 611263f plus the
VISION switch; the retrieval does not use VISION). The rules are the founder's T0 message and the committed rows
of DECISIONS 2026-10-20: each test is judged against its **score of record**, fixed before the run — D17's run of
record of 2026-10-19 (`2026-10-19-ncert-retrieve-rewrite-2.md`) for `query_rewrite`, the D5 live re-run's pass for
`smoke`; the same-day old-model runs are diagnostic only; **any score below the score of record is a regression
and stops for the founder**, with a diagnosis, no prompt edited.

## Verdict: STOPPED — the bar is met, but nine MRR scores are below the score of record

D17's bar on the new run of record: **MET** — acceptance 15 of 15 in the answer set, every Hindi query (6 of 6),
every subject 5 of 5, held-out 3 of 4. Every count-based score (in the answer set, hit@1, hit@3) in every table is
equal to or above the score of record, and so is every other table (the tick tables, the diagnostic, held-out,
English-rewrite and no-filter tables). These nine cells are below it:

| table | score of record (Haiku 4.5) | new (Haiku 5.5) | same-day control (Haiku 4.5) | lower ranks behind it |
|---|---|---|---|---|
| acceptance, fused, all — MRR | 0.802 | **0.793** | 0.802 | a09, a10 |
| acceptance, fused, Hindi — MRR | 0.742 | **0.717** | 0.742 | a09, a10 |
| acceptance, fused, Chemistry — MRR | 0.690 | **0.660** | 0.690 | a09, a10 |
| baseline, unfused, all — MRR | 0.638 | **0.637** | 0.637 | q03 |
| baseline, unfused, English — MRR | 0.657 | **0.656** | 0.656 | q03 |
| baseline, unfused, Physics — MRR | 0.638 | **0.637** | 0.637 | q03 |
| baseline, fused, all — MRR | 0.738 | **0.737** | 0.737 | q03 |
| baseline, fused, English — MRR | 0.657 | **0.656** | 0.656 | q03 |
| baseline, fused, Physics — MRR | 0.738 | **0.737** | 0.737 | q03 |

## The diagnosis, query by query

Every rank that differs from the score of record, with the same-day control beside it:

| query | lang | fused rank: record → new [control] | |
|---|---|---|---|
| **a09-aufbau-order-hi** (acceptance, Chemistry) | hi | 4 → **5** [4] | lower |
| **a10-ionisation-enthalpy-trend-hi** (acceptance, Chemistry) | hi | 5 → **10** [5] | lower |
| **q03-displacement-vs-path** (baseline) | en | 13 → **15** [15] (unfused alike) | lower |
| h01-pressure-on-equilibrium-hi (held out) | hi | 6 → **1** [6] | higher |
| d06-rosenmund (diagnostic) | hinglish | 2 → **1** [2] | higher |
| a01-photoelectric-stopping-potential (acceptance) | hinglish | 12 → **11** [12] | higher |

**a09 and a10 follow the model.** Both are Hindi, so their fused ranks depend on the CHEAP model's search version,
and the control — Haiku 4.5 the same day, its search versions for these two word for word those of the score of
record — put both back exactly where the score of record has them (4 and 5). The search versions:
- a09 (the order in which electrons fill orbitals): Haiku 4.5 "order of filling electrons in atomic orbitals aufbau
  principle"; Haiku 5.5 "Rules for filling electrons in orbitals of an atom: aufbau principle, Pauli exclusion
  principle and Hund's rule, electronic configuration".
- a10 (why ionisation enthalpy rises across a period): Haiku 4.5 "Why does ionisation enthalpy increase from left
  to right across a period?"; Haiku 5.5 "Why does ionisation enthalpy increase across a period from left to right in
  the periodic table? Periodic trends in ionisation enthalpy, effective nuclear charge and atomic radius".

Claude reads both as the drift ruling B's drift guard is for — the search version adding topics the question does
not contain (Pauli and Hund's rule for a question about the filling order; effective nuclear charge and atomic radius
for a question about the ionisation-enthalpy trend) — for the founder to overrule; the terms may also be read as the
book's own answer to a "why" question. What the reports do show for a10 is limited: its top three are the same three
paragraphs as in the score of record and the control (§3.7.3 ¶2, §3.7.1 ¶21, §3.7.1 ¶27), reordered, so the
paragraphs that took ranks 4–9 above the keyed §3.7.1 ¶15 are in no report and not in the log, and which added term
displaced it is not shown. A one-query live run printing a10's whole fused list would show it.

Haiku 5.5's search versions are longer than Haiku 4.5's throughout: over the 25, a mean of 21.4 words against 12.6
(record) and 12.7 (control); 17 of the 25 carry a second clause after a colon or a question mark, none of Haiku
4.5's do; two name the book ("Biology class 12", "NCERT Class 12 Biology"). Length is not topic addition, and the
same longer versions sit behind two of the three higher ranks — h01 ("Effect of increasing pressure on chemical
equilibrium, Le Chatelier's principle, change in number of gaseous moles") and d06 ("Conversion of acid chloride to
aldehyde: Rosenmund reduction using hydrogen and poisoned palladium catalyst on barium sulphate"). Claude's reading,
for the founder to overrule: the added terms help where they are the book's own for the question and hurt where
they open a neighbouring topic.

**q03 does not follow the model.** It is English, so it is never rewritten in the bar's fused mode and its rank
cannot depend on the CHEAP model; the control moved it the same way (15), and D17's two runs had already moved it
between 15 and 13 with no model change. Claude reads the cause as query-embedding variance (the D17 diagnosis's
reading; the vectors were not compared), for the founder to overrule; the score of record holds its better draw.
Under the founder's rule it still counts, so its six cells are listed.

**Otherwise as in the score of record:** the English-rewrite diagnostic (unfused 0.917, rewritten and fused 1.000,
identical in all three runs); the same two queries miss (d01, the equation gap; h03, the known miss of ruling C —
its returned passages differ, top similarity 0.464 → 0.584); the hand-check — q12 is again the one query grounded
only through its search version, and its top chunk is again phy11-part1 §1.6.1 ¶5, which answers the question
(PASS, Claude's verdict, for the founder to overrule).

## The smoke: PASS on every new tier (step 1); the control's one failure is its id check (step 2)

`AiLiveSmokeTest` on the new tiers: 5 tests, 0 failures. On the old tiers every call succeeded, but
`theConfiguredModelIdsExistAtTheProvider` failed: the provider's models API answered the alias `claude-haiku-4-5`
with the dated id `claude-haiku-4-5-20251001`, and the test compares the two strings exactly
(`AiLiveSmokeTest.java:90-92`). The 5.5 ids came back as configured. Claude reads this as the check's own fault, not
the model's (for the founder to overrule), and proposes a prefix match as a follow-up; nothing is changed here.

The rows the smoke printed — its database is the test container's, so the run log (scratchpad b3c73b13
`d18-t0-regression.log`, lines 120–128 for step 1 and 260–268 for step 2) is their only record — verbatim, in log
order. Step 1, NEW tiers (REASON, embeddings, VISION, CHEAP ×2):

```
ai_calls 5eb552d0-c2de-48a9-8101-1ef846f8283e | smoke | claude-sonnet-5-5 | smoke v1 | status=ok | in=141 out=71 cache_read=0 cache_write=9791 | 4887 ms | 230 paise | request_id=smoke-reason-1bc81ea0-5cfb-4eb6-b506-15fb3b1e05d6
ai_calls 3b5b8b37-0bdb-48d6-b970-9e30a83bdcc8 | embed | global.cohere.embed-v4:0 | — | status=ok | in=7 out=0 cache_read=0 cache_write=0 | 1216 ms | 1 paise | request_id=smoke-embed-4c9199b3-1208-4ea0-a550-ca34947b9961
ai_calls 7ca67ca8-c9fe-4a22-a314-3605735e576c | embed | global.cohere.embed-v4:0 | — | status=ok | in=27 out=0 cache_read=0 cache_write=0 | 454 ms | 1 paise | request_id=smoke-embed-4c9199b3-1208-4ea0-a550-ca34947b9961
ai_calls f9afe223-b35f-49f5-8dcb-e84f9d6d7dcb | smoke | claude-haiku-5-5 | smoke v1 | status=ok | in=245 out=56 cache_read=0 cache_write=9791 | 1175 ms | 12 paise | request_id=smoke-vision-723f57e9-937b-499f-8c36-07da6e78dd38
ai_calls 072f7d74-3f98-4b6a-bcd3-d5881e6b3b20 | smoke | claude-haiku-5-5 | smoke v1 | status=ok | in=241 out=56 cache_read=9791 cache_write=0 | 1011 ms | 2 paise | request_id=smoke-dac8eabd-51df-4929-8426-9ef717f4be9e
ai_calls 8e9ac175-e8e0-412a-9a24-24a13af2ad20 | smoke | claude-haiku-5-5 | smoke v1 | status=ok | in=241 out=56 cache_read=9791 cache_write=0 | 1126 ms | 2 paise | request_id=smoke-dac8eabd-51df-4929-8426-9ef717f4be9e
```

Step 2, OLD tiers (REASON, embeddings, VISION, CHEAP ×2):

```
ai_calls d9c94b81-e50d-41cb-81e3-f4088c8f09da | smoke | claude-sonnet-5 | smoke v1 | status=ok | in=239 out=56 cache_read=0 cache_write=9860 | 1843 ms | 232 paise | request_id=smoke-reason-400824ef-c7b1-40c3-99ed-f9c5f277bd54
ai_calls 969a1e1d-fe78-4b0e-b2a9-8586124081d6 | embed | global.cohere.embed-v4:0 | — | status=ok | in=7 out=0 cache_read=0 cache_write=0 | 1083 ms | 1 paise | request_id=smoke-embed-0e28afac-bc4b-4717-92a7-15bc9601ddd0
ai_calls 006abdfc-f207-4f49-a34d-7aec3c15156c | embed | global.cohere.embed-v4:0 | — | status=ok | in=27 out=0 cache_read=0 cache_write=0 | 415 ms | 1 paise | request_id=smoke-embed-0e28afac-bc4b-4717-92a7-15bc9601ddd0
ai_calls 114531a3-ed48-4e6a-9407-2328a519451f | smoke | claude-haiku-4-5 | smoke v1 | status=ok | in=449 out=50 cache_read=0 cache_write=6595 | 1177 ms | 81 paise | request_id=smoke-vision-bea7abd9-fcfb-492e-bd05-386eefc54ea1
ai_calls ab0ef6b6-fefb-4dad-80d5-f58ac4292de6 | smoke | claude-haiku-4-5 | smoke v1 | status=ok | in=445 out=50 cache_read=6595 cache_write=0 | 1076 ms | 13 paise | request_id=smoke-c9195d6c-756b-4a21-93cd-9691ece75c8b
ai_calls f21735c4-8f56-4835-aa4e-0708b0b36017 | smoke | claude-haiku-4-5 | smoke v1 | status=ok | in=445 out=50 cache_read=6595 cache_write=0 | 1257 ms | 13 paise | request_id=smoke-c9195d6c-756b-4a21-93cd-9691ece75c8b
```

The OLD set restores the pre-T0 settings exactly: REASON's `effort: medium` and `thinking: disabled` were its
pre-T0 values too (application.yml at 0da4675).

## Cost per call, old against new, from the ledger

| call | old | new | |
|---|---|---|---|
| `query_rewrite` (25 calls each, margai_d15 ledger, log lines 1499–1500) | Haiku 4.5, the control: ₹0.158 (₹3.96; the record's were ₹3.95) | Haiku 5.5: **₹0.024** (₹0.60) | 6.6× cheaper |
| the whole retrieval run (130 calls) | ₹5.01 (control), ₹5.00 (record) | **₹1.65** | |
| smoke, CHEAP, cache warm | 13 paise | **2 paise** | |
| smoke, VISION, cache cold (writes the prefix) | 81 paise | **12 paise** | |
| smoke, REASON, cache cold | Sonnet 5: 232 paise | Sonnet 5.5: **230 paise** | the same input, output and cache-write prices, the only classes this call billed; Sonnet 5.5's cache read is half Sonnet 5's |
| embedding | 1 paise | 1 paise | the ledger's one-paisa floor |

## What the 5.5 models do differently

- **Forced tool choice:** Haiku 5.5 accepts it (CHEAP and VISION answered through the forced tool), settling the
  documentation's conflict; Sonnet 5.5 runs `auto`, as D16 measured.
- **Token counts:** the smoke's cached prefix is 9,791 tokens on both 5.5 models against 6,595 on Haiku 4.5 and
  9,860 on Sonnet 5, while the uncached input fell (Haiku 445 → 241, Sonnet 239 → 141); the run does not show
  whether the cached span covers the same content on each model, so the cached figures are not a tokenizer measure.
- **Latency:** Sonnet 5.5 took 4.9 s on the REASON smoke against Sonnet 5's 1.8 s, inside the 20 s budget; it runs
  `between_tools` thinking and the `auto` tool's instruction, which one call cannot separate from the model.
  Haiku 5.5 matched Haiku 4.5 (about 1 s).
- **Output:** Haiku 5.5's search versions are longer (above); the smoke's answers are the same typed record on every
  tier, each call one `ok` row.
- **Temperature:** Haiku 5.5 accepts none but 1 (documentation; the run sent none), so the new run of record is not
  at the temperature 0 ruling B required of D17's rerun.

## Every prompt in use (the founder's 3b)

| prompt | tier | score of record | rerun | result |
|---|---|---|---|---|
| `smoke` v1 | CHEAP, REASON, VISION | the D5 live re-run's pass | step 1 | PASS on all three |
| `query_rewrite` v1 | CHEAP | D17's run of record | step 3 | bar MET; nine MRR cells lower (a09, a10; q03) — **stopped** |
| `ncert_extract` v5 | VISION, profile-pinned (Opus 5 English, Opus 5.5 Hindi) | — | not rerun | the switch does not touch the profiles |
| `ncert_verify` v1 | VISION, profile-pinned (Sonnet 5) | — | not rerun | the same |
| `_protocol` v1 (fragments) | every call | — | through the above | — |

The eval suite has 0 fixtures (it arrives at D23), and the 14-day simulation harness is not built, so neither ran.

## For the founder's choice

The founder's options for a regression: "pin that prompt to the old model via a per-prompt override, or fix it with
a fresh eval". Facts that bear on them:
- **A per-prompt override does not exist yet.** Tiers are chosen per task, `QueryRewriteTask` uses CHEAP, and a
  prompt's configuration holds only its version; pinning it to Haiku 4.5 means building a small configuration key (a
  model per prompt) with a test, an eval stamp and a changelog row.
- **The same-day control is that pin in effect:** the same jar and query file with CHEAP on Haiku 4.5 at temperature
  0, the only CHEAP caller in the run being `query_rewrite` — and it, too, is below the score of record on the six
  baseline cells, by q03 alone.
- **On a fresh eval:** Claude reads ruling B's drift guard (`query_rewrite` v2: no topic the question does not contain)
  as the fix for this cause, for the founder to overrule. Ruling B placed v2 in the D37 design, "evaluated on the D23
  eval set, not on these 19 queries"; the D23 eval set does not exist yet.
- **`QueryRewriteTask` is D18's measuring stand-in.** At D37 the router's one call produces the search version
  (TECH_PLAN §4.2), on CHEAP, and D23's eval and D37 re-measure it there.
- **The net effect on these queries is mixed:** two Chemistry Hindi ranks lower inside the answer set and q03 lower
  (variance); three ranks higher, the held-out line's hit@1 2 → 3 of 4 (MRR 0.542 → 0.750).
