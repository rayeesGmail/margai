import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:intl/intl.dart' as intl;

import 'app_localizations_en.dart';
import 'app_localizations_hi.dart';

// ignore_for_file: type=lint

/// Callers can lookup localized strings with an instance of AppLocalizations
/// returned by `AppLocalizations.of(context)`.
///
/// Applications need to include `AppLocalizations.delegate()` in their app's
/// `localizationDelegates` list, and the locales they support in the app's
/// `supportedLocales` list. For example:
///
/// ```dart
/// import 'l10n/app_localizations.dart';
///
/// return MaterialApp(
///   localizationsDelegates: AppLocalizations.localizationsDelegates,
///   supportedLocales: AppLocalizations.supportedLocales,
///   home: MyApplicationHome(),
/// );
/// ```
///
/// ## Update pubspec.yaml
///
/// Please make sure to update your pubspec.yaml to include the following
/// packages:
///
/// ```yaml
/// dependencies:
///   # Internationalization support.
///   flutter_localizations:
///     sdk: flutter
///   intl: any # Use the pinned version from flutter_localizations
///
///   # Rest of dependencies
/// ```
///
/// ## iOS Applications
///
/// iOS applications define key application metadata, including supported
/// locales, in an Info.plist file that is built into the application bundle.
/// To configure the locales supported by your app, you’ll need to edit this
/// file.
///
/// First, open your project’s ios/Runner.xcworkspace Xcode workspace file.
/// Then, in the Project Navigator, open the Info.plist file under the Runner
/// project’s Runner folder.
///
/// Next, select the Information Property List item, select Add Item from the
/// Editor menu, then select Localizations from the pop-up menu.
///
/// Select and expand the newly-created Localizations item then, for each
/// locale your application supports, add a new item and select the locale
/// you wish to add from the pop-up menu in the Value field. This list should
/// be consistent with the languages listed in the AppLocalizations.supportedLocales
/// property.
abstract class AppLocalizations {
  AppLocalizations(String locale)
    : localeName = intl.Intl.canonicalizedLocale(locale.toString());

  final String localeName;

  static AppLocalizations of(BuildContext context) {
    return Localizations.of<AppLocalizations>(context, AppLocalizations)!;
  }

  static const LocalizationsDelegate<AppLocalizations> delegate =
      _AppLocalizationsDelegate();

  /// A list of this localizations delegate along with the default localizations
  /// delegates.
  ///
  /// Returns a list of localizations delegates containing this delegate along with
  /// GlobalMaterialLocalizations.delegate, GlobalCupertinoLocalizations.delegate,
  /// and GlobalWidgetsLocalizations.delegate.
  ///
  /// Additional delegates can be added by appending to this list in
  /// MaterialApp. This list does not have to be used at all if a custom list
  /// of delegates is preferred or required.
  static const List<LocalizationsDelegate<dynamic>> localizationsDelegates =
      <LocalizationsDelegate<dynamic>>[
        delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ];

  /// A list of this localizations delegate's supported locales.
  static const List<Locale> supportedLocales = <Locale>[
    Locale('en'),
    Locale('hi'),
    Locale.fromSubtags(languageCode: 'hi', scriptCode: 'Latn'),
  ];

  /// Application name shown in the launcher, app bar and task switcher. Brand name; not translated.
  ///
  /// In en, this message translates to:
  /// **'MARG AI'**
  String get appTitle;

  /// Login screen headline (SPEC §8 screen 1). Mentor voice: warm, direct, proudly AI.
  ///
  /// In en, this message translates to:
  /// **'Hi! I\'m your NEET mentor.'**
  String get loginHeadline;

  /// Login screen intro under the headline. Email while the SMS channel waits for the DLT template (D7 ruling).
  ///
  /// In en, this message translates to:
  /// **'Sign in with your email and I\'ll send you a 6-digit code.'**
  String get loginIntro;

  /// No description provided for @emailLabel.
  ///
  /// In en, this message translates to:
  /// **'Email'**
  String get emailLabel;

  /// No description provided for @emailHint.
  ///
  /// In en, this message translates to:
  /// **'you@example.com'**
  String get emailHint;

  /// No description provided for @sendCodeButton.
  ///
  /// In en, this message translates to:
  /// **'Send code'**
  String get sendCodeButton;

