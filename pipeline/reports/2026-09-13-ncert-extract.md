# margai-pipeline ncert extract

- run: 2026-09-13 11:14 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content
page tiles: 2 (each page sent as overlapping bands, unscaled)
request id: pipeline-ncert-extract-1db9e165-aaaa-4ba6-af1c-e96da24d53bc

## text layer (authoritative for characters where it is legible)

| chapter | disposition | legibility |
|---|---|---|
| 7 | fed as the character authority | 0.389 |

## end-of-chapter apparatus (never sent to the model)

| chapter | starts at | heading | pages not sent |
|---|---|---|---|
| 7 | page 13 | SUMMARY | 5 |

## pages per chapter

| chapter | pages | called | paragraphs |
|---|---|---|---|
| 7 | 17 | 12 | 113 |

## total

| pages in jsonl | called this run | already done | apparatus | paragraphs |
|---|---|---|---|---|
| 17 | 12 | 0 | 5 | 113 |

## characters that differ from the page's text layer — adjudicate these

checked: 12 of the 12 page(s) called this run
- ch 7 p3 §7.3 ¶1: 'V^2' is not on the page (as 'V2')
- ch 7 p4 §7.3 ¶2: 'R_m' is not on the page (as 'Rm')
- ch 7 p4 §7.3 ¶2: 'R_E' is not on the page (as 'RE')
- ch 7 p4 §7.3 ¶2: 'R_m^2' is not on the page (as 'Rm2')
- ch 7 p4 §7.3 ¶2: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p4 §7.3 ¶9: 'Gm_2' is not on the page (as 'Gm2')
- ch 7 p4 §7.3 ¶9: 'r_21^2' is not on the page (as 'r212')
- ch 7 p4 §7.3 ¶9: 'r_21' is not on the page (as 'r21')
- ch 7 p4 §7.3 ¶9: 'Gm_3' is not on the page (as 'Gm3')
- ch 7 p4 §7.3 ¶9: 'r_31^2' is not on the page (as 'r312')
- ch 7 p4 §7.3 ¶9: 'r_31' is not on the page (as 'r31')
- ch 7 p4 §7.3 ¶9: 'Gm_4' is not on the page (as 'Gm4')
- ch 7 p4 §7.3 ¶9: 'r_41^2' is not on the page (as 'r412')
- ch 7 p5 §7.3 ¶15: '2Gm^2' is not on the page (as '2Gm2')
- ch 7 p5 §7.3 ¶17: '4Gm^2' is not on the page (as '4Gm2')
- ch 7 p5 §7.3 ¶19: '2Gm^2' is not on the page (as '2Gm2')
- ch 7 p6 §7.4 ¶3: 'd^2' is not on the page (as 'd2')
- ch 7 p6 §7.4 ¶4: 'd^2' is not on the page (as 'd2')
- ch 7 p6 §7.5 ¶4: 'R_E^3' is not on the page (as 'RE3')
- ch 7 p6 §7.5 ¶4: 'r^3' is not on the page (as 'r3')
- ch 7 p7 §7.5 ¶5: 'r^2' is not on the page (as 'r2')
- ch 7 p7 §7.5 ¶5: 'r^3' is not on the page (as 'r3')
- ch 7 p7 §7.5 ¶6: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p7 §7.5 ¶7: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p7 §7.6 ¶3: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p9 §7.8 ¶2: 'V_f^2' is not on the page (as 'Vf2')
- ch 7 p9 §7.8 ¶3: 'V_i^2' is not on the page (as 'Vi2')
- ch 7 p10 §7.8 ¶4: 'V_i^2' is not on the page (as 'Vi2')
- ch 7 p10 §7.8 ¶4: 'M_E' is not on the page (as 'ME')
- ch 7 p10 §7.8 ¶4: 'V_f^2' is not on the page (as 'Vf2')
- ch 7 p10 §7.8 ¶5: 'V_i^2' is not on the page (as 'Vi2')
- ch 7 p10 §7.8 ¶5: 'M_E' is not on the page (as 'ME')
- ch 7 p10 §7.8 ¶6: 'M_E' is not on the page (as 'ME')
- ch 7 p10 §7.8 ¶7: 'M_E' is not on the page (as 'ME')
- ch 7 p10 §7.8 ¶8: 'M_E' is not on the page (as 'ME')
- ch 7 p10 §7.8 ¶8: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p10 §7.8 ¶14: 'v^2' is not on the page (as 'v2')
- ch 7 p10 §7.8 ¶16: 'v^2' is not on the page (as 'v2')
- ch 7 p11 §7.9 ¶2: 'V^2' is not on the page (as 'V2')
- ch 7 p11 §7.9 ¶3: 'V^2' is not on the page (as 'V2')
- ch 7 p11 §7.9 ¶3: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p11 §7.9 ¶4: 'T_0' is not on the page (as 'T0')
- ch 7 p11 §7.9 ¶5: 'T_0' is not on the page (as 'T0')
- ch 7 p11 §7.9 ¶7: 'R^3' is not on the page (as 'R3')
- ch 7 p11 §7.9 ¶7: '10^18' is not on the page (as '1018')
- ch 7 p11 §7.9 ¶8: 'T_M^2' is not on the page (as 'TM2')
- ch 7 p11 §7.9 ¶8: 'T_E^2' is not on the page (as 'TE2')
- ch 7 p11 §7.9 ¶8: 'R_MS^3' is not on the page (as 'RMS3')
- ch 7 p11 §7.9 ¶8: 'R_ES^3' is not on the page (as 'RES3')
- ch 7 p12 §7.9 ¶11: 'R_E^2' is not on the page (as 'RE2')
- ch 7 p12 §7.10 ¶1: 'v^2' is not on the page (as 'v2')

## pages whose paragraph count does not match the page's shape

checked: 12 of the 12 page(s) called this run
- ch 7 p10: 13 paragraphs against 4 blocks of prose on the page — cut too finely?
- ch 7 p12: 13 paragraphs against 3 blocks of prose on the page — cut too finely?

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 12 | 60116 | 14691 | 63580 | 5780 | ₹13.30 |
jsonl: extract/phy11-part1/en.jsonl (17 pages, 0 of them from earlier runs)
