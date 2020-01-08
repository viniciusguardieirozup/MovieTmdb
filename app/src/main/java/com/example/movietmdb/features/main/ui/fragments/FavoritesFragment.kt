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
import com.example.movietmdb.databinding.FavoritesFragmentBinding
import com.example.movietmdb.features.main.viewmodel.FavoritesViewModel
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.FavButtonListener
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.data.MoviePresentation

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding: FavoritesFragmentBinding

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
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.favorites_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel =
            ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        configObserver()
        viewModel.getFavMovies()
    }

    private fun configObserver() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Loading) {
                if (it.loading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            } else if (it is ViewState.Data) {
                configRecycler(it.movies as ArrayList<MoviePresentation>)
            }
        })
    }

    private fun configRecycler(favMovies: ArrayList<MoviePresentation>) {
        val adapter = CustomAdapter()
        adapter.setListener(object : FavButtonListener {
            override fun favButtonClicked(moviePresentation: MoviePresentation) {
                viewModel.setFavorite(moviePresentation)
            }
        })
        adapter.addAll(favMovies)
        binding.rc.adapter = adapter
    }
}