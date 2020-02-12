package com.example.movietmdb.mappers

import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService
import org.junit.Assert.*
import org.junit.Test

class MovieDataMapperTest : BaseJUnitTest() {

    @Test
    fun mapFromPresentation_checkReturn() {
        //GIVEN
        val aux = ArrayList<Int>()
        aux.add(10)
        val moviePresentation = MoviePresentation(
            null,
            null,
            null,
            null,
            aux,
            42,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "10",
            true,
            0
        )

        //WHEN
        val result = MovieDataMapper.mapFromPresentation(moviePresentation)

        //THEN
        assertEquals(null, result.adult)
        assertEquals(null, result.backdropPath)
        assertEquals("[10]", result.genreIds)
        assertEquals(42, result.id)
        assertEquals("10.0", result.voteAverage.toString())
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalTitle)
        assertEquals(null, result.overView)
        assertEquals(null, result.popularity)
        assertEquals(null, result.posterPath)
        assertEquals(null, result.releaseData)
        assertEquals(null, result.title)
        assertEquals(null, result.video)

    }

    @Test
    fun convertListMovieService_checkAssert() {
        //GIVEN
        val listMovieService = ArrayList<MovieService>()
        val movieService = movieService()
        for (i in 0..2)
            listMovieService.add(movieService)
        //WHEN
        val result = MovieDataMapper.convertListMovieService(listMovieService)

        //THEN
        for (i in 0..2){
            assertMapFromMovieService(result = result[i])
        }

    }

    fun movieService() : MovieService{
        val aux = ArrayList<Int>()
        aux.add(10)
        return MovieService(
            null,
            null,
            null,
            null,
            aux,
            42,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            10.0
        )
    }

    private fun assertMapFromMovieService(result : MovieData){
        assertEquals(null, result.adult)
        assertEquals("https://image.tmdb.org/t/p/original/null", result.backdropPath)
        assertEquals("[10]", result.genreIds)
        assertEquals(42, result.id)
        assertEquals("10.0", result.voteAverage.toString())
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalTitle)
        assertEquals(null, result.overView)
        assertEquals(null, result.popularity)
        assertEquals(null, result.posterPath)
        assertEquals(null, result.releaseData)
        assertEquals(null, result.title)
        assertEquals(null, result.video)
    }
}