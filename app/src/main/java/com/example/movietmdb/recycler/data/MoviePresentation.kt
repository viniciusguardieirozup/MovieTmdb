package com.example.movietmdb.recycler.data

import android.os.Parcelable
import android.renderscript.ScriptGroup
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviePresentation(
    private val _posterPath: String?,
    private val _adult: Boolean?,
    private val _overView: String?,
    private val _releaseData: String?,
    private val _genreIds: ArrayList<Int>,
    private val _id: Int,
    private val _originalTitle: String?,
    private val _originalLanguage: String?,
    private val _title: String?,
    private val _backdropPath: String?,
    private val _popularity: Double?,
    private val _voteCount: Int?,
    private val _video: Boolean?,
    private val _voteAverage: String,
    private var _favorite: Boolean,
    private var _type: Int
) : Parcelable, BaseObservable() {

    val posterPath: String?
        @Bindable get() = _posterPath

    val adult: Boolean?
        @Bindable get() = _adult

    val overView: String?
        @Bindable get() = _overView

    val releaseData
        @Bindable get() = _releaseData

    val genreIds: ArrayList<Int>
        @Bindable get() = _genreIds

    val id: Int
        @Bindable get() = _id

    val originalTitle: String?
        @Bindable get() = _originalTitle

    val originalLanguage
        @Bindable get() = _originalLanguage

    val title
        @Bindable get() = _title

    val backdropPath
        @Bindable get() = _backdropPath

    val popularity
        @Bindable get() = _popularity

    val voteCount
        @Bindable get() = _voteCount

    val video
        @Bindable get() = _video

    val voteAverage
        @Bindable get() = _voteAverage

    var favorite
        @Bindable get() = _favorite
        set(value) {
            _favorite = value
            notifyPropertyChanged(BR.favorite)
        }

    var type
        @Bindable get() = _type
        set(value) {
            _type = value
            notifyPropertyChanged(BR.type)
        }
}
