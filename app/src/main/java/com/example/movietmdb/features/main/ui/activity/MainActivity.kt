package com.example.movietmdb.features.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityMainBinding
import com.example.movietmdb.features.main.ui.fragments.FavoritesFragment
import com.example.movietmdb.features.main.ui.fragments.MovieByGenresFragment
import com.example.movietmdb.features.main.ui.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //listener for bottom navegation
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favorites -> {
                    replaceFragment(FavoritesFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.registered -> {
                    replaceFragment(MovieByGenresFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.register -> {
                    replaceFragment(SearchFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                else -> return@OnNavigationItemSelectedListener false
            }
        }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(SearchFragment.newInstance())
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bnMenu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    //function to change the fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content_changer, fragment).commit()
    }
}
