package com.tareq.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackButtonClick : AnalyticsAction
}