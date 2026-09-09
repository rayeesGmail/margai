import 'dart:async';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/clock.dart';
import 'package:margai/core/ticker.dart';
import 'package:margai/features/auth/models.dart';
import 'package:margai/features/auth/providers.dart';
import 'package:margai/features/auth/repository.dart';

import '../../support/fake_auth_repository.dart';

void main() {
  late FakeAuthRepository repository;
  late InMemoryTokenStore store;
  late DateTime now;
  late StreamController<DateTime> ticks;
  late ProviderContainer container;

  LoginNotifier notifier() => container.read(loginProvider.notifier);
  LoginState state() => container.read(loginProvider);

  setUp(() async {
    repository = FakeAuthRepository();
    store = InMemoryTokenStore();
    now = DateTime.utc(2026, 9, 8, 12, 0, 0);
    ticks = StreamController<DateTime>.broadcast();
    container = ProviderContainer.test(
      overrides: [
        authRepositoryProvider.overrideWithValue(repository),
        tokenStoreProvider.overrideWithValue(store),
        clockProvider.overrideWithValue(() => now),
        tickerProvider.overrideWith((ref) => ticks.stream),
      ],
    );
    await container.read(authStateProvider.future);
    // Riverpod 3 pauses a provider's own subscriptions while nothing listens to it; in the app
    // the screen watches loginProvider, so the test keeps one listener open the same way.
    final keepAlive = container.listen(loginProvider, (_, _) {});
    addTearDown(keepAlive.close);
  });

  tearDown(() => ticks.close());

  group('happy path (PLAN D8 ✅, TECH_PLAN §3.7)', () {
    test('email → code → signed in, session stored, auth state flips', () async {
      repository
        ..onRequest(FakeAuthRepository.challenge)
        ..onVerify(FakeAuthRepository.signedIn);

      notifier().emailChanged('  Founder@Example.com ');
      await notifier().requestCode();

      expect(repository.requestedEmails, ['Founder@Example.com']);
      expect(state().step, LoginStep.code);
      expect(state().challenge?.challengeId, 'c-1');
      expect(state().resendAt, now.add(const Duration(seconds: 30)));
      expect(state().now, now);
      expect(state().canResend, isFalse);
      expect(state().resendSeconds, 30);
      expect(state().busy, isFalse);

      await notifier().verify(' 444771 ');

      expect(repository.verified, [(challengeId: 'c-1', code: '444771')]);
      expect(state().signedIn, isTrue);
      expect(state().failure, isNull);
      expect(container.read(authStateProvider).value, isA<SignedIn>());
      expect((await store.read())?.refreshToken, 'refresh-1');
    });
  });

  group('email entry', () {
    test('a blank or malformed email never reaches the server', () async {
      await notifier().requestCode();
      expect(state().emailReason, 'not_blank');

      notifier().emailChanged('nope');
      await notifier().requestCode();
      expect(state().emailReason, 'email.invalid');

      expect(repository.requestedEmails, isEmpty);
      expect(state().step, LoginStep.entry);
    });

    test('typing clears the field reason and the last failure', () async {
      notifier().emailChanged('nope');
      await notifier().requestCode();
      notifier().emailChanged('nope@x.in');
      expect(state().emailReason, isNull);
      expect(state().failure, isNull);
    });

    test('the server\'s VALIDATION_FAILED reason lands on the field', () async {
      repository.onRequest(
        const ApiFailure(
          code: 'VALIDATION_FAILED',
          status: 400,
          details: {'email': 'email.invalid'},
        ),
      );
      notifier().emailChanged('odd@but.shaped');
      await notifier().requestCode();
      expect(state().emailReason, 'email.invalid');
      expect(state().failure?.code, 'VALIDATION_FAILED');
      expect(state().step, LoginStep.entry);
    });

    test('OTP_RATE_LIMITED sets the cooldown from retry_after and stays on entry', () async {
      repository.onRequest(
        const ApiFailure(
          code: 'OTP_RATE_LIMITED',
          status: 429,
          retryAfter: Duration(seconds: 3507),
        ),
      );
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();

      expect(state().failure?.code, 'OTP_RATE_LIMITED');
      expect(state().resendAt, now.add(const Duration(seconds: 3507)));
      expect(state().canResend, isFalse);
      expect(state().resendSeconds, 3507);
      expect(state().step, LoginStep.entry);
      expect(state().email, 'a@b.in');
    });
  });

  group('resend and the countdown', () {
    setUp(() async {
      repository.onRequest(FakeAuthRepository.challenge);
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
    });

    test('inside the cooldown is a no-op', () async {
      now = now.add(const Duration(seconds: 10));
      await notifier().resend();
      expect(repository.requestedEmails, hasLength(1));
    });

    test('the ticker moves the countdown while a code is pending', () async {
      ticks.add(now.add(const Duration(seconds: 12)));
      await Future<void>.delayed(Duration.zero);
      expect(state().now, now.add(const Duration(seconds: 12)));
      expect(state().resendSeconds, 18);
      expect(state().canResend, isFalse);

      ticks.add(now.add(const Duration(seconds: 30)));
      await Future<void>.delayed(Duration.zero);
      expect(state().resendSeconds, 0);
      expect(state().canResend, isTrue);
    });

    test('Change email keeps the ticker only while the cooldown is pending', () async {
      notifier().changeEmail();
      expect(ticks.hasListener, isTrue);
      expect(state().canRequest, isFalse);

      ticks.add(now.add(const Duration(seconds: 30)));
      await Future<void>.delayed(Duration.zero);
      expect(state().canRequest, isTrue);
      // The auto-disposed ticker is released one microtask after its last subscriber closes.
      await Future<void>.delayed(Duration.zero);
      expect(ticks.hasListener, isFalse);
    });

    test('Change email after the cooldown stops the ticker at once', () async {
      now = now.add(const Duration(seconds: 31));
      notifier().changeEmail();
      await Future<void>.delayed(Duration.zero);
      expect(ticks.hasListener, isFalse);
      expect(state().canRequest, isTrue);
    });

    test('after the cooldown sends a new code and resets attempts', () async {
      repository.onVerify(
        const ApiFailure(
          code: 'OTP_INVALID',
          status: 401,
          details: {'attempts_left': 4},
        ),
      );
      await notifier().verify('000000');
      expect(state().attemptsLeft, 4);

      repository.onRequest(
        const OtpChallenge(
          challengeId: 'c-2',
          resendAfter: Duration(seconds: 30),
          channel: 'email',
        ),
      );
      now = now.add(const Duration(seconds: 31));
      await notifier().resend();

      expect(repository.requestedEmails, hasLength(2));
      expect(state().challenge?.challengeId, 'c-2');
      expect(state().attemptsLeft, isNull);
      expect(state().codeDead, isFalse);
      expect(state().failure, isNull);
      expect(state().resendSeconds, 30);
    });

    test('a failed resend keeps the old challenge on screen', () async {
      repository.onRequest(const ApiFailure.offline());
      now = now.add(const Duration(seconds: 31));
      await notifier().resend();

      expect(state().step, LoginStep.code);
      expect(state().challenge?.challengeId, 'c-1');
      expect(state().failure?.isOffline, isTrue);
    });
  });

  group('the entry step honours the cooldown (PLAN D9 rows 4-5)', () {
    test('a fresh flow may request at once', () {
      expect(state().canRequest, isTrue);
      expect(state().longWait, isFalse);
      expect(state().resendMinutes, 0);
    });

    test('a 429 on entry disables Send code, counts down, and never calls inside the wait', () async {
      repository
        ..onRequest(
          const ApiFailure(
            code: 'OTP_RATE_LIMITED',
            status: 429,
            retryAfter: Duration(seconds: 20),
          ),
        )
        ..onRequest(FakeAuthRepository.challenge);
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
      expect(state().canRequest, isFalse);
      expect(state().resendSeconds, 20);
      expect(ticks.hasListener, isTrue);

      now = now.add(const Duration(seconds: 10));
      ticks.add(now);
      await Future<void>.delayed(Duration.zero);
      expect(state().resendSeconds, 10);
      await notifier().requestCode();
      expect(repository.requestedEmails, hasLength(1));
      expect(state().step, LoginStep.entry);

      now = now.add(const Duration(seconds: 10));
      ticks.add(now);
      await Future<void>.delayed(Duration.zero);
      expect(state().canRequest, isTrue);
      await notifier().requestCode();
      expect(repository.requestedEmails, hasLength(2));
      expect(state().step, LoginStep.code);
    });

    test('once the wait elapses on the entry step the ticker stops', () async {
      repository.onRequest(
        const ApiFailure(
          code: 'OTP_RATE_LIMITED',
          status: 429,
          retryAfter: Duration(seconds: 20),
        ),
      );
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();

      ticks.add(now.add(const Duration(seconds: 20)));
      await Future<void>.delayed(Duration.zero);
      expect(state().canRequest, isTrue);
      await Future<void>.delayed(Duration.zero);
      expect(ticks.hasListener, isFalse);
    });

    test('typing a different email lifts the cooldown; the same one keeps it', () async {
      repository
        ..onRequest(
          const ApiFailure(
            code: 'OTP_RATE_LIMITED',
            status: 429,
            retryAfter: Duration(seconds: 3507),
          ),
        )
        ..onRequest(FakeAuthRepository.challenge);
      notifier().emailChanged('capped@b.in');
      await notifier().requestCode();
      expect(state().canRequest, isFalse);

      notifier().emailChanged(' Capped@B.in');
      expect(state().canRequest, isFalse, reason: 'same destination, only spelled differently');

      notifier().emailChanged('other@b.in');
      expect(state().canRequest, isTrue);
      expect(state().resendAt, isNull);
      await Future<void>.delayed(Duration.zero);
      expect(ticks.hasListener, isFalse);

      await notifier().requestCode();
      expect(repository.requestedEmails, ['capped@b.in', 'other@b.in']);
      expect(state().step, LoginStep.code);
    });

    test('the countdown rounds up, so a blocked button never reads 0s', () async {
      repository.onRequest(FakeAuthRepository.challenge);
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();

      ticks.add(now.add(const Duration(seconds: 29, milliseconds: 100)));
      await Future<void>.delayed(Duration.zero);
      expect(state().canResend, isFalse);
      expect(state().resendSeconds, 1);

      ticks.add(now.add(const Duration(seconds: 30)));
      await Future<void>.delayed(Duration.zero);
      expect(state().canResend, isTrue);
      expect(state().resendSeconds, 0);
    });

    test('waits of a minute or more read in whole minutes, rounded up', () async {
      repository.onRequest(
        const ApiFailure(
          code: 'OTP_RATE_LIMITED',
          status: 429,
          retryAfter: Duration(seconds: 3507),
        ),
      );
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
      expect(state().longWait, isTrue);
      expect(state().resendMinutes, 59);

      ticks.add(now.add(const Duration(seconds: 3447)));
      await Future<void>.delayed(Duration.zero);
      expect(state().resendSeconds, 60);
      expect(state().longWait, isTrue);
      expect(state().resendMinutes, 1);

      ticks.add(now.add(const Duration(seconds: 3448)));
      await Future<void>.delayed(Duration.zero);
      expect(state().resendSeconds, 59);
      expect(state().longWait, isFalse);
    });
  });

  group('verify outcomes (DECISIONS D7 OTP outcomes row)', () {
    setUp(() async {
      repository.onRequest(FakeAuthRepository.challenge);
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
    });

    test('a non-6-digit code never reaches the server', () async {
      await notifier().verify('12');
      expect(state().codeReason, 'code.digits');
      await notifier().verify('');
      expect(state().codeReason, 'not_blank');
      expect(repository.verified, isEmpty);
    });

    test('OTP_INVALID counts attempts down; zero kills the code without a contradiction', () async {
      repository
        ..onVerify(
          const ApiFailure(
            code: 'OTP_INVALID',
            status: 401,
            messageUser: 'try once more',
            details: {'attempts_left': 1},
          ),
        )
        ..onVerify(
          const ApiFailure(
            code: 'OTP_INVALID',
            status: 401,
            messageUser: 'try once more',
            details: {'attempts_left': 0},
          ),
        );

      await notifier().verify('000001');
      expect(state().attemptsLeft, 1);
      expect(state().codeDead, isFalse);
      expect(state().failure?.messageUser, 'try once more');
      expect(state().step, LoginStep.code);

      await notifier().verify('000002');
      expect(state().attemptsLeft, 0);
      expect(state().codeDead, isTrue);
      // "Try once more" next to "no tries left" would contradict itself: only the attempts line.
      expect(state().failure, isNull);

      await notifier().verify('000003');
      expect(repository.verified, hasLength(2));
    });

    test('OTP_EXPIRED kills the code; the challenge stays for the screen', () async {
      repository.onVerify(const ApiFailure(code: 'OTP_EXPIRED', status: 401));
      await notifier().verify('444771');
      expect(state().codeDead, isTrue);
      expect(state().failure?.code, 'OTP_EXPIRED');
      expect(state().step, LoginStep.code);
      expect(state().signedIn, isFalse);
    });

    test('RATE_LIMITED on verify sets the cooldown', () async {
      repository.onVerify(
        const ApiFailure(
          code: 'RATE_LIMITED',
          status: 429,
          retryAfter: Duration(seconds: 7),
        ),
      );
      await notifier().verify('444771');
      expect(state().resendAt, now.add(const Duration(seconds: 7)));
    });
  });

  group('flaky network (DEV_SPEC §6 screen 1, SPEC §1 principle 5)', () {
    test('offline on request keeps the email; Retry re-sends it', () async {
      repository
        ..onRequest(const ApiFailure.offline())
        ..onRequest(FakeAuthRepository.challenge);
      notifier().emailChanged('a@b.in');

      await notifier().requestCode();
      expect(state().failure?.isOffline, isTrue);
      expect(state().canRetry, isTrue);
      expect(state().email, 'a@b.in');
      expect(state().step, LoginStep.entry);

      await notifier().retry();
      expect(repository.requestedEmails, ['a@b.in', 'a@b.in']);
      expect(state().step, LoginStep.code);
      expect(state().failure, isNull);
    });

    test('offline on verify keeps the code; Retry re-verifies the same code', () async {
      repository
        ..onRequest(FakeAuthRepository.challenge)
        ..onVerify(const ApiFailure.offline())
        ..onVerify(FakeAuthRepository.signedIn);
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();

      await notifier().verify('444771');
      expect(state().failure?.isOffline, isTrue);
      expect(state().canRetry, isTrue);
      expect(state().lastIntent, isA<VerifyIntent>());

      await notifier().retry();
      expect(repository.verified, [
        (challengeId: 'c-1', code: '444771'),
        (challengeId: 'c-1', code: '444771'),
      ]);
      expect(state().signedIn, isTrue);
    });

    test('Retry is offered after a malformed or certificate answer and re-runs the intent', () async {
      repository
        ..onRequest(const ApiFailure.malformed(502))
        ..onRequest(FakeAuthRepository.challenge)
        ..onVerify(const ApiFailure.certificate())
        ..onVerify(FakeAuthRepository.signedIn);
      notifier().emailChanged('a@b.in');

      await notifier().requestCode();
      expect(state().failure?.isMalformed, isTrue);
      expect(state().canRetry, isTrue);
      await notifier().retry();
      expect(repository.requestedEmails, ['a@b.in', 'a@b.in']);
      expect(state().step, LoginStep.code);

      await notifier().verify('444771');
      expect(state().failure?.isCertificate, isTrue);
      expect(state().canRetry, isTrue);
      await notifier().retry();
      expect(repository.verified, hasLength(2));
      expect(state().signedIn, isTrue);
    });

    test('Retry is not offered after a server answer', () async {
      repository
        ..onRequest(FakeAuthRepository.challenge)
        ..onVerify(const ApiFailure(code: 'OTP_INVALID', status: 401));
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
      await notifier().verify('000000');
      expect(state().canRetry, isFalse);
    });
  });

  group('change email and sign-out', () {
    test('Change email returns to entry with the email kept and the challenge dropped', () async {
      repository
        ..onRequest(FakeAuthRepository.challenge)
        ..onVerify(const ApiFailure(code: 'OTP_EXPIRED', status: 401));
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
      await notifier().verify('444771');

      notifier().changeEmail();

      expect(state().step, LoginStep.entry);
      expect(state().email, 'a@b.in');
      expect(state().challenge, isNull);
      expect(state().failure, isNull);
      expect(state().codeDead, isFalse);
      expect(state().attemptsLeft, isNull);
      // The cooldown from the earlier send still applies to the next request.
      expect(state().canResendAt(now), isFalse);
    });

    test('a sign-out starts the flow over, so the guard lands on /login (TECH_PLAN §5.3)', () async {
      repository
        ..onRequest(FakeAuthRepository.challenge)
        ..onVerify(FakeAuthRepository.signedIn);
      notifier().emailChanged('a@b.in');
      await notifier().requestCode();
      await notifier().verify('444771');
      expect(state().signedIn, isTrue);

      await container.read(authStateProvider.notifier).signOut();

      expect(state().step, LoginStep.entry);
      expect(state().signedIn, isFalse);
      expect(state().email, isEmpty);
      // The auto-disposed ticker is released one microtask after its last subscriber closes.
      await Future<void>.delayed(Duration.zero);
      expect(ticks.hasListener, isFalse);
    });
  });
}
