package com.example.movietmdb.database

import androidx.room.Database

@Database(entities = arrayOf(MovieData::class), version = 1)
abstract class RoomDatabase{
    abstract fun roomInterfaceDao() : RoomInterface

}