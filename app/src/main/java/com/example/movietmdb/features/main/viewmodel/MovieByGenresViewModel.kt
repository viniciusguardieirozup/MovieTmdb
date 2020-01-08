package com.example.movietmdb.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietmdb.repository.retrofit.GenresList
import com.example.movietmdb.repository.retrofit.RetrofitInitializer
import kotlinx.coroutines.launch

sealed class ViewStateGenre {
    class Loading(val loading: Boolean) : ViewStateGenre()
    class Data(val genres: GenresList) : ViewStateGenre()
}

class MovieByGenresViewModel : ViewModel() {

    val movieListData = MutableLiveData<ViewStateGenre>()

    fun getGenres() {
        movieListData.value = ViewStateGenre.Loading(true)
        viewModelScope.launch {
            movieListData.value =
                ViewStateGenre.Data(RetrofitInitializer().retrofitServices.getGenres())
            movieListData.value = ViewStateGenre.Loading(false)
        }
    }

}