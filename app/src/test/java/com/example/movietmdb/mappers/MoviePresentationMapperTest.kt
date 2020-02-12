package com.example.movietmdb.mappers

import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService
import org.junit.Assert.*
import org.junit.Test

class MoviePresentationMapperTest : BaseJUnitTest() {

    @Test
    fun convertListMovieData_returnArrayListMoviePresentation() {
        //GIVEN
        val aux = ArrayList<MovieData>()
        val movieData = createMovieData()

        for (i in 0..2) {
            aux.add(movieData)
        }

        //WHEN
        val result = MoviePresentationMapper.converterListMovieData(aux)

        //THEN
        for (i in 0..2)
            assertMapFromMovieData(result[i])
    }

    @Test
    fun convertListMovieService_returnArrayListMoviePresentation() {
        //GIVEN
        val listMovieService = ArrayList<MovieService>()
        val listMovieData = ArrayList<MovieData>()

        val movieData = createMovieData()
        val movieService = createMovieService()

        for (i in 0..2) {
            listMovieData.add(movieData)
            listMovieService.add(movieService)
        }

        //WHEN
        val result =
            MoviePresentationMapper.convertListMovieService(
                listMovieService,
                listMovieData
            )

        //THEN
        for (i in 0..2)
            assertMapFromMovieService(result[i])
    }

    private fun assertMapFromMovieService(result: MoviePresentation) {
        val aux = ArrayList<Int>()
        aux.add(10)
        assertEquals(null, result.adult)
        assertEquals("https://image.tmdb.org/t/p/original/null", result.backdropPath)
        assertEquals(aux, result.genreIds)
        assertEquals(42, result.id)
        assertEquals("100", result.voteAverage)
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalTitle)
        assertEquals(null, result.overView)
        assertEquals(null, result.popularity)
        assertEquals(null, result.posterPath)
        assertEquals(null, result.releaseData)
        assertEquals(null, result.title)
        assertEquals(null, result.video)
        assertEquals(true, result.favorite)

    }


    private fun createMovieService(): MovieService {
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


    private fun assertMapFromMovieData(result: MoviePresentation) {
        val aux = ArrayList<Int>()
        aux.add(10)
        assertEquals(null, result.adult)
        assertEquals(null, result.backdropPath)
        assertEquals(aux, result.genreIds)
        assertEquals(42, result.id)
        assertEquals("10", result.voteAverage)
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalLanguage)
        assertEquals(null, result.originalTitle)
        assertEquals(null, result.overView)
        assertEquals(null, result.popularity)
        assertEquals(null, result.posterPath)
        assertEquals(null, result.releaseData)
        assertEquals(null, result.title)
        assertEquals(null, result.video)
        assertEquals(true, result.favorite)
    }


    private fun createMovieData(): MovieData {
        return MovieData(
            null,
            null,
            null,
            null,
            "[10]",
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
}