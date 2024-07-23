@file:OptIn(ExperimentalMaterial3Api::class)

package com.tareq.analytics.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.analytics.presentation.components.AnalyticsCard
import com.tareq.core.presentation.designsystem.StepTrackerTheme
import com.tareq.core.presentation.designsystem.components.StepTrackerScaffold
import com.tareq.core.presentation.designsystem.components.StepTrackerToolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnalyticsDashboardScreenRoot(
    onBackButtonClick: () -> Unit,
    viewModel: AnalyticsDashboardViewModel = koinViewModel(),
) {
    AnalyticsDashboardScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                AnalyticsAction.OnBackButtonClick -> onBackButtonClick()
            }
        }
    )
}

@Composable
private fun AnalyticsDashboardScreen(
    state: AnalyticsDashboardState?,
    onAction: (AnalyticsAction) -> Unit,
) {
    StepTrackerScaffold(
        topAppBar = {
            StepTrackerToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.analytics),
                onBackClick = {
                    onAction(AnalyticsAction.OnBackButtonClick)
                }
            )
        }
    ) { padding ->
        if (state == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    strokeWidth = 4.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnalyticsCard(
                        title = stringResource(id = R.string.total_distance_run),
                        value = state.totalDistanceRun,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    AnalyticsCard(
                        title = stringResource(id = R.string.total_time_run),
                        value = state.totalTimeRun,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnalyticsCard(
                        title = stringResource(id = R.string.fastest_ever_run),
                        value = state.fastestEverRun,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    AnalyticsCard(
                        title = stringResource(id = R.string.avg_distance_per_run),
                        value = state.avgDistance,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnalyticsCard(
                        title = stringResource(id = R.string.avg_pace_per_run),
                        value = state.avgPace,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AnalyticsDashboardScreenPreview() {
    StepTrackerTheme {
        AnalyticsDashboardScreen(
            state = AnalyticsDashboardState(
                totalDistanceRun = "0.2 km",
                totalTimeRun = "0d 0h 0m",
                fastestEverRun = "143.9 km/h",
                avgDistance = "0.2 km",
                avgPace = "07:10",
            ),
            onAction = {}
        )
    }
}