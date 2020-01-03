package com.example.movietmdb.feature.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietmdb.recycler.MoviePresentation
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

sealed class ViewState {
    class Loading(val loading: Boolean) : ViewState()
    class Data(val movies: List<MoviePresentation>) : ViewState()
}

class SearchFragmentViewModel : ViewModel(), CoroutineScope {

    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun stopJob() {
        job.cancel()
    }

    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun searchMovies(name: String, page: Int) {
        moviesLiveData.value =
            ViewState.Loading(
                true
            )
        launch {
            moviesLiveData.value =
                ViewState.Data(
                    RepositoryRules().getMovies(
                        name,
                        page
                    )
                )
            moviesLiveData.value =
                ViewState.Loading(
                    false
                )
        }
    }

}