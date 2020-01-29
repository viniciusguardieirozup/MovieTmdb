package com.example.movietmdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movietmdb.features.description.viewmodel.DescriptionViewModel
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import com.example.movietmdb.repository.retrofit.SearchResults
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DescriptionViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @InjectMockKs
    private lateinit var viewModel: DescriptionViewModel

    @MockK
    private lateinit var repositoryRules: RepositoryRules
    @MockK
    private lateinit var response: SearchResults
    @MockK
    private lateinit var movie: MoviePresentation
    @MockK
    private lateinit var observer: Observer<ViewState>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun testGetSimilar() {
        //GIVEN
        coEvery { repositoryRules.getSimilar(1, 1) } returns response
        //WHEN
        viewModel.getSimilar(1)
        //THEN

    }

    @Test
    fun dontSaveOnRepository() {


    }


}