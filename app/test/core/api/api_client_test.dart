import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_client.dart';
import 'package:margai/core/api/api_failure.dart';

import '../../support/fake_adapter.dart';

void main() {
  late FakeAdapter adapter;
  String? bearer;
  String language = 'en';

  ApiClient client({String baseUrl = 'http://10.0.2.2:8081'}) => ApiClient(
    baseUrl: baseUrl,
    appVersion: 'margai/0.1.0+1 android',
    acceptLanguage: () => language,
    bearer: () async => bearer,
    adapter: adapter,
    clock: () => DateTime.utc(2026, 9, 8, 12, 30, 45, 123),
  );

  setUp(() {
    adapter = FakeAdapter();
    bearer = null;
    language = 'en';
  });

  group('request shape (TECH_PLAN §3.1, §3.8, §5.4)', () {
    test('posts JSON under /api/v1 with every header the contract names', () async {
      adapter.reply(FakeReply.json(200, {'ok': true}));
      language = 'hi-Latn';

      await client().post('/auth/otp/request', {'email': 'a@b.in'});

      final sent = adapter.last;
      expect(sent.options.method, 'POST');
      expect(
        sent.options.uri.toString(),
        'http://10.0.2.2:8081/api/v1/auth/otp/request',
      );
      expect(sent.json, {'email': 'a@b.in'});
      expect(sent.header('content-type'), startsWith('application/json'));
      expect(sent.header('accept'), startsWith('application/json'));
      expect(sent.header('X-App-Version'), 'margai/0.1.0+1 android');
      expect(sent.header('Accept-Language'), 'hi-Latn');
      expect(sent.header('X-Client-Time'), '2026-09-08T12:30:45.123Z');
      expect(
        sent.header('X-Request-Id'),
        matches(
          RegExp(
            r'^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$',
          ),
        ),
      );
      expect(sent.header('Authorization'), isNull);
    });

    test('a trailing slash on the base URL does not double up', () async {
      adapter.reply(FakeReply.json(200, {}));
      await client(baseUrl: 'http://10.0.2.2:8081/').get('/me');
      expect(adapter.last.options.uri.path, '/api/v1/me');
    });

    test('every call carries a fresh request id', () async {
      adapter
        ..reply(FakeReply.json(200, {}))
        ..reply(FakeReply.json(200, {}));
      final api = client();
      await api.get('/me');
      await api.get('/me');
      expect(
        adapter.requests[0].header('X-Request-Id'),
        isNot(adapter.requests[1].header('X-Request-Id')),
      );
    });

    test('the bearer travels only when the supplier has one', () async {
      adapter.reply(FakeReply.json(200, {}));
      bearer = 'access-1';
      await client().get('/me');
      expect(adapter.last.header('Authorization'), 'Bearer access-1');
    });

    test('timeouts are 10 s connect and 30 s receive', () {
      expect(ApiClient.connectTimeout, const Duration(seconds: 10));
      expect(ApiClient.receiveTimeout, const Duration(seconds: 30));
    });
  });

  group('envelope mapping (TECH_PLAN §3.3)', () {
    test('a 401 envelope becomes an ApiFailure with code, copy and details', () async {
      adapter.reply(
        FakeReply.envelope(
          401,
          'OTP_INVALID',
          messageEn: "That code didn't match. Try once more.",
          messageUser: 'यह कोड मेल नहीं खाया।',
          details: {'attempts_left': 3},
        ),
      );

      final failure = await _failureOf(
        client().post('/auth/otp/verify', {'code': '000000'}),
      );

      expect(failure.code, 'OTP_INVALID');
      expect(failure.status, 401);
      expect(failure.isEnvelope, isTrue);
      expect(failure.messageEn, "That code didn't match. Try once more.");
      expect(failure.messageUser, 'यह कोड मेल नहीं खाया।');
      expect(failure.attemptsLeft, 3);
      expect(failure.retryAfter, isNull);
    });

    test('a 429 reads Retry-After from the header, else from the details', () async {
      adapter
        ..reply(
          FakeReply.envelope(
            429,
            'OTP_RATE_LIMITED',
            details: {'retry_after_s': 30},
            headers: {'Retry-After': '30'},
          ),
        )
        ..reply(
          FakeReply.envelope(
            429,
            'RATE_LIMITED',
            details: {'retry_after_s': 7},
          ),
        );
      final api = client();

      final first = await _failureOf(api.post('/auth/otp/request', {}));
      final second = await _failureOf(api.post('/auth/otp/request', {}));

      expect(first.retryAfter, const Duration(seconds: 30));
      expect(second.retryAfter, const Duration(seconds: 7));
    });

    test('a 400 VALIDATION_FAILED exposes the reason code per field', () async {
      adapter.reply(
        FakeReply.envelope(
          400,
          'VALIDATION_FAILED',
          details: {'email': 'email.invalid'},
        ),
      );

      final failure = await _failureOf(
        client().post('/auth/otp/request', {'email': 'nope'}),
      );

      expect(failure.reasonFor('email'), 'email.invalid');
      expect(failure.reasonFor('phone'), isNull);
    });

    test('a 500 INTERNAL carries the request id for support', () async {
      adapter.reply(
        FakeReply.envelope(
          500,
          'INTERNAL',
          details: {'request_id': 'req-123'},
        ),
      );
      final failure = await _failureOf(client().get('/me'));
      expect(failure.code, 'INTERNAL');
      expect(failure.requestId, 'req-123');
    });
  });

  group('honest offline state (TECH_PLAN §5.4, DEV_SPEC §6 screen 1)', () {
    test('a refused connection is offline', () async {
      adapter.fail(
        DioException.connectionError(
          requestOptions: RequestOptions(path: '/x'),
          reason: 'Connection refused',
        ),
      );
      final failure = await _failureOf(client().get('/me'));
      expect(failure.isOffline, isTrue);
      expect(failure.code, ApiFailure.offlineCode);
    });

    test('a raw socket error from the adapter is offline too', () async {
      adapter.fail(const SocketException('Network is unreachable'));
      final failure = await _failureOf(client().get('/me'));
      expect(failure.isOffline, isTrue);
    });

    test('a receive timeout is offline, not a crash', () async {
      adapter.fail(
        DioException.receiveTimeout(
          timeout: const Duration(seconds: 30),
          requestOptions: RequestOptions(path: '/x'),
        ),
      );
      final failure = await _failureOf(client().get('/me'));
      expect(failure.isOffline, isTrue);
    });
  });

  group('answers that are not the envelope', () {
    test('a proxy HTML page on 502 is malformed with the status', () async {
      adapter.reply(
        const FakeReply(502, '<html>Bad Gateway</html>', contentType: 'text/html'),
      );
      final failure = await _failureOf(client().get('/me'));
      expect(failure.isMalformed, isTrue);
      expect(failure.status, 502);
    });

    test('a 200 whose body is not a JSON object is malformed', () async {
      adapter.reply(const FakeReply(200, '[1, 2, 3]'));
      final failure = await _failureOf(client().get('/me'));
      expect(failure.isMalformed, isTrue);
      expect(failure.status, 200);
    });

    test('an error status with a JSON body that lacks error.code is malformed', () async {
      adapter.reply(FakeReply.json(503, {'message': 'nope'}));
      final failure = await _failureOf(client().get('/me'));
      expect(failure.isMalformed, isTrue);
      expect(failure.status, 503);
    });
  });

  test('a 200 JSON object comes back decoded', () async {
    adapter.reply(
      FakeReply.json(200, {
        'challenge_id': 'c-1',
        'resend_after_s': 30,
        'channel': 'email',
      }),
    );
    final body = await client().post('/auth/otp/request', {'email': 'a@b.in'});
    expect(body, {'challenge_id': 'c-1', 'resend_after_s': 30, 'channel': 'email'});
  });
}

Future<ApiFailure> _failureOf(Future<Object?> call) async {
  try {
    await call;
  } on ApiFailure catch (failure) {
    return failure;
  }
  fail('expected an ApiFailure');
}
