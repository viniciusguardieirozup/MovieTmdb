package com.example.movietmdb.mappers

import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService

//Mapper for DataMovies
object MovieDataMapper {

    private fun mapFromMovieService(searchMovie: MovieService): MovieData {
        val posterPath: String? = if (searchMovie.posterPath == null) {
            null
        } else {
            "https://image.tmdb.org/t/p/w185/" + searchMovie.posterPath
        }
        return MovieData(
            posterPath,
            searchMovie.overView,
            searchMovie.genreIds.toString(),
            searchMovie.id,
            searchMovie.title,
            "https://image.tmdb.org/t/p/original/" + searchMovie.backdropPath,
            searchMovie.popularity,
            searchMovie.voteCount,
            searchMovie.voteAverage
        )
    }

    fun mapFromPresentation(moviePresentation: MoviePresentation): MovieData {
        return MovieData(
            moviePresentation.posterPath,
            moviePresentation.overView,
            moviePresentation.genreIds.toString(),
            moviePresentation.id,
            moviePresentation.title,
            moviePresentation.backdropPath,
            moviePresentation.popularity,
            moviePresentation.voteCount,
            moviePresentation.voteAverage.toDouble()/10
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