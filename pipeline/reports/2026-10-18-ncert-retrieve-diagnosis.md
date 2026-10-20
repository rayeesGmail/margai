# ncert retrieve — the misses diagnosed (D17)

Written by Claude beside the run report `2026-10-18-ncert-retrieve.md` (22:35 IST, request
`pipeline-ncert-retrieve-cb281028-…`, 51 query embeddings, ₹0.51), as the founder's bar requires: every miss
diagnosed — answer key or retrieval — before the ✅ is judged (DECISIONS 2026-10-18). Each diagnosis is read from
the rows; nothing below changes a key, a query or a retrieval setting.

## The verdict on the bar: NOT MET

| check | needed | got | met |
|---|---|---|---|
| in the answer set | ≥ 13 of 15 | 12 of 15 | no |
| every Hindi query | all | 4 of 6 | no |
| physics | ≥ 4 of 5 | 4 of 5 | yes |
| chemistry | ≥ 4 of 5 | 3 of 5 | no |
| biology | ≥ 4 of 5 | 5 of 5 | yes |

The two Chemistry Hindi misses fail the Hindi check and the Chemistry check whatever is said of a01 below, so the
verdict does not rest on any key judgement.

## The acceptance misses

**a01-photoelectric-stopping-potential** (physics, Hinglish, the founder's) — **retrieval; the key holds.** The
key is Einstein's explanation, phy12-part2 §11.6 ¶2, ¶4, ¶8. What came back first is the observation section:
§11.4.2 ¶4 (similarity 0.606: higher intensity raises the saturation current, "the stopping potential remains the
same … the maximum kinetic energy … is independent of intensity"), §11.4.3 ¶1 (0.538: "greater the frequency …
greater is the maximum kinetic energy … we need greater retarding potential"), §11.4.3 ¶12 (0.596), all found by
both halves; none of the keyed §11.6 paragraphs (¶2, ¶4, ¶8) entered the answer set. The query's own words — stopping potential, frequency,
intensity — are the observation section's words, while §11.6 explains in photons, quanta and K_max. The returned
paragraphs answer *what* happens and give the kinetic-energy step of *why*; the photon reason the question asks
for is §11.6's alone. Whether §11.4.2 ¶4 and §11.4.3 ¶1 are better answers under the D15 rule (DECISIONS
2026-09-20) is put to the founder; Claude reads them as the observation, not the reason, and keeps the key; a
widened key would still leave the bar unmet.

**a09-aufbau-order-hi** (chemistry, Hindi, Claude's) — **retrieval; the key holds.** Rank 1 is chem11-part1
§2.6.4 ¶1 (vector only, 0.443), the opening of the right section: it names the aufbau principle and its basis but
not the order the question asks for, which is ¶2 (increasing energy) and ¶3 (1s, 2s, 2p …). A neighbour is not a
better answer, so the D15 rule (DECISIONS 2026-09-20) does not widen the key. Every result came from the vector
half: no Chemistry book has Hindi rows (option B moved them to the beta period), so the text half had nothing to
match a Devanagari query against.

**a10-ionisation-enthalpy-trend-hi** (chemistry, Hindi, Claude's) — **retrieval; the key holds.** Vector half
only, low similarities (0.379, 0.377, 0.357), two of the top three from other chapters (chem11-part1 §6.2 ¶3,
chem12-part1 §4.3.7 ¶7) and the third the right section's electron-gain paragraph (§3.7.1 ¶21); ¶15, which
answers it, did not enter the answer set. Same cause as a09: cross-lingual vector alone, no Hindi text to match.

## The other misses (they decide nothing)

**q09-moment-of-inertia-hi**, **q12-dimensional-analysis-hi** (baseline, phy11-part1) — **retrieval**, as at D15.
q09 came back with nothing from §6.9 — phy11-part2 §10.6 ¶1 (vector, 0.363), §11.11 ¶2 (text), phy11-part1 §4.1 ¶2
(vector, 0.309) — so the key is not in question. q12 is a grounding failure: no vector match reached the 0.30 floor, so the run returned nothing although the text
half matched paragraphs of the neighbouring §1.6.1 (a ₹0 probe of the same tsquery; neither keyed paragraph was in
its top eight).

**d01-cannizzaro** (diagnostic) — **retrieval, by the equation gap.** chem12-part2 §8.4 ¶35 explains the
Cannizzaro reaction in prose — "aldehydes which do not have an alpha-hydrogen … on heating with concentrated
alkali" — and names neither benzaldehyde nor sodium hydroxide; the substances a student names are in the reaction
equation the transcription omits (TRACKER PARKED "A reaction pass for the organic chapters"). The other five
diagnostics, whose prose carries the reagents' names, all came back (four at rank 1, Rosenmund at rank 2).

## What the run shows beyond the misses

- **Hindi hits ride on the Hindi text; grounding does not.** Every Hindi acceptance hit on the six Hindi books
  (a02, a05, a14, a15) was found by both halves, the Hindi `tsv` beside the vector; both Chemistry Hindi queries had
  the vector half alone and missed. Cross-lingual vector similarities run low — 0.25 to 0.46 across the Hindi
  queries' top three passages — around the 0.30 floor. Grounding is judged on the vector alone, so a `tsv` match
  cannot lift a query the vector leaves under the floor (q12).
- **The subject filter changed no rank.** The unfiltered acceptance run is identical to the filtered one, query by
  query, so on this set a router's missing subject would have cost nothing.
- **The baseline improved, though the book in the address only makes its scoring stricter.** D15's fifteen now score hit@1 9/15, hit@3 10/15, MRR
  0.637 (Hindi 0.600), against D15's 7/15, 10/15, 0.593 (Hindi 0.500); phy11-part1 has Hindi rows since D16, so the
  Hindi `tsv` half now contributes.
