package com.example.movietmdb.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.repository.retrofit.SearchResults
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class PaginationViewModelTest : BaseJUnitTest(){

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = pagination()

    @MockK
    private lateinit var searchResult : SearchResults

    @Test
    fun checkLastPage_isntLastPage_pageIncrement(){

        //GIVEN
        every { searchResult.totalPages } returns 3
        //WHEN
        viewModel.checkLastPage(searchResult)
        //THEN
        assertEquals(2,viewModel.returnPage())
        assertEquals(false,viewModel.returnLastPage())
    }

    @Test
    fun checkLastPage_isLastPage_pageIncrement(){

        //GIVEN
        every { searchResult.totalPages } returns 1
        //WHEN
        viewModel.checkLastPage(searchResult)
        //THEN
        assertEquals(1,viewModel.returnPage())
        assertEquals(true,viewModel.returnLastPage())
    }

    @Test
    fun noMoviesAvailable_noMoviesFound_error(){

        //GIVEN
        every { searchResult.totalResults } returns 0
        //WHEN
        viewModel.noMoviesAvailable(searchResult)
        //THEN
        assertEquals("Movies not found", (viewModel.moviesLiveData.value as ViewState.Error).message)
    }

    @Test
    fun noMorePageAvailable_noMorePages_error(){
        //GIVEN
        every { searchResult.totalPages } returns 1

        //WHEN
        viewModel.checkLastPage(searchResult)
        viewModel.noMorePageAvailable()
        //THEN
        assertEquals("No more movies", (viewModel.moviesLiveData.value as ViewState.Error).message)
    }

}

class pagination : PaginationViewModel()