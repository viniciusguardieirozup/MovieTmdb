package com.example.movietmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movietmdb.fragments.FavoritesFragment
import com.example.movietmdb.fragments.RegisteredFragment
import com.example.movietmdb.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //listener for bottom navegation
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favorites -> {
                    replaceFragment(FavoritesFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.registered -> {
                    replaceFragment(RegisteredFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.register -> {
                    replaceFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                else -> return@OnNavigationItemSelectedListener false
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(SearchFragment())
        bn_menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    //function to change the fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content_changer, fragment).commit()
    }

}
