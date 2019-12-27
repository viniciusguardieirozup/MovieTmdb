package com.example.movietmdb.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.movietmdb.retrofit.GenresList

class GenresViewPagerAdapter(fm: FragmentManager, val list : GenresList) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){


    override fun getItem(position: Int): Fragment {
        val frag = GenreFragment()
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