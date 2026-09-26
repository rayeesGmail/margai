# margai-pipeline ncert verify

- run: 2026-09-26 20:07 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 2 chapters of phy11-part2 (en)
- result: ok
content store: s3://margai-beta-content
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 fd5a58e91f4e329ae617ed9dc5f68b66248f327e5001644a4309fb1a3386fd50

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 10 | 20 | 13 | 6 | 15 | 0 | 0 | 0 | 1 |
| 12 | 12 | 11 | 0 | 9 | 0 | 0 | 0 | 3 |

## where rows start against where the print starts paragraphs

- ch 10 p1: 3 rows start here, the print starts 12 paragraphs — printed starts no row begins with: "with boiling water is hotter than" · "physics, we need to define the" · "etc., more carefully. In this chapter," · "which heat flows from one body" · "fitting on the rim of a" · "goes down. You will also learn" · "or freezes, and its temperature does" · "processes even though a great deal" · "out of it."
- ch 10 p4: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.5 ¶5 "Here alpha_V is also a characteristic of"
- ch 10 p5: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.5 ¶7 "Water exhibits an anomalous behaviour; it contracts" · §10.5 ¶10 "At 0 °C, alpha_v = 3.7 ×" · printed starts no row begins with: "At constant pressure"
- ch 10 p6: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §10.5 ¶15 "Answer Consider a rectangular sheet of the" · §10.5 ¶18 "Answer Given, T_1 = 27 °C L_T1" · printed starts no row begins with: "Consider a rectangular sheet of the"
- ch 10 p7: 11 rows start here, the print starts 8 paragraphs — rows the print does not start: §10.6 ¶3 "In the third step, in place of" · §10.6 ¶5 "where ∆Q is the amount of heat" · §10.6 ¶10 "where C is known as molar specific"
- ch 10 p8: 4 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "Substance Specific heat capacity" · "Gas C (J mol K )"
- ch 10 p10: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.8 ¶8 "Triple Point The temperature of a substance"
- ch 10 p11: 5 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.8 ¶10 "Let us now remove the burner. Allow"
- ch 10 p12: 4 rows start here, the print starts 6 paragraphs — rows the print does not start: §10.8.1 ¶5 "Answer Heat lost by water = m" · printed starts no row begins with: "Heat lost by water = ms" · "Heat required to raise temperature of" · "Heat lost = heat gained"
- ch 10 p13: 6 rows start here, the print starts 13 paragraphs — rows the print does not start: §10.8.1 ¶6 "Example 10.5 Calculate the heat required to" · printed starts no row begins with: "specific heat capacity of water, s" · "latent heat of fusion of ice," · "latent heat of steam, L" · "ice at –12 °C to steam" · "–12 °C to ice at 0" · "0 °C to water at 0" · "at 0 °C to water at" · "at 100 °C to steam at"
- ch 10 p15: 8 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.9.1 ¶9 "Answer" · §10.9.1 ¶10 "Given, L_1 = L_2 = L =" · §10.9.1 ¶12 "So, H = H_1 = H_2 =" · §10.9.1 ¶13 "(i) T_0 = (K_1 T_1 + K_2" · §10.9.1 ¶14 "(ii) K' = 2K_1 K_2 / (K_1" · printed starts no row begins with: "(ii) the equivalent thermal conductivity of"
- ch 10 p16: 4 rows start here, the print starts 3 paragraphs — rows the print does not start: §10.9.2 ¶2 "In forced convection, material is forced to"
- ch 10 p17: 7 rows start here, the print starts 4 paragraphs — rows the print does not start: §10.9.3 ¶2 "When this thermal radiation falls on other" · §10.9.3 ¶3 "We find that black bodies absorb and" · §10.9.4 ¶2 "Notice that the wavelength lambda_m for which"
- ch 10 p19: 7 rows start here, the print starts 7 paragraphs — rows the print does not start: §10.10 ¶6 "From Eqs. (10.15) and (10.16) we have" · printed starts no row begins with: "∴ Rate of loss of heat"
- ch 10 p20: 5 rows start here, the print starts 6 paragraphs — printed starts no row begins with: "Change in temperature"
- ch 12 p1: 3 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "gases by considering that gases are" · "particles. The actual atomic theory got" · "150 years later. Kinetic theory explains" · "which are short range forces that" · "and liquids, can be neglected for" · "was developed in the nineteenth century"
- ch 12 p3: 6 rows start here, the print starts 5 paragraphs — rows the print does not start: §12.3 ¶4 "where M is the mass of the"
- ch 12 p4: 6 rows start here, the print starts 4 paragraphs — rows the print does not start: §12.3 ¶7 "At low pressures or high temperatures the" · §12.3 ¶9 "Finally, consider a mixture of non-interacting ideal"
- ch 12 p5: 11 rows start here, the print starts 12 paragraphs — printed starts no row begins with: "Volume of a water molecule"
- ch 12 p6: 8 rows start here, the print starts 7 paragraphs — rows the print does not start: §12.4.1 ¶6 "where v is the speed and v^2_bar"
- ch 12 p7: 13 rows start here, the print starts 12 paragraphs — rows the print does not start: §12.4.2 ¶3 "We are now ready for a kinetic" · §12.4.2 ¶7 "The square root of v^2_bar is known" · §12.4.2 ¶13 "* E denotes the translational part of" · printed starts no row begins with: "( We can also write v" · "molecule = (3/2) ) k T"
- ch 12 p8: 10 rows start here, the print starts 11 paragraphs — rows the print does not start: §12.4.2 ¶14 "You should note that the composition of" · printed starts no row begins with: "of a molecule of the gas." · "speeds at any temperature."
- ch 12 p9: 9 rows start here, the print starts 6 paragraphs — rows the print does not start: §12.5 ¶5 "where omega_1 and omega_2 are the angular" · §12.5 ¶6 "We have assumed above that the O_2" · §12.5 ¶8 "* Rotation along the line joining the"
- ch 12 p12: 9 rows start here, the print starts 8 paragraphs — rows the print does not start: §12.7 ¶5 "Let us estimate l and tau for"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
- ch 10 p6: the print starts "Since α ≃ 10 K ," where §10.5 ¶16 starts "Since alpha_l ≃ 10^-5 K^-1, from Table" — the layer dropped the line's math
- ch 12 p3: the print starts "where µ is the number of" where §12.3 ¶3 starts "where mu is the number of moles" — the layer dropped the line's math
- ch 12 p4: the print starts "If we fix µ and T" where §12.3 ¶8 starts "If we fix mu and T in" — the layer dropped the line's math
- ch 12 p6: the print starts "where is the average of v" where §12.4.1 ¶5 starts "where v_x^2_bar is the average of v_x^2" — the layer dropped the line's math

