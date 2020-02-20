package com.example.movietmdb.features.description.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityDescriptionBinding
import com.example.movietmdb.features.description.viewmodel.DescriptionViewModel
import com.example.movietmdb.recycler.adapter.DescriptionAdapter
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.viewModel.ViewState
import kotlinx.android.synthetic.main.activity_description.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private val viewModel: DescriptionViewModel by viewModel()
    private lateinit var movie: MoviePresentation
    private val adapter: DescriptionAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description)
        configBinding()
        configToolBar()
        configViewModel()
        configRecycler()
        pagination()
    }

    private fun configBinding() {
        movie = intent?.extras?.getParcelable<MoviePresentation>("movie") as MoviePresentation
        binding.lifecycleOwner = this
    }

    private fun configToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun configViewModel() {
        viewModel.startViewModel(movie)
        binding.viewModel = viewModel
        configViewModelObserver()
    }

    private fun configViewModelObserver() {
        viewModel.moviesLiveData.observe(this, Observer {
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


    private fun pagination() {
        binding.description3RecyclerView.viewTreeObserver.addOnScrollChangedListener {
            if (!binding.description3RecyclerView.canScrollVertically(1)) {
                viewModel.getSimilar()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
