package com.tareq.auth.data.di

import com.tareq.auth.data.AuthRepositoryImpl
import com.tareq.auth.data.EmailPatternValidator
import com.tareq.auth.domain.AuthRepository
import com.tareq.auth.domain.PatternValidator
import com.tareq.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}