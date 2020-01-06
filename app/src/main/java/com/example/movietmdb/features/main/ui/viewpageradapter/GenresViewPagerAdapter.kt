package com.example.movietmdb.features.main.ui.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.movietmdb.features.main.ui.fragments.GenreFragment
import com.example.movietmdb.repository.retrofit.GenresList

class GenresViewPagerAdapter(fm: FragmentManager, val list: GenresList) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        val frag =
            GenreFragment.newInstance()
        frag.id = list.genres[position].id.toString()
        return frag

    }

    override fun getCount(): Int {
        return list.genres.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list.genres[position].name
    }
}