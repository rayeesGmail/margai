# ncert retrieve --rewrite — the misses diagnosed (D18's test, D17's tick)

Written by Claude beside the run report `2026-10-19-ncert-retrieve-rewrite.md` (07:57 IST, request
`pipeline-ncert-retrieve-fce6a719-…`, ₹5.01 by the ledger: 105 query embeddings ₹1.05, 25 `query_rewrite`
calls on claude-haiku-4-5 ₹3.96), as the founder's ruling requires every miss diagnosed — answer key or
retrieval — before the ✅ is judged (DECISIONS 2026-10-18). Nothing below changes a key, a query, the prompt or
a retrieval setting. The run report's read line says "40 concept queries — 15 acceptance, 15 baseline, 6
diagnostic": the four held-out queries were run and scored (their own table and the tick's held-out line) but
left out of that count, which named the D17 sets by hand; fixed in 7d1cb8c, the report stays as generated. The h03
note in eval/retrieval-queries.json was reworded after the run (7356ca2: Claude, not the founder, left ¶8 and ¶10
out of the key); no query text, set or key changed, so the report's input sha256 (1e09b094…) is that of the file at
97601ce, not at HEAD.

## D17's condition: met on the fused run under two readings of Claude's, pending the founder's confirmation

| check | needed | fused | unfused |
|---|---|---|---|
| in the answer set | ≥ 13 of 15 | 15 of 15 | 12 of 15 |
| every Hindi query | all | 6 of 6 | 4 of 6 |
| physics | ≥ 4 of 5 | 5 of 5 | 4 of 5 |
| chemistry | ≥ 4 of 5 | 5 of 5 | 3 of 5 |
| biology | ≥ 4 of 5 | 5 of 5 | 5 of 5 |
| held-out queries | ≥ 3 of 4 | 3 of 4 | 2 of 4 |

The founder's condition (DECISIONS 2026-10-19): the acceptance set meets the bar with fusion on, and the held-out
queries hit at least 3 of 4 — both hold, under two readings of Claude's put to him. The held-out line counts a
query in the answer set at any rank, the acceptance bar's measure — Claude's reading of "hit": h01 enters at rank
6, and at hit@3 the line is 2 of 4. And fused grounding is judged on the higher vector similarity of the question
and its search version — Claude's choice: q12 (baseline, not in the bar), a grounding failure on its own text on
2026-10-18, came back grounded through fusion, and whether h01 grounded only through its search version is not in
this report (the fused per-query table now shows grounding in each mode, 7356ca2). D17 ticks on his confirmation.
No acceptance query misses with fusion on.

## The misses with fusion on

**h03-capacitor-current-leads-hi** (held out, physics, Hindi) — **retrieval; the key holds.** The search version
reads well — "Why does current lead voltage in a capacitor in alternating current" — and it missed unfused too.
What came back: phy12-part1 §7.3 ¶1 (both halves, 0.464; the phasor section's opening), phy12-part2 §14.7 ¶4 (text
only; a rectifier), and §7.5 ¶3 (both, 0.491) — the right section's set-up paragraph, a neighbour of the keyed
derivation ¶4, ¶5, ¶7, none of which entered the answer set. Those three are mostly equations (i = dq/dt, the
cosine as a shifted sine), and where the book states the result it says the current is "ahead of" the voltage;
§7.5 ¶10, the paragraph that says "leads", is the descriptive summary Claude left out of the key, applying to h03
the founder's strict reading of "kyu" for a01 (DECISIONS 2026-10-19), for him to overrule. With 3 of 4 the
held-out line is met without it.

**d01-cannizzaro** (diagnostic, English) — **retrieval, by the equation gap**, unchanged from the first run: an
English query is not rewritten for the bar, and chem12-part2 §8.4 ¶35 names neither benzaldehyde nor sodium
hydroxide; the reaction pass carries to D37 (founder, DECISIONS 2026-10-19).

## What fusion changed

- **No fused query got worse.** Of the 19 non-English queries fused, seven moved, all upward: q09 miss → 1, q12
  miss → 2, a01 miss → 12, a02 2 → 1, a09 miss → 4, a10 miss → 5, h01 miss → 6; the other twelve kept their rank
  (English queries are not fused for the bar).
- **Some hits sit low.** a01 (the founder's photoelectric query) enters the answer set only at rank 12, after the
  observation paragraphs of §11.4 it came back with on the first run; h01 at 6, a10 at 5, a09 at 4. They count
  under the bar's "in the answer set", but the answer step would read them below other passages.
- **Acceptance** MRR 0.733 → 0.802, Hindi 0.583 → 0.742, Hinglish 0.667 → 0.694. **Baseline** 13 → 15 of 15 in
  the answer set, MRR 0.637 → 0.737, its five Hindi queries 3 → 5 of 5 and MRR 0.600 → 0.900. **Held-out** 2 → 3
  of 4.
- **The English acceptance queries rewritten too** (the founder's diagnostic, not part of the bar): 6 of 6 either
  way; hit@1 5/6 → 6/6 and MRR 0.917 → 1.000, the one move being a03 rank 2 → 1; none lower. Six queries are a
  thin sample for the D37 question of a search version for every doubt.

## The search versions, read

All 25 are English. Most name the textbook's concept for the student's words (a09 "aufbau principle", q09
"moment of inertia"). Four go past the question: a02 ends "due to refraction" and a05 "due to thermal expansion" —
the cause each "why" asks for, a conclusion the prompt says not to add — and a13 adds "and blood groups", a15
"through immunology and immune response". h04's version keeps the student's "Okazaki fragments" and adds no
textbook term for it; h04 ranked 1 on its own text, unfused and fused, so this run does not show the rewrite
bridging coaching vocabulary. Fusion is what keeps drift from costing anything — a13 still ranked 1, since
retrieval runs on the question too, never on the rewrite alone.

## The founder's revisit line for a Hindi vector column

His ruling (D17 ruling 3, 2026-10-18, recorded on DECISIONS 2026-10-18 as the confirmation of Claude's row):
"Revisit only if fused retrieval still misses Hindi queries." Fused retrieval missed one Hindi query, h03 — on a book with Hindi rows (phy12-part1) — so the line is
reached by that one query; whether a vector over `text_hi` would reach §7.5's derivation is not measured. It is
his to rule on; Claude's reading is in the TRACKER day log.
