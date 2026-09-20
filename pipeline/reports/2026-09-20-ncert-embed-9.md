# margai-pipeline ncert embed

- run: 2026-09-20 16:35 IST
- input: ../pipeline/inputs/books.yaml
- sha256: 43094de04766d480a31d739fac3a7135d697568a2cc78ef7ebe587685e3b6b5c
- read: 880 paragraph(s) waiting for a vector (--redo: every selected paragraph, embedded or not)
- result: ok
request id: pipeline-ncert-embed-2bd1cbc5-8a8d-4d12-961a-19a11ddab4ef
chunk: one paragraph, its English text as loaded (§6.4)
embedding input: the section title prefixed to the paragraph, fragments under 40 characters skipped (D15 experiment; 79 titles read)
concept queries: 15 from ../eval/retrieval-queries.json (5 in Hindi)

## cost

| calls | input tokens | spent |
|---|---|---|
| 895 | 85032 | ₹8.95 |

## paragraphs embedded per chapter

| chapter | embedded |
|---|---|
| 1 | 83 |
| 2 | 52 |
| 3 | 102 |
| 4 | 133 |
| 5 | 125 |
| 6 | 280 |
| 7 | 105 |

## fragments left unembedded (too short to answer anything alone)

14 of them carried a vector from an earlier run and it was dropped, so they leave the index rather than only missing the new one
a vector for "Answer" or "No work is done if :" can never be usefully retrieved and competes for a place in the top k
- ch 1 §1.3 ¶6 — "The example gives the following rules :"
- ch 2 §2.4 ¶15 — "Solving, we get t_2 = 3 s"
- ch 3 §3.5 ¶9 — "(ii) its components A_x and A_y"
- ch 4 §4.10 ¶5 — "(i) The weight of the car, mg"
- ch 4 §4.10 ¶6 — "(ii) Normal reaction, N"
- ch 4 §4.10 ¶7 — "(iii) Frictional force, f"
- ch 4 §4.10 ¶13 — "But f ≤ mu_s N"
- ch 4 §4.11 ¶9 — "Answer"
- ch 4 §4.11 ¶13 — "Action-reaction pairs"
- ch 5 §5.3 ¶4 — "No work is done if :"
- ch 6 §6.10 ¶12 — "Answer"
- ch 6 §6.11 ¶24 — "Answer"
- ch 6 §6.12 ¶15 — "Now, Eq. (6.28b) states dL/dt = tau"
- ch 7 §7.9 ¶7 — "Which is approximately 85 minutes."

## ncert_paragraphs.embedding

| embedded this run | still without a vector |
|---|---|
| 880 | 0 |

## paragraphs still without a vector

none — every English paragraph of the book is embedded

## concept queries — the score (PLAN D15 ✅)

| set | queries | hit@1 | hit@3 | MRR |
|---|---|---|---|---|
| all | 15 | 8/15 (53%) | 9/15 (60%) | 0.591 |
| english | 10 | 5/10 (50%) | 6/10 (60%) | 0.587 |
| hindi → english paragraphs | 5 | 3/5 (60%) | 3/5 (60%) | 0.600 |

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
| q01-significant-figures | en | 10 |  | [ch 1 §1.3.1 ¶2] | if I multiply two measured lengths how many digits should I keep in the answer |
| q02-speed-vs-velocity | en | 1 |  | [ch 2 §2.2 ¶10] | is speed the same thing as velocity or is there a difference |
| q03-displacement-vs-path | en | 5 |  | [ch 3 §3.2.1 ¶2] | if I walk around a park and come back to where I started how much have I moved |
| q04-friction-contact-area | en | 1 |  | [ch 4 §4.9.1 ¶2] | does friction depend on how much surface area is touching |
| q05-inertia-bus | en | 1 |  | [ch 4 §4.4 ¶8] | why do I get thrown backwards when a bus suddenly starts moving |
| q06-car-turning-flat-road | en | 8 |  | [ch 4 §4.10 ¶9, ch 4 §4.10 ¶18] | what stops a car from skidding sideways when it takes a turn on a flat road |
| q07-zero-work | en | 9 |  | [ch 5 §5.3 ¶7] | if I carry my bag across a flat room have I done any work on it |
| q08-rotational-kinetic-energy | en | 1 |  | [ch 6 §6.9 ¶2, ch 6 §6.9 ¶4, ch 6 §6.9 ¶6] | a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one |
| q09-moment-of-inertia-hi | hi | miss |  | [ch 6 §6.9 ¶13, ch 6 §6.9 ¶11] | किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है |
| q10-angular-momentum-conservation | en | 1 |  | [ch 6 §6.12.1 ¶3] | why does a skater spin faster when she pulls her arms in |
| q11-escape-speed | en | 3 |  | [ch 7 §7.8 ¶9, ch 7 §7.8 ¶6] | how fast does a rocket have to go to leave the earth for good |
| q12-dimensional-analysis-hi | hi | miss | grounding failure | [ch 1 §1.6.2 ¶9, ch 1 §1.6 ¶1] | विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं |
| q13-inertia-hi | hi | 1 |  | [ch 4 §4.4 ¶8, ch 4 §4.4 ¶2] | जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है |
| q14-momentum-conservation-hi | hi | 1 |  | [ch 4 §4.7 ¶1] | बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है |
| q15-gravity-with-depth-hi | hi | 1 |  | [ch 7 §7.6 ¶11] | पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है |

