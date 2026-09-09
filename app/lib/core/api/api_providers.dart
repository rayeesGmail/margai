import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../auth/auth_state.dart';
import '../auth/session_refresher.dart';
import '../auth/token_store.dart';
import '../config/app_config.dart';
import '../config/app_version.dart';
import '../l10n/language_mapper.dart';
import 'api_client.dart';

/// The network layer under the client: `null` means dio's own (the real one). Tests override it
/// with a scripted adapter so the real providers can be exercised without a server.
final apiAdapterProvider = Provider<HttpClientAdapter?>((ref) => null);

/// The shared [ApiClient] (TECH_PLAN §5.4): base URL from the build config, the app version
/// header, `Accept-Language` from the current locale, the bearer read from the token store on
/// each call, the single-flight refresh on `AUTH_EXPIRED` / `AUTH_INVALID`, and the sign-out when
/// a fresh token is still refused (D10). The refresher is read lazily inside the handler — it
/// depends on the auth repository, which depends on this client.
final Provider<ApiClient> apiClientProvider = Provider<ApiClient>((ref) {
  final config = ref.watch(appConfigProvider);
  return ApiClient(
    baseUrl: config.apiBaseUrl,
    appVersion: ref.watch(appVersionProvider),
    acceptLanguage: () => ref.read(appLanguageProvider).acceptLanguage,
    bearer: () async => (await ref.read(tokenStoreProvider).read())?.accessToken,
    onAuthExpired: () => ref.read(sessionRefresherProvider).refresh(),
    onSessionLost: () => ref.read(authStateProvider.notifier).signOut(),
    adapter: ref.watch(apiAdapterProvider),
  );
});
