package com.example.movietmdb.features.main.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.movietmdb.R
import com.example.movietmdb.presentation.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mActivityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java,
        false,
        true
    )

    @Test
    fun views_viewIsVisible() {
        //onView(withId(R.id.searchMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.recylerSearchMovie)).check(matches(isDisplayed()))
    }

    @Test
    fun searchMovie_itemDisplayed() {


    }
}