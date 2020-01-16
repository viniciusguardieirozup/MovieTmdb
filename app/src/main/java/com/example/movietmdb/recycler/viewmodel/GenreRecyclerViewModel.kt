package com.example.movietmdb.recycler.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.features.main.viewmodel.ViewState
import com.example.movietmdb.repository.RepositoryRules
import kotlinx.coroutines.launch

class GenreRecyclerViewModel(
    private val repositoryRules: RepositoryRules
) : ViewModel() {

    val mutableLiveData = MutableLiveData<ViewState>()

    fun getMoviesByGenres(id: Int) {
        viewModelScope.launch {
            mutableLiveData.value = ViewState.Data(repositoryRules.getMoviesByGenres(id, 1))
        }
    }
}