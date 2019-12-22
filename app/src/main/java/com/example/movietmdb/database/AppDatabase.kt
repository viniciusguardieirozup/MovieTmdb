package com.example.movietmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase

//dataabase creator
@Database(entities = arrayOf(MovieData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomInterfaceDao(): RoomInterface

}