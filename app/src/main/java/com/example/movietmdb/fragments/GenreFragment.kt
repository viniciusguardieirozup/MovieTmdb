package com.example.movietmdb.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.database.MovieDao
import com.example.movietmdb.database.MovieData
import com.example.movietmdb.mapper.MoviePresentationMapper
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.retrofit.MovieService
import com.example.movietmdb.retrofit.RetrofitInitializer
import com.example.movietmdb.retrofit.SearchResults
import kotlinx.android.synthetic.main.genres_layout.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GenreFragment : Fragment() {

    lateinit var id: String
    private var page = 1
    private var lastPage = false
    private var loading = false
    private lateinit var favMovies: List<MovieData>
    private lateinit var thread: DataBaseThread
    private lateinit var db: MovieDao
    private var adapter: CostumAdapter = CostumAdapter()

    companion object {
        private const val favoritesMoviesListKEY = "fav_movies"
        private const val threadKEY = "thread"

        fun newInstance(
            favMovies: List<MovieData>,
            thread: DataBaseThread
        ) = GenreFragment().apply {
            arguments = bundleOf(
                favoritesMoviesListKEY to favMovies,
                threadKEY to thread
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            @Suppress("UNCHECKED_CAST")
            favMovies =
                arguments?.get(favoritesMoviesListKEY) as? ArrayList<MovieData> ?: return null
            thread = arguments?.get(threadKEY) as DataBaseThread
            db = MovieTmdbApplication.db.movieDao()
        }
        return View.inflate(context, R.layout.genres_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rcGenre.adapter = adapter
        thread = DataBaseThread()
        thread.launch {
            progressBarGenre.visibility = View.VISIBLE
            favMovies = db.getAll()
            progressBarGenre.visibility = View.GONE
        }
        configRetrofit(page)
    }

    fun configRetrofit(page: Int) {
        loading = true
        var resultsRetrofit: SearchResults
        thread.launch {
            progressBarGenre.visibility = View.VISIBLE
            try {
                resultsRetrofit =
                    RetrofitInitializer().retrofitServices.getMoviesByGenres(id.toInt(), page)
                val moviesResults = resultsRetrofit.results
                if (page == resultsRetrofit.totalPages)
                    lastPage = true
                configureImagesGlide(moviesResults)
                configureRecycler(moviesResults)
            } catch (e: Throwable) {
                Log.e("error", e.message.toString())
                Toast.makeText(context, "Movies not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun configureImagesGlide(moviesResults: ArrayList<MovieService>) {
        val size = moviesResults.size - 1
        for (i in 0..size) {
            moviesResults[i].posterPath?.let {
                Log.v("Teste1", moviesResults[i].posterPath + i.toString())
                suspendCoroutine<MovieService> { continuation ->
                    Glide.with(context).asBitmap()
                        .load("http://image.tmdb.org/t/p/w185/" + moviesResults[i].posterPath)
                        .into(object : SimpleTarget<Bitmap>(100, 100) {
                            override fun onResourceReady(
                                resource: Bitmap?,
                                transition: Transition<in Bitmap>?
                            ) {
                                val stream = ByteArrayOutputStream()
                                resource?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                                moviesResults[i].posterPath =
                                    Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
                                continuation.resume(moviesResults[i])
                            }
                        })
                }
            }
        }
    }

    //function to configure the recycler view
    private fun configureRecycler(results: ArrayList<MovieService>?) {
        progressBarGenre.visibility = View.GONE
        results?.let {
            val moviesSearched =
                MoviePresentationMapper().convertListMovieService(results, favMovies)
            adapter.addAll(moviesSearched)
        }
        loading = false
        pagination()
    }

    fun pagination() {
        rcGenre.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !lastPage && !loading) {
                    page++
                    Log.v("teste", page.toString())
                    configRetrofit(page)
                }
            }
        })
    }

}