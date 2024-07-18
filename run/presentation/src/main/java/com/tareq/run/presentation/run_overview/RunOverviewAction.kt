package com.tareq.run.presentation.run_overview

sealed interface RunOverviewAction {
    data object OnClickRun: RunOverviewAction
    data object OnLogoutClicked: RunOverviewAction
    data object OnAnalyticsClicked: RunOverviewAction
}