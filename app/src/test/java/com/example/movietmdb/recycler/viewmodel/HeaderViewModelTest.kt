package com.example.movietmdb.recycler.viewmodel

import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

class HeaderViewModelTest : BaseCoroutineTest(){

    @InjectMockKs
    private lateinit var viewModel: HeaderViewModel

    @MockK
    private lateinit var movie : MoviePresentation
    @MockK
    private lateinit var moviesRepository : MoviesRepository
    @MockK
    private lateinit var movieData : MovieData

    @Test
    fun setFavorite_MovieFavortieTrue_returnMovieFavortieFalse() = runBlockingTest{
        //GIVEN
        var movie = createMoviePresentation()
        movie.favorite = true
        coEvery{moviesRepository.removeMovie(any())} just Runs

        //WHEN
        viewModel.setFavorite(movie)

        //THEN
        assertEquals(false,movie.favorite)
    }

    @Test
    fun setFavorite_MovieFavortieFalse_returnMovieFavortieTrue() = runBlockingTest{
        //GIVEN
        var movie = createMoviePresentation()
        movie.favorite = false
        coEvery{moviesRepository.insertMovie(any())} just Runs

        //WHEN
        viewModel.setFavorite(movie)

        //THEN
        assertEquals(true,movie.favorite)
    }

    private fun createMoviePresentation() : MoviePresentation{
        val aux = ArrayList<Int>()
        aux.add(0)
        return MoviePresentation(null,
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
    }
}