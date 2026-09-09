import 'dart:async';

import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/session_refresher.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/features/auth/models.dart';

import '../../support/fake_auth_repository.dart';

/// TECH_PLAN §5.4 "single-flight refresh" and §3.2 rotation on the device (PLAN D10): one refresh
/// in flight at a time, the rotated pair stored beside the unchanged user, and a dead family
/// (`AUTH_INVALID`) ending the session — while an offline refresh leaves it untouched.
void main() {
  late InMemoryTokenStore store;
  late List<String> refreshed;
  late int sessionLost;
  late Completer<TokensResult> gate;
  late bool gated;

  SessionRefresher refresher() => SessionRefresher(
    store: store,
    refresh: (token) {
      refreshed.add(token);
      if (gated) {
        return gate.future;
      }
      return Future.value(FakeAuthRepository.rotated);
    },
    onSessionLost: () async => sessionLost++,
  );

  setUp(() async {
    store = InMemoryTokenStore();
    await store.write(FakeAuthRepository.signedIn.toSession());
    refreshed = <String>[];
    sessionLost = 0;
    gate = Completer<TokensResult>();
    gated = false;
  });

  test('rotates the stored pair, keeps the user and hands back the new access token', () async {
    final access = await refresher().refresh();

    expect(access, 'access-2');
    expect(refreshed, ['refresh-1']);
    final session = await store.read();
    expect(session?.accessToken, 'access-2');
    expect(session?.refreshToken, 'refresh-2');
    expect(session?.user, FakeAuthRepository.user);
    expect(sessionLost, 0);
  });

  test('callers that arrive while one refresh is in flight share it', () async {
    gated = true;
    final subject = refresher();

    final first = subject.refresh();
    final second = subject.refresh();
    final third = subject.refresh();
    await Future<void>.delayed(Duration.zero);
    expect(refreshed, hasLength(1));
    gate.complete(FakeAuthRepository.rotated);

    expect(await Future.wait([first, second, third]), everyElement('access-2'));
    expect(refreshed, hasLength(1));
  });

  test('once a refresh has finished the next one is a new call', () async {
    final subject = refresher();

    await subject.refresh();
    await subject.refresh();

    expect(refreshed, ['refresh-1', 'refresh-2']);
  });

  test('AUTH_INVALID ends the session: the store is cleared and the failure propagates', () async {
    final subject = SessionRefresher(
      store: store,
      refresh: (_) async => throw const ApiFailure(code: 'AUTH_INVALID', status: 401),
      onSessionLost: () async {
        sessionLost++;
        await store.clear();
      },
    );

    await expectLater(
      subject.refresh(),
      throwsA(isA<ApiFailure>().having((f) => f.code, 'code', 'AUTH_INVALID')),
    );

    expect(sessionLost, 1);
    expect(await store.read(), isNull);
  });

  test('an offline refresh keeps the session for the next attempt', () async {
    final subject = SessionRefresher(
      store: store,
      refresh: (_) async => throw const ApiFailure.offline(),
      onSessionLost: () async => sessionLost++,
    );

    await expectLater(
      subject.refresh(),
      throwsA(isA<ApiFailure>().having((f) => f.isOffline, 'isOffline', isTrue)),
    );

    expect(sessionLost, 0);
    expect((await store.read())?.refreshToken, 'refresh-1');
  });

  test('with nothing stored there is nothing to refresh: the session is lost', () async {
    await store.clear();

    await expectLater(
      refresher().refresh(),
      throwsA(isA<ApiFailure>().having((f) => f.code, 'code', 'AUTH_INVALID')),
    );

    expect(refreshed, isEmpty);
    expect(sessionLost, 1);
  });
}
