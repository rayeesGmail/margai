# Change Spec CS-2 — Tiers, Pricing & Feature Roadmap v2

**Status:** Founder-approved product change, issued at ~D25 (2026-09-27). To be integrated by
Claude Code into the existing document system per §12. SPEC.md amendments listed in §12.1 are
authorized by this change spec.
**Intent in one line:** lock the plan/tier structure and pricing, add the product behaviors
decided since CS-1 (wellbeing protocol, auth model, learning-science guards, mocks, follow-ups,
WhatsApp policy), and sort every new feature idea into Phase 1 core, Phase 1 beta backlog,
Phase 2, later, or never.
**What this spec does not do:** it contains no implementation design. Architecture, schemas,
APIs, and UI construction remain Claude Code's to plan (plan first, then build).

---

## 1. Governing principles (apply to every section below)

1. **Correctness and care are never tiered.** Answer quality, verification, grounding,
   wellbeing support, streak care, and the mentor's tone are identical in Free, Pro, and Pro+.
   Tiers differ only in *quantity* (limits), *speed*, *depth features*, *formats* (video,
   voice), and *audiences* (parents). No model-selection menu, ever.
2. **Pro+ sells formats and audiences, never better truth.** A Pro and a Pro+ student asking
   the same doubt receive the identical verified solution.
3. **Beta evidence promotes; the calendar does not.** Backlog and later items move only when
   beta behavior or student demand justifies them.
4. **Scope guard.** The Phase 1 core list (§4) is committed. The beta backlog (§5) ships only
   if the D60 gate passes, in priority order. If the build slips, the backlog shrinks; the
   core list does not.
5. **Public surfaces show only what exists.** Until Pro+ ships, the app, site, and store
   listing show Free and Pro only.

## 2. Pricing and plans

### 2.1 Beta (December 2026 → mid-January 2027)
Free for all beta participants: 6 weeks of Pro at no cost. No payments collected.

### 2.2 Public launch (late January 2027)

| Plan | Paywall display |
|---|---|
| Free | ₹0 |
| Pro, monthly | ~~₹499~~ **₹299/month** — labelled "founding price" |
| Pro, annual | ~~₹3,999~~ **₹2,999/year** — highlighted as best value (₹250/month equivalent) |

### 2.3 Rules
- **Grandfathering:** a subscriber who joins at a founding price keeps it for as long as the
  subscription stays active. Founding means founding.
- **Founding expiry is evidence-triggered, not dated:** when season-one data shows strong
  conversion and low churn, the founder closes founding prices for *new* signups, who then pay
  list (₹499/month, ₹3,999/year). An intermediate launch offer (e.g. ₹399/month) is permitted.
  Founder decision; implementation must make this a configuration change, not a release.
- **Annual front-and-center from January** (exam-panic season).
- **GST:** season one operates as a proprietorship below the ₹20L threshold; no GST is charged.
  Pricing configuration must support switching to GST-inclusive display when registration
  becomes required.
- **Comparisons are always same-rung** (list vs list, founding vs founding) on any surface.

### 2.4 Pro+ (Phase 2, provisional)

| Plan | Founding | List |
|---|---|---|
| Pro+, monthly | ₹479/month | ₹799/month |
| Pro+, annual | ₹4,999/year | ₹6,499/year |

Provisional until tested with students and parents. Same grandfathering rules as Pro.

## 3. Tier entitlements (canonical)

| Feature | Free | Pro | Pro+ (Phase 2) |
|---|---|---|---|
| Onboarding, first plan, diagnostic test | ✓ | ✓ | ✓ |
| Snap a doubt during onboarding † | ✓ | ✓ | ✓ |
| Adaptive daily planner (nightly re-plan, reasons, plan chat) | ✓ Full | ✓ Full | ✓ Full |
| Practice engine (timed MCQs, instant solutions) | ✓ Full | ✓ Full | ✓ Full |
| Full past papers as timed mocks | ✓ | ✓ | ✓ |
| Mock autopsy | Summary | Full | Full |
| AI doubt solver | 5/day (cached = ½) | Unlimited (queued, never refused) | Unlimited |
| Follow-up questions on a doubt | Count ½ toward limit | Unlimited | Unlimited |
| Answer quality and verification | Same | Same | Same |
| Explanation depth toggle † | — | ✓ | ✓ |
| Voice input for doubts ‡ | ✓ | ✓ | ✓ |
| Error notebook | Last 30 errors | Full + mistake healing | Full + healing |
| Weekly report | Basic trajectory card | Deep report | Deep report |
| Mastery Map v1 † | ✓ | ✓ | ✓ |
| Streaks, mood and slump care | ✓ | ✓ | ✓ |
| Milestone cards, "I'm confused" button, focus timer † | ✓ | ✓ | ✓ |
| Wellbeing and crisis support | ✓ | ✓ | ✓ |
| Home-screen widget, shareable answer cards ‡ | ✓ | ✓ | ✓ |
| Mnemonics on demand ‡ | — | ✓ | ✓ |
| Priority speed at peak hours | — | ✓ | ✓ |
| Video answers (animated, EN/HI narration) | — | — | ✓ |
| Viva mode + teach-back | — | — | ✓ |
| Parent digest (weekly on WhatsApp) | — | — | ✓ |
| External mock-scorecard ingestion | — | — | ✓ |
| Personal formula/fact sheet | — | — | ✓ |
| Misconception flip cards | — | — | ✓ |
| WhatsApp support, data export | ✓ | ✓ | ✓ |
| One-tap cancel, 7-day refund | — | ✓ | ✓ |

