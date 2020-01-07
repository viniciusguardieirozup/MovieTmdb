package com.example.movietmdb.recycler

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.example.movietmdb.R
import com.example.movietmdb.mappers.MovieDataMapper
import com.example.movietmdb.repository.RepositoryRules
import com.example.movietmdb.features.description.ui.DescriptionActivity
import com.example.movietmdb.DataBaseThread
import kotlinx.android.synthetic.main.recycler_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

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

    fun reset() {
        lista = ArrayList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.bind(item)
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), CoroutineScope {
    private val movieTitle = itemView.movieTitle
    private val noteText = itemView.movieNote
    private val movieDescription = itemView.movieDescription
    private val moviePoster = itemView.moviePoster
    private val movieFav = itemView.favoriteButton
    private var fav = false
    private lateinit var moviePresentation: MoviePresentation

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun stopJob() {
        job.cancel()
    }

    fun bind(moviePresentation: MoviePresentation) {
        moviePresentation.posterPath?.let {
            this.moviePresentation = moviePresentation
            movieTitle.text = moviePresentation.title
            noteText.text = moviePresentation.voteAverage.toString()
            movieDescription.text = moviePresentation.overView
            val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(itemView.context)
                .load(moviePresentation.posterPath).apply(request)
                .into(moviePoster)

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
            (itemView.context as Activity).overridePendingTransition(
                R.anim.slide_to_left_enter,
                R.anim.slide_to_left_exit
            )
        }
        movieFav.setOnClickListener {
            val aux = MovieDataMapper().mapFromPresentation(moviePresentation)
            if (!fav) {
                launch {
                    RepositoryRules().insertMovie(aux)
                    movieFav.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
                    fav = true
                }
            } else {
                launch {
                    RepositoryRules().removeMovie(aux)
                    movieFav.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
                    fav = false
                }
            }
        }
    }
}
