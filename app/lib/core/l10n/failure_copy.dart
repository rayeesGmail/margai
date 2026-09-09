import '../../l10n/app_localizations.dart';
import '../api/api_failure.dart';

/// Turns codes into words (TECH_PLAN §3.3, §11.7; DECISIONS D7 copy rule, D8): the envelope's
/// `message_user_lang` when the server sent one, else this app's ARB copy by error code; reason
/// codes and the two client-only codes always come from the ARB. No prose is ever built in Java
/// or in a widget.
abstract final class FailureCopy {
  /// The one line a screen shows for [failure].
  static String message(AppLocalizations l10n, ApiFailure failure) {
    if (failure.isOffline) {
      return l10n.failureOffline;
    }
    if (failure.isMalformed) {
      return l10n.failureMalformed;
    }
    final user = failure.messageUser;
    if (user != null && user.trim().isNotEmpty) {
      return user;
    }
    final en = failure.messageEn;
    if (en != null && en.trim().isNotEmpty) {
      return en;
    }
    return byCode(l10n, failure.code);
  }

  /// Fallback copy for an `ErrorCode` (TECH_PLAN §3.3) when the envelope carried no message.
  static String byCode(AppLocalizations l10n, String code) => switch (code) {
    'VALIDATION_FAILED' => l10n.errorValidationFailed,
    'AUTH_REQUIRED' => l10n.errorAuthRequired,
    'AUTH_EXPIRED' => l10n.errorAuthExpired,
    'AUTH_INVALID' => l10n.errorAuthInvalid,
    'OTP_INVALID' => l10n.errorOtpInvalid,
    'OTP_EXPIRED' => l10n.errorOtpExpired,
    'RATE_LIMITED' => l10n.errorRateLimited,
    'OTP_RATE_LIMITED' => l10n.errorOtpRateLimited,
    'INTERNAL' => l10n.errorInternal,
    _ => l10n.errorUnknown,
  };

  /// The reason codes a `VALIDATION_FAILED` envelope puts against a field (server
  /// `ApiExceptionHandler`, `Identifiers`, `OtpService`, `AuthController`; D7). Keep this list in
  /// step with the server: the coverage test walks it in all three locales.
  static const List<String> reasonCodes = <String>[
    'not_blank',
    'not_null',
    'min',
    'size',
    'phone.invalid',
    'email.invalid',
    'identifier.required',
    'identifier.one_only',
    'channel.unavailable',
    'code.digits',
    'malformed',
    'unsupported',
  ];

  /// Copy for one reason code, rendered under the field it names.
  static String reason(AppLocalizations l10n, String code) => switch (code) {
    'not_blank' => l10n.reasonNotBlank,
    'not_null' => l10n.reasonNotNull,
    'min' => l10n.reasonMin,
    'size' => l10n.reasonSize,
    'phone.invalid' => l10n.reasonPhoneInvalid,
    'email.invalid' => l10n.reasonEmailInvalid,
    'identifier.required' => l10n.reasonIdentifierRequired,
    'identifier.one_only' => l10n.reasonIdentifierOneOnly,
    'channel.unavailable' => l10n.reasonChannelUnavailable,
    'code.digits' => l10n.reasonCodeDigits,
    'malformed' => l10n.reasonBodyMalformed,
    'unsupported' => l10n.reasonContentTypeUnsupported,
    _ => l10n.reasonUnknown,
  };
}
