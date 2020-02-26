package com.example.movietmdb.mappers

import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.repository.db.entity.MovieData
import org.junit.Assert.*
import org.junit.Test

class MoviePresentationMapperTest : BaseMapper() {

    @Test
    fun converterListMovieData_moviePresentation() {
        //GIVEN
        val aux = returnMovieData()
        val list = ArrayList<MovieData>()
        for (i in 0..10)
            list.add(aux)

        //WHEN
        val result = MoviePresentationMapper.converterListMovieData(list)

        //THEN
        for (i in 0..10) {
            assertFromMovieDataToMoviePresentation(list[i], result = result[i])
        }
    }

    @Test
    fun converterListMovieService_moviePresentation() {
        //GIVEN
        val aux = returnListService()

        //WHEN
        val result = MoviePresentationMapper.convertListMovieService(aux)

        //THEN
        for (i in 0..10) {
            assertFromMovieServiceToMoviePresentation(aux[i], result = result[i])
        }
    }


}