package com.example.movietmdb.database

import androidx.room.Dao
import androidx.room.Query

//interface for databse
@Dao
interface RoomInterface {
    @Query("SELECT * FROM moviedata")
    fun getAll(): List<MovieData>


}