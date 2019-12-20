package com.example.movietmdb.retrofit

import com.google.gson.annotations.SerializedName

data class DataMovies(
    @SerializedName("poster_path") val posterPath: String?, @SerializedName("adult") val adult: Boolean, @SerializedName(
        "overview"
    ) val overView: String, @SerializedName("release_data") val releaseData: String, @SerializedName(
        "genre_ids"
    ) val genreIds: ArrayList<Int>, @SerializedName("id") val id: Int, @SerializedName("original_title") val originalTitle: String, @SerializedName(
        "original_language"
    ) val originalLanguage: String, @SerializedName("title") val title: String, @SerializedName("backdrop_path") val backdropPath: String?, @SerializedName(
        "popularity"
    ) val popularity: Int, @SerializedName("vote_count") val voteCount: Int, @SerializedName("video") val video: Boolean, @SerializedName(
        "vote_average"
    ) val voteAverage: Int
)