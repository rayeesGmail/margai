import 'package:flutter/material.dart';

import '../../l10n/app_localizations.dart';
import '../api/api_failure.dart';
import '../l10n/failure_copy.dart';
import '../theme/app_theme.dart';

/// One failure, honestly (TECH_PLAN §5.1 shared widgets): the copy from [FailureCopy], the
/// request reference on an `INTERNAL`, and a Retry action when the caller offers one (only after
/// a failure that never reached the server).
class FailureLine extends StatelessWidget {
  const FailureLine({required this.failure, this.onRetry, super.key});

  final ApiFailure failure;
  final VoidCallback? onRetry;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context);
    final scheme = Theme.of(context).colorScheme;
    final requestId = failure.requestId;
    return Container(
      padding: const EdgeInsets.all(AppSpacing.md),
      decoration: BoxDecoration(
        color: scheme.errorContainer,
        borderRadius: BorderRadius.circular(AppSpacing.sm),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            FailureCopy.message(l10n, failure),
            style: TextStyle(color: scheme.onErrorContainer),
          ),
          if (requestId != null)
            Padding(
              padding: const EdgeInsets.only(top: AppSpacing.xs),
              child: Text(
                l10n.failureRequestId(requestId),
                style: Theme.of(context).textTheme.bodySmall?.copyWith(
                  color: scheme.onErrorContainer,
                ),
              ),
            ),
          if (onRetry != null)
            Align(
              alignment: Alignment.centerRight,
              child: TextButton(
                onPressed: onRetry,
                child: Text(l10n.retryButton),
              ),
            ),
        ],
      ),
    );
  }
}
