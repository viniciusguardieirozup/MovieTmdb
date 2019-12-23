package com.example.movietmdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
//data class for database
@Entity(
    tableName = "fav_movies"

)
data class MovieData(
    val posterPath: String?,
    val adult: Boolean?,
    val overView: String?,
    val releaseData: String?,
    val genreIds:  String,
    @PrimaryKey val id: Int?,
    val originalTitle: String?,
    val originalLanguage: String?,
    val title: String?,
    val backdropPath: String?,
    val popularity: Double?,
    val voteCount: Int?,
    val video: Boolean?,
    val voteAverage: Double?
)