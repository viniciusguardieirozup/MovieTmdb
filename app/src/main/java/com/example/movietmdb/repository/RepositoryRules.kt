package com.example.movietmdb.repository

import com.example.movietmdb.mappers.MoviePresentationMapper
import com.example.movietmdb.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.DAO.MovieDao
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.GenresList
import com.example.movietmdb.repository.retrofit.MoviesAPI
import com.example.movietmdb.repository.retrofit.SearchResults
import java.lang.Exception

class RepositoryRules(private val moviesAPI: MoviesAPI, private val movieDAO: MovieDao) {

    suspend fun insertMovie(movie: MovieData) {
        movieDAO.insertMovie(movie)
    }

    suspend fun removeMovie(movie: MovieData) {
        movieDAO.removeMovie(movie)
    }

    suspend fun getMovies(name: String, page: Int) =
        moviesAPI.searchMoviesByUser(name, page)

    suspend fun getFavMovies() = movieDAO.getAll()

    suspend fun getSimilar(id: Int, page: Int) = moviesAPI.getSimilars(id, page)

    suspend fun getMoviesByGenres(id: Int, page: Int) = moviesAPI.getMoviesByGenres(id, page)


    suspend fun getGenres(): GenresList {
        return moviesAPI.getGenres()
    }
}