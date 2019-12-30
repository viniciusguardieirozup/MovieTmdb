package com.example.movietmdb.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.retrofit.GenresList
import com.example.movietmdb.retrofit.RetrofitInitializer
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.movies_by_genre_layout.*
import kotlinx.android.synthetic.main.search_movies_layout.*
import kotlinx.coroutines.launch

class MovieByGenresFragment : Fragment() {
    lateinit var genresList: GenresList

    companion object {
        fun newInstance(): MovieByGenresFragment {
            return MovieByGenresFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.movies_by_genre_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val thread = DataBaseThread()
        val fm = fragmentManager
        thread.launch {
            progressBar2.visibility = View.VISIBLE
            genresList = RetrofitInitializer().retrofitServices.getGenres()
            Log.v("search", genresList.genres.size.toString())

            fm?.let {
                vpGenres.adapter = GenresViewPagerAdapter(fm, genresList)
            }
            tbMovies.setupWithViewPager(vpGenres)
            progressBar2.visibility = View.VISIBLE
        }
    }
}