  /// Primary button label while the code request is in flight.
  ///
  /// In en, this message translates to:
  /// **'Sending…'**
  String get sendingCode;

  /// No description provided for @codeSentTitle.
  ///
  /// In en, this message translates to:
  /// **'Check your email'**
  String get codeSentTitle;

  /// No description provided for @codeSentTo.
  ///
  /// In en, this message translates to:
  /// **'I\'ve sent a 6-digit code to {email}.'**
  String codeSentTo(String email);

  /// No description provided for @codeLabel.
  ///
  /// In en, this message translates to:
  /// **'6-digit code'**
  String get codeLabel;

  /// No description provided for @verifyButton.
  ///
  /// In en, this message translates to:
  /// **'Verify'**
  String get verifyButton;

  /// No description provided for @verifying.
  ///
  /// In en, this message translates to:
  /// **'Checking…'**
  String get verifying;

  /// No description provided for @resendButton.
  ///
  /// In en, this message translates to:
  /// **'Send a new code'**
  String get resendButton;

  /// Countdown until a resend is allowed (server resend_after_s / retry_after_s).
  ///
  /// In en, this message translates to:
  /// **'New code in {seconds}s'**
  String resendIn(int seconds);

  /// No description provided for @changeEmailButton.
  ///
  /// In en, this message translates to:
  /// **'Change email'**
  String get changeEmailButton;

  /// After a wrong code: details.attempts_left from OTP_INVALID (5 per code).
  ///
  /// In en, this message translates to:
  /// **'{count, plural, =0{No tries left on this code — ask for a new one.} =1{1 try left.} other{{count} tries left.}}'**
  String attemptsLeft(int count);

  /// No description provided for @retryButton.
  ///
  /// In en, this message translates to:
  /// **'Retry'**
  String get retryButton;

  /// Client-only failure: no network, refused connection or timeout (TECH_PLAN §5.4).
  ///
  /// In en, this message translates to:
  /// **'You\'re offline. Check your connection and retry — nothing you typed is lost.'**
  String get failureOffline;

  /// Client-only failure: an answer that is not the error envelope.
  ///
  /// In en, this message translates to:
  /// **'The server sent something I couldn\'t read. Try again in a moment.'**
  String get failureMalformed;

  /// Shown under an INTERNAL failure so support can find the call.
  ///
  /// In en, this message translates to:
  /// **'Reference: {requestId}'**
  String failureRequestId(String requestId);

  /// Fallback copy by error code when the envelope carries no message (TECH_PLAN §3.3). Same strings as the server's messages_en.
  ///
  /// In en, this message translates to:
  /// **'Some details don\'t look right. Have a look and try again.'**
  String get errorValidationFailed;

  /// No description provided for @errorAuthRequired.
  ///
  /// In en, this message translates to:
  /// **'Please sign in to continue.'**
  String get errorAuthRequired;

  /// No description provided for @errorAuthExpired.
  ///
  /// In en, this message translates to:
  /// **'Your session needs a quick refresh.'**
  String get errorAuthExpired;

  /// No description provided for @errorAuthInvalid.
  ///
  /// In en, this message translates to:
  /// **'Please sign in again.'**
  String get errorAuthInvalid;

  /// No description provided for @errorOtpInvalid.
  ///
  /// In en, this message translates to:
  /// **'That code didn\'t match. Try once more.'**
  String get errorOtpInvalid;

  /// No description provided for @errorOtpExpired.
  ///
  /// In en, this message translates to:
  /// **'That code isn\'t valid any more. Ask for a new one.'**
  String get errorOtpExpired;

  /// No description provided for @errorRateLimited.
  ///
  /// In en, this message translates to:
  /// **'A little too fast. Try again in a moment.'**
  String get errorRateLimited;

  /// No description provided for @errorOtpRateLimited.
  ///
  /// In en, this message translates to:
  /// **'Too many codes requested. Give it a little time.'**
  String get errorOtpRateLimited;

  /// No description provided for @errorInternal.
  ///
  /// In en, this message translates to:
  /// **'Something went wrong on our side. Please try again.'**
  String get errorInternal;

  /// No description provided for @errorUnknown.
  ///
  /// In en, this message translates to:
  /// **'That didn\'t work. Please try again.'**
  String get errorUnknown;

