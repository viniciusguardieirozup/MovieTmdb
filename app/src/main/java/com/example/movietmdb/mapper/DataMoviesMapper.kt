package com.example.movietmdb.mapper

import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.retrofit.MovieService

//Mapper for DataMovies
class DataMoviesMapper {

    //MovieService to MoviePresentation converter
    fun mapInMoviePresentation(dataMovies: MovieService): MoviePresentation {
        return MoviePresentation(
            dataMovies.posterPath,
            dataMovies.adult,
            dataMovies.overView,
            dataMovies.releaseData,
            dataMovies.genreIds,
            dataMovies.id,
            dataMovies.originalTitle,
            dataMovies.originalLanguage,
            dataMovies.title,
            dataMovies.backdropPath,
            dataMovies.popularity,
            dataMovies.voteCount,
            dataMovies.video,
            dataMovies.voteAverage
        )
    }

}