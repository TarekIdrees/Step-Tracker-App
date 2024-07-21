package com.tareq.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tareq.core.database.dao.RunPendingSyncDao
import com.tareq.core.domain.run.RemoteRunDataSource

class DeleteRunsWorker(
    context: Context,
    private val workerParameters: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val runId = workerParameters.inputData.getString(RUN_ID) ?: return Result.failure()

        return when (val result = remoteRunDataSource.deleteRun(runId)) {
            is com.tareq.core.domain.util.Result.Error -> result.error.toWorkResult()
            is com.tareq.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteDeletedRunPendingSyncEntity(runId)
                Result.success()
            }
        }
    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }
}