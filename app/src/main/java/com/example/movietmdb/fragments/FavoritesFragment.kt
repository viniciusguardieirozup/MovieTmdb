package com.example.movietmdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.mapper.MoviePresentationMapper
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.favorites_layout.*
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var favMovies: ArrayList<MoviePresentation> = ArrayList(0)
    val thread = DataBaseThread()

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

        val db = MovieTmdbApplication.db.movieDao()
        thread.launch {
            val list = db.getAll()
            favMovies = MoviePresentationMapper().converterListMovieData(list)
            configRecycler()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        thread.stopJob()
    }


    private fun configRecycler() {
        progressBar.visibility = View.GONE
        val adapter = CostumAdapter()
        adapter.addAll(favMovies)
        rc.adapter = adapter
    }
}