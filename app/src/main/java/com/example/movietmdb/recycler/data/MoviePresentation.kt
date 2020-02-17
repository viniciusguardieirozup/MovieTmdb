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
    private val _overView: String?,
    private val _genreIds: ArrayList<Int>,
    private val _id: Int,
    private val _title: String?,
    private val _backdropPath: String?,
    private val _popularity: Double?,
    private val _voteCount: Int?,
    private val _voteAverage: String,
    private var _favorite: Boolean,
    private var _type: Int
) : Parcelable, BaseObservable() {

    val posterPath: String?
        @get:Bindable get() = _posterPath

    val overView: String?
        @get:Bindable get() = _overView

    val genreIds: ArrayList<Int>
        @get:Bindable get() = _genreIds

    val id: Int
        @get:Bindable get() = _id

    val title
        @get:Bindable get() = _title

    val backdropPath
        @get:Bindable get() = _backdropPath

    val popularity
        @get:Bindable get() = _popularity

    val voteCount
        @get:Bindable get() = _voteCount

    val voteAverage
        @get:Bindable get() = _voteAverage

    var favorite
        @get:Bindable get() = _favorite
        set(value) {
            if(_favorite != value){
                _favorite = value
//                notifyPropertyChanged()
            }
        }

    var type
        @get:Bindable get() = _type
        set(value) {
            if(_type != value){
                _type = value
//                notifyPropertyChanged(BR.type)
            }
        }
}
