package com.example.movietmdb.recycler.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.ViewState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GenreRecyclerViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: GenreRecyclerViewModel

    @MockK
    private lateinit var moviesRepository: MoviesRepository
    @MockK
    private lateinit var searchResults: SearchResults
    @MockK
    private lateinit var listMovieData: List<MovieData>
    @MockK
    private lateinit var moviePresentation: ArrayList<MoviePresentation>

    val viewSateResult: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    val mutable: MutableList<ViewState> = mutableListOf()

    @Before
    fun configMutable() {
        viewModel.moviesLiveData.observeForever(viewSateResult)
    }

    @After
    fun removeMutable() {
        viewModel.moviesLiveData.removeObserver(viewSateResult)
    }

    @Test
    fun getMoviesByGenres_isntLastPage_mutableData() = runBlockingTest {
        //GIVEN
        coEvery { moviesRepository.getMoviesByGenres(any(), any()) } returns searchResults
        coEvery { moviesRepository.getFavMovies() } returns listMovieData
        every { searchResults.totalResults } returns 1
        every { searchResults.totalPages } returns 3
        every { MoviePresentationMapper.convertListMovieService(
            searchResults.results,
            listMovieData
        ) } returns moviePresentation

        //WHEN
        viewModel.getMoviesByGenres(10)

        //THEN
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(moviePresentation, (mutable[1] as ViewState.Data).movies)
        assertEquals(false, (mutable[2] as ViewState.Loading).loading)

    }


}