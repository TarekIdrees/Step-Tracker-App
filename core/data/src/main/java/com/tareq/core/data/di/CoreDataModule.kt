package com.tareq.core.data.di

import com.tareq.core.data.auth.EncryptedSessionStorage
import com.tareq.core.data.networking.HttpClientFactory
import com.tareq.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDateModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}