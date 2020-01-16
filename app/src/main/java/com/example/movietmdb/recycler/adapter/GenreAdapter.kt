package com.example.movietmdb.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ItemMoviesGenresLayoutBinding
import com.example.movietmdb.recycler.data.GenrePresentation
import com.example.movietmdb.recycler.holder.GenresViewHolder

class GenreAdapter : RecyclerView.Adapter<GenresViewHolder>() {

    private val genresList = ArrayList<GenrePresentation>()
    lateinit var lifeCicle: LifecycleOwner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding: ItemMoviesGenresLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movies_genres_layout,
                parent,
                false
            )
        return GenresViewHolder(binding, lifeCicle)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(genresList[position])
    }

    fun addAll(aux: ArrayList<GenrePresentation>) {
        genresList.addAll(aux)
    }

}