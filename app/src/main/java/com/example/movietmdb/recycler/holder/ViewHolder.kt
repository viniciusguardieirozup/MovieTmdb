package com.example.movietmdb.recycler.holder

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ItemMovieLayoutBinding
import com.example.movietmdb.features.description.ui.Description2Activity
import com.example.movietmdb.recycler.data.MoviePresentation

class ViewHolder(val binding: ItemMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var movieItem: MoviePresentation

    fun bind(
        movieItem: MoviePresentation
    ) {
        binding.item = movieItem
        this.movieItem = movieItem
        movieItem.posterPath?.let {
            val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(itemView.context)
                .load(movieItem.posterPath).apply(request)
                .into(binding.itemMovieImage)
        }
        binding.executePendingBindings()
        itemClick()
    }


    private fun itemClick() {
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, Description2Activity::class.java)
            intent.putExtra("movie", movieItem)
            itemView.context.startActivity(intent)
            (itemView.context as Activity).overridePendingTransition(
                R.anim.slide_to_left_enter,
                R.anim.slide_to_left_exit
            )
        }
    }

}