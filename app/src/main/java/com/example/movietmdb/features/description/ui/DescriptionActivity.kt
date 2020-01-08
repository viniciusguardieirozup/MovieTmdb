package com.example.movietmdb.features.description.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityDescriptionBinding
import com.example.movietmdb.features.main.ui.activity.MainActivity
import com.example.movietmdb.recycler.data.MoviePresentation

class DescriptionActivity : AppCompatActivity() {
    private lateinit var viewModel: BaseMovieViewModel
    private lateinit var binding: ActivityDescriptionBinding
    private lateinit var movie: MoviePresentation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        configBinding()
        configViewModel(intent.extras)
        configPosterImage()
        configImageFav()
        configClicks()
    }

    private fun configBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description)
        viewModel = ViewModelProviders.of(this).get(BaseMovieViewModel::class.java)
        binding.viewModelDescription = viewModel
    }

    private fun configImageFav() {
        if (movie.favorite) {
            binding.favoriteButtonDescriptionActivity.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp))
        } else {
            binding.favoriteButtonDescriptionActivity.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_white_24dp))
        }
    }

    private fun configViewModel(movieFromActivity: Bundle?) {
        movieFromActivity?.let {
            movie = movieFromActivity.getParcelable<MoviePresentation>("movie") as MoviePresentation
            viewModel.movie = movie
        }
    }

    private fun configPosterImage() {
        val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(applicationContext)
            .load(movie.posterPath).apply(request)
            .into(binding.imageDescriptionLayout)
    }

    private fun configClicks() {
        binding.favoriteButtonDescriptionActivity.setOnClickListener {
            viewModel.setFavorite(movie)
            configImageFav()
        }
        binding.goBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            (this).overridePendingTransition(
                R.anim.slide_to_right_enter,
                R.anim.slide_to_right_exit
            )
        }

    }
}


