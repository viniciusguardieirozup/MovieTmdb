package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repositoryRules: RepositoryRules) : BaseMovieViewModel(repositoryRules) {
    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun getFavMovies() {
        moviesLiveData.value = ViewState.Loading(true)
        viewModelScope.launch {
            moviesLiveData.value =
                ViewState.Data(MoviePresentationMapper().converterListMovieData(repositoryRules.getFavMovies()))
            moviesLiveData.value = ViewState.Loading(false)
        }

    }
}