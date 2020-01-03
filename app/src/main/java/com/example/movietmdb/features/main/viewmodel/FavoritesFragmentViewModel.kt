package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class FavoritesFragmentViewModel : ViewModel() {

    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun getFavMovies() {
        moviesLiveData.value = ViewState.Loading(true)
        viewModelScope.launch {
            moviesLiveData.value = ViewState.Data(RepositoryRules().getFavMovies())
            moviesLiveData.value = ViewState.Loading(false)
        }

    }
}