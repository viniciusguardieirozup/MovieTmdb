package com.example.movietmdb

import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest

open class BaseKoinTest  : AutoCloseKoinTest(){

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
    }

}