## top 3 passages per query


**q01-significant-figures** (en) — if I multiply two measured lengths how many digits should I keep in the answer
  1. phy11-part1 ch 1 §1.3 ¶1 · both · similarity 0.505 · Every measurement involves errors. Thus, the result of measurement should be reported in …
  2. phy11-part1 ch 1 §1.3 ¶21 · both · similarity 0.492 · (6) The multiplying or dividing factors which are neither rounded numbers nor numbers rep…
  3. phy11-part1 ch 1 §1.3 ¶13 · both · similarity 0.486 · (2) There can be some confusion regarding the trailing zero(s). Suppose a length is repor…

**q02-speed-vs-velocity** (en) — is speed the same thing as velocity or is there a difference
  1. ✅ phy11-part1 ch 2 §2.2 ¶10 · both · similarity 0.565 · Instantaneous speed or simply speed is the magnitude of velocity. For example, a velocity…
  2. phy11-part1 ch 2 §2.2 ¶1 · both · similarity 0.514 · The average velocity tells us how fast an object has been moving over a given time interv…
  3. phy11-part1 ch 1 §1.4 ¶5 · both · similarity 0.446 · Note that in this type of representation, the magnitudes are not considered. It is the qu…

**q03-displacement-vs-path** (en) — if I walk around a park and come back to where I started how much have I moved
  1. phy11-part1 ch 2 §2.1 ¶1 · vector · similarity 0.363 · Motion is common to everything in the universe. We walk, run and ride a bicycle. Even whe…
  2. phy11-part1 ch 4 §4.4 ¶8 · text · The property of inertia contained in the First law is evident in many situations. Suppose…
  3. phy11-part1 ch 3 §3.7.1 ¶2 · vector · similarity 0.344 · Suppose a particle moves along the curve shown by the thick line and is at P at time t an…

**q04-friction-contact-area** (en) — does friction depend on how much surface area is touching
  1. ✅ phy11-part1 ch 4 §4.9.1 ¶2 · both · similarity 0.485 · We know from experience that as the applied force exceeds a certain limit, the body begin…
  2. phy11-part1 ch 4 §4.9.1 ¶14 · both · similarity 0.406 · Rolling friction again has a complex origin, though somewhat different from that of stati…
  3. phy11-part1 ch 4 §4.9.1 ¶4 · both · similarity 0.426 · Thus, when two bodies are in contact, each experiences a contact force by the other. Fric…

**q05-inertia-bus** (en) — why do I get thrown backwards when a bus suddenly starts moving
  1. ✅ phy11-part1 ch 4 §4.4 ¶8 · both · similarity 0.560 · The property of inertia contained in the First law is evident in many situations. Suppose…
  2. phy11-part1 ch 4 §4.4 ¶7 · both · similarity 0.295 · Consider the motion of a car starting from rest, picking up speed and then moving on a sm…
  3. phy11-part1 ch 4 §4.2 ¶4 · vector · similarity 0.339 · What is the flaw in Aristotle’s argument? The answer is: a moving toy car comes to rest b…

**q06-car-turning-flat-road** (en) — what stops a car from skidding sideways when it takes a turn on a flat road
  1. phy11-part1 ch 4 §4.10 ¶17 · both · similarity 0.371 · Example 4.10 A cyclist speeding at 18 km/h on a level road takes a sharp circular turn of…
  2. phy11-part1 ch 4 §4.9.1 ¶16 · both · similarity 0.371 · In many practical situations, however, friction is critically needed. Kinetic friction th…
  3. phy11-part1 ch 4 §4.10 ¶15 · both · similarity 0.363 · Then Eqs. (4.19a) and (4.19b) become N cos theta = mg + mu_s N sin theta (4.20a) N sin th…

**q07-zero-work** (en) — if I carry my bag across a flat room have I done any work on it
  1. phy11-part1 ch 5 §5.7 ¶5 · both · similarity 0.299 · The work done by a conservative force such as gravity depends on the initial and final po…
  2. phy11-part1 ch 5 §5.3 ¶3 · vector · similarity 0.387 · We see that if there is no displacement, there is no work done even if the force is large…
  3. phy11-part1 ch 5 §5.10 ¶1 · text · Often it is interesting to know not only the work done on an object, but also the rate at…

