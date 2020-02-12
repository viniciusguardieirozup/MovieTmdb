package com.example.movietmdb.features.main.viewmodel

import com.example.movietmdb.viewModel.BaseMovieViewModel
import com.example.movietmdb.viewModel.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository

class FavoritesViewModel(private val moviesRepository: MoviesRepository) :
    BaseMovieViewModel() {

    fun getFavMovies() {
        load {
            moviesLiveData.value = ViewState.Loading(true)
            moviesLiveData.value =
                ViewState.Data(MoviePresentationMapper.converterListMovieData(moviesRepository.getFavMovies()))
            moviesLiveData.value = ViewState.Loading(false)
        }
    }
}