package com.example.movietmdb.recycler.holder

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ItemMovieLayoutBinding
import com.example.movietmdb.features.description.ui.DescriptionActivity
import com.example.movietmdb.recycler.FavButtonListener
import com.example.movietmdb.recycler.data.MoviePresentation
import kotlinx.android.synthetic.main.item_movie_layout.view.*

class ViewHolder(val binding: ItemMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    private val moviePoster = itemView.moviePoster
    private val movieFav = itemView.favoriteButton
    private lateinit var movieItem: MoviePresentation
    private lateinit var myListener: FavButtonListener

    fun bind(
        movieItem: MoviePresentation,
        favButtonListener: FavButtonListener
    ) {
        binding.item = movieItem
        myListener = favButtonListener
        this.movieItem = movieItem
        movieItem.posterPath?.let {
            val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(itemView.context)
                .load(movieItem.posterPath).apply(request)
                .into(moviePoster)
        }
        setFavImage()
        binding.executePendingBindings()
    }

    init {
        itemClick()
        favButtonClick()

    }

    private fun itemClick() {
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DescriptionActivity::class.java)
            intent.putExtra("movie", movieItem)
            itemView.context.startActivity(intent)
            (itemView.context as Activity).overridePendingTransition(
                R.anim.slide_to_left_enter,
                R.anim.slide_to_left_exit
            )
        }
    }

    private fun favButtonClick() {
        movieFav.setOnClickListener {
            myListener.favButtonClicked(movieItem)
            setFavImage()
        }
    }

    private fun setFavImage() {
        if (movieItem.favorite) {
            movieFav.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
        } else {
            movieFav.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }
}