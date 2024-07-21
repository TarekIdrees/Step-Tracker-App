package com.tareq.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Entity for scenario when user delete a run without sync it
 * The solution for this scenario will be before fetch the remote data
 * we have to make sure that the API knows about all runs that have been deleted locally.
 */
@Entity
data class DeletedRunSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val runId: String,
    val userId: String
)