† Phase 1 beta backlog (§5), ships only if the D60 gate holds. ‡ Phase 2 addition to Free/Pro (§6).
Entitlements must be configuration-driven so any feature can move between tiers without a release.

## 4. Phase 1 — core additions (committed)

### 4.1 Wellbeing and crisis protocol — BLOCKING for beta
- **Definition:** a specified, tested behavior for any student input (doubt thread, plan chat,
  mood signal) indicating serious distress or self-harm risk.
- **Required behavior:** a warm, human-toned acknowledgment; no clinical diagnosis; no
  lecturing; clear pointers to real help — Tele-MANAS (14416, India's national mental-health
  helpline) — and encouragement to talk to someone they trust. The study context is set aside
  in that moment; the app never pushes practice or streaks in the same response.
- **Rules:** never promise confidentiality outcomes or describe what authorities may do; never
  end or refuse the conversation; resources shown accurately and kept current.
- **Acceptance:** eval-suite cases covering direct, indirect, Hinglish, and Hindi phrasings;
  100% of cases produce the required behavior; founder reviews the copy; legal review of copy
  and data handling in the founder's lawyer hour.

### 4.2 Authentication and identity
- **Beta:** Google Sign-In primary; email fallback for exceptions; phone OTP dormant.
- **Public launch:** Google remains primary; phone OTP (SMS via DLT under the proprietorship,
  and/or WhatsApp OTP once Meta verification lands) becomes the second method.
- **Identity model:** one user, multiple linked credentials. Phone capture happens in-app after
  sign-in and links to the existing account.
- **Acceptance:** "sign in with Google, later add phone via OTP, still one user" with history
  intact; no path creates duplicate users.
- **Minors note:** beta cohort is recruited 18+; the parent-consent flow becomes live at public
  launch when SMS/WhatsApp OTP exists. Record the beta-era posture in DECISIONS.

### 4.3 Pro is never refused
When a Pro user reaches the per-user budget breaker or fair-use cap, the request is accepted and
queued with honest copy ("answer in a few minutes"), never rejected. Free tier keeps its hard
limit. Breaker amount stays ₹25/user/day.

### 4.4 Anchored doubt follow-ups
- Follow-ups are threaded under the doubt they belong to, with context kept.
- Free tier: a follow-up counts ½ toward the daily limit.
- Threads have a sensible depth cap, after which the student is invited to ask a new doubt.
- **No open, general-purpose chatbot tab.** The only other conversation is the plan chat.

### 4.5 Streaks — repair framing and light days
- Streak = consecutive days with at least one plan block completed.
- Light-plan days on low mood or inferred slump are framed as protecting the streak.
- A broken streak is reported as a ratio ("12 of the last 14 days"), never as a loss.
- The 20:30 streak-save nudge names one small action; never threatens.
- No streak leaderboards; identical in all tiers; streaks retire gracefully in exam season.

### 4.6 Learning-science behavior guards
- **Try once more:** on a near-miss answer, offer one retry before revealing the solution
  (occasionally, not every time).
- **Your turn:** on a third doubt about the same concept within a short window, the mentor
  sometimes responds "walk me through where you got stuck" before answering.
- **Confidence question:** after an error streak or on low-mood days, serve one question
  slightly below the student's band before returning to stretch difficulty.
- **Mixed practice by default:** plans keep interleaving across subjects; a student may request
  a single-topic set, but the default stays mixed.
- **Explain the why occasionally:** the mentor explains its reasoning for rest and sleep
  recommendations.
- **Acceptance:** each guard is config-tunable (frequency, thresholds) and measurable.

### 4.7 Full past papers as timed mocks
- NEET 2018–2026 papers (from the PYQ bank) available as full timed mocks under exam
  conditions, followed by the mock autopsy.
