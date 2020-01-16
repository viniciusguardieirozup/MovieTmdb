package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.repository.RepositoryRules
import com.example.movietmdb.repository.retrofit.GenresList
import kotlinx.coroutines.launch

sealed class ViewStateGenre {
    class Data(val genres: GenresList) : ViewStateGenre()
}

class GenreViewModel(private val repositoryRules: RepositoryRules) :
    BaseMovieViewModel(repositoryRules) {

    val genreLiveData = MutableLiveData<ViewStateGenre>()


    fun getGenres() {
        viewModelScope.launch {
            val result = repositoryRules.getGenres()
            genreLiveData.value = ViewStateGenre.Data(result)
        }
    }

}