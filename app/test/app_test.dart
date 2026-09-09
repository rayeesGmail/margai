import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/app.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/clock.dart';
import 'package:margai/core/ticker.dart';
import 'package:margai/features/auth/repository.dart';
import 'package:margai/features/auth/screens/login_screen.dart';
import 'package:margai/features/auth/screens/otp_screen.dart';
import 'package:margai/features/planner/screens/today_placeholder_screen.dart';

import 'support/fake_auth_repository.dart';
import 'support/pump.dart';

/// The whole app through the real router (TECH_PLAN §5.3 guard), with the network and the
/// device storage faked.
void main() {
  final now = DateTime.utc(2026, 9, 8, 12);

  Future<void> pumpApp(
    WidgetTester tester, {
    required TokenStore store,
    FakeAuthRepository? repository,
  }) async {
    await tester.pumpWidget(
      ProviderScope(
        overrides: [
          tokenStoreProvider.overrideWithValue(store),
          authRepositoryProvider.overrideWithValue(
            repository ?? FakeAuthRepository(),
          ),
          clockProvider.overrideWithValue(() => now),
          tickerProvider.overrideWith((ref) => Stream.value(now)),
        ],
        child: const MargaiApp(),
      ),
    );
    await tester.pumpAndSettle();
  }

  testWidgets('a fresh install boots to the login screen', (tester) async {
    await pumpApp(tester, store: InMemoryTokenStore());
    expect(find.byType(LoginScreen), findsOneWidget);
    expect(find.text(copyFor(allLocales.first).loginHeadline), findsOneWidget);
  });

  testWidgets('a stored session boots straight to the signed-in landing', (tester) async {
    final store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());

    await pumpApp(tester, store: store);

    expect(find.byType(TodayPlaceholderScreen), findsOneWidget);
    expect(find.byType(LoginScreen), findsNothing);
    expect(
      find.text(
        copyFor(allLocales.first).todayPlaceholderBody('founder@example.com'),
      ),
      findsOneWidget,
    );
  });

  testWidgets('email → code screen → verified → landing, then the session is on the device', (
    tester,
  ) async {
    final store = InMemoryTokenStore();
    final repository = FakeAuthRepository()
      ..onRequest(FakeAuthRepository.challenge)
      ..onVerify(FakeAuthRepository.signedIn);
    await pumpApp(tester, store: store, repository: repository);

    await tester.enterText(find.byType(TextField), 'founder@example.com');
    await tester.tap(find.byType(FilledButton));
    await tester.pumpAndSettle();
    expect(find.byType(OtpScreen), findsOneWidget);

    await tester.tap(find.text(copyFor(allLocales.first).changeEmailButton));
    await tester.pumpAndSettle();
    expect(find.byType(LoginScreen), findsOneWidget);
    expect(
      tester.widget<TextField>(find.byType(TextField)).controller?.text,
      'founder@example.com',
    );

    repository.onRequest(FakeAuthRepository.challenge);
    await tester.tap(find.byType(FilledButton));
    await tester.pumpAndSettle();
    expect(find.byType(OtpScreen), findsOneWidget);

    await tester.enterText(find.byType(TextField), '444771');
    await tester.pumpAndSettle();

    expect(find.byType(TodayPlaceholderScreen), findsOneWidget);
    expect((await store.read())?.refreshToken, 'refresh-1');
  });
}
