package com.example.movietmdb.mappers

import org.junit.Test

class MovieDataMapperTest : BaseMapper() {

    @Test
    fun mapFromPresentation_movieData(){
        //GIVEN
        val movie = returnMoviePresentation()

        //WHEN
        val result = MovieDataMapper.mapFromPresentation(movie)

        //THEN
        assertFromMoviePresentationToMovieData(movie,result)
    }

    @Test
    fun convertListMovieService_movieData(){
        //GIVEN
        val aux = returnListService()
        //WHEN
        val result = MovieDataMapper.convertListMovieService(aux)
        //THEN
        val size = aux.size -1
        for(i in 0..size){
            assertFromMovieServiceToMovieData(aux[i], result = result[i])
        }
    }





}