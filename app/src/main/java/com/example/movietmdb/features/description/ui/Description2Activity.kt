package com.example.movietmdb.features.description.ui

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityDescription2Binding
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.recycler.adapter.CustomAdapter
import com.example.movietmdb.recycler.data.MoviePresentation


class Description2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityDescription2Binding
    private lateinit var viewModel: DescriptionViewModel
    private lateinit var movie: MoviePresentation


    //variables to toolbar
    private val scrollBounds = Rect()
    private var similarVisible = false
    private var similarText = false

    //variables to nestedScroll
    private var density: Float = 0f
    private var oldScrollValue = 0
    private var newScrollValue = 0

    //variables to recyclerView
    private val adapter = CustomAdapter()
    private var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = intent?.extras?.getParcelable<MoviePresentation>("movie") as MoviePresentation
        density = Resources.getSystem().displayMetrics.density

        configBinding()
        configViewModel()
        configRecycler()
        configNestedScrollListener()
        configImage()
        configBackButton()

    }

    private fun configBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description2)
    }

    private fun configImage() {
        val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(applicationContext).load(movie.backdropPath).apply(request)
            .into(binding.description2Image)
    }

    private fun configBackButton() {
        binding.description2BackButton.setOnClickListener {
            super.onBackPressed()
        }
    }

    //functions to configure viewModel
    private fun configViewModel() {
        viewModel = ViewModelProviders.of(this).get(DescriptionViewModel::class.java)
        viewModel.movie = movie
        binding.viewModelDescription2 = viewModel
        configViewModelObserver()
    }

    private fun configViewModelObserver() {
        viewModel.mutable.observe(this, Observer {
            if (it is ViewState.Data) {

                adapter.addAll(it.movies as ArrayList<MoviePresentation>)
            }
            if (it is ViewState.Loading) {
                if (it.loading) {
                    binding.description2ProgressBar.visibility = View.VISIBLE
                } else {
                    binding.description2ProgressBar.visibility = View.GONE
                }
            }
        }
        )
    }

    //functions to configure nestedScroll animates
    private fun configNestedScrollListener() {
        binding.description2NestedScroll.viewTreeObserver.addOnScrollChangedListener {
            val aux = binding.description2NestedScroll.scrollY
            oldScrollValue = newScrollValue
            newScrollValue = aux
            val dislocate =
                (aux * density + 0.5f)
            var alpha = (0.0008 * dislocate + 0.2).toFloat()
            if (alpha > 1)
                alpha = 1f
            binding.description2View.alpha = alpha
            isViewVisible(binding.description2SimilarText)
        }
    }

    private fun configSimilarText() {
        if (similarText) {
            binding.description2View.text = "Similar"
        } else {
            binding.description2View.text = ""
        }
    }

    private fun isViewVisible(view: View) {
        binding.description2NestedScroll.getHitRect(scrollBounds)

        if (!view.getLocalVisibleRect(scrollBounds) || scrollBounds.height() < view.height) {
            //not visible
            if (screenUp()) {
                if (similarVisible) {
                    similarVisible = false
                    similarText = true
                }

            }

        } else {
            if (!screenUp()) {
                if (!similarVisible) {
                    similarVisible = true
                    similarText = false
                }
            }
            //visible
            similarVisible = true
        }
        configSimilarText()
    }

    private fun screenUp(): Boolean {
        return newScrollValue >= oldScrollValue
    }

    //functions to configure recyclerview
    private fun configRecycler() {
        binding.description2RecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.description2RecyclerView.adapter = adapter
        viewModel.getSimilars(movie.id)
        pagination()
    }

    private fun pagination() {
        binding.description2RecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getSimilars(movie.id)
                }
            }
        })
    }
}
