package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class GenreFragmentViewModel : ViewModel(){

    val moviesLiveData = MutableLiveData<ViewState>()

    fun getMoviesByGenre(id : Int, page : Int){
        moviesLiveData.value = ViewState.Loading(true)
        viewModelScope.launch {
            moviesLiveData.value = ViewState.Data(RepositoryRules().getMoviesByGenres(id,page))
            moviesLiveData.value = ViewState.Loading(false)
        }

    }
    fun getGenres(){

    }

}