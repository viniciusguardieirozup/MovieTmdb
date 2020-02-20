package com.example.movietmdb.mappers

import android.util.Log
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService
import kotlin.math.roundToInt

object MoviePresentationMapper {

    private fun mapFromService(dataMovie: MovieData): MoviePresentation {
        return MoviePresentation(
            dataMovie.posterPath,
            dataMovie.overView,
            stringToArray(dataMovie.genreIds),
            dataMovie.id,
            dataMovie.title,
            dataMovie.backdropPath,
            dataMovie.popularity,
            dataMovie.voteCount,
            (dataMovie.voteAverage*10).roundToInt().toString(),
            1
        )
    }

    private fun mapFromData(dataMovie: MovieData): MoviePresentation {
        return MoviePresentation(
            dataMovie.posterPath,
            dataMovie.overView,
            stringToArray(dataMovie.genreIds),
            dataMovie.id,
            dataMovie.title,
            dataMovie.backdropPath,
            dataMovie.popularity,
            dataMovie.voteCount,
            (dataMovie.voteAverage*10).roundToInt().toString(),
            1
        )
    }

    private fun stringToArray(ids: String): ArrayList<Int> {
        val aux = ArrayList<Int>(0)
        val auxS = ids.replace("[", "").replace("]", "").split(",")
        val size = auxS.size - 1
        for (i in 0..size) {
            if (auxS[i].trim() != "")
                aux.add(auxS[i].trim().toInt())
        }
        return aux
    }

    fun converterListMovieData(movies: List<MovieData>): ArrayList<MoviePresentation> {
        val moviesList = ArrayList<MoviePresentation>()
        val size = movies.size - 1
        for (i in 0..size) {
            moviesList.add(mapFromData(movies[i]))
        }
        return moviesList
    }

    fun convertListMovieService(
        movies: List<MovieService>
    ): ArrayList<MoviePresentation> {
        val moviesList = ArrayList<MoviePresentation>()
        val moviesData = MovieDataMapper.convertListMovieService(movies)
        val size = movies.size - 1
        for (i in 0..size) {
                moviesList.add(mapFromService(moviesData[i]))

        }
        return moviesList
    }
}