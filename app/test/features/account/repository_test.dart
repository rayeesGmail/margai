import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_client.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/features/account/repository.dart';

import '../../support/fake_adapter.dart';

/// The two `/me` calls of TECH_PLAN §3.7 (D10) over the fake network, in the shapes the server's
/// MeControllerTest and AccountFlowTest pin.
void main() {
  late FakeAdapter adapter;
  late AccountRepository repository;

  const meJson = <String, Object?>{
    'user': {
      'id': '555ef46d-0000-4000-8000-000000000001',
      'email': 'founder@example.com',
      'language': 'hi',
      'role': 'student',
    },
    'profile': {
      'is_minor': false,
      'onboarding_step': 'intro',
      'morning_notification_time': '07:00:00',
      'current_streak': 0,
      'longest_streak': 0,
      'hours_weekday': 5.5,
      'goal': 'govt_mbbs',
    },
  };

  setUp(() {
    adapter = FakeAdapter();
    repository = AccountRepository(
      ApiClient(
        baseUrl: 'http://10.0.2.2:8081',
        appVersion: 'margai/0.1.0+1 android',
        acceptLanguage: () => 'en',
        bearer: () async => 'access-1',
        adapter: adapter,
      ),
    );
  });

  test('getMe reads /me and parses the user and the profile', () async {
    adapter.reply(FakeReply.json(200, meJson));

    final me = await repository.getMe();

    expect(adapter.last.options.method, 'GET');
    expect(adapter.last.options.uri.path, '/api/v1/me');
    expect(adapter.last.header('Authorization'), 'Bearer access-1');
    expect(me.user.email, 'founder@example.com');
    expect(me.user.language, AppLanguage.hi);
    expect(me.profile.onboardingStep, 'intro');
    expect(me.profile.isMinor, isFalse);
    expect(me.profile.currentStreak, 0);
    expect(me.profile.hoursWeekday, 5.5);
    expect(me.profile.goal, 'govt_mbbs');
    expect(me.profile.attemptType, isNull);
  });

  test('update patches only the present fields and parses the answer', () async {
    adapter.reply(FakeReply.json(200, meJson));

    final me = await repository.update(language: AppLanguage.hi);

    expect(adapter.last.options.method, 'PATCH');
    expect(adapter.last.options.uri.path, '/api/v1/me');
    expect(adapter.last.json, {'language': 'hi'});
    expect(me.user.language, AppLanguage.hi);
  });

  test('a bad value comes back as VALIDATION_FAILED with the reason on its field', () async {
    adapter.reply(
      FakeReply.envelope(
        400,
        'VALIDATION_FAILED',
        details: {'language': 'language.invalid'},
      ),
    );

    await expectLater(
      repository.update(language: AppLanguage.hi),
      throwsA(
        isA<ApiFailure>().having(
          (f) => f.reasonFor('language'),
          'reason',
          'language.invalid',
        ),
      ),
    );
  });

  test('a 200 without the documented shape is malformed, not a crash', () async {
    adapter.reply(FakeReply.json(200, {'user': 'nope'}));

    await expectLater(
      repository.getMe(),
      throwsA(isA<ApiFailure>().having((f) => f.isMalformed, 'isMalformed', isTrue)),
    );
  });

  test('unknown profile keys are ignored (TECH_PLAN §3.1: fields are added on later days)', () async {
    adapter.reply(
      FakeReply.json(200, {
        'user': meJson['user'],
        'profile': {'onboarding_step': 'intro', 'a_field_from_d61': 42},
        'subscription': {'status': 'free'},
      }),
    );

    final me = await repository.getMe();

    expect(me.profile.onboardingStep, 'intro');
  });
}