## joins across page breaks against the print

none

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none

## free-check flags set aside by the founder's rulings

each was read against its page and ruled noise; the row counts clean
- ch 10 §10.6 ¶11 runs from p7 onto p8, but p8 opens a new paragraph: "Substance Specific heat capacity" — ruled noise: p7 ends at the measure and p8 opens with Table 10.3 across the top; the paragraph resumes under it
- ch 12 §12.4.2 ¶12 runs from p7 onto p8, but p8 opens a new paragraph: "of a molecule of the gas." — ruled noise: p8 opens "of a molecule of the gas. Therefore,", the rest of p7's "where m is the mass"
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (202 rows)
request id: pipeline-ncert-verify-6583789e-84f0-4015-b56b-cf74def309df

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 4 | 4 | 0 |
artefact: verify/phy11-part2/en.jsonl (104 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 4 | 17749 | 1359 | 21753 | 7251 | ₹6.47 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 12 p10 §12.6.2 ¶3: printed "gamma = 9/7 (12.35)" · transcribed "gamma = (9/7) R (12.35)"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

- ch 10 p10: "Figure : Pressure-temperature phase diagrams for (a) water and (b) CO_2 (not to the scale)."

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 10 p5 §10.5 ¶10: printed "about 3300 × 10^-6 K^-1" · transcribed "about 3300 × 10^-6 K^-1"
- ch 10 p10 §10.8 ¶8: printed "and pressure 6.11×10^-3 Pa" · transcribed "and pressure 6.11×10^(-3) Pa"
- ch 10 p15 §10.9.1 ¶8: printed "K_2 = 109 W m^-1 K^-1)" · transcribed "K_2 = 109 W m^-1K^-1)"
- ch 10 p16 §10.9.2 ¶3: printed "reveresed" · transcribed "reveresed"
- ch 10 p17 §10.9.4 ¶2: printed "lambda_m" · transcribed "lambda_m"
- ch 10 p18 §10.9.4 ¶5: printed "0.3 × 10^-4 × 0.4 × 5.67 × 10^-8 × (3000)^4" · transcribed "0.3 × 10^-4 × 0.4 × 5.67 × 10^-8 × (3000)^4"
- ch 10 p19 §10.10 ¶6: printed "From Eqs. (10.15) and (10.16)" · transcribed "From Eqs. (10.15) and (10.16)"
- ch 12 p8 §12.4.2 ¶16: printed "v_349 / v_352 = (352/ 349)^(1/2) = 1.0044" · transcribed "v_349 / v_352 = (352/349)^(1/2) = 1.0044"
- ch 12 p8 §12.4.2 ¶16: printed "Hence difference Delta V / V = 0.44 %." · transcribed "Hence difference Delta V / V = 0.44 %."
- ch 12 p8 §12.4.2 ¶17: printed "[235U is the isotope needed" · transcribed "[235U is the isotope needed"
- ch 12 p10 §12.6.1 ¶2: printed "U = (3/2) k_B T × N_A = (3/2) RT (12.27)" · transcribed "U = (3/2) k_B T × N_A = (3/2) RT (12.27)"
- ch 12 p11 §12.6.3 ¶5: printed "= 22.4 litres" · transcribed "= 22.4 litres"
- ch 12 p12 §12.7 ¶4: printed "<v_r>" · transcribed "<v_r>"

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
- ch 12 p10 §12.6.1 ¶1: transcribed "C_v (monatomic gas) = dU/dT = (3/2) RT" (printed "C_v (monatomic gas) = dU/dT = (3/2) R")

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 8
verdicts recorded on rows: 197

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 10 | 107 | 107 | 107 | 0 | 0 | 0 | 0 | 100.0% |
| 12 | 95 | 90 | 88 | 1 | 0 | 1 | 0 | — (5 rows without a verdict) |
clean for the book (PLAN D15 ✅): not computed — some rows have no verdict yet
not in the clean share, adjudicate before recording it: 24 page-level start flags, 0 numbered equations the print carries that the rows do not, 1 passages no row carries
