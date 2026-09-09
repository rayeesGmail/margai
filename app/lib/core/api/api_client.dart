import 'dart:io';

import 'package:dio/dio.dart';

import 'api_failure.dart';
import 'request_ids.dart';

/// Supplies the bearer token for authenticated calls; `null` on the public routes or when the
/// student is signed out.
typedef BearerSupplier = Future<String?> Function();

/// The one HTTP client every repository uses (TECH_PLAN §5.4, `.claude/rules/app.md`). dio with
/// the request headers of §3.1 (`X-Request-Id`, `X-App-Version`, `X-Client-Time`,
/// `Accept-Language` per §3.8, the bearer), the base path `/api/v1`, 10-second connect and
/// 30-second receive timeouts, and one mapping of every failure to [ApiFailure]: the server's
/// envelope when there is one, [ApiFailure.offline] when the network never answered,
/// [ApiFailure.malformed] when the answer is not the envelope, [ApiFailure.certificate] when
/// the secure connection could not be made (D9). The single-flight refresh on `AUTH_EXPIRED`
/// arrives with token rotation at D10.
class ApiClient {
  ApiClient({
    required String baseUrl,
    required String appVersion,
    required this._acceptLanguage,
    required this._bearer,
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
    _dio.interceptors.add(
      InterceptorsWrapper(onRequest: _decorate),
    );
  }

  static const String basePath = '/api/v1';
  static const Duration connectTimeout = Duration(seconds: 10);
  static const Duration receiveTimeout = Duration(seconds: 30);

  final Dio _dio;
  final String Function() _acceptLanguage;
  final BearerSupplier _bearer;
  final DateTime Function() _clock;
  final RequestIds _requestIds;

  /// `POST path` with a JSON body; the decoded JSON object on success.
  Future<Map<String, Object?>> post(String path, Map<String, Object?> body) =>
      _run(() => _dio.post<Object?>(path, data: body));

  /// `GET path`; the decoded JSON object on success.
  Future<Map<String, Object?>> get(String path) =>
      _run(() => _dio.get<Object?>(path));

  Future<void> _decorate(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    options.headers['X-Request-Id'] = _requestIds.next();
    options.headers['X-Client-Time'] = _clock().toUtc().toIso8601String();
    options.headers['Accept-Language'] = _acceptLanguage();
    final bearer = await _bearer();
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
