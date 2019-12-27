package com.example.movietmdb

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.mapper.MovieDataMapper
import com.example.movietmdb.recycler.MoviePresentation
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.coroutines.launch

class DescriptionActivity : AppCompatActivity() {
    private var favorite = false
    private val thread = DataBaseThread()
    private val db = MovieTmdbApplication.db.movieDao()
    private lateinit var movie : MoviePresentation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        val movieFromActivity = intent.extras
        configScreen(movieFromActivity)
        configButtonClick()
    }

    private fun configScreen(movieFromActivity : Bundle?){
        movieFromActivity?.let {
            movie = movieFromActivity.getSerializable("movie") as MoviePresentation
            titleDescriptionLayout.text = movie.title
            val aux = Base64.decode(movie.posterPath, Base64.DEFAULT)
            val exibir = BitmapFactory.decodeByteArray(aux, 0, aux.size)
            imageDescriptionLayout.setImageBitmap(exibir)
            overviewDescriptionLayout.text = movie.overView
            favorite = movie.favorite
            if(favorite){
                favoriteButtonDesriptionActivity.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
            }
            else{
                favoriteButtonDesriptionActivity.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
            }
        }
    }

    private fun configButtonClick(){
        favoriteButtonDesriptionActivity.setOnClickListener{
            if(favorite){
                favoriteButtonDesriptionActivity.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp)
                favorite = false
                thread.launch {
                    db.removeMovie(MovieDataMapper().mapFromPresentation(movie))
                }
            }
            else{
                favoriteButtonDesriptionActivity.setBackgroundResource(R.drawable.ic_favorite_white_24dp)
                favorite = true
                thread.launch {
                    db.insertMovie(MovieDataMapper().mapFromPresentation(movie))
                }
            }
        }
    }
}
