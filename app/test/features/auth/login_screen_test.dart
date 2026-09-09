import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/features/auth/providers.dart';
import 'package:margai/features/auth/screens/login_screen.dart';

import '../../support/fake_auth_repository.dart';
import '../../support/pump.dart';

void main() {
  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    group('LoginScreen in ${locale.toLanguageTag()} (SPEC §8 screen 1)', () {
      testWidgets('idle: mentor headline, email field, send button', (tester) async {
        await pumpScreen(tester, const LoginScreen(), locale: locale);

        expect(find.text(l10n.loginHeadline), findsOneWidget);
        expect(find.text(l10n.loginIntro), findsOneWidget);
        expect(find.text(l10n.emailLabel), findsOneWidget);
        expect(find.text(l10n.sendCodeButton), findsOneWidget);
        expect(find.byType(LinearProgressIndicator), findsNothing);
      });

      testWidgets('busy: label changes, button and field disabled', (tester) async {
        await pumpScreen(
          tester,
          const LoginScreen(),
          locale: locale,
          state: const LoginState(email: 'a@b.in', busy: true),
        );

        expect(find.text(l10n.sendingCode), findsOneWidget);
        expect(find.byType(LinearProgressIndicator), findsOneWidget);
        expect(
          tester.widget<FilledButton>(find.byType(FilledButton)).onPressed,
          isNull,
        );
        expect(tester.widget<TextField>(find.byType(TextField)).enabled, isFalse);
      });

      testWidgets('inside a cooldown: Send code is disabled with the countdown, email editable', (
        tester,
      ) async {
        final now = DateTime.utc(2026, 9, 8, 12);
        await pumpScreen(
          tester,
          const LoginScreen(),
          locale: locale,
          now: now,
          state: LoginState(
            email: 'a@b.in',
            resendAt: now.add(const Duration(seconds: 20)),
            now: now,
            failure: const ApiFailure(code: 'OTP_RATE_LIMITED', status: 429),
            lastIntent: const RequestCodeIntent(),
          ),
        );

        expect(find.text(l10n.sendCodeIn(20)), findsOneWidget);
        expect(find.text(l10n.sendCodeButton), findsNothing);
        expect(
          tester.widget<FilledButton>(find.byType(FilledButton)).onPressed,
          isNull,
        );
        expect(tester.widget<TextField>(find.byType(TextField)).enabled, isTrue);
      });

      testWidgets('a long wait reads in minutes', (tester) async {
        final now = DateTime.utc(2026, 9, 8, 12);
        await pumpScreen(
          tester,
          const LoginScreen(),
          locale: locale,
          now: now,
          state: LoginState(
            email: 'a@b.in',
            resendAt: now.add(const Duration(seconds: 3507)),
            now: now,
          ),
        );
        expect(find.text(l10n.sendCodeInMinutes(59)), findsOneWidget);
      });

      testWidgets('field reason renders from ARB, not server prose', (tester) async {
        await pumpScreen(
          tester,
          const LoginScreen(),
          locale: locale,
          state: const LoginState(email: 'nope', emailReason: 'email.invalid'),
        );
        expect(find.text(l10n.reasonEmailInvalid), findsOneWidget);
      });

      testWidgets('an envelope failure shows the server\'s user-language copy, no Retry', (
        tester,
      ) async {
        await pumpScreen(
          tester,
          const LoginScreen(),
          locale: locale,
          state: const LoginState(
            email: 'a@b.in',
            failure: ApiFailure(
              code: 'OTP_RATE_LIMITED',
              status: 429,
              messageEn: 'Too many codes requested.',
              messageUser: 'server copy in my language',
            ),
            lastIntent: RequestCodeIntent(),
          ),
        );
        expect(find.text('server copy in my language'), findsOneWidget);
        expect(find.text(l10n.retryButton), findsNothing);
      });

      testWidgets('offline: honest copy, email kept, Retry re-sends', (tester) async {
        final repository = FakeAuthRepository()
          ..onRequest(FakeAuthRepository.challenge);
        await pumpScreen(
          tester,
          const LoginScreen(),
          locale: locale,
          repository: repository,
          state: const LoginState(
            email: 'a@b.in',
            failure: ApiFailure.offline(),
            lastIntent: RequestCodeIntent(),
          ),
        );

        expect(find.text(l10n.failureOffline), findsOneWidget);
        expect(
          tester.widget<TextField>(find.byType(TextField)).controller?.text,
          'a@b.in',
        );

        await tester.tap(find.text(l10n.retryButton));
        await tester.pumpAndSettle();

        expect(repository.requestedEmails, ['a@b.in']);
        final state = containerOf(tester, LoginScreen).read(loginProvider);
        expect(state.step, LoginStep.code);
        expect(state.failure, isNull);
      });
    });
  }

  testWidgets('typing an email and tapping Send asks the server for a code', (tester) async {
    final repository = FakeAuthRepository()
      ..onRequest(FakeAuthRepository.challenge);
    await pumpScreen(
      tester,
      const LoginScreen(),
      locale: allLocales.first,
      repository: repository,
    );

    await tester.enterText(find.byType(TextField), 'founder@example.com');
    await tester.tap(find.byType(FilledButton));
    await tester.pumpAndSettle();

    expect(repository.requestedEmails, ['founder@example.com']);
    expect(
      containerOf(tester, LoginScreen).read(loginProvider).step,
      LoginStep.code,
    );
  });

  testWidgets('a blank email is refused on the device', (tester) async {
    final repository = FakeAuthRepository();
    await pumpScreen(
      tester,
      const LoginScreen(),
      locale: allLocales.first,
      repository: repository,
    );

    await tester.tap(find.byType(FilledButton));
    await tester.pumpAndSettle();

    expect(find.text(copyFor(allLocales.first).reasonNotBlank), findsOneWidget);
    expect(repository.requestedEmails, isEmpty);
  });
}
