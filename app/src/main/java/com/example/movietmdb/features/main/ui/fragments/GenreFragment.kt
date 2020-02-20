package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.movietmdb.R
import com.example.movietmdb.databinding.FragmentGenresBinding
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.mappers.GenrePresentationMapper
import com.example.movietmdb.recycler.adapter.GenreAdapter
import com.example.movietmdb.repository.retrofit.GenresList
import com.example.movietmdb.viewModel.ViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreFragment : Fragment() {

    private val viewModel: GenreViewModel by viewModel()
    private val adapter: GenreAdapter by inject()
    private lateinit var binding: FragmentGenresBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_genres, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getGenres()
        configObserverViewModel()
    }

    private fun configObserverViewModel() {
        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewState.Genre) {
                configureRecycler(it.genres)
            } else if (it is ViewState.Loading) {
                if (it.loading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rcGenre.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.rcGenre.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun configureRecycler(results: GenresList) {
        adapter.addAll(GenrePresentationMapper.convertList(results))
        binding.rcGenre.adapter = adapter
    }
}