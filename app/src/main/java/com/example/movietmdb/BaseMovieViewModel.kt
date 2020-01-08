package com.example.movietmdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import kotlinx.coroutines.launch

open class BaseMovieViewModel : ViewModel() {

    lateinit var movie: MoviePresentation

    fun setFavorite(movie: MoviePresentation) {
        if (movie.favorite) {
            movie.favorite = false
            viewModelScope.launch {
                MovieTmdbApplication.repository.removeMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        } else {
            movie.favorite = true
            viewModelScope.launch {
                MovieTmdbApplication.repository.insertMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        }
    }


}