package com.example.movietmdb.features.main.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.movietmdb.R
import com.example.movietmdb.databinding.GenresFragmentBinding
import com.example.movietmdb.features.main.viewmodel.GenreViewModel
import com.example.movietmdb.features.main.viewmodel.ViewStateGenre
import com.example.movietmdb.mappers.GenrePresentationMapper
import com.example.movietmdb.recycler.adapter.GenreAdapter
import com.example.movietmdb.repository.retrofit.GenresList
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreFragment : Fragment() {

    private val viewModel: GenreViewModel by viewModel()
    private val adapter: GenreAdapter by inject()
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
        adapter.lifeCicle = viewLifecycleOwner
        binding.rcGenre.adapter = adapter
        viewModel.getGenres()
        configObserverViewModel()
    }

    private fun configObserverViewModel() {
        viewModel.genreLiveData.observe(viewLifecycleOwner, Observer {
            if (it is ViewStateGenre.Data) {
                configureRecycler(it.genres)
            }
        })
    }

    private fun configureRecycler(results: GenresList) {
        adapter.addAll(GenrePresentationMapper().convertList(results))
        binding.rcGenre.adapter = adapter
    }
}