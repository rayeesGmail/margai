import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/l10n/failure_copy.dart';
import '../../../core/theme/app_theme.dart';
import '../../../core/widgets/failure_line.dart';
import '../../../core/widgets/one_hand_page.dart';
import '../../../l10n/app_localizations.dart';
import '../identifiers.dart';
import '../providers.dart';

/// SPEC §8 screen 1, the code half: six digits, verify, resend after the cooldown, change the
/// identifier, and the honest failure states. SMS auto-read joins when the SMS channel does
/// (TRACKER F1); the email code is typed or pasted (Android's one-time-code autofill hint).
/// Renders [LoginState] and dispatches intents; every rule, the countdown included, is state.
class OtpScreen extends ConsumerStatefulWidget {
  const OtpScreen({super.key});

  @override
  ConsumerState<OtpScreen> createState() => _OtpScreenState();
}

class _OtpScreenState extends ConsumerState<OtpScreen> {
  final TextEditingController _code = TextEditingController();

  @override
  void dispose() {
    _code.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context);
    final state = ref.watch(loginProvider);
    final notifier = ref.read(loginProvider.notifier);
    // A new code makes the old digits stale: empty the field when the challenge changes.
    ref.listen(loginProvider.select((s) => s.challenge?.challengeId), (
      previous,
      next,
    ) {
      if (previous != null && next != null && next != previous) {
        _code.clear();
      }
    });
    final failure = state.failure;
    final codeReason = state.codeReason;
    final attemptsLeft = state.attemptsLeft;

    return OneHandPage(
      primaryAction: FilledButton(
        onPressed: state.busy || state.codeDead
            ? null
            : () => notifier.verify(_code.text),
        child: Text(state.busy ? l10n.verifying : l10n.verifyButton),
      ),
      secondaryActions: [
        TextButton(
          onPressed: state.canResend ? notifier.resend : null,
          child: Text(
            state.canResend
                ? l10n.resendButton
                : state.longWait
                ? l10n.resendInMinutes(state.resendMinutes)
                : l10n.resendIn(state.resendSeconds),
          ),
        ),
        TextButton(
          onPressed: state.busy ? null : notifier.changeEmail,
          child: Text(l10n.changeEmailButton),
        ),
      ],
      children: [
        Text(
          l10n.codeSentTitle,
          style: Theme.of(context).textTheme.headlineSmall,
        ),
        const SizedBox(height: AppSpacing.sm),
        Text(l10n.codeSentTo(state.email.trim())),
        const SizedBox(height: AppSpacing.lg),
        TextField(
          controller: _code,
          enabled: !state.busy && !state.codeDead,
          autofocus: true,
          keyboardType: TextInputType.number,
          autofillHints: const [AutofillHints.oneTimeCode],
          inputFormatters: [
            FilteringTextInputFormatter.digitsOnly,
            LengthLimitingTextInputFormatter(Identifiers.codeLength),
          ],
          textInputAction: TextInputAction.done,
          style: const TextStyle(fontSize: 24, letterSpacing: 8),
          onChanged: notifier.codeTyped,
          onSubmitted: notifier.verify,
          decoration: InputDecoration(
            labelText: l10n.codeLabel,
            counterText: '',
            errorText: codeReason == null
                ? null
                : FailureCopy.reason(l10n, codeReason),
          ),
        ),
        if (attemptsLeft != null)
          Padding(
            padding: const EdgeInsets.only(top: AppSpacing.sm),
            child: Text(l10n.attemptsLeft(attemptsLeft)),
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
}
