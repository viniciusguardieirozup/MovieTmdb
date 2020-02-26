package com.example.movietmdb.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseMovieViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val viewModel = BaseMovieViewModel()

    @Test
    fun load_NothrowException() = runBlockingTest {
        //GIVEN

        //WHEN
        viewModel.load(suspend {

        })
        //THEN
        assertEquals(false, viewModel.loading)
    }

    @Test
    fun load_throwException() = runBlockingTest {
        //GIVEN

        //WHEN
        viewModel.load(suspend {
            throw Exception()
        })
        //THEN
        assertEquals(
            "Problem to find this movie",
            (viewModel.moviesLiveData.value as ViewState.Error).message
        )
        assertEquals(false, viewModel.loading)
    }
}