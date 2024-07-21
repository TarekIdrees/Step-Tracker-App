package com.tareq.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tareq.core.database.dao.RunPendingSyncDao
import com.tareq.core.database.mapper.toRun
import com.tareq.core.domain.run.RemoteRunDataSource

class CreateRunsWorker(
    context: Context,
    private val workerParameters: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val pendingRunId = workerParameters.inputData.getString(RUN_ID) ?: return Result.failure()

        val pendingRunsEntity = pendingSyncDao.getRunPendingSyncEntity(pendingRunId)
            ?: return Result.failure()

        val run = pendingRunsEntity.run.toRun()
        return when (val result =
            remoteRunDataSource.postRun(run, pendingRunsEntity.mapPictureBytes)) {
            is com.tareq.core.domain.util.Result.Error -> result.error.toWorkResult()
            is com.tareq.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteRunPendingSyncEntity(pendingRunId)
                Result.success()
            }
        }
    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }
}