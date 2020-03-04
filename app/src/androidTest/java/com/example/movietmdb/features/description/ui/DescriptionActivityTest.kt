package com.example.movietmdb.features.description.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.movietmdb.presentation.activity.DescriptionActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DescriptionActivityTest{
    @get:Rule
    val mActivityRule = ActivityTestRule<DescriptionActivity>(
        DescriptionActivity::class.java,
        false,
        true)


}