import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/clock.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/core/ticker.dart';
import 'package:margai/features/auth/providers.dart';
import 'package:margai/features/auth/repository.dart';
import 'package:margai/l10n/app_localizations.dart';

import 'fake_auth_repository.dart';

/// The three locales every widget test runs in (TECH_PLAN §8.4). Key parity between the ARB
/// files is enforced by test/l10n/arb_parity_test.dart (gen-l10n would otherwise fill a missing
/// translation with the English template silently); these loops prove each screen state renders
/// the locale's own copy.
final List<Locale> allLocales = AppLanguage.values
    .map((language) => language.locale)
    .toList();

/// The generated copy for [locale], to assert on what the screen must show.
AppLocalizations copyFor(Locale locale) => lookupAppLocalizations(locale);

/// A [LoginNotifier] that starts in a chosen state, so a screen can be rendered per state.
class SeededLoginNotifier extends LoginNotifier {
  SeededLoginNotifier(this.initial);

  final LoginState initial;

  @override
  LoginState initialState() => initial;
}

/// Pumps [screen] inside a localised MaterialApp with the auth providers faked: the login state
/// seeded, the repository scripted, an in-memory token store, and a frozen clock and ticker.
Future<void> pumpScreen(
  WidgetTester tester,
  Widget screen, {
  required Locale locale,
  LoginState state = const LoginState(),
  FakeAuthRepository? repository,
  TokenStore? store,
  DateTime? now,
}) async {
  final fixedNow = now ?? DateTime.utc(2026, 9, 8, 12);
  await tester.pumpWidget(
    ProviderScope(
      overrides: [
        loginProvider.overrideWith(() => SeededLoginNotifier(state)),
        authRepositoryProvider.overrideWithValue(
          repository ?? FakeAuthRepository(),
        ),
        tokenStoreProvider.overrideWithValue(store ?? InMemoryTokenStore()),
        clockProvider.overrideWithValue(() => fixedNow),
        tickerProvider.overrideWith((ref) => Stream.value(fixedNow)),
      ],
      child: MaterialApp(
        locale: locale,
        localizationsDelegates: AppLocalizations.localizationsDelegates,
        supportedLocales: AppLocalizations.supportedLocales,
        home: screen,
      ),
    ),
  );
  // A busy screen shows an indeterminate progress bar, which never settles.
  if (state.busy) {
    await tester.pump();
  } else {
    await tester.pumpAndSettle();
  }
}

/// The provider container behind a pumped screen, to read state after an interaction.
ProviderContainer containerOf(WidgetTester tester, Type screen) =>
    ProviderScope.containerOf(tester.element(find.byType(screen)));
