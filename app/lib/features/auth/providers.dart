import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/api/api_failure.dart';
import '../../core/auth/auth_state.dart';
import '../../core/clock.dart';
import '../../core/ticker.dart';
import 'identifiers.dart';
import 'models.dart';
import 'repository.dart';

/// Which half of SPEC §8 screen 1 is showing: the identifier entry or the code entry.
enum LoginStep { entry, code }

/// The last thing the student asked for. After a failure that never produced a server answer,
/// Retry re-runs it with the same input (DEV_SPEC §6 screen 1: "must survive flaky network";
/// DECISIONS D8, D9).
sealed class LoginIntent {
  const LoginIntent();
}

class RequestCodeIntent extends LoginIntent {
  const RequestCodeIntent();
}

class VerifyIntent extends LoginIntent {
  const VerifyIntent(this.code);

  final String code;
}

/// Everything the two login screens render. Typed input is never dropped by a failure, and every
/// decision a screen shows (may I resend, how long until then) is a getter here, not in a widget.
class LoginState {
  const LoginState({
    this.email = '',
    this.challenge,
    this.resendAt,
    this.now,
    this.attemptsLeft,
    this.failure,
    this.emailReason,
    this.codeReason,
    this.busy = false,
    this.codeDead = false,
    this.lastIntent,
    this.signedIn = false,
  });

  /// As typed; the server trims and lowercases.
  final String email;

  /// Present from the moment a code was sent; `null` means the entry step.
  final OtpChallenge? challenge;

  /// When a resend is allowed again: the server's `resend_after_s`, or `retry_after_s` after a
  /// 429 (TECH_PLAN §3.4).
  final DateTime? resendAt;

  /// The notifier's view of the time, stamped on every intent and refreshed once a second while
  /// a cooldown is running, so the countdown is state, not a widget computation.
  final DateTime? now;

  /// From `details.attempts_left` after `OTP_INVALID`; `null` before the first wrong code.
  final int? attemptsLeft;

  /// The last failure worth showing; cleared by the next intent.
  final ApiFailure? failure;

  /// Reason code for the email field (`not_blank`, `email.invalid`), local or from the server.
  final String? emailReason;

  /// Reason code for the code field (`not_blank`, `code.digits`), local or from the server.
  final String? codeReason;

  final bool busy;

  /// `OTP_EXPIRED`, or attempts exhausted: the only remedy is a new code (D7 DECISIONS).
  final bool codeDead;

  final LoginIntent? lastIntent;

  /// True once the session is stored; the router leaves the login screens.
  final bool signedIn;

  LoginStep get step => challenge == null ? LoginStep.entry : LoginStep.code;

  bool canResendAt(DateTime at) => resendAt == null || !at.isBefore(resendAt!);

  /// May the screen offer "Send a new code" right now.
  bool get canResend => !busy && now != null && canResendAt(now!);

  /// May the entry step ask for a code right now: not busy, and no cooldown pending — from a
  /// 429, or from a code sent just before Change email (PLAN D9 rows 4–5). A fresh flow may.
  bool get canRequest =>
      !busy && (resendAt == null || (now != null && canResendAt(now!)));

  /// A wait of a minute or more reads in minutes (the hourly cap answers with up to 3,600 s).
  bool get longWait => resendSeconds >= 60;

  /// Whole minutes until a resend is allowed, rounded up; 0 when it already is.
  int get resendMinutes => (resendSeconds + 59) ~/ 60;

  /// Whole seconds until a resend is allowed, rounded up so a blocked button never reads "0s";
  /// 0 when it already is.
  int get resendSeconds {
    final at = resendAt;
    final current = now;
    if (at == null || current == null || !current.isBefore(at)) {
      return 0;
    }
    return (at.difference(current).inMilliseconds + 999) ~/ 1000;
  }

  /// Retry makes sense only after a failure that never produced a server answer: offline, a
  /// non-envelope reply (a captive portal's page) or a failed secure connection (D9).
  bool get canRetry =>
      failure != null && !failure!.isEnvelope && lastIntent != null;

