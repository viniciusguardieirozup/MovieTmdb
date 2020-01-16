package com.example.movietmdb

import android.app.Application
import com.example.movietmdb.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MovieTmdbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MovieTmdbApplication)
            modules(
                listOf(
                    viewModelsModule,
                    databaseModule,
                    netModule,
                    apiModule,
                    repositoryModule,
                    adapter
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}