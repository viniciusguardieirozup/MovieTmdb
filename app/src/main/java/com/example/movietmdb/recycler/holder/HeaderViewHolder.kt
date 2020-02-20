package com.example.movietmdb.recycler.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutHeaderBinding
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.viewmodel.HeaderViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject


class HeaderViewHolder(val binding: LayoutHeaderBinding) : RecyclerView.ViewHolder(binding.root),
    KoinComponent {

    private val viewModel: HeaderViewModel by inject()
    private lateinit var movie : MoviePresentation

    fun bind(movie: MoviePresentation) {
        this.movie = movie
        configImage()
        viewModel.movie = this.movie
        binding.viewModel = viewModel
        buttonClick()
    }

    private fun buttonClick(){
        binding.favoriteHeaderButton.setOnClickListener {
            viewModel.setFavorite()
            configImage()
        }
    }

    private fun configImage(){
        if(movie.favorite){
            binding.favoriteHeaderButton.setImageResource(R.drawable.ic_favorite_white_24dp)
        }
        else{
            binding.favoriteHeaderButton.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        }
    }
}