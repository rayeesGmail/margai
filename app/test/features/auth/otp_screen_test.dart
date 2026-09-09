import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/features/auth/models.dart';
import 'package:margai/features/auth/providers.dart';
import 'package:margai/features/auth/screens/otp_screen.dart';

import '../../support/fake_auth_repository.dart';
import '../../support/pump.dart';

void main() {
  final now = DateTime.utc(2026, 9, 8, 12);
  final sent = LoginState(
    email: 'founder@example.com',
    challenge: FakeAuthRepository.challenge,
    resendAt: now.add(const Duration(seconds: 27)),
    now: now,
    lastIntent: const RequestCodeIntent(),
  );

  FilledButton verifyButton(WidgetTester tester) =>
      tester.widget<FilledButton>(find.byType(FilledButton));

  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    group('OtpScreen in ${locale.toLanguageTag()} (SPEC §8 screen 1)', () {
      testWidgets('sent: where the code went, the field, the countdown, change email', (
        tester,
      ) async {
        await pumpScreen(tester, const OtpScreen(), locale: locale, state: sent, now: now);

        expect(find.text(l10n.codeSentTitle), findsOneWidget);
        expect(find.text(l10n.codeSentTo('founder@example.com')), findsOneWidget);
        expect(find.text(l10n.codeLabel), findsOneWidget);
        expect(find.text(l10n.verifyButton), findsOneWidget);
        expect(find.text(l10n.resendIn(27)), findsOneWidget);
        expect(find.text(l10n.resendButton), findsNothing);
        expect(find.text(l10n.changeEmailButton), findsOneWidget);
        expect(verifyButton(tester).onPressed, isNotNull);
      });

      testWidgets('after the cooldown the resend is offered', (tester) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          state: sent,
          now: now.add(const Duration(seconds: 30)),
        );
        expect(find.text(l10n.resendButton), findsOneWidget);
        expect(
          tester
              .widget<TextButton>(
                find.widgetWithText(TextButton, l10n.resendButton),
              )
              .onPressed,
          isNotNull,
        );
      });

      testWidgets('a long wait on the code step reads in minutes', (tester) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          now: now,
          state: sent.copyWith(resendAt: now.add(const Duration(seconds: 3507))),
        );
        expect(find.text(l10n.resendInMinutes(59)), findsOneWidget);
        expect(find.text(l10n.resendButton), findsNothing);
      });

      testWidgets('wrong code: server copy plus the attempts-left plural', (tester) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          now: now,
          state: sent.copyWith(
            attemptsLeft: 3,
            failure: const ApiFailure(
              code: 'OTP_INVALID',
              status: 401,
              messageUser: 'server: no match',
              details: {'attempts_left': 3},
            ),
            lastIntent: const VerifyIntent('000000'),
          ),
        );
        expect(find.text('server: no match'), findsOneWidget);
        expect(find.text(l10n.attemptsLeft(3)), findsOneWidget);
        expect(find.text(l10n.retryButton), findsNothing);
        expect(verifyButton(tester).onPressed, isNotNull);
      });

      testWidgets('a dead code disables verify and the field; a new code is the way out', (
        tester,
      ) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          now: now.add(const Duration(seconds: 30)),
          state: sent.copyWith(
            codeDead: true,
            failure: const ApiFailure(code: 'OTP_EXPIRED', status: 401),
          ),
        );
        expect(find.text(l10n.errorOtpExpired), findsOneWidget);
        expect(verifyButton(tester).onPressed, isNull);
        expect(tester.widget<TextField>(find.byType(TextField)).enabled, isFalse);
        expect(find.text(l10n.resendButton), findsOneWidget);
      });

      testWidgets('attempts exhausted: the attempts line alone, verify disabled', (
        tester,
      ) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          now: now,
          state: sent.copyWith(codeDead: true, attemptsLeft: 0),
        );
        expect(find.text(l10n.attemptsLeft(0)), findsOneWidget);
        expect(find.text(l10n.errorOtpInvalid), findsNothing);
        expect(verifyButton(tester).onPressed, isNull);
      });

      testWidgets('offline on verify: honest copy and Retry', (tester) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          now: now,
          state: sent.copyWith(
            failure: const ApiFailure.offline(),
            lastIntent: const VerifyIntent('444771'),
          ),
        );
        expect(find.text(l10n.failureOffline), findsOneWidget);
        expect(find.text(l10n.retryButton), findsOneWidget);
      });

      testWidgets('busy: checking label, everything disabled', (tester) async {
        await pumpScreen(
          tester,
          const OtpScreen(),
          locale: locale,
          now: now,
          state: sent.copyWith(busy: true),
        );
        expect(find.text(l10n.verifying), findsOneWidget);
        expect(find.byType(LinearProgressIndicator), findsOneWidget);
        expect(verifyButton(tester).onPressed, isNull);
      });
    });
  }

  testWidgets('the sixth digit submits and a good code signs the student in', (tester) async {
    final repository = FakeAuthRepository()..onVerify(FakeAuthRepository.signedIn);
    await pumpScreen(
      tester,
      const OtpScreen(),
      locale: allLocales.first,
      repository: repository,
      state: sent,
      now: now,
    );
    final container = containerOf(tester, OtpScreen);
    await container.read(authStateProvider.future);

    await tester.enterText(find.byType(TextField), '444771');
    await tester.pumpAndSettle();

    expect(repository.verified, [(challengeId: 'c-1', code: '444771')]);
    expect(container.read(loginProvider).signedIn, isTrue);
    expect(container.read(authStateProvider).value, isA<SignedIn>());
  });

  testWidgets('letters are filtered out and a short code is refused on the device', (
    tester,
  ) async {
    final repository = FakeAuthRepository();
    await pumpScreen(
      tester,
      const OtpScreen(),
      locale: allLocales.first,
      repository: repository,
      state: sent,
      now: now,
    );

    await tester.enterText(find.byType(TextField), '12ab');
    await tester.tap(find.byType(FilledButton));
    await tester.pumpAndSettle();

    expect(tester.widget<TextField>(find.byType(TextField)).controller?.text, '12');
    expect(find.text(copyFor(allLocales.first).reasonCodeDigits), findsOneWidget);
    expect(repository.verified, isEmpty);
  });

  testWidgets('a new code empties the stale digits from the field', (tester) async {
    final repository = FakeAuthRepository()
      ..onRequest(
        const OtpChallenge(
          challengeId: 'c-2',
          resendAfter: Duration(seconds: 30),
          channel: 'email',
        ),
      );
    await pumpScreen(
      tester,
      const OtpScreen(),
      locale: allLocales.first,
      repository: repository,
      state: sent,
      now: now.add(const Duration(seconds: 30)),
    );
    await tester.enterText(find.byType(TextField), '00000');
    expect(tester.widget<TextField>(find.byType(TextField)).controller?.text, '00000');

    await tester.tap(find.text(copyFor(allLocales.first).resendButton));
    await tester.pumpAndSettle();

    expect(repository.requestedEmails, ['founder@example.com']);
    expect(tester.widget<TextField>(find.byType(TextField)).controller?.text, isEmpty);
  });

  testWidgets('Change email drops the challenge and keeps the email', (tester) async {
    await pumpScreen(
      tester,
      const OtpScreen(),
      locale: allLocales.first,
      state: sent,
      now: now,
    );

    await tester.tap(find.text(copyFor(allLocales.first).changeEmailButton));
    await tester.pump();

    final state = containerOf(tester, OtpScreen).read(loginProvider);
    expect(state.step, LoginStep.entry);
    expect(state.email, 'founder@example.com');
  });
}
