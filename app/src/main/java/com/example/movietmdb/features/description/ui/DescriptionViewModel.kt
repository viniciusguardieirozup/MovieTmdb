package com.example.movietmdb.features.description.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class DescriptionViewModel(val repository: RepositoryRules) : BaseMovieViewModel() {
    private var page = 1
    val mutable = MutableLiveData<ViewState>()
    private var lastPage = false

    fun getSimilar(id: Int) {
        mutable.value = ViewState.Loading(true)
        if (!loading && !lastPage) {
            load {
                loading = true

                val moviesResults = repository.getSimilar(id, page)
                val favMovies = repository.getFavMovies()
                mutable.value = ViewState.Data(
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
                    mutable.value = ViewState.Error("Movies not found")
                }
            }
        } else if (lastPage && !loading) {
            mutable.value = ViewState.Error("No more movies")
        }
        mutable.value = ViewState.Loading(false)
    }

    fun setFavorite(movie: MoviePresentation) {
        if (movie.favorite) {
            movie.favorite = false
            viewModelScope.launch {
                repository.removeMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        } else {
            movie.favorite = true
            viewModelScope.launch {
                repository.insertMovie(
                    MovieDataMapper().mapFromPresentation(
                        movie
                    )
                )
            }
        }
    }
}