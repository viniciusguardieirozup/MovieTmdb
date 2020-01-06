package com.example.movietmdb.features.description.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.DataBaseThread
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityDescriptionBinding
import com.example.movietmdb.features.description.viewmodel.DescriptionActivityViewModel
import com.example.movietmdb.features.main.ui.activity.MainActivity
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.coroutines.launch

class DescriptionActivity : AppCompatActivity() {
    private lateinit var viewModel: DescriptionActivityViewModel
    lateinit var binding: ActivityDescriptionBinding
    private lateinit var movie: MoviePresentation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        goBackButton.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(
                R.anim.slide_to_right_enter,
                R.anim.slide_to_right_exit
            )
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description)
        viewModel = ViewModelProviders.of(this).get(DescriptionActivityViewModel::class.java)
        binding.teste = viewModel

        val movieFromActivity = intent.extras
        configScreen(movieFromActivity)
    }

    private fun configScreen(movieFromActivity: Bundle?) {
        movieFromActivity?.let {
            movie = movieFromActivity.getSerializable("movie") as MoviePresentation
            viewModel.movie = movie
            viewModel.setImageFav()

            DataBaseThread().launch {
                val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(applicationContext)
                    .load(movie.posterPath).apply(request)
                    .into(imageDescriptionLayout)
            }
        }
    }


}