- Out-of-syllabus questions (post-2022 rationalization) are excluded from mock scoring and
  labelled; question count and marking follow each paper's original scheme where feasible.
- Free: full mock + autopsy summary. Pro/Pro+: full autopsy.

### 4.8 Pricing display and paywall
Implements §2 exactly: struck list price, founding price, annual highlighted, honest copy ("No
hidden charges · Cancel in one tap · 7-day instant refund"). Paywall triggers unchanged.
Founding/list switch and GST display are configuration.

### 4.9 WhatsApp — zero-API pieces
- Click-to-chat WhatsApp support link in Profile → Support.
- Share-to-WhatsApp via the OS share sheet for milestone cards, answer cards (Phase 2), and
  referral links.
- **Policy:** WhatsApp is for support, sharing, OTP delivery, and parent communication — never
  for the student's learning loop (no doubt-solving bot, no study nudges over WhatsApp).

### 4.10 Learning-outcome metrics
Instrument from day one, alongside engagement metrics: errors healed per active week; mock-score
change over rolling four-week windows for active users; share of doubts on previously-healed
concepts (relapse rate). Dashboards per SPEC §11.

### 4.11 AI provider resilience
Direct Anthropic API remains primary. Bedrock (access now restored) is configured as a
**fallback provider only**, switchable by configuration during an outage. No primary switch.
PARKED "revisit Bedrock" item closes as "resolved; fallback only."

## 5. Phase 1 — beta backlog (gated on the D60 gate)

**D60 gate:** the full daily loop runs unattended for three consecutive real days on the
founder's test account. Backlog items ship only if it passes, in this priority order, behind a
configuration flag each, with one success metric each.

| # | Feature | Definition | Guards | Metric |
|---|---|---|---|---|
| 1 | **Snap a doubt during onboarding** | After the first-plan reveal, invite the student to photograph or type any current doubt | Optional, skippable; never delays the plan reveal | % of new users solving a doubt on day 0; D7 delta |
| 2 | **Explanation depth toggle** | Simpler / Deeper / Example chips on any answer; re-expresses the verified answer at a different register | Simpler ends with one retrieval question; Example numericals pass standard verification; counts ½ on the free meter; renderings cached with the parent answer | Toggle usage rate; retrieval-check accuracy |
| 3 | **"I'm confused" button** | One-tap feeling signal during practice or on an answer; offers a short concept reset or an easier question | Never costs meter; no judgment in copy; one tap adjusts a session, a pattern adjusts a plan | Taps per session; subsequent-accuracy delta |
| 4 | **Mastery Map v1** | The syllabus tree colored by mastery, with a chapter detail sheet (topics' status, what it unlocks, open errors) | **Frozen scope:** colored tree + detail sheet only. Excludes graph rendering, drawn edges, animation, layouts | Weekly map opens; return rate |
| 5 | **Milestone celebration cards** | Specific, mentor-voiced cards at learning milestones; shareable image | Max one per day; after the triggering action; never comparative; no personal data unless added; identical in all tiers | Share rate |
| 6 | **Focus timer on learn blocks** | Start/stop timer for outside study tied to the learn block | Optional; its measured time feeds pacing data, not judgment | Timer usage; pacing-data coverage |

## 6. Phase 2 — June 2027 (for the season-two cohort)

### 6.1 Pro+ tier contents
Video answers (from verified solutions only; cached library; fair-use on fresh generations) ·
viva mode + teach-back (shared voice plumbing, built together) · parent digest (weekly WhatsApp
message + web progress page) · external mock-scorecard ingestion (photograph → confirm →
autopsy; same capture-confirm-delete pattern) · personal formula/fact sheet · misconception
flip cards.

### 6.2 Additions to Free/Pro
- **Mnemonics on demand (Pro):** for arbitrary-association content only; the mentor declines
  for causal content with a one-line reason; supplements, never replaces, the grounded answer.
- **Voice input for doubts (all tiers):** speech-to-text in English, Hindi, Hinglish into the
  existing doubt pipeline.
- **Android home-screen widget (all tiers):** today's next block and streak.
- **Shareable answer cards (all tiers):** clean branded image of a doubt answer.
- **Notification timing learned from behavior (all tiers).**
- **Graduation flows and continuity path** (journey card, data export package, referral gift;
  humane re-onboarding for another attempt) — must ship before the first cohort's June results.
- **Web review surface** (notebook, reports, parent's window).
- **Hindi-first experience**, including completing the Chanakya→Unicode converter if slipped.

### 6.3 Internal priority
1) Video answers and parent digest (the Pro+ revenue case) → 2) viva + teach-back →
3) graduation flows (hard date) → 4) everything else as capacity allows.

