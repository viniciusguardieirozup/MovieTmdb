package com.example.movietmdb.repository.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//data class for database
@Entity(tableName = "fav_movies")
@Parcelize
data class MovieData(
    val posterPath: String?,
    val adult: Boolean?,
    val overView: String?,
    val releaseData: String?,
    val genreIds: String,
    @PrimaryKey val id: Int?,
    val originalTitle: String?,
    val originalLanguage: String?,
    val title: String?,
    val backdropPath: String?,
    val popularity: Double?,
    val voteCount: Int?,
    val video: Boolean?,
    val voteAverage: Double?
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return (other as MovieData).id == id
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}