  /// Reason codes of VALIDATION_FAILED details (D7 DECISIONS): field → code, rendered here, never as server prose.
  ///
  /// In en, this message translates to:
  /// **'This can\'t be empty.'**
  String get reasonNotBlank;

  /// No description provided for @reasonNotNull.
  ///
  /// In en, this message translates to:
  /// **'This is needed.'**
  String get reasonNotNull;

  /// No description provided for @reasonMin.
  ///
  /// In en, this message translates to:
  /// **'That\'s too short.'**
  String get reasonMin;

  /// No description provided for @reasonSize.
  ///
  /// In en, this message translates to:
  /// **'That\'s too long.'**
  String get reasonSize;

  /// No description provided for @reasonPhoneInvalid.
  ///
  /// In en, this message translates to:
  /// **'That doesn\'t look like an Indian mobile number.'**
  String get reasonPhoneInvalid;

  /// No description provided for @reasonEmailInvalid.
  ///
  /// In en, this message translates to:
  /// **'That doesn\'t look like an email address.'**
  String get reasonEmailInvalid;

  /// No description provided for @reasonIdentifierRequired.
  ///
  /// In en, this message translates to:
  /// **'Enter your email to continue.'**
  String get reasonIdentifierRequired;

  /// No description provided for @reasonIdentifierOneOnly.
  ///
  /// In en, this message translates to:
  /// **'Use either your email or your phone, not both.'**
  String get reasonIdentifierOneOnly;

  /// No description provided for @reasonChannelUnavailable.
  ///
  /// In en, this message translates to:
  /// **'Signing in by phone isn\'t available yet — use your email.'**
  String get reasonChannelUnavailable;

  /// No description provided for @reasonCodeDigits.
  ///
  /// In en, this message translates to:
  /// **'The code is 6 digits.'**
  String get reasonCodeDigits;

  /// No description provided for @reasonBodyMalformed.
  ///
  /// In en, this message translates to:
  /// **'The app sent something the server couldn\'t read. Please update the app.'**
  String get reasonBodyMalformed;

  /// No description provided for @reasonContentTypeUnsupported.
  ///
  /// In en, this message translates to:
  /// **'The app sent something the server couldn\'t read. Please update the app.'**
  String get reasonContentTypeUnsupported;

  /// No description provided for @reasonUnknown.
  ///
  /// In en, this message translates to:
  /// **'Have a look at this and try again.'**
  String get reasonUnknown;

  /// Placeholder for SPEC §8 screen 7 (Today) until D29 builds it.
  ///
  /// In en, this message translates to:
  /// **'You\'re in.'**
  String get todayPlaceholderTitle;

  /// No description provided for @todayPlaceholderBody.
  ///
  /// In en, this message translates to:
  /// **'Signed in as {identifier}.'**
  String todayPlaceholderBody(String identifier);

  /// No description provided for @todayPlaceholderNote.
  ///
  /// In en, this message translates to:
  /// **'Your onboarding and first plan arrive in a later build. Nothing else to do here yet.'**
  String get todayPlaceholderNote;
}

class _AppLocalizationsDelegate
    extends LocalizationsDelegate<AppLocalizations> {
  const _AppLocalizationsDelegate();

  @override
  Future<AppLocalizations> load(Locale locale) {
    return SynchronousFuture<AppLocalizations>(lookupAppLocalizations(locale));
  }

  @override
  bool isSupported(Locale locale) =>
      <String>['en', 'hi'].contains(locale.languageCode);

  @override
  bool shouldReload(_AppLocalizationsDelegate old) => false;
}

AppLocalizations lookupAppLocalizations(Locale locale) {
  // Lookup logic when language+script codes are specified.
  switch (locale.languageCode) {
    case 'hi':
      {
        switch (locale.scriptCode) {
          case 'Latn':
            return AppLocalizationsHiLatn();
        }
        break;
      }
  }

  // Lookup logic when only language code is specified.
  switch (locale.languageCode) {
    case 'en':
      return AppLocalizationsEn();
    case 'hi':
      return AppLocalizationsHi();
  }

  throw FlutterError(
    'AppLocalizations.delegate failed to load unsupported locale "$locale". This is likely '
    'an issue with the localizations generation tool. Please file an issue '
    'on GitHub with a reproducible sample app and the gen-l10n configuration '
    'that was used.',
  );
}
