package com.example.movietmdb.recycler.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.databinding.LayoutHeaderBinding
import com.example.movietmdb.recycler.data.MoviePresentation
import org.koin.core.KoinComponent


class HeaderViewHolder(val binding: LayoutHeaderBinding) : RecyclerView.ViewHolder(binding.root),
    KoinComponent {

    fun bind(movie: MoviePresentation) {
        binding.movie = movie
    }
}