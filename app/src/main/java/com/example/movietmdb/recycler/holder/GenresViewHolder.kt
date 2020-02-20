package com.example.movietmdb.recycler.holder

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.databinding.LayoutItemMoviesGenresBinding
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.data.GenrePresentation
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.viewmodel.GenreRecyclerViewModel
import com.example.movietmdb.viewModel.ViewState
import org.koin.core.KoinComponent
import org.koin.core.inject

class GenresViewHolder(val binding: LayoutItemMoviesGenresBinding) :
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
    }

    private fun configObserver() {
        recyclerViewModel.moviesLiveData.observe(itemView.context as LifecycleOwner, Observer {
            if (it is ViewState.Data) {
                adapter.addAll(it.movies as ArrayList<MoviePresentation>)
            }
        })
    }

    private fun configRecycler() {
        recyclerViewModel.getMoviesByGenres(genre.id)
        pagination()
    }

    private fun pagination() {
        binding.itemGenresRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollHorizontally(1)) {
                    recyclerViewModel.getMoviesByGenres(genre.id)
                }
            }
        })
    }
}