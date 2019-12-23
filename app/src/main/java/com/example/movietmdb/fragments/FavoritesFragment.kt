package com.example.movietmdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.database.MovieData
import com.example.movietmdb.mapper.MoviePresentationMapper
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.favorites_layout.*
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var favMovies: ArrayList<MoviePresentation> = ArrayList(0)

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.favorites_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val thread = DataBaseThread(MovieTmdbApplication.db.movieDao())

        thread.launch {
            thread.auxAllFavoritesMovies()
            converter(thread.list)
            configRecycler()
            thread.stopJob()
        }
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    private fun converter(movies: List<MovieData>) {
        val size = movies.size - 1
        for (i in 0..size) {
            favMovies.add(MoviePresentationMapper().mapFromData(movies[i]))
        }
    }

    private fun configRecycler() {
        val adapter = CostumAdapter(favMovies)
        rc.adapter = adapter
    }
}