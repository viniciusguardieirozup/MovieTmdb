package com.example.movietmdb

import android.app.Application
import com.example.movietmdb.di.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class ApplicationTest : BaseKoinTest() {
    @RelaxedMockK
    private lateinit var application: Application

    @Test
    fun checkModules_viewModelModule() {
        koinApplication {
            androidContext(application)
            modules(
                listOf(
                    viewModelsModule,
                    databaseModule,
                    netModule,
                    apiModule,
                    repositoryModule,
                    adapter,
                    request
                )
            )
        }.checkModules()
    }
}