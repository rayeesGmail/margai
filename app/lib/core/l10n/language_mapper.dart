import 'dart:ui';

import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../auth/auth_state.dart';

/// The student's language as the server names it: the value in `/me`, in the JWT `lang` claim
/// and in generated content (TECH_PLAN §3.8, §5.5). Hinglish is Hindi in Latin script, the
/// BCP-47 tag `hi-Latn`, which Flutter's gen-l10n serves as its own locale.
enum AppLanguage {
  en('en', Locale('en'), 'en'),
  hi('hi', Locale('hi'), 'hi'),
  hinglish(
    'hinglish',
    Locale.fromSubtags(languageCode: 'hi', scriptCode: 'Latn'),
    'hi-Latn',
  );

  const AppLanguage(this.wireValue, this.locale, this.acceptLanguage);

  /// `en | hi | hinglish` — the same string on the server, in the JWT and here.
  final String wireValue;

  /// The Flutter locale that selects the ARB file.
  final Locale locale;

  /// The `Accept-Language` value the public auth routes honour (TECH_PLAN §3.8): the server reads
  /// `hi` as Hindi, `hi-Latn` as Hinglish and anything else as English.
  final String acceptLanguage;

  /// Server value → language; an unknown value is English, the server's own default.
  static AppLanguage fromWire(String? value) {
    for (final language in values) {
      if (language.wireValue == value) {
        return language;
      }
    }
    return en;
  }

  /// Any locale → the closest supported language. Region subtags are ignored (`hi_IN` is Hindi,
  /// `en_IN` is English); only the `Latn` script turns Hindi into Hinglish.
  static AppLanguage fromLocale(Locale locale) {
    if (locale.languageCode == 'hi') {
      return locale.scriptCode == 'Latn' ? hinglish : hi;
    }
    return en;
  }
}

/// The app's current locale (TECH_PLAN §5.2 `localeProvider`, §5.5): the signed-in account's
/// language — it is saved with `PATCH /me` and locally, and wins on every later start (D10) —
/// and, signed out, the device locale as the suggestion (SPEC §5 "language auto-suggested,
/// changeable"), which the login flow sends as `Accept-Language` so a new account starts in it.
class LocaleNotifier extends Notifier<Locale> {
  @override
  Locale build() {
    final auth = ref.watch(authStateProvider).value;
    if (auth is SignedIn) {
      return auth.user.language.locale;
    }
    return AppLanguage.fromLocale(PlatformDispatcher.instance.locale).locale;
  }
}

final localeProvider = NotifierProvider<LocaleNotifier, Locale>(
  LocaleNotifier.new,
);

/// The language behind [localeProvider], for headers and wire values.
final appLanguageProvider = Provider<AppLanguage>(
  (ref) => AppLanguage.fromLocale(ref.watch(localeProvider)),
);
