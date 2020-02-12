package com.example.movietmdb.features.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.GenresList
import com.example.movietmdb.repository.retrofit.MovieService
import com.example.movietmdb.viewModel.ViewState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.http.GET

class GenreViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: GenreViewModel

    @MockK
    private lateinit var genreList: GenresList
    @MockK
    private lateinit var moviesRepository: MoviesRepository

    private val viewStateResult: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    private val mutable: MutableList<ViewState> = mutableListOf()

    @Before
    fun configObserver() {
        mutable.clear()
        viewModel.moviesLiveData.observeForever(viewStateResult)
    }

    @After
    fun removeObsever() {
        viewModel.moviesLiveData.removeObserver(viewStateResult)
    }

    @Test
    fun getGenres_checkMutable() = runBlockingTest {
        //GIVEN
        coEvery { moviesRepository.getGenres() } returns genreList

        //WHEN
        viewModel.getGenres()

        //THEN
        assertEquals(true,(mutable[0] as ViewState.Loading).loading)
        assertEquals(genreList,(mutable[1] as ViewState.Genre).genres)
        assertEquals(false,(mutable[2] as ViewState.Loading).loading)
    }
}