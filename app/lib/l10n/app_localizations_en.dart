// ignore: unused_import
import 'package:intl/intl.dart' as intl;

import 'app_localizations.dart';

// ignore_for_file: type=lint

/// The translations for English (`en`).
class AppLocalizationsEn extends AppLocalizations {
  AppLocalizationsEn([String locale = 'en']) : super(locale);

  @override
  String get appTitle => 'MARG AI';

  @override
  String get loginHeadline => 'Hi! I\'m your NEET mentor.';

  @override
  String get loginIntro =>
      'Sign in with your email and I\'ll send you a 6-digit code.';

  @override
  String get emailLabel => 'Email';

  @override
  String get emailHint => 'you@example.com';

  @override
  String get sendCodeButton => 'Send code';

  @override
  String get sendingCode => 'Sending…';

  @override
  String sendCodeIn(int seconds) {
    return 'Send code in ${seconds}s';
  }

  @override
  String sendCodeInMinutes(int minutes) {
    String _temp0 = intl.Intl.pluralLogic(
      minutes,
      locale: localeName,
      other: 'Send code in $minutes min',
      one: 'Send code in 1 min',
    );
    return '$_temp0';
  }

  @override
  String get codeSentTitle => 'Check your email';

  @override
  String codeSentTo(String email) {
    return 'I\'ve sent a 6-digit code to $email.';
  }

  @override
  String get codeLabel => '6-digit code';

  @override
  String get verifyButton => 'Verify';

  @override
  String get verifying => 'Checking…';

  @override
  String get resendButton => 'Send a new code';

  @override
  String resendIn(int seconds) {
    return 'New code in ${seconds}s';
  }

  @override
  String resendInMinutes(int minutes) {
    String _temp0 = intl.Intl.pluralLogic(
      minutes,
      locale: localeName,
      other: 'New code in $minutes min',
      one: 'New code in 1 min',
    );
    return '$_temp0';
  }

  @override
  String get changeEmailButton => 'Change email';

  @override
  String attemptsLeft(int count) {
    String _temp0 = intl.Intl.pluralLogic(
      count,
      locale: localeName,
      other: '$count tries left.',
      one: '1 try left.',
      zero: 'No tries left on this code — ask for a new one.',
    );
    return '$_temp0';
  }

  @override
  String get retryButton => 'Retry';

  @override
  String get failureOffline =>
      'You\'re offline. Check your connection and retry — nothing you typed is lost.';

  @override
  String get failureMalformed =>
      'The server sent something I couldn\'t read. Try again in a moment.';

  @override
  String failureRequestId(String requestId) {
    return 'Reference: $requestId';
  }

  @override
  String get errorValidationFailed =>
      'Some details don\'t look right. Have a look and try again.';

  @override
  String get errorAuthRequired => 'Please sign in to continue.';

  @override
  String get errorAuthExpired => 'Your session needs a quick refresh.';

  @override
  String get errorAuthInvalid => 'Please sign in again.';

  @override
  String get errorOtpInvalid => 'That code didn\'t match. Try once more.';

  @override
  String get errorOtpExpired =>
      'That code isn\'t valid any more. Ask for a new one.';

  @override
  String get errorRateLimited => 'A little too fast. Try again in a moment.';

  @override
  String get errorOtpRateLimited =>
      'Too many codes requested. Give it a little time.';

  @override
  String get errorInternal =>
      'Something went wrong on our side. Please try again.';

  @override
  String get errorUnknown => 'That didn\'t work. Please try again.';

  @override
  String get reasonNotBlank => 'This can\'t be empty.';

  @override
  String get reasonNotNull => 'This is needed.';

  @override
  String get reasonMin => 'That\'s too short.';

  @override
  String get reasonSize => 'That\'s too long.';

  @override
  String get reasonPhoneInvalid =>
      'That doesn\'t look like an Indian mobile number.';

  @override
  String get reasonEmailInvalid => 'That doesn\'t look like an email address.';

  @override
  String get reasonIdentifierRequired => 'Enter your email to continue.';

  @override
  String get reasonIdentifierOneOnly =>
      'Use either your email or your phone, not both.';

  @override
  String get reasonChannelUnavailable =>
      'Signing in by phone isn\'t available yet — use your email.';

  @override
  String get reasonCodeDigits => 'The code is 6 digits.';

  @override
  String get reasonBodyMalformed =>
      'The app sent something the server couldn\'t read. Please update the app.';

  @override
  String get reasonContentTypeUnsupported =>
      'The app sent something the server couldn\'t read. Please update the app.';

  @override
  String get reasonUnknown => 'Have a look at this and try again.';

  @override
  String get todayPlaceholderTitle => 'You\'re in.';

  @override
  String todayPlaceholderBody(String identifier) {
    return 'Signed in as $identifier.';
  }

  @override
  String get todayPlaceholderNote =>
      'Your onboarding and first plan arrive in a later build. Nothing else to do here yet.';
}
