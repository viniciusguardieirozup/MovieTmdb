package com.example.movietmdb.database

import androidx.room.Dao
import androidx.room.Query
//interface for databse
@Dao
interface RoomInterface{
    @Query("SELECT * FROM moviedata")
    fun getAll():List<MovieData>
    @Query("SELECT * FROM moviedata WHERE id IN (:movieId)")
    fun loadAllByIds(movieId: IntArray): List<MovieData>
}