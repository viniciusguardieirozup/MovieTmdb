package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.repository.RepositoryRules


class GenreViewModel(private val repositoryRules: RepositoryRules) :
    BaseMovieViewModel() {

    val genreLiveData = MutableLiveData<ViewState>()


    fun getGenres() {
        genreLiveData.value = ViewState.Loading(true)
        load {
            val result = repositoryRules.getGenres()
            genreLiveData.value = ViewState.Genre(result)
            genreLiveData.value = ViewState.Loading(false)
        }
    }

}