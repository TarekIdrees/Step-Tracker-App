package com.tareq.run.domain

import com.tareq.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {

    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}