package com.tareq.core.data.di

import com.tareq.core.data.auth.EncryptedSessionStorage
import com.tareq.core.data.networking.HttpClientFactory
import com.tareq.core.data.run.OfflineFirstRunRepository
import com.tareq.core.database.RoomLocalRunDataSource
import com.tareq.core.domain.SessionStorage
import com.tareq.core.domain.run.LocalRunDataSource
import com.tareq.core.domain.run.RemoteRunDataSource
import com.tareq.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDateModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}