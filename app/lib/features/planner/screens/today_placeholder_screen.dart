import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../core/api/api_failure.dart';
import '../../../core/auth/auth_state.dart';
import '../../../core/router/app_router.dart';
import '../../../core/theme/app_theme.dart';
import '../../../core/widgets/failure_line.dart';
import '../../../core/widgets/one_hand_page.dart';
import '../../../l10n/app_localizations.dart';
import '../../account/providers.dart';

/// Holds the slot of SPEC §8 screen 7 (Today, owner `planner`, TECH_PLAN §5.8) until D29 builds
/// it: the signed-in landing the router needs (DECISIONS D8). Says only what the student's own
/// data backs (SPEC §1 principle 1): who they are signed in as. It is the one screen every start
/// reaches, so it watches `meProvider` — the one `/me` call on app start (§3.7, D10) — and shows
/// a fetch that failed honestly, with Retry. Profile & settings (screen 13) opens from the bar
/// until the bottom bar arrives (D29).
class TodayPlaceholderScreen extends ConsumerWidget {
  const TodayPlaceholderScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final l10n = AppLocalizations.of(context);
    final auth = ref.watch(authStateProvider).value;
    final identifier = auth is SignedIn ? auth.user.identifier : '';
    final me = ref.watch(meProvider);
    final fetchFailure = me.error;

    return OneHandPage(
      appBar: AppBar(
        title: Text(l10n.appTitle),
        actions: [
          IconButton(
            icon: const Icon(Icons.person_outline),
            tooltip: l10n.profileOpenButton,
            onPressed: () => context.push(AppRoutes.profile),
          ),
        ],
      ),
      children: [
        Text(
          l10n.todayPlaceholderTitle,
          style: Theme.of(context).textTheme.headlineSmall,
        ),
        const SizedBox(height: AppSpacing.sm),
        Text(l10n.todayPlaceholderBody(identifier)),
        const SizedBox(height: AppSpacing.md),
        Text(
          l10n.todayPlaceholderNote,
          style: Theme.of(context).textTheme.bodySmall,
        ),
        if (fetchFailure is ApiFailure)
          Padding(
            padding: const EdgeInsets.only(top: AppSpacing.md),
            child: FailureLine(
              failure: fetchFailure,
              onRetry: () => ref.read(meProvider.notifier).reload(),
            ),
          ),
      ],
    );
  }
}
