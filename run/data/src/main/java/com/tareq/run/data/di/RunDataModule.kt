package com.tareq.run.data.di

import com.tareq.core.domain.run.SyncRunScheduler
import com.tareq.run.data.CreateRunsWorker
import com.tareq.run.data.DeleteRunsWorker
import com.tareq.run.data.FetchRunsWorker
import com.tareq.run.data.SyncRunWorkerScheduler
import com.tareq.run.data.connectivity.PhoneToWatchConnector
import com.tareq.run.domain.WatchConnector
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunsWorker)
    workerOf(::DeleteRunsWorker)
    workerOf(::FetchRunsWorker)
    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
    singleOf(::PhoneToWatchConnector).bind<WatchConnector>()
}