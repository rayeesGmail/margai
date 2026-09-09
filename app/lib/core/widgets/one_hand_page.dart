import 'package:flutter/material.dart';

import '../theme/app_theme.dart';

/// The page shape every full-screen route shares (SPEC §1 principle 5, one-hand reachability):
/// scrolling content in a phone-width column, the primary action pinned to the bottom edge where
/// a thumb rests, and the keyboard never covering either.
class OneHandPage extends StatelessWidget {
  const OneHandPage({
    required this.children,
    this.primaryAction,
    this.secondaryActions = const <Widget>[],
    super.key,
  });

  final List<Widget> children;
  final Widget? primaryAction;
  final List<Widget> secondaryActions;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Center(
          child: ConstrainedBox(
            constraints: const BoxConstraints(
              maxWidth: AppLayout.maxContentWidth,
            ),
            child: Column(
              children: [
                Expanded(
                  child: ListView(
                    padding: const EdgeInsets.fromLTRB(
                      AppSpacing.lg,
                      AppSpacing.xl,
                      AppSpacing.lg,
                      AppSpacing.md,
                    ),
                    children: children,
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.fromLTRB(
                    AppSpacing.lg,
                    0,
                    AppSpacing.lg,
                    AppSpacing.md,
                  ),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      ?primaryAction,
                      if (secondaryActions.isNotEmpty)
                        Padding(
                          padding: const EdgeInsets.only(top: AppSpacing.sm),
                          child: Wrap(
                            alignment: WrapAlignment.spaceBetween,
                            spacing: AppSpacing.sm,
                            children: secondaryActions,
                          ),
                        ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
