import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../auth/token_store.dart';
import '../config/app_config.dart';
import '../config/app_version.dart';
import '../l10n/language_mapper.dart';
import 'api_client.dart';

/// The shared [ApiClient] (TECH_PLAN §5.4): base URL from the build config, the app version
/// header, `Accept-Language` from the current locale, and the bearer read from the token store
/// on each call. Tests override this provider with a client over the fake adapter.
final apiClientProvider = Provider<ApiClient>((ref) {
  final config = ref.watch(appConfigProvider);
  return ApiClient(
    baseUrl: config.apiBaseUrl,
    appVersion: ref.watch(appVersionProvider),
    acceptLanguage: () => ref.read(appLanguageProvider).acceptLanguage,
    bearer: () async => (await ref.read(tokenStoreProvider).read())?.accessToken,
  );
});
