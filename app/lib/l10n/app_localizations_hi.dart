// ignore: unused_import
import 'package:intl/intl.dart' as intl;

import 'app_localizations.dart';

// ignore_for_file: type=lint

/// The translations for Hindi (`hi`).
class AppLocalizationsHi extends AppLocalizations {
  AppLocalizationsHi([String locale = 'hi']) : super(locale);

  @override
  String get appTitle => 'MARG AI';

  @override
  String get loginHeadline => 'नमस्ते! मैं आपका NEET मेंटर हूँ।';

  @override
  String get loginIntro =>
      'अपने ईमेल से साइन इन करें — मैं आपको 6 अंकों का कोड भेजूँगा।';

  @override
  String get emailLabel => 'ईमेल';

  @override
  String get emailHint => 'you@example.com';

  @override
  String get sendCodeButton => 'कोड भेजें';

  @override
  String get sendingCode => 'भेज रहा हूँ…';

  @override
  String get codeSentTitle => 'अपना ईमेल देखें';

  @override
  String codeSentTo(String email) {
    return 'मैंने $email पर 6 अंकों का कोड भेजा है।';
  }

  @override
  String get codeLabel => '6 अंकों का कोड';

  @override
  String get verifyButton => 'जाँचें';

  @override
  String get verifying => 'जाँच रहा हूँ…';

  @override
  String get resendButton => 'नया कोड भेजें';

  @override
  String resendIn(int seconds) {
    return 'नया कोड $seconds सेकंड में';
  }

  @override
  String get changeEmailButton => 'ईमेल बदलें';

  @override
  String attemptsLeft(int count) {
    String _temp0 = intl.Intl.pluralLogic(
      count,
      locale: localeName,
      other: '$count कोशिशें बाकी।',
      one: '1 कोशिश बाकी।',
      zero: 'इस कोड पर कोई कोशिश बाकी नहीं।',
    );
    return '$_temp0';
  }

  @override
  String get retryButton => 'फिर कोशिश करें';

  @override
  String get failureOffline =>
      'आप ऑफ़लाइन हैं। कनेक्शन देखकर फिर कोशिश करें — आपने जो लिखा है वह सुरक्षित है।';

  @override
  String get failureMalformed =>
      'सर्वर से कुछ ऐसा आया जो मैं पढ़ नहीं पाया। कुछ पल बाद फिर कोशिश करें।';

  @override
  String failureRequestId(String requestId) {
    return 'संदर्भ: $requestId';
  }

  @override
  String get errorValidationFailed =>
      'कुछ जानकारी सही नहीं लग रही। एक बार देखकर फिर कोशिश करें।';

  @override
  String get errorAuthRequired => 'जारी रखने के लिए साइन इन करें।';

  @override
  String get errorAuthExpired => 'आपके सेशन को एक छोटा रिफ्रेश चाहिए।';

  @override
  String get errorAuthInvalid => 'कृपया दोबारा साइन इन करें।';

  @override
  String get errorOtpInvalid => 'यह कोड मेल नहीं खाया। एक बार और कोशिश करें।';

  @override
  String get errorOtpExpired => 'यह कोड अब मान्य नहीं है। नया कोड मंगाएँ।';

  @override
  String get errorRateLimited =>
      'थोड़ा तेज़ हो गया। कुछ पल बाद फिर कोशिश करें।';

  @override
  String get errorOtpRateLimited =>
      'बहुत सारे कोड मंगाए गए हैं। थोड़ा इंतज़ार करें।';

  @override
  String get errorInternal =>
      'हमारी तरफ़ से कुछ गड़बड़ हुई। कृपया फिर कोशिश करें।';

  @override
  String get errorUnknown => 'यह काम नहीं कर पाया। कृपया फिर कोशिश करें।';

  @override
  String get reasonNotBlank => 'यह खाली नहीं हो सकता।';

  @override
  String get reasonNotNull => 'यह ज़रूरी है।';

  @override
  String get reasonMin => 'यह बहुत छोटा है।';

  @override
  String get reasonSize => 'यह बहुत लंबा है।';

  @override
  String get reasonPhoneInvalid => 'यह भारतीय मोबाइल नंबर जैसा नहीं लग रहा।';

  @override
  String get reasonEmailInvalid => 'यह ईमेल पता जैसा नहीं लग रहा।';

  @override
  String get reasonIdentifierRequired => 'जारी रखने के लिए अपना ईमेल लिखें।';

  @override
  String get reasonIdentifierOneOnly =>
      'ईमेल या फ़ोन में से एक चुनें, दोनों नहीं।';

  @override
  String get reasonChannelUnavailable =>
      'फ़ोन से साइन इन अभी उपलब्ध नहीं है — अपना ईमेल इस्तेमाल करें।';

  @override
  String get reasonCodeDigits => 'कोड 6 अंकों का होता है।';

  @override
  String get reasonBodyMalformed =>
      'ऐप ने कुछ ऐसा भेजा जो सर्वर पढ़ नहीं पाया। कृपया ऐप अपडेट करें।';

  @override
  String get reasonContentTypeUnsupported =>
      'ऐप ने कुछ ऐसा भेजा जो सर्वर पढ़ नहीं पाया। कृपया ऐप अपडेट करें।';

