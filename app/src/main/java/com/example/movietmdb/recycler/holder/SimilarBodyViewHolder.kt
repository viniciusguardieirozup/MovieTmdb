package com.example.movietmdb.recycler.holder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movietmdb.DebouncedOnClickListener
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutItemMovieSimilarBinding
import com.example.movietmdb.features.description.ui.DescriptionActivity
import com.example.movietmdb.recycler.data.MoviePresentation


class SimilarBodyViewHolder(val binding: LayoutItemMovieSimilarBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var movieItem: MoviePresentation

    fun bind(
        movieItem: MoviePresentation
    ) {
        binding.item = movieItem
        this.movieItem = movieItem
        if (movieItem.posterPath == null) {
            binding.itemSimilarMovieImage.setImageResource(R.drawable.ic_not_found)
        } else {
            Glide.with(itemView.context)
                .load(movieItem.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemSimilarMovieImage)
        }
        itemClick()
    }

    private fun itemClick() {
        itemView.setOnClickListener(object : DebouncedOnClickListener() {
            override fun onDebouncedClick(v: View) {
                val intent = Intent(v.context,DescriptionActivity::class.java)
                intent.putExtra("movie",movieItem)
                v.context.startActivity(intent)
            }
        })
    }

}