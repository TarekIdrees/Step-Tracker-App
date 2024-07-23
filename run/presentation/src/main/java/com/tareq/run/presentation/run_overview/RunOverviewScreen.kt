@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.tareq.run.presentation.run_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.core.presentation.designsystem.AnalyticsIcon
import com.tareq.core.presentation.designsystem.LogoIcon
import com.tareq.core.presentation.designsystem.LogoutIcon
import com.tareq.core.presentation.designsystem.RunIcon
import com.tareq.core.presentation.designsystem.StepTrackerTheme
import com.tareq.core.presentation.designsystem.components.ContentVisibilityAnimation
import com.tareq.core.presentation.designsystem.components.StepTrackerFloatingActionButton
import com.tareq.core.presentation.designsystem.components.StepTrackerScaffold
import com.tareq.core.presentation.designsystem.components.StepTrackerToolbar
import com.tareq.core.presentation.designsystem.components.util.DropDownItem
import com.tareq.run.presentation.R
import com.tareq.run.presentation.run_overview.components.EmptyRunsPlaceholder
import com.tareq.run.presentation.run_overview.components.RunListItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun RunOverviewScreenRoot(
    onStartRunClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    viewModel: RunOverviewViewModel = koinViewModel(),
) {
    RunOverviewScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RunOverviewAction.OnAnalyticsClicked -> onAnalyticsClick()
                RunOverviewAction.OnRunClicked -> onStartRunClick()
                RunOverviewAction.OnLogoutClicked -> onLogoutClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RunOverviewScreen(
    state: RunOverviewState,
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
                    onAction(RunOverviewAction.OnRunClicked)
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            ContentVisibilityAnimation(
                modifier = Modifier.align(Alignment.Center),
                state = state.isLoadingRuns
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    strokeWidth = 4.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            ContentVisibilityAnimation(
                modifier = Modifier.padding(padding),
                state = state.runs.isEmpty() && !state.isLoadingRuns
            ) {
                EmptyRunsPlaceholder()
            }

            ContentVisibilityAnimation(state = state.runs.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .padding(horizontal = 16.dp),
                    contentPadding = padding,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = state.runs,
                        key = { it.id }
                    ) {
                        RunListItem(
                            runUi = it,
                            onDeleteClick = {
                                onAction(RunOverviewAction.DeleteRun(it))
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun RunOverViewScreenPreview() {
    StepTrackerTheme {
        RunOverviewScreen(
            state = RunOverviewState(),
            onAction = {}
        )
    }
}