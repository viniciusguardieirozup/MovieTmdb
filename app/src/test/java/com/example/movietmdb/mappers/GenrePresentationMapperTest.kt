package com.example.movietmdb.mappers

import org.junit.Test

class GenrePresentationMapperTest : BaseMapper() {

    @Test
    fun convertFromGenre_genres_genrePresentation() {
        //GIVEN
        val genres = returnGenre()
        //WHEN
        val result = GenrePresentationMapper.convertFromGenre(genres)
        //THEN
        assertGenre(genres, result)
    }

    @Test
    fun convertList_arrayGenres_arrayGenrePresentation() {
        //GIVEN
        val genresList = returnGenreList()

        //WHEN
        val result = GenrePresentationMapper.convertList(genresList)

        //THEN
        for (i in 1..10)
            assertGenre(genresList.genres[i], result = result[i])
    }
}