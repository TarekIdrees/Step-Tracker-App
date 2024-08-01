package com.tareq.core.connectivity.domain

import kotlinx.coroutines.flow.Flow

interface NodeDiscovery {

    fun observeConnectedDevices(localDevice: DeviceType): Flow<Set<DeviceNode>>
}