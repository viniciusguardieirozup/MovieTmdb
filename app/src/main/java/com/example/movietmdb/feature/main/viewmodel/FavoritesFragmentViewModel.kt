package com.example.movietmdb.feature.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoritesFragmentViewModel : ViewModel(), CoroutineScope{
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun stopJob() {
        job.cancel()
    }

    val moviesLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun getFavMovies(){
        moviesLiveData.value = ViewState.Loading(true)
        launch {
            moviesLiveData.value = ViewState.Data(RepositoryRules().getFavMovies())
            moviesLiveData.value = ViewState.Loading(false)
        }

    }
}