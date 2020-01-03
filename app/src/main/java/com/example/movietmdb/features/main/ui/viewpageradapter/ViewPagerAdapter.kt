package com.example.movietmdb.features.main.ui.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.movietmdb.features.main.ui.fragments.FavoritesFragment
import com.example.movietmdb.features.main.ui.fragments.MovieByGenresFragment
import com.example.movietmdb.features.main.ui.fragments.SearchFragment

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoritesFragment.newInstance()
            1 -> SearchFragment.newInstance()
            else -> MovieByGenresFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Favorites"
            1 -> "Register"
            else -> "Registered"
        }
    }
}