# margai-pipeline ncert embed

- run: 2026-10-01 07:43 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 0 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-0577e1d4-30c3-4c8a-80f6-d2e8cb563e02
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: 15 from ../eval/retrieval-queries.json (5 in Hindi)

## cost

| calls | input tokens | spent |
|---|---|---|
| 15 | 450 | ₹0.15 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 0 |
| 2 | 0 |
| 3 | 0 |
| 4 | 0 |
| 5 | 0 |
| 6 | 0 |
| 7 | 0 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 0 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

## concept queries — the score (PLAN D15 ✅)

| set | queries | hit@1 | hit@3 | MRR |
|---|---|---|---|---|
| all | 15 | 7/15 (47%) | 10/15 (67%) | 0.593 |
| english | 10 | 5/10 (50%) | 7/10 (70%) | 0.639 |
| hindi → english paragraphs | 5 | 2/5 (40%) | 3/5 (60%) | 0.500 |

## which half of the hybrid actually fired

a query the text half never answers is a query where hybrid retrieval is vector retrieval
counted over the passages that survived the token cap, and a query whose grounding failed carries none — so this under-reports both halves rather than over-reporting either
| half | queries it returned something for |
|---|---|
| vector | 14 of 15 |
| full text | 10 of 15 |

## per query

| id | lang | rank of the expected paragraph | note | expected | query |
|---|---|---|---|---|---|
| q01-significant-figures | en | 4 |  | [ch 1 §1.3.1 ¶2] | if I multiply two measured lengths how many digits should I keep in the answer |
| q02-speed-vs-velocity | en | 1 |  | [ch 2 §2.2 ¶10] | is speed the same thing as velocity or is there a difference |
| q03-displacement-vs-path | en | 15 |  | [ch 3 §3.2.1 ¶2] | if I walk around a park and come back to where I started how much have I moved |
| q04-friction-contact-area | en | 1 |  | [ch 4 §4.9.1 ¶2] | does friction depend on how much surface area is touching |
| q05-inertia-bus | en | 1 |  | [ch 4 §4.4 ¶8] | why do I get thrown backwards when a bus suddenly starts moving |
| q06-car-turning-flat-road | en | 2 |  | [ch 4 §4.10 ¶9, ch 4 §4.10 ¶18] | what stops a car from skidding sideways when it takes a turn on a flat road |
| q07-zero-work | en | 13 |  | [ch 5 §5.3 ¶7] | if I carry my bag across a flat room have I done any work on it |
| q08-rotational-kinetic-energy | en | 2 |  | [ch 6 §6.9 ¶2, ch 6 §6.9 ¶4, ch 6 §6.9 ¶6] | a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one |
| q09-moment-of-inertia-hi | hi | miss |  | [ch 6 §6.9 ¶13, ch 6 §6.9 ¶11] | किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है |
| q10-angular-momentum-conservation | en | 1 |  | [ch 6 §6.12.1 ¶3] | why does a skater spin faster when she pulls her arms in |
| q11-escape-speed | en | 1 |  | [ch 7 §7.8 ¶9, ch 7 §7.8 ¶6] | how fast does a rocket have to go to leave the earth for good |
| q12-dimensional-analysis-hi | hi | miss | grounding failure | [ch 1 §1.6.2 ¶9, ch 1 §1.6 ¶1] | विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं |
| q13-inertia-hi | hi | 1 |  | [ch 4 §4.4 ¶8, ch 4 §4.4 ¶2] | जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है |
| q14-momentum-conservation-hi | hi | 2 |  | [ch 4 §4.7 ¶1] | बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है |
| q15-gravity-with-depth-hi | hi | 1 |  | [ch 7 §7.6 ¶11] | पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है |

## top 3 passages per query


