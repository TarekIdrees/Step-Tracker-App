package com.tareq.run.presentation.run_overview

import com.tareq.run.presentation.run_overview.model.RunUi

sealed interface RunOverviewAction {
    data object OnRunClicked: RunOverviewAction
    data object OnLogoutClicked: RunOverviewAction
    data object OnAnalyticsClicked: RunOverviewAction
    data class DeleteRun(val runUi: RunUi): RunOverviewAction
}