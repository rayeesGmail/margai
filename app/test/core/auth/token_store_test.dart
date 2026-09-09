import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/core/auth/user_summary.dart';
import 'package:margai/core/l10n/language_mapper.dart';

void main() {
  const user = UserSummary(
    id: 'u-1',
    email: 'a@b.in',
    language: AppLanguage.hi,
    role: 'student',
  );
  const session = StoredSession(
    accessToken: 'access-1',
    refreshToken: 'refresh-1',
    user: user,
  );

  group('StoredSession', () {
    test('serialises both tokens and the user in one blob and parses it back', () {
      final parsed = StoredSession.tryParse(session.serialise());
      expect(parsed, isNotNull);
      expect(parsed!.accessToken, 'access-1');
      expect(parsed.refreshToken, 'refresh-1');
      expect(parsed.user, user);
    });

    test('garbage, a partial blob or nothing parses to null, never throws', () {
      expect(StoredSession.tryParse(null), isNull);
      expect(StoredSession.tryParse(''), isNull);
      expect(StoredSession.tryParse('not json'), isNull);
      expect(StoredSession.tryParse('[1]'), isNull);
      expect(StoredSession.tryParse('{"access":"a"}'), isNull);
      expect(
        StoredSession.tryParse('{"access":"a","refresh":"r","user":{}}'),
        isNull,
      );
    });
  });

  group('InMemoryTokenStore', () {
    test('reads what was written and nothing after clear', () async {
      final store = InMemoryTokenStore();
      expect(await store.read(), isNull);

      await store.write(session);
      expect((await store.read())?.refreshToken, 'refresh-1');
      expect(store.writes, 1);

      await store.clear();
      expect(await store.read(), isNull);
    });
  });
}
