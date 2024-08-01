package com.tareq.wear.run.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tareq.wear.run.domain.ExerciseTracker
import com.tareq.wear.run.domain.PhoneConnector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TrackerViewModel(
    private val exerciseTracker: ExerciseTracker,
    private val phoneConnector: PhoneConnector
) : ViewModel() {

    var state by mutableStateOf(TrackerState())
        private set

    private val hasBodySensorPermission = MutableStateFlow(false)

    init {
        phoneConnector
            .connectedDevice
            .filterNotNull()
            .onEach { connectNode ->
                state = state.copy(isConnectedPhoneNearby = connectNode.isNearby)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: TrackerAction) {
        when (action) {
            is TrackerAction.OnBodySensorPermissionResult -> {
                hasBodySensorPermission.value = action.isGranted
                if (action.isGranted) {
                    viewModelScope.launch {
                        val isHeartRateTrackingSupported =
                            exerciseTracker.isHeartRateTrackingSupported()
                        state = state.copy(
                            canTrackHeartRate = isHeartRateTrackingSupported
                        )
                    }
                }
            }

            TrackerAction.OnFinishRunClick -> Unit
            TrackerAction.OnToggleRunClick -> Unit
        }
    }
}