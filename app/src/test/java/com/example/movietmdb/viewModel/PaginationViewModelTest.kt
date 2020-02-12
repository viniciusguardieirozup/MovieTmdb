package com.example.movietmdb.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.repository.retrofit.SearchResults
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.*
import org.junit.Assert.*

class PaginationViewModelTest : BaseJUnitTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: createPaginationViewModel

    @MockK
    private lateinit var moviesResults: SearchResults


    private val viewStateResults: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    private val mutable: MutableList<ViewState> = mutableListOf()

    @Before
    fun configObserver() {
        mutable.clear()
        viewModel.moviesLiveData.observeForever(viewStateResults)
    }

    @After
    fun removeObserver() {
        viewModel.moviesLiveData.removeObserver(viewStateResults)
    }

    @Test
    fun checkLastPage_isLastPage_lastPageTruePageDontIncrement() {
        //GIVEN
        every { moviesResults.totalPages } returns 1

        //WHEN
        viewModel.checkLastPage(moviesResults)
        val result = viewModel.returnLastPage()
        val page = viewModel.returnPage()

        //THEN
        assertTrue(result)
        assertEquals(1, page)
    }

    @Test
    fun checkLastPage_isntLastPage_lastPageFalsePageIncrement() {
        //GIVEN
        every { moviesResults.totalPages } returns 2

        //WHEN
        viewModel.checkLastPage(moviesResults)
        val result = viewModel.returnLastPage()
        val page = viewModel.returnPage()

        //THEN
        assertFalse(result)
        assertEquals(2, page)
    }

    @Test
    fun noMoviesAvailable_moviesFounded_noError() {
        //GIVEN
        every { moviesResults.totalResults } returns 1

        //WHEN
        viewModel.noMoviesAvailable(moviesResults)
        val mutableSize = mutable.size
        //THEN
        assertEquals(0, mutableSize)
    }

    @Test
    fun noMoviesAvailable_moviesNotFounded_Error() {
        //GIVEN
        every { moviesResults.totalResults } returns 0

        //WHEN
        viewModel.noMoviesAvailable(moviesResults)
        //THEN
        assertEquals("Movies not found", (mutable[0] as ViewState.Error).message)
    }

    @Test
    fun noMorePageAvailable_isntLastPage_noError(){
        //GIVEN

        //WHEN
        viewModel.noMorePageAvailable()
        val mutableSize = mutable.size

        //THEN
        assertEquals(0,mutableSize)
    }

    @Test
    fun noMorePageAvailable_isLastPage_error(){
        //GIVEN
        every { moviesResults.totalPages } returns 1
        //WHEN
        viewModel.checkLastPage(moviesResults)
        viewModel.noMorePageAvailable()

        //THEN
        assertEquals("No more movies",(mutable[0] as ViewState.Error).message)
    }

    class createPaginationViewModel : PaginationViewModel() {

    }
}