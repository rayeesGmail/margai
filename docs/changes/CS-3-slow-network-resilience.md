# Change Spec CS-3 — Slow-Network Resilience

**Status:** Founder-approved product change, issued at ~D25 (2026-09-27). To be integrated by
Claude Code per §7. SPEC.md amendments in §7.1 are authorized by this change spec.
**Intent in one line:** the app must feel reliable on slow, patchy mobile data — the normal
condition for many of our students — not just on good Wi-Fi.
**What this spec does not do:** it contains no implementation design. How queuing, compression,
prefetching and testing are built is Claude Code's to plan (plan first, then build).

---

## 1. Principles

1. **Never lose a student's work.** Anything she does offline or on a failing connection is kept
   on the device and delivered later.
2. **Never show a dead screen.** No endless spinners and no blank pages; every wait or failure has
   clear, honest copy and a next step.
3. **Send less.** Payloads, images and downloads are as small as they can be without hurting
   quality.
4. **Depend on the network as little as possible at the moments that matter** — especially the
   7 AM plan.

## 2. Already specced — confirm these hold

These exist in SPEC/TECH_PLAN; this spec makes them acceptance requirements under slow networks:
- Today's plan and its practice questions available offline; answers queued on device and synced
  later, with server re-judging authoritative (D34, TECH_PLAN §5.6 Option A).
- Doubt answers stream progressively rather than appearing after a long wait.
- Retries back off sensibly and never hammer a weak connection.
- Login tolerates bad networks: Google Sign-In; phone OTP with auto-read and resend (at launch).
- Every failure state has clear copy and a retry path.

## 3. New requirements

### 3.1 Offline photo-doubt queue
- A student can photograph or type a doubt with no connection.
- The doubt is held on the device, clearly marked "waiting for network," and sent automatically
  when a connection returns — including after the app is closed and reopened.
- When the answer arrives, she is notified (within the existing notification caps) and the answer
  opens from the notification.
- Queued doubts count toward the free-tier limit when sent, not when captured. If sending would
  exceed the limit, the existing paywall/limit behavior applies at that point.
- She can see and cancel queued doubts.
- Queued photos follow the same privacy rules as uploads: deleted from the device once sent, and
  from the server per existing retention rules.

### 3.2 Photo compression before upload
- Doubt photos and document captures (scorecard, marksheet, timetable) are reduced to the smallest
  size that preserves reliable reading of the question or document before upload.
- Target: typically a few hundred kilobytes rather than several megabytes.
- Compression must not reduce extraction quality: the doubt-solver and document-extraction
  evals must show no regression versus uncompressed input.

### 3.3 Background prefetch of tomorrow's plan
- When the nightly plan is ready, the app downloads it (and its practice questions) in the
  background at the first reasonable connection.
- The 7 AM Today screen must open from local data without waiting on the network.
- If no prefetch happened, the app fetches on open as today, and the existing no-planless-morning
  fallback still applies.
- Prefetch respects the device's data-saver and battery-saver settings.

### 3.4 Low-data behavior (applies now to images; to video in Phase 2)
- Images in the app (diagrams, cards) load at a size suited to the device and connection.
- **Phase 2 note:** video answers default to low quality on slow connections, offer "download on
  Wi-Fi," and are cached for replay. Recorded here so Phase 2 inherits the requirement.

## 4. Acceptance criteria

- **Throttled-network test:** on a simulated slow 3G profile, the Today screen opens from local
  data, a practice session completes, and a doubt answer begins streaming within an acceptable
  time; no screen shows an endless spinner.
- **Offline doubt test:** capture a photo doubt in airplane mode, close the app, reopen with a
  connection — the doubt sends automatically, the answer arrives, and a notification opens it.
- **Airplane-mode practice test:** complete a practice block offline; results sync with no loss
  and streak/notebook update correctly (existing D34 test, re-run under this spec).
- **Compression test:** photo payloads meet the size target; doubt and document evals show no
  quality regression.
- **Prefetch test:** with the nightly plan ready and connectivity at night only, the morning Today
  screen opens instantly in airplane mode.
- **Real device:** all of the above pass on a low-cost Android phone, not just an emulator.

## 5. Metrics (dashboards, before beta)
Screen load times and failure rates broken down by network type; share of doubts sent from the
offline queue; time from queue to answer; prefetch success rate (share of mornings where Today
opened from local data); photo upload sizes.

## 6. Non-goals
No full offline doubt-solving (answers still require the server). No offline access to features
beyond today's plan, its practice, the notebook's cached view, and the doubt queue. No change to
answer-quality gates, privacy rules, or free-tier limits beyond the timing rule in §3.1.

## 7. Integration instructions for Claude Code

### 7.1 SPEC.md (edits authorized by this change spec)
- §6.2 (practice) and §6.1 (planner): add §3.3 prefetch and the "Today opens from local data"
  requirement.
- §6.3 (doubt solver): add §3.1 offline queue and §3.2 compression.
- §9 or the relevant non-functional section: add §1 principles and §4 acceptance as reliability
  requirements.
Touch nothing else in SPEC.

### 7.2 TECH_PLAN
Add sections for the offline doubt queue, image compression, background prefetch, low-data image
handling, and throttled-network testing. Dated notes; do not rewrite history.

### 7.3 PLAN and TRACKER day mappings
- §3.1 offline doubt queue: D38 (photo path) with its notification in D46; if D38 is full, the
  D42 buffer.
- §3.2 compression: D38 (photo path) and D28/D29 capture flows retro-fitted in the next buffer.
- §3.3 prefetch: D57 (nightly batch + morning notification).
- §3.4 image sizing: D69 (performance pass).
- §4 throttled-network and real-device tests: D69, re-run at D77 (dress rehearsal).
- §5 metrics: D73 dashboards.

### 7.4 DECISIONS.md rows
Offline doubt queue (limit counted at send time) · compression with no-regression eval gate ·
prefetch of tomorrow's plan · slow-network acceptance tests as a beta gate · video low-data
requirement recorded for Phase 2.

### 7.5 Process
Store this file as `docs/changes/CS-3-slow-network-resilience.md`. Plan first: show the integration
plan before editing. Surface any conflict with SPEC, TECH_PLAN, CS-1 or CS-2 instead of resolving
silently.
