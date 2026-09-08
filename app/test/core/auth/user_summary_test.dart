import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/user_summary.dart';
import 'package:margai/core/l10n/language_mapper.dart';

void main() {
  test('parses the verify response user object (TECH_PLAN §3.7, snake_case)', () {
    final user = UserSummary.fromJson({
      'id': '555ef46d-0000-4000-8000-000000000001',
      'email': 'founder@example.com',
      'language': 'en',
      'role': 'student',
    });

    expect(user.id, '555ef46d-0000-4000-8000-000000000001');
    expect(user.email, 'founder@example.com');
    expect(user.phone, isNull);
    expect(user.language, AppLanguage.en);
    expect(user.role, 'student');
    expect(user.displayName, isNull);
    expect(user.identifier, 'founder@example.com');
  });

  test('a phone login and a display name come through; hinglish maps', () {
    final user = UserSummary.fromJson({
      'id': 'u-2',
      'phone': '+919876543210',
      'language': 'hinglish',
      'role': 'student',
      'display_name': 'Asha',
    });
    expect(user.phone, '+919876543210');
    expect(user.identifier, '+919876543210');
    expect(user.language, AppLanguage.hinglish);
    expect(user.displayName, 'Asha');
  });

  test('round-trips through JSON without inventing null fields', () {
    final user = UserSummary.fromJson({
      'id': 'u-3',
      'email': 'a@b.in',
      'language': 'hi',
      'role': 'student',
    });
    final json = user.toJson();
    expect(json.containsKey('phone'), isFalse);
    expect(json.containsKey('display_name'), isFalse);
    expect(UserSummary.fromJson(json), user);
  });
}
