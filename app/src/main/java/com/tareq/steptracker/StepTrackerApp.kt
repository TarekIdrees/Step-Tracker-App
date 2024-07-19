package com.tareq.steptracker

import android.app.Application
import com.tareq.auth.data.di.authDataModule
import com.tareq.auth.presentation.di.authViewModelModule
import com.tareq.core.data.di.coreDateModule
import com.tareq.core.database.di.databaseModule
import com.tareq.run.location.di.locationModule
import com.tareq.run.presentation.di.runPresentationModule
import com.tareq.steptracker.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class StepTrackerApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(this@StepTrackerApp)
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                coreDateModule,
                runPresentationModule,
                locationModule,
                databaseModule
            )
        }
    }
}