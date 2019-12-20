package com.example.movietmdb.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.movietmdb.R
import com.example.movietmdb.mapper.DataMoviesMapper
import com.example.movietmdb.recycler.CostumAdapter
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.retrofit.RetrofitInitializer
import com.example.movietmdb.retrofit.SearchResults
import kotlinx.android.synthetic.main.search_movies_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.search_movies_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    private fun getTextToSearch() {
        searchMovie.clearFocus()
        val movieName = searchMovie.query.toString()
        searchMovie.setQuery("", false)
        getResultRetrofit(movieName)
    }

    private fun getResultRetrofit(movieName: String) {
        val call = RetrofitInitializer().retrofitServices.searchMoviesByUser(movieName)
        call.enqueue(object : Callback<SearchResults> {
            override fun onFailure(call: Call<SearchResults>, t: Throwable) {
                Log.v("erro", t.message.toString())
            }

            override fun onResponse(call: Call<SearchResults>, response: Response<SearchResults>) {
                val results = response.body()
                results?.let {
                    configureRecycler(results)
                }
            }

        })
    }

    private fun configureRecycler(results: SearchResults) {
        val size = results.results.size - 1
        val movieList = ArrayList<MoviePresentation>()
        for (i in 0..size) {
            movieList.add(DataMoviesMapper().mapInMoviePresentation(results.results[i]))
        }
        recylerSearchMovie.adapter = CostumAdapter(movieList)

    }

}