package com.tareq.wear.app.presentation

import android.app.Application
import com.tareq.wear.run.data.di.wearRunDataModule
import com.tareq.wear.run.presentation.di.runPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StepTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StepTrackerApp)
            modules(
                runPresentationModule,
                wearRunDataModule
            )
        }
    }
}