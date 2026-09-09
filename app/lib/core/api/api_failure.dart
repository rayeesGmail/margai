/// A failed API call as the screens see it (TECH_PLAN §5.4): either the server's envelope
/// `{error: {code, message_en, message_user_lang, details}}` (§3.3) or one of the three
/// client-only codes for a call that never produced an envelope (offline, malformed, certificate;
/// DECISIONS D8 and D9). Screens render [messageUser] when the server sent one and fall back to
/// ARB copy keyed by [code]; [details] hold machine-readable values only (reason codes per field,
/// `attempts_left`, `retry_after_s`, `request_id`).
class ApiFailure implements Exception {
  const ApiFailure({
    required this.code,
    this.status,
    this.messageEn,
    this.messageUser,
    this.details = const <String, Object?>{},
    this.retryAfter,
  });

  /// No network, a refused connection or a timeout: the request never got an answer. The screen
  /// shows the honest offline state and keeps what the student typed.
  const ApiFailure.offline() : this(code: offlineCode);

  /// An answer that is not the envelope (a proxy's HTML page, an empty body, a broken JSON).
  const ApiFailure.malformed(int? status)
    : this(code: malformedCode, status: status);

  /// The TLS handshake or certificate check failed, so nothing left the phone. On Android the
  /// usual cause is a clock that is far off (PLAN D9 row 6); the copy says so.
  const ApiFailure.certificate() : this(code: certificateCode);

  static const String offlineCode = 'OFFLINE';
  static const String malformedCode = 'MALFORMED';
  static const String certificateCode = 'CERTIFICATE';

  /// An `ErrorCode` name from TECH_PLAN §3.3, or one of the three client-only codes above.
  final String code;

  /// HTTP status when there was a response.
  final int? status;

  final String? messageEn;

  /// The message in the language the request asked for (`Accept-Language`, or the JWT `lang`).
  final String? messageUser;

  final Map<String, Object?> details;

  /// From the `Retry-After` header, else `details.retry_after_s` (429s carry both, D7).
  final Duration? retryAfter;

  bool get isOffline => code == offlineCode;

  bool get isMalformed => code == malformedCode;

  bool get isCertificate => code == certificateCode;

  /// The server answered with the §3.3 envelope; false for the three client-only codes.
  bool get isEnvelope => !isOffline && !isMalformed && !isCertificate;

  /// `details.attempts_left` on `OTP_INVALID` (0 on the last attempt).
  int? get attemptsLeft => _intDetail('attempts_left');

  /// The `request_id` an `INTERNAL` failure carries, for support.
  String? get requestId => details['request_id']?.toString();

  /// The reason code for one field of a `VALIDATION_FAILED` envelope (`email.invalid`,
  /// `not_blank`, `channel.unavailable`, …), rendered by the app's ARB copy.
  String? reasonFor(String field) {
    final value = details[field];
    return value is String ? value : null;
  }

  int? _intDetail(String key) {
    final value = details[key];
    if (value is int) {
      return value;
    }
    if (value is num) {
      return value.toInt();
    }
    return int.tryParse(value?.toString() ?? '');
  }

  @override
  String toString() =>
      'ApiFailure($code${status == null ? '' : ', $status'}'
      '${details.isEmpty ? '' : ', $details'})';
}
