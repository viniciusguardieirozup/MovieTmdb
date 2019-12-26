package com.example.movietmdb.mapper

import com.example.movietmdb.database.MovieData
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.retrofit.MovieService

//Mapper for DataMovies
class MovieDataMapper {

    //MovieService to MoviePresentation converter

    private fun mapFromMovieService(seacrhMovie: MovieService): MovieData {
        return MovieData(
            seacrhMovie.posterPath,
            seacrhMovie.adult,
            seacrhMovie.overView,
            seacrhMovie.releaseData,
            seacrhMovie.genreIds.toString(),
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

    fun mapFromPresentation(moviePresentation: MoviePresentation): MovieData {
        return MovieData(
            moviePresentation.posterPath,
            moviePresentation.adult,
            moviePresentation.overView,
            moviePresentation.releaseData,
            moviePresentation.genreIds.toString(),
            moviePresentation.id,
            moviePresentation.originalTitle,
            moviePresentation.originalLanguage,
            moviePresentation.title,
            moviePresentation.backdropPath,
            moviePresentation.popularity,
            moviePresentation.voteCount,
            moviePresentation.video,
            moviePresentation.voteAverage

        )
    }

    fun convertListMovieService(movies: List<MovieService>): ArrayList<MovieData> {
        val moviesList = ArrayList<MovieData>()
        val size = movies.size - 1
        for (i in 0..size) {
            moviesList.add(mapFromMovieService(movies[i]))
        }
        return moviesList
    }


}