import '../l10n/language_mapper.dart';

/// The `user` object the server returns on `POST /auth/otp/verify` and, from D10, inside `/me`
/// (TECH_PLAN §3.7; server `account.api.UserSummary`). Nulls are omitted on the wire (§11.3):
/// an email-only account simply has no `phone`.
class UserSummary {
  const UserSummary({
    required this.id,
    required this.language,
    required this.role,
    this.phone,
    this.email,
    this.displayName,
  });

  factory UserSummary.fromJson(Map<String, Object?> json) => UserSummary(
    id: json['id'] as String,
    phone: json['phone'] as String?,
    email: json['email'] as String?,
    language: AppLanguage.fromWire(json['language'] as String?),
    role: json['role'] as String? ?? 'student',
    displayName: json['display_name'] as String?,
  );

  final String id;
  final String? phone;
  final String? email;
  final AppLanguage language;

  /// `student` or `admin` (server `UserRole`).
  final String role;
  final String? displayName;

  /// The identifier the student signed in with: email while the SMS channel waits for F1,
  /// phone once it exists (D7 ruling, DECISIONS 2026-09-08).
  String get identifier => email ?? phone ?? id;

  Map<String, Object?> toJson() => <String, Object?>{
    'id': id,
    if (phone != null) 'phone': phone,
    if (email != null) 'email': email,
    'language': language.wireValue,
    'role': role,
    if (displayName != null) 'display_name': displayName,
  };

  @override
  bool operator ==(Object other) =>
      other is UserSummary &&
      other.id == id &&
      other.phone == phone &&
      other.email == email &&
      other.language == language &&
      other.role == role &&
      other.displayName == displayName;

  @override
  int get hashCode =>
      Object.hash(id, phone, email, language, role, displayName);

  @override
  String toString() => 'UserSummary($id, $identifier, ${language.wireValue})';
}
