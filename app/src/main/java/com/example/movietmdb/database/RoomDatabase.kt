package com.example.movietmdb.database

import androidx.room.Database
//dataabase creator
@Database(entities = arrayOf(MovieData::class), version = 1)
abstract class RoomDatabase{
    abstract fun roomInterfaceDao() : RoomInterface

}