package com.tareq.wear.run.presentation

sealed interface TrackerEvent {
    data object RunFinished: TrackerEvent
}