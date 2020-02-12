package com.example.movietmdb.features.main.viewmodel

import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.PaginationViewModel
import com.example.movietmdb.viewModel.ViewState

class SearchViewModel(private val moviesRepository: MoviesRepository) :
    PaginationViewModel() {

    private var oldName = ""

    fun searchMovies(name: String) {
        checkNewSearch(name)
        if (!loading && !lastPage) {
            moviesLiveData.value = ViewState.Loading(true)
            loadSimilar(name)
        } else {
            noMorePageAvailable()
        }
        moviesLiveData.value = ViewState.Loading(false)
    }

    private fun checkNewSearch(name: String) {
        if (oldName != name) {
            lastPage = false
            page = 1
        }
    }

    private suspend fun accessRepositoryMapResult(name: String): SearchResults {
        val moviesResults = moviesRepository.getMovies(name, page)
        val favMovies = moviesRepository.getFavMovies()
        moviesLiveData.value = ViewState.Data(
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        )
        return moviesResults
    }

    private fun loadSimilar(name: String) {
        load {
            loading = true
            val moviesResults = accessRepositoryMapResult(name)
            checkLastPage(moviesResults)
            loading = false
            noMoviesAvailable(moviesResults)
        }
    }


}