package com.example.movietmdb.features.description.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.BaseMovieViewModel
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityDescription2Binding
import com.example.movietmdb.recycler.data.MoviePresentation


class Description2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDescription2Binding
    private lateinit var viewModel: BaseMovieViewModel
    private lateinit var movie: MoviePresentation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description2)
        movie = intent?.extras?.getParcelable<MoviePresentation>("movie") as MoviePresentation
        configViewModel()
        configBinding()
        configNestedScrollListener()
        configImage()
        configBackButton()

    }

    private fun configBackButton() {
        binding.description2BackButton.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun configNestedScrollListener() {
        binding.description2NestedScroll.viewTreeObserver.addOnScrollChangedListener {
            val dislocate =
                (binding.description2NestedScroll.scrollY * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            var alpha = (0.0008 * dislocate + 0.2).toFloat()
            if (alpha > 1)
                alpha = 1f
            binding.description2View.alpha = alpha
        }
    }


    private fun configViewModel() {
        viewModel = ViewModelProviders.of(this).get(BaseMovieViewModel::class.java)
        viewModel.movie = movie
    }

    private fun configBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description2)
        binding.viewModelDescription2 = viewModel
    }

    private fun configImage() {
        val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(applicationContext).load(movie.backdropPath).apply(request)
            .into(binding.description2Image)
    }

}
