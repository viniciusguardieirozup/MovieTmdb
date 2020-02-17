package com.example.movietmdb.features.main.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter.setPassword
import androidx.lifecycle.MutableLiveData
import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.repository.MoviesRepository
import com.example.movietmdb.repository.retrofit.SearchResults
import com.example.movietmdb.viewModel.PaginationViewModel
import com.example.movietmdb.viewModel.ViewState


class SearchViewModel(private val moviesRepository: MoviesRepository) :
    PaginationViewModel() {

    val movieName = MutableLiveData<String>()

    val resetAdapter = MutableLiveData<Boolean>()

    private var oldName = ""

    fun searchMovies() {
        checkNewSearch(movieName.value.toString())
        if (!loading && !lastPage) {
            moviesLiveData.value = ViewState.Loading(true)
            loadSimilar(movieName.value.toString())
        } else {
            noMorePageAvailable()
        }
        moviesLiveData.value = ViewState.Loading(false)
    }

    private fun checkNewSearch(name: String) {
        if (oldName != name) {
            lastPage = false
            page = 1
            oldName = name

            job.cancel()

            resetAdapter.value = true
        } else {
            resetAdapter.value = false
        }
    }

    private suspend fun accessRepositoryMapResult(name: String): SearchResults {
        val moviesResults = moviesRepository.getMovies(name, page)
        val favMovies = moviesRepository.getFavMovies()
        moviesLiveData.value = ViewState.Data(
            MoviePresentationMapper.convertListMovieService(
                moviesResults.results,
                favMovies
            )
        )
        return moviesResults
    }

    private fun loadSimilar(name: String) {
        load {
            loading = true
            val moviesResults = accessRepositoryMapResult(name)
            checkLastPage(moviesResults)
            loading = false
            noMoviesAvailable(moviesResults)
        }
    }

}