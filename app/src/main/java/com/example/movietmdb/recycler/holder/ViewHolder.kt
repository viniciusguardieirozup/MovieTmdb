package com.example.movietmdb.recycler.holder

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutItemMovieBinding
import com.example.movietmdb.features.description.ui.DescriptionActivity
import com.example.movietmdb.recycler.data.MoviePresentation


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
            val intent = Intent(itemView.context, DescriptionActivity::class.java)
            movieItem.type = 0
            intent.putExtra("movie", movieItem)
            itemView.context.startActivity(intent)
            (itemView.context as Activity).overridePendingTransition(
                R.anim.slide_to_left_enter,
                R.anim.slide_to_left_exit
            )
        }
    }

}