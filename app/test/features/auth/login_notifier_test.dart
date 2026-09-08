import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/clock.dart';
import 'package:margai/features/auth/models.dart';
import 'package:margai/features/auth/providers.dart';
import 'package:margai/features/auth/repository.dart';

import '../../support/fake_auth_repository.dart';

void main() {
  late FakeAuthRepository repository;
  late InMemoryTokenStore store;
  late DateTime now;
  late ProviderContainer container;

  LoginNotifier notifier() => container.read(loginProvider.notifier);
  LoginState state() => container.read(loginProvider);

  setUp(() async {
    repository = FakeAuthRepository();
    store = InMemoryTokenStore();
    now = DateTime.utc(2026, 9, 8, 12, 0, 0);
    container = ProviderContainer.test(
      overrides: [
        authRepositoryProvider.overrideWithValue(repository),
        tokenStoreProvider.overrideWithValue(store),
        clockProvider.overrideWithValue(() => now),
      ],
    );
    await container.read(authStateProvider.future);
  });

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
      expect(state().canResend(now), isFalse);
      expect(state().resendIn(now), const Duration(seconds: 30));
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
      expect(state().canResend(now), isFalse);
      expect(state().step, LoginStep.entry);
      expect(state().email, 'a@b.in');
    });
  });

  group('resend', () {
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

    test('OTP_INVALID counts attempts down; zero kills the code', () async {
      repository
        ..onVerify(
          const ApiFailure(
            code: 'OTP_INVALID',
            status: 401,
            details: {'attempts_left': 1},
          ),
        )
        ..onVerify(
          const ApiFailure(
            code: 'OTP_INVALID',
            status: 401,
            details: {'attempts_left': 0},
          ),
        );

      await notifier().verify('000001');
      expect(state().attemptsLeft, 1);
      expect(state().codeDead, isFalse);
      expect(state().step, LoginStep.code);

      await notifier().verify('000002');
      expect(state().attemptsLeft, 0);
      expect(state().codeDead, isTrue);
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

  group('change email', () {
    test('returns to entry with the email kept and the challenge dropped', () async {
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
      expect(state().canResend(now), isFalse);
    });
  });
}
