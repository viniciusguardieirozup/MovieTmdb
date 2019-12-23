package com.example.movietmdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//interface for database
@Dao
interface MovieDao {
    @Query("SELECT * FROM fav_movies")
    suspend fun getAll(): List<MovieData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieData)

}