package com.tareq.core.database.di

import androidx.room.Room
import com.tareq.core.database.RoomLocalRunDataSource
import com.tareq.core.database.RunDatabase
import com.tareq.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            RunDatabase::class.java,
            "run.db"
        ).build()
    }
    single { get<RunDatabase>().runDao }
    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}