package com.example.movietmdb.ui.fragments

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.movietmdb.MovieTmdbApplication
import com.example.movietmdb.R
import com.example.movietmdb.coroutines.DataBaseThread
import com.example.movietmdb.repository.database.MovieData
import com.example.movietmdb.mapper.MoviePresentationMapper
import com.example.movietmdb.ui.recycler.CostumAdapter
import com.example.movietmdb.repository.retrofit.MovieService
import com.example.movietmdb.repository.retrofit.RetrofitInitializer
import com.example.movietmdb.repository.retrofit.SearchResults
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
    private lateinit var adapter: CostumAdapter
    private lateinit var movieName: String
    private var page = 1
    private var lastPage = false
    private var loading = false

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
        adapter = CostumAdapter()
        recylerSearchMovie.adapter = adapter
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
        movieName = searchMovie.query.toString()
        page = 1
        getResultRetrofit(movieName, page)
    }

    //function to get the results from retrofit
    private fun getResultRetrofit(movieName: String, page: Int) {
        loading = true
        var resultsRetrofit: SearchResults
        thread.launch {
            progressBar3.visibility = View.VISIBLE

            try {
                resultsRetrofit =
                    RetrofitInitializer()
                        .retrofitServices.searchMoviesByUser(movieName, page)
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
        progressBar3.visibility = View.GONE

        results?.let {

            val moviesSearched =
                MoviePresentationMapper().convertListMovieService(results, favMovies)
            adapter.addAll(moviesSearched)
        }
        loading = false
        pagination()
    }

    fun pagination() {
        recylerSearchMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !lastPage && !loading) {
                    page++
                    Log.v("teste", page.toString())
                    getResultRetrofit(movieName, page)

                }
            }
        })
    }

}


