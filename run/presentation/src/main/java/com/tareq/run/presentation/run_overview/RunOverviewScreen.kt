@file:OptIn(ExperimentalMaterial3Api::class)

package com.tareq.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.core.presentation.designsystem.AnalyticsIcon
import com.tareq.core.presentation.designsystem.LogoIcon
import com.tareq.core.presentation.designsystem.LogoutIcon
import com.tareq.core.presentation.designsystem.RunIcon
import com.tareq.core.presentation.designsystem.StepTrackerTheme
import com.tareq.core.presentation.designsystem.components.StepTrackerFloatingActionButton
import com.tareq.core.presentation.designsystem.components.StepTrackerScaffold
import com.tareq.core.presentation.designsystem.components.StepTrackerToolbar
import com.tareq.core.presentation.designsystem.components.util.DropDownItem
import com.tareq.run.presentation.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun RunOverviewScreenRoot(
    onStartRunClick: () -> Unit,
    viewModel: RunOverviewViewModel = koinViewModel(),
) {
    RunOverviewScreen(
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnClickRun -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    StepTrackerScaffold(
        topAppBar = {
            StepTrackerToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.step_tracker),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClicked)
                        1 -> onAction(RunOverviewAction.OnLogoutClicked)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            StepTrackerFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnClickRun)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverViewScreenPreview() {
    StepTrackerTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}