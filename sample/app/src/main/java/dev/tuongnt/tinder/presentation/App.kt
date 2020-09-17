package dev.tuongnt.tinder.presentation

import android.app.Application
import dev.tuongnt.tinder.BuildConfig
import dev.tuongnt.tinder.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) AndroidLogger() else EmptyLogger()
            androidContext(this@App)
            modules(listOf(appModule, networkModule, databaseModule, dataModule, useCaseModule))
        }
    }
}