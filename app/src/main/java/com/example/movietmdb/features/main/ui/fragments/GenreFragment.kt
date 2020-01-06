package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.features.main.viewmodel.GenreFragmentViewModel
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.genres_layout.*

class GenreFragment : Fragment() {

    lateinit var id: String
    private var page = 1
    private lateinit var viewModel: GenreFragmentViewModel
    private var lastPage = false
    private var loading = false
    private var adapter: CostumAdapter =
        CostumAdapter()

    companion object {
        fun newInstance(): GenreFragment {
            return GenreFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.genres_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcGenre.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(GenreFragmentViewModel::class.java)
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Loading) {
                if (it.loading) {
                    loading = true
                    progressBarGenre.visibility = View.VISIBLE
                } else {
                    loading = false
                    progressBarGenre.visibility = View.GONE
                }
            } else if (it is ViewState.Data) {
                configureRecycler(it.movies as ArrayList<MoviePresentation>)
            }
        })
        viewModel.getMoviesByGenre(id.toInt(), page)
    }


    //function to configure the recycler view
    private fun configureRecycler(results: ArrayList<MoviePresentation>?) {
        if (results == null) {
            Toast.makeText(context, "No more movies found", Toast.LENGTH_SHORT).show()
            lastPage = true
        } else {
            adapter.addAll(results)
        }
        pagination()
    }

    private fun pagination() {
        rcGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !lastPage && !loading) {
                    page++
                    viewModel.getMoviesByGenre(id.toInt(), page)
                }
            }
        })
    }

}