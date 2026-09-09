import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'token_store.dart';
import 'user_summary.dart';

/// Who is signed in (TECH_PLAN §5.2 `authStateProvider`, §5.3 guard). Resolved once at start from
/// the [TokenStore]; the router treats the loading phase as "unknown" and shows the splash.
sealed class AuthState {
  const AuthState();
}

class SignedOut extends AuthState {
  const SignedOut();
}

class SignedIn extends AuthState {
  const SignedIn(this.user);

  final UserSummary user;
}

class AuthNotifier extends AsyncNotifier<AuthState> {
  @override
  Future<AuthState> build() async {
    final session = await ref.watch(tokenStoreProvider).read();
    return session == null ? const SignedOut() : SignedIn(session.user);
  }

  /// Persists a fresh session (both tokens and the user) and moves to [SignedIn]; the router
  /// then leaves the login screens.
  Future<void> signIn(StoredSession session) async {
    await ref.read(tokenStoreProvider).write(session);
    state = AsyncData(SignedIn(session.user));
  }

  /// Rewrites the stored user beside the tokens as they are (a language switch through
  /// `PATCH /me`, D10) and republishes [SignedIn]. A no-op while signed out.
  Future<void> updateUser(UserSummary user) async {
    final store = ref.read(tokenStoreProvider);
    final session = await store.read();
    if (session == null || state.value is! SignedIn) {
      return;
    }
    await store.write(
      StoredSession(
        accessToken: session.accessToken,
        refreshToken: session.refreshToken,
        user: user,
      ),
    );
    state = AsyncData(SignedIn(user));
  }

  /// Clears the device and moves to [SignedOut]. The Profile screen's logout calls the server
  /// first (`POST /auth/logout`, D10); a dead family on refresh (`AUTH_INVALID`) calls this alone.
  Future<void> signOut() async {
    await ref.read(tokenStoreProvider).clear();
    state = const AsyncData(SignedOut());
  }
}

final authStateProvider = AsyncNotifierProvider<AuthNotifier, AuthState>(
  AuthNotifier.new,
);
