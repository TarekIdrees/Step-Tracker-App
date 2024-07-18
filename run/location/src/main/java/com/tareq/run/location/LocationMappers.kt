package com.tareq.run.location

import android.location.Location
import com.tareq.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.tareq.core.domain.location.Location(
            latitude = latitude,
            longitude = longitude,
        ),
        altitude = altitude,
    )
}