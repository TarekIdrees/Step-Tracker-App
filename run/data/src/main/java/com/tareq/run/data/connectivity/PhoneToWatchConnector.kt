package com.tareq.run.data.connectivity

import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.DeviceType
import com.tareq.core.connectivity.domain.NodeDiscovery
import com.tareq.run.domain.WatchConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PhoneToWatchConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope,
) : WatchConnector {

    private val _connectedDevice = MutableStateFlow<DeviceNode?>(null)
    override val connectedDevice = _connectedDevice.asStateFlow()

    private val isTrackable = MutableStateFlow(false)

    override fun setIsDeviceTrackable(isTrackable: Boolean) {
        this.isTrackable.value = isTrackable
    }

    val messagingActions = nodeDiscovery
        .observeConnectedDevices(DeviceType.PHONE)
        .onEach { connectedNodes ->
            val node = connectedNodes.firstOrNull()
            if (node != null && node.isNearby) {
                _connectedDevice.value = node
            }
        }
        .launchIn(applicationScope)
}