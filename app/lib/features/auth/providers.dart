import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/api/api_failure.dart';
import '../../core/auth/auth_state.dart';
import '../../core/clock.dart';
import 'identifiers.dart';
import 'models.dart';
import 'repository.dart';

/// Which half of SPEC §8 screen 1 is showing: the identifier entry or the code entry.
enum LoginStep { entry, code }

/// The last thing the student asked for. After an offline failure, Retry re-runs it with the
/// same input (DEV_SPEC §6 screen 1: "must survive flaky network"; DECISIONS D8).
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

/// Everything the two login screens render. Typed input is never dropped by a failure.
class LoginState {
  const LoginState({
    this.email = '',
    this.challenge,
    this.resendAt,
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

  bool canResend(DateTime now) => resendAt == null || !now.isBefore(resendAt!);

  Duration resendIn(DateTime now) {
    final at = resendAt;
    if (at == null || !now.isBefore(at)) {
      return Duration.zero;
    }
    return at.difference(now);
  }

  /// Retry makes sense only after a failure that never reached the server.
  bool get canRetry => failure?.isOffline == true && lastIntent != null;

  LoginState copyWith({
    String? email,
    Object? challenge = _keep,
    Object? resendAt = _keep,
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

  @override
  LoginState build() => const LoginState();

  DateTime get _now => ref.read(clockProvider)();

  AuthRepository get _repository => ref.read(authRepositoryProvider);

  void emailChanged(String value) {
    state = state.copyWith(email: value, emailReason: null, failure: null);
  }

  /// Sends a code to the typed email. From the code step this is a resend: the old challenge
  /// stays on screen until the new one arrives, so a failed resend loses nothing.
  Future<void> requestCode() async {
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
      lastIntent: const RequestCodeIntent(),
    );
    try {
      final challenge = await _repository.requestOtp(email: email);
      state = state.copyWith(
        busy: false,
        challenge: challenge,
        resendAt: _now.add(challenge.resendAfter),
        attemptsLeft: null,
        codeDead: false,
        failure: null,
      );
    } on ApiFailure catch (failure) {
      state = state.copyWith(
        busy: false,
        failure: failure,
        resendAt: failure.retryAfter == null
            ? state.resendAt
            : _now.add(failure.retryAfter!),
        emailReason: failure.code == validationFailed
            ? failure.reasonFor('email')
            : null,
      );
    }
  }

  /// A resend inside the cooldown is a no-op; the screen shows the countdown instead.
  Future<void> resend() async {
    if (state.busy || !state.canResend(_now)) {
      return;
    }
    await requestCode();
  }

  Future<void> verify(String code) async {
    final challenge = state.challenge;
    if (challenge == null || state.busy) {
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
      lastIntent: VerifyIntent(digits),
    );
    try {
      final result = await _repository.verifyOtp(
        challengeId: challenge.challengeId,
        code: digits,
      );
      await ref.read(authStateProvider.notifier).signIn(result.toSession());
      state = state.copyWith(busy: false, failure: null, signedIn: true);
    } on ApiFailure catch (failure) {
      final attemptsLeft = failure.code == otpInvalid
          ? failure.attemptsLeft
          : state.attemptsLeft;
      state = state.copyWith(
        busy: false,
        failure: failure,
        attemptsLeft: attemptsLeft,
        codeDead:
            failure.code == otpExpired ||
            (failure.code == otpInvalid && attemptsLeft == 0),
        codeReason: failure.code == validationFailed
            ? failure.reasonFor('code')
            : null,
        resendAt: failure.retryAfter == null
            ? state.resendAt
            : _now.add(failure.retryAfter!),
      );
    }
  }

  /// Back to the entry step with the email still there; any cooldown still applies.
  void changeEmail() {
    if (state.busy) {
      return;
    }
    state = state.copyWith(
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
}

final loginProvider = NotifierProvider<LoginNotifier, LoginState>(
  LoginNotifier.new,
);
