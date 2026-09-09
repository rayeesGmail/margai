import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/l10n/failure_copy.dart';
import '../../../core/theme/app_theme.dart';
import '../../../core/widgets/failure_line.dart';
import '../../../core/widgets/one_hand_page.dart';
import '../../../l10n/app_localizations.dart';
import '../providers.dart';

/// SPEC §8 screen 1, the entry half: the identifier — email while the SMS channel waits for the
/// DLT template (D7 ruling). Renders [LoginState] and dispatches intents; every rule lives in
/// [LoginNotifier] (`.claude/rules/app.md`: no logic in widgets).
class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  late final TextEditingController _email = TextEditingController(
    text: ref.read(loginProvider).email,
  );

  @override
  void dispose() {
    _email.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context);
    final state = ref.watch(loginProvider);
    final notifier = ref.read(loginProvider.notifier);
    final failure = state.failure;
    final emailReason = state.emailReason;

    return OneHandPage(
      primaryAction: FilledButton(
        onPressed: state.canRequest ? notifier.requestCode : null,
        child: Text(_primaryLabel(l10n, state)),
      ),
      children: [
        Text(
          l10n.loginHeadline,
          style: Theme.of(context).textTheme.headlineSmall,
        ),
        const SizedBox(height: AppSpacing.sm),
        Text(l10n.loginIntro),
        const SizedBox(height: AppSpacing.lg),
        TextField(
          controller: _email,
          enabled: !state.busy,
          keyboardType: TextInputType.emailAddress,
          autofillHints: const [AutofillHints.email],
          autocorrect: false,
          textInputAction: TextInputAction.done,
          onChanged: notifier.emailChanged,
          onSubmitted: (_) => notifier.requestCode(),
          decoration: InputDecoration(
            labelText: l10n.emailLabel,
            hintText: l10n.emailHint,
            errorText: emailReason == null
                ? null
                : FailureCopy.reason(l10n, emailReason),
          ),
        ),
        if (state.busy)
          const Padding(
            padding: EdgeInsets.only(top: AppSpacing.md),
            child: LinearProgressIndicator(),
          ),
        if (failure != null)
          Padding(
            padding: const EdgeInsets.only(top: AppSpacing.md),
            child: FailureLine(
              failure: failure,
              onRetry: state.canRetry ? notifier.retry : null,
            ),
          ),
      ],
    );
  }

  /// Sending, a pending cooldown (seconds, or whole minutes for a long wait), or the plain call.
  static String _primaryLabel(AppLocalizations l10n, LoginState state) {
    if (state.busy) {
      return l10n.sendingCode;
    }
    if (state.canRequest) {
      return l10n.sendCodeButton;
    }
    return state.longWait
        ? l10n.sendCodeInMinutes(state.resendMinutes)
        : l10n.sendCodeIn(state.resendSeconds);
  }
}
