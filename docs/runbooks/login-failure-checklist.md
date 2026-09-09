# Login failure checklist (PLAN D9 ✅)

The ten ways a sign-in goes wrong, and what MARG AI does about each — the PLAN D9 acceptance
("a test checklist of 10 failure modes all handled gracefully"), kept as a runbook so it can be
re-run at the Week-2 gate, at D71 (security review) and after any change to `auth`, `account`,
the app's `core/api` or the login screens. SPEC §8 screen 1 calls this screen "bulletproof retry";
DEV_SPEC §6 calls it sacred (R5). Runbooks live in `docs/runbooks/` (TECH_PLAN §7.5).

**Graceful** means, for every row: a defined server answer (status, code, machine-readable
`details` — never prose from Java, TECH_PLAN §3.3), a defined screen state with the copy in all
three locales, nothing the student typed lost, and a way forward on screen. **PASS** = every row's
named tests are green inside `cd server && ./mvnw verify` and `cd app && flutter test`, the
app-visible rows were observed on a device against the local server, and the two server-only rows
(6, 7) have a curl + log transcript. A 500, lost input, contradictory copy, a stack trace or a dead
end is a FAIL.

## How to run it

```bash
# 1. the API with the sandbox sender (the code is printed on logger margai.otp.sandbox);
#    pick a free port — 8080 is often taken on the founder's machine, and a server left over
#    from an SES live proof may hold 8081
cd server && SERVER_PORT=8082 ./mvnw spring-boot:run
#    row 3 wants a short code lifetime: restart with MARGAI_AUTH_OTP_TTL=40s for that row only
# 2. the app on the AVD (scripts/dev-setup.sh --emulator), pointed at that port
cd app && flutter build apk --debug --dart-define=API_BASE_URL=http://10.0.2.2:8082 \
  && adb install -r build/app/outputs/flutter-apk/app-debug.apk
adb shell pm clear com.margai.app          # a signed-out device between rows
# 3. curl for the server-only rows; the database through the compose container
docker compose exec -T db psql -U margai -d margai -c "select count(*) from users where email = '…'"
```

The server's `X-Forwarded-For` last hop is the address every per-address limit keys on (TECH_PLAN
§1.5); a direct connection uses the socket address, so every curl below shares one bucket.

## The ten rows

