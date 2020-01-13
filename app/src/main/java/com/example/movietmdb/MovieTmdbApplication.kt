package com.example.movietmdb

import android.app.Application
import androidx.room.Room
import com.example.movietmdb.di.viewModel
import com.example.movietmdb.repository.RepositoryRules
import com.example.movietmdb.repository.db.AppDatabase
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class MovieTmdbApplication : Application() {

    companion object {
        lateinit var db: AppDatabase
        var repository = RepositoryRules()

    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "fav_movies").build()
        startKoin(this, listOf(viewModel))
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()

    }
}