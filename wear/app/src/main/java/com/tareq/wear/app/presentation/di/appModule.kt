package com.tareq.wear.app.presentation.di

import com.tareq.wear.app.presentation.StepTrackerApp
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        (androidApplication() as StepTrackerApp).applicationScope
    }
}