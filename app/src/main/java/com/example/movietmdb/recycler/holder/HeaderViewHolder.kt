package com.example.movietmdb.recycler.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.HeaderLayoutBinding
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.viewmodel.HeaderViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject


class HeaderViewHolder(val binding: HeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root),
    KoinComponent {

    private val viewModel: HeaderViewModel by inject()

    fun bind(movie: MoviePresentation) {
        binding.movie = movie
        configImage(movie)
        configButton(movie)
    }

    private fun configButton(movie: MoviePresentation) {
        binding.favoriteHeaderButton.setOnClickListener {
            viewModel.setFavorite(movie)
            configImage(movie)
        }
    }

    private fun configImage(movie: MoviePresentation) {
        if (movie.favorite) {
            binding.favoriteHeaderButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            binding.favoriteHeaderButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }

    }

}