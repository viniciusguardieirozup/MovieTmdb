package com.example.movietmdb.features.description.viewmodel

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DescriptionActivityViewModel : ViewModel() {

    lateinit var movie: MoviePresentation
    lateinit var mutableLiveData: LiveData<Bitmap>

    fun setFavorite() {
        viewModelScope.launch {
            if (movie.favorite) {
                movie.favorite = false
                MovieTmdbApplication.repository.removeMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            } else {
                movie.favorite = true
                MovieTmdbApplication.repository.saveMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        }
    }

    fun configImagePoster() {
        viewModelScope.launch {
            Glide.with()
                .load(movie.posterPath).apply(request)
                .into(imageDescriptionLayout)
        }
    }

}