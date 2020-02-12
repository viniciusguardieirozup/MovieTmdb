package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.viewModel.BaseMovieViewModel
import com.example.movietmdb.viewModel.ViewState
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