package com.example.movietmdb.mappers

import com.example.movietmdb.presentation.recycler.data.GenrePresentation
import com.example.movietmdb.repository.retrofit.Genres
import com.example.movietmdb.repository.retrofit.GenresList

object GenrePresentationMapper {
    fun convertFromGenre(genre: Genres): GenrePresentation {
        return GenrePresentation(genre.id, genre.name)
    }

    fun convertList(genre: GenresList): ArrayList<GenrePresentation> {
        val aux = ArrayList<GenrePresentation>()
        val size = genre.genres.size - 1
        for (i in 0..size) {
            aux.add(convertFromGenre(genre.genres[i]))
        }
        return aux
    }
}