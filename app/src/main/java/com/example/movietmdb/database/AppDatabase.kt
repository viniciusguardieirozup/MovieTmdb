package com.example.movietmdb.database

import android.os.Parcelable
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.android.parcel.Parcelize

//database creator
@Database(entities = [MovieData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}