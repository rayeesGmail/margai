# margai-pipeline ncert embed

- run: 2026-09-25 23:00 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 19 paragraph(s) waiting for a vector
- result: ok
request id: pipeline-ncert-embed-d5a84ec4-2495-4219-b063-0415e3973127
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the paragraph's own text_en, and nothing else (§6.4)
concept queries: 15 from ../eval/retrieval-queries.json (5 in Hindi)

## cost

| calls | input tokens | spent |
|---|---|---|
| 34 | 2175 | ₹0.34 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 0 |
| 2 | 6 |
| 3 | 0 |
| 4 | 4 |
| 5 | 3 |
| 6 | 2 |
| 7 | 4 |

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 19 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

## concept queries — the score (PLAN D15 ✅)

| set | queries | hit@1 | hit@3 | MRR |
|---|---|---|---|---|
| all | 15 | 8/15 (53%) | 10/15 (67%) | 0.629 |
| english | 10 | 6/10 (60%) | 7/10 (70%) | 0.693 |
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
| q01-significant-figures | en | 5 |  | [ch 1 §1.3.1 ¶2] | if I multiply two measured lengths how many digits should I keep in the answer |
| q02-speed-vs-velocity | en | 1 |  | [ch 2 §2.2 ¶10] | is speed the same thing as velocity or is there a difference |
| q03-displacement-vs-path | en | 7 |  | [ch 3 §3.2.1 ¶2] | if I walk around a park and come back to where I started how much have I moved |
| q04-friction-contact-area | en | 1 |  | [ch 4 §4.9.1 ¶2] | does friction depend on how much surface area is touching |
| q05-inertia-bus | en | 1 |  | [ch 4 §4.4 ¶8] | why do I get thrown backwards when a bus suddenly starts moving |
| q06-car-turning-flat-road | en | 2 |  | [ch 4 §4.10 ¶9, ch 4 §4.10 ¶18] | what stops a car from skidding sideways when it takes a turn on a flat road |
| q07-zero-work | en | 11 |  | [ch 5 §5.3 ¶7] | if I carry my bag across a flat room have I done any work on it |
| q08-rotational-kinetic-energy | en | 1 |  | [ch 6 §6.9 ¶2, ch 6 §6.9 ¶4, ch 6 §6.9 ¶6] | a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one |
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
  3. phy11-part1 ch 1 §1.3 ¶21 · both · similarity 0.490 · (6) The multiplying or dividing factors which are neither rounded numbers nor numbers rep…

**q02-speed-vs-velocity** (en) — is speed the same thing as velocity or is there a difference
  1. ✅ phy11-part1 ch 2 §2.2 ¶10 · both · similarity 0.562 · Instantaneous speed or simply speed is the magnitude of velocity. For example, a velocity…
  2. phy11-part1 ch 1 §1.4 ¶5 · both · similarity 0.516 · Note that in this type of representation, the magnitudes are not considered. It is the qu…
  3. phy11-part1 ch 2 §2.2 ¶1 · both · similarity 0.474 · The average velocity tells us how fast an object has been moving over a given time interv…

**q03-displacement-vs-path** (en) — if I walk around a park and come back to where I started how much have I moved
  1. phy11-part1 ch 3 §3.7.1 ¶2 · vector · similarity 0.372 · Suppose a particle moves along the curve shown by the thick line and is at P at time t an…
  2. phy11-part1 ch 4 §4.4 ¶8 · text · The property of inertia contained in the First law is evident in many situations. Suppose…
  3. phy11-part1 ch 5 §5.5 ¶5 · vector · similarity 0.365 · Example 5.5 A woman pushes a trunk on a railway platform which has a rough surface. She a…

**q04-friction-contact-area** (en) — does friction depend on how much surface area is touching
  1. ✅ phy11-part1 ch 4 §4.9.1 ¶2 · both · similarity 0.478 · We know from experience that as the applied force exceeds a certain limit, the body begin…
  2. phy11-part1 ch 4 §4.9.1 ¶4 · both · similarity 0.417 · Thus, when two bodies are in contact, each experiences a contact force by the other. Fric…
  3. phy11-part1 ch 4 §4.9.1 ¶14 · both · similarity 0.411 · Rolling friction again has a complex origin, though somewhat different from that of stati…

**q05-inertia-bus** (en) — why do I get thrown backwards when a bus suddenly starts moving
  1. ✅ phy11-part1 ch 4 §4.4 ¶8 · both · similarity 0.532 · The property of inertia contained in the First law is evident in many situations. Suppose…
  2. phy11-part1 ch 4 §4.4 ¶7 · both · similarity 0.295 · Consider the motion of a car starting from rest, picking up speed and then moving on a sm…
  3. phy11-part1 ch 4 §4.2 ¶4 · vector · similarity 0.318 · What is the flaw in Aristotle’s argument? The answer is: a moving toy car comes to rest b…

