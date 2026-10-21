# margai-pipeline pyq extract

- run: 2026-10-20 19:35 IST
- input: ../pipeline/inputs/pyq-papers.yaml
- sha256: 628d3c5e3af699dc978e20932d0e8e1c29c51839cdef5d29fc839ae35148116c
- read: nothing yet
- result: ok
paper: neet-2025-45 (NEET 2025, booklet code 45, 2025-05-04)
content store: s3://margai-beta-content
transcriber: claude-opus-5-5, pyq_extract.v1 (the effort is the visionopus55 profile's)
booklet: source/neet-pyq-sources/2025/neet-2025-45-booklet-hi-en.pdf (hi-en; pages 2–46, 45 in all)
text layer: withheld — a bilingual booklet's layer interleaves its Hindi
request id: pipeline-pyq-extract-8de5c129-b00d-44d1-9eb0-029bb58f87d3

## pages

| in the register | read this run | from earlier runs | in the jsonl |
|---|---|---|---|
| 45 | 45 | 0 | 45 |

## questions per section — against the count the booklet declares

| subject | section | questions | declared | with a figure |
|---|---|---|---|---|
| physics | — | 45 | 45 | 11 |
| chemistry | — | 45 | 45 | 9 |
| biology | — | 90 | 90 | 4 |

## flags — every one is checked against the page image before the file is approved

- Q1: figure at stem on page 2 — graph: y-axis vertical, x-axis horizontal; a curved liquid surface starting on the y-axis, dipping slightly then rising to meet a vertical line at x = L; angle theta_0 marked between the curve and a dashed horizontal line at the meeting point
- Q6: figure at stem on page 3 — circuit: nodes A, B, C, D, E, F. A–B 5Ω; B–C 2.5Ω; A–D diagonal 6Ω; A–F wire; F–E 3Ω; E–D 1.5Ω; C–D wire; from B a branch with 1.5Ω going right then down through 5.5Ω to a 5 V battery; battery connected back to E through 1/3 Ω.
- Q7: figure at stem on page 4 — Dashed vertical circle centred at O; bob of mass m at bottom on string of length l (marked with double arrow) given horizontal velocity v_0 to the right; point P on circle in upper right, string OP making angle theta above horizontal dashed line through O.
- Q8: figure at stem on page 4 — Logic circuit: inputs A and B into a NOR gate (top); inputs B and A into a NAND gate (bottom); outputs of NOR and NAND feed an AND gate giving output Y.
- Q11: figure at stem on page 5 — circuit segment from A to B: current i arrow from A, inductor labelled 1H, cell labelled 5V, resistor labelled 2Ω, then point B
- Q19: figure at stem on page 7 — Three rods in series between two hatched walls: left wall at 3T, right wall at T; rods labelled 2K, K, 2K from left to right; left junction marked T_1, right junction marked T_2.
- Q23: figure at option:1 on page 8 — Two stacked graphs against t: upper ω(t) starts at a positive value and rises, levelling off to a constant; lower A(t) is a horizontal straight line (constant).
- Q23: figure at option:2 on page 8 — Two stacked graphs against t: upper ω(t) starts at a positive value and rises, levelling off; lower A(t) starts at a value and decreases, levelling off.
- Q23: figure at option:3 on page 8 — Two stacked graphs against t: upper ω(t) rises and levels off; lower A(t) also rises and levels off.
- Q23: figure at option:4 on page 8 — Two stacked graphs against t: upper ω(t) starts high and decreases, levelling off; lower A(t) starts high and decreases, levelling off.
- Q36: figure at stem on page 11 — circuit: points A and B connected across a 50V cell; between A and B two parallel branches: upper branch 1Ω then point C then 2Ω; lower branch 3Ω then point D then 4Ω; a wire CD joins C and D
- Q38: figure at stem on page 12 — Graph A: y-axis 'Photoelectric current', x-axis 'Intensity of light', origin 0; straight line rising from the origin.
- Q38: figure at stem on page 12 — Graph B: y-axis 'Photoelectric current', x-axis 'Intensity of light', origin 0; horizontal line above the x-axis.
- Q38: figure at stem on page 12 — Graph C: y-axis 'Photoelectric current', x-axis 'Frequency of light', origin 0; straight line rising from the origin.
- Q38: figure at stem on page 12 — Graph D: y-axis 'Photoelectric current', x-axis 'Frequency of light', origin 0; straight line rising from a point on the x-axis to the right of the origin.
- Q39: figure at stem on page 13 — Figure: a large solid sphere (hatched) centred on the origin, with Y axis vertical and X axis horizontal; a smaller unhatched circle sits inside on the right, touching the Y-axis and the right edge of the large sphere; its diameter along the X axis is marked 2R
- Q40: figure at stem on page 13 — Circuit: AC source V_in across the primary coil of a transformer; the secondary coil has a centre tap marked by an arrow; the top end of the secondary goes through diode D_1 and the bottom end goes through diode D_2; the two diodes join at the top of a load resistor R_L; output terminals are taken across R_L, with the centre tap wired to the bottom of R_L
- Q49: figure at stem on page 16 — Reaction scheme: 1-methylcyclopentene (cyclopentene ring with CH3 on the double-bond carbon) -> over arrow '(i) HBr, benzoyl peroxide (ii) KCN (iii) Na (Hg)/C2H5OH' -> P (Major)
- Q49: figure at option:1 on page 16 — Cyclopentane ring with CH3 on one carbon and CH2NH2 on the adjacent carbon
- Q49: figure at option:2 on page 16 — Cyclopentane ring with CH3 and CH2NH2 both on the same carbon
- Q49: figure at option:3 on page 16 — Cyclopentane ring with CH3 on one carbon and NC on the adjacent carbon
- Q49: figure at option:4 on page 16 — Cyclopentane ring with NC and CH3 both on the same carbon
- Q60: figure at option:1 on page 18 — benzene ring attached to C(=O)–O Na; arrow labelled 'sodalime' above and 'Δ' below
- Q60: figure at option:2 on page 18 — zigzag chain labelled n-hexane; arrow with 'Mo_2O_3' above and '773K, 10 - 20 atm.' below
- Q60: figure at option:3 on page 18 — H–C≡C–H; arrow with 'red hot Iron Tube' above and 'at 873 K' below
- Q60: figure at option:4 on page 18 — benzene ring attached to N(+)≡N with Cl(-); arrow with 'H_2O' above and 'warm' below
- Q65: figure at option:1 on page 20 — Energy profile: y-axis 'Energy (kJ mol^-1)', x-axis 'Reaction progress'; R level higher, hump, P level lower; double arrow between R and P labelled 74.8
- Q65: figure at option:2 on page 20 — Energy profile: y-axis 'Energy (kJ mol^-1)', x-axis 'Reaction progress'; R low, rising to peak, P slightly below peak; double arrow from peak to P level labelled 74.8
- Q65: figure at option:3 on page 20 — Energy profile: y-axis 'Energy (kJ mol^-1)', x-axis 'Reaction progress'; R level, hump, P lower; double arrow inside the hump from peak down labelled 74.8
- Q65: figure at option:4 on page 20 — Energy profile: y-axis 'Energy (kJ mol^-1)', x-axis 'Reaction progress'; R low, hump, P higher than R; double arrow between R level and P level labelled 74.8
- Q75: figure at stem on page 23 — Structural formula: two H_3C groups attached to CH, which is bonded to CH_2 - CH_3, i.e. (H_3C)_2CH-CH_2-CH_3
- Q76: figure at stem on page 23 — Three structures: I benzene ring with H marked * on ring carbon; II phenyl-C≡C-H with the terminal alkyne C-H marked *; III cyclopropene with the sp3 CH_2 carbon bearing two H, one C-H marked *
- Q77: figure at option:1 on page 24 — skeletal structure: cyclohexane ring (plain hexagon)
- Q77: figure at option:2 on page 24 — benzene ring (hexagon with inner circle) bonded to OH
- Q77: figure at option:3 on page 24 — benzene ring (hexagon with inner circle) bonded to CH=CH_2
- Q77: figure at option:4 on page 24 — benzene ring (hexagon with inner circle) bonded to NH_2
- Q78: figure at stem on page 24 — reaction: phenyl ketone C6H5-C(=O)-CH2-CH2-CN; arrow with (i) CH_3MgBr (excess) above and (ii) H_3O^+ below
- Q78: figure at option:1 on page 24 — structure: carbon bearing CH_3, OH and a ring drawn as hexagon (no inner circle shown clearly), attached to CH2-CH2-CN
- Q78: figure at option:2 on page 24 — structure: phenyl-C(CH_3)(OH)-CH2-CH2-C(=O)-CH_3
- Q78: figure at option:3 on page 24 — structure: phenyl-C(H_3C)(OH)-CH2-CH2-C(OH)(CH_3)(CH_3)
- Q78: figure at option:4 on page 24 — structure: phenyl-C(=O)-CH2-CH2-C(=O)-CH_3
- Q83: figure at stem on page 26 — reaction scheme: methyl benzoate (benzene ring bearing C(=O)OCH3, drawn with C=O labelled O and OCH3) -> benzaldehyde (benzene ring bearing CHO)
- Q84: figure at stem on page 26 — Assertion A: skeletal (zigzag) structure of an alkyl chain ending in I (iodoalkane) undergoes SN2 faster than the same zigzag chain ending in Cl (chloroalkane)
- Q115: figure at stem on page 32 — Circular plasmid map labelled: BamHI site at top, Tet R (hatched segment) at top right, EcoRI site within β Galactosidase segment on right, amp (hatched segment) at bottom, Ori (striped block) on left.
- Q125: figure at option:1 on page 35 — Nephron tubule diagram with P and D segments. P: arrows out labelled HCO_3^- and NaCl at top; arrows in from bottom labelled H^+ and H_2O. D: top arrows NaCl, H_2O, HCO_3^-; bottom arrows K^+ and H^+.
- Q125: figure at option:2 on page 35 — P: top arrows HCO_3^-, NaCl, H_2O; bottom H^+, NH_3. D: top NaCl, H_2O, HCO_3^-; bottom K^+, H^+.
- Q125: figure at option:3 on page 35 — P: top HCO_3^-, NaCl, H_2O; bottom H^+, NH_3. D: top H_2O, HCO_3^-; bottom NaCl, H^+.
- Q125: figure at option:4 on page 35 — P: top NaCl, HCO_3^-; bottom H^+, H_2O. D: top NaCl, NH_3; bottom K^+, H_2O.
- Q126: figure at stem on page 35 — Stirred-tank bioreactor diagram with central shaft and impellers; label B points to top fitting above vessel, C to upper disc near top, A to impeller below it, D to bottom sparger ring with tube.
- Q152: figure at stem on page 40 — Pedigree chart: F0 unaffected male × affected female (filled circle). F1: carrier female (dotted circle) married to unaffected male (left), and carrier female married to unaffected male (right). F2: from left couple, two carrier females; from right couple, affected male (filled square) and unaffected female. Left F2 second carrier female in consanguineous marriage (double line) with affected male. F3: four children marked ? — square, circle, circle, square. Legend: unaffected male, affected male, carrier female, unaffected female, affected female.

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 45 | 165645 | 47383 | 177584 | 4036 | ₹150.16 |
jsonl: extract/pyq/neet-2025-45.jsonl (45 pages)
draft: /Users/rayeesahmed/Documents/margai/pipeline/data/pyq/neet-2025-45.draft.json (180 questions; answers empty until NTA's key is filled in at review)
