package com.tareq.wear.run.data.di

import com.tareq.wear.run.data.HealthServicesExerciseTracker
import com.tareq.wear.run.data.connectivity.WatchToPhoneConnector
import com.tareq.wear.run.domain.ExerciseTracker
import com.tareq.wear.run.domain.PhoneConnector
import com.tareq.wear.run.domain.RunningTracker
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val wearRunDataModule = module {
    singleOf(::HealthServicesExerciseTracker).bind<ExerciseTracker>()
    singleOf(::WatchToPhoneConnector).bind<PhoneConnector>()
    singleOf(::RunningTracker)
    single {
        get<RunningTracker>().elapsedTime
    }
}