package com.example.movietmdb.features.description.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.pagination.DataSourceFactory
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class DescriptionViewModel(val repository: RepositoryRules) : BaseMovieViewModel() {
    val mutable = MutableLiveData<ViewState>()
    lateinit var itemPagedList: LiveData<PagedList<MoviePresentation>>

    fun getSimilar(id: Int) {
        mutable.value = ViewState.Loading(true)
        if (!loading) {
            load {
                loading = true
                val factory = DataSourceFactory(repository, id)
                val config = PagedList.Config.Builder().setPageSize(20).setInitialLoadSizeHint(40)
                    .setPrefetchDistance(10).setEnablePlaceholders(false).build()
                itemPagedList = LivePagedListBuilder(factory, config).build()

            }
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