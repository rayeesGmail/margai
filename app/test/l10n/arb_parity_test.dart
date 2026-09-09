import 'dart:convert';
import 'dart:io';

import 'package:flutter_test/flutter_test.dart';

/// gen-l10n fills an untranslated key with the English template and only warns, so the three
/// ARB files must be compared by hand to keep TECH_PLAN §8.4's promise that a missing key fails a
/// test. The test runs from `app/`, where `flutter test` starts.
void main() {
  const template = 'lib/l10n/app_en.arb';
  const translations = <String>['lib/l10n/app_hi.arb', 'lib/l10n/app_hi_Latn.arb'];

  Map<String, Object?> load(String path) =>
      jsonDecode(File(path).readAsStringSync()) as Map<String, Object?>;

  Set<String> messageKeys(Map<String, Object?> arb) =>
      arb.keys.where((key) => !key.startsWith('@')).toSet();

  test('every locale carries exactly the template\'s keys, none empty', () {
    final english = load(template);
    final expected = messageKeys(english);
    expect(expected, isNotEmpty);

    for (final path in translations) {
      final arb = load(path);
      final keys = messageKeys(arb);
      expect(
        keys,
        expected,
        reason: '$path: missing ${expected.difference(keys)}, extra ${keys.difference(expected)}',
      );
      for (final key in keys) {
        expect((arb[key] as String).trim(), isNotEmpty, reason: '$path: $key');
      }
    }
  });

  test('placeholders and plural cases survive translation', () {
    final english = load(template);
    final placeholder = RegExp(r'\{(\w+)(?:,|\})');
    for (final path in translations) {
      final arb = load(path);
      for (final key in messageKeys(english)) {
        final source = placeholder
            .allMatches(english[key]! as String)
            .map((m) => m.group(1))
            .toSet();
        final target = placeholder
            .allMatches(arb[key]! as String)
            .map((m) => m.group(1))
            .toSet();
        expect(target, source, reason: '$path: $key placeholders');
      }
      for (final key in ['attemptsLeft']) {
        for (final form in ['=0{', '=1{', 'other{']) {
          expect(arb[key], contains(form), reason: '$path: $key $form');
        }
      }
    }
  });

  test('the locales declared in the files are the three of TECH_PLAN §5.5', () {
    expect(load(template)['@@locale'], 'en');
    expect(load(translations[0])['@@locale'], 'hi');
    expect(load(translations[1])['@@locale'], 'hi_Latn');
  });
}
