package com.example.movietmdb.features.description.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.ViewState
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.MovieService
import com.example.movietmdb.repository.retrofit.SearchResults
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        viewModel.mutable.observeForever(viewStateResult)
    }

    @After
    fun removeObservers() {
        viewModel.mutable.removeObserver(viewStateResult)
    }

    //getSimilar
    @Test
    fun getSimilar_resultSuccessful_mutableData() {
        //GIVEN
        viewModel.loading = false
        viewModel.lastPage = false
        //WHEN
        viewModel.getSimilar()
        //THEN
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(presentation, (mutable[1] as ViewState.Data).movies)
        assertEquals(false, (mutable[2] as ViewState.Loading).loading)
    }

    //noMorePageAvailable
    @Test
    fun noMorePageAvailable_lastPageTrueLoadingFalse_mutableError() {
        //given
        viewModel.lastPage = true
        viewModel.loading = false
        //when
        viewModel.getSimilar()
        //then
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals("No more movies", (mutable[1] as ViewState.Error).message)
        assertEquals(false, (mutable[2] as ViewState.Loading).loading)

    }

    @Test
    fun noMorePageAvailable_lastPageTrueLoadingTrue_nothing() {
        //given
        viewModel.lastPage = true
        viewModel.loading = true
        //when
        viewModel.getSimilar()
        //then
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(false, (mutable[1] as ViewState.Loading).loading)
    }

    @Test
    fun noMorePageAvailable_lastPageFalseLoadingTrue_nothing() {
        //given
        viewModel.lastPage = false
        viewModel.loading = true

        //when
        viewModel.getSimilar()
        //then
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(false, (mutable[1] as ViewState.Loading).loading)
    }

    @Test
    fun noMorePageAvailable_lastPageFalseLoadingFalse_mutableData() {
        //given
        viewModel.lastPage = false
        viewModel.loading = false
        //when
        viewModel.getSimilar()
        //then
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(presentation, (mutable[1] as ViewState.Data).movies)
        assertEquals(false, (mutable[2] as ViewState.Loading).loading)
    }

    //checkLastPage
    @Test
    fun checkLastPage_isLastPage_viewModelLastPageTrue() {
        //given
        viewModel.lastPage = false
        every { moviesResults.totalPages } returns viewModel.page
        //when
        viewModel.getSimilar()
        //then
        assertEquals(true, viewModel.lastPage)
    }

    @Test
    fun checkLastPage_isntLastPage_viewModelLastPageFalsePageIncrement() {
        //given
        viewModel.lastPage = false
        viewModel.page = 0
        every { moviesResults.totalPages } returns 1
        //when
        viewModel.getSimilar()
        //then
        assertEquals(false, viewModel.lastPage)
        assertEquals(1, viewModel.page)
    }

    //accessRepositoryMapResult
    @Test
    fun accessRepositoryMapResult_lastPageFalseLoadingFalse_SearchResults() {
        //GIVEN
        viewModel.lastPage = false
        viewModel.loading = false
        //WHEN
        viewModel.getSimilar()
        //THEN
        assertEquals(presentation, (mutable[1] as ViewState.Data).movies)
    }

    //noMoviesResults
    @Test
    fun noMoviesResults_totalResults0_ViewStateError() {
        //GIVEN
        every { moviesResults.totalResults } returns 0
        viewModel.lastPage = false
        viewModel.loading = false
        viewModel.page = 0
        every { moviesResults.totalPages } returns 1
        //WHEN
        viewModel.getSimilar()
        //THEN
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(presentation, (mutable[1] as ViewState.Data).movies)
        assertEquals("Movies not found", (mutable[2] as ViewState.Error).message)
        assertEquals(false, (mutable[3] as ViewState.Loading).loading)
    }

    @Test
    fun noMoviesResults_totalResults1_ViewStateNoError() {
        //GIVEN
        viewModel.lastPage = false
        viewModel.loading = false
        every { moviesResults.totalResults } returns 1
        viewModel.lastPage = false
        viewModel.loading = false
        viewModel.page = 0
        every { moviesResults.totalPages } returns 1
        //WHEN
        viewModel.getSimilar()
        //THEN
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(presentation, (mutable[1] as ViewState.Data).movies)
        assertEquals(false, (mutable[2] as ViewState.Loading).loading)
    }

    //loadSimilar
    @Test
    fun loadSimilar_lastPageFalseLoadingFalse() {
        //GIVEN
        viewModel.lastPage = false
        viewModel.loading = false
        every { moviesResults.totalResults } returns 1
        viewModel.lastPage = false
        viewModel.loading = false
        viewModel.page = 0
        every { moviesResults.totalPages } returns 1
        //WHEN
        viewModel.getSimilar()
        //THEN
        assertEquals(false, viewModel.loading)
    }
}