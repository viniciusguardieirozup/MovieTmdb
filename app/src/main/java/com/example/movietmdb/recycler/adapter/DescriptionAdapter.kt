package com.example.movietmdb.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import com.example.movietmdb.databinding.HeaderLayoutBinding
import com.example.movietmdb.databinding.ItemMovieLayoutBinding
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.recycler.holder.HeaderViewHolder
import com.example.movietmdb.recycler.holder.ViewHolder

class DescriptionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = ArrayList<MoviePresentation>()

    companion object {
        private const val HEADER = 0
        private const val BODY = 1
    }

    fun addAll(list: List<MoviePresentation>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER) {
            val binding : HeaderLayoutBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.header_layout,
                    parent,
                    false
                )
            return HeaderViewHolder(binding)
        } else {
            val binding: ItemMovieLayoutBinding =
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_movie_layout,
                    parent,
                    false
                )
            return ViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]

        when (item.type) {
            HEADER -> {
                (holder as HeaderViewHolder).bind(item)
            }
            BODY -> {
                (holder as ViewHolder).bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

}