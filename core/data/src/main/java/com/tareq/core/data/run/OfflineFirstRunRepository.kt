package com.tareq.core.data.run

import com.tareq.core.database.dao.RunPendingSyncDao
import com.tareq.core.database.mapper.toRun
import com.tareq.core.domain.SessionStorage
import com.tareq.core.domain.run.LocalRunDataSource
import com.tareq.core.domain.run.RemoteRunDataSource
import com.tareq.core.domain.run.Run
import com.tareq.core.domain.run.RunId
import com.tareq.core.domain.run.RunRepository
import com.tareq.core.domain.run.SyncRunScheduler
import com.tareq.core.domain.util.DataError
import com.tareq.core.domain.util.EmptyResult
import com.tareq.core.domain.util.Result
import com.tareq.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineFirstRunRepository(
    private val remoteRunDataSource: RemoteRunDataSource,
    private val localRunDataSource: LocalRunDataSource,
    private val applicationScope: CoroutineScope,
    private val runPendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val syncRunScheduler: SyncRunScheduler
) : RunRepository {
    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when (val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(
            run = runWithId,
            mapPicture = mapPicture
        )

        return when (remoteResult) {
            is Result.Error -> {
                applicationScope.launch {
                    syncRunScheduler.scheduleSync(
                        syncType = SyncRunScheduler.SyncType.CreateRun(
                            run = runWithId,
                            mapPictureBytes = mapPicture
                        ),
                    )
                }.join()
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)

        // Edge case when the run is created in offline-mode,
        // and then deleted in offline-mode, in that case
        // we don't need to sync anything.
        val isPendingRunsSync = runPendingSyncDao.getRunPendingSyncEntity(id) != null
        if (isPendingRunsSync) {
            runPendingSyncDao.deleteRunPendingSyncEntity(id)
            return
        }

        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()

        if (remoteResult is Result.Error) {
            applicationScope.launch {
                syncRunScheduler.scheduleSync(
                    syncType = SyncRunScheduler.SyncType.DeleteRun(
                        runId = id
                    )
                )
            }.join()
        }
    }

    override suspend fun syncPendingRuns() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val pendingRunsCreated = async {
                runPendingSyncDao.getAllRunsPendingSyncEntities(userId)
            }
            val pendingRunsDeleted = async {
                runPendingSyncDao.getAllDeletedRunsPendingSyncEntities(userId)
            }
            val createdJobs = pendingRunsCreated
                .await()
                .map { runPendingEntity ->
                    launch {
                        val run = runPendingEntity.run.toRun()
                        when (remoteRunDataSource.postRun(run, runPendingEntity.mapPictureBytes)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteRunPendingSyncEntity(
                                        runPendingEntity.runId
                                    )
                                }
                            }
                        }
                    }
                }

            val deletedJobs = pendingRunsDeleted
                .await()
                .map { runPendingEntity ->
                    launch {
                        when (remoteRunDataSource.deleteRun(runPendingEntity.runId)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteDeletedRunPendingSyncEntity(
                                        runPendingEntity.runId
                                    )
                                }
                            }
                        }
                    }
                }

            createdJobs.forEach { it.join() }
            deletedJobs.forEach { it.join() }
        }
    }
}