# D18 T2 — the Week-3 gate, read book by book

Written by Claude beside the probe report `2026-10-20-ncert-retrieve-gate.md` (`ncert retrieve --gate week3`, run
by the founder at 10:19 IST on the jar built at the T2 commit 8fda8df, Claude Haiku 5.5 writing the search
versions) and the gate's run of record `2026-10-20-ncert-retrieve-rewrite.md` (T0's fused rerun on Haiku 5.5;
founder, DECISIONS 2026-10-20). The rule is the founder's (DECISIONS 2026-10-20): every book-level gap in D17's run
of record gets one fresh probe, written with its key from the book rows and committed before the run, in the
held-out set and never used for tuning; the gate is read book by book; a probe miss is diagnosed and stops for the
founder's ruling, with nothing adjusted to make a probe pass.

The probes and keys were committed at 10:03 IST (8fda8df), before the run, and are unchanged since: the run read
`eval/retrieval-queries.json` at sha256 `f94fd279…7e881a`, the file's hash at HEAD.

## Verdict: PASS — six of six probes in the answer set; every book searchable in the languages the gate names

The gate reads "searchable in both languages for the six books with Hindi text; for Chemistry, Hindi queries reach
the English rows" (founder, DECISIONS 2026-10-18). No probe missed, so nothing stopped the read. The verdict is
Claude's reading of that rule, for the founder to overrule.

## The probes

| probe | gap it closes | rank unfused | rank fused | grounded via, fused |
|---|---|---|---|---|
| h05-lenz-opposition-hi | phy12-part1, Hindi | 4 | 7 | both |
| h06-capillary-rise | phy11-part2, English | 1 | 1 | question |
| h07-ethyne-acidic | chem11-part2, English | 1 | 1 | question |
| h08-redox-electron-transfer-hi | chem11-part2, Hindi reaching the English rows | 3 | 1 | both |
| h09-transition-metal-colour-hi | chem12-part1, Hindi reaching the English rows | 8 | 1 | both |
| h10-egg-white-boiling-hi | chem12-part2, Hindi reaching the English rows | 1 | 1 | both |

No probe was grounded only through its search version, so no hand-check is due. The run made 18 calls for ₹0.32
from the ledger: four `query_rewrite` calls on `claude-haiku-5-5`, ledgered under feature `eval` (₹0.18), and
fourteen embeddings on `global.cohere.embed-v4:0` (₹0.14). These are the run script's ledger lines (feature, model,
calls, ₹), quoted because the script's log is not kept:

```
embed|global.cohere.embed-v4:0|14|0.14
eval|claude-haiku-5-5|4|0.18
```

## Book by book

A book counts in a language when at least one query in that language returns one of its key paragraphs **in that
book** within the fused answer set. "Hindi" means a Devanagari query; Hinglish is shown beside the Hindi column but
not counted. That is how D17's gap list was drawn: it named chem12-part2 as lacking Hindi although a06 and d06 are
Hinglish (Claude's reading, for the founder to overrule). Ranks are fused. Every query below is from the run of
record unless marked as a probe.

| book | subject | English | Hindi (Devanagari) | Hinglish, beside |
|---|---|---|---|---|
| phy11-part1 | physics | q01–q08, q10, q11: all ten in the set, six at rank 1 | q09 1, q12 2, q13 1, q14 1, q15 1 | — |
| phy11-part2 | physics | **h06 1** (probe) | a05 1 | — |
| phy12-part1 | physics | a03 2 | **h05 7** (probe); h03 miss | — |
| phy12-part2 | physics | a04 1 | a02 1 | a01 11 |
| chem11-part1 | chemistry | a07 3 (its chem11-part1 key; its chem12-part1 key is at rank 1) | a09 5, a10 10, h01 1 | — |
| chem11-part2 | chemistry | **h07 1** (probe) | **h08 1** (probe) | h02 1 |
| chem12-part1 | chemistry | a08 1, a07 1 | **h09 1** (probe) | — |
| chem12-part2 | chemistry | d02, d03, d04, d05 1 (diagnostic set); d01 miss | **h10 1** (probe) | a06 1, d06 1 |
| bio11 | biology | a12 1 | a14 1 | — |
| bio12 | biology | a13 1 | a15 1 | a11 1, h04 1 |

The six books with Hindi text (phy11-part1, phy11-part2, phy12-part1, phy12-part2, bio11, bio12) each have an
English query and a Hindi query in the answer set. The four Chemistry books, which have no Hindi text loaded (their
Hindi editions moved to the beta period; DECISIONS 2026-10-18), each have a Devanagari Hindi query reaching their
English rows.

## What the founder should see before accepting

1. **chem12-part2's English line rests on the diagnostic set.** The acceptance set has no chem12-part2 English query.
   d02–d05 (organic name reactions, written for the D17 reaction question) carry the line at rank 1. D17's gap list,
   which the founder ruled on, did not name chem12-part2 English as a gap, so it got no probe.
2. **Two misses remain, both carried from D17 and neither a probe.**
   - h03 (held-out, phy12-part1 Hindi, the capacitor's leading current) is the known miss of ruling C. The Hindi
     vector column is at D23 (DECISIONS 2026-10-19).
   - d01 (diagnostic, chem12-part2 English, Cannizzaro) is the reaction-equation gap. The reaction pass carries to
     the D37 answer checks (founder, DECISIONS 2026-10-19).
3. **h05 fell from 4 unfused to 7 fused.** It stayed inside the answer set. Rank is a diagnostic, not a gate (ruling
   A, DECISIONS 2026-10-19: "Keep reporting rank and top-3 as diagnostics, not gates"). Applying that to fused
   against unfused in one run is Claude's reading.
   - The fused top three are the statement of Lenz's law and its sign (§6.5 ¶2, ¶3) and §6.4 ¶2 (Experiment 6.1's
     moving magnet).
   - The key is the energy argument (§6.5 ¶5–¶6). It is Claude's key, applying the founder's strict reading of "kyu"
     for a01 (D17 ruling 4), for the founder to overrule. It reached the answer set but not the top three.
   - The report prints only the top three for a hit, so it does not show which key paragraph sat at 7 or what filled
     ranks 4–6.
4. **Drift in the search versions.** The founder ruled drift is recorded and measured at D23, with no edit to
   `query_rewrite` v1 (DECISIONS 2026-10-20).
   - h05's version adds "Class 12", a class name.
   - h09's adds crystal field splitting, chapter 5's topic. The rank-1 hit is h09's chapter-5 key.
   - h10's adds the four levels of protein structure. Ranks 2 and 3 are §10.2.3.
   - In each case the key still ranked as shown above.
5. **One run, sampled.** The 5.5 models run at temperature 1 only (founder, DECISIONS 2026-10-20). This is one draw
   per probe. The founder's default — two runs per set, judged on the worse run, both reported — is to be proposed
   in the D23 plan and was not applied here, so a second draw could rank differently.
6. **D18 is not ticked by this.** D18 is T0, T1 and T2 (DECISIONS 2026-10-20), and T0 is done. PLAN D18 and
   TECH_PLAN §15.12 put T1's readiness at the founder's smoke of CHEAP and REASON. D18 ticks when that smoke passes
   (Claude's reading, for the founder to overrule). The ECS task role's Bedrock statement is a deployment
   prerequisite carried to F8, which creates the task role; it is not a tick condition. The founder ran the smoke
   at 10:27 IST and Bedrock refused both calls: `AccessDeniedException: anthropic.claude-haiku-5-5 is not available
   for this account`, and the same for `anthropic.claude-sonnet-5-5`. D18 therefore stays open for the founder's
   ruling.
