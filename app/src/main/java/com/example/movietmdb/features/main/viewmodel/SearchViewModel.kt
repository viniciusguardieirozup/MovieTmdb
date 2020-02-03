package com.example.movietmdb.features.main.viewmodel

import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository

class SearchFragmentViewModel(private val moviesRepository: MoviesRepository) :
    BaseMovieViewModel() {

    private var page = 1
    private var lastPage = false
    private var oldName = ""

    fun searchMovies(name: String) {
        if(oldName!=name){
            lastPage = false
            page = 1
        }
        if (!loading && !lastPage) {
            moviesLiveData.value = ViewState.Loading(true)
            load {
                loading = true

                val moviesResults = moviesRepository.getMovies(name, page)
                val favMovies = moviesRepository.getFavMovies()

                moviesLiveData.value = ViewState.Data(
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
                    moviesLiveData.value = ViewState.Error("Movies not found")
                }
            }
        } else if (lastPage && !loading) {
            moviesLiveData.value = ViewState.Error("No more movies")
        }
        moviesLiveData.value = ViewState.Loading(false)
    }
}