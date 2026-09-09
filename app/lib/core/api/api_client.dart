import 'dart:io';

import 'package:dio/dio.dart';

import 'api_failure.dart';
import 'request_ids.dart';

/// Supplies the bearer token for authenticated calls; `null` when the student is signed out.
typedef BearerSupplier = Future<String?> Function();

/// Replaces an access token the server would not take (TECH_PLAN §5.4): answers the new one, or
/// throws the [ApiFailure] that ended the attempt — `AUTH_INVALID` when the session is gone,
/// offline when the network was. The client never calls it twice for one request.
typedef AuthExpiredHandler = Future<String> Function();

/// Ends the session on the device (TECH_PLAN §3.3: `AUTH_INVALID` means re-login) when even a
/// freshly refreshed token is refused — the account itself can no longer be served.
typedef SessionLostHandler = Future<void> Function();

/// The one HTTP client every repository uses (TECH_PLAN §5.4, `.claude/rules/app.md`). dio with
/// the request headers of §3.1 (`X-Request-Id`, `X-App-Version`, `X-Client-Time`,
/// `Accept-Language` per §3.8, the bearer), the base path `/api/v1`, 10-second connect and
/// 30-second receive timeouts, and one mapping of every failure to [ApiFailure]: the server's
/// envelope when there is one, [ApiFailure.offline] when the network never answered,
/// [ApiFailure.malformed] when the answer is not the envelope, [ApiFailure.certificate] when
/// the secure connection could not be made (D9).
///
/// Since D10 (PLAN "token rotation"): an authenticated call that comes back 401 `AUTH_EXPIRED`
/// — or `AUTH_INVALID`, which is what a stored access token becomes after the server's signing
/// key changed while the refresh token in the same store is still good — asks [onAuthExpired]
/// for a new access token and is retried once with it; the handler is single-flight, so calls
/// that fail together share one refresh. A retry that is still `AUTH_INVALID` means the account
/// itself cannot be served (deleted, or unrepaired), and [onSessionLost] signs the device out
/// (§3.3: re-login). The public auth routes (`/auth/otp/*`, `/auth/refresh`) carry no bearer and
/// never refresh: the server does not read one there, and a stale token in the store must not
/// stand in the way of the refresh itself.
class ApiClient {
  ApiClient({
    required String baseUrl,
    required String appVersion,
    required this._acceptLanguage,
    required this._bearer,
    this._onAuthExpired,
    this._onSessionLost,
    HttpClientAdapter? adapter,
    DateTime Function()? clock,
    RequestIds? requestIds,
  }) : _clock = clock ?? DateTime.now,
       _requestIds = requestIds ?? RequestIds(),
       _dio = Dio(
         BaseOptions(
           baseUrl: '${_stripSlash(baseUrl)}$basePath',
           connectTimeout: connectTimeout,
           receiveTimeout: receiveTimeout,
           sendTimeout: connectTimeout,
           contentType: Headers.jsonContentType,
           responseType: ResponseType.json,
           headers: <String, Object?>{
             Headers.acceptHeader: Headers.jsonContentType,
             'X-App-Version': appVersion,
           },
         ),
       ) {
    if (adapter != null) {
      _dio.httpClientAdapter = adapter;
    }
    _dio.interceptors.add(InterceptorsWrapper(onRequest: _decorate));
  }

  static const String basePath = '/api/v1';
  static const Duration connectTimeout = Duration(seconds: 10);
  static const Duration receiveTimeout = Duration(seconds: 30);

  /// Request option carrying the bearer a retry must use, so the retried call never depends on
  /// the store having been written first.
  static const String bearerOption = 'margai.bearer';

  final Dio _dio;
  final String Function() _acceptLanguage;
  final BearerSupplier _bearer;
  final AuthExpiredHandler? _onAuthExpired;
  final SessionLostHandler? _onSessionLost;
  final DateTime Function() _clock;
  final RequestIds _requestIds;

  /// The routes TECH_PLAN §1.5 step 3 leaves open: no bearer is sent, none is read.
  static bool isPublic(String path) =>
      path.startsWith('/auth/otp/') || path == '/auth/refresh';

  /// `POST path` with a JSON body; the decoded JSON object on success (empty for a 204).
  Future<Map<String, Object?>> post(String path, Map<String, Object?> body) =>
      _request('POST', path, body: body);

  /// `GET path`; the decoded JSON object on success.
  Future<Map<String, Object?>> get(String path) => _request('GET', path);

  /// `PATCH path` with a JSON body; the decoded JSON object on success.
  Future<Map<String, Object?>> patch(String path, Map<String, Object?> body) =>
      _request('PATCH', path, body: body);

