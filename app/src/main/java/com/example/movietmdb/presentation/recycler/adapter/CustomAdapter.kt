package com.example.movietmdb.presentation.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.LayoutItemMovieBinding
import com.example.movietmdb.presentation.recycler.data.MoviePresentation
import com.example.movietmdb.presentation.recycler.holder.ViewHolder

class CustomAdapter : RecyclerView.Adapter<ViewHolder>() {


    private var list = ArrayList<MoviePresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: LayoutItemMovieBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_movie,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(newMovies: ArrayList<MoviePresentation>) {
        list.addAll(newMovies)
        notifyDataSetChanged()
    }

    fun reset() {
        list = ArrayList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}

