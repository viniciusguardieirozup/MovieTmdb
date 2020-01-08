package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movietmdb.R
import com.example.movietmdb.databinding.MoviesByGenreFragmentBinding
import com.example.movietmdb.features.main.ui.viewpageradapter.GenresViewPagerAdapter
import com.example.movietmdb.features.main.viewmodel.MovieByGenresViewModel
import com.example.movietmdb.features.main.viewmodel.ViewStateGenre

class MovieByGenresFragment : Fragment() {

    private lateinit var binding: MoviesByGenreFragmentBinding

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
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.movies_by_genre_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProviders.of(this).get(MovieByGenresViewModel::class.java)
        viewModel.movieListData.observe(viewLifecycleOwner, Observer {
            if (it is ViewStateGenre.Loading) {
                if (it.loading) {
                    binding.pbMoviesByGenres.visibility = View.VISIBLE
                } else {
                    binding.pbMoviesByGenres.visibility = View.GONE
                }
            } else if (it is ViewStateGenre.Data) {
                val aux = it.genres
                val fm = fragmentManager
                fm?.let {
                    binding.vpGenres.adapter =
                        GenresViewPagerAdapter(fm, aux)
                }
                binding.tbMovies.setupWithViewPager(binding.vpGenres)
            }
        })
        viewModel.getGenres()
    }
}