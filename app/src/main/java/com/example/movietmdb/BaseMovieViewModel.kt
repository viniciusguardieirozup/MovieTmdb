package com.example.movietmdb

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import com.example.movietmdb.repository.retrofit.GenresList
import kotlinx.coroutines.launch

sealed class ViewState {
    class Loading(val loading: Boolean) : ViewState()
    class Data(val movies: List<MoviePresentation>) : ViewState()
    class Error(val message: String) : ViewState()
    class Genre(val genres: GenresList) : ViewState()
}

open class BaseMovieViewModel : ViewModel() {

    lateinit var movie : MoviePresentation
    protected var loading = false
    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun load(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                moviesLiveData.value = ViewState.Error("Problem to find this movie")
            } finally {
                loading = false
            }
        }
    }
}