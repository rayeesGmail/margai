import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../features/auth/models.dart';
import '../../features/auth/repository.dart';
import '../api/api_failure.dart';
import 'auth_state.dart';
import 'token_store.dart';

/// Rotates the device's token family (TECH_PLAN §3.2, §5.4 "single-flight refresh"; PLAN D10
/// "token rotation"). One refresh runs at a time: every caller that finds one in flight awaits
/// the same future, so a burst of expired calls costs one `POST /auth/refresh`. On success the
/// rotated pair replaces the stored one beside the unchanged user. `AUTH_INVALID` — the family
/// was revoked, or the account is gone — ends the session through [onSessionLost] and the
/// failure propagates so the caller sees why; anything else (offline, a server fault) propagates
/// and leaves the session for the next attempt.
class SessionRefresher {
  SessionRefresher({
    required this._store,
    required this._refresh,
    required this._onSessionLost,
  });

  final TokenStore _store;
  final Future<TokensResult> Function(String refreshToken) _refresh;
  final Future<void> Function() _onSessionLost;

  Future<String>? _inFlight;

  /// The new access token; throws the [ApiFailure] that ended the attempt.
  Future<String> refresh() {
    final running = _inFlight;
    if (running != null) {
      return running;
    }
    final attempt = _rotate().whenComplete(() => _inFlight = null);
    _inFlight = attempt;
    return attempt;
  }

  Future<String> _rotate() async {
    final session = await _store.read();
    if (session == null) {
      await _onSessionLost();
      throw const ApiFailure.authInvalid();
    }
    final TokensResult tokens;
    try {
      tokens = await _refresh(session.refreshToken);
    } on ApiFailure catch (failure) {
      if (failure.isAuthInvalid) {
        await _onSessionLost();
      }
      rethrow;
    }
    await _store.write(
      StoredSession(
        accessToken: tokens.accessToken,
        refreshToken: tokens.refreshToken,
        user: session.user,
      ),
    );
    return tokens.accessToken;
  }
}

/// The app's refresher (TECH_PLAN §5.2 global providers): the store, the auth repository's
/// refresh call and the sign-out. Read lazily from `apiClientProvider`'s handler, so the client
/// and the repository that uses it never form a cycle at build time.
final Provider<SessionRefresher> sessionRefresherProvider = Provider<SessionRefresher>((ref) {
  return SessionRefresher(
    store: ref.watch(tokenStoreProvider),
    refresh: (token) => ref.read(authRepositoryProvider).refresh(token),
    onSessionLost: () => ref.read(authStateProvider.notifier).signOut(),
  );
});
