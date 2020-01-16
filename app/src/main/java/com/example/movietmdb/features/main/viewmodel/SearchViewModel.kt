package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

sealed class ViewState {
    class Loading(val loading: Boolean) : ViewState()
    class Data(val movies: List<MoviePresentation>) : ViewState()
    class Error(val message: String) : ViewState()
}

class SearchFragmentViewModel(private val repositoryRules: RepositoryRules) :
    BaseMovieViewModel(repositoryRules) {

    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()
    private var page = 1
    private var loading = false
    private var lastPage = false

    @Throws(java.lang.Exception::class)
    fun searchMovies(name: String) {
        if (!loading && !lastPage) {
            moviesLiveData.value = ViewState.Loading(true)
            viewModelScope.launch {
                loading = true

                val moviesResults = repositoryRules.getMovies(name, page)
                val favMovies = repositoryRules.getFavMovies()

                moviesLiveData.value = ViewState.Data(
                    MoviePresentationMapper().convertListMovieService(
                        moviesResults.results,
                        favMovies
                    )
                )
                if (moviesResults.totalPages == page) {
                    lastPage = true
                } else {
                    page++
                }
                moviesLiveData.value = ViewState.Loading(false)
                loading = false
                if (moviesResults.totalResults == 0) {
                    moviesLiveData.value = ViewState.Error("Movies not found")
                }
            }
        } else if (lastPage && !loading) {
            moviesLiveData.value = ViewState.Error("No more movies")
        }
    }


}