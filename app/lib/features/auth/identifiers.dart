/// Client-side shape checks before a round trip (SPEC §8 screen 1 "bulletproof retry" starts
/// with not sending what cannot work). The server's `Identifiers` stays the authority and answers
/// with the same reason codes (`not_blank`, `email.invalid`), so the copy is shared (D7 DECISIONS).
abstract final class Identifiers {
  static const String notBlank = 'not_blank';
  static const String emailInvalid = 'email.invalid';
  static const String codeDigits = 'code.digits';

  static const int emailMaxLength = 254;
  static const int codeLength = 6;

  static final RegExp _emailShape = RegExp(r'^[^\s@]+@[^\s@]+\.[^\s@]{2,}$');
  static final RegExp _codeShape = RegExp('^[0-9]{$codeLength}\$');

  /// `null` when the email may be sent, else the reason code to render.
  static String? emailReason(String raw) {
    final email = raw.trim();
    if (email.isEmpty) {
      return notBlank;
    }
    if (email.length > emailMaxLength || !_emailShape.hasMatch(email)) {
      return emailInvalid;
    }
    return null;
  }

  /// `null` when the code may be sent, else the reason code to render.
  static String? codeReason(String raw) {
    final code = raw.trim();
    if (code.isEmpty) {
      return notBlank;
    }
    return _codeShape.hasMatch(code) ? null : codeDigits;
  }
}
