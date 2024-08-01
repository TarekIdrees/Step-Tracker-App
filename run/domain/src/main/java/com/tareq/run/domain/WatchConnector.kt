package com.tareq.run.domain

import com.tareq.core.connectivity.domain.DeviceNode
import kotlinx.coroutines.flow.StateFlow

interface WatchConnector {
    val connectedDevice: StateFlow<DeviceNode?>
    fun setIsDeviceTrackable(isTrackable: Boolean)
}