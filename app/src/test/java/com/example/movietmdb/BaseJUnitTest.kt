package com.example.movietmdb

import io.mockk.MockKAnnotations
import org.junit.Before

abstract class BaseJUnitTest {

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }
}