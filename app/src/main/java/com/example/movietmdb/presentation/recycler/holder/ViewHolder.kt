package com.example.movietmdb.presentation.recycler.holder

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutItemMovieBinding
import com.example.movietmdb.presentation.fragments.SearchFragmentDirections
import com.example.movietmdb.presentation.recycler.data.MoviePresentation

class ViewHolder(val binding: LayoutItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var movieItem: MoviePresentation

    fun bind(
        movieItem: MoviePresentation
    ) {
        binding.itemClick.isEnabled = true
        binding.item = movieItem
        this.movieItem = movieItem
        if (movieItem.posterPath == null) {
            binding.itemMovieImage.setImageResource(R.drawable.ic_not_found)
        } else {
            Glide.with(itemView.context)
                .load(movieItem.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemMovieImage)
        }
        itemClick()
    }

    private fun itemClick() {
        binding.itemClick.setOnClickListener {
            it.isEnabled = false
            val action =
                SearchFragmentDirections
                    .actionSearchFragmentToDescription(movieItem)

            it.findNavController().navigate(action)
        }
    }

}