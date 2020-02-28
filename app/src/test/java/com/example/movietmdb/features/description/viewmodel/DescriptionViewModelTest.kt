package com.example.movietmdb.features.description.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class DescriptionViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var movie: MoviePresentation

    @MockK
    private lateinit var moviesRepository: MoviesRepository
    @MockK
    private lateinit var movieData : MovieData

    @InjectMockKs
    private lateinit var viewModel: DescriptionViewModel


    @Before
    fun configViewModel() = runBlockingTest{

        every {movie.backdropPath} returns ""
        coEvery { moviesRepository.getAMovie(movie.id) } returns movieData
        viewModel.startViewModel(movie)

    }
}