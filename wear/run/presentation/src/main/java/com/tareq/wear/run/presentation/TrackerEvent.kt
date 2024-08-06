package com.tareq.wear.run.presentation

import com.tareq.core.presentation.ui.UiText

sealed interface TrackerEvent {
    data object RunFinished: TrackerEvent
    data class Error(val message: UiText): TrackerEvent
}