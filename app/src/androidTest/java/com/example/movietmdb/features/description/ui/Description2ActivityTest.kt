package com.example.movietmdb.features.description.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Description2ActivityTest{
    @Rule
    val mActivityRule = ActivityTestRule<Description2Activity>(Description2Activity::class.java,
        false,
        true)

    @Test
    fun testViews(){
        onView(with)
    }
}