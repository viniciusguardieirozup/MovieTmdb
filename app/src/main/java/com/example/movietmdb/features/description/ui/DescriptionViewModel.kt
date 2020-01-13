package com.example.movietmdb.features.description.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class DescriptionViewModel : BaseMovieViewModel() {
    private var page = 1

    val mutable = MutableLiveData<ViewState>()
    fun getSimilars(id : Int) {
        mutable.value = ViewState.Loading(true)
        viewModelScope.launch {
            mutable.value = ViewState.Data(
                MoviePresentationMapper().convertListMovieService(
                    MovieTmdbApplication.repository.getSimilar(
                        id,
                        page
                    ).results, MovieTmdbApplication.repository.getFavMovies()

                )
            )
            mutable.value = ViewState.Loading(false)
            page++
        }
    }
}