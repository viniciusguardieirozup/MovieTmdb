package com.example.movietmdb.mappers

import android.util.Log
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService
import kotlin.math.roundToInt

class MoviePresentationMapper {

    private fun mapFromService(dataMovie: MovieData, fav: Boolean): MoviePresentation {
        return MoviePresentation(
            dataMovie.posterPath,
            dataMovie.adult,
            dataMovie.overView,
            dataMovie.releaseData,
            stringToArray(dataMovie.genreIds),
            dataMovie.id,
            dataMovie.originalTitle,
            dataMovie.originalLanguage,
            dataMovie.title,
            dataMovie.backdropPath,
            dataMovie.popularity,
            dataMovie.voteCount,
            dataMovie.video,
            (dataMovie.voteAverage*10).roundToInt().toString(),
            fav
        )
    }

    private fun mapFromData(dataMovie: MovieData, fav: Boolean): MoviePresentation {
        return MoviePresentation(
            dataMovie.posterPath,
            dataMovie.adult,
            dataMovie.overView,
            dataMovie.releaseData,
            stringToArray(dataMovie.genreIds),
            dataMovie.id,
            dataMovie.originalTitle,
            dataMovie.originalLanguage,
            dataMovie.title,
            dataMovie.backdropPath,
            dataMovie.popularity,
            dataMovie.voteCount,
            dataMovie.video,
            dataMovie.voteAverage.roundToInt().toString(),
            fav
        )
    }

    private fun stringToArray(ids: String): ArrayList<Int> {
        Log.v("ee", ids)
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
            moviesList.add(mapFromData(movies[i], true))
        }
        return moviesList
    }

    fun convertListMovieService(
        movies: List<MovieService>,
        fav: List<MovieData>
    ): ArrayList<MoviePresentation> {
        val moviesList = ArrayList<MoviePresentation>()
        val moviesData = MovieDataMapper().convertListMovieService(movies)
        val size = movies.size - 1
        for (i in 0..size) {
            if (fav.contains(moviesData[i])) {
                moviesList.add(mapFromService(moviesData[i], true))
            } else {
                moviesList.add(mapFromService(moviesData[i], false))
            }
        }
        return moviesList
    }
}