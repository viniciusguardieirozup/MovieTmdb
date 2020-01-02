package com.example.movietmdb.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase

//database creator
@Database(entities = [MovieData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}