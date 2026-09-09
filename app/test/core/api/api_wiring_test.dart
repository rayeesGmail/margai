import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/api/api_providers.dart';
import 'package:margai/core/auth/auth_state.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/config/app_version.dart';

import '../../support/fake_adapter.dart';
import '../../support/fake_auth_repository.dart';

/// The real providers wired together over a fake network (TECH_PLAN §5.4, PLAN D10 "token
/// rotation"): an expired access token on `/me` is replaced through `/auth/refresh` with the
/// stored refresh token, the rotated pair lands in the store, and a dead family signs the app out.
void main() {
  late FakeAdapter adapter;
  late InMemoryTokenStore store;
  late ProviderContainer container;

  setUp(() async {
    adapter = FakeAdapter();
    store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    container = ProviderContainer.test(
      overrides: [
        tokenStoreProvider.overrideWithValue(store),
        apiAdapterProvider.overrideWithValue(adapter),
        appVersionProvider.overrideWithValue('margai/0.1.0+1 android'),
      ],
    );
    await container.read(authStateProvider.future);
  });

  test('an expired token on /me is refreshed with the stored refresh token and the call retried', () async {
    adapter
      ..reply(FakeReply.envelope(401, 'AUTH_EXPIRED'))
      ..reply(
        FakeReply.json(200, {
          'access_token': 'access-2',
          'refresh_token': 'refresh-2',
          'expires_in': 900,
        }),
      )
      ..reply(FakeReply.json(200, {'user': {'id': 'u-1'}}));

    final body = await container.read(apiClientProvider).get('/me');

    expect(body['user'], {'id': 'u-1'});
    expect(adapter.requests.map((r) => r.options.uri.path), [
      '/api/v1/me',
      '/api/v1/auth/refresh',
      '/api/v1/me',
    ]);
    expect(adapter.requests[0].header('Authorization'), 'Bearer access-1');
    expect(adapter.requests[1].header('Authorization'), isNull);
    expect(adapter.requests[1].json, {'refresh_token': 'refresh-1'});
    expect(adapter.requests[2].header('Authorization'), 'Bearer access-2');
    final session = await store.read();
    expect(session?.accessToken, 'access-2');
    expect(session?.refreshToken, 'refresh-2');
    expect(session?.user, FakeAuthRepository.user);
    expect(container.read(authStateProvider).value, isA<SignedIn>());
  });

  test('an access token from a previous server key is replaced and the app stays signed in', () async {
    // A local restart mints a new ephemeral JWT secret (DECISIONS D7); the refresh token row survives.
    adapter
      ..reply(FakeReply.envelope(401, 'AUTH_INVALID'))
      ..reply(
        FakeReply.json(200, {
          'access_token': 'access-2',
          'refresh_token': 'refresh-2',
          'expires_in': 900,
        }),
      )
      ..reply(FakeReply.json(200, {'user': {'id': 'u-1'}}));

    final body = await container.read(apiClientProvider).get('/me');

    expect(body['user'], {'id': 'u-1'});
    expect(adapter.requests.map((r) => r.options.uri.path), [
      '/api/v1/me',
      '/api/v1/auth/refresh',
      '/api/v1/me',
    ]);
    expect((await store.read())?.accessToken, 'access-2');
    expect(container.read(authStateProvider).value, isA<SignedIn>());
  });

  test('an account the server can no longer serve signs the app out after one refresh', () async {
    adapter
      ..reply(FakeReply.envelope(401, 'AUTH_INVALID'))
      ..reply(
        FakeReply.json(200, {
          'access_token': 'access-2',
          'refresh_token': 'refresh-2',
          'expires_in': 900,
        }),
      )
      ..reply(FakeReply.envelope(401, 'AUTH_INVALID'));

    await expectLater(
      container.read(apiClientProvider).get('/me'),
      throwsA(isA<ApiFailure>().having((f) => f.code, 'code', 'AUTH_INVALID')),
    );

    expect(adapter.requests, hasLength(3));
    expect(container.read(authStateProvider).value, isA<SignedOut>());
    expect(await store.read(), isNull);
  });

  test('a revoked family on refresh signs the app out and clears the device', () async {
    adapter
      ..reply(FakeReply.envelope(401, 'AUTH_EXPIRED'))
      ..reply(FakeReply.envelope(401, 'AUTH_INVALID'));

    await expectLater(
      container.read(apiClientProvider).get('/me'),
      throwsA(isA<ApiFailure>().having((f) => f.code, 'code', 'AUTH_INVALID')),
    );

    expect(container.read(authStateProvider).value, isA<SignedOut>());
    expect(await store.read(), isNull);
  });
}
