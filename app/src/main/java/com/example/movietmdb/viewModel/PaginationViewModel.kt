package com.example.movietmdb.viewModel

import com.example.movietmdb.repository.retrofit.SearchResults

abstract class PaginationViewModel : BaseMovieViewModel() {


    protected var lastPage = false
    protected var page = 1


    fun noMorePageAvailable() {
        if (lastPage && !loading) {
            moviesLiveData.value =
                ViewState.Error("No more movies")
        }
    }

    fun noMoviesAvailable(moviesResults: SearchResults) {
        if (moviesResults.totalResults == 0) {
            moviesLiveData.value =
                ViewState.Error("Movies not found")
        }
    }

    fun checkLastPage(moviesResults: SearchResults) {
        if (moviesResults.totalPages == page) {
            lastPage = true
        } else {
            page++
        }
    }

    fun returnPage() = page
    fun returnLastPage() = lastPage

}