package com.example.movietmdb.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.presentation.recycler.data.MoviePresentation
import com.example.movietmdb.repository.retrofit.GenresList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

sealed class ViewState {
    class Loading(val loading: Boolean) : ViewState()
    class Data(val movies: List<MoviePresentation>) : ViewState()
    class Error(val message: String) : ViewState()
    class Genre(val genres: GenresList) : ViewState()
}

open class BaseMovieViewModel : ViewModel() {

    lateinit var movie: MoviePresentation
    var loading = false
    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()
     protected var job : Job = Job()


    fun load(block: suspend () -> Unit) {
        job = viewModelScope.launch {
            try {
                block()
            }
            catch (e : CancellationException){

            }
            catch (e: Exception) {
                moviesLiveData.value =
                    ViewState.Error(
                        "Problem to find this movie"
                    )
            } finally {
                loading = false
            }
        }
    }
}