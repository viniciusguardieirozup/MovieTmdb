package com.example.movietmdb.recycler.holder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.databinding.ItemMoviesGenresLayoutBinding
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.data.GenrePresentation
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.viewmodel.GenreRecyclerViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class GenresViewHolder(val binding: ItemMoviesGenresLayoutBinding, val lifecycle: LifecycleOwner) :
    RecyclerView.ViewHolder(binding.root), KoinComponent {

    private lateinit var genre: GenrePresentation
    private val adapter: CustomAdapter by inject()
    private val recyclerViewModel: GenreRecyclerViewModel by inject()

    fun bind(genre: GenrePresentation) {
        this.genre = genre
        binding.genresName = genre
        binding.itemGenresRecyclerView.adapter = adapter
        configObserver()
        configRecycler()
        binding.executePendingBindings()
    }

    private fun configObserver() {

        recyclerViewModel.mutableLiveData.observe(lifecycle, Observer {
            if (it is ViewState.Data) {
                adapter.addAll(it.movies as ArrayList<MoviePresentation>)
            }
        })

    }

    private fun configRecycler() {
        recyclerViewModel.getMoviesByGenres(genre.id)

    }
}