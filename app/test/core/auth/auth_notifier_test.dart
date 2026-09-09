import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/auth/user_summary.dart';
import 'package:margai/core/l10n/language_mapper.dart';

void main() {
  const user = UserSummary(
    id: 'u-1',
    email: 'a@b.in',
    language: AppLanguage.en,
    role: 'student',
  );
  const session = StoredSession(
    accessToken: 'access-1',
    refreshToken: 'refresh-1',
    user: user,
  );

  ProviderContainer containerOver(TokenStore store) => ProviderContainer.test(
    overrides: [tokenStoreProvider.overrideWithValue(store)],
  );

  test('a cold start with an empty store is signed out', () async {
    final container = containerOver(InMemoryTokenStore());
    expect(container.read(authStateProvider), isA<AsyncLoading<AuthState>>());
    expect(await container.read(authStateProvider.future), isA<SignedOut>());
  });

  test('signIn persists the session and moves to SignedIn', () async {
    final store = InMemoryTokenStore();
    final container = containerOver(store);
    await container.read(authStateProvider.future);

    await container.read(authStateProvider.notifier).signIn(session);

    final state = container.read(authStateProvider).value;
    expect(state, isA<SignedIn>());
    expect((state! as SignedIn).user, user);
    expect((await store.read())?.accessToken, 'access-1');
  });

  test('a new start over the same store resolves SignedIn (PLAN D10 groundwork)', () async {
    final store = InMemoryTokenStore();
    await store.write(session);

    final state = await containerOver(store).read(authStateProvider.future);

    expect(state, isA<SignedIn>());
    expect((state as SignedIn).user.identifier, 'a@b.in');
  });

  test('signOut clears the store and moves to SignedOut', () async {
    final store = InMemoryTokenStore();
    await store.write(session);
    final container = containerOver(store);
    await container.read(authStateProvider.future);

    await container.read(authStateProvider.notifier).signOut();

    expect(container.read(authStateProvider).value, isA<SignedOut>());
    expect(await store.read(), isNull);
  });

  test('a corrupt blob on the device is signed out, not a crash', () async {
    final state = await containerOver(
      InMemoryTokenStore('{"access":1}'),
    ).read(authStateProvider.future);
    expect(state, isA<SignedOut>());
  });
}
