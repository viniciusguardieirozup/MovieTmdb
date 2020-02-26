package com.example.movietmdb

import com.example.movietmdb.mappers.MoviePresentationMapper
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class BaseCoroutineTest : BaseJUnitTest() {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(testCoroutineDispatcher)
        mockkObject(MoviePresentationMapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}