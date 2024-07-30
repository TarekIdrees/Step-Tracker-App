package com.tareq.wear.run.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.health.services.client.ExerciseUpdateCallback
import androidx.health.services.client.HealthServices
import androidx.health.services.client.HealthServicesException
import androidx.health.services.client.clearUpdateCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.ExerciseConfig
import androidx.health.services.client.data.ExerciseLapSummary
import androidx.health.services.client.data.ExerciseTrackedStatus
import androidx.health.services.client.data.ExerciseType
import androidx.health.services.client.data.ExerciseUpdate
import androidx.health.services.client.data.WarmUpConfig
import androidx.health.services.client.endExercise
import androidx.health.services.client.getCapabilities
import androidx.health.services.client.getCurrentExerciseInfo
import androidx.health.services.client.pauseExercise
import androidx.health.services.client.prepareExercise
import androidx.health.services.client.resumeExercise
import androidx.health.services.client.startExercise
import com.tareq.core.domain.util.EmptyResult
import com.tareq.core.domain.util.Result
import com.tareq.wear.run.domain.ExerciseError
import com.tareq.wear.run.domain.ExerciseTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class HealthServicesExerciseTracker(
    private val context: Context
) : ExerciseTracker {

    private val client = HealthServices.getClient(context).exerciseClient

    override val heartRate: Flow<Int>
        get() = callbackFlow {
            val callback = object : ExerciseUpdateCallback {
                override fun onAvailabilityChanged(
                    dataType: DataType<*, *>,
                    availability: Availability
                ) = Unit

                override fun onExerciseUpdateReceived(update: ExerciseUpdate) {
                    val heartRates = update.latestMetrics.getData(DataType.HEART_RATE_BPM)
                    val currentHeartRate = heartRates.firstOrNull()?.value

                    currentHeartRate?.let {
                        trySend(currentHeartRate.roundToInt())
                    }
                }

                override fun onLapSummaryReceived(lapSummary: ExerciseLapSummary) = Unit

                override fun onRegistered() = Unit

                override fun onRegistrationFailed(throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
            client.setUpdateCallback(callback)

            awaitClose {
                runBlocking {
                    client.clearUpdateCallback(callback)
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun isHeartRateTrackingSupported(): Boolean {
        return hasBodySensorsPermission() && runCatching {
            val capabilities = client.getCapabilities()
            val supportedDataTypes = capabilities
                .typeToCapabilities[ExerciseType.RUNNING]
                ?.supportedDataTypes ?: setOf()

            DataType.HEART_RATE_BPM in supportedDataTypes

        }.getOrDefault(false)
    }

    override suspend fun prepareExercise(): EmptyResult<ExerciseError> {
        val exerciseEnvironmentResult = checkExerciseEnvironment()
        if (exerciseEnvironmentResult is Result.Error) {
            return exerciseEnvironmentResult
        }

        val config = WarmUpConfig(
            exerciseType = ExerciseType.RUNNING,
            dataTypes = setOf(DataType.HEART_RATE_BPM)
        )
        client.prepareExercise(config)
        return Result.Success(Unit)
    }


    override suspend fun startExercise(): EmptyResult<ExerciseError> {
        val exerciseEnvironmentResult = checkExerciseEnvironment()
        if (exerciseEnvironmentResult is Result.Error) {
            return exerciseEnvironmentResult
        }

        val config = ExerciseConfig
            .Builder(ExerciseType.RUNNING)
            .setDataTypes(setOf(DataType.HEART_RATE_BPM))
            .setIsAutoPauseAndResumeEnabled(false)
            .build()

        client.startExercise(config)
        return Result.Success(Unit)
    }

    override suspend fun resumeExercise(): EmptyResult<ExerciseError> {
        val exerciseEnvironmentResult = checkExerciseEnvironment()
        if (exerciseEnvironmentResult is Result.Error
            && exerciseEnvironmentResult.error == ExerciseError.ONGOING_DIFFERENT_EXERCISE
        ) {
            return exerciseEnvironmentResult
        }

        return try {
            client.resumeExercise()
            Result.Success(Unit)
        } catch (e: HealthServicesException) {
            Result.Error(ExerciseError.EXERCISE_ALREADY_ENDED)
        }
    }

    override suspend fun pauseExercise(): EmptyResult<ExerciseError> {
        val exerciseEnvironmentResult = checkExerciseEnvironment()
        if (exerciseEnvironmentResult is Result.Error
            && exerciseEnvironmentResult.error == ExerciseError.ONGOING_DIFFERENT_EXERCISE
        ) {
            return exerciseEnvironmentResult
        }

        return try {
            client.pauseExercise()
            Result.Success(Unit)
        } catch (e: HealthServicesException) {
            Result.Error(ExerciseError.EXERCISE_ALREADY_ENDED)
        }
    }

    override suspend fun stopExercise(): EmptyResult<ExerciseError> {
        val exerciseEnvironmentResult = checkExerciseEnvironment()
        if (exerciseEnvironmentResult is Result.Error
            && exerciseEnvironmentResult.error == ExerciseError.ONGOING_DIFFERENT_EXERCISE
        ) {
            return exerciseEnvironmentResult
        }

        return try {
            client.endExercise()
            Result.Success(Unit)
        } catch (e: HealthServicesException) {
            Result.Error(ExerciseError.EXERCISE_ALREADY_ENDED)
        }
    }

    private suspend fun checkExerciseEnvironment(): Result<Unit, ExerciseError> {
        if (!isHeartRateTrackingSupported()) {
            return Result.Error(ExerciseError.TRACKING_NOT_SUPPORTED)
        }

        val exerciseInfoResult = getActiveExerciseInfo()
        return if (exerciseInfoResult is Result.Error) {
            exerciseInfoResult
        } else {
            Result.Success(Unit)
        }
    }

    private fun hasBodySensorsPermission(): Boolean {
        return context.checkSelfPermission(Manifest.permission.BODY_SENSORS) ==
                PackageManager.PERMISSION_GRANTED
    }

    private suspend fun getActiveExerciseInfo(): EmptyResult<ExerciseError> {
        val info = client.getCurrentExerciseInfo()
        return when (info.exerciseTrackedStatus) {
            ExerciseTrackedStatus.NO_EXERCISE_IN_PROGRESS -> {
                Result.Success(Unit)
            }

            ExerciseTrackedStatus.OWNED_EXERCISE_IN_PROGRESS -> {
                Result.Error(ExerciseError.ONGOING_OWN_EXERCISE)
            }

            ExerciseTrackedStatus.OTHER_APP_IN_PROGRESS -> {
                Result.Error(ExerciseError.ONGOING_DIFFERENT_EXERCISE)
            }

            else -> Result.Error(ExerciseError.UNKNOWN)
        }
    }
}