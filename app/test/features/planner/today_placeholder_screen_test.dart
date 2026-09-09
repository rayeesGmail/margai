import 'package:flutter_test/flutter_test.dart';
import 'package:margai/core/api/api_failure.dart';
import 'package:margai/core/auth/token_store.dart';
import 'package:margai/features/auth/screens/splash_screen.dart';
import 'package:margai/features/planner/screens/today_placeholder_screen.dart';

import '../../support/fake_account_repository.dart';
import '../../support/fake_auth_repository.dart';
import '../../support/pump.dart';

void main() {
  for (final locale in allLocales) {
    final l10n = copyFor(locale);

    testWidgets('Today placeholder in ${locale.toLanguageTag()} names the signed-in identity', (
      tester,
    ) async {
      final store = InMemoryTokenStore();
      await store.write(FakeAuthRepository.signedIn.toSession());

      await pumpScreen(
        tester,
        const TodayPlaceholderScreen(),
        locale: locale,
        store: store,
      );

      expect(find.text(l10n.todayPlaceholderTitle), findsOneWidget);
      expect(
        find.text(l10n.todayPlaceholderBody('founder@example.com')),
        findsOneWidget,
      );
      expect(find.text(l10n.todayPlaceholderNote), findsOneWidget);
      expect(find.byTooltip(l10n.profileOpenButton), findsOneWidget);
      expect(find.text(l10n.failureOffline), findsNothing);
    });

    testWidgets('Today in ${locale.toLanguageTag()} shows a /me that could not be fetched, with Retry', (
      tester,
    ) async {
      final store = InMemoryTokenStore();
      await store.write(FakeAuthRepository.signedIn.toSession());
      final accounts = FakeAccountRepository()
        ..onGetMe(const ApiFailure.offline())
        ..onGetMe(FakeAccountRepository.me);

      await pumpScreen(
        tester,
        const TodayPlaceholderScreen(),
        locale: locale,
        store: store,
        accounts: accounts,
      );

      expect(find.text(l10n.failureOffline), findsOneWidget);
      await tester.tap(find.text(l10n.retryButton));
      await tester.pumpAndSettle();
      expect(find.text(l10n.failureOffline), findsNothing);
      expect(accounts.gets, 2);
    });

    testWidgets('Splash in ${locale.toLanguageTag()} shows the brand', (tester) async {
      await pumpScreen(tester, const SplashScreen(), locale: locale);
      expect(find.text(l10n.appTitle), findsOneWidget);
    });
  }
}
