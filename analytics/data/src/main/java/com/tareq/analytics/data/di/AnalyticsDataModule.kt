package com.tareq.analytics.data.di


import com.tareq.analytics.data.RoomAnalyticsRepository
import com.tareq.analytics.domain.AnalyticsRepository
import com.tareq.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}