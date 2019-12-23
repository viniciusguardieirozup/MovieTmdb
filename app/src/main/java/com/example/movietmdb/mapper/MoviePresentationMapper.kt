package com.example.movietmdb.mapper

import android.util.Log
import com.example.movietmdb.database.MovieData
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.retrofit.MovieService

class MoviePresentationMapper {
    fun mapFromService(seacrhMovie: MovieService): MoviePresentation {
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
            seacrhMovie.voteAverage
        )
    }

    fun mapFromData(dataMovie: MovieData): MoviePresentation {
        var aux : ArrayList<Int> = stringToArray(dataMovie.genreIds)
        return MoviePresentation(
            dataMovie.posterPath,
            dataMovie.adult,
            dataMovie.overView,
            dataMovie.releaseData,
            aux,
            dataMovie.id,
            dataMovie.originalTitle,
            dataMovie.originalLanguage,
            dataMovie.title,
            dataMovie.backdropPath,
            dataMovie.popularity,
            dataMovie.voteCount,
            dataMovie.video,
            dataMovie.voteAverage
        )

    }
    private fun stringToArray(ids:String):ArrayList<Int>{
        Log.v("ee",ids)
        val aux = ArrayList<Int>(0)
        val auxS = ids.replace("[","").replace("]","").split(",")
        val size = auxS.size-1
        for(i in 0..size){
            if(auxS[i].trim()!="")
                aux.add(auxS[i].trim().toInt())
        }

        return aux
    }
}