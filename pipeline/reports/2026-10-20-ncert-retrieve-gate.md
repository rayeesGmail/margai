# margai-pipeline ncert retrieve gate

- run: 2026-10-20 10:19 IST
- input: ../eval/retrieval-queries.json
- sha256: f94fd2795a931d1e08c2adf9234108e42c231995a57b9463799835916c7e881a
- read: 6 concept queries — 0 acceptance, 0 baseline, 0 diagnostic, 6 heldout
- result: ok

## the corpus searched — every English paragraph carries a vector

| book | subject | with a vector | without |
|---|---|---|---|
| phy11-part1 | physics | 909 | 0 |
| phy11-part2 | physics | 682 | 0 |
| phy12-part1 | physics | 1042 | 0 |
| phy12-part2 | physics | 500 | 0 |
| chem11-part1 | chemistry | 1331 | 0 |
| chem11-part2 | chemistry | 534 | 0 |
| chem12-part1 | chemistry | 657 | 0 |
| chem12-part2 | chemistry | 582 | 0 |
| bio11 | biology | 748 | 0 |
| bio12 | biology | 673 | 0 |
request id: pipeline-ncert-retrieve-f106327c-92cf-4ff1-bf9b-719751e2c30d

## cost

| calls | input tokens | spent |
|---|---|---|
| 18 | 1546 | ₹0.32 |

## the Week-3 gate probes (gate week3) — 6 of 6 in the answer set

each probe read fused — a non-English probe retrieved with its English search version and fused, never the rewrite alone; an English probe on its own text — the unfused rank beside it
| id | books | subject | lang | rank unfused | rank fused | grounded via, unfused | grounded via, fused | in the answer set |
|---|---|---|---|---|---|---|---|---|
| h05-lenz-opposition-hi | phy12-part1 | physics | hi | 4 | 7 | question | both | yes |
| h06-capillary-rise | phy11-part2 | physics | en | 1 | 1 | question | question | yes |
| h07-ethyne-acidic | chem11-part2 | chemistry | en | 1 | 1 | question | question | yes |
| h08-redox-electron-transfer-hi | chem11-part2 | chemistry | hi | 3 | 1 | question | both | yes |
| h09-transition-metal-colour-hi | chem12-part1 | chemistry | hi | 8 | 1 | question | both | yes |
| h10-egg-white-boiling-hi | chem12-part2 | chemistry | hi | 1 | 1 | question | both | yes |

## the search versions the model wrote

| id | lang | query | search version |
|---|---|---|---|
| h05-lenz-opposition-hi | hi | प्रेरित धारा हमेशा उसी परिवर्तन का विरोध क्यों करती है जिससे वह पैदा होती है? | Why does induced current always oppose the change in magnetic flux that produces it? Lenz's law explanation, electromagnetic induction, Class 12 |
| h08-redox-electron-transfer-hi | hi | ऑक्सीकरण और अपचयन को इलेक्ट्रॉन के लेन-देन से कैसे समझाया जाता है? | How are oxidation and reduction explained in terms of loss and gain of electrons in redox reactions |
| h09-transition-metal-colour-hi | hi | संक्रमण धातुओं के आयन रंगीन क्यों होते हैं? | Why are transition metal ions coloured? d-d transition, crystal field splitting of d orbitals and absorption of visible light |
| h10-egg-white-boiling-hi | hi | उबालने पर अंडे का सफ़ेद भाग जम क्यों जाता है? | Why does egg white coagulate on boiling? Denaturation of proteins by heat, structure of proteins, primary secondary tertiary quaternary structure |

## grounded only through the search version — the top passage in full, for the hand-check (0)

none

## misses, fused — what was expected, and what came back (0)

none

## top 3 passages per probe, fused


**h05-lenz-opposition-hi** (hi) — प्रेरित धारा हमेशा उसी परिवर्तन का विरोध क्यों करती है जिससे वह पैदा होती है?
  1. phy12-part1 ch 6 §6.5 ¶2 · both · similarity 0.625 · The polarity of induced emf is such that it tends to produce a current which opposes the …
  2. phy12-part1 ch 6 §6.5 ¶3 · both · similarity 0.585 · The negative sign shown in Eq. (6.3) represents this effect. We can understand Lenz's law…
  3. phy12-part1 ch 6 §6.4 ¶2 · both · similarity 0.523 · The motion of a magnet towards or away from coil C_1 in Experiment 6.1 and moving a curre…

**h06-capillary-rise** (en) — why does water climb up a very thin glass tube on its own even though gravity pulls it down
  1. ✅ phy11-part2 ch 9 §9.6.5 ¶1 · both · similarity 0.514 · One consequence of the pressure difference across a curved liquid-air interface is the we…
  2. ✅ phy11-part2 ch 9 §9.6.5 ¶2 · both · similarity 0.421 · Thus the pressure of the water inside the tube, just at the meniscus (air-water interface…
  3. phy11-part2 ch 9 §9.6.2 ¶9 · vector · similarity 0.432 · A fluid will stick to a solid surface if the surface energy between fluid and the solid i…

**h07-ethyne-acidic** (en) — why is ethyne acidic when ethene and ethane are not
  1. ✅ chem11-part2 ch 9 §9.4.4 ¶3 · both · similarity 0.627 · A. Acidic character of alkyne: Sodium metal and sodamide (NaNH_2) are strong bases. They …
  2. chem11-part2 ch 9 §9.4.4 ¶4 · vector · similarity 0.527 · These reactions are not shown by alkenes and alkanes, hence used for distinction between …
  3. chem11-part2 ch 8 §8.3.1 ¶2 · text · These structural formulas can be further abbreviated by omitting some or all of the dashe…

**h08-redox-electron-transfer-hi** (hi) — ऑक्सीकरण और अपचयन को इलेक्ट्रॉन के लेन-देन से कैसे समझाया जाता है?
  1. ✅ chem11-part2 ch 7 §7.2 ¶3 · both · similarity 0.612 · Each of the above steps is called a half reaction, which explicitly shows involvement of …
  2. chem11-part2 ch 7 §7.3.4 ¶1 · both · similarity 0.578 · As you have observed in the above discussion, the concept of redox processes has been evo…
  3. chem11-part2 ch 7 §7.1 ¶5 · vector · similarity 0.529 · Incorporating the reactions (7.5 to 7.7) within the fold of oxidation reactions encourage…

**h09-transition-metal-colour-hi** (hi) — संक्रमण धातुओं के आयन रंगीन क्यों होते हैं?
  1. ✅ chem12-part1 ch 5 §5.5.5 ¶2 · both · similarity 0.551 · The colour in the coordination compounds can be readily explained in terms of the crystal…
  2. chem12-part1 ch 5 §5.5.5 ¶3 · both · similarity 0.532 · Colour of Some Gem Stones The colours produced by electronic transitions within the d orb…
  3. chem12-part1 ch 5 §5.5.4 ¶4 · both · similarity 0.494 · Such a series is termed as spectrochemical series. It is an experimentally determined ser…

**h10-egg-white-boiling-hi** (hi) — उबालने पर अंडे का सफ़ेद भाग जम क्यों जाता है?
  1. ✅ chem12-part2 ch 10 §10.2.4 ¶1 · both · similarity 0.624 · Protein found in a biological system with a unique three-dimensional structure and biolog…
  2. chem12-part2 ch 10 §10.2.3 ¶11 · both · similarity 0.439 · (iii) Tertiary structure of proteins: The tertiary structure of proteins represents overa…
  3. chem12-part2 ch 10 §10.2.3 ¶6 · both · similarity 0.431 · Structure and shape of proteins can be studied at four different levels, i.e., primary, s…
