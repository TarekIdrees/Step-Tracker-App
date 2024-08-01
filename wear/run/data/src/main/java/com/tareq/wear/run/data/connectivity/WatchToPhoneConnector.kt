package com.tareq.wear.run.data.connectivity

import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.DeviceType
import com.tareq.core.connectivity.domain.NodeDiscovery
import com.tareq.wear.run.domain.PhoneConnector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WatchToPhoneConnector(
    nodeDiscovery: NodeDiscovery,
    applicationScope: CoroutineScope
) : PhoneConnector {

    private val _connectedDevice = MutableStateFlow<DeviceNode?>(null)
    override val connectedDevice = _connectedDevice.asStateFlow()

    val messagingActions = nodeDiscovery
        .observeConnectedDevices(DeviceType.WATCH)
        .onEach { connectedNodes ->
            val node = connectedNodes.firstOrNull()
            if(node != null && node.isNearby) {
                _connectedDevice.value = node
            }
        }
        .launchIn(applicationScope)
}