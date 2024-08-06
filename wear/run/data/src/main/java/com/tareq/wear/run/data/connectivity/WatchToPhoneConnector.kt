@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tareq.wear.run.data.connectivity

import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.DeviceType
import com.tareq.core.connectivity.domain.NodeDiscovery
import com.tareq.core.connectivity.domain.messaging.MessagingAction
import com.tareq.core.connectivity.domain.messaging.MessagingClient
import com.tareq.core.connectivity.domain.messaging.MessagingError
import com.tareq.core.domain.util.EmptyResult
import com.tareq.wear.run.domain.PhoneConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn

class WatchToPhoneConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope,
    private val messagingClient: MessagingClient
) : PhoneConnector {

    private val _connectedDevice = MutableStateFlow<DeviceNode?>(null)
    override val connectedDevice = _connectedDevice.asStateFlow()

    override val messagingActions = nodeDiscovery
        .observeConnectedDevices(DeviceType.WATCH)
        .flatMapLatest { connectedNodes ->
            val node = connectedNodes.firstOrNull()
            if (node != null && node.isNearby) {
                _connectedDevice.value = node
                messagingClient.connectToNode(node.id)
            } else flowOf()
        }
        .shareIn(
            applicationScope,
            SharingStarted.Eagerly
        )

    override suspend fun sendActionToPhone(action: MessagingAction): EmptyResult<MessagingError> {
        return messagingClient.sendOrQueueAction(action)
    }
}