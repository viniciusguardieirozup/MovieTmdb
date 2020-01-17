package com.example.movietmdb.features.main.viewmodel

import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.RepositoryRules

class SearchFragmentViewModel(private val repositoryRules: RepositoryRules) :
    BaseMovieViewModel() {


    private var page = 1
    private var lastPage = false

    fun searchMovies(name: String) {
        if (!loading && !lastPage) {
            moviesLiveData.value = ViewState.Loading(true)
            load {
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
                loading = false
                if (moviesResults.totalResults == 0) {
                    moviesLiveData.value = ViewState.Error("Movies not found")
                }
            }
        } else if (lastPage && !loading) {
            moviesLiveData.value = ViewState.Error("No more movies")
        }
        moviesLiveData.value = ViewState.Loading(false)
    }


}