package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.coroutines.launch

sealed class ViewState {
    class Loading(val loading: Boolean) : ViewState()
    class Data(val movies: List<MoviePresentation>) : ViewState()
}

class SearchFragmentViewModel : ViewModel() {

    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun searchMovies(name: String, page: Int) {
        moviesLiveData.value =
            ViewState.Loading(
                true
            )
        viewModelScope.launch {
            moviesLiveData.value =
                ViewState.Data(
                    MovieTmdbApplication.repository.getMovies(
                        name,
                        page
                    )
                )
            moviesLiveData.value =
                ViewState.Loading(
                    false
                )
        }
    }

}