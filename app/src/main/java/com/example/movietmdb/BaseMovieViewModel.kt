package com.example.movietmdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

open class BaseMovieViewModel(private val repositoryRules: RepositoryRules) : ViewModel() {

    lateinit var movie: MoviePresentation

    fun setFavorite(movie: MoviePresentation) {
        if (movie.favorite) {
            movie.favorite = false
            viewModelScope.launch {
                repositoryRules.removeMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        } else {
            movie.favorite = true
            viewModelScope.launch {
                repositoryRules.insertMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        }
    }


}