  LoginState copyWith({
    String? email,
    Object? challenge = _keep,
    Object? resendAt = _keep,
    Object? now = _keep,
    Object? attemptsLeft = _keep,
    Object? failure = _keep,
    Object? emailReason = _keep,
    Object? codeReason = _keep,
    bool? busy,
    bool? codeDead,
    Object? lastIntent = _keep,
    bool? signedIn,
  }) => LoginState(
    email: email ?? this.email,
    challenge: identical(challenge, _keep)
        ? this.challenge
        : challenge as OtpChallenge?,
    resendAt: identical(resendAt, _keep) ? this.resendAt : resendAt as DateTime?,
    now: identical(now, _keep) ? this.now : now as DateTime?,
    attemptsLeft: identical(attemptsLeft, _keep)
        ? this.attemptsLeft
        : attemptsLeft as int?,
    failure: identical(failure, _keep) ? this.failure : failure as ApiFailure?,
    emailReason: identical(emailReason, _keep)
        ? this.emailReason
        : emailReason as String?,
    codeReason: identical(codeReason, _keep)
        ? this.codeReason
        : codeReason as String?,
    busy: busy ?? this.busy,
    codeDead: codeDead ?? this.codeDead,
    lastIntent: identical(lastIntent, _keep)
        ? this.lastIntent
        : lastIntent as LoginIntent?,
    signedIn: signedIn ?? this.signedIn,
  );

  static const Object _keep = Object();
}

/// The login flow (TECH_PLAN §5.8 `auth · LoginNotifier`): request a code, verify it, resend
/// after the cooldown, change the email, retry after an offline failure. Outcomes follow the D7
/// OTP decisions row one for one; no logic lives in the widgets (`.claude/rules/app.md`).
class LoginNotifier extends Notifier<LoginState> {
  static const String validationFailed = 'VALIDATION_FAILED';
  static const String otpInvalid = 'OTP_INVALID';
  static const String otpExpired = 'OTP_EXPIRED';

  ProviderSubscription<AsyncValue<DateTime>>? _tick;

  @override
  LoginState build() {
    // A sign-out (D10 logout, or a revoked family on refresh) starts the flow over: the old
    // challenge is spent and the guard must land on /login, not /login/otp (TECH_PLAN §5.3).
    ref.listen<AsyncValue<AuthState>>(authStateProvider, (previous, next) {
      if (previous?.value is SignedIn && next.value is SignedOut) {
        _stopTicking();
        state = const LoginState();
      }
    });
    ref.onDispose(_stopTicking);
    final initial = initialState();
    if (initial.challenge != null || initial.resendAt != null) {
      _startTicking();
    }
    return initial;
  }

  /// The state the flow starts in; tests seed a screen by overriding this.
  LoginState initialState() => const LoginState();

  DateTime get _now => ref.read(clockProvider)();

  AuthRepository get _repository => ref.read(authRepositoryProvider);

  /// A cooldown belongs to the destination that earned it (TECH_PLAN §3.4 keys the cap and the
  /// cooldown per phone or email): typing a different address lifts it, so a student capped on
  /// one inbox is not stuck for an hour on another. The server still answers 429 if the "new"
  /// address turns out to be the same one.
  void emailChanged(String value) {
    final differentDestination =
        state.resendAt != null &&
        Identifiers.normaliseEmail(value) !=
            Identifiers.normaliseEmail(state.email);
    if (differentDestination) {
      _stopTicking();
    }
    state = state.copyWith(
      email: value,
      emailReason: null,
      failure: null,
      resendAt: differentDestination ? null : state.resendAt,
    );
  }

  /// Sends a code to the typed email. From the code step this is a resend: the old challenge
  /// stays on screen until the new one arrives, so a failed resend loses nothing. Inside a
  /// cooldown this is a no-op on either step: the server would only answer 429 again.
  Future<void> requestCode() async {
    if (state.busy || !state.canResendAt(_now)) {
      return;
    }
    final email = state.email.trim();
    final reason = Identifiers.emailReason(email);
    if (reason != null) {
      state = state.copyWith(emailReason: reason, failure: null);
      return;
    }
    state = state.copyWith(
      busy: true,
      failure: null,
      emailReason: null,
      codeReason: null,
      now: _now,
      lastIntent: const RequestCodeIntent(),
    );
    try {
      final challenge = await _repository.requestOtp(email: email);
      final now = _now;
      state = state.copyWith(
        busy: false,
        challenge: challenge,
        resendAt: now.add(challenge.resendAfter),
        now: now,
        attemptsLeft: null,
        codeDead: false,
        failure: null,
      );
      _startTicking();
    } on ApiFailure catch (failure) {
      final now = _now;
      state = state.copyWith(
        busy: false,
        failure: failure,
        now: now,
        resendAt: failure.retryAfter == null
            ? state.resendAt
            : now.add(failure.retryAfter!),
        emailReason: failure.code == validationFailed
            ? failure.reasonFor('email')
            : null,
      );
      if (state.resendAt != null) {
        _startTicking();
      }
    }
  }

