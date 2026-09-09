import 'dart:ui';

import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/l10n/language_mapper.dart';

void main() {
  group('AppLanguage.fromWire', () {
    test('maps the three server values (TECH_PLAN §3.8, §5.5)', () {
      expect(AppLanguage.fromWire('en'), AppLanguage.en);
      expect(AppLanguage.fromWire('hi'), AppLanguage.hi);
      expect(AppLanguage.fromWire('hinglish'), AppLanguage.hinglish);
    });

    test('an unknown or missing value is English, the server default', () {
      expect(AppLanguage.fromWire('ta'), AppLanguage.en);
      expect(AppLanguage.fromWire(''), AppLanguage.en);
      expect(AppLanguage.fromWire(null), AppLanguage.en);
    });
  });

  group('AppLanguage.fromLocale', () {
    test('Hindi in Devanagari is hi, Hindi in Latin script is hinglish', () {
      expect(AppLanguage.fromLocale(const Locale('hi')), AppLanguage.hi);
      expect(AppLanguage.fromLocale(const Locale('hi', 'IN')), AppLanguage.hi);
      expect(
        AppLanguage.fromLocale(
          const Locale.fromSubtags(languageCode: 'hi', scriptCode: 'Latn'),
        ),
        AppLanguage.hinglish,
      );
    });

    test('English with any region, and any other language, is en', () {
      expect(AppLanguage.fromLocale(const Locale('en')), AppLanguage.en);
      expect(AppLanguage.fromLocale(const Locale('en', 'IN')), AppLanguage.en);
      expect(AppLanguage.fromLocale(const Locale('ta', 'IN')), AppLanguage.en);
      expect(AppLanguage.fromLocale(const Locale('mr')), AppLanguage.en);
    });
  });

  group('round trips', () {
    test('every language maps to its locale and back', () {
      for (final language in AppLanguage.values) {
        expect(AppLanguage.fromLocale(language.locale), language);
        expect(AppLanguage.fromWire(language.wireValue), language);
      }
    });

    test('Accept-Language values are what RequestLanguage on the server reads', () {
      expect(AppLanguage.en.acceptLanguage, 'en');
      expect(AppLanguage.hi.acceptLanguage, 'hi');
      expect(AppLanguage.hinglish.acceptLanguage, 'hi-Latn');
    });

    test('the Hinglish locale carries the Latn script subtag', () {
      expect(AppLanguage.hinglish.locale.languageCode, 'hi');
      expect(AppLanguage.hinglish.locale.scriptCode, 'Latn');
      expect(AppLanguage.hinglish.locale.toLanguageTag(), 'hi-Latn');
    });
  });
}
