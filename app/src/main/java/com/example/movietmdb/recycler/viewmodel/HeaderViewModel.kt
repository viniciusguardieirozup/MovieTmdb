package com.example.movietmdb.recycler.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import kotlinx.coroutines.launch


class HeaderViewModel(val moviesRepository: MoviesRepository) : BaseMovieViewModel() {

    fun setFavorite(movie: MoviePresentation) {
        if (movie.favorite) {
            movie.favorite = false
            viewModelScope.launch {
                moviesRepository.removeMovie(MovieDataMapper.mapFromPresentation(movie))
            }
        } else {
            movie.favorite = true
            viewModelScope.launch {
                moviesRepository.insertMovie(MovieDataMapper.mapFromPresentation(movie))
            }
        }
    }
}