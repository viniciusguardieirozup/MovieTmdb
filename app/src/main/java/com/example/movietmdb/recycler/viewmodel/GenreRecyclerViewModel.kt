package com.example.movietmdb.recycler.viewmodel

import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.PaginationViewModel
import com.example.movietmdb.viewModel.ViewState

class GenreRecyclerViewModel(
    private val moviesRepository: MoviesRepository
) : PaginationViewModel() {


    fun getMoviesByGenres(id: Int) {
        moviesLiveData.value = ViewState.Loading(true)
        if (!loading && !lastPage) {
            loadSimilar(id)
        } else {
            noMorePageAvailable()
        }
        moviesLiveData.value = ViewState.Loading(false)
    }

    private fun loadSimilar(id: Int) {
        load {
            loading = true
            val moviesResults = accessRepositoryMapResult(id)
            checkLastPage(moviesResults)
            loading = false
            noMoviesAvailable(moviesResults)
        }
    }

    private suspend fun accessRepositoryMapResult(id: Int): SearchResults {
        val moviesResults = moviesRepository.getMoviesByGenres(id, page)
        val favMovies = moviesRepository.getFavMovies()
        moviesLiveData.value = ViewState.Data(
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        )
        return moviesResults
    }
}