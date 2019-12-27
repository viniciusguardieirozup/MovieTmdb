package com.example.movietmdb

import android.app.Application
import androidx.room.Room
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.database.AppDatabase
import com.example.movietmdb.retrofit.GenresList
import com.example.movietmdb.retrofit.RetrofitInitializer
import kotlinx.coroutines.launch

class MovieTmdbApplication : Application() {

    companion object {
        lateinit var db: AppDatabase

    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "fav_movies").build()

    }
}