**q01-significant-figures** (en) — if I multiply two measured lengths how many digits should I keep in the answer
  1. phy11-part1 ch 1 §1.3 ¶1 · both · similarity 0.501 · Every measurement involves errors. Thus, the result of measurement should be reported in …
  2. phy11-part1 ch 1 §1.3 ¶13 · both · similarity 0.488 · (2) There can be some confusion regarding the trailing zero(s). Suppose a length is repor…
  3. phy11-part1 ch 1 §1.3.3 ¶10 · vector · similarity 0.573 · This example justifies the idea to retain one more extra digit (than the number of digits…

**q02-speed-vs-velocity** (en) — is speed the same thing as velocity or is there a difference
  1. ✅ phy11-part1 ch 2 §2.2 ¶10 · both · similarity 0.562 · Instantaneous speed or simply speed is the magnitude of velocity. For example, a velocity…
  2. phy11-part2 ch 9 §9.3 ¶1 · text · So far we have studied fluids at rest. The study of the fluids in motion is known as flui…
  3. phy11-part1 ch 1 §1.4 ¶5 · vector · similarity 0.516 · Note that in this type of representation, the magnitudes are not considered. It is the qu…

**q03-displacement-vs-path** (en) — if I walk around a park and come back to where I started how much have I moved
  1. phy11-part1 ch 3 §3.7.1 ¶2 · vector · similarity 0.372 · Suppose a particle moves along the curve shown by the thick line and is at P at time t an…
  2. phy11-part1 ch 4 §4.4 ¶8 · text · The property of inertia contained in the First law is evident in many situations. Suppose…
  3. phy12-part1 ch 2 §2.1 ¶9 · vector · similarity 0.370 · (Note here that this displacement is in an opposite sense to the electric force and hence…

**q04-friction-contact-area** (en) — does friction depend on how much surface area is touching
  1. ✅ phy11-part1 ch 4 §4.9.1 ¶2 · both · similarity 0.478 · We know from experience that as the applied force exceeds a certain limit, the body begin…
  2. phy11-part1 ch 4 §4.9.1 ¶14 · both · similarity 0.411 · Rolling friction again has a complex origin, though somewhat different from that of stati…
  3. phy11-part1 ch 4 §4.9.1 ¶4 · vector · similarity 0.417 · Thus, when two bodies are in contact, each experiences a contact force by the other. Fric…

**q05-inertia-bus** (en) — why do I get thrown backwards when a bus suddenly starts moving
  1. ✅ phy11-part1 ch 4 §4.4 ¶8 · both · similarity 0.532 · The property of inertia contained in the First law is evident in many situations. Suppose…
  2. phy12-part2 ch 9 §9.2.3 ¶24 · both · similarity 0.399 · Although the jogger has been moving with a constant speed, the speed of his/her image app…
  3. phy11-part2 ch 9 §9.4.2 ¶3 · text · (ii) Ball moving with spin: A ball which is spinning drags air along with it. If the surf…

**q06-car-turning-flat-road** (en) — what stops a car from skidding sideways when it takes a turn on a flat road
  1. phy11-part1 ch 4 §4.9.1 ¶16 · both · similarity 0.364 · In many practical situations, however, friction is critically needed. Kinetic friction th…
  2. ✅ phy11-part1 ch 4 §4.10 ¶18 · both · similarity 0.365 · Answer On an unbanked road, frictional force alone can provide the centripetal force need…
  3. phy11-part1 ch 4 §4.10 ¶20 · vector · similarity 0.442 · Answer On a banked road, the horizontal component of the normal force and the frictional …

**q07-zero-work** (en) — if I carry my bag across a flat room have I done any work on it
  1. phy11-part1 ch 5 §5.3 ¶3 · vector · similarity 0.381 · We see that if there is no displacement, there is no work done even if the force is large…
  2. phy11-part2 ch 11 §11.10 ¶1 · text · Imagine some process in which a thermodynamic system goes from an initial state i to a fi…
  3. phy11-part1 ch 5 §5.3 ¶5 · vector · similarity 0.367 · (i) the displacement is zero as seen in the example above. A weightlifter holding a 150 k…

**q08-rotational-kinetic-energy** (en) — a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one
  1. phy11-part1 ch 6 §6.9 ¶1 · both · similarity 0.536 · We have already mentioned that we are developing the study of rotational motion parallel …
  2. ✅ phy11-part1 ch 6 §6.9 ¶2 · both · similarity 0.598 · where m_i is the mass of the particle. The total kinetic energy K of the body is then giv…
  3. phy11-part2 ch 13 §13.7 ¶2 · both · similarity 0.478 · In section 13.5 we have seen that the velocity of a particle executing SHM, is a periodic…

**q09-moment-of-inertia-hi** (hi) — किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है
  1. phy11-part2 ch 10 §10.6 ¶1 · vector · similarity 0.363 · Take some water in a vessel and start heating it on a burner. Soon you will notice that b…
  2. phy11-part1 ch 4 §4.1 ¶2 · vector · similarity 0.309 · Let us first guess the answer based on our common experience. To move a football at rest,…
  3. phy11-part1 ch 4 §4.5 ¶5 · vector · similarity 0.305 · • If two stones, one light and the other heavy, are dropped from the top of a building, a…

**q10-angular-momentum-conservation** (en) — why does a skater spin faster when she pulls her arms in
  1. ✅ phy11-part1 ch 6 §6.12.1 ¶3 · both · similarity 0.403 · This then is the required form, for fixed axis rotation, of Eq. (6.29a), which expresses …
  2. phy11-part1 ch 6 §6.12.1 ¶4 · both · similarity 0.347 · A circus acrobat and a diver take advantage of this principle. Also, skaters and classica…
  3. phy11-part2 ch 9 §9.4.2 ¶3 · both · similarity 0.352 · (ii) Ball moving with spin: A ball which is spinning drags air along with it. If the surf…

**q11-escape-speed** (en) — how fast does a rocket have to go to leave the earth for good
  1. ✅ phy11-part1 ch 7 §7.8 ¶6 · vector · similarity 0.463 · The minimum value of V_i corresponds to the case when the L.H.S. of Eq. (7.29) equals zer…
  2. phy11-part2 ch 10 §10.9.1 ¶5 · text · Compare the relatively large thermal conductivities of good thermal conductors and, metal…
  3. phy11-part1 ch 7 §7.8 ¶10 · vector · similarity 0.453 · Equation (7.32) applies equally well to an object thrown from the surface of the moon wit…

**q12-dimensional-analysis-hi** (hi) — विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं
  - nothing above the similarity floor

**q13-inertia-hi** (hi) — जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है
  1. ✅ phy11-part1 ch 4 §4.4 ¶2 · vector · similarity 0.408 · Newton built on Galileo’s ideas and laid the foundation of mechanics in terms of three la…
  2. phy11-part2 ch 14 §14.4.2 ¶5 · vector · similarity 0.405 · This relation was first given by Newton and is known as Newton's formula.
  3. ✅ phy11-part1 ch 4 §4.4 ¶8 · vector · similarity 0.351 · The property of inertia contained in the First law is evident in many situations. Suppose…

**q14-momentum-conservation-hi** (hi) — बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है
  1. phy11-part1 ch 4 §4.5 ¶20 · vector · similarity 0.325 · Answer The retardation ‘a’ of the bullet (assumed constant) is given by a = -u^2 / 2s = (…
  2. ✅ phy11-part1 ch 4 §4.7 ¶1 · vector · similarity 0.323 · The second and third laws of motion lead to an important consequence: the law of conserva…
  3. phy11-part1 ch 5 §5.8 ¶16 · vector · similarity 0.315 · At point C, the string becomes slack and the velocity of the bob is horizontal and to the…

**q15-gravity-with-depth-hi** (hi) — पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है
  1. ✅ phy11-part1 ch 7 §7.6 ¶11 · vector · similarity 0.433 · Thus, as we go down below earth's surface, the acceleration due gravity decreases by a fa…
  2. phy11-part1 ch 7 §7.6 ¶6 · vector · similarity 0.361 · Now, consider a point mass m at a depth d below the surface of the earth (Fig. 7.8(b)), s…
  3. phy11-part1 ch 7 §7.6 ¶5 · vector · similarity 0.343 · Equation (7.15) thus tells us that for small heights h above the value of g decreases by …