**q08-rotational-kinetic-energy** (en) — a spinning wheel is made of many particles so what is its total kinetic energy 1/2 mv^2 for each one
  1. ✅ phy11-part1 ch 6 §6.9 ¶2 · both · similarity 0.592 · where m_i is the mass of the particle. The total kinetic energy K of the body is then giv…
  2. phy11-part1 ch 6 §6.9 ¶1 · both · similarity 0.538 · We have already mentioned that we are developing the study of rotational motion parallel …
  3. phy11-part1 ch 6 §6.11 ¶27 · both · similarity 0.529 · (c) Let omega be the final angular velocity. The kinetic energy gained = (1/2) I omega^2,…

**q09-moment-of-inertia-hi** (hi) — किसी वस्तु को घुमाना शुरू करना कितना कठिन होगा यह किन बातों पर निर्भर करता है
  1. phy11-part1 ch 4 §4.1 ¶2 · vector · similarity 0.322 · Let us first guess the answer based on our common experience. To move a football at rest,…
  2. phy11-part1 ch 6 §6.1.1 ¶1 · vector · similarity 0.286 · Let us try to explore this question by taking some examples of the motion of rigid bodies…
  3. phy11-part1 ch 4 §4.5 ¶5 · vector · similarity 0.271 · • If two stones, one light and the other heavy, are dropped from the top of a building, a…

**q10-angular-momentum-conservation** (en) — why does a skater spin faster when she pulls her arms in
  1. ✅ phy11-part1 ch 6 §6.12.1 ¶3 · both · similarity 0.360 · This then is the required form, for fixed axis rotation, of Eq. (6.29a), which expresses …
  2. phy11-part1 ch 4 §4.5 ¶7 · vector · similarity 0.384 · • A seasoned cricketer catches a cricket ball coming in with great speed far more easily …
  3. phy11-part1 ch 6 §6.8.1 ¶8 · text · If the effort arm d_2 is larger than the load arm, the mechanical advantage is greater th…

**q11-escape-speed** (en) — how fast does a rocket have to go to leave the earth for good
  1. phy11-part1 ch 7 §7.8 ¶7 · vector · similarity 0.472 · If the object is thrown from the surface of the earth, h = 0, and we get (V_i)_min = sqrt…
  2. phy11-part1 ch 7 §7.1 ¶1 · text · Early in our lives, we become aware of the tendency of all material objects to be attract…
  3. ✅ phy11-part1 ch 7 §7.8 ¶6 · vector · similarity 0.467 · The minimum value of V_i corresponds to the case when the L.H.S. of Eq. (7.29) equals zer…

**q12-dimensional-analysis-hi** (hi) — विमीय विश्लेषण से किसी समीकरण के बारे में क्या पता चलता है और क्या नहीं
  - nothing above the similarity floor

**q13-inertia-hi** (hi) — जड़त्व किसे कहते हैं और न्यूटन के पहले नियम से इसका क्या संबंध है
  1. ✅ phy11-part1 ch 4 §4.4 ¶2 · vector · similarity 0.409 · Newton built on Galileo’s ideas and laid the foundation of mechanics in terms of three la…
  2. ✅ phy11-part1 ch 4 §4.4 ¶8 · vector · similarity 0.398 · The property of inertia contained in the First law is evident in many situations. Suppose…
  3. phy11-part1 ch 4 §4.4 ¶6 · vector · similarity 0.375 · Consider a book at rest on a horizontal surface Fig. (4.2(a)). It is subject to two exter…

**q14-momentum-conservation-hi** (hi) — बंदूक से गोली चलाने पर बंदूक पीछे क्यों हटती है
  1. ✅ phy11-part1 ch 4 §4.7 ¶1 · vector · similarity 0.322 · The second and third laws of motion lead to an important consequence: the law of conserva…
  2. phy11-part1 ch 4 §4.5 ¶20 · vector · similarity 0.305 · Answer The retardation ‘a’ of the bullet (assumed constant) is given by a = -u^2 / 2s = (…
  3. phy11-part1 ch 5 §5.8 ¶16 · vector · similarity 0.286 · At point C, the string becomes slack and the velocity of the bob is horizontal and to the…

**q15-gravity-with-depth-hi** (hi) — पृथ्वी की सतह से नीचे जाने पर गुरुत्वीय त्वरण का मान कैसे बदलता है
  1. ✅ phy11-part1 ch 7 §7.6 ¶11 · vector · similarity 0.422 · Thus, as we go down below earth's surface, the acceleration due gravity decreases by a fa…
  2. phy11-part1 ch 7 §7.6 ¶5 · vector · similarity 0.383 · Equation (7.15) thus tells us that for small heights h above the value of g decreases by …
  3. phy11-part1 ch 7 §7.6 ¶6 · vector · similarity 0.381 · Now, consider a point mass m at a depth d below the surface of the earth (Fig. 7.8(b)), s…
