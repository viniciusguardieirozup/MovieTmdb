package com.example.movietmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.repository.retrofit.GenresList
import com.example.movietmdb.repository.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.movies_by_genre_layout.*
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
            genresList = RetrofitInitializer()
                .retrofitServices.getGenres()
            Log.v("search", genresList.genres.size.toString())

            fm?.let {
                vpGenres.adapter =
                    GenresViewPagerAdapter(
                        fm,
                        genresList
                    )
            }
            tbMovies.setupWithViewPager(vpGenres)
            progressBar2.visibility = View.VISIBLE
        }
    }
}