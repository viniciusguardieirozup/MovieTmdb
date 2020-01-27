package com.example.movietmdb.features.description.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movietmdb.R
import com.example.movietmdb.ViewState
import com.example.movietmdb.databinding.ActivityDescription2Binding
import com.example.movietmdb.features.description.viewmodel.DescriptionViewModel
import com.example.movietmdb.recycler.adapter.DescriptionAdapter
import com.example.movietmdb.recycler.data.MoviePresentation
import org.koin.androidx.viewmodel.ext.android.viewModel

class Description2Activity : AppCompatActivity() {

    private val REQUESTSTORAGE = 111

    private lateinit var binding: ActivityDescription2Binding
    private val viewModel: DescriptionViewModel by viewModel()
    private lateinit var movie: MoviePresentation

    //variables to toolbar
    private val scrollBounds = Rect()
    private var similarVisible = false
    private var similarText = false
    private var isInSimilar = false

    //variables to nestedScroll
    private var density: Float = 0f
    private var oldScrollValue = 0
    private var newScrollValue = 0

    //variables to recyclerView
    private val adapter = DescriptionAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = intent?.extras?.getParcelable<MoviePresentation>("movie") as MoviePresentation
        density = Resources.getSystem().displayMetrics.density

        configBinding()
        configViewModel()
        configRecycler()
        configNestedScrollListener()
        configImage()
        configFavButton()
        configBackButton()
        configFavButtonClick()

    }

    private fun configBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description2)
    }

    private fun configRecycler() {
        binding.description2RecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.description2RecyclerView.adapter = adapter
        binding.description2RecyclerView.isNestedScrollingEnabled = false
    }

    //functions to configure the images
    private fun configImage() {
        val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(applicationContext).load(movie.backdropPath).apply(request)
            .into(binding.description2Image)
    }

    private fun configFavButton() {
        if (viewModel.movie.favorite) {
            binding.description2FavoriteButton.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
        } else {
            binding.description2FavoriteButton.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }

    //functions to config viewButtons
    private fun configBackButton() {
        binding.description2BackButton.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun configFavButtonClick() {
        binding.description2FavoriteButton.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                viewModel.setFavorite(movie)
                configFavButton()
            } else {
                Toast.makeText(
                    this,
                    "The write external storage is necessary to save the movies on database",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUESTSTORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.setFavorite(movie)
                configFavButton()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //functions to configure viewModel
    private fun configViewModel() {
        viewModel.movie = movie
        binding.viewModelDescription2 = viewModel
        viewModel.getSimilar(movie.id)
        configViewModelObserver()
    }

    private fun configViewModelObserver() {
        viewModel.itemPagedList.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.mutable.observe(this, Observer {

            when (it) {
                is ViewState.Data -> {
                    //adapter.addAll(it.movies as ArrayList<MoviePresentation>)
                }
                is ViewState.Loading -> {
                    if (it.loading) {
                        binding.description2ProgressBar.visibility = View.VISIBLE
                    } else {
                        binding.description2ProgressBar.visibility = View.GONE
                    }
                }
                is ViewState.Error -> {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    //functions to configure nestedScroll animates
    private fun configNestedScrollListener() {
        binding.description2NestedScroll.viewTreeObserver.addOnScrollChangedListener {
            pagination()
            oldScrollValue = newScrollValue
            newScrollValue = binding.description2NestedScroll.scrollY
            configAlpha(alphaCalculation(newScrollValue))
            isViewVisible(binding.description2SimilarText)
        }
    }

    private fun pagination() {
        if (!binding.description2NestedScroll.canScrollVertically(1) && isInSimilar) {
            viewModel.getSimilar(movie.id)
        }
    }

    private fun configAlpha(alpha: Float) {
        if (alpha > 1)
            binding.description2View.alpha = 1f
        binding.description2View.alpha = alpha
    }

    private fun alphaCalculation(scrollValue: Int): Float {
        val dislocate =
            (scrollValue * density + 0.5f)
        return (0.0008 * dislocate + 0.2).toFloat()
    }

    private fun screenUp(): Boolean {
        return newScrollValue >= oldScrollValue
    }

    //function to set the text in the similarView
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
                    isInSimilar = true
                }

            }

        } else {
            if (!screenUp()) {
                if (!similarVisible) {
                    similarVisible = true
                    similarText = false
                    isInSimilar = true
                }
            }
            //visible
            similarVisible = true
        }
        configSimilarText()
    }
}
