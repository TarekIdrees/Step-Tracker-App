package com.tareq.core.database

import android.database.sqlite.SQLiteFullException
import com.tareq.core.database.dao.RunDao
import com.tareq.core.database.mapper.toRun
import com.tareq.core.database.mapper.toRunEntity
import com.tareq.core.domain.run.LocalRunDataSource
import com.tareq.core.domain.run.Run
import com.tareq.core.domain.run.RunId
import com.tareq.core.domain.util.DataError
import com.tareq.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
) : LocalRunDataSource {
    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns()
            .map { runEntities ->
                runEntities.map { runEntity -> runEntity.toRun() }
            }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val runEntity = run.toRunEntity()
            runDao.upsertRun(runEntity)
            Result.Success(runEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val runEntities = runs.map { run -> run.toRunEntity() }
            runDao.upsertRuns(runEntities)
            Result.Success(runEntities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}