  @override
  String get reasonUnknown => 'इसे एक बार देखकर फिर कोशिश करें।';

  @override
  String get todayPlaceholderTitle => 'आप अंदर आ गए।';

  @override
  String todayPlaceholderBody(String identifier) {
    return '$identifier के रूप में साइन इन हैं।';
  }

  @override
  String get todayPlaceholderNote =>
      'आपका ऑनबोर्डिंग और पहला प्लान अगले बिल्ड में आएगा। अभी यहाँ और कुछ नहीं करना है।';
}

/// The translations for Hindi, using the Latin script (`hi_Latn`).
class AppLocalizationsHiLatn extends AppLocalizationsHi {
  AppLocalizationsHiLatn() : super('hi_Latn');

  @override
  String get appTitle => 'MARG AI';

  @override
  String get loginHeadline => 'Hi! Main aapka NEET mentor hoon.';

  @override
  String get loginIntro =>
      'Apne email se sign in karo — main 6-digit code bhejta hoon.';

  @override
  String get emailLabel => 'Email';

  @override
  String get emailHint => 'you@example.com';

  @override
  String get sendCodeButton => 'Code bhejo';

  @override
  String get sendingCode => 'Bhej raha hoon…';

  @override
  String get codeSentTitle => 'Apna email check karo';

  @override
  String codeSentTo(String email) {
    return 'Maine $email par 6-digit code bheja hai.';
  }

  @override
  String get codeLabel => '6-digit code';

  @override
  String get verifyButton => 'Verify karo';

  @override
  String get verifying => 'Check kar raha hoon…';

  @override
  String get resendButton => 'Naya code bhejo';

  @override
  String resendIn(int seconds) {
    return 'Naya code ${seconds}s mein';
  }

  @override
  String get changeEmailButton => 'Email badlo';

  @override
  String attemptsLeft(int count) {
    String _temp0 = intl.Intl.pluralLogic(
      count,
      locale: localeName,
      other: '$count tries baaki.',
      one: '1 try baaki.',
      zero: 'Is code par koi try baaki nahi.',
    );
    return '$_temp0';
  }

  @override
  String get retryButton => 'Phir try karo';

  @override
  String get failureOffline =>
      'Aap offline ho. Connection check karke phir try karo — jo likha hai wo safe hai.';

  @override
  String get failureMalformed =>
      'Server se kuch aisa aaya jo main padh nahi paaya. Ek pal baad phir try karo.';

  @override
  String failureRequestId(String requestId) {
    return 'Reference: $requestId';
  }

  @override
  String get errorValidationFailed =>
      'Kuch details sahi nahi lag rahi. Ek baar dekh kar phir try karo.';

  @override
  String get errorAuthRequired => 'Continue karne ke liye sign in karo.';

  @override
  String get errorAuthExpired => 'Session ko ek chhota refresh chahiye.';

  @override
  String get errorAuthInvalid => 'Please dobara sign in karo.';

  @override
  String get errorOtpInvalid => 'Code match nahi hua. Ek baar aur try karo.';

  @override
  String get errorOtpExpired => 'Yeh code ab valid nahi hai. Naya code mangao.';

  @override
  String get errorRateLimited =>
      'Thoda tez ho gaya. Ek pal baad phir try karo.';

  @override
  String get errorOtpRateLimited =>
      'Bahut saare codes maange gaye. Thoda wait karo.';

  @override
  String get errorInternal =>
      'Hamari taraf se kuch gadbad hui. Please phir try karo.';

  @override
  String get errorUnknown => 'Yeh kaam nahi kiya. Please phir try karo.';

  @override
  String get reasonNotBlank => 'Yeh khaali nahi ho sakta.';

  @override
  String get reasonNotNull => 'Yeh zaroori hai.';

  @override
  String get reasonMin => 'Yeh bahut chhota hai.';

  @override
  String get reasonSize => 'Yeh bahut lamba hai.';

  @override
  String get reasonPhoneInvalid =>
      'Yeh Indian mobile number jaisa nahi lag raha.';

  @override
  String get reasonEmailInvalid => 'Yeh email address jaisa nahi lag raha.';

  @override
  String get reasonIdentifierRequired =>
      'Continue karne ke liye apna email likho.';

  @override
  String get reasonIdentifierOneOnly =>
      'Email ya phone mein se ek chuno, dono nahi.';

  @override
  String get reasonChannelUnavailable =>
      'Phone se sign in abhi available nahi hai — apna email use karo.';

  @override
  String get reasonCodeDigits => 'Code 6 digits ka hota hai.';

  @override
  String get reasonBodyMalformed =>
      'App ne kuch aisa bheja jo server padh nahi paaya. Please app update karo.';

  @override
  String get reasonContentTypeUnsupported =>
      'App ne kuch aisa bheja jo server padh nahi paaya. Please app update karo.';

  @override
  String get reasonUnknown => 'Ise ek baar dekh kar phir try karo.';

  @override
  String get todayPlaceholderTitle => 'Aap andar aa gaye.';

  @override
  String todayPlaceholderBody(String identifier) {
    return '$identifier ke roop mein signed in ho.';
  }

  @override
  String get todayPlaceholderNote =>
      'Aapka onboarding aur pehla plan agle build mein aayega. Abhi yahan aur kuch nahi karna.';
}
