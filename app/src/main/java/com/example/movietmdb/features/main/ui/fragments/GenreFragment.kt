package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.GenresFragmentBinding
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.FavButtonListener
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.data.MoviePresentation

class GenreFragment : Fragment() {

    lateinit var id: String
    private lateinit var viewModel: GenreViewModel
    private lateinit var adapter: CustomAdapter
    private lateinit var binding: GenresFragmentBinding

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
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.genres_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = CustomAdapter()

        binding.rcGenre.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(GenreViewModel::class.java)
        configObserverViewModel()
        viewModel.getMoviesByGenre(id.toInt())
    }

    private fun configObserverViewModel() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Loading) {
                if (it.loading) {
                    binding.progressBarGenre.visibility = View.VISIBLE
                } else {
                    binding.progressBarGenre.visibility = View.GONE
                }
            } else if (it is ViewState.Data) {
                try {
                    configureRecycler(it.movies as ArrayList<MoviePresentation>)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun configureRecycler(results: ArrayList<MoviePresentation>) {

        adapter.addAll(results)
        pagination()
    }

    private fun pagination() {
        binding.rcGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getMoviesByGenre(id.toInt())
                }
            }
        })
    }
}