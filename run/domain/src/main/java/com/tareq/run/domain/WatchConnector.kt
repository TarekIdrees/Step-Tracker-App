package com.tareq.run.domain

import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.messaging.MessagingAction
import com.tareq.core.connectivity.domain.messaging.MessagingError
import com.tareq.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WatchConnector {
    val connectedDevice: StateFlow<DeviceNode?>
    val messagingActions: Flow<MessagingAction>

    suspend fun sendActionToWatch(action: MessagingAction): EmptyResult<MessagingError>
    fun setIsDeviceTrackable(isTrackable: Boolean)
}