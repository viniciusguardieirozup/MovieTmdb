package com.example.movietmdb.recycler.holder

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movietmdb.DebouncedOnClickListener
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutItemMovieBinding
import com.example.movietmdb.features.main.ui.fragments.SearchFragmentDirections
import com.example.movietmdb.recycler.data.MoviePresentation

//bindingadapter glide melhor forma?
class ViewHolder(val binding: LayoutItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var movieItem: MoviePresentation

    fun bind(
        movieItem: MoviePresentation
    ) {
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
        itemView.setOnClickListener {
            val action =
                SearchFragmentDirections
                    .actionSearchFragmentToDescription(movieItem)
            it.findNavController().navigate(action)
        }
    }

}