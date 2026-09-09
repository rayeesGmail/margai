import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/widgets/failure_line.dart';
import 'package:margai/l10n/app_localizations.dart';

import '../../support/pump.dart';

void main() {
  Future<void> pumpLine(
    WidgetTester tester,
    Locale locale,
    ApiFailure failure, {
    VoidCallback? onRetry,
  }) async {
    await tester.pumpWidget(
      MaterialApp(
        locale: locale,
        localizationsDelegates: AppLocalizations.localizationsDelegates,
        supportedLocales: AppLocalizations.supportedLocales,
        home: Scaffold(body: FailureLine(failure: failure, onRetry: onRetry)),
      ),
    );
    await tester.pumpAndSettle();
  }

  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    group('FailureLine in ${locale.toLanguageTag()}', () {
      testWidgets('INTERNAL shows the server copy and the request reference', (
        tester,
      ) async {
        await pumpLine(
          tester,
          locale,
          const ApiFailure(
            code: 'INTERNAL',
            status: 500,
            messageUser: 'server copy',
            details: {'request_id': 'req-123'},
          ),
        );
        expect(find.text('server copy'), findsOneWidget);
        expect(find.text(l10n.failureRequestId('req-123')), findsOneWidget);
        expect(find.text(l10n.retryButton), findsNothing);
      });

      testWidgets('INTERNAL without server copy falls back to the ARB line', (
        tester,
      ) async {
        await pumpLine(
          tester,
          locale,
          const ApiFailure(code: 'INTERNAL', status: 500),
        );
        expect(find.text(l10n.errorInternal), findsOneWidget);
      });

      testWidgets('offline with a retry callback shows the Retry action', (
        tester,
      ) async {
        var retried = 0;
        await pumpLine(
          tester,
          locale,
          const ApiFailure.offline(),
          onRetry: () => retried++,
        );
        expect(find.text(l10n.failureOffline), findsOneWidget);
        await tester.tap(find.text(l10n.retryButton));
        expect(retried, 1);
      });

      testWidgets('malformed answer uses the ARB line', (tester) async {
        await pumpLine(tester, locale, const ApiFailure.malformed(502));
        expect(find.text(l10n.failureMalformed), findsOneWidget);
      });
    });
  }
}
