package com.example.movietmdb.repository.db.DAO

import androidx.room.*
import com.example.movietmdb.repository.db.entity.MovieData

//interface for database
@Dao
interface MovieDao {
    @Query("SELECT * FROM fav_movies")
    suspend fun getAll(): List<MovieData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieData)

    @Delete
    suspend fun removeMovie(movie: MovieData)

    @Query("SELECT * FROM fav_movies WHERE id = :movieID")
    suspend fun searchMovie(movieID : Int) : MovieData
}