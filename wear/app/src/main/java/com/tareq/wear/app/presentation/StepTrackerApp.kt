package com.tareq.wear.app.presentation

import android.app.Application
import com.tareq.core.connectivity.data.di.coreConnectivityDataModule
import com.tareq.wear.app.presentation.di.appModule
import com.tareq.wear.run.data.di.wearRunDataModule
import com.tareq.wear.run.presentation.di.wearRunPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StepTrackerApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StepTrackerApp)
            modules(
                appModule,
                wearRunPresentationModule,
                wearRunDataModule,
                coreConnectivityDataModule
            )
        }
    }
}