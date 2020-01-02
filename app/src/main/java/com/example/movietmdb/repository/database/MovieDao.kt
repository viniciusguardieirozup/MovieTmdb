package com.example.movietmdb.repository.database

import androidx.room.*

//interface for database
@Dao
interface MovieDao {
    @Query("SELECT * FROM fav_movies")
    suspend fun getAll(): List<MovieData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieData)

    @Delete
    suspend fun removeMovie(movie : MovieData)

}