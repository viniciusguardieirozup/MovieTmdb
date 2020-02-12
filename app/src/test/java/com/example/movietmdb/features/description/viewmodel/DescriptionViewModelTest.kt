package com.example.movietmdb.features.description.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.ViewState
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DescriptionViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var moviesRepository: MoviesRepository

    @InjectMockKs
    private lateinit var viewModel: DescriptionViewModel

    @MockK
    private lateinit var moviesResults: SearchResults
    @MockK
    private lateinit var favMovies: List<MovieData>
    @MockK
    private lateinit var presentation: ArrayList<MoviePresentation>
    @MockK
    private lateinit var services: ArrayList<MovieService>

    private val viewStateResult: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    private var mutable: MutableList<ViewState> = mutableListOf()


    @Before
    fun configMocks() {
        coEvery { moviesRepository.getSimilar(any(), any()) } returns moviesResults
        every { moviesResults.results } returns services
        coEvery { moviesRepository.getFavMovies() } returns favMovies
        every {
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        } returns presentation

        mutable.clear()
        viewModel.moviesLiveData.observeForever(viewStateResult)
    }

    @After
    fun removeObservers() {
        mutable.clear()
        viewModel.moviesLiveData.removeObserver(viewStateResult)
    }

    @Test
    fun accessRepositoryMapResult_checkReturn_checkMutable() = runBlockingTest {
        //GIVEN
        coEvery { moviesRepository.getSimilar(any(), any()) } returns moviesResults
        coEvery { moviesRepository.getFavMovies() } returns favMovies
        every {
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        } returns presentation

        //WHEN
        val result = viewModel.accessRepositoryMapResult()

        //THEN
        assertEquals(moviesResults, result)
        assertEquals(presentation, (mutable[0] as ViewState.Data).movies)
    }

    @Test
    fun getSimilar_checkMutable() = runBlockingTest {
        //GIVEN
        coEvery { viewModel.accessRepositoryMapResult() } returns moviesResults
        every { moviesResults.totalResults } returns 1
        every { moviesResults.totalPages } returns 3

        //WHEN
        viewModel.getSimilar()

        //THEN

    }

}