package com.example.movietmdb.repository.retrofit

import com.google.gson.annotations.SerializedName
//data class with retrofit results
data class SearchResults(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<MovieService>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)