## 7. Later — season two and beyond
Full graph-style concept maps · "why this exists" purpose one-liners · exam-day countdown ritual
(built for the final-100-days window) · NCERT line-recall drills (gated on the NCERT license) ·
JEE vertical and further expansion per roadmap.

## 8. Explicitly never
Model-selection menus or correctness-tiered pricing · leaderboards beyond the single weekly
percentile line · student-to-student social or chat · an open general-purpose chatbot ·
learning-loop features over WhatsApp · meme-tone branding · streak threats or loss-framed copy.

## 9. Business context affecting the product (for awareness, not implementation)
Season one operates as a proprietorship registered under Udyam (trade name used across bank,
Razorpay, DLT, D-U-N-S, and the Play organization account). The Play developer name shows the
trade name, never a person's name. Razorpay settlements move under the proprietorship before
public launch. Pvt Ltd conversion occurs on founder-defined triggers; the product must not
hard-code the legal entity name anywhere outside configuration and legal pages.

## 10. Acceptance summary
- §4.1 crisis protocol passes its eval cases before any beta student onboards.
- §4.2 linking test passes; no duplicate-user path exists.
- §4.3 queue path covered by the D65 breaker simulation.
- §4.7 at least one full past paper runs end-to-end as a mock with autopsy.
- §4.8 paywall matches §2 exactly; founding/list switch and GST display verified as config.
- §4.10 outcome metrics visible on dashboards before beta.
- §5 items ship only after the D60 gate, each behind a flag with its metric live.

## 11. Non-goals
No change to answer-quality gates, verification, or grounding rules. No new tiers beyond Free,
Pro, Pro+. No Pro+ marketing before it exists. No schedule change to the committed plan beyond
the day mappings in §12.3.

## 12. Integration instructions for Claude Code

### 12.1 SPEC.md (edits authorized by this change spec)
- §6.9 (monetization): replace the pricing and tier content with §2 and §3 of this spec.
- §6.3 (doubt solver): add anchored follow-ups (§4.4), Pro-never-refused (§4.3), and the
  "your turn" guard (§4.6).
- §6.6 (wellbeing): add the crisis protocol (§4.1).
- §5 (onboarding/auth): add Google Sign-In + linked credentials and the launch phone-OTP plan
  (§4.2); note the beta-era minors posture.
- §6.1 (planner) and §6.4 (notebook): add streak repair (§4.5), confidence question, mixed
  default, and try-once-more (§4.6).
- §10 (personalization charter): add principles 1–2 of §1 and the learning-science guards.
- §12 (Phase 2 list): replace with §6–§8 of this spec.
Touch nothing else in SPEC.

### 12.2 TECH_PLAN
Add or amend sections for: entitlement configuration, pricing configuration (founding/list/GST),
identity linking, crisis protocol and its eval cases, follow-up metering, mock-from-past-paper
flow, outcome metrics, Bedrock fallback. Dated notes; do not rewrite history.

### 12.3 PLAN and TRACKER day mappings
- §4.2 Google Sign-In + linking: next available buffer, must land before D74.
- §4.1 crisis protocol: behavior + eval cases in D59 (with slump rules); copy in D67; blocking
  item on the D78 beta gate.
- §4.3: D44 (copy/routing) and D65 (breaker wiring + simulation).
- §4.4: D46.
- §4.5 and §4.6: D58–D59, with copy in D67.
- §4.7: D35 (mocks) and D54 (autopsy).
- §4.8: D61–D63.
- §4.9: D64 or any buffer.
- §4.10: D73 dashboards, instrumentation as each feature lands.
- §4.11: next buffer (config only).
- §5 backlog: weeks 15–20, after the D60 gate, in priority order.
- Founder workstreams: add "pricing-evidence review" at beta end; Udyam/DLT/D-U-N-S/Razorpay
  realignment per the season-one entity decision.

### 12.4 DECISIONS.md rows
Tier philosophy (§1.1–1.2) · pricing structure and grandfathering (§2) · founding expiry by
evidence · Pro never refused · anchored follow-ups and no open chatbot · WhatsApp policy · auth
model and linking · crisis protocol as a beta blocker · learning-science guards · Mastery Map v1
frozen scope · Bedrock fallback-only · season-one entity as proprietorship (context only).

### 12.5 PARKED.md
Add §6 and §7 items with the "promoted by evidence" note; close the Bedrock revisit item.

### 12.6 Process
Store this file as `docs/changes/CS-2-tiers-pricing-roadmap.md`. Plan first: show the
integration plan across SPEC, TECH_PLAN, PLAN, TRACKER, DECISIONS, and PARKED before editing.
Surface any conflict with SPEC, TECH_PLAN, or CS-1 instead of resolving silently.
