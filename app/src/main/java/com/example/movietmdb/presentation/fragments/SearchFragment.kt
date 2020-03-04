package com.example.movietmdb.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.FragmentSearchMoviesBinding
import com.example.movietmdb.presentation.viewmodel.ViewState
import com.example.movietmdb.presentation.viewmodel.SearchViewModel
import com.example.movietmdb.presentation.recycler.adapter.CustomAdapter
import com.example.movietmdb.presentation.recycler.data.MoviePresentation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchMoviesBinding
    private val adapter: CustomAdapter by inject()
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_search_movies,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recylerSearchMovie.adapter = adapter
        pagination()
        configViewModel()
        observerMovieName()
    }

    private fun observerMovieName() {
        viewModel.movieName.observe(viewLifecycleOwner, Observer {
            viewModel.searchMovies()
        })
    }

    private fun configureRecycler(results: ArrayList<MoviePresentation>?) {
        results?.let {
            adapter.addAll(results)
        }
    }

    private fun pagination() {
        binding.recylerSearchMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    try {
                        viewModel.searchMovies()
                    }catch (e : Exception){
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun configViewModel() {
        binding.viewModel = viewModel
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    if (it.loading) {
                        binding.pbSearch.visibility = View.VISIBLE
                    } else {
                        binding.pbSearch.visibility = View.GONE
                    }
                }
                is ViewState.Data -> {
                    try {
                        configureRecycler(it.movies as ArrayList<MoviePresentation>)
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                }
                is ViewState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.resetAdapter.observe(viewLifecycleOwner, Observer {
            if (it){
                adapter.reset()
            }
        })
    }
}


