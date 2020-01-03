package com.example.movietmdb.feature.description.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.example.movietmdb.DataBaseThread
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.feature.main.ui.activity.MainActivity
import com.example.movietmdb.mapper.MovieDataMapper
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.coroutines.launch

class DescriptionActivity : AppCompatActivity() {
    private var favorite = false
    private val thread =
        DataBaseThread()
    private val db = MovieTmdbApplication.db.movieDao()
    private lateinit var movie: MoviePresentation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        goBackButton.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            overridePendingTransition(
                R.anim.slide_to_right_enter,
                R.anim.slide_to_right_exit
            )
        }
        val movieFromActivity = intent.extras
        configScreen(movieFromActivity)
        configButtonClick()

    }

    private fun configScreen(movieFromActivity: Bundle?) {
        movieFromActivity?.let {
            movie = movieFromActivity.getSerializable("movie") as MoviePresentation
            titleDescriptionLayout.text = movie.title
            val aux = Base64.decode(movie.posterPath, Base64.DEFAULT)
            val exibir = BitmapFactory.decodeByteArray(aux, 0, aux.size)
            imageDescriptionLayout.setImageBitmap(exibir)
            overviewDescriptionLayout.text = movie.overView
            favorite = movie.favorite
            if (favorite) {
                favoriteButtonDescriptionActivity.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
            } else {
                favoriteButtonDescriptionActivity.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
            }
        }
    }

    private fun configButtonClick() {
        favoriteButtonDescriptionActivity.setOnClickListener {
            if (favorite) {
                favoriteButtonDescriptionActivity.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
                favorite = false
                thread.launch {
                    db.removeMovie(MovieDataMapper().mapFromPresentation(movie))
                }
            } else {
                favoriteButtonDescriptionActivity.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
                favorite = true
                thread.launch {
                    db.insertMovie(MovieDataMapper().mapFromPresentation(movie))
                }
            }
        }
    }
}
