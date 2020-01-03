package com.example.movietmdb.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietmdb.repository.db.DAO.MovieDao
import com.example.movietmdb.repository.db.entity.MovieData

//database creator
@Database(entities = [MovieData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}