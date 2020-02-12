package com.example.movietmdb.features.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.viewModel.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.SearchResults
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class SearchViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: SearchViewModel

    @MockK
    private lateinit var moviesRepository: MoviesRepository
    @MockK
    private lateinit var searchResults: SearchResults
    @MockK
    private lateinit var favMovies: List<MovieData>
    @MockK
    private lateinit var presentation: ArrayList<MoviePresentation>

    private val viewStateResult: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    private var mutable: MutableList<ViewState> = mutableListOf()

    @Before
    fun configObserver(){
        every {
            MoviePresentationMapper.convertListMovieService(
                searchResults.results,
                favMovies
            )
        } returns presentation
        mutable.clear()
        viewModel.moviesLiveData.observeForever(viewStateResult)
    }

    @After
    fun removeObservers() {
        viewModel.moviesLiveData.removeObserver(viewStateResult)
    }

    @Test
    fun checkLastPage_isntLastPage_pageIncrement() {
        //GIVEN
        coEvery { moviesRepository.getMovies(any(), any()) } returns searchResults
        coEvery { moviesRepository.getFavMovies() } returns favMovies
        every {searchResults.totalPages } returns 3
        every {searchResults.totalResults } returns 10

        //WHEN
        viewModel.searchMovies("")
        val result = viewModel.returnPage()
        //THEN
        assertEquals(2,result)
    }

    @Test
    fun checkLastPage_isLastPage_mutableError() {
        //GIVEN
        coEvery { moviesRepository.getMovies(any(), any()) } returns searchResults
        coEvery { moviesRepository.getFavMovies() } returns favMovies
        every {searchResults.totalPages } returns 1
        every {searchResults.totalResults } returns 10

        //WHEN
        viewModel.searchMovies("")
        viewModel.searchMovies("")

        //THEN
        assertEquals(true,(mutable[0] as ViewState.Loading).loading)
        assertEquals(presentation,(mutable[1] as ViewState.Data).movies)
        assertEquals(false,(mutable[2] as ViewState.Loading).loading)
        assertEquals("No more movies",(mutable[3] as ViewState.Error).message)
    }
}