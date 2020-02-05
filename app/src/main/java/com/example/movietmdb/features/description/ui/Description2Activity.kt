package com.example.movietmdb.features.description.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.R
import com.example.movietmdb.ViewState
import com.example.movietmdb.databinding.ActivityDescription3Binding
import com.example.movietmdb.features.description.viewmodel.DescriptionViewModel
import com.example.movietmdb.recycler.adapter.DescriptionAdapter
import com.example.movietmdb.recycler.data.MoviePresentation
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class Description2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDescription3Binding
    private val viewModel: DescriptionViewModel by viewModel()
    private lateinit var movie: MoviePresentation

    private val adapter: DescriptionAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = intent?.extras?.getParcelable<MoviePresentation>("movie") as MoviePresentation
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description3)
        configViewModel()
        configViewModelObserver()
        configRecycler()
        configImage()
        pagination()
    }

   private fun configViewModel() {
        viewModel.movie = movie
        val array = ArrayList<MoviePresentation>()
        array.add(movie)
        adapter.addAll(array)
        viewModel.id = movie.id
        viewModel.getSimilar()
    }

    private fun configViewModelObserver() {
        viewModel.mutable.observe(this, Observer {
            when (it) {
                is ViewState.Data -> {
                    adapter.addAll(it.movies as ArrayList<MoviePresentation>)
                    adapter.notifyDataSetChanged()

                }
                is ViewState.Error -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun configRecycler() {
        val aux = GridLayoutManager(applicationContext, 3)
        aux.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) {
                    3
                } else {
                    1
                }
            }

        }
        binding.description3RecyclerView.layoutManager = aux
        binding.description3RecyclerView.adapter = adapter

    }

    private fun configImage() {
        val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(applicationContext).load(movie.backdropPath).apply(request)
            .into(binding.description3Image)
    }

    private fun pagination() {
        binding.description3RecyclerView.viewTreeObserver.addOnScrollChangedListener {
            if (!binding.description3RecyclerView.canScrollVertically(1)) {
                viewModel.getSimilar()
            }

        }
    }
}
