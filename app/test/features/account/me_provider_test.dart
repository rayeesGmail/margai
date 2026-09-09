import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/features/account/models.dart';
import 'package:margai/features/account/providers.dart';
import 'package:margai/features/account/repository.dart';
import 'package:margai/features/auth/repository.dart';

import '../../support/fake_account_repository.dart';
import '../../support/fake_auth_repository.dart';

/// TECH_PLAN §5.2 `meProvider`, §3.7 "one call on app start" (D10): `/me` is fetched once per
/// sign-in, is `null` while signed out, surfaces its failure honestly and can be reloaded.
void main() {
  late FakeAccountRepository accounts;
  late InMemoryTokenStore store;

  ProviderContainer containerOver(TokenStore store) => ProviderContainer.test(
    overrides: [
      tokenStoreProvider.overrideWithValue(store),
      accountRepositoryProvider.overrideWithValue(accounts),
      authRepositoryProvider.overrideWithValue(FakeAuthRepository()),
    ],
  );

  setUp(() {
    accounts = FakeAccountRepository();
    store = InMemoryTokenStore();
  });

  test('a stored session fetches /me once on start', () async {
    await store.write(FakeAuthRepository.signedIn.toSession());
    accounts.onGetMe(FakeAccountRepository.me);
    final container = containerOver(store);
    await container.read(authStateProvider.future);
    final keepAlive = container.listen(meProvider, (_, _) {});
    addTearDown(keepAlive.close);

    final me = await container.read(meProvider.future);

    expect(me?.user.email, 'founder@example.com');
    expect(me?.profile.onboardingStep, 'intro');
    expect(accounts.gets, 1);
  });

  test('signed out there is no /me and no call', () async {
    final container = containerOver(store);

    expect(await container.read(meProvider.future), isNull);
    expect(accounts.gets, 0);
  });

  test('a failed fetch is an error state the screen can show, and reload tries again', () async {
    await store.write(FakeAuthRepository.signedIn.toSession());
    accounts
      ..onGetMe(const ApiFailure.offline())
      ..onGetMe(FakeAccountRepository.me);
    final container = containerOver(store);
    // Auth first, so the one build this test watches is the signed-in fetch.
    await container.read(authStateProvider.future);
    final keepAlive = container.listen(meProvider, (_, _) {});
    addTearDown(keepAlive.close);

    await expectLater(container.read(meProvider.future), throwsA(isA<ApiFailure>()));
    expect(container.read(meProvider).hasError, isTrue);
    expect(container.read(meProvider).error, isA<ApiFailure>());

    await container.read(meProvider.notifier).reload();

    expect(container.read(meProvider).value?.user.email, 'founder@example.com');
    expect(accounts.gets, 2);
  });

  test('signing in fetches, signing out drops it', () async {
    accounts.onGetMe(FakeAccountRepository.me);
    final container = containerOver(store);
    final keepAlive = container.listen(meProvider, (_, _) {});
    addTearDown(keepAlive.close);
    expect(await container.read(meProvider.future), isNull);

    await container.read(authStateProvider.notifier).signIn(FakeAuthRepository.signedIn.toSession());
    await container.read(meProvider.future);
    expect(container.read(meProvider).value, isA<Me>());
    expect(accounts.gets, 1);

    await container.read(authStateProvider.notifier).signOut();
    await container.read(meProvider.future);
    expect(container.read(meProvider).value, isNull);
    expect(accounts.gets, 1);
  });

  test('replace publishes a payload the caller already holds without another call', () async {
    await store.write(FakeAuthRepository.signedIn.toSession());
    accounts.onGetMe(FakeAccountRepository.me);
    final container = containerOver(store);
    final keepAlive = container.listen(meProvider, (_, _) {});
    addTearDown(keepAlive.close);
    await container.read(meProvider.future);

    container.read(meProvider.notifier).replace(FakeAccountRepository.meInHindi);

    expect(container.read(meProvider).value?.user, FakeAccountRepository.hindiUser);
    expect(accounts.gets, 1);
  });
}
