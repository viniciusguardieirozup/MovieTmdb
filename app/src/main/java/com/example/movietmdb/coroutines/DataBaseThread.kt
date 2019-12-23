package com.example.movietmdb.coroutines

import android.util.Log
import com.example.movietmdb.database.MovieDao
import com.example.movietmdb.database.MovieData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DataBaseThread(private val movieDao: MovieDao) : CoroutineScope {

    private val job: Job = Job()

    val list = ArrayList<MovieData>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun stopJob() {
        job.cancel()
    }
    suspend fun auxAllFavoritesMovies() {
        coroutineScope {
            launch {
                withContext(coroutineContext) { list.addAll(movieDao.getAll()) }
            }
        }
    }

    fun saveFavoritesMovie(movie: MovieData) {
        Log.v("test", movie.title ?: "")
        launch {
            withContext(coroutineContext) { auxSaveFavoritesMovie(movie) }
        }
    }

    private suspend fun auxSaveFavoritesMovie(movie: MovieData) {
        try {
            movieDao.insertMovie(movie)
        }catch (e : Exception){
        }
    }


}