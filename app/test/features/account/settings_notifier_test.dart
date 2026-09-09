import 'package:flutter/widgets.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/features/account/providers.dart';
import 'package:margai/features/account/repository.dart';
import 'package:margai/features/auth/providers.dart';
import 'package:margai/features/auth/repository.dart';

import '../../support/fake_account_repository.dart';
import '../../support/fake_auth_repository.dart';

/// SPEC §6.11 (language switch) and §8 screen 13 (logout) as `account · SettingsNotifier`
/// (TECH_PLAN §5.8, D10; DECISIONS D10): a switch is saved on the server, then locally, then the
/// locale follows and the tokens are rotated so the JWT agrees; logout is best-effort on the
/// server and unconditional on the device.
void main() {
  late FakeAccountRepository accounts;
  late FakeAuthRepository auth;
  late InMemoryTokenStore store;
  late ProviderContainer container;

  SettingsNotifier notifier() => container.read(settingsProvider.notifier);
  SettingsState state() => container.read(settingsProvider);

  setUp(() async {
    accounts = FakeAccountRepository()..onGetMe(FakeAccountRepository.me);
    auth = FakeAuthRepository();
    store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    container = ProviderContainer.test(
      overrides: [
        tokenStoreProvider.overrideWithValue(store),
        accountRepositoryProvider.overrideWithValue(accounts),
        authRepositoryProvider.overrideWithValue(auth),
      ],
    );
    await container.read(authStateProvider.future);
    // Riverpod 3 pauses a provider's own subscriptions while nothing listens; in the app the
    // screens watch these, so the test keeps the same listeners open.
    for (final subscription in [
      container.listen(settingsProvider, (_, _) {}),
      container.listen(meProvider, (_, _) {}),
      container.listen(localeProvider, (_, _) {}),
      container.listen(loginProvider, (_, _) {}),
    ]) {
      addTearDown(subscription.close);
    }
    await container.read(meProvider.future);
  });

  group('setLanguage', () {
    test('saves on the server, then locally; the locale follows and the tokens rotate', () async {
      accounts.onUpdate(FakeAccountRepository.meInHindi);
      auth.onRefresh(FakeAuthRepository.rotated);

      await notifier().setLanguage(AppLanguage.hi);

      expect(accounts.updatedLanguages, [AppLanguage.hi]);
      final stored = await store.read();
      expect(stored?.user.language, AppLanguage.hi);
      expect((container.read(authStateProvider).value! as SignedIn).user.language, AppLanguage.hi);
      expect(container.read(localeProvider), const Locale('hi'));
      expect(container.read(meProvider).value?.user.language, AppLanguage.hi);
      expect(auth.refreshed, ['refresh-1']);
      expect(stored?.accessToken, 'access-2');
      expect(stored?.refreshToken, 'refresh-2');
      expect(accounts.gets, 1);
      expect(state().busy, isFalse);
      expect(state().failure, isNull);
    });

    test('the language already in use is a no-op', () async {
      await notifier().setLanguage(AppLanguage.en);

      expect(accounts.updatedLanguages, isEmpty);
      expect(auth.refreshed, isEmpty);
    });

    test('a failed save shows the failure and changes nothing', () async {
      accounts.onUpdate(const ApiFailure.offline());

      await notifier().setLanguage(AppLanguage.hi);

      expect(state().failure?.isOffline, isTrue);
      expect(state().busy, isFalse);
      expect((await store.read())?.user.language, AppLanguage.en);
      expect(container.read(localeProvider), const Locale('en'));
      expect(auth.refreshed, isEmpty);
    });

    test('a failed rotation after a saved switch is not the student\'s problem', () async {
      accounts.onUpdate(FakeAccountRepository.meInHindi);
      auth.onRefresh(const ApiFailure.offline());

      await notifier().setLanguage(AppLanguage.hi);

      expect(container.read(localeProvider), const Locale('hi'));
      expect(state().failure, isNull);
      expect((await store.read())?.accessToken, 'access-1');
      expect(container.read(authStateProvider).value, isA<SignedIn>());
    });

    test('Hinglish is the hi_Latn locale', () async {
      accounts.onUpdate(FakeAccountRepository.meInHinglish);
      auth.onRefresh(FakeAuthRepository.rotated);

      await notifier().setLanguage(AppLanguage.hinglish);

      expect(container.read(localeProvider), AppLanguage.hinglish.locale);
    });

    test('dismissFailure clears the line', () async {
      accounts.onUpdate(const ApiFailure.offline());
      await notifier().setLanguage(AppLanguage.hi);

      notifier().dismissFailure();

      expect(state().failure, isNull);
    });

    test('Retry after a failure that never reached the server re-runs the same switch', () async {
      accounts
        ..onUpdate(const ApiFailure.offline())
        ..onUpdate(FakeAccountRepository.meInHindi);
      auth.onRefresh(FakeAuthRepository.rotated);
      await notifier().setLanguage(AppLanguage.hi);
      expect(state().canRetry, isTrue);
      expect(state().lastLanguage, AppLanguage.hi);

      await notifier().retry();

      expect(accounts.updatedLanguages, [AppLanguage.hi, AppLanguage.hi]);
      expect(state().failure, isNull);
      expect(container.read(localeProvider), const Locale('hi'));
    });

    test('a server answer offers no Retry: the student picks again', () async {
      accounts.onUpdate(
        const ApiFailure(
          code: 'VALIDATION_FAILED',
          status: 400,
          details: {'language': 'language.invalid'},
        ),
      );

      await notifier().setLanguage(AppLanguage.hi);

      expect(state().failure?.isEnvelope, isTrue);
      expect(state().canRetry, isFalse);
    });
  });

  group('logout', () {
    test('tells the server with the stored refresh token, then clears the device', () async {
      auth.onLogout(null);

      await notifier().logout();

      expect(auth.loggedOut, ['refresh-1']);
      expect(await store.read(), isNull);
      expect(container.read(authStateProvider).value, isA<SignedOut>());
      await container.read(meProvider.future);
      expect(container.read(meProvider).value, isNull);
      expect(container.read(loginProvider).step, LoginStep.entry);
      expect(container.read(localeProvider), const Locale('en'));
      expect(state().busy, isFalse);
    });

    test('offline, the device is cleared all the same (SPEC §1 principle 5)', () async {
      auth.onLogout(const ApiFailure.offline());

      await notifier().logout();

      expect(auth.loggedOut, ['refresh-1']);
      expect(await store.read(), isNull);
      expect(container.read(authStateProvider).value, isA<SignedOut>());
      expect(state().failure, isNull);
    });

    test('a session the server already ended is cleared without fuss', () async {
      auth.onLogout(const ApiFailure.authInvalid());

      await notifier().logout();

      expect(await store.read(), isNull);
      expect(container.read(authStateProvider).value, isA<SignedOut>());
    });
  });
}
