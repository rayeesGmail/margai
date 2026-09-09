import 'dart:ui';

import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/auth/user_summary.dart';
import 'package:margai/core/l10n/language_mapper.dart';

/// TECH_PLAN §5.5: the device locale is only a suggestion; the account's language "wins on every
/// later start" (D10). Signed out, the suggestion is back.
void main() {
  const hindi = UserSummary(
    id: 'u-1',
    email: 'a@b.in',
    language: AppLanguage.hi,
    role: 'student',
  );
  const hinglish = UserSummary(
    id: 'u-1',
    email: 'a@b.in',
    language: AppLanguage.hinglish,
    role: 'student',
  );

  ProviderContainer containerOver(TokenStore store) => ProviderContainer.test(
    overrides: [tokenStoreProvider.overrideWithValue(store)],
  );

  test('a stored session boots into the account\'s language', () async {
    final store = InMemoryTokenStore();
    await store.write(
      const StoredSession(accessToken: 'a', refreshToken: 'r', user: hindi),
    );
    final container = containerOver(store);
    final keepAlive = container.listen(localeProvider, (_, _) {});
    addTearDown(keepAlive.close);

    await container.read(authStateProvider.future);

    expect(container.read(localeProvider), const Locale('hi'));
  });

  test('signed out, the locale is the device suggestion; a sign-in changes it', () async {
    final store = InMemoryTokenStore();
    final container = containerOver(store);
    final keepAlive = container.listen(localeProvider, (_, _) {});
    addTearDown(keepAlive.close);
    await container.read(authStateProvider.future);
    final suggestion = AppLanguage.fromLocale(PlatformDispatcher.instance.locale).locale;
    expect(container.read(localeProvider), suggestion);

    await container.read(authStateProvider.notifier).signIn(
      const StoredSession(accessToken: 'a', refreshToken: 'r', user: hinglish),
    );
    expect(container.read(localeProvider), AppLanguage.hinglish.locale);

    await container.read(authStateProvider.notifier).signOut();
    expect(container.read(localeProvider), suggestion);
  });

  test('the Accept-Language the client sends follows the same choice', () async {
    final store = InMemoryTokenStore();
    await store.write(
      const StoredSession(accessToken: 'a', refreshToken: 'r', user: hinglish),
    );
    final container = containerOver(store);
    final keepAlive = container.listen(appLanguageProvider, (_, _) {});
    addTearDown(keepAlive.close);

    await container.read(authStateProvider.future);

    expect(container.read(appLanguageProvider).acceptLanguage, 'hi-Latn');
  });
}
