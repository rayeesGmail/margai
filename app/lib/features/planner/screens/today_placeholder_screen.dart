import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/auth/auth_state.dart';
import '../../../core/theme/app_theme.dart';
import '../../../core/widgets/one_hand_page.dart';
import '../../../l10n/app_localizations.dart';

/// Holds the slot of SPEC §8 screen 7 (Today, owner `planner`, TECH_PLAN §5.8) until D29 builds
/// it: the signed-in landing the router needs (DECISIONS D8). Says only what the student's own
/// data backs (SPEC §1 principle 1): who they are signed in as.
class TodayPlaceholderScreen extends ConsumerWidget {
  const TodayPlaceholderScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final l10n = AppLocalizations.of(context);
    final auth = ref.watch(authStateProvider).value;
    final identifier = auth is SignedIn ? auth.user.identifier : '';

    return OneHandPage(
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
      ],
    );
  }
}
