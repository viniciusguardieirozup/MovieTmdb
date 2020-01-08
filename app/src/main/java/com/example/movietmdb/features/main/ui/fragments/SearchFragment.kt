package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.SearchMoviesFragmentBinding
import com.example.movietmdb.features.main.viewmodel.SearchFragmentViewModel
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.FavButtonListener
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.data.MoviePresentation

class SearchFragment : Fragment() {

    private lateinit var binding: SearchMoviesFragmentBinding
    private lateinit var adapter: CustomAdapter
    private lateinit var movieName: String
    private lateinit var viewModel: SearchFragmentViewModel

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    //function to inflate layout into this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.search_movies_fragment,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CustomAdapter()
        adapter.setListener(object : FavButtonListener {
            override fun favButtonClicked(moviePresentation: MoviePresentation) {
                viewModel.setFavorite(moviePresentation)
            }

        })
        binding.recylerSearchMovie.adapter = adapter
        configViewModel()
        configSearchView()
    }

    private fun getTextToSearch() {
        binding.searchMovie.clearFocus()
        movieName = binding.searchMovie.query.toString()
        adapter.reset()
        viewModel.searchMovies(movieName)
    }

    private fun configureRecycler(results: ArrayList<MoviePresentation>?) {
        results?.let {
            adapter.addAll(results)
        }
        pagination()
    }

    private fun pagination() {
        binding.recylerSearchMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.searchMovies(movieName)
                }
            }
        })
    }

    private fun configViewModel() {
        viewModel = ViewModelProviders.of(this).get(SearchFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Loading) {
                if (it.loading) {
                    binding.pbSearch.visibility = View.VISIBLE
                } else {
                    binding.pbSearch.visibility = View.GONE
                }
            } else if (it is ViewState.Data) {
                try {
                    configureRecycler(it.movies as ArrayList<MoviePresentation>)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            } else if (it is ViewState.Error) {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configSearchView() {
        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getTextToSearch()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}