| # | Failure | How to trigger | The server answers | The student sees | Pinned by (green in `verify` / `flutter test`) |
|---|---|---|---|---|---|
| 1 | Wrong code, 1st–4th time | type a wrong 6-digit code | 401 `OTP_INVALID`, `details.attempts_left` 4…1 | the server's line ("That code didn't match. Try once more.") plus "N tries left."; digits kept, Verify still live | server `AuthFlowTest.wrongCodesCountDownThenTheChallengeDies`, `OtpServiceTest.aWrongCodeCountsAnAttemptAndReportsWhatIsLeft` · app `login_notifier_test` "OTP_INVALID counts attempts down; zero kills the code without a contradiction", `otp_screen_test` "wrong code: server copy plus the attempts-left plural" |
| 2 | Wrong code, 5th time | a fifth wrong code | 401 `OTP_INVALID` with `attempts_left: 0`; any later try on that challenge → 401 `OTP_EXPIRED` | the attempts line alone ("No tries left on this code — ask for a new one."), field and Verify disabled, "Send a new code" after the cooldown | server as row 1 + `OtpServiceTest.theFifthWrongCodeExhaustsTheChallengeAndLaterTriesAreExpired` · app `otp_screen_test` "attempts exhausted: the attempts line alone, verify disabled" |
| 3 | Code older than 5 minutes, even the right one | wait past `margai.auth.otp.ttl`, then type the right code | 401 `OTP_EXPIRED`, no `attempts_left` | "That code isn't valid any more. Ask for a new one.", field and Verify disabled, resend offered | server `AuthUnhappyPathsTest.theRightCodeAfterFiveMinutesIsExpired`, `OtpServiceTest.unknownUsedAndExpiredChallengesAreExpired` · app `login_notifier_test` "OTP_EXPIRED kills the code; the challenge stays for the screen", `otp_screen_test` "a dead code disables verify and the field; a new code is the way out" |
| 4 | Resend inside the 30-second cooldown — including the app killed and reopened mid-flow, or Change email right after a send | ask for a second code within 30 s | 429 `OTP_RATE_LIMITED`, `Retry-After` and `details.retry_after_s` = seconds left | the button counts down and is disabled on **both** steps ("New code in 27s" / "Send code in 27s"); the app never calls inside a cooldown it knows about; a fresh app that does not know it shows the countdown from the 429 | server `AuthUnhappyPathsTest.aResendInsideTheCooldownIsRateLimitedWithTheWait`, `OtpServiceTest.aRequestInsideTheCooldownIsRateLimitedWithTheRemainingWait` · app `login_notifier_test` "inside the cooldown is a no-op", "a 429 on entry disables Send code, counts down, and never calls inside the wait", "Change email keeps the ticker only while the cooldown is pending", `login_screen_test` "inside a cooldown: Send code is disabled with the countdown, email editable", `app_test` "email → code screen → verified → landing, then the session is on the device" |
| 5 | 4th code within an hour | three codes 31 s apart, then a fourth | 429 `OTP_RATE_LIMITED`, `retry_after_s` up to 3600 (the oldest request leaving the window) | "Send code in 59 min" (whole minutes, rounded up), button disabled, email kept; same on the code step | server `AuthUnhappyPathsTest.theFourthCodeInAnHourWaitsForTheWindowToPass`, `OtpServiceTest.theHourlyCapWaitsUntilTheOldestRequestLeavesTheWindow` · app `login_notifier_test` "waits of a minute or more read in whole minutes, rounded up", `login_screen_test` "a long wait reads in minutes", `otp_screen_test` "a long wait on the code step reads in minutes" |
| 6 | Phone clock hours off | set the device clock ahead, or send `X-Client-Time` by hand | 200 — the flow is unaffected (durations only, server-clock token validation); `client_skew_s` in the request's MDC, one WARN and `auth.clock_skew{band}` past `margai.auth.clock-skew-warn` (2 min); the header is never echoed | nothing — unless the clock breaks TLS, then "I couldn't make a secure connection. Check your phone's date and time, then retry." with Retry | server `ClientTimeFilterTest` (5 cases), `AuthUnhappyPathsTest.aSkewedClientClockStillSignsInAndIsCounted`, `JwtServiceTest` (expiry on the server clock) · app `api_client_test` "a rejected certificate is its own client-only code", "a failed TLS handshake from the socket is the same code", `failure_line_test` "a certificate failure names the phone clock and offers Retry" |
| 7 | Duplicate accounts: case and spacing variants of one email; two first logins at the same instant | `Founder@Example.COM ` and `founder@example.com`; two codes for one new email verified in parallel | one `users` row; both verifies 200 with the same `user.id`, exactly one `is_new_user: true` — never a 500 | signed in, once | server `AccountConcurrencyTest.simultaneousFirstLoginsOfOneEmailMakeOneAccount`, `AuthUnhappyPathsTest.twoSimultaneousFirstLoginsShareOneAccount`, `IdentifiersTest.emailsAreTrimmedAndLowercased`, `AccountServiceTest.emailLoginCreatesThenFindsTheAccount` |
| 8 | Malformed request: broken JSON, a non-JSON content type, a non-UUID `challenge_id` | curl (the app cannot send these) | 400 `VALIDATION_FAILED` with `details.body: malformed` or `details.content_type: unsupported` — reason codes, no Java names, no stack | the authored line "The app sent something the server couldn't read. Please update the app." — not the generic "some details don't look right" | server `AuthUnhappyPathsTest.malformedRequestsAreReasonCodesNeverProse`, `ApiEnvelopeTest.malformedJsonIsAValidationFailure`, `ApiEnvelopeTest.validationDetailsAreReasonCodesNeverProse` · app `failure_copy_test` "a body-level reason wins over the generic validation line" |
| 9 | Offline at every step (request, verify, resend, Retry itself), and a captive portal's HTML instead of the API | airplane mode, or stop the server; a proxy page | nothing reaches the server / a non-envelope answer | "You're offline. Check your connection and retry — nothing you typed is lost." (or "The server sent something I couldn't read…"), email and digits kept, one Retry that re-runs the same request or the same verify | app `login_notifier_test` "offline on request keeps the email; Retry re-sends it", "offline on verify keeps the code; Retry re-verifies the same code", "a failed resend keeps the old challenge on screen", "Retry is offered after a malformed or certificate answer and re-runs the intent", `api_client_test` "a refused connection is offline", "a receive timeout is offline, not a crash", "a proxy HTML page on 502 is malformed with the status", `login_screen_test` "offline: honest copy, email kept, Retry re-sends", `otp_screen_test` "offline on verify: honest copy and Retry" |
| 10 | Server restart with a per-process pepper (every local run), or a pepper rotation, between request and verify | request a code, restart the server, type the right code | 401 `OTP_EXPIRED` at once — pending codes are retired at boot when the pepper is ephemeral; a configured pepper leaves them alone | "That code isn't valid any more. Ask for a new one." with the resend — not five "didn't match" | server `OtpStartupTest` (both cases), `AuthConstraintsTest.retiringLiveChallengesExpiresOnlyPendingOnes` · app as row 3 |

## Pinned as well, not counted

- **A verify flood** (61 verifies in a minute from one address) → 429 `RATE_LIMITED` with
  `Retry-After` before any service code runs: `AuthUnhappyPathsTest.aVerifyFloodIsRateLimitedBeforeTheService`,
  `RateLimitFilterTest.verifyAndRefreshGetAPerAddressMinuteBucket`. The app shows the server's line
  ("A little too fast. Try again in a moment.") and keeps the digits; the resend button follows its
  own cooldown, not this bucket's `Retry-After` (`login_notifier_test` "RATE_LIMITED on verify shows
  the line and leaves the resend alone").
