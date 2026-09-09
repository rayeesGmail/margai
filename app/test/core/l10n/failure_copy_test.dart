import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/l10n/failure_copy.dart';

import '../../support/pump.dart';

void main() {
  const errorCodes = <String>[
    'VALIDATION_FAILED',
    'AUTH_REQUIRED',
    'AUTH_EXPIRED',
    'AUTH_INVALID',
    'OTP_INVALID',
    'OTP_EXPIRED',
    'RATE_LIMITED',
    'OTP_RATE_LIMITED',
    'INTERNAL',
  ];

  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    group('in ${locale.toLanguageTag()}', () {
      test('every D7 reason code has its own copy (DECISIONS D7 copy rule → D8)', () {
        final seen = <String>{};
        for (final code in FailureCopy.reasonCodes) {
          final copy = FailureCopy.reason(l10n, code);
          expect(copy.trim(), isNotEmpty, reason: code);
          expect(copy, isNot(l10n.reasonUnknown), reason: '$code fell through');
          seen.add(copy);
        }
        // Two body-shape codes share a line on purpose; everything else is distinct.
        expect(seen.length, greaterThanOrEqualTo(FailureCopy.reasonCodes.length - 1));
        expect(FailureCopy.reason(l10n, 'never.heard'), l10n.reasonUnknown);
      });

      test('every login-path error code has fallback copy', () {
        for (final code in errorCodes) {
          final copy = FailureCopy.byCode(l10n, code);
          expect(copy.trim(), isNotEmpty, reason: code);
          expect(copy, isNot(l10n.errorUnknown), reason: '$code fell through');
        }
        expect(FailureCopy.byCode(l10n, 'SOMETHING_NEW'), l10n.errorUnknown);
      });

      test('the client-only codes and the plural read in this locale', () {
        expect(
          FailureCopy.message(l10n, const ApiFailure.offline()),
          l10n.failureOffline,
        );
        expect(
          FailureCopy.message(l10n, const ApiFailure.malformed(502)),
          l10n.failureMalformed,
        );
        expect(l10n.attemptsLeft(0), isNotEmpty);
        expect(l10n.attemptsLeft(1), isNot(l10n.attemptsLeft(2)));
        expect(l10n.resendIn(27), contains('27'));
      });
    });
  }

  group('precedence (TECH_PLAN §3.3, DECISIONS D8)', () {
    final l10n = copyFor(allLocales.first);

    test('the envelope\'s user-language message wins', () {
      const failure = ApiFailure(
        code: 'OTP_INVALID',
        messageEn: 'en text',
        messageUser: 'user text',
      );
      expect(FailureCopy.message(l10n, failure), 'user text');
    });

    test('then the English message, then the ARB fallback by code', () {
      expect(
        FailureCopy.message(
          l10n,
          const ApiFailure(code: 'OTP_INVALID', messageEn: 'en text'),
        ),
        'en text',
      );
      expect(
        FailureCopy.message(
          l10n,
          const ApiFailure(code: 'OTP_INVALID', messageUser: '  '),
        ),
        l10n.errorOtpInvalid,
      );
    });
  });
}
