package com.example.movietmdb.feature.main.ui.fragments.searchmovies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.feature.main.ui.fragments.searchmovies.viewmodel.SearchFragmentViewModel
import com.example.movietmdb.feature.main.ui.fragments.searchmovies.viewmodel.ViewState
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.search_movies_layout.*

//fragment for  searchMovies
class SearchFragment : Fragment() {

    private lateinit var adapter: CostumAdapter
    private lateinit var movieName: String
    private var page = 1
    private var loading = false
    private lateinit var viewModel: SearchFragmentViewModel

    //static function
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
        return View.inflate(context, R.layout.search_movies_layout, null)
    }

    //function called when this fragment was created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CostumAdapter()
        recylerSearchMovie.adapter = adapter
        viewModel =
            ViewModelProviders.of(this).get(SearchFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Loading) {
                if (it.loading) {
                    progressBar3.visibility = View.VISIBLE
                    loading = true
                } else {
                    progressBar3.visibility = View.GONE
                    loading = false
                }
            } else if (it is ViewState.Data) {
                configureRecycler(it.movies as ArrayList<MoviePresentation>)
            }
        })

        searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getTextToSearch()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    //function to get the movie name typed by the user and call getResultsRetrofit
    private fun getTextToSearch() {
        searchMovie.clearFocus()
        movieName = searchMovie.query.toString()
        page = 1
        viewModel.searchMovies(movieName, page)
    }

    private fun configureRecycler(results: ArrayList<MoviePresentation>?) {
        results?.let {
            adapter.addAll(results)
        }
        pagination()
    }

    private fun pagination() {
        recylerSearchMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !loading) {
                    page++
                    Log.v("teste",page.toString())
                    viewModel.searchMovies(movieName, page)
                }
            }
        })
    }
}


