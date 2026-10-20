# ncert retrieve --rewrite, the rerun — the run of record for D17 (hand-check and misses)

Written by Claude beside the run report `2026-10-19-ncert-retrieve-rewrite-2.md` (the founder's rerun on the jar
built at 0efbac6, ₹5.00 by the ledger: 105 query embeddings ₹1.05, 25 `query_rewrite` calls on claude-haiku-4-5
₹3.95). The founder's ruling B (DECISIONS 2026-10-19) makes this rerun the run of record and sets its conditions:
every query that grounded only through its search version is hand-checked for whether its top chunk answers the
original question, the verdicts are put here, any failure stops the tick, and if the fused results differ from
the morning run both are reported. Ruling A reads the held-out "hit" as "in the answer set".

## D17's tick: MET on the run of record

| check | needed | fused | unfused |
|---|---|---|---|
| in the answer set | ≥ 13 of 15 | 15 of 15 | 12 of 15 |
| every Hindi query | all | 6 of 6 | 4 of 6 |
| physics | ≥ 4 of 5 | 5 of 5 | 4 of 5 |
| chemistry | ≥ 4 of 5 | 5 of 5 | 3 of 5 |
| biology | ≥ 4 of 5 | 5 of 5 | 5 of 5 |
| held-out queries | ≥ 3 of 4 | 3 of 4 | 2 of 4 |

## The hand-check (ruling B, condition 2): PASS

Grounding with fusion on, query by query (the report's per-query table): the 21 English queries are grounded by
their question (they are not fused for the bar); of the 19 non-English queries, 18 are grounded by both texts and
**one, q12, only by its search version**. h01, whose fused hit decides the held-out line, was grounded by its own
question unfused and by both texts fused, so the stricter reading of grounding would not have changed that line.

**q12-dimensional-analysis-hi** (baseline) — "विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या
नहीं" (what dimensional analysis tells us about an equation, and what it does not). Search version: "What
dimensional analysis can and cannot tell about an equation". Top chunk: phy11-part1 ch 1 §1.6.1 ¶5 — a test of
consistency of dimensions "tells us no more and no less than a test of consistency of units"; "if an equation fails
this consistency test, it is proved wrong, but if it passes, it is not proved right"; "a dimensionally correct
equation need not be actually an exact (correct) equation, but a dimensionally wrong … equation must be wrong".
**Verdict: answers the original question** — both halves of it, what the test can tell (a dimensionally wrong
equation is wrong) and what it cannot (a dimensionally correct one is not thereby right). The keyed paragraphs
(§1.6.2 ¶9, §1.6 ¶1) come back too, the first at rank 2.

No query fails the hand-check, so the tick is not stopped.

## The misses with fusion on — the same two as the morning run

**h03-capacitor-current-leads-hi** (held out) — the known miss of ruling C ("equation-heavy passage, 'ahead of' vs
'leads'"); retrieval, the key holding (§7.5 ¶4, ¶5, ¶7, with ¶8 and ¶10 left out by Claude, applying the founder's
strict reading of "kyu" for a01). Which side says which, as Claude reads the rows: the keyed ¶7 says "the current is
pi/2 ahead of voltage"; "leads" is the search version's word (the student's Hindi says "वोल्टता से आगे क्यों चलती
है", goes ahead of the voltage); and the book's own "leads" is in ¶10, outside the key ("in the case of a capacitor,
the current leads the voltage by pi/2", read from the pipeline database row).
**d01-cannizzaro** (diagnostic, English) — retrieval, by the equation gap; the reaction pass carries to D37.

## Against the morning run (both reported, the rerun of record)

- **Every fused and unfused rank is the same but one**: q03-displacement-vs-path (baseline, English, so not fused)
  came back at rank 13 in both modes, against 15 in the morning run. An English query runs the same retrieval in
  both runs, but its text is embedded afresh each run, and the top-3 listings show the same paragraphs' similarities
  moving by 0.001–0.002 between the two runs on English queries (q01 0.522 → 0.523, q07 0.381 → 0.382, d01 0.529 →
  0.531): the query vectors are not bit-identical from call to call, and a shift that size can reorder paragraphs
  ranked 13th–15th, whose similarities sit close together. That is Claude's reading of the listings; the vectors
  themselves were not compared. It is inside the answer set either way.
- **Four search versions differ** although the CHEAP tier runs at temperature 0 — three reworded and one only
  capitalised: q13 dropped "definition", a02 now reads "…appear raised due to refraction of light at the water
  surface", a04 "total internal reflection and its applications optical fibres", and a07 differs only in its
  capital first letter. None of the four changed rank.
  Temperature 0 does not make the model's wording exactly repeatable; fusion keeps the variation from moving a
  result here. a02's new version still adds a cause the question does not state, the drift ruling B's v2 guard
  is for.
- **Four third-place passages in the top-3 listings are different**, none of them a keyed paragraph and none
  changing a rank: q13 fused (§4.6 ¶1 → §4.5 ¶1) and a02 fused (phy12-part2 §14.4 ¶12 → phy11-part2 §9.6.2 ¶9),
  whose search versions were reworded; a15 fused (bio12 §7.2 ¶1 → §7.2.2 ¶1), whose search version was not; and
  d01, English, in both modes (chem12-part2 §6.7.2 ¶8 → chem11-part2 §9.5.4 ¶3, similarity 0.500 → 0.501) — the
  last two by the same small embedding variance as q03's.
- The tick table, the acceptance, diagnostic and held-out tables, the English diagnostic (hit@1 5/6 → 6/6) and
  the misses are as in the morning run; the baseline's MRR moves by q03 alone, up a thousandth (0.637 → 0.638
  unfused, 0.737 → 0.738 fused; English 0.656 → 0.657).