  Future<Map<String, Object?>> _request(
    String method,
    String path, {
    Map<String, Object?>? body,
  }) async {
    Future<Response<Object?>> send([String? bearer]) => _dio.request<Object?>(
      path,
      data: body,
      options: Options(
        method: method,
        extra: bearer == null ? null : <String, Object?>{bearerOption: bearer},
      ),
    );
    try {
      return await _run(send);
    } on ApiFailure catch (failure) {
      final handler = _onAuthExpired;
      final replaceable = failure.isAuthExpired || failure.isAuthInvalid;
      if (!replaceable || isPublic(path) || handler == null) {
        rethrow;
      }
      final renewed = await handler();
      try {
        return await _run(() => send(renewed));
      } on ApiFailure catch (again) {
        if (again.isAuthInvalid) {
          // A token minted a moment ago is refused: the account, not the token, is the problem.
          await _onSessionLost?.call();
        }
        rethrow;
      }
    }
  }

  Future<void> _decorate(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    options.headers['X-Request-Id'] = _requestIds.next();
    options.headers['X-Client-Time'] = _clock().toUtc().toIso8601String();
    options.headers['Accept-Language'] = _acceptLanguage();
    final bearer = isPublic(options.path)
        ? null
        : (options.extra[bearerOption] as String? ?? await _bearer());
    if (bearer != null && bearer.isNotEmpty) {
      options.headers['Authorization'] = 'Bearer $bearer';
    }
    handler.next(options);
  }

  Future<Map<String, Object?>> _run(
    Future<Response<Object?>> Function() call,
  ) async {
    Response<Object?> response;
    try {
      response = await call();
    } on DioException catch (failure) {
      throw _map(failure);
    } on TlsException {
      throw const ApiFailure.certificate();
    } on SocketException {
      throw const ApiFailure.offline();
    }
    final data = response.data;
    if (data == null || (data is String && data.trim().isEmpty)) {
      // A 204, or an empty 200: nothing to decode and nothing wrong.
      return <String, Object?>{};
    }
    if (data is Map<String, Object?>) {
      return data;
    }
    if (data is Map) {
      return data.map((key, value) => MapEntry(key.toString(), value));
    }
    throw ApiFailure.malformed(response.statusCode);
  }

  static ApiFailure _map(DioException failure) {
    switch (failure.type) {
      case DioExceptionType.connectionError:
      case DioExceptionType.connectionTimeout:
      case DioExceptionType.sendTimeout:
      case DioExceptionType.receiveTimeout:
        return const ApiFailure.offline();
      case DioExceptionType.badResponse:
        return _fromResponse(failure.response);
      case DioExceptionType.transformTimeout:
        // The answer arrived but could not be decoded in time: not the envelope.
        return ApiFailure.malformed(failure.response?.statusCode);
      case DioExceptionType.badCertificate:
        return const ApiFailure.certificate();
      case DioExceptionType.cancel:
      case DioExceptionType.unknown:
        // A handshake failure surfaces from the socket layer as a TlsException (its
        // HandshakeException subtype), never as a SocketException.
        if (failure.error is TlsException) {
          return const ApiFailure.certificate();
        }
        if (failure.error is SocketException) {
          return const ApiFailure.offline();
        }
        if (failure.response != null) {
          return _fromResponse(failure.response);
        }
        return ApiFailure.malformed(null);
    }
  }

  static ApiFailure _fromResponse(Response<Object?>? response) {
    final status = response?.statusCode;
    final data = response?.data;
    if (data is! Map) {
      return ApiFailure.malformed(status);
    }
    final error = data['error'];
    if (error is! Map || error['code'] is! String) {
      return ApiFailure.malformed(status);
    }
    final rawDetails = error['details'];
    final details = rawDetails is Map
        ? rawDetails.map((key, value) => MapEntry(key.toString(), value))
        : const <String, Object?>{};
    return ApiFailure(
      code: error['code'] as String,
      status: status,
      messageEn: error['message_en'] as String?,
      messageUser: error['message_user_lang'] as String?,
      details: details,
      retryAfter: _retryAfter(response, details),
    );
  }

  static Duration? _retryAfter(
    Response<Object?>? response,
    Map<String, Object?> details,
  ) {
    final header = response?.headers.value('retry-after');
    final fromHeader = int.tryParse(header ?? '');
    if (fromHeader != null) {
      return Duration(seconds: fromHeader);
    }
    final fromDetails = details['retry_after_s'];
    if (fromDetails is num) {
      return Duration(seconds: fromDetails.toInt());
    }
    return null;
  }

  static String _stripSlash(String url) =>
      url.endsWith('/') ? url.substring(0, url.length - 1) : url;
}
