package com.example.movietmdb.features.description.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.PaginationViewModel
import com.example.movietmdb.viewModel.ViewState

class DescriptionViewModel(val moviesRepository: MoviesRepository) : PaginationViewModel() {

    private var id = 0

    fun startViewModel(movie : MoviePresentation){
        this.id = movie.id
        this.movie = movie
        this.movie.type = 0
        moviesLiveData.value = ViewState.Data(arrayListOf(movie))
    }

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