## Grounding in the log, the production record (ruling B, condition 1)

The generated report shows grounding for 59 of the run's 80 retrievals: the jar at 0efbac6 printed no grounding
column in the unfiltered table (15 acceptance retrievals) or the English-rewritten table (6), and the report stays
as generated. The run's log has the other 21. It carries one `retrieval … grounded_via=…` line per retrieval, 80 in
all, agreeing with the report where both show it:
- the 40 filtered unfused retrievals: 39 `question` and 1 `none` (q12, whose own vector stays under the floor — a
  grounding failure unfused, grounded fused by its search version alone, the query hand-checked above);
- the 15 unfiltered acceptance retrievals: 15 `question`;
- the 19 fused non-English retrievals: 18 `both` and 1 `search_version` (q12);
- the 6 English acceptance queries rewritten for the diagnostic: 6 `both`, so none of them needed the hand-check.

Claude reads ruling B's condition 1 ("in eval reports and in production logs") as met for this run by those log
lines, transcribed here, for the founder to overrule. From the next run the report prints all 80 itself (fe831e7). `none` is
Claude's name for a grounding failure, added beside the founder's three values (question | search_version | both) so
that every retrieval logs one.

The lines follow, so that the evidence outlives the scratchpad log
(`/private/tmp/claude-501/-Users-rayeesahmed-Documents-margai/b3c73b13-4707-4adf-99c6-be7ea5a14d09/scratchpad/d18-retrieve-rewrite-2.log`).
Each is quoted verbatim after the log prefix (the date 2026-10-19, `+05:30 INFO 83910 --- [margai-server] [main]
c.margai.ai.retrieval.HybridRetriever : retrieval`) and the request id, pipeline-ncert-retrieve-c8344510-5995-43dc-8dfc-e8ceba226af4 (the report's line 23), which is the
same on all 80. The query id
after `#` is Claude's: the run's retrievals matched, in order, to the order of the report's tables.

Filtered, unfused (40):
```
23:01:10.888 grounded_via=question search_version=false passages=13 node_filter_dropped=false   # q01-significant-figures
23:01:14.706 grounded_via=question search_version=false passages=15 node_filter_dropped=false   # q02-speed-vs-velocity
23:01:15.832 grounded_via=question search_version=false passages=16 node_filter_dropped=false   # q03-displacement-vs-path
23:01:16.251 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # q04-friction-contact-area
23:01:16.637 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # q05-inertia-bus
23:01:17.061 grounded_via=question search_version=false passages=14 node_filter_dropped=false   # q06-car-turning-flat-road
23:01:20.636 grounded_via=question search_version=false passages=16 node_filter_dropped=false   # q07-zero-work
23:01:21.159 grounded_via=question search_version=false passages=13 node_filter_dropped=false   # q08-rotational-kinetic-energy
23:01:26.718 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # q09-moment-of-inertia-hi
23:01:30.333 grounded_via=question search_version=false passages=15 node_filter_dropped=false   # q10-angular-momentum-conservation
23:01:31.071 grounded_via=question search_version=false passages=16 node_filter_dropped=false   # q11-escape-speed
23:01:32.067 grounded_via=none search_version=false passages=0 node_filter_dropped=false   # q12-dimensional-analysis-hi
23:01:32.858 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # q13-inertia-hi
23:01:36.221 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # q14-momentum-conservation-hi
23:01:36.956 grounded_via=question search_version=false passages=14 node_filter_dropped=false   # q15-gravity-with-depth-hi
23:01:40.385 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a01-photoelectric-stopping-potential
23:01:44.120 grounded_via=question search_version=false passages=13 node_filter_dropped=false   # a02-apparent-depth-hi
23:01:44.790 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a03-comb-attracts-paper
23:01:48.981 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a04-optical-fibre
23:01:52.833 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a05-thermal-expansion-lid-hi
23:01:53.502 grounded_via=question search_version=false passages=15 node_filter_dropped=false   # a06-sn1-racemisation
23:01:56.111 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a07-catalyst-equilibrium
23:01:59.282 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a08-boiling-point-elevation
23:01:59.924 grounded_via=question search_version=false passages=8 node_filter_dropped=false   # a09-aufbau-order-hi
23:02:01.996 grounded_via=question search_version=false passages=8 node_filter_dropped=false   # a10-ionisation-enthalpy-trend-hi
23:02:05.134 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a11-double-fertilisation
23:02:05.554 grounded_via=question search_version=false passages=8 node_filter_dropped=false   # a12-rh-incompatibility
23:02:09.062 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a13-dna-fingerprinting
23:02:10.669 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a14-light-reactions-site-hi
23:02:15.417 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a15-vaccination-hi
23:02:18.676 grounded_via=question search_version=false passages=15 node_filter_dropped=false   # d01-cannizzaro
23:02:19.278 grounded_via=question search_version=false passages=15 node_filter_dropped=false   # d02-aldol
23:02:19.909 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # d03-etard
23:02:21.237 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # d04-reimer-tiemann
23:02:22.021 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # d05-gabriel
23:02:22.744 grounded_via=question search_version=false passages=14 node_filter_dropped=false   # d06-rosenmund
23:02:23.452 grounded_via=question search_version=false passages=8 node_filter_dropped=false   # h01-pressure-on-equilibrium-hi
23:02:24.020 grounded_via=question search_version=false passages=13 node_filter_dropped=false   # h02-carbocation-stability
23:02:24.769 grounded_via=question search_version=false passages=14 node_filter_dropped=false   # h03-capacitor-current-leads-hi
23:02:25.183 grounded_via=question search_version=false passages=14 node_filter_dropped=false   # h04-okazaki-lagging-strand
```

Unfiltered acceptance (15):
```
23:02:25.898 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a01-photoelectric-stopping-potential
23:02:26.308 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a02-apparent-depth-hi
23:02:26.831 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a03-comb-attracts-paper
23:02:27.247 grounded_via=question search_version=false passages=13 node_filter_dropped=false   # a04-optical-fibre
23:02:27.794 grounded_via=question search_version=false passages=10 node_filter_dropped=false   # a05-thermal-expansion-lid-hi
23:02:28.817 grounded_via=question search_version=false passages=15 node_filter_dropped=false   # a06-sn1-racemisation
23:02:33.129 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a07-catalyst-equilibrium
23:02:36.030 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a08-boiling-point-elevation
23:02:37.261 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a09-aufbau-order-hi
23:02:38.378 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a10-ionisation-enthalpy-trend-hi
23:02:39.387 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a11-double-fertilisation
23:02:43.402 grounded_via=question search_version=false passages=12 node_filter_dropped=false   # a12-rh-incompatibility
23:02:44.501 grounded_via=question search_version=false passages=13 node_filter_dropped=false   # a13-dna-fingerprinting
23:02:45.777 grounded_via=question search_version=false passages=11 node_filter_dropped=false   # a14-light-reactions-site-hi
23:02:46.920 grounded_via=question search_version=false passages=14 node_filter_dropped=false   # a15-vaccination-hi
```

Fused, non-English (19):
```
23:03:21.022 grounded_via=both search_version=true passages=13 node_filter_dropped=false   # q09-moment-of-inertia-hi
23:03:26.808 grounded_via=search_version search_version=true passages=17 node_filter_dropped=false   # q12-dimensional-analysis-hi
23:03:28.448 grounded_via=both search_version=true passages=12 node_filter_dropped=false   # q13-inertia-hi
23:03:35.353 grounded_via=both search_version=true passages=13 node_filter_dropped=false   # q14-momentum-conservation-hi
23:03:37.461 grounded_via=both search_version=true passages=16 node_filter_dropped=false   # q15-gravity-with-depth-hi
23:03:41.958 grounded_via=both search_version=true passages=12 node_filter_dropped=false   # a01-photoelectric-stopping-potential
23:03:43.321 grounded_via=both search_version=true passages=16 node_filter_dropped=false   # a02-apparent-depth-hi
23:03:50.807 grounded_via=both search_version=true passages=16 node_filter_dropped=false   # a05-thermal-expansion-lid-hi
23:03:55.646 grounded_via=both search_version=true passages=19 node_filter_dropped=false   # a06-sn1-racemisation
23:04:05.552 grounded_via=both search_version=true passages=11 node_filter_dropped=false   # a09-aufbau-order-hi
23:04:06.822 grounded_via=both search_version=true passages=12 node_filter_dropped=false   # a10-ionisation-enthalpy-trend-hi
23:04:10.384 grounded_via=both search_version=true passages=13 node_filter_dropped=false   # a11-double-fertilisation
23:04:11.419 grounded_via=both search_version=true passages=13 node_filter_dropped=false   # a14-light-reactions-site-hi
23:04:12.642 grounded_via=both search_version=true passages=13 node_filter_dropped=false   # a15-vaccination-hi
23:04:14.058 grounded_via=both search_version=true passages=19 node_filter_dropped=false   # d06-rosenmund
23:04:15.560 grounded_via=both search_version=true passages=15 node_filter_dropped=false   # h01-pressure-on-equilibrium-hi
23:04:18.827 grounded_via=both search_version=true passages=15 node_filter_dropped=false   # h02-carbocation-stability
23:04:23.194 grounded_via=both search_version=true passages=14 node_filter_dropped=false   # h03-capacitor-current-leads-hi
23:04:27.885 grounded_via=both search_version=true passages=15 node_filter_dropped=false   # h04-okazaki-lagging-strand
```

English acceptance, rewritten and fused for the diagnostic (6):
```
23:04:31.494 grounded_via=both search_version=true passages=15 node_filter_dropped=false   # a03-comb-attracts-paper
23:04:32.715 grounded_via=both search_version=true passages=15 node_filter_dropped=false   # a04-optical-fibre
23:04:34.921 grounded_via=both search_version=true passages=15 node_filter_dropped=false   # a07-catalyst-equilibrium
23:04:36.183 grounded_via=both search_version=true passages=16 node_filter_dropped=false   # a08-boiling-point-elevation
23:04:38.035 grounded_via=both search_version=true passages=9 node_filter_dropped=false   # a12-rh-incompatibility
23:04:38.952 grounded_via=both search_version=true passages=14 node_filter_dropped=false   # a13-dna-fingerprinting
```
