package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.mappers.MoviePresentationMapper
import kotlinx.coroutines.launch

class FavoritesViewModel : BaseMovieViewModel() {
    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun getFavMovies() {
        moviesLiveData.value = ViewState.Loading(true)
        viewModelScope.launch {
            moviesLiveData.value =
                ViewState.Data(MoviePresentationMapper().converterListMovieData(MovieTmdbApplication.repository.getFavMovies()))
            moviesLiveData.value = ViewState.Loading(false)
        }

    }
}