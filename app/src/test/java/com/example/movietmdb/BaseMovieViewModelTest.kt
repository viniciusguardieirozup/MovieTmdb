package com.example.movietmdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@ExperimentalCoroutinesApi
class BaseMovieViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: BaseMovieViewModel

    private val viewStateResult: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    private var mutable: MutableList<ViewState> = mutableListOf()

    @Before
    fun configMutable() {
        mutable.clear()
        viewModel.moviesLiveData.observeForever(viewStateResult)
    }

    @After
    fun removeObserver() {
        viewModel.moviesLiveData.removeObserver(viewStateResult)
    }

    @Test
    fun load_executeOneBlockSuccessful_loadingEndsFalse() {
        //GIVEN

        //WHEN
        viewModel.load { blockTest() }

        //THEN
        assertEquals(false, viewModel.loading)

    }

    @Test
    fun load_executeOneBlockWithException_loadingEndsFalseMutableError() = runBlockingTest {
        //GIVEN

        //WHEN
        viewModel.load { blockTestException() }

        //THEN
        assertEquals(false, viewModel.loading)
        assertEquals("Problem to find this movie", (mutable[0] as ViewState.Error).message)
    }

    private suspend fun blockTest(): Unit = suspendCancellableCoroutine {
        it.resume(Unit)
    }

    private suspend fun blockTestException(): Unit = suspendCancellableCoroutine {
        it.resumeWithException(Exception())
    }
}