package com.example.movietmdb.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutItemMoviesGenresBinding
import com.example.movietmdb.presentation.recycler.data.GenrePresentation
import com.example.movietmdb.presentation.recycler.holder.GenresViewHolder

class GenreAdapter : RecyclerView.Adapter<GenresViewHolder>() {

    private var genresList = ArrayList<GenrePresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding: LayoutItemMoviesGenresBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_movies_genres,
                parent,
                false
            )
        return GenresViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(genresList[position])
    }

    fun addAll(aux: ArrayList<GenrePresentation>) {
        genresList = aux
        notifyDataSetChanged()
    }

}