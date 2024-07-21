package com.tareq.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tareq.core.domain.run.RunRepository


class FetchRunsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val runRepository: RunRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        return when (val result = runRepository.fetchRuns()) {
            is com.tareq.core.domain.util.Result.Error -> result.error.toWorkResult()
            is com.tareq.core.domain.util.Result.Success -> Result.success()
        }
    }
}