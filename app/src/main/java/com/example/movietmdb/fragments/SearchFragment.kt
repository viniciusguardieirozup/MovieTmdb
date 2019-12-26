package com.example.movietmdb.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.database.MovieData
import com.example.movietmdb.mapper.MoviePresentationMapper
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.retrofit.MovieService
import com.example.movietmdb.retrofit.RetrofitInitializer
import com.example.movietmdb.retrofit.SearchResults
import kotlinx.android.synthetic.main.search_movies_layout.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//fragment for  searchMovies
class SearchFragment : Fragment() {

    private var favMovies: List<MovieData> = ArrayList()
    private var thread = DataBaseThread()
    private var db = MovieTmdbApplication.db.movieDao()

    //static function
    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    //function to inflate layout into this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.search_movies_layout, null)
    }

    //function called when this fragment was created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        thread.launch {
            progressBar3.visibility = View.VISIBLE
            favMovies = db.getAll()
            progressBar3.visibility = View.GONE
        }
        searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getTextToSearch()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


    //function to get the movie name typed by the user and call getResultsRetrofit
    private fun getTextToSearch() {
        searchMovie.clearFocus()
        getResultRetrofit(searchMovie.query.toString())
    }

    //function to get the results from retrofit
    private fun getResultRetrofit(movieName: String) {
        var results: SearchResults
        thread.launch {
            progressBar3.visibility = View.VISIBLE

            try {
                results = RetrofitInitializer().retrofitServices.searchMoviesByUser(movieName)
                val moviesResults = results.results

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

    //function to configure the recycler view
    private fun configureRecycler(results: ArrayList<MovieService>?) {
        progressBar3.visibility = View.GONE
        results?.let {
            val moviesSearched =
                MoviePresentationMapper().convertListMovieService(results, favMovies)

            recylerSearchMovie.adapter = CostumAdapter(moviesSearched)
        }



    }
}


