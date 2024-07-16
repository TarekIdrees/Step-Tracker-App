package com.tareq.core.data.di

import com.tareq.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDateModule = module {
    single {
        HttpClientFactory().build()
    }
}