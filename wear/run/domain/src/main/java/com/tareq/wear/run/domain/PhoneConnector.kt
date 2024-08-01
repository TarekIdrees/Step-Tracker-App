package com.tareq.wear.run.domain

import com.tareq.core.connectivity.domain.DeviceNode
import kotlinx.coroutines.flow.StateFlow

interface PhoneConnector {
    val connectedDevice: StateFlow<DeviceNode?>
}