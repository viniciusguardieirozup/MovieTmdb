package com.example.movietmdb.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ItemMovieLayoutBinding
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.holder.ViewHolder

class CustomAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var list = ArrayList<MoviePresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMovieLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie_layout,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(newMovies: ArrayList<MoviePresentation>) {
        val oldSize = list.size
        list.addAll(newMovies)
        notifyItemRangeChanged(oldSize, newMovies.size)
    }

    fun reset() {
        list = ArrayList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}

