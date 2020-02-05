package com.example.movietmdb.di

import android.app.Application
import androidx.room.Room
import com.example.movietmdb.repository.db.AppDatabase
import com.example.movietmdb.repository.db.DAO.MovieDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "fav_movies").build()
    }

    fun provideDAO(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDAO(database = get()) }
}