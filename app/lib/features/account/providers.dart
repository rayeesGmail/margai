import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../core/api/api_failure.dart';
import '../../core/auth/auth_state.dart';
import '../../core/auth/session_refresher.dart';
import '../../core/auth/token_store.dart';
import '../../core/l10n/language_mapper.dart';
import '../auth/repository.dart';
import 'models.dart';
import 'repository.dart';

/// The `/me` payload (TECH_PLAN §5.2 `meProvider`, §3.7 "one call on app start"; D10): fetched
/// once per sign-in, `null` while signed out. A failed fetch is an error state the screens show
/// honestly beside what the stored session already knows; [reload] tries again.
class MeNotifier extends AsyncNotifier<Me?> {
  @override
  Future<Me?> build() async {
    final signedIn = ref.watch(
      authStateProvider.select((auth) => auth.value is SignedIn),
    );
    if (!signedIn) {
      return null;
    }
    return ref.read(accountRepositoryProvider).getMe();
  }

  Future<void> reload() async {
    state = const AsyncLoading();
    state = await AsyncValue.guard(build);
  }

  /// A payload the caller already holds (the answer of `PATCH /me`): no second call.
  void replace(Me me) {
    state = AsyncData(me);
  }
}

/// No automatic retries (Riverpod 3 would otherwise re-run a failed build on a backoff for
/// minutes): a failed `/me` is shown honestly with one Retry, the D8 discipline (DECISIONS D10).
final meProvider = AsyncNotifierProvider<MeNotifier, Me?>(
  MeNotifier.new,
  retry: (retryCount, error) => null,
);

/// What the Profile screen renders around the stored session: a switch or a logout in flight,
/// and the last failure worth showing.
class SettingsState {
  const SettingsState({this.busy = false, this.failure});

  final bool busy;
  final ApiFailure? failure;

  SettingsState copyWith({bool? busy, Object? failure = _keep}) => SettingsState(
    busy: busy ?? this.busy,
    failure: identical(failure, _keep) ? this.failure : failure as ApiFailure?,
  );

  static const Object _keep = Object();
}

/// SPEC §8 screen 13 as `account · SettingsNotifier` (TECH_PLAN §5.8, D10): the language switch
/// of SPEC §6.11 and logout. No logic lives in the screen (`.claude/rules/app.md`).
class SettingsNotifier extends Notifier<SettingsState> {
  @override
  SettingsState build() => const SettingsState();

  /// Saves the language on the server (`PATCH /me`), then locally — the stored user, so it wins
  /// on the next start (§5.5) — and the locale follows; then the tokens are rotated so the JWT's
  /// `lang` agrees at once (§3.8 "on next refresh", made now; DECISIONS D10). A failed rotation
  /// is not the student's problem: the switch already happened, the next expiry refreshes anyway.
  Future<void> setLanguage(AppLanguage language) async {
    final auth = ref.read(authStateProvider).value;
    if (state.busy || auth is! SignedIn || auth.user.language == language) {
      return;
    }
    state = state.copyWith(busy: true, failure: null);
    try {
      final me = await ref.read(accountRepositoryProvider).update(language: language);
      await ref.read(authStateProvider.notifier).updateUser(me.user);
      ref.read(meProvider.notifier).replace(me);
    } on ApiFailure catch (failure) {
      state = state.copyWith(busy: false, failure: failure);
      return;
    }
    await _rotateTokens();
    state = state.copyWith(busy: false);
  }

  Future<void> _rotateTokens() async {
    try {
      await ref.read(sessionRefresherProvider).refresh();
    } on ApiFailure {
      // AUTH_INVALID has already signed the app out; anything else waits for the next expiry.
    }
  }

  /// Best-effort on the server, unconditional on the device (DECISIONS D10; SPEC §1 principle 5):
  /// `POST /auth/logout` with the stored refresh token, and whatever it answers — 204, offline,
  /// a family the server already ended — the store is cleared and the app is signed out. The
  /// D8 wiring then resets the login flow and the router lands on `/login`.
  Future<void> logout() async {
    if (state.busy) {
      return;
    }
    state = state.copyWith(busy: true, failure: null);
    final session = await ref.read(tokenStoreProvider).read();
    if (session != null) {
      try {
        await ref.read(authRepositoryProvider).logout(session.refreshToken);
      } on ApiFailure {
        // The family expires on its own within 30 days (TECH_PLAN §3.2); the device is clean now.
      }
    }
    await ref.read(authStateProvider.notifier).signOut();
    state = const SettingsState();
  }

  void dismissFailure() {
    state = state.copyWith(failure: null);
  }
}

final settingsProvider = NotifierProvider<SettingsNotifier, SettingsState>(
  SettingsNotifier.new,
);