**q06-car-turning-flat-road** (en) — what stops a car from skidding sideways when it takes a turn on a flat road
  1. phy11-part1 ch 4 §4.9.1 ¶16 · both · similarity 0.364 · In many practical situations, however, friction is critically needed. Kinetic friction th…
  2. ✅ phy11-part1 ch 4 §4.10 ¶18 · both · similarity 0.365 · Answer On an unbanked road, frictional force alone can provide the centripetal force need…
  3. phy11-part1 ch 4 §4.10 ¶20 · vector · similarity 0.441 · Answer On a banked road, the horizontal component of the normal force and the frictional …

**q07-zero-work** (en) — if I carry my bag across a flat room have I done any work on it
  1. phy11-part1 ch 5 §5.3 ¶3 · vector · similarity 0.382 · We see that if there is no displacement, there is no work done even if the force is large…
  2. phy11-part1 ch 5 §5.10 ¶1 · text · Often it is interesting to know not only the work done on an object, but also the rate at…
  3. phy11-part1 ch 5 §5.3 ¶5 · vector · similarity 0.369 · (i) the displacement is zero as seen in the example above. A weightlifter holding a 150 k…

**q08-rotational-kinetic-energy** (en) — a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one
  1. ✅ phy11-part1 ch 6 §6.9 ¶2 · both · similarity 0.598 · where m_i is the mass of the particle. The total kinetic energy K of the body is then giv…
  2. phy11-part1 ch 6 §6.9 ¶1 · both · similarity 0.536 · We have already mentioned that we are developing the study of rotational motion parallel …
  3. phy11-part1 ch 6 §6.11 ¶27 · both · similarity 0.525 · (c) Let omega be the final angular velocity. The kinetic energy gained = (1/2) I omega^2,…

**q09-moment-of-inertia-hi** (hi) — किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है
  1. phy11-part1 ch 4 §4.1 ¶2 · vector · similarity 0.310 · Let us first guess the answer based on our common experience. To move a football at rest,…
  2. phy11-part1 ch 4 §4.5 ¶5 · vector · similarity 0.306 · • If two stones, one light and the other heavy, are dropped from the top of a building, a…
  3. phy11-part1 ch 4 §4.5 ¶4 · vector · similarity 0.271 · • Suppose a light-weight vehicle (say a small car) and a heavy weight vehicle (say a load…

**q10-angular-momentum-conservation** (en) — why does a skater spin faster when she pulls her arms in
  1. ✅ phy11-part1 ch 6 §6.12.1 ¶3 · both · similarity 0.403 · This then is the required form, for fixed axis rotation, of Eq. (6.29a), which expresses …
  2. phy11-part1 ch 6 §6.12.1 ¶4 · both · similarity 0.347 · A circus acrobat and a diver take advantage of this principle. Also, skaters and classica…
  3. phy11-part1 ch 4 §4.5 ¶7 · vector · similarity 0.383 · • A seasoned cricketer catches a cricket ball coming in with great speed far more easily …

**q11-escape-speed** (en) — how fast does a rocket have to go to leave the earth for good
  1. ✅ phy11-part1 ch 7 §7.8 ¶6 · vector · similarity 0.463 · The minimum value of V_i corresponds to the case when the L.H.S. of Eq. (7.29) equals zer…
  2. phy11-part1 ch 7 §7.1 ¶1 · text · Early in our lives, we become aware of the tendency of all material objects to be attract…
  3. phy11-part1 ch 7 §7.8 ¶10 · vector · similarity 0.452 · Equation (7.32) applies equally well to an object thrown from the surface of the moon wit…

**q12-dimensional-analysis-hi** (hi) — विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं
  - nothing above the similarity floor

**q13-inertia-hi** (hi) — जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है
  1. ✅ phy11-part1 ch 4 §4.4 ¶2 · vector · similarity 0.408 · Newton built on Galileo’s ideas and laid the foundation of mechanics in terms of three la…
  2. ✅ phy11-part1 ch 4 §4.4 ¶8 · vector · similarity 0.351 · The property of inertia contained in the First law is evident in many situations. Suppose…
  3. phy11-part1 ch 4 §4.5 ¶1 · vector · similarity 0.348 · The first law refers to the simple case when the net external force on a body is zero. Th…

**q14-momentum-conservation-hi** (hi) — बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है
  1. phy11-part1 ch 4 §4.5 ¶20 · vector · similarity 0.325 · Answer The retardation ‘a’ of the bullet (assumed constant) is given by a = -u^2 / 2s = (…
  2. ✅ phy11-part1 ch 4 §4.7 ¶1 · vector · similarity 0.323 · The second and third laws of motion lead to an important consequence: the law of conserva…
  3. phy11-part1 ch 5 §5.8 ¶16 · vector · similarity 0.315 · At point C, the string becomes slack and the velocity of the bob is horizontal and to the…

**q15-gravity-with-depth-hi** (hi) — पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है
  1. ✅ phy11-part1 ch 7 §7.6 ¶11 · vector · similarity 0.433 · Thus, as we go down below earth's surface, the acceleration due gravity decreases by a fa…
  2. phy11-part1 ch 7 §7.6 ¶6 · vector · similarity 0.361 · Now, consider a point mass m at a depth d below the surface of the earth (Fig. 7.8(b)), s…
  3. phy11-part1 ch 7 §7.6 ¶5 · vector · similarity 0.343 · Equation (7.15) thus tells us that for small heights h above the value of g decreases by …
