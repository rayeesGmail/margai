# margai-pipeline ncert verify

- run: 2026-09-19 00:19 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 1 chapters of phy11-part1 (en)
- result: ok
content store: s3://margai-beta-content

## the print's typography against the rows (free; routes attention, never refuses)

| chapter | pages compared | page breaks judged | page breaks undecided | start flags | join flags | figure flags | equation flags | starts paired |
|---|---|---|---|---|---|---|---|---|
| 4 | 17 | 15 | 1 | 14 | 2 | 0 | 0 | 0 |

## where rows start against where the print starts paragraphs

- ch 4 p2: 8 rows start here, the print starts 9 paragraphs — printed starts no row begins with: "(iii) Motion on a horizontal plane"
- ch 4 p3: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.4 ¶3 "The state of rest or uniform linear" · §4.4 ¶4 "Two kinds of situations are encountered in"
- ch 4 p5: 9 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.5 ¶2 "Momentum of a body is defined to" · §4.5 ¶3 "Momentum is clearly a vector quantity. The" · §4.5 ¶4 "• Suppose a light-weight vehicle (say a" · §4.5 ¶5 "• If two stones, one light and" · §4.5 ¶6 "• Speed is another important parameter to" · §4.5 ¶7 "• A seasoned cricketer catches a cricket" · §4.5 ¶8 "• Observations confirm that the product of" · §4.5 ¶9 "• In the preceding observations, the vector" · printed starts no row begins with: "if they are moving with the" · "on its motion." · "force that needs to be applied." · "second law of motion."
- ch 4 p6: 7 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.5 ¶11 "The rate of change of momentum of" · §4.5 ¶12 "Thus, if under the action of a" · §4.5 ¶13 "The unit of force has not been" · printed starts no row begins with: "law is obviously consistent with the"
- ch 4 p7: 10 rows start here, the print starts 10 paragraphs — rows the print does not start: §4.5 ¶21 "The retarding force, by the second law" · §4.5 ¶22 "The actual resistive force, and therefore, retardation" · §4.5 ¶26 "Impulse We sometimes encounter examples where a" · printed starts no row begins with: "of velocity remains unchanged (Fig. 4.5)." · "mass m is described by y" · "the force acting on the particle."
- ch 4 p8: 13 rows start here, the print starts 9 paragraphs — rows the print does not start: §4.5 ¶27 "A large force acting for a short" · §4.6 ¶2 "Thus, according to Newtonian mechanics, force never" · §4.6 ¶3 "To every action, there is always an" · §4.6 ¶4 "Newton’s wording of the third law is" · §4.6 ¶6 "Forces always occur in pairs. Force on" · printed starts no row begins with: "force on the body B by"
- ch 4 p10: 8 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.7 ¶3 "The total momentum of an isolated system" · §4.7 ¶4 "An important example of the application of" · §4.8 ¶3 "In other words, the resultant of any" · §4.8 ¶6 "* Equilibrium of a body requires not" · printed starts no row begins with: "the mass of the rope."
- ch 4 p11: 9 rows start here, the print starts 7 paragraphs — rows the print does not start: §4.8 ¶10 "Note the answer does not depend on" · §4.9 ¶5 "* We are not considering, for simplicity,"
- ch 4 p12: 3 rows start here, the print starts 2 paragraphs — rows the print does not start: §4.9.1 ¶1 "Let us return to the example of"
- ch 4 p13: 8 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.9.1 ¶9 "Therefore, tan theta_max = mu_s or theta_max" · §4.9.1 ¶10 "For theta_max = 15°, mu_s = tan"
- ch 4 p14: 5 rows start here, the print starts 3 paragraphs — rows the print does not start: §4.9.1 ¶13 "A body like a ring or a" · §4.9.1 ¶16 "In many practical situations, however, friction is"
- ch 4 p15: 15 rows start here, the print starts 5 paragraphs — rows the print does not start: §4.10 ¶2 "where m is the mass of the" · §4.10 ¶4 "Motion of a car on a level" · §4.10 ¶8 "As there is no acceleration in the" · §4.10 ¶9 "The centripetal force required for circular motion" · §4.10 ¶10 "which is independent of the mass of" · §4.10 ¶11 "Motion of a car on a banked" · §4.10 ¶12 "The centripetal force is provided by the" · §4.10 ¶13 "But f ≤ mu_s N" · §4.10 ¶14 "Thus to obtain v_max we put f" · §4.10 ¶15 "Then Eqs. (4.19a) and (4.19b) become N"
- ch 4 p16: 6 rows start here, the print starts 6 paragraphs — rows the print does not start: §4.10 ¶16 "For mu_s = 0 in Eq. (4.21" · printed starts no row begins with: "(b) maximum permissible speed to avoid"
- ch 4 p17: 14 rows start here, the print starts 14 paragraphs — rows the print does not start: §4.11 ¶7 "The following example illustrates the above procedure" · §4.11 ¶9 "Answer" · §4.11 ¶12 "By the third law, the action of" · §4.11 ¶13 "Action-reaction pairs" · §4.11 ¶14 "For (a): (i) the force of gravity" · §4.11 ¶15 "For (b): (i) the force of gravity" · printed starts no row begins with: "links, supports, etc." · "as one system." · "under consideration is without a net" · "laws of motion." · "be shown as –F." · "(ii) the force on the floor"

