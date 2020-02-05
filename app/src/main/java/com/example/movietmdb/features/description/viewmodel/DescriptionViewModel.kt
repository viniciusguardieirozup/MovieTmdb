package com.example.movietmdb.features.description.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.SearchResults

class DescriptionViewModel(val moviesRepository: MoviesRepository) : BaseMovieViewModel() {

    var page = 1
    val mutable = MutableLiveData<ViewState>()
    var lastPage = false
    var id = 0

    fun getSimilar() {
        mutable.value = ViewState.Loading(true)
        if (!loading && !lastPage) {
            loadSimilar()
        } else {
            noMorePageAvailable()
        }
        mutable.value = ViewState.Loading(false)
    }

    private fun checkLastPage(moviesResults: SearchResults) {
        if (moviesResults.totalPages == page) {
            lastPage = true
        } else {
            page++
        }
    }

    private suspend fun accessRepositoryMapResult(): SearchResults {
        val moviesResults = moviesRepository.getSimilar(id, page)
        val favMovies = moviesRepository.getFavMovies()
        mutable.value = ViewState.Data(
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        )
        return moviesResults
    }

    private fun noMorePageAvailable() {
        if (lastPage && !loading) {
            mutable.value = ViewState.Error("No more movies")
        }
    }

    private fun noMoviesResults(moviesResults: SearchResults) {
        if (moviesResults.totalResults == 0) {
            mutable.value = ViewState.Error("Movies not found")
        }
    }

    private fun loadSimilar() {
        load {
            loading = true
            val moviesResults = accessRepositoryMapResult()
            checkLastPage(moviesResults)
            loading = false
            noMoviesResults(moviesResults)
        }
    }
}