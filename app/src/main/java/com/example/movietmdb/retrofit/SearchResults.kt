package com.example.movietmdb.retrofit

import com.google.gson.annotations.SerializedName

data class SearchResults(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<DataMovies>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
