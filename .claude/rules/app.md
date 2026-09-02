---
paths:
  - "app/**"
---

# app/ rules

- Follow the SPEC §8 screen catalog and SPEC §5–6 behaviours; do not invent screens.
- No logic in widgets. State lives in Riverpod providers; widgets render provider state.
- Every network call goes through the shared `ApiClient`, which speaks the DEV_SPEC §5 error
  envelope `{error: {code, message_en, message_user_lang}}` and surfaces honest offline states.
- Practice events, mood taps and block status use the offline outbox pattern (drift/sqlite,
  DEV_SPEC §6.1) with retry — never fire-and-forget. A student on a train finishes today's practice.
- Doubt solving requires network; show the friendly offline state, never a silent failure.
- All user-facing copy in ARB files for en / hi / hinglish; mentor voice (warm, direct, never
  guilt-tripping, never pretending to be human). No hard-coded strings in widgets.
- `cd app && flutter analyze && flutter test` must pass before any commit.
- Target mid-range Android, one-hand use, patchy internet (SPEC §1 principle 5).
- Third-party SDKs limited to FCM, Razorpay and PostHog.
