import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/app.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/clock.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/core/ticker.dart';
import 'package:margai/features/account/repository.dart';
import 'package:margai/features/account/screens/profile_screen.dart';
import 'package:margai/features/auth/repository.dart';
import 'package:margai/features/auth/screens/login_screen.dart';
import 'package:margai/features/auth/screens/otp_screen.dart';
import 'package:margai/features/planner/screens/today_placeholder_screen.dart';

import 'support/fake_account_repository.dart';
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
    FakeAccountRepository? accounts,
    DateTime Function()? clock,
    Stream<DateTime>? ticks,
  }) async {
    await tester.pumpWidget(
      ProviderScope(
        overrides: [
          tokenStoreProvider.overrideWithValue(store),
          authRepositoryProvider.overrideWithValue(
            repository ?? FakeAuthRepository(),
          ),
          accountRepositoryProvider.overrideWithValue(
            accounts ?? (FakeAccountRepository()..onGetMe(FakeAccountRepository.me)),
          ),
          clockProvider.overrideWithValue(clock ?? () => now),
          tickerProvider.overrideWith((ref) => ticks ?? Stream.value(now)),
        ],
        child: const MargaiApp(),
      ),
    );
    await tester.pumpAndSettle();
  }

  testWidgets('a stored session fetches /me once on start (TECH_PLAN §3.7)', (tester) async {
    final store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    final accounts = FakeAccountRepository()..onGetMe(FakeAccountRepository.me);

    await pumpApp(tester, store: store, accounts: accounts);

    expect(find.byType(TodayPlaceholderScreen), findsOneWidget);
    expect(accounts.gets, 1);
  });

  testWidgets('Today → Profile → Log out → the login screen, fresh; a restart stays signed out', (
    tester,
  ) async {
    final store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    final auth = FakeAuthRepository()..onLogout(null);
    final l10n = copyFor(allLocales.first);
    await pumpApp(tester, store: store, repository: auth);

    await tester.tap(find.byTooltip(l10n.profileOpenButton));
    await tester.pumpAndSettle();
    expect(find.byType(ProfileScreen), findsOneWidget);
    expect(find.text(l10n.signedInAs('founder@example.com')), findsOneWidget);

    await tester.tap(find.text(l10n.logoutButton));
    await tester.pumpAndSettle();

    expect(find.byType(LoginScreen), findsOneWidget);
    expect(find.byType(ProfileScreen), findsNothing);
    expect(auth.loggedOut, ['refresh-1']);
    expect(await store.read(), isNull);
    expect(tester.widget<TextField>(find.byType(TextField)).controller?.text, isEmpty);

    // A cold start over the cleared store: still the login screen (PLAN D10 ✅ "clean state").
    await pumpApp(tester, store: store);
    expect(find.byType(LoginScreen), findsOneWidget);
  });

  testWidgets('a language switch on Profile re-renders the app in that language', (tester) async {
    final store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    final accounts = FakeAccountRepository()
      ..onGetMe(FakeAccountRepository.me)
      ..onUpdate(FakeAccountRepository.meInHindi);
    final auth = FakeAuthRepository()..onRefresh(FakeAuthRepository.rotated);
    final english = copyFor(AppLanguage.en.locale);
    final hindi = copyFor(AppLanguage.hi.locale);
    await pumpApp(tester, store: store, accounts: accounts, repository: auth);

    await tester.tap(find.byTooltip(english.profileOpenButton));
    await tester.pumpAndSettle();
    await tester.tap(find.text(english.languageHindi));
    await tester.pumpAndSettle();

    expect(find.text(hindi.profileTitle), findsOneWidget);
    expect(find.text(hindi.logoutButton), findsOneWidget);
    expect((await store.read())?.user.language, AppLanguage.hi);
    expect((await store.read())?.accessToken, 'access-2');
  });

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
    final l10n = copyFor(allLocales.first);
    var clock = now;
    final ticks = StreamController<DateTime>.broadcast();
    addTearDown(ticks.close);
    await pumpApp(
      tester,
      store: store,
      repository: repository,
      clock: () => clock,
      ticks: ticks.stream,
    );

    await tester.enterText(find.byType(TextField), 'founder@example.com');
    await tester.tap(find.byType(FilledButton));
    await tester.pumpAndSettle();
    expect(find.byType(OtpScreen), findsOneWidget);

    await tester.tap(find.text(l10n.changeEmailButton));
    await tester.pumpAndSettle();
    expect(find.byType(LoginScreen), findsOneWidget);
    expect(
      tester.widget<TextField>(find.byType(TextField)).controller?.text,
      'founder@example.com',
    );
    // The cooldown of the code just sent still applies (PLAN D9 row 4): the button counts down.
    expect(find.text(l10n.sendCodeIn(30)), findsOneWidget);
    expect(
      tester.widget<FilledButton>(find.byType(FilledButton)).onPressed,
      isNull,
    );

    clock = now.add(const Duration(seconds: 31));
    ticks.add(clock);
    await tester.pumpAndSettle();
    expect(find.text(l10n.sendCodeButton), findsOneWidget);

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
