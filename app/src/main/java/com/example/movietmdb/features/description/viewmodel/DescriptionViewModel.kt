package com.example.movietmdb.features.description.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository

class DescriptionViewModel(val moviesRepository: MoviesRepository) : BaseMovieViewModel() {
    private var page = 1
    val mutable = MutableLiveData<ViewState>()
    private var lastPage = false
    var id = 0

    fun getSimilar() {
        mutable.value = ViewState.Loading(true)
        if (!loading && !lastPage) {
            load {
                loading = true

                val moviesResults = moviesRepository.getSimilar(id, page)
                val favMovies = moviesRepository.getFavMovies()
                mutable.value = ViewState.Data(
                    MoviePresentationMapper.convertListMovieService(
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
                    mutable.value = ViewState.Error("Movies not found")
                }
            }
        } else if (lastPage && !loading) {
            mutable.value = ViewState.Error("No more movies")
        }
        mutable.value = ViewState.Loading(false)
    }
}