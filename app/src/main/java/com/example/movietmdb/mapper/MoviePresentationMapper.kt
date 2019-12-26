package com.example.movietmdb.mapper

import android.util.Log
import com.bumptech.glide.Glide
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.database.MovieData
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.retrofit.MovieService
import kotlinx.coroutines.launch

class MoviePresentationMapper {
    fun mapFromService(seacrhMovie: MovieService,fav : Boolean): MoviePresentation {
        return MoviePresentation(
            seacrhMovie.posterPath,
            seacrhMovie.adult,
            seacrhMovie.overView,
            seacrhMovie.releaseData,
            seacrhMovie.genreIds,
            seacrhMovie.id,
            seacrhMovie.originalTitle,
            seacrhMovie.originalLanguage,
            seacrhMovie.title,
            seacrhMovie.backdropPath,
            seacrhMovie.popularity,
            seacrhMovie.voteCount,
            seacrhMovie.video,
            seacrhMovie.voteAverage,
            fav
        )
    }

    private fun mapFromData(dataMovie: MovieData, fav : Boolean): MoviePresentation {

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
            dataMovie.voteAverage,
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
            moviesList.add(mapFromData(movies[i],true))
        }
        return moviesList
    }
    fun convertListMovieService(movies: List<MovieService>, fav: List<MovieData>): ArrayList<MoviePresentation> {
        val moviesList = ArrayList<MoviePresentation>()
        val moviesData = MovieDataMapper().convertListMovieService(movies)

        val size = movies.size - 1
        for (i in 0..size) {
            if(fav.contains(moviesData[i])) {
                moviesList.add(mapFromData(moviesData[i],true))
            } else{
                moviesList.add(mapFromData(moviesData[i],false))
            }
        }
        return moviesList
    }
}