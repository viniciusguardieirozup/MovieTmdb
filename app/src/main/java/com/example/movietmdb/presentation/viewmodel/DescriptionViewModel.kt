package com.example.movietmdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.presentation.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.presentation.viewmodel.PaginationViewModel
import com.example.movietmdb.presentation.viewmodel.ViewState
import kotlinx.coroutines.launch

class DescriptionViewModel(val moviesRepository: MoviesRepository) : PaginationViewModel() {

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url
    private val _favorite = MutableLiveData<Boolean>()
    val favorite : LiveData<Boolean>
        get() = _favorite

    fun startViewModel(movie: MoviePresentation) {
        this.movie = movie
        this.movie.type = 0
        moviesLiveData.value = ViewState.Data(arrayListOf(movie))
        _url.value = movie.backdropPath
        viewModelScope.launch {
            val results: MovieData? = moviesRepository.getAMovie(movie.id)
            _favorite.value = results != null
        }
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

    private suspend fun accessRepositoryMapResult(): SearchResults {
        val moviesResults = moviesRepository.getSimilar(movie.id, page)
        moviesLiveData.value = ViewState.Data(
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results
            )
        )
        return moviesResults
    }

    fun setFavorite(){
        if(_favorite.value == true){
            _favorite.value = false
            viewModelScope.launch { moviesRepository.removeMovie(MovieDataMapper.mapFromPresentation(movie)) }
        }
        else{
            _favorite.value = true
            viewModelScope.launch { moviesRepository.insertMovie(MovieDataMapper.mapFromPresentation(movie)) }
        }
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