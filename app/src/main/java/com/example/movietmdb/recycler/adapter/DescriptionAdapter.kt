package com.example.movietmdb.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.ItemMovieLayoutBinding
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.holder.ViewHolder

class DescriptionAdapter : PagedListAdapter<MoviePresentation,ViewHolder>(diff) {

    companion object{
        val diff = object : DiffUtil.ItemCallback<MoviePresentation>(){
            override fun areItemsTheSame(
                oldItem: MoviePresentation,
                newItem: MoviePresentation
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: MoviePresentation,
                newItem: MoviePresentation
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =getItem(position)
        item?.let {
            holder.bind(item)
        }
    }

}

