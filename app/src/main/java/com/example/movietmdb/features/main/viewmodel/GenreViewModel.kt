package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.MovieTmdbApplication
import kotlinx.coroutines.launch

class GenreViewModel : BaseMovieViewModel() {

    private var page = 1
    val moviesLiveData = MutableLiveData<ViewState>()
    private var loading = false


    fun getMoviesByGenre(id: Int) {
        moviesLiveData.value = ViewState.Loading(true)
        if (!loading) {
            viewModelScope.launch {
                loading = true
                val result = MovieTmdbApplication.repository.getMoviesByGenres(id, page)
                moviesLiveData.value = ViewState.Data(result)
                page++
                moviesLiveData.value = ViewState.Loading(false)
                loading = false
            }
        }

    }

}