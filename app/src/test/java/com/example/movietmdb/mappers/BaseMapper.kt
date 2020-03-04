package com.example.movietmdb.mappers

import com.example.movietmdb.BaseJUnitTest
import com.example.movietmdb.presentation.recycler.data.GenrePresentation
import com.example.movietmdb.presentation.recycler.data.MoviePresentation
import com.example.movietmdb.repository.db.entity.MovieData
import com.example.movietmdb.repository.retrofit.Genres
import com.example.movietmdb.repository.retrofit.GenresList
import com.example.movietmdb.repository.retrofit.MovieService
import org.junit.Assert

open class BaseMapper : BaseJUnitTest() {


    //Genres
    protected fun returnGenreList(): GenresList {
        val listGenres = ArrayList<Genres>()
        val aux = returnGenre()
        for (i in 0..10) {
            listGenres.add(aux)
        }
        return GenresList(listGenres)
    }

    protected fun returnGenre(): Genres {
        return Genres(1, "name")
    }

    protected fun assertGenre(genres: Genres, result: GenrePresentation) {
        Assert.assertEquals(genres.id, result.id)
        Assert.assertEquals(genres.name, result.name)
    }

    //MovieData
    protected fun assertFromMoviePresentationToMovieData(
        movie: MoviePresentation,
        result: MovieData
    ) {
        Assert.assertEquals(movie.backdropPath, result.backdropPath)
        Assert.assertEquals(movie.posterPath, result.posterPath)
        Assert.assertEquals(movie.id, result.id)
        Assert.assertEquals(movie.genreIds.toString(), result.genreIds)
        Assert.assertEquals(movie.overView, result.overView)
        Assert.assertEquals(movie.title, result.title)
        Assert.assertEquals(movie.popularity, result.popularity)
        Assert.assertEquals(movie.voteCount, result.voteCount)
        Assert.assertEquals("10.0", (result.voteAverage * 10).toString())
    }

    protected fun returnMovieData(): MovieData {
        return MovieData(
            null,
            null,
            "[10]",
            42,
            null,
            null,
            null,
            null,
            10.0
        )
    }

    protected fun assertFromMovieDataToMoviePresentation(
        movie: MovieData,
        result: MoviePresentation
    ) {
        val aux = ArrayList<Int>()
        aux.add(10)
        Assert.assertEquals(null, result.backdropPath)
        Assert.assertEquals(movie.posterPath, result.posterPath)
        Assert.assertEquals(movie.id, result.id)
        Assert.assertEquals(aux, result.genreIds)
        Assert.assertEquals(movie.overView, result.overView)
        Assert.assertEquals(movie.title, result.title)
        Assert.assertEquals(movie.popularity, result.popularity)
        Assert.assertEquals(movie.voteCount, result.voteCount)
        Assert.assertEquals("100", result.voteAverage)
    }

    //Movie Presentation
    protected fun returnMoviePresentation(): MoviePresentation {
        val aux = ArrayList<Int>()
        aux.add(10)
        return MoviePresentation(
            null,
            null,
            aux,
            42,
            null,
            null,
            null,
            null,
            "10",
            0
        )
    }

    //MovieService
    private fun returnMovieService(): MovieService {
        val aux = ArrayList<Int>()
        aux.add(10)
        return MovieService(
            null,
            null,
            "teste",
            null,
            aux,
            42,
            null,
            null,
            "10",
            null,
            null,
            null,
            null,
            10.0
        )
    }

    private fun returnMovieServicePosterPathNotNull(): MovieService {
        val aux = ArrayList<Int>()
        aux.add(10)
        return MovieService(
            "123",
            null,
            "teste",
            null,
            aux,
            42,
            null,
            null,
            "10",
            null,
            null,
            null,
            null,
            10.0
        )
    }

    protected fun returnListService(): List<MovieService> {
        val aux = returnMovieService()
        val list = ArrayList<MovieService>()
        for (i in 0..10) {
            list.add(aux)
        }
        return list
    }

    protected fun returnListServicePathNotNull(): List<MovieService> {
        val aux = returnMovieServicePosterPathNotNull()
        val list = ArrayList<MovieService>()
        for (i in 0..10) {
            list.add(aux)
        }
        return list
    }

    fun assertFromMovieServiceToMovieData(movie: MovieService, result: MovieData) {
        Assert.assertEquals("https://image.tmdb.org/t/p/original/null", result.backdropPath)
        Assert.assertEquals(movie.posterPath, result.posterPath)
        Assert.assertEquals(movie.id, result.id)
        Assert.assertEquals(movie.genreIds.toString(), result.genreIds)
        Assert.assertEquals(movie.overView, result.overView)
        Assert.assertEquals(movie.title, result.title)
        Assert.assertEquals(movie.popularity, result.popularity)
        Assert.assertEquals(movie.voteCount, result.voteCount)
        Assert.assertEquals("10.0", result.voteAverage.toString())
    }

    fun assertFromMovieServiceToMoviePresentation(movie: MovieService, result: MoviePresentation) {
        Assert.assertEquals("https://image.tmdb.org/t/p/original/null", result.backdropPath)
        Assert.assertEquals(movie.posterPath, result.posterPath)
        Assert.assertEquals(movie.id, result.id)
        Assert.assertEquals(movie.genreIds, result.genreIds)
        Assert.assertEquals(movie.overView, result.overView)
        Assert.assertEquals(movie.title, result.title)
        Assert.assertEquals(movie.popularity, result.popularity)
        Assert.assertEquals(movie.voteCount, result.voteCount)
        Assert.assertEquals("100", result.voteAverage)
    }
}