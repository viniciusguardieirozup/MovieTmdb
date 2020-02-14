package com.example.movietmdb.features.description.viewmodel

import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.PaginationViewModel
import com.example.movietmdb.viewModel.ViewState

class DescriptionViewModel(val moviesRepository: MoviesRepository) : PaginationViewModel() {

    var id = 0

    fun getSimilar() {
        moviesLiveData.value = ViewState.Loading(true)
        if (!loading && !lastPage) {
            loadSimilar()
        } else {
            noMorePageAvailable()
        }
        moviesLiveData.value = ViewState.Loading(false)
    }

    suspend fun accessRepositoryMapResult(): SearchResults {
        val moviesResults = moviesRepository.getSimilar(id, page)
        val favMovies = moviesRepository.getFavMovies()
        moviesLiveData.value = ViewState.Data(
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        )
        return moviesResults
    }

    private fun loadSimilar() {
        load {
            loading = true
            val moviesResults = accessRepositoryMapResult()
            checkLastPage(moviesResults)
            loading = false
            noMoviesAvailable(moviesResults)
        }
    }
}