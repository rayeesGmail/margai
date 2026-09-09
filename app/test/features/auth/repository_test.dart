import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_client.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/l10n/language_mapper.dart';
import 'package:margai/features/auth/repository.dart';

import '../../support/fake_adapter.dart';

void main() {
  late FakeAdapter adapter;
  late AuthRepository repository;

  setUp(() {
    adapter = FakeAdapter();
    repository = AuthRepository(
      ApiClient(
        baseUrl: 'http://10.0.2.2:8081',
        appVersion: 'margai/0.1.0+1 android',
        acceptLanguage: () => 'en',
        bearer: () async => null,
        adapter: adapter,
      ),
    );
  });

  group('requestOtp (TECH_PLAN §3.7, the D7 AuthFlowTest shapes)', () {
    test('posts {email} and parses the challenge', () async {
      adapter.reply(
        FakeReply.json(200, {
          'challenge_id': '50b686be-0000-4000-8000-000000000001',
          'resend_after_s': 30,
          'channel': 'email',
        }),
      );

      final challenge = await repository.requestOtp(
        email: 'founder@example.com',
      );

      expect(adapter.last.options.uri.path, '/api/v1/auth/otp/request');
      expect(adapter.last.json, {'email': 'founder@example.com'});
      expect(challenge.challengeId, '50b686be-0000-4000-8000-000000000001');
      expect(challenge.resendAfter, const Duration(seconds: 30));
      expect(challenge.channel, 'email');
    });

    test('a 429 OTP_RATE_LIMITED reaches the caller as an ApiFailure', () async {
      adapter.reply(
        FakeReply.envelope(
          429,
          'OTP_RATE_LIMITED',
          details: {'retry_after_s': 3507},
          headers: {'Retry-After': '3507'},
        ),
      );

      await expectLater(
        repository.requestOtp(email: 'founder@example.com'),
        throwsA(
          isA<ApiFailure>()
              .having((f) => f.code, 'code', 'OTP_RATE_LIMITED')
              .having(
                (f) => f.retryAfter,
                'retryAfter',
                const Duration(seconds: 3507),
              ),
        ),
      );
    });
  });

  group('verifyOtp', () {
    test('posts {challenge_id, code} and parses tokens, user and is_new_user', () async {
      adapter.reply(
        FakeReply.json(200, {
          'expires_in': 900,
          'is_new_user': true,
          'user': {
            'id': '555ef46d-0000-4000-8000-000000000001',
            'email': 'founder@example.com',
            'language': 'en',
            'role': 'student',
          },
          'access_token': 'access-1',
          'refresh_token': 'refresh-1',
        }),
      );

      final result = await repository.verifyOtp(
        challengeId: '50b686be-0000-4000-8000-000000000001',
        code: '444771',
      );

      expect(adapter.last.options.uri.path, '/api/v1/auth/otp/verify');
      expect(adapter.last.json, {
        'challenge_id': '50b686be-0000-4000-8000-000000000001',
        'code': '444771',
      });
      expect(result.accessToken, 'access-1');
      expect(result.refreshToken, 'refresh-1');
      expect(result.expiresIn, const Duration(minutes: 15));
      expect(result.isNewUser, isTrue);
      expect(result.user.email, 'founder@example.com');
      expect(result.user.language, AppLanguage.en);
      expect(result.toSession().user.identifier, 'founder@example.com');
    });

    test('a wrong code is OTP_INVALID with attempts_left', () async {
      adapter.reply(
        FakeReply.envelope(
          401,
          'OTP_INVALID',
          details: {'attempts_left': 4},
        ),
      );

      await expectLater(
        repository.verifyOtp(challengeId: 'c-1', code: '000000'),
        throwsA(
          isA<ApiFailure>()
              .having((f) => f.code, 'code', 'OTP_INVALID')
              .having((f) => f.attemptsLeft, 'attemptsLeft', 4),
        ),
      );
    });

    test('a 200 without the documented fields is malformed, not a crash', () async {
      adapter.reply(FakeReply.json(200, {'access_token': 'access-1'}));

      await expectLater(
        repository.verifyOtp(challengeId: 'c-1', code: '444771'),
        throwsA(
          isA<ApiFailure>().having((f) => f.isMalformed, 'isMalformed', isTrue),
        ),
      );
    });
  });

  group('refresh and logout (TECH_PLAN §3.2, §3.7; D10)', () {
    test('refresh posts {refresh_token} and parses the rotated pair', () async {
      adapter.reply(
        FakeReply.json(200, {
          'access_token': 'access-2',
          'refresh_token': 'refresh-2',
          'expires_in': 900,
        }),
      );

      final tokens = await repository.refresh('refresh-1');

      expect(adapter.last.options.uri.path, '/api/v1/auth/refresh');
      expect(adapter.last.json, {'refresh_token': 'refresh-1'});
      expect(tokens.accessToken, 'access-2');
      expect(tokens.refreshToken, 'refresh-2');
      expect(tokens.expiresIn, const Duration(minutes: 15));
    });

    test('a spent refresh token is AUTH_INVALID', () async {
      adapter.reply(FakeReply.envelope(401, 'AUTH_INVALID'));

      await expectLater(
        repository.refresh('refresh-1'),
        throwsA(isA<ApiFailure>().having((f) => f.code, 'code', 'AUTH_INVALID')),
      );
    });

    test('logout posts {refresh_token} and completes on the empty 204', () async {
      adapter.reply(const FakeReply(204, '', contentType: 'text/plain'));

      await repository.logout('refresh-1');

      expect(adapter.last.options.uri.path, '/api/v1/auth/logout');
      expect(adapter.last.json, {'refresh_token': 'refresh-1'});
    });
  });
}