  /// A resend inside the cooldown is a no-op; the screen shows the countdown instead.
  Future<void> resend() async {
    if (state.busy || !state.canResendAt(_now)) {
      return;
    }
    await requestCode();
  }

  /// Typing clears the field reason; the sixth digit submits (SPEC §8 screen 1 "bulletproof",
  /// one tap fewer on a phone keyboard).
  void codeTyped(String value) {
    if (state.codeReason != null) {
      state = state.copyWith(codeReason: null);
    }
    if (value.trim().length == Identifiers.codeLength) {
      verify(value);
    }
  }

  Future<void> verify(String code) async {
    final challenge = state.challenge;
    if (challenge == null || state.busy || state.codeDead) {
      return;
    }
    final digits = code.trim();
    final reason = Identifiers.codeReason(digits);
    if (reason != null) {
      state = state.copyWith(codeReason: reason, failure: null);
      return;
    }
    state = state.copyWith(
      busy: true,
      failure: null,
      codeReason: null,
      now: _now,
      lastIntent: VerifyIntent(digits),
    );
    try {
      final result = await _repository.verifyOtp(
        challengeId: challenge.challengeId,
        code: digits,
      );
      await ref.read(authStateProvider.notifier).signIn(result.toSession());
      _stopTicking();
      state = state.copyWith(busy: false, failure: null, signedIn: true);
    } on ApiFailure catch (failure) {
      final now = _now;
      final attemptsLeft = failure.code == otpInvalid
          ? failure.attemptsLeft
          : state.attemptsLeft;
      final exhausted = failure.code == otpInvalid && attemptsLeft == 0;
      state = state.copyWith(
        busy: false,
        // On the last attempt the server's "try once more" would contradict "no tries left";
        // the attempts line carries the remedy alone (SPEC §1 principle 3, mentor voice: direct).
        failure: exhausted ? null : failure,
        now: now,
        attemptsLeft: attemptsLeft,
        codeDead: failure.code == otpExpired || exhausted,
        codeReason: failure.code == validationFailed
            ? failure.reasonFor('code')
            : null,
        resendAt: failure.retryAfter == null
            ? state.resendAt
            : now.add(failure.retryAfter!),
      );
    }
  }

  /// Back to the entry step with the email still there; any cooldown still applies — and keeps
  /// counting down on the entry step, so the ticker stays only while one is pending.
  void changeEmail() {
    if (state.busy) {
      return;
    }
    final now = _now;
    if (state.canResendAt(now)) {
      _stopTicking();
    }
    state = state.copyWith(
      now: now,
      challenge: null,
      attemptsLeft: null,
      failure: null,
      codeReason: null,
      codeDead: false,
      lastIntent: null,
    );
  }

  /// Runs the last intent again with the same input (after an offline failure).
  Future<void> retry() async {
    switch (state.lastIntent) {
      case RequestCodeIntent():
        await requestCode();
      case VerifyIntent(:final code):
        await verify(code);
      case null:
        return;
    }
  }

  void dismissFailure() {
    state = state.copyWith(failure: null);
  }

  /// The once-a-second clock runs only while a cooldown can be on screen: on the code step from
  /// a sent code, and on the entry step only until a pending cooldown elapses (SPEC §1
  /// principle 5: no idle timer on a mid-range phone).
  void _startTicking() {
    _tick ??= ref.listen<AsyncValue<DateTime>>(tickerProvider, (_, next) {
      final at = next.value;
      if (at != null) {
        state = state.copyWith(now: at);
        if (state.step == LoginStep.entry && state.canResendAt(at)) {
          _stopTicking();
        }
      }
    });
  }

  void _stopTicking() {
    _tick?.close();
    _tick = null;
  }
}

final loginProvider = NotifierProvider<LoginNotifier, LoginState>(
  LoginNotifier.new,
);
