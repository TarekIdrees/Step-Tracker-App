package com.tareq.core.connectivity.data

import android.content.Context
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.Wearable
import com.tareq.core.connectivity.domain.DeviceNode
import com.tareq.core.connectivity.domain.DeviceType
import com.tareq.core.connectivity.domain.NodeDiscovery
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class WearNodeDiscovery(
    context: Context
) : NodeDiscovery {

    private val capabilityClient = Wearable.getCapabilityClient(context)

    override fun observeConnectedDevices(localDevice: DeviceType): Flow<Set<DeviceNode>> {
        return callbackFlow {
            val remoteCapability = when (localDevice) {
                DeviceType.WATCH -> "steptracker_phone_app"
                DeviceType.PHONE -> "steptracker_wear_app"
            }

            try {
                val capability = capabilityClient
                    .getCapability(remoteCapability, CapabilityClient.FILTER_REACHABLE)
                    .await()
                val connectedDevices = capability.nodes.map { it.toDeviceNode() }.toSet()
                send(connectedDevices)
            } catch (e: ApiException) {
                awaitClose()
                return@callbackFlow
            }

            val listener: (CapabilityInfo) -> Unit = {
                trySend(it.nodes.map { node -> node.toDeviceNode() }.toSet())
            }
            capabilityClient.addListener(listener, remoteCapability)

            awaitClose {
                capabilityClient.removeListener(listener)
            }
        }
    }
}