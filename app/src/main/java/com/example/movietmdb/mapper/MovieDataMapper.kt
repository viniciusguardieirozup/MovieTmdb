package com.example.movietmdb.mapper

import com.example.movietmdb.database.MovieData
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.retrofit.MovieService

//Mapper for DataMovies
class MovieDataMapper {

    //MovieService to MoviePresentation converter

    fun mapFromMovieService(seacrhMovie: MovieService): MovieData {
        var aux = seacrhMovie.genreIds.toString()
        return MovieData(
            seacrhMovie.posterPath,
            seacrhMovie.adult,
            seacrhMovie.overView,
            seacrhMovie.releaseData,
            aux,
            seacrhMovie.id,
            seacrhMovie.originalTitle,
            seacrhMovie.originalLanguage,
            seacrhMovie.title,
            seacrhMovie.backdropPath,
            seacrhMovie.popularity,
            seacrhMovie.voteCount,
            seacrhMovie.video,
            seacrhMovie.voteAverage
        )
    }



}