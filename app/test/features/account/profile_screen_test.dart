import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/features/account/providers.dart';
import 'package:margai/features/account/screens/profile_screen.dart';

import '../../support/fake_account_repository.dart';
import '../../support/fake_auth_repository.dart';
import '../../support/pump.dart';

/// SPEC §8 screen 13 (Profile & settings) at D10 — the language switch of §6.11 and logout —
/// per state in all three locales (TECH_PLAN §8.4). The screen renders [SettingsState] and the
/// stored session and dispatches intents; nothing is decided here.
void main() {
  Future<InMemoryTokenStore> signedInStore() async {
    final store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    return store;
  }

  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    group('ProfileScreen in ${locale.toLanguageTag()}', () {
      testWidgets('idle: title, identity, the three languages with the current one selected, Log out', (
        tester,
      ) async {
        await pumpScreen(
          tester,
          const ProfileScreen(),
          locale: locale,
          store: await signedInStore(),
        );

        expect(find.text(l10n.profileTitle), findsOneWidget);
        expect(find.text(l10n.signedInAs('founder@example.com')), findsOneWidget);
        expect(find.text(l10n.languageLabel), findsOneWidget);
        expect(find.text(l10n.languageNote), findsOneWidget);
        expect(find.text(l10n.languageEnglish), findsOneWidget);
        expect(find.text(l10n.languageHindi), findsOneWidget);
        expect(find.text(l10n.languageHinglish), findsOneWidget);
        expect(find.text(l10n.profileNote), findsOneWidget);
        expect(find.text(l10n.logoutButton), findsOneWidget);
        expect(find.byType(LinearProgressIndicator), findsNothing);
        final english = tester.widget<RadioListTile<AppLanguage>>(
          find.widgetWithText(RadioListTile<AppLanguage>, l10n.languageEnglish),
        );
        expect(english.value, AppLanguage.en);
        expect(
          tester.widget<FilledButton>(find.byType(FilledButton)).onPressed,
          isNotNull,
        );
        // Only what exists is offered (SPEC §1): the three language tiles, no subscription,
        // export or deletion entries.
        expect(find.byType(ListTile), findsNWidgets(3));
        expect(find.byType(FilledButton), findsOneWidget);
      });

      testWidgets('busy: progress shown, the button and the options disabled', (tester) async {
        await pumpScreen(
          tester,
          const ProfileScreen(),
          locale: locale,
          store: await signedInStore(),
          settings: const SettingsState(busy: true),
        );

        expect(find.byType(LinearProgressIndicator), findsOneWidget);
        expect(find.text(l10n.loggingOut), findsOneWidget);
        expect(
          tester.widget<FilledButton>(find.byType(FilledButton)).onPressed,
          isNull,
        );
        expect(
          tester
              .widgetList<RadioListTile<AppLanguage>>(find.byType(RadioListTile<AppLanguage>))
              .map((tile) => tile.enabled),
          everyElement(isFalse),
        );
      });

      testWidgets('a failed switch shows the honest line with Retry; the options stay live', (
        tester,
      ) async {
        await pumpScreen(
          tester,
          const ProfileScreen(),
          locale: locale,
          store: await signedInStore(),
          settings: const SettingsState(
            failure: ApiFailure.offline(),
            lastLanguage: AppLanguage.hi,
          ),
        );

        expect(find.text(l10n.failureOffline), findsOneWidget);
        expect(find.text(l10n.retryButton), findsOneWidget);
        expect(
          tester
              .widgetList<RadioListTile<AppLanguage>>(find.byType(RadioListTile<AppLanguage>))
              .map((tile) => tile.enabled),
          everyElement(isTrue),
        );
      });

      testWidgets('a /me that could not be fetched is shown honestly with Retry', (tester) async {
        final accounts = FakeAccountRepository()
          ..onGetMe(const ApiFailure.offline())
          ..onGetMe(FakeAccountRepository.me);

        await pumpScreen(
          tester,
          const ProfileScreen(),
          locale: locale,
          store: await signedInStore(),
          accounts: accounts,
        );

        expect(find.text(l10n.failureOffline), findsOneWidget);
        expect(find.text(l10n.signedInAs('founder@example.com')), findsOneWidget);

        await tester.ensureVisible(find.text(l10n.retryButton));
        await tester.tap(find.text(l10n.retryButton));
        await tester.pumpAndSettle();

        expect(find.text(l10n.failureOffline), findsNothing);
        expect(accounts.gets, 2);
      });
    });
  }

  group('intents', () {
    final locale = allLocales.first;
    final l10n = copyFor(locale);

    testWidgets('a /me the server answered with an error shows the line without Retry (the D8 rule)', (
      tester,
    ) async {
      final accounts = FakeAccountRepository()
        ..onGetMe(
          const ApiFailure(
            code: 'INTERNAL',
            status: 500,
            details: {'request_id': 'req-9'},
          ),
        );

      await pumpScreen(
        tester,
        const ProfileScreen(),
        locale: locale,
        store: await signedInStore(),
        accounts: accounts,
      );

      expect(find.text(l10n.errorInternal), findsOneWidget);
      expect(find.text(l10n.failureRequestId('req-9')), findsOneWidget);
      expect(find.text(l10n.retryButton), findsNothing);
    });

    testWidgets('tapping हिन्दी dispatches the switch and the screen re-renders in Hindi', (
      tester,
    ) async {
      final store = await signedInStore();
      final accounts = FakeAccountRepository()
        ..onGetMe(FakeAccountRepository.me)
        ..onUpdate(FakeAccountRepository.meInHindi);
      final auth = FakeAuthRepository()..onRefresh(FakeAuthRepository.rotated);
      await pumpScreen(
        tester,
        const ProfileScreen(),
        locale: locale,
        store: store,
        accounts: accounts,
        repository: auth,
      );

      await tester.tap(find.text(l10n.languageHindi));
      await tester.pumpAndSettle();

      expect(accounts.updatedLanguages, [AppLanguage.hi]);
      expect((await store.read())?.user.language, AppLanguage.hi);
      expect(auth.refreshed, ['refresh-1']);
      final selected = tester.widget<RadioGroup<AppLanguage>>(find.byType(RadioGroup<AppLanguage>));
      expect(selected.groupValue, AppLanguage.hi);
    });

    testWidgets('tapping Log out tells the server and clears the device', (tester) async {
      final store = await signedInStore();
      final auth = FakeAuthRepository()..onLogout(null);
      await pumpScreen(
        tester,
        const ProfileScreen(),
        locale: locale,
        store: store,
        repository: auth,
      );

      await tester.tap(find.text(l10n.logoutButton));
      await tester.pumpAndSettle();

      expect(auth.loggedOut, ['refresh-1']);
      expect(await store.read(), isNull);
    });
  });
}
