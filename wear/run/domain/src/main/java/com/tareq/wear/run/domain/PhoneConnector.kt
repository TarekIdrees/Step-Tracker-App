package com.tareq.wear.run.domain

import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.messaging.MessagingAction
import com.tareq.core.connectivity.domain.messaging.MessagingError
import com.tareq.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PhoneConnector {
    val connectedDevice: StateFlow<DeviceNode?>
    val messagingActions: Flow<MessagingAction>

    suspend fun sendActionToPhone(action: MessagingAction): EmptyResult<MessagingError>
}