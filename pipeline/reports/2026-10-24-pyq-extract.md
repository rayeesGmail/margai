# margai-pipeline pyq extract

- run: 2026-10-24 19:29 IST
- input: ../pipeline/inputs/pyq-papers.yaml
- sha256: 5577d62ec2e4428facc222d5d272903cd45a3a418ef700344bdb7767a975411b
- read: 6 paper(s) in the register
- result: ok
paper: neet-2021-m4 (NEET 2021, booklet code M4, 2021-09-12)
content store: s3://margai-beta-content
transcriber: claude-opus-5-5, pyq_extract.v1 (the effort is the visionopus55 profile's)
page images: 150 dpi, PDFBox 3.0.5, Java 25.0.4.1, 2 tile(s) a page
booklet: source/neet-pyq-sources/2021/neet-2021-M4-booklet-en.pdf (en; pages 2–25, 24 in all)
text layer: fed as the character authority (legibility 0.319)
request id: pipeline-pyq-extract-9705160a-5e5b-4358-b5fc-9a242b5a990d

## pages

| in the register | read this run | from earlier runs | in the jsonl |
|---|---|---|---|
| 24 | 24 | 0 | 24 |

## page images — what each call read, at pages/pyq/neet-2021-m4/ and under ../pipeline/data/pyq/pages/neet-2021-m4

| page | sha256 | image |
|---|---|---|
| 2 | 3fcb076920c2c11e287ff6c8e824c492af1fb79f812d1790b5f29e34650467ce | kept with this run's call |
| 3 | 9cd81825871e196ce628090b8475920c6fe2c6794d6ae392e064e15e5c6365e9 | kept with this run's call |
| 4 | 25aaf54e3765788a43ef2f3668aebd7fa0a2d2fa8c660390cb2c3640a78dcfc0 | kept with this run's call |
| 5 | e2684b7068f529cae047bb0656d4d6d04fe65ffb1a2da45323790b6f18f5969a | kept with this run's call |
| 6 | 8243092de3ba99b1da3d1b4d4524cf2b18ca87bef01c2a837785690f2af5ca71 | kept with this run's call |
| 7 | 91a1e14a0b9ae6a35b091d698c63acb470f7072f0a7816911ff8a06667542c01 | kept with this run's call |
| 8 | 52d3a1d589c6a8f6e334d23291dbb2bd2086f629207a99e5653920ffe56566c2 | kept with this run's call |
| 9 | 7829a0e6c97b91f42082e58c154b1d14b3d1e9eed5dece828182125c0e9e3921 | kept with this run's call |
| 10 | 3ea8e996d64c0654def1e56185a663d3f57b80fde105827992189103a77e91e1 | kept with this run's call |
| 11 | bd6ed2c189b57544d81ee9c90f9c204ce00e3a1190ad0f112c75503443994115 | kept with this run's call |
| 12 | b35df79871fe2a9e1c5a5f918b1d27baa9e43a452d6840d698b85fa17453a344 | kept with this run's call |
| 13 | 2594339f7800ef472d283cdc2f5aeb6cf9455110845c2c71b4e50324785ee36f | kept with this run's call |
| 14 | 5cdf4526984a1b3327cd43f2850238fa8a4fd8e7714a37abd77b942db6b3fe5b | kept with this run's call |
| 15 | 763cf7e6785ee64ac510e90d7d07372d0f33d4452b89caa9916e396703001d14 | kept with this run's call |
| 16 | 1ca7de19ba3c0e0fe5cf6b688d45d26cc90f54a0f209b974f9249df3ec439fcf | kept with this run's call |
| 17 | 70f1efc474c1f69d70e0339d6fa00dc69cdcc934091df25193b436f96310f8fc | kept with this run's call |
| 18 | 576c511dd4406691b2ccfd5e6d8e2ce4fa415e551e118bf86a5d9ec3e752d771 | kept with this run's call |
| 19 | 31516f32aa304a77e110b0ae0860785ec9f30df7c21059be758789547dfd179f | kept with this run's call |
| 20 | 28cd483c7312444c85ed930d39ba12d168a32ff771c97843bb7afdfd74b9c59a | kept with this run's call |
| 21 | b8d5c2493f03cae5c2d58084953e16727675cd118ff4ec94e23bbe89c9a382a7 | kept with this run's call |
| 22 | 125ab3faf29468440e7b345db24c100a14d301463176e5c635fb71a3d6e66a0d | kept with this run's call |
| 23 | 3199d72767bd7c4d6312f4fc22693a97e3777c78e206f3d2a6f911532d296e02 | kept with this run's call |
| 24 | 1b52570888efce480c0e61c9bb2b24da7af36039e7f7189ded466d4124f945a5 | kept with this run's call |
| 25 | 0dec9c01f7e16157ba1315cad1f3e488b126a7f1538f57119a41442a9256fa96 | kept with this run's call |

## questions per section — against the count the booklet declares

| subject | section | questions | declared | with a figure |
|---|---|---|---|---|
| physics | A | 35 | 35 | 6 |
| physics | B | 15 | 15 | 4 |
| chemistry | A | 35 | 35 | 4 |
| chemistry | B | 15 | 15 | 4 |
| botany | A | 35 | 35 | 1 |
| botany | B | 15 | 15 | 1 |
| zoology | A | 35 | 35 | 0 |
| zoology | B | 15 | 15 | 0 |

## flags — every one is checked against the page image before the file is approved

- page 15: the heading 'Section - A (Biology : Botany)' comes before question 101, which the register puts in botany
- page 18: the heading 'Section - B (Biology : Botany)' comes before question 136, which the register puts in botany
- page 20: the heading 'Section - A (Biology : Zoology)' comes before question 151, which the register puts in zoology
- page 23: the heading 'Section - B (Biology : Zoology)' comes before question 186, which the register puts in zoology
- Q4: figure at stem on page 2 — circuit: two terminals; between them a rectangular network with capacitor C on top branch and capacitor C on bottom branch, and a vertical capacitor C connected between top and bottom wires in the middle
- Q5: figure at stem on page 2 — right-angled triangular prism with right angle at bottom left, 60° angle at bottom right; incident ray arrow falls normally (right-angle mark) on the hypotenuse face
- Q6: figure at stem on page 2 — three dashed field lines diverging from a point on the left toward the right, central one labelled E_vec; a dipole on the central line with +q on left and -q on right
- Q14: text layer, page 3: 'upsilon' 1x here, 0x on the page
- Q17: figure at stem on page 4 — Horizontal conductor P to Q carrying 5 A (arrow towards Q); electron moving parallel above it with arrow, labelled 'Electron v = 10^5 m/s'; vertical separation marked 20 cm.
- Q20: figure at stem on page 4 — Series circuit: inductor, capacitor, resistor in series with ac source V; voltages marked 40 V across inductor, 10 V across capacitor, 40 V across resistor.
- Q26: figure at option:1 on page 5 — graph of B (vertical axis) vs r (horizontal axis): curve starting at origin rising ever more steeply upward (concave up)
- Q26: figure at option:2 on page 5 — graph of B vs r: straight line from origin rising linearly
- Q26: figure at option:3 on page 5 — graph of B vs r: line rising linearly from origin to a peak marked by a dashed vertical line, then curve decreasing towards the r axis
- Q26: figure at option:4 on page 5 — graph of B vs r: curve rising steeply from origin then flattening to a constant value (concave down saturation)
- Q38: figure at stem on page 7 — Timing diagram of inputs A, B, C (levels 0 and 5) against time marks t1 to t6. A is high t1–t2, t3–t4, t5–t6. B is high t2–t4. C is high before t2 and after t4, low t2–t4. Below it, a logic circuit: A and B go into an AND gate; B and C go into a NAND gate; both gate outputs go into an OR gate whose output is y.
- Q38: figure at option:1 on page 7 — Waveform of y with marks t1 to t6: a flat line at 0 V, with short vertical tick marks at t1–t6.
- Q38: figure at option:2 on page 7 — Waveform of y: at 0 V, rising to 5 V for a middle stretch, back to 0 V, then rising to 5 V again at the end; levels labelled 5 V and 0 V.
- Q38: figure at option:3 on page 7 — Waveform of y: a flat line at 5 V.
- Q38: figure at option:4 on page 7 — Waveform of y: at 5 V, dropping to 0 V, rising to 5 V, then dropping to 0 V at the end; levels labelled 5 V and 0 V.
- Q40: figure at stem on page 7 — A rod with marks 0, 20 cm, 40 cm and 160 cm. A wedge sits under the rod at the 40 cm mark. A 2 kg mass hangs at 20 cm and a mass m hangs at 160 cm.
- Q41: figure at stem on page 7 — A principal axis with a convex lens on it and a plane mirror standing perpendicular to the axis on the right. The distance from the object to the lens is marked 60 cm; the distance from the lens to the mirror is marked 40 cm.
- Q49: figure at stem on page 9 — circuit: from point A, current i_1 flows through resistor r_1, then splits into two parallel branches: upper branch resistor r_2 with current i_2, lower branch resistor r_3 with current i_3; branches rejoin and lead to point B
- Q60: figure at option:1 on page 10 — PE vs Reaction Progress graph: A starts low, tall peak, B ends higher than A (endothermic profile).
- Q60: figure at option:2 on page 10 — PE vs Reaction Progress graph: A starts high, peak, B ends lower than A (exothermic profile).
- Q60: figure at option:3 on page 10 — PE vs Reaction Progress graph: A and B at the same level with a peak between.
- Q60: figure at option:4 on page 10 — PE vs Reaction Progress graph: A starts low, rising curve to a small hump, B slightly below hump, well above A.
- Q62: text layer, page 10: 'hbr' 2x here, 0x on the page
- Q62: text layer, page 10: 'coc' 1x here, 0x on the page
- Q62: text layer, page 10: 'cbr' 1x here, 0x on the page
- Q63: figure at option:1 on page 11 — graph: y-axis Pressure (P) (bar), x-axis Volume (V) (dm^3); three hyperbola-shaped curves labelled from top to bottom 200 K, 400 K, 600 K
- Q63: figure at option:2 on page 11 — graph: y-axis Pressure (P) (bar), x-axis Volume (V) (dm^3); one horizontal line labelled (200 K, 400 K, 600 K)
- Q63: figure at option:3 on page 11 — graph: y-axis Pressure (P) (bar), x-axis Volume (V) (dm^3); three straight lines from origin, steepest labelled 200 K, then 400 K, then 600 K
- Q63: figure at option:4 on page 11 — graph: y-axis Pressure (P) (bar), x-axis Volume (V) (dm^3); three hyperbola-shaped curves labelled from top to bottom 600 K, 400 K, 200 K
- Q79: figure at option:1 on page 12 — skeletal structure: CH3–CH2–NO2 with lone pair dots on N
- Q79: figure at option:2 on page 12 — skeletal structure: CH3–CH2–NH–CH3 with lone pair dots on N
- Q79: figure at option:3 on page 12 — skeletal structure: CH3–CH2–NH2 with lone pair dots on N
- Q79: figure at option:4 on page 12 — skeletal structure: CH3–CH2–N(CH3)–CH2–CH3, N with lone pair dots and CH3 below N
- Q82: figure at option:1 on page 13 — skeletal formula: zig-zag chain with a C=C double bond near the upper left, an isopropyl-type branch at the upper right end, and a methyl branch at the lower left carbon continuing into a chain to the lower right
- Q82: figure at option:2 on page 13 — skeletal formula: upper chain from a methyl branch on the left to an isopropyl-type end at the right; a vertical bond down from the left branch carbon into a lower chain with a C=C double bond, ending at the lower right
- Q82: figure at option:3 on page 13 — skeletal formula: upper part with a C=C double bond leading to an isopropyl-type branch at the upper right; a vertical bond down at the left to a branched carbon with a methyl to the lower left and a chain to the lower right
- Q82: figure at option:4 on page 13 — skeletal formula: upper chain ending in an isopropyl-type branch at the upper right; a vertical C=C double bond at the left joining to a lower carbon with a methyl to the left and a chain to the lower right
- Q86: text layer, page 13: 'iii' 6x here, 5x on the page
- Q88: figure at stem on page 14 — Reaction: cyclohexanone ring with C=O at C1, CH_2-C(=O)-OCH_3 substituent at C2 and CH_3 at C3; arrow labelled NaBH_4 above and C_2H_5OH below, product '?'
- Q88: figure at option:1 on page 14 — Cyclohexane ring with OH at C1, CH_2-CH(OH)-OCH_3 at C2 (H and OH on that carbon), CH_3 at C3
- Q88: figure at option:2 on page 14 — Cyclohexanone ring (C=O at C1) with CH_2-CH_2-OH at C2 and CH_3 at C3
- Q88: figure at option:3 on page 14 — Cyclohexane ring with OH at C1, CH_2-CH(OH)-CH_3 at C2, CH_3 at C3
- Q88: figure at option:4 on page 14 — Cyclohexane ring with OH at C1, CH_2-C(=O)-OCH_3 at C2, CH_3 at C3
- Q91: figure at stem on page 14 — Reaction sequence: 2,4,6-tribromoaniline (NH_2, Br at both ortho and para) -> (NaNO_2, HCl, 0 - 5°C) -> 2,4,6-tribromobenzenediazonium chloride (N_2^+ Cl^-) -> (R) -> 1,3,5-tribromobenzene
- Q96: notation: a degree sign not after a number — the layer's Greek letter copied through?: "…t option. [Λ°_(H^+) = 350 S cm^2 mol^…"
- Q96: notation: a degree sign not after a number — the layer's Greek letter copied through?: "…m^2 mol^-1 Λ°_(CH_3COO^-) = 50 S cm^2…"
- Q98: text layer, page 15: 'figure' 2x here, 0x on the page
- Q98: figure at stem on page 15 — List-I item (a): benzene ring with arrow labelled 'CO, HCl' above and 'Anhyd.AlCl_3/CuCl' below
- Q98: figure at stem on page 15 — List-I item (b): R–C(=O)–CH_3 + NaOX ->
- Q99: figure at stem on page 15 — Reaction: toluene (benzene with CH_3) + CrO_2Cl_2 -> (CS_2) X -> (H_3O^+) benzaldehyde (benzene with C(=O)H)
- Q99: figure at option:1 on page 15 — benzene ring with CH(OCrOHCl_2)_2 substituent
- Q99: figure at option:2 on page 15 — benzene ring with CH(OCOCH_3)_2 substituent
- Q99: figure at option:3 on page 15 — benzene ring with CH bearing two Cl (CHCl_2)
- Q99: figure at option:4 on page 15 — benzene ring with CH bearing Cl and H (CH_2Cl)
- Q104: text layer, page 16: 'tissues' 2x here, 1x on the page
- Q122: figure at stem on page 17 — flow chart: (a) curved arrow looping back onto DNA; DNA -(b)-> mRNA -(c)-> (d)
- Q138: text layer, page 18: 'floral' 4x here, 0x on the page
- Q138: text layer, page 18: 'formula' 4x here, 0x on the page
- Q138: figure at stem on page 18 — Floral formulae in Column - I: (a) % ⚥ K_(5) C_1+2+(2) A_(9)+1 G_1 (G underlined); (b) ⊕ ⚥ K_(5) C_(5) A_5 G_2 with arc over C and A (epipetalous), G underlined; (c) ⊕ ⚥ P_(3+3) A_3+3 G_(3) with arc over P and A, G underlined; (d) ⊕ ⚥ K_2+2 C_4 A_2+4 G_(2), G underlined
- Q160: text layer, page 21: 'rnas' 2x here, 1x on the page
- Q171: text layer, page 22: 'techniques' 2x here, 1x on the page

## low-confidence pages (below 0.80) — a routing signal, not a guarantee

none

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 24 | 113894 | 47124 | 92828 | 4036 | ₹129.42 |
jsonl: extract/pyq/neet-2021-m4.jsonl (24 pages)
draft: ../pipeline/data/pyq/neet-2021-m4.draft.json (200 questions; answers empty until NTA's key is filled in at review)
