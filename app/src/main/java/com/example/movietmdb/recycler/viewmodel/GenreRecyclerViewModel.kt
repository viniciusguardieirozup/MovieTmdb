package com.example.movietmdb.recycler.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.RepositoryRules

class GenreRecyclerViewModel(
    private val repositoryRules: RepositoryRules
) : BaseMovieViewModel() {

    val mutableLiveData = MutableLiveData<ViewState>()
    private var lastPage: Boolean = false
    private var page = 1

    fun getMoviesByGenres(id: Int) {
        if (!loading && !lastPage) {
            mutableLiveData.value = ViewState.Loading(true)
            load {
                loading = true
                val moviesResults = repositoryRules.getMoviesByGenres(id, page)
                val favMovies = repositoryRules.getFavMovies()

                mutableLiveData.value = ViewState.Data(
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
                mutableLiveData.value = ViewState.Loading(false)
                loading = false
                if (moviesResults.totalResults == 0) {
                    lastPage = true
                }
            }
        }
    }
}