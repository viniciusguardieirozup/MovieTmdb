package com.example.movietmdb.features.description.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movietmdb.R
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlin.properties.Delegates

class DescriptionActivityViewModel : ViewModel() {

    lateinit var movie: MoviePresentation
    var favMovie by Delegates.notNull<Int>()

    fun remove(movie: MoviePresentation) {
        RepositoryRules().deleteMovie(MovieDataMapper().mapFromPresentation(movie))
    }

    fun save(movie: MoviePresentation) {
        RepositoryRules().saveMovie(MovieDataMapper().mapFromPresentation(movie))
    }

    fun setImageFav() {
        favMovie = if (movie.favorite) {
            R.drawable.ic_favorite_white_24dp
        } else {
            R.drawable.ic_favorite_border_white_24dp
        }
    }

}