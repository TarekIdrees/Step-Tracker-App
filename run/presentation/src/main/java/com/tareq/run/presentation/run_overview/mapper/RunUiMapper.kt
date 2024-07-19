package com.tareq.run.presentation.run_overview.mapper

import com.tareq.core.domain.run.Run
import com.tareq.core.presentation.ui.formatted
import com.tareq.core.presentation.ui.toFormattedKm
import com.tareq.core.presentation.ui.toFormattedKmh
import com.tareq.core.presentation.ui.toFormattedMeters
import com.tareq.core.presentation.ui.toFormattedPace
import com.tareq.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {

    val dateTimeInLocalTime = dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)
    val distanceInKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceInKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceInKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}