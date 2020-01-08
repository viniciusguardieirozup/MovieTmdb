package com.example.movietmdb.recycler

import com.example.movietmdb.recycler.data.MoviePresentation


interface FavButtonListener {
    fun favButtonClicked(moviePresentation: MoviePresentation)

}