## printed starts paired with a row only after allowing for math the layer dropped

each of these quieted a page; read them against the page if a page looks too clean
none

## joins across page breaks against the print

- ch 4 §4.5 ¶27 starts a paragraph at the top of p8, but the print continues p7's: "A large force acting for a"
- ch 4 §4.7 ¶3 starts a paragraph at the top of p10, but the print continues p9's: "The total momentum of an isolated"

## figure_refs against the paragraph and the chapter's captions

none

## numbered equations the print carries that the rows do not

none
rulings: ../pipeline/inputs/ncert-corrections.yaml sha256 0053479fca07bcb3af52bba0f193836fc389be135b399ad5c3e43a37d310ccfa
verifier: claude-sonnet-5 (prompt ncert_verify.v1); transcribed by: claude-opus-5 (139 rows)
request id: pipeline-ncert-verify-05d74752-6a53-4f89-a113-e25e0f8b408c

## pages read by the second read

| pages | read this run | from earlier runs |
|---|---|---|
| 1 | 1 | 0 |
artefact: verify/phy11-part1/en.jsonl (108 pages)

## cost (from the ai_calls ledger)

| calls | input | output | cache read | cache write | cost |
|---|---|---|---|---|---|
| 1 | 4541 | 435 | 0 | 7251 | ₹2.85 |

## the second read's flags — adjudicate these against the page

each line: the address, the span as the page prints it, the span as the row carries it
- ch 4 p17 §4.11 ¶11: printed "270 – R'  =  27  0.1N" · transcribed "270 – R' = 27 × 0.1N"

## rows the verifier could not find on their page

none

## running text the page prints that no row carries

none

## items the verifier returned no verdict for

none

## set aside by code: the spans differ only in spacing or a glyph variant, or not at all

- ch 4 p7 §4.5 ¶21: printed "0.04 kg × 6750" · transcribed "0.04 kg × 6750"
- ch 4 p11 §4.9 ¶2: printed "upward bouyant force" · transcribed "upward bouyant force"
- ch 4 p14 §4.9.1 ¶15: printed "another effective way of reducing friction (Fig. 4.13(a))." · transcribed "another effective way of reducing friction (Fig. 4.13(a))."

## set aside by code: the verifier quoted a transcription the row does not carry

the row is left not judged: the verifier claimed a difference it could not place
none

## set aside by code: a heading or a caption listed as omitted text

none
flags set aside by the founder's rulings in ncert-corrections.yaml: 2
verdicts recorded on rows: 139

## clean paragraphs — the second read matches and no join or figure flag names the row

| chapter | rows | with a verdict | matches | differs | not on page | not judged | join or figure flags | clean |
|---|---|---|---|---|---|---|---|---|
| 4 | 139 | 139 | 138 | 1 | 0 | 0 | 2 | 97.8% |
clean for the book (PLAN D15 ✅): not computed — only some chapters were selected
not in the clean share, adjudicate before recording it: 14 page-level start flags, 0 numbered equations the print carries that the rows do not, 0 passages no row carries
