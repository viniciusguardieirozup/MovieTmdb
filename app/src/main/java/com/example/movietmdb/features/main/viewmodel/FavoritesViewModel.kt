package com.example.movietmdb.features.main.viewmodel

import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository

class FavoritesViewModel(private val moviesRepository: MoviesRepository) :
    BaseMovieViewModel() {

    fun getFavMovies() {
        moviesLiveData.value = ViewState.Loading(true)
        load {
            moviesLiveData.value =
                ViewState.Data(MoviePresentationMapper.converterListMovieData(moviesRepository.getFavMovies()))
            moviesLiveData.value = ViewState.Loading(false)
        }
    }
}