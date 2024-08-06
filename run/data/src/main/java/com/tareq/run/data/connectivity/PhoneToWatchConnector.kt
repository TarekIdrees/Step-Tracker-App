@file:OptIn(ExperimentalCoroutinesApi::class)

package com.tareq.run.data.connectivity

import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.DeviceType
import com.tareq.core.connectivity.domain.NodeDiscovery
import com.tareq.core.connectivity.domain.messaging.MessagingAction
import com.tareq.core.connectivity.domain.messaging.MessagingClient
import com.tareq.core.connectivity.domain.messaging.MessagingError
import com.tareq.core.domain.util.EmptyResult
import com.tareq.run.domain.WatchConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class PhoneToWatchConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope,
    private val messagingClient: MessagingClient
) : WatchConnector {

    private val _connectedDevice = MutableStateFlow<DeviceNode?>(null)
    override val connectedDevice = _connectedDevice.asStateFlow()

    private val isTrackable = MutableStateFlow(false)

    override fun setIsDeviceTrackable(isTrackable: Boolean) {
        this.isTrackable.value = isTrackable
    }

    override val messagingActions = nodeDiscovery
        .observeConnectedDevices(DeviceType.PHONE)
        .flatMapLatest { connectedDevices ->
            val node = connectedDevices.firstOrNull()
            if (node != null && node.isNearby) {
                _connectedDevice.value = node
                messagingClient.connectToNode(node.id)
            } else flowOf()
        }
        .onEach { action ->
            if (action == MessagingAction.ConnectionRequest) {
                if (isTrackable.value) {
                    sendActionToWatch(MessagingAction.Trackable)
                } else {
                    sendActionToWatch(MessagingAction.Untrackable)
                }
            }
        }
        .shareIn(
            applicationScope,
            SharingStarted.Eagerly
        )

    init {
        _connectedDevice
            .filterNotNull()
            .flatMapLatest { isTrackable }
            .onEach { isTrackable ->
                sendActionToWatch(MessagingAction.ConnectionRequest)
                val action = if (isTrackable) {
                    MessagingAction.Trackable
                } else MessagingAction.Untrackable
                sendActionToWatch(action)
            }
            .launchIn(applicationScope)
    }

    override suspend fun sendActionToWatch(action: MessagingAction): EmptyResult<MessagingError> {
        return messagingClient.sendOrQueueAction(action)
    }
}