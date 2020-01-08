package com.example.movietmdb.recycler.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//data class for the object that will be displayed on the recyclerView
@Parcelize
data class MoviePresentation(
    val posterPath: String?,
    val adult: Boolean?,
    val overView: String?,
    val releaseData: String?,
    val genreIds: ArrayList<Int>,
    val id: Int?,
    val originalTitle: String?,
    val originalLanguage: String?,
    val title: String?,
    val backdropPath: String?,
    val popularity: Double?,
    val voteCount: Int?,
    val video: Boolean?,
    val voteAverage: Double?,
    var favorite: Boolean
) : Parcelable