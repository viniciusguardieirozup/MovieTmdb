package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movietmdb.R
import com.example.movietmdb.features.main.ui.viewpageradapter.GenresViewPagerAdapter
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.features.main.viewmodel.ViewStateGenre
import kotlinx.android.synthetic.main.movies_by_genre_layout.*

class MovieByGenresFragment : Fragment() {

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
        val viewModel = ViewModelProviders.of(this).get(GenreViewModel::class.java)
        viewModel.movieListData.observe(viewLifecycleOwner, Observer {
            if (it is ViewStateGenre.Loading) {
                if (it.loading) {
                    progressBar2.visibility = View.VISIBLE
                } else {
                    progressBar2.visibility = View.GONE
                }
            } else if (it is ViewStateGenre.Data) {
                val aux = it.genres
                val fm = fragmentManager
                fm?.let {
                    vpGenres.adapter =
                        GenresViewPagerAdapter(
                            fm,
                            aux
                        )
                }
                tbMovies.setupWithViewPager(vpGenres)
            }
        })
        viewModel.getGenres()
    }
}