package com.example.movietmdb.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ActivityDescriptionBinding
import com.example.movietmdb.presentation.viewmodel.DescriptionViewModel
import com.example.movietmdb.presentation.recycler.adapter.DescriptionAdapter
import com.example.movietmdb.presentation.recycler.data.MoviePresentation
import com.example.movietmdb.presentation.viewmodel.ViewState
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
    }

    private fun configToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        configObserverToolBarImage()
        configToolBarImageClick()
    }

    private fun configObserverToolBarImage(){
        viewModel.favorite.observe(this, Observer {
            if(it){
                binding.imageToolBar.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
            }
            else{
                binding.imageToolBar.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
            }
        })
    }

    private fun configToolBarImageClick(){
        binding.imageToolBar.setOnClickListener {
            viewModel.setFavorite()
        }
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
