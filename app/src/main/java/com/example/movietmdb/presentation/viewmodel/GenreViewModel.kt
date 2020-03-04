package com.example.movietmdb.presentation.viewmodel

import com.example.movietmdb.presentation.viewmodel.BaseMovieViewModel
import com.example.movietmdb.presentation.viewmodel.ViewState
import com.example.movietmdb.repository.MoviesRepository


class GenreViewModel(private val moviesRepository: MoviesRepository) :
    BaseMovieViewModel() {

    fun getGenres() {
        moviesLiveData.value = ViewState.Loading(true)
        load {
            val result = moviesRepository.getGenres()
            moviesLiveData.value = ViewState.Genre(result)
            moviesLiveData.value = ViewState.Loading(false)
        }
    }

}