package com.example.movietmdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieData(
    val posterPath: String?,
    val adult: Boolean?,
    val overView: String?,
    val releaseData: String?,
    val genreIds: ArrayList<Int>?,
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