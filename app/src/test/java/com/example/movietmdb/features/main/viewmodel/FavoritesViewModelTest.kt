package com.example.movietmdb.features.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietmdb.BaseCoroutineTest
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.viewModel.ViewState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoritesViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    private lateinit var viewModel: FavoritesViewModel

    @MockK
    private lateinit var moviesRepository: MoviesRepository
    @MockK
    lateinit var moviesPresentation: ArrayList<MoviePresentation>

    private val ViewStateResult: (t: ViewState) -> Unit = {
        mutable.add(it)
    }

    private val mutable: MutableList<ViewState> = mutableListOf()

    @Before
    fun configObserver() {
        mutable.clear()
        viewModel.moviesLiveData.observeForever(ViewStateResult)
    }

    @After
    fun removeObserver() {
        viewModel.moviesLiveData.removeObserver(ViewStateResult)
    }

    @Test
    fun getFavMovies_checkMutable() = runBlockingTest {
        //GIVEN
        coEvery { MoviePresentationMapper.converterListMovieData(moviesRepository.getFavMovies()) } returns moviesPresentation

        //WHEN
        viewModel.getFavMovies()

        //THEN
        assertEquals(true, (mutable[0] as ViewState.Loading).loading)
        assertEquals(moviesPresentation, (mutable[1] as ViewState.Data).movies)
        assertEquals(false, (mutable[2] as ViewState.Loading).loading)

    }
}