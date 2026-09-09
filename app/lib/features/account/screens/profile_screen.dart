import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/api/api_failure.dart';
import '../../../core/auth/auth_state.dart';
import '../../../core/l10n/language_mapper.dart';
import '../../../core/theme/app_theme.dart';
import '../../../core/widgets/failure_line.dart';
import '../../../core/widgets/one_hand_page.dart';
import '../../../l10n/app_localizations.dart';
import '../providers.dart';

/// SPEC §8 screen 13, Profile & settings, at D10: the language switch of §6.11 and logout. Only
/// what exists is offered (SPEC §1 principle 1) — target, hours, subscription, export and
/// deletion arrive with their days (D25, D61–D64). Renders the stored session, [SettingsState]
/// and `meProvider`, and dispatches intents; every rule lives in [SettingsNotifier]
/// (`.claude/rules/app.md`: no logic in widgets).
class ProfileScreen extends ConsumerWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final l10n = AppLocalizations.of(context);
    final auth = ref.watch(authStateProvider).value;
    final user = auth is SignedIn ? auth.user : null;
    final settings = ref.watch(settingsProvider);
    final notifier = ref.read(settingsProvider.notifier);
    final me = ref.watch(meProvider);
    final fetchFailure = me.error;
    final failure = settings.failure;

    return OneHandPage(
      appBar: AppBar(title: Text(l10n.profileTitle)),
      primaryAction: FilledButton(
        onPressed: settings.busy ? null : notifier.logout,
        child: Text(settings.busy ? l10n.loggingOut : l10n.logoutButton),
      ),
      children: [
        Text(l10n.signedInAs(user?.identifier ?? '')),
        if (fetchFailure is ApiFailure)
          Padding(
            padding: const EdgeInsets.only(top: AppSpacing.md),
            child: FailureLine(
              failure: fetchFailure,
              // One Retry rule for every screen (D8/D9): only after a failure that never
              // produced a server answer.
              onRetry: fetchFailure.isEnvelope
                  ? null
                  : () => ref.read(meProvider.notifier).reload(),
            ),
          ),
        const SizedBox(height: AppSpacing.lg),
        Text(
          l10n.languageLabel,
          style: Theme.of(context).textTheme.titleMedium,
        ),
        const SizedBox(height: AppSpacing.xs),
        Text(
          l10n.languageNote,
          style: Theme.of(context).textTheme.bodySmall,
        ),
        const SizedBox(height: AppSpacing.sm),
        RadioGroup<AppLanguage>(
          groupValue: user?.language,
          onChanged: (language) {
            if (language != null) {
              notifier.setLanguage(language);
            }
          },
          child: Column(
            children: [
              for (final language in AppLanguage.values)
                RadioListTile<AppLanguage>(
                  value: language,
                  enabled: !settings.busy,
                  title: Text(_label(l10n, language)),
                ),
            ],
          ),
        ),
        if (settings.busy)
          const Padding(
            padding: EdgeInsets.only(top: AppSpacing.md),
            child: LinearProgressIndicator(),
          ),
        if (failure != null)
          Padding(
            padding: const EdgeInsets.only(top: AppSpacing.md),
            child: FailureLine(
              failure: failure,
              onRetry: settings.canRetry ? notifier.retry : null,
            ),
          ),
        const SizedBox(height: AppSpacing.lg),
        Text(
          l10n.profileNote,
          style: Theme.of(context).textTheme.bodySmall,
        ),
      ],
    );
  }

  /// Each option in its own language, whatever the current locale (DECISIONS D10).
  static String _label(AppLocalizations l10n, AppLanguage language) =>
      switch (language) {
        AppLanguage.en => l10n.languageEnglish,
        AppLanguage.hi => l10n.languageHindi,
        AppLanguage.hinglish => l10n.languageHinglish,
      };
}
