package com.example.movietmdb.ui.recycler

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movietmdb.ui.activity.DescriptionActivity
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.mapper.MovieDataMapper
import kotlinx.android.synthetic.main.recycler_layout.view.*
import kotlinx.coroutines.launch

class CostumAdapter() : RecyclerView.Adapter<ViewHolder>() {

    private var lista: ArrayList<MoviePresentation> = ArrayList()

    //inflate the recycler layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun addAll(newMovies: ArrayList<MoviePresentation>) {
        val oldSize = lista.size
        lista.addAll(newMovies)
        notifyItemRangeChanged(oldSize, newMovies.size)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.bind(item)
    }

}

//a costum viewHolder


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieTitle = itemView.movieTitle
    private val noteText = itemView.movieNote
    private val movieDescription = itemView.movieDescription
    private val moviePoster = itemView.moviePoster
    private val movieFav = itemView.favoriteButton
    private var fav = false
    private lateinit var moviePresentation: MoviePresentation


    fun bind(moviePresentation: MoviePresentation) {

        this.moviePresentation = moviePresentation
        movieTitle.text = moviePresentation.title
        noteText.text = moviePresentation.voteAverage.toString()
        movieDescription.text = moviePresentation.overView
        moviePresentation.posterPath?.let {
            val aux = Base64.decode(moviePresentation.posterPath, Base64.DEFAULT)
            val exibir = BitmapFactory.decodeByteArray(aux, 0, aux.size)
            moviePoster.setImageBitmap(exibir)
        }

        if (moviePresentation.favorite) {
            movieFav.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
            this.fav = true
        } else {
            movieFav.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
            this.fav = false
        }
    }

    init {
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DescriptionActivity::class.java)
            intent.putExtra("movie", moviePresentation)
            itemView.context.startActivity(intent)
            (itemView.context as Activity).overridePendingTransition(R.anim.slide_to_left_enter,R.anim.slide_to_left_exit)
        }
        movieFav.setOnClickListener {
            val thread = DataBaseThread()
            val db = MovieTmdbApplication.db.movieDao()
            val aux = MovieDataMapper().mapFromPresentation(moviePresentation)
            if (!fav) {
                thread.launch {
                    db.insertMovie(aux)
                    movieFav.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
                    fav = true
                }
            } else {
                thread.launch {
                    db.removeMovie(aux)
                    movieFav.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
                    fav = false
                }
            }
        }
    }


}