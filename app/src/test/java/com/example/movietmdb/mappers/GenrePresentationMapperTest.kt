package com.example.movietmdb.mappers

import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.recycler.data.GenrePresentation
import com.example.movietmdb.repository.retrofit.Genres
import com.example.movietmdb.repository.retrofit.GenresList
import io.mockk.Matcher
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Array

class GenrePresentationMapperTest : BaseJUnitTest() {

    @MockK
    lateinit var genre: Genres
    @MockK
    lateinit var genreList: GenresList

    @Test
    fun convertFromGenre_genre_GenrePresentation() {
        //GIVEN
        val genre = Genres(id = 0, name = "Stub")

        //WHEN
        val result = GenrePresentationMapper.convertFromGenre(genre)

        //THEN
        assertEquals(0, result.id)
        assertEquals("Stub", result.name)
    }

    @Test
    fun convertList_genreList_ArrayListGenrePresentation() {
        //GIVEN
        every { genreList.genres.size } returns 10
        for (i in 0..9)
            every { genreList.genres[i] } returns genre
        every { genre.id } returns 0
        every { genre.name } returns "Stub"

        //WHEN
        val result = GenrePresentationMapper.convertList(genreList)

        //THEN
        for(i in 0..9){
            assertEquals(0,result[i].id)
            assertEquals("Stub",result[i].name)
        }

    }

}