- **A bug on the server** → 500 `INTERNAL` with `details.request_id` and nothing else:
  `ApiEnvelopeTest.aBugIsInternalWithTheRequestIdAndNothingElse`; the app shows the line and
  "Reference: <id>" for support (`failure_line_test` "INTERNAL shows the server copy and the request reference").
- **A challenge of another purpose** (parent consent, D27) presented to the login verify → 401
  `OTP_EXPIRED` by `OtpService.verify`'s purpose check; it gets a dedicated test when the second
  purpose exists.
- **The verify succeeded but the answer was lost** (the server committed, the phone timed out):
  the app shows the offline line with Retry; the retry finds the challenge already used and gets
  `OTP_EXPIRED`; a new code signs the student in. Documented, unchanged — the ≤ 1-minute detour is
  cheaper than making verify idempotent on a challenge that must stay single-use.

## Known edges (recorded, not fixed here)

- An access token stays valid for up to 15 minutes after account deletion (D64 decides per-request checks).
- Two spellings of one Gmail inbox (dots, plus tags) are two accounts (PARKED since D7).
- Row 10 in production: rotating `margai.auth.otp.pepper` retires nothing automatically (the pepper
  is configured, so codes are left alone) but the old codes can no longer match — a rotation is a
  ≤ 5-minute window of "didn't match"; the F8 runbook notes it.

## Runs after D9

- **D10 (2026-09-09)** — `auth`, `account` and the app's `core/api` all changed (logout, `/me`,
  the single-flight refresh, no bearer on the public routes). Every row's pinning tests ran green
  inside the gate on each of the nine commits (server 306 tests, app 242); the ten-row device
  re-run is the Week-2 gate's (D12), three days on, per the D10 plan's closing question 4. Two
  rows gained pins in passing: row 9's offline handling now also covers a refresh that goes
  offline (the session stays, `session_refresher_test`), and row 10's per-process secrets now
  end a signed-in session gracefully — the dead family answers `AUTH_INVALID` and the app returns
  to login (`api_wiring_test`).

## D9 run — 2026-09-09 · PASS

AVD `margai_android36` (Android 16, 1080×2400) driven through the accessibility tree
(`uiautomator dump`, taps by label) against a local server on port 8082 with the sandbox sender;
curl for the server-only rows; 19 screenshots in the session scratchpad, the tree dumps and the
transcript in the TRACKER day log. Every row's tests are green in the two suites (server 280
tests, app 168).

| # | Observed |
|---|---|
| 1 | After each wrong code the digits stayed in the field, the server line and "4 / 3 / 2 / 1 tries left." rendered, Verify stayed live (shots 03, 06). |
| 2 | The fifth wrong code left only "No tries left on this code — ask for a new one."; the field and Verify disabled, "Send a new code" live (shot 07). |
| 3 | Server started with `MARGAI_AUTH_OTP_TTL=40s`; the right code typed 45 s after it was sent → "That code isn't valid any more. Ask for a new one.", field and Verify disabled, resend live (shot 17). |
| 4 | Resend → "New code in 26s" disabled (shot 08). App force-stopped and reopened inside the cooldown, same address → the server's 429 rendered as its line plus a disabled countdown that went live a second later (shot 09). Change email right after a send → address kept, "Send code in 20s" disabled (shot 10). |
| 5 | The fourth code for one address inside the hour → "Too many codes requested. Give it a little time." and "Send code in 57 min" disabled, address kept (shot 11). Typing a different address freed the button and it sent (shots 13, 14 — after the follow-up fix `54e8566`, found by this row). |
| 6 | curl with `X-Client-Time` 3 h ahead → 200, challenge issued, header not echoed; server log `WARN … ClientTimeFilter : client clock is 10799 s ahead of ours on POST /api/v1/auth/otp/request (request_id=fda9dd2a-…)`. The TLS copy is pinned by tests (the local server is plain HTTP). |
| 7 | `  TWINS-…@EXAMPLE.COM  ` requested inside the cooldown of `twins-…@example.com` → 429 (one destination). Two codes 31 s apart, two verifies fired in parallel → both 200 with user `05df08ab-…`, `is_new_user` true exactly once; `select count(*) from users where email = …` → 1. |
| 8 | `{"email":` → 400 `details.body: malformed`; `text/plain` → `details.content_type: unsupported`; `challenge_id: "not-a-uuid"` → `details.body: malformed` — no Java names, no stack. The app's rendering of those reasons is pinned by `failure_copy_test`. |
| 9 | Server stopped, verify → "You're offline. Check your connection and retry — nothing you typed is lost.", digits kept, Retry (shot 15). Entry step with a new address and the server down → the same line, address kept (shot 18); Retry after the server returned re-sent the request and landed on the code screen (shot 19). |
| 10 | The code pending from before a restart: the restarted server logged `retired 1 pending OTP code(s): the OTP pepper is per process …`, and Retry answered "That code isn't valid any more. Ask for a new one." with the resend live (shot 16). |
