package com.tareq.run.presentation.di

import com.tareq.run.domain.RunningTracker
import com.tareq.run.presentation.active_run.ActiveRunViewModel
import com.tareq.run.presentation.run_overview.RunOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)
    single {
        get<RunningTracker>().elapsedTime
    }
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}