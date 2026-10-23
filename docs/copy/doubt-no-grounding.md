# Doubt with no NCERT grounding — the reply copy

SPEC §6.3 (amended 2026-10-23): "When a doubt finds no NCERT grounding, the app says so honestly, naming, where it
knows, that the current book doesn't teach the topic, and offers what it can do instead." This file holds that
reply's copy until D37 builds it into the server's `messages_{en,hi,hinglish}.properties` (TECH_PLAN §3.3).

**Status.**
- **English, variants A and B:** approved by the founder, 2026-10-23 (DECISIONS).
- **Hindi:** Claude's draft, held for the founder's native-reader review at D64 before it ships (his ruling, 2026-10-23).
- **Hinglish:** Claude's draft, added on his instruction, reviewed with the Hindi at D64 (Claude's placement).

**When it is shown.** Retrieval finds no paragraph above the floor, even after the retry without the node filter (TECH_PLAN §4.3 stage 6, a grounding failure).
- **Variant A:** the doubt's topic, as the router classifies it, is on the topic lookup list. The list holds the NMC 2026 clauses the current NCERT does not teach, drafted by Claude from the 2026-10-23 note's diff for the founder's approval before D37. Each entry gives what the book teaches nearby and whether an extension source is planned.
- **Variant B:** anything else.
- **The "We're adding …" line** of variant A shows only where a source of that kind is planned (the founder, 2026-10-23):
  - the earlier-edition line, where an earlier-edition section is planned;
  - the Lab Manual line, where a Lab Manual experiment is (salt analysis, the vernier);
  - neither, where no source is planned.

**The product rule** (the founder, 2026-10-23): a doubt the app cannot ground never counts against the free doubts.
- The line "This didn't use one of today's free doubts" is shown only to a student whose doubts are limited, the Free tier. Pro has no free-doubt limit (SPEC §6.9), and in the beta every student has Pro (the founder, 2026-10-23).

**The syllabus chip** ("See where {topic} sits in your syllabus") is dropped (the founder, 2026-10-23); variant A keeps its one chip.

Braces mark the values filled in: `{topic}` is the topic's name, and `{nearby concept}` is what the book teaches nearby, both from the lookup list. Square brackets mark tappable chips.

## Variant A — a topic the current NCERT does not teach

**English (approved)**

> This is about {topic}. NEET's syllabus still lists it, but your current NCERT book doesn't teach it — so I can't
> give you a step-by-step answer backed by your textbook, and I won't guess.
>
> What I can do now:
> [Explain {nearby concept} — it's in your book]
>
> We're adding NCERT's earlier-edition text for topics like this, so I'll be able to answer from it later.
> *(or, for a Lab Manual topic:)* We're adding NCERT's Lab Manual text for topics like this, so I'll be able to
> answer from it later.
> This didn't use one of today's free doubts.

**Hindi (draft — for the founder's review at D64)**

> यह सवाल {topic} पर है। NEET के सिलेबस में यह विषय अब भी है, पर आपकी मौजूदा NCERT किताब इसे नहीं पढ़ाती — इसलिए
> आपकी पाठ्यपुस्तक के आधार पर इसका step-by-step हल नहीं दिया जा सकता, और अंदाज़े से जवाब नहीं दिया जाएगा।
>
> अभी यह हो सकता है:
> [{nearby concept} समझें — यह आपकी किताब में है]
>
> ऐसे विषयों के लिए NCERT के पिछले संस्करण का पाठ जोड़ा जा रहा है, ताकि आगे इनका जवाब उसी से दिया जा सके।
> *(या, प्रयोगशाला पुस्तिका वाले विषय के लिए:)* ऐसे विषयों के लिए NCERT की प्रयोगशाला पुस्तिका का पाठ जोड़ा जा रहा है,
> ताकि आगे इनका जवाब उसी से दिया जा सके।
> यह सवाल आज के मुफ़्त सवालों में नहीं गिना गया।

**Hinglish (draft — for review at D64)**

> Yeh sawaal {topic} par hai. NEET ke syllabus mein yeh topic abhi bhi hai, par aapki current NCERT book ise nahi
> padhati — isliye aapki textbook ke basis par iska step-by-step answer nahi diya ja sakta, aur andaaze se jawab
> nahi diya jayega.
>
> Abhi yeh ho sakta hai:
> [{nearby concept} samjhein — yeh aapki book mein hai]
>
> Aise topics ke liye NCERT ke pichhle edition ka text joda ja raha hai, taaki aage inka jawab usi se diya ja sake.
> *(ya, Lab Manual wale topic ke liye:)* Aise topics ke liye NCERT ke Lab Manual ka text joda ja raha hai, taaki aage
> inka jawab usi se diya ja sake.
> Yeh sawaal aaj ke free doubts mein nahi gina gaya.

## Variant B — not found in the NCERT books

**English (approved)**

> I couldn't find this in your NCERT books, so I won't guess an answer.
>
> [Ask it another way — naming the chapter often helps]
>
> If you think it's in your book, tap Report and we'll check.
> This didn't use one of today's free doubts.

**Hindi (draft — for the founder's review at D64)**

> यह सवाल आपकी NCERT किताबों में नहीं मिला, इसलिए अंदाज़े से जवाब नहीं दिया जाएगा।
>
> [इसे दूसरे शब्दों में पूछें — अध्याय का नाम बताने से अक्सर मदद मिलती है]
>
> अगर आपको लगता है कि यह आपकी किताब में है, तो Report पर टैप करें — हम जाँच करेंगे।
> यह सवाल आज के मुफ़्त सवालों में नहीं गिना गया।

**Hinglish (draft — for review at D64)**

> Yeh sawaal aapki NCERT books mein nahi mila, isliye andaaze se jawab nahi diya jayega.
>
> [Ise doosre shabdon mein poochhein — chapter ka naam batane se aksar madad milti hai]
>
> Agar aapko lagta hai ki yeh aapki book mein hai, toh Report par tap karein — hum check karenge.
> Yeh sawaal aaj ke free doubts mein nahi gina gaya.

## Notes

- **No gender:** the Hindi and Hinglish use an impersonal form ("दिया जा सकता", "diya ja sakta") rather than a first-person verb, so the app takes no gender.
- **Encoding:** this file is Unicode Devanagari, checked 2026-10-23 with every vowel sign present. A terminal or chat window that drops combining marks shows words such as "सिलेबस" broken. Review the text from this file, not from a paste.
