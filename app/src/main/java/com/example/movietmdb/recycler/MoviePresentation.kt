package com.example.movietmdb.recycler
//data class for the object that will be displayed on the recyclerView
data class MoviePresentation(
    val posterPath: String?,
    val adult: Boolean?,
    val overView: String?,
    val releaseData: String?,
    val genreIds: ArrayList<Int>?,
    val id: Int?,
    val originalTitle: String?,
    val originalLanguage: String?,
    val title: String?,
    val backdropPath: String?,
    val popularity: Double?,
    val voteCount: Int?,
    val video: Boolean?,
    val voteAverage: Double?
)