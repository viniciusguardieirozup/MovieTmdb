package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.movietmdb.R
import com.example.movietmdb.features.main.viewmodel.FavoritesFragmentViewModel
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.favorites_layout.*

class FavoritesFragment : Fragment() {

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
        val viewModel: FavoritesFragmentViewModel =
            ViewModelProviders.of(this).get(FavoritesFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Loading) {
                if (it.loading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
            else if(it is ViewState.Data){

                configRecycler(it.movies as ArrayList<MoviePresentation>)
            }
        })
        viewModel.getFavMovies()
    }

    private fun configRecycler(favMovies : ArrayList<MoviePresentation>) {
        val adapter = CostumAdapter()
        adapter.addAll(favMovies)
        rc.adapter = adapter
    }
}