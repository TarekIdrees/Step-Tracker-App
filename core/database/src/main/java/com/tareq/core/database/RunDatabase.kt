package com.tareq.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tareq.core.database.dao.AnalyticsDao
import com.tareq.core.database.dao.RunDao
import com.tareq.core.database.dao.RunPendingSyncDao
import com.tareq.core.database.entity.DeletedRunSyncEntity
import com.tareq.core.database.entity.RunEntity
import com.tareq.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        DeletedRunSyncEntity::class,
        RunPendingSyncEntity::class
    ],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
    abstract val analyticsDao: AnalyticsDao
}