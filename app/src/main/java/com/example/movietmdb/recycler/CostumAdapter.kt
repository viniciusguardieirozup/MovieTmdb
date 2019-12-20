package com.example.movietmdb.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.R
import kotlinx.android.synthetic.main.recycler_layout.view.*

class CostumAdapter(val lista: ArrayList<MoviePresentation>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.movieTitle.text = item.title
        holder.noteText.text = item.voteAverage.toString()

    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val movieTitle = itemView.movieTitle
    val noteText = itemView.movieNote
}
