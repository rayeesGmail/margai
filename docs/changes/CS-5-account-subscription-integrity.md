# Change Spec CS-5 — Account & Subscription Integrity

**Status:** Founder-approved product change, issued at ~D25 (2026-09-27). To be integrated by
Claude Code per §7. SPEC.md amendments in §7.1 are authorized by this change spec.
**Intent in one line:** one subscription serves one student, sharing is impractical, and free-tier
farming is limited — without punishing families who share a phone.
**What this spec does not do:** it contains no implementation design. Session handling, device
recognition and detection logic are Claude Code's to plan (plan first, then build).

---

## 1. Principles

1. **A subscription belongs to one student's account** — not to a phone number or a device.
2. **Families are normal.** Siblings sharing one phone must each be able to have their own account.
3. **The product is the first deterrent.** Every plan, notebook and trajectory is personal, so a
   shared account degrades for everyone who uses it. Say so kindly.
4. **Be gentle, never accusatory.** A little leakage is acceptable; locking out a genuine student
   or angering a parent is not.
5. **Respect platform and privacy rules.** No permanent hardware identifiers (such as IMEI or
   serial numbers). Use app-level install identifiers and Google Play's official app-integrity
   check, consistent with Play policy and India's data protection law.

## 2. Identity rules

- An account is identified by its Google login and, from public launch, a verified phone number
  (per CS-2 §4.2 linked credentials).
- **Uniqueness:** a Google account and a phone number can each belong to only one MARGAI account.
- Subscriptions attach to the account and follow it across devices.

## 3. Session and device rules

### 3.1 One active phone per account
- Signing in on a new phone signs the account out on the previous phone, with a clear message on
  both ("You've signed in on another phone").
- **Phase 2 (web):** one phone plus one browser may be active at once.
- Offline work queued on the signed-out phone (CS-3) must still sync on its next sign-in by the
  same account; it is never silently discarded.

### 3.2 "Your devices" screen
- In Profile/Settings: where the account is signed in, last active time, and a sign-out control
  for each.
- Lets a student recover her own account if a phone is lost or shared by mistake.

### 3.3 Multiple accounts on one phone
- A phone may hold several accounts (for siblings) with a simple account switcher.
- Each account has its own plan, notebook, history and subscription; switching never mixes data.
- Pro on one account never unlocks Pro for another account on the same phone.

## 4. Free-tier abuse limits

- **Free accounts per phone:** capped (default 3, configurable). Creating more shows a friendly
  message explaining the limit.
- **Free doubt limit per phone as well as per account:** the daily free doubt allowance cannot be
  multiplied by switching free accounts on the same phone. (Pro accounts on a shared phone are
  unaffected.)
- Limits must be configuration values, tunable from beta data without a release.

## 5. Unusual-use handling

- **Signals** (examples, tunable): the same account active in distant locations within a short
  time; usage far beyond normal patterns; rapid repeated sign-ins across phones.
- **Response ladder:** soft in-app message → re-verification (sign in again / OTP) → temporary
  rate limit. **No automatic bans.** Any account restriction beyond a rate limit requires founder
  review.
- The existing fair-use cap and ₹25 per-user daily breaker continue to bound cost regardless of
  sharing.
- Copy stays kind and non-accusatory, e.g. "Your plan is built only from your work — sharing your
  account mixes up your plan."

## 6. Terms, family pricing and acceptance

### 6.1 Terms of use
State that a subscription is personal and non-transferable; explain the one-active-phone rule in
plain language on the paywall and in settings.

### 6.2 Sibling discount (Phase 2)
A second student account in the same family at a discount (e.g. 50%), purchased by the same
payer. Converts a sharing problem into revenue. Specified here, built in Phase 2 alongside the
parent digest.

### 6.3 Acceptance criteria
- Signing in on phone B signs out phone A; queued offline work from phone A syncs when the same
  account next signs in there.
- The devices screen lists active phones and signs one out on request.
- Two accounts on one phone keep fully separate data and subscriptions.
- A fourth free account on one phone is blocked with a friendly message; switching free accounts
  does not increase the phone's daily free doubts.
- A simulated two-city concurrent session triggers the soft message and re-verification, never a
  ban.
- No permanent hardware identifiers are collected (verified in the privacy/security review).

## 7. Integration instructions for Claude Code

### 7.1 SPEC.md (edits authorized by this change spec)
- §5 (auth/identity): add §2 uniqueness and §3 session rules.
- §6.9 (monetization): add §4 free-tier limits, §6.1 terms, and the Phase 2 sibling discount.
- §6.11 (profile/settings): add the devices screen and account switcher.
- Non-functional/privacy section: add §1.5 identifier rules and §5 response ladder.
Touch nothing else in SPEC.

### 7.2 TECH_PLAN
Add sections for session policy, device recognition via app install identifiers and the Play
integrity check, per-phone free limits, unusual-use signals and the response ladder, and the
account switcher. Dated notes; do not rewrite history.

### 7.3 PLAN and TRACKER day mappings
- §2 uniqueness: with the Google Sign-In + linking work (CS-2 §4.2, before D74).
- §3.1 one active phone and §3.2 devices screen: D61–D63 (billing week).
- §3.3 account switcher: D63–D64.
- §4 free-tier limits: D44 (free limits) extended at D65.
- §5 unusual-use handling: D65 (with the breaker work), tested at D71 (security review).
- §6.1 terms: D64 (legal pages).
- §6.2 sibling discount: Phase 2.

### 7.4 DECISIONS.md rows
Subscription belongs to the account · one active phone (web later: phone + browser) · multiple
accounts per phone allowed · free-account and per-phone free-doubt caps · no permanent hardware
identifiers · gentle response ladder with no automatic bans · sibling discount in Phase 2.

### 7.5 Process
Store this file as `docs/changes/CS-5-account-subscription-integrity.md`. Plan first: show the
integration plan before editing. Surface any conflict with SPEC, TECH_PLAN, CS-2 or CS-3 instead
